package co.com.scaladrone.modelling.dominio.servicios

import java.util.concurrent.Executors

import co.com.scaladrone.modelling.dominio.entidades._

import scala.collection.immutable
import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source
import scala.util.{Success, Try}

// Necesidad del Drone al cual se le va a aplicar las funciones y la lógica
// Cohesión en servicio (Servicios diferentes)
// Mejor girar que girar derecha e izquierda (Sugerencia)


sealed trait AlgebraServicioDrone {
  def girarIzquierda(drone: Drone): Drone
  def girarDerecha(drone: Drone): Drone
  def avanzar(drone: Drone): Drone
  def mover(instruccion: Instruccion, drone: Drone): Drone
  def realizarEntrega(entrega: Entrega, drone: Drone): Drone
  def realizarEntregas(ruta: Ruta, drone: Drone): Future[List[Drone]]
  def realizarPedidos(periplo: Periplo): List[Future[List[Drone]]]
  def entregarAlmuerzos(url: String)
  def enviarDomicilios(listaUrl: List[String])
}

  sealed trait InterpretacionAlgebraServicioDrone extends AlgebraServicioDrone{

    val estadoInicial = EstadoDrone(Coordenada(0,0),N())

    override def girarDerecha(drone: Drone): Drone = {
      val x = drone.posicionActual.orientacion match{
        case N() => E()
        case S() => O()
        case E() => S()
        case O() => N()
      }
      val res = Drone(drone.identificador,EstadoDrone(drone.posicionActual.coordenada, x),drone.capacidad)
      //println(res)
      res
    }

    override def girarIzquierda(drone: Drone): Drone = {
      val x = drone.posicionActual.orientacion match {
        case N() => O()
        case S() => E()
        case E() => N()
        case O() => S()
      }
      val res = Drone(drone.identificador,EstadoDrone(drone.posicionActual.coordenada, x),drone.capacidad)
      //println(res)
      res
    }


    override def avanzar(drone: Drone): Drone = {
      val x = drone.posicionActual.coordenada.x
      val y = drone.posicionActual.coordenada.y

      drone.posicionActual.orientacion match {

        case N() => Drone(drone.identificador, EstadoDrone(Coordenada(x, y + 1), drone.posicionActual.orientacion), drone.capacidad)
        case S() => Drone(drone.identificador, EstadoDrone(Coordenada(x, y - 1), drone.posicionActual.orientacion), drone.capacidad)
        case E() => Drone(drone.identificador, EstadoDrone(Coordenada(x + 1, y), drone.posicionActual.orientacion), drone.capacidad)
        case O() => Drone(drone.identificador, EstadoDrone(Coordenada(x - 1, y), drone.posicionActual.orientacion), drone.capacidad)

      }

      /*drone.posicionActual.orientacion match {
        case N() => {
          if (drone.posicionActual.coordenada.y == 10)
            Right {
              Drone(EstadoDrone(Coordenada(x, y + 1), drone.posicionActual.orientacion))
            }
          else
            Left("La posición en la coordenada no puede tener valores mayores a 10")
        }
        case S() => {
          if (drone.posicionActual.coordenada.y == -10)
            Right {
              Drone(EstadoDrone(Coordenada(x, y - 1), drone.posicionActual.orientacion))
            }
          else
            Left("La posición en la coordenada no puede tener valores menores a -10")
        }
        case E() => {
          if (drone.posicionActual.coordenada.x == 10)
            Right {
              Drone(EstadoDrone(Coordenada(x + 1, y), drone.posicionActual.orientacion))
            }
          else
            Left("La posición en la coordenada no puede tener valores mayores a 10")
        }
        case O() => {
          if (drone.posicionActual.coordenada.x == -10)
            Right {
              Drone(EstadoDrone(Coordenada(x - 1, y), drone.posicionActual.orientacion))
            }
          else
            Left("La posición en la coordenada no puede tener valores mayores a -10")
        }
      }*/
    }


    override def mover(instruccion: Instruccion, drone: Drone): Drone = {

      instruccion match {
        case A() => avanzar(drone)
        case D() => girarDerecha(drone)
        case I() => girarIzquierda(drone)
      }
    }

   override def realizarEntrega(entrega: Entrega, drone: Drone): Drone = {

     /*entrega
       .movimientos
       .scanLeft(drone)((estado, instru) => mover(instru, estado))*/

      entrega
        .movimientos
        .foldLeft(drone)((estado, instru) => mover(instru, estado))

    }

    override def realizarEntregas(ruta: Ruta, drone: Drone): Future[List[Drone]] = {

      Future(ruta.pedidos.scanLeft(drone)((x,y) => realizarEntrega(y,x)).tail)

    }
    implicit val ecParaRealizarPedidos = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(20))

    override def realizarPedidos(periplo: Periplo): List[Future[List[Drone]]] = {
      val x:List[Int] = Range(1, periplo.rutas.size + 1).toList
      val y = periplo.rutas.zip(x)
      y.map(x => realizarEntregas(x._1, Drone(x._2, EstadoDrone(Coordenada(0,0),N()),10)))
    }

    override def entregarAlmuerzos(url: String): Unit = {

      val droneInicial = Drone(1,EstadoDrone(Coordenada(0,0),N()),10)
      var estadoActual: EstadoDrone = new EstadoDrone(Coordenada(0,0),N())

      val a = InterpretacionAlgebraServicioArchivo.leerArchivo(url)
      val res1 = InterpretacionAlgebraServicioArchivo.archivoAListaInstrucciones(a)
      val res2 = res1.fold[List[Drone]](x=>{List(Drone(1,EstadoDrone(Coordenada(0,0),N()),10))}, y=>{InterpretacionAlgebraServicioDrone.realizarEntregas(y, droneInicial)})
      val res3 = InterpretacionAlgebraServicioArchivo.exportarArchivo(res2)
    }

    override def enviarDomicilios(listaUrl: List[String]): Unit = {
      val droneInicial = Drone(1,EstadoDrone(Coordenada(0,0),N()),10)
      var estadoActual: EstadoDrone = new EstadoDrone(Coordenada(0,0),N())

      val a = InterpretacionAlgebraServicioArchivo.leerArchivos(listaUrl)
      val res1 = InterpretacionAlgebraServicioArchivo.archivosAListasInstrucciones(a)
      val res2 = res1.fold[List[List[Drone]]](x=>{List(List(Drone(1,EstadoDrone(Coordenada(0,0),N()),10)))}, y=>{InterpretacionAlgebraServicioDrone.realizarPedidos(y)})
      val res3 = InterpretacionAlgebraServicioArchivo.exportarArchivos(res2)
    }

}

// Trait Object
object InterpretacionAlgebraServicioDrone extends InterpretacionAlgebraServicioDrone

