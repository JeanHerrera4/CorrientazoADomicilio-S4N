package co.com.scaladrone

import co.com.scaladrone.modelling.dominio.entidades._
import co.com.scaladrone.modelling.dominio.servicios.{InterpretacionAlgebraServicioArchivo, InterpretacionAlgebraServicioDrone}
import org.scalatest.FunSuite

import scala.util.Success

class SuiteExample extends FunSuite {

  test("Drone gira a la derecha"){
    val drone = Drone(EstadoDrone(Coordenada(0,0),N()))
    val a: Drone = InterpretacionAlgebraServicioDrone.girarDerecha(drone)

    assert(a == Drone(EstadoDrone(Coordenada(0,0),E())))
  }

  test("Drone gira a la izquierda"){
    val drone = Drone(EstadoDrone(Coordenada(0,0),N()))
    val a: Drone = InterpretacionAlgebraServicioDrone.girarIzquierda(drone)

    assert(a == Drone(EstadoDrone(Coordenada(0,0),O())))
  }

  test("Drone avanza"){
    val drone = Drone(EstadoDrone(Coordenada(0,0),N()))
    val a: Drone = InterpretacionAlgebraServicioDrone.avanzar(drone)

    assert(a == Drone(EstadoDrone(Coordenada(0,1),N())))
  }

  test("Drone se mueve") {

    val drone = Drone(EstadoDrone(Coordenada(1, 1), N()))
    val a: Drone = InterpretacionAlgebraServicioDrone.mover(A(), drone)

    assert(a == Drone(EstadoDrone(Coordenada(1, 2), N())))
  }

  test("Drone realiza una entrega"){
    val drone = Drone(EstadoDrone(Coordenada(0, 0), N()))
    val entrega = Entrega(List(A(), A(), A(), A(), I(), A(), A(), D()))

    val a: Drone = InterpretacionAlgebraServicioDrone.realizarEntrega(entrega, drone)

    assert(a == Drone(EstadoDrone(Coordenada(-2, 4), N())))
  }

  test("Drone recorre una ruta realizando entregas"){
    val drone = Drone(EstadoDrone(Coordenada(0, 0), N()))
    val ruta = Ruta(List(
                  Entrega(List(A(), A(), A(), A(), I(), A(), A(), D())),
                  Entrega(List(D(), D(), A(), I(), A(), D())),
                  Entrega(List(A(), A(), I(), A(), D(), A(), D()))))


    val a: List[Drone] = InterpretacionAlgebraServicioDrone.realizarEntregas(ruta, drone)

    assert(a == List(Drone(EstadoDrone(Coordenada(0, 0), N())),
                    Drone(EstadoDrone(Coordenada(-2, 4), N())),
                    Drone(EstadoDrone(Coordenada(-1, 3), S())),
                    Drone(EstadoDrone(Coordenada(0, 0), O()))
    ))
  }

  test("Leer archivo"){
    val url = "/home/s4n/Documents/scala-drone/src/main/resources/in/in01.txt"
    val instrucciones = InterpretacionAlgebraServicioArchivo.leerArchivo(url)

    assert(instrucciones == List("AAAAIAAD", "DDAIAD", "AAIADAD", "AAADAIA", "DAAIAIAA", "AAIDIADDA", "AAADIDDA", "DAADAIIA", "IIIIDADDAA", "AADAAIAA"))
  }

  test("Convertir un caracter a una Instruccion"){
    val listaString = "AAIADAID"
    val instrucciones = InterpretacionAlgebraServicioArchivo.caracterAInstruccion(listaString)

    assert(instrucciones == Entrega(List(A(), A(), I(), A(), D(), A(), I(), D())))
  }

  test("Convertir archivo en Ruta (Lista de Entregas)"){
    val url = "/home/s4n/Documents/scala-drone/src/main/resources/in/in01.txt"
    val listaInstrucciones = InterpretacionAlgebraServicioArchivo
      .archivoAListaInstrucciones(InterpretacionAlgebraServicioArchivo.leerArchivo(url))

    assert(listaInstrucciones == Success(Ruta(
                                    List(
                                      Entrega(List(A(), A(), A(), A(), I(), A(), A(), D())),
                                      Entrega(List(D(), D(), A(), I(), A(), D())),
                                      Entrega(List(A(), A(), I(), A(), D(), A(), D())),
                                      Entrega(List(A(), A(), A(), D(), A(), I(), A())),
                                      Entrega(List(D(), A(), A(), I(), A(), I(), A(), A())),
                                      Entrega(List(A(), A(), I(), D(), I(), A(), D(), D(), A())),
                                      Entrega(List(A(), A(), A(), D(), I(), D(), D(), A())),
                                      Entrega(List(D(), A(), A(), D(), A(), I(), I(), A())),
                                      Entrega(List(I(), I(), I(), I(), D(), A(), D(), D(), A(), A())),
                                      Entrega(List(A(), A(), D(), A(), A(), I(), A(), A()))))))
  }
}