package co.com.scaladrone.modelling.dominio

import co.com.scaladrone.modelling.dominio.entidades._
import co.com.scaladrone.modelling.dominio.servicios.InterpretacionAlgebraServicioDrone
import co.com.scaladrone.modelling.dominio.servicios.InterpretacionAlgebraServicioArchivo

object Main extends App {

  // Función del servicio Drone: Recibir el in txt y entregar el out txt
  val ruta = "/home/s4n/Documents/scala-drone/src/main/resources/in/in01.txt"
  InterpretacionAlgebraServicioDrone.entregarAlmuerzos(ruta)

}
