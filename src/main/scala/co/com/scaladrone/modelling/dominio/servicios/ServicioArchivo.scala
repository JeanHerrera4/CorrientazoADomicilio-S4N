package co.com.scaladrone.modelling.dominio.servicios

import java.io.{File, PrintWriter}

import co.com.scaladrone.modelling.dominio.entidades._

import scala.io.Source
import scala.util.{Success, Try}

sealed trait AlgebraServicioArchivo {

  def leerArchivo(url: String): List[String]
  def caracterAInstruccion(cadena: String): Entrega
  def archivoAListaInstrucciones(cadenaString: List[String]): Try[Ruta]
  //def rutasValidas(rutas : Try[Ruta]): Ruta
  def exportarArchivo(listaDrone: List[Drone])
  def convertirOrientacionAString(orientacion: Orientacion): String
}

sealed trait InterpretacionAlgebraServicioArchivo extends AlgebraServicioArchivo {

  override def leerArchivo(url: String): List[String] = {
    val fuente = Source.fromFile(url)
    fuente.getLines.toList
  }

  override def caracterAInstruccion(cadena: String): Entrega = {
    val caracteresIntruccion = cadena.toList
    Entrega(caracteresIntruccion.map(x => Instruccion.newInstruccion(x)))
  }

  override def archivoAListaInstrucciones(listaString: List[String]): Try[Ruta] = {
    Try(Ruta(listaString.map(x => Entrega(x.toList.map(y => Instruccion.newInstruccion(y))))))
  }

  /*override def rutasValidas(rutas: Try[Ruta]): Ruta = {
    rutas.filter(x => x == Success)
  }*/

  //Lista de Try de rutas y manejar la excepción, aquí tiro el fold

  override def exportarArchivo(listDrone: List[Drone]): Unit = {

    val impresora = new PrintWriter(new File(s"out01.txt"))
    impresora.write("== Reporte de entregas ==")
    listDrone.map(x => {
        val posX = x.posicionActual.coordenada.x
        val posY = x.posicionActual.coordenada.y
        val orientacion = convertirOrientacionAString(x.posicionActual.orientacion)
        impresora.write(s"\n($posX,$posY) dirección $orientacion")
    })
    impresora.close()
  }

  override def convertirOrientacionAString(orientacion: Orientacion): String = {

    orientacion match {
      case N() => "Norte"
      case S() => "Sur"
      case E() => "Este"
      case O() => "Oeste"
    }
  }

}

// Trait Object
object InterpretacionAlgebraServicioArchivo extends InterpretacionAlgebraServicioArchivo

