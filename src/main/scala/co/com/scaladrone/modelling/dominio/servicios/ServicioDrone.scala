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

      val xy = drone.posicionActual.orientacion match {
        case N() => Coordenada(x,y+1)
        case S() => Coordenada(x, y-1)
        case E() => Coordenada(x+1, y)
        case O() => Coordenada(x-1, y)
      }
      val res = Drone(EstadoDrone(xy, drone.posicionActual.orientacion))
      //println(res)
      res
    }

    override def mover(instruccion: Instruccion, drone: Drone): Drone = {

      instruccion match {
        case A() => avanzar(drone)
        case D() => girarDerecha(drone)
        case I() => girarIzquierda(drone)
        case _ => throw new Exception(s"Caracter invalido para creacion de instruccion")
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
        res
    }
}

// Trait Object
object InterpretacionAlgebraServicioDrone extends InterpretacionAlgebraServicioDrone

