package co.com.scaladrone.modelling.dominio.servicios

import co.com.scaladrone.modelling.dominio.entidades._

import scala.collection.immutable
import scala.io.Source

// Necesidad del Drone al cual se le va a aplicar las funciones y la lógica
// Cohesión en servicio (Servicios diferentes)
// Mejor girar que girar derecha e izquierda (Sugerencia)


sealed trait AlgebraServicioDrone {
  def girarIzquierda(estadoDrone: EstadoDrone): EstadoDrone
  def girarDerecha(estadoDrone: EstadoDrone): EstadoDrone
  def avanzar(estadoDrone: EstadoDrone): EstadoDrone
  def mover(instruccion: Instruccion, estadoDrone: EstadoDrone): EstadoDrone
  def realizarEntrega(listaInstrucciones: List[Instruccion]): EstadoDrone
  def realizarEntregas(listasInstrucciones: List[List[Instruccion]]): List[EstadoDrone]
}

  sealed trait InterpretacionAlgebraServicioDrone extends AlgebraServicioDrone{

    val estadoInicial = EstadoDrone(Coordenada(0,0),N())

    override def girarDerecha(estadoDrone: EstadoDrone): EstadoDrone = {
      val x = estadoDrone.orientacion match{
        case N() => E()
        case S() => O()
        case E() => S()
        case O() => N()
      }
      val res = EstadoDrone(estadoDrone.coordenada,x)
      //println(res)
      res
    }

    override def girarIzquierda(estadoDrone: EstadoDrone): EstadoDrone = {
      val x = estadoDrone.orientacion match {
        case N() => O()
        case S() => E()
        case E() => N()
        case O() => S()
      }
      val res = EstadoDrone(estadoDrone.coordenada,x)
      //println(res)
      res
    }

    override def avanzar(estadoDrone: EstadoDrone): EstadoDrone = {
      val x = estadoDrone.coordenada.x
      val y = estadoDrone.coordenada.y

      val xy = estadoDrone.orientacion match {
        case N() => Coordenada(x,y+1)
        case S() => Coordenada(x, y-1)
        case E() => Coordenada(x+1, y)
        case O() => Coordenada(x-1, y)
      }
      val res = EstadoDrone(xy, estadoDrone.orientacion)
      //println(res)
      res
    }

    override def mover(instruccion: Instruccion, estadoDrone: EstadoDrone): EstadoDrone = {

      instruccion match {
        case A() => avanzar(estadoDrone)
        case D() => girarDerecha(estadoDrone)
        case I() => girarIzquierda(estadoDrone)
        case _ => throw new Exception(s"Caracter invalido para creacion de instruccion")
      }
    }

   override def realizarEntrega(listaInstrucciones: List[Instruccion]): EstadoDrone = {

      listaInstrucciones
        .foldLeft(EstadoDrone(Coordenada(0,0), N()))((estado, instru) => mover(instru, estado))
    }

    override def realizarEntregas(listasInstrucciones: List[List[Instruccion]]): List[EstadoDrone] = {

      val res = listasInstrucciones.foldLeft(List(EstadoDrone(Coordenada(0,0), N()))){
        (listEstado, listInstruccion) =>
          listEstado :+ listEstado.flatMap(x => realizarEntrega(listInstruccion))
            //resultado.map(x => realizarEntrega(item))

      }
        res
        /*.foldLeft(EstadoDrone(Coordenada(0, 0), N())){
        (estado,listaInstru) => realizarEntrega(listaInstru)
      }*/

    }

}

// Trait Object
object InterpretacionAlgebraServicioDrone extends InterpretacionAlgebraServicioDrone

