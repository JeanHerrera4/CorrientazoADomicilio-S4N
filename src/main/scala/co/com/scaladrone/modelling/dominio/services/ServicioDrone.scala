package co.com.scaladrone.modelling.dominio.services

import co.com.scaladrone.modelling.dominio.entities._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

// Algebra del API


sealed trait AlgebraServicioDrone {
  def girarIzquierda(estadoDrone: EstadoDrone): EstadoDrone
  def girarDerecha(estadoDrone: EstadoDrone): EstadoDrone
  def avanzar(estadoDrone: EstadoDrone): EstadoDrone
  def mover(instruccion: Instruccion, estadoDrone: EstadoDrone): EstadoDrone
  def charToInstruccion(s: String): List[Instruccion]
  //def entrega(listaInstrucciones: List[Instruccion]) :
  def leerArchivo(): List[String]
}

  sealed trait InterpretacionAlgebraServicioDrone extends AlgebraServicioDrone{

    override def girarDerecha(estadoDrone: EstadoDrone): EstadoDrone = {
      val x = estadoDrone.orientacion match{
        case N() => E()
        case S() => O()
        case E() => S()
        case O() => N()
      }
      val res = EstadoDrone(estadoDrone.coordenada,x)
      //println(res)
      res
    }

    override def girarIzquierda(estadoDrone: EstadoDrone): EstadoDrone = {
      val x = estadoDrone.orientacion match {
        case N() => O()
        case S() => E()
        case E() => N()
        case O() => S()
      }
      val res = EstadoDrone(estadoDrone.coordenada,x)
      //println(res)
      res
    }

    override def avanzar(estadoDrone: EstadoDrone): EstadoDrone = {
      val x = estadoDrone.coordenada.x
      val y = estadoDrone.coordenada.y

      val xy = estadoDrone.orientacion match {
        case N() => Coordenada(x,y+1)
        case S() => Coordenada(x, y-1)
        case E() => Coordenada(x+1, y)
        case O() => Coordenada(x-1, y)
      }
      val res = EstadoDrone(xy, estadoDrone.orientacion)
      //println(res)
      res
    }

    override def mover(instruccion: Instruccion, estadoDrone: EstadoDrone): EstadoDrone = {

      instruccion match {
        case A() => avanzar(estadoDrone)
        case D() => girarDerecha(estadoDrone)
        case I() => girarIzquierda(estadoDrone)
        case _ => throw new Exception(s"Caracter invalido para creacion de instruccion")
      }
    }

    override def charToInstruccion(s: String): List[Instruccion] = {
      val caracteresIntruccion = s.toList
      caracteresIntruccion.map(x => Instruccion.newInstruccion(x))
    }

    override def leerArchivo(): List[String] = {
      val fileStream = getClass.getResourceAsStream("/in/in01.txt")
      val lineas: List[String] = Source.fromInputStream(fileStream).getLines.toList
      println(lineas)
      lineas
    }

    //override def entrega(listaInstrucciones: List[Instruccion]): Unit = ???

}

// Trait Object
object InterpretacionAlgebraServicioDrone extends InterpretacionAlgebraServicioDrone



//Interpretacion del algebra
/*sealed trait InterpretacionServicioDrone extends AlgebraServicioDrone{

  override def tarifar(poliza:Poliza):Future[Tarifa] = {
    Future.successful(Tarifa(1000))
  }

  override def consultarPoliza(numero:String):Future[Poliza] = {
    Future(Poliza(numero, List(Cobertura("1", "1.1"))))
  }

  override def adicionarCobertura(cobertura:Cobertura, poliza:Poliza): Future[Poliza] = {
    Future{
      Poliza(poliza.numero, cobertura::poliza.coberturas)
    }
  }

  override def crearCobertura(cdgarantia: String, cdsubgarantia:String): Future[Cobertura] = Future{
    Cobertura(cdgarantia, cdsubgarantia)
  }
}

// Trait Object
object InterpretacionServicioDrone extends InterpretacionServicioDrone
*/