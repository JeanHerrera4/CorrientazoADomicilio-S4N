package co.com.scaladrone.modelling.dominio

import co.com.scaladrone.modelling.dominio.entidades._
import co.com.scaladrone.modelling.dominio.servicios.InterpretacionAlgebraServicioDrone
import co.com.scaladrone.modelling.dominio.servicios.InterpretacionAlgebraServicioArchivo

object Main extends App {

  // Función del servicio Drone: Recibir el in txt y entregar el out txt

  //val ruta = "src/main/resources/in/in01.txt"
  //InterpretacionAlgebraServicioDrone.entregarAlmuerzos(ruta)

  val rutas = List("src/main/resources/in/in01.txt", "src/main/resources/in/in02.txt", "src/main/resources/in/in03.txt")
  InterpretacionAlgebraServicioDrone.enviarDomicilios(rutas)

}
