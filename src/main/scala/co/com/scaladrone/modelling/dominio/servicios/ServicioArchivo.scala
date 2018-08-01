package co.com.scaladrone.modelling.dominio.servicios

import co.com.scaladrone.modelling.dominio.entidades.{Entrega, EstadoDrone, Instruccion, Ruta}

import scala.io.Source
import scala.util.Try

sealed trait AlgebraServicioArchivo {

  def leerArchivo(url: String): List[String]
  def caracterAInstruccion(cadena: String): Entrega
  def archivoAListaInstrucciones(cadenaString: List[String]): Try[Ruta]
  //def exportarArchivo()
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

  //override def exportarArchivo(): Unit

}

// Trait Object
object InterpretacionAlgebraServicioArchivo extends InterpretacionAlgebraServicioArchivo

