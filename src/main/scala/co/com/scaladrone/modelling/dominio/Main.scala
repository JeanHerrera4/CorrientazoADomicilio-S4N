package co.com.scaladrone.modelling.dominio

import co.com.scaladrone.modelling.dominio.entidades._
import co.com.scaladrone.modelling.dominio.servicios.InterpretacionAlgebraServicioDrone
import co.com.scaladrone.modelling.dominio.servicios.InterpretacionAlgebraServicioArchivo

import scala.util.Try

object Main extends App {

  // Función del servicio Drone: Recibir el in txt y entregar el out txt

  val droneInicial = Drone(EstadoDrone(Coordenada(0,0),N()))

  val prueba = "DDAIAD"
  var estadoActual: EstadoDrone = new EstadoDrone(Coordenada(0,0),N())

  //val res1 = InterpretacionAlgebraServicioArchivo.caracterAInstruccion(prueba)

  println("----------------------------------+")
  //val res2 = InterpretacionAlgebraServicioDrone.realizarEntrega(res1, droneInicial)
  //println(res2)

  println("----------------------------------")
  val res3 = InterpretacionAlgebraServicioArchivo.leerArchivo("/home/s4n/Documents/scala-drone/src/main/resources/in01.txt")
  println(res3)

  println("----------------------------------")
  val res4 = InterpretacionAlgebraServicioArchivo.archivoAListaInstrucciones(res3)
  val res5 = res4.fold[List[Drone]](x=>{List(Drone(EstadoDrone(Coordenada(0,0),N())))}, y=>{InterpretacionAlgebraServicioDrone.realizarEntregas(y, droneInicial)})
  //val res5 = InterpretacionAlgebraServicioDrone.realizarEntregas(res4, droneInicial)
  println(res5)

}
