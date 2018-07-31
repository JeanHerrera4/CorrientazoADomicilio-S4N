package co.com.scaladrone.modelling.dominio.servicios

import co.com.scaladrone.modelling.dominio.entidades.{EstadoDrone, Instruccion}

import scala.io.Source

sealed trait AlgebraServicioArchivo {

  def leerArchivo(url: String): List[String]
  def caracterAInstruccion(cadena: String): List[Instruccion]
  def archivoAListaInstrucciones(cadenaString: List[String]): List[List[Instruccion]]
}

sealed trait InterpretacionAlgebraServicioArchivo extends AlgebraServicioArchivo {

  override def leerArchivo(url: String): List[String] = {
    val fuente = Source.fromFile(url)
    fuente.getLines.toList
  }

  override def caracterAInstruccion(cadena: String): List[Instruccion] = {
    val caracteresIntruccion = cadena.toList
    caracteresIntruccion.map(x => Instruccion.newInstruccion(x))
  }

  override def archivoAListaInstrucciones(listaString: List[String]): List[List[Instruccion]] = {
    listaString.map(x => x.toList.map(y => Instruccion.newInstruccion(y)))
  }

}

// Trait Object
object InterpretacionAlgebraServicioArchivo extends InterpretacionAlgebraServicioArchivo

