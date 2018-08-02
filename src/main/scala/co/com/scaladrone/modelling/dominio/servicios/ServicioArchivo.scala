package co.com.scaladrone.modelling.dominio.servicios

import java.io.{File, PrintWriter}

import co.com.scaladrone.modelling.dominio.entidades._

import scala.io.Source
import scala.util.Try

sealed trait AlgebraServicioArchivo {

  def leerArchivo(url: String): List[String]
  def caracterAInstruccion(cadena: String): Entrega
  def archivoAListaInstrucciones(cadenaString: List[String]): Try[Ruta]
  def exportarArchivo(listaDrone: List[Drone])
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

  override def exportarArchivo(listDrone: List[Drone]): Unit = {

    val impresora = new PrintWriter(new File(s"out01.txt"))
    listDrone.map(x => {
        val posX = x.posicionActual.coordenada.x
        val posY = x.posicionActual.coordenada.y
        val orientacion = x.posicionActual.orientacion.toString
        impresora.write(s"\n($posX,$posY) dirección $orientacion")
    })
    impresora.close()
  }

}

// Trait Object
object InterpretacionAlgebraServicioArchivo extends InterpretacionAlgebraServicioArchivo

