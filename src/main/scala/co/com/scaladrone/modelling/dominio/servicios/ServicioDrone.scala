package co.com.scaladrone.modelling.dominio.servicios

import co.com.scaladrone.modelling.dominio.entidades._

import scala.collection.immutable
import scala.io.Source
import scala.util.Try

// Necesidad del Drone al cual se le va a aplicar las funciones y la lógica
// Cohesión en servicio (Servicios diferentes)
// Mejor girar que girar derecha e izquierda (Sugerencia)


sealed trait AlgebraServicioDrone {
  def girarIzquierda(drone: Drone): Drone
  def girarDerecha(drone: Drone): Drone
  def avanzar(drone: Drone): Drone
  def mover(instruccion: Instruccion, drone: Drone): Drone
  def realizarEntrega(entrega: Entrega, drone: Drone): Drone
  def realizarEntregas(ruta: Ruta, drone: Drone): List[Drone]
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
      val res = Drone(EstadoDrone(drone.posicionActual.coordenada, x))
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
      val res = Drone(EstadoDrone(drone.posicionActual.coordenada, x))
      //println(res)
      res
    }



    override def avanzar(drone: Drone): Drone = {
      val x = drone.posicionActual.coordenada.x
      val y = drone.posicionActual.coordenada.y

      drone.posicionActual.orientacion match {

        case N() => Drone(EstadoDrone(Coordenada(x, y + 1), drone.posicionActual.orientacion))
        case S() => Drone(EstadoDrone(Coordenada(x, y - 1), drone.posicionActual.orientacion))
        case E() => Drone(EstadoDrone(Coordenada(x + 1, y), drone.posicionActual.orientacion))
        case O() => Drone(EstadoDrone(Coordenada(x - 1, y), drone.posicionActual.orientacion))

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

      entrega
        .movimientos
        .foldLeft(drone)((estado, instru) => mover(instru, estado))

    }

    override def realizarEntregas(ruta: Ruta, drone: Drone): List[Drone] = {

      val droneL: List[Drone] = List(drone)

      val res = ruta.pedidos.foldLeft(droneL){
        (listaEstados, listaInstrucciones) =>
          listaEstados :+ realizarEntrega(listaInstrucciones, listaEstados.last)
      }

      // val res5 = res4
      // .fold[List[Drone]](x=>{List(Drone(EstadoDrone(Coordenada(0,0),N())))},
      // y=>{InterpretacionAlgebraServicioDrone.realizarEntregas(y, droneInicial)})
        res
    }

}

// Trait Object
object InterpretacionAlgebraServicioDrone extends InterpretacionAlgebraServicioDrone

