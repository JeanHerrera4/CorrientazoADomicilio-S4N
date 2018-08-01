package co.com.scaladrone

import co.com.scaladrone.modelling.dominio.entidades._
import co.com.scaladrone.modelling.dominio.servicios.InterpretacionAlgebraServicioDrone
import org.scalatest.FunSuite

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
}