package co.com.scaladrone.modelling.dominio.servicios

import co.com.scaladrone.modelling.dominio.entidades.{Cobertura, Poliza, Tarifa}

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

// Algebra del API
sealed trait AlgebraServicioPoliza {
  def tarifar(poliza:Poliza):Future[Tarifa]
  def consultarPoliza(numero:String):Future[Poliza]
  def adicionarCobertura(cobertura:Cobertura, poliza:Poliza): Future[Poliza]
  def crearCobertura(cdgarantia: String, cdsubgarantia:String): Future[Cobertura]
}

//Interpretacion del algebra
sealed trait InterpretacionServicioPoliza extends AlgebraServicioPoliza{

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
object InterpretacionServicioPoliza extends InterpretacionServicioPoliza