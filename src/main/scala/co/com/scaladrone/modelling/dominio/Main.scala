package co.com.scaladrone.modelling.dominio

import co.com.scaladrone.modelling.dominio.entidades.{Coordenada, EstadoDrone, N}
import co.com.scaladrone.modelling.dominio.servicios.InterpretacionAlgebraServicioDrone
import co.com.scaladrone.modelling.dominio.servicios.InterpretacionAlgebraServicioArchivo

object Main extends App {

  // Función del servicio Drone: Recibir el in txt y entregar el out txt

  val prueba = "DDAIAD"
  var estadoActual: EstadoDrone = new EstadoDrone(Coordenada(0,0),N())

  val res1 = InterpretacionAlgebraServicioArchivo.caracterAInstruccion(prueba)

  println("----------------------------------+")
  val res2 = InterpretacionAlgebraServicioDrone.realizarEntrega(res1, EstadoDrone(Coordenada(0,0),N()))
  println(res2)

  println("----------------------------------")
  val res3 = InterpretacionAlgebraServicioArchivo.leerArchivo("/home/s4n/Documents/scala-drone/src/main/resources/in01.txt")
  println(res3)

  println("----------------------------------")
  val res4 = InterpretacionAlgebraServicioArchivo.archivoAListaInstrucciones(res3)
  val res5 = InterpretacionAlgebraServicioDrone.realizarEntregas(res4, List(EstadoDrone(Coordenada(0,0),N())))
  println(res5)

}
