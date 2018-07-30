package co.com.scaladrone.modelling.dominio.entities

trait Instruccion

object Instruccion {
  def newInstruccion(c:Char):Instruccion = {
    c.toUpper match {
      case 'A' => A()
      case 'D' => D()
      case 'I' => I()
      case _ => throw new Exception(s"Caracter invalido para creacion de instruccion: $c")
    }
  }
}

case class A() extends Instruccion
case class I() extends Instruccion
case class D() extends Instruccion


trait Orientacion

object Orientacion {
  def newOrientacion(c:Char): Orientacion ={
    c.toUpper match{
      case 'N' => N()
      case 'S' => S()
      case 'E' => E()
      case 'O' => O()
      case _ => throw new  Exception(s"Caracter invalido para segumiento de orientacion: $c")
    }
  }
}

case class N() extends Orientacion
case class S() extends Orientacion
case class E() extends Orientacion
case class O() extends Orientacion

case class Coordenada (x: Int, y: Int)

trait UbicacionDrone{
  val orientacion: Orientacion
  val coordenada: Coordenada
}

case class EstadoDrone(coordenada: Coordenada, orientacion: Orientacion) extends UbicacionDrone

