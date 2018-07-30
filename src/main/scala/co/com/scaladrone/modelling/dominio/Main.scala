package co.com.scaladrone.modelling.dominio

import co.com.scaladrone.modelling.dominio.entities._
import co.com.scaladrone.modelling.dominio.services.InterpretacionAlgebraServicioDrone

object Main extends App {

  val hola = "AAAAIAAD"

  val res10 = InterpretacionAlgebraServicioDrone.charToInstruccion(hola)

  var estadoActual: EstadoDrone = new EstadoDrone(Coordenada(0,0),N())

  res10.foreach(x =>  estadoActual = InterpretacionAlgebraServicioDrone.mover(x,estadoActual))

  val texto = InterpretacionAlgebraServicioDrone.leerArchivo()
  println(texto)

  //println(res10)

  /*val estadoInicial: EstadoDrone = new EstadoDrone(Coordenada(0,0),N())

  val res = InterpretacionAlgebraServicioDrone.girarDerecha(estadoInicial)
  val res2 = InterpretacionAlgebraServicioDrone.avanzar(res)
  val res3 = InterpretacionAlgebraServicioDrone.girarDerecha(res2)
  val res4 = InterpretacionAlgebraServicioDrone.avanzar(res3)
  //println(res4)

  val res9 = InterpretacionAlgebraServicioDrone.mover(I(), estadoInicial)
  //println(res9)*/


}
