package co.com.scaladrone

import java.util.concurrent.Executors

import co.com.scaladrone.modelling.dominio.entidades._
import co.com.scaladrone.modelling.dominio.servicios.{InterpretacionAlgebraServicioArchivo, InterpretacionAlgebraServicioDrone}
import org.scalatest.FunSuite

import scala.concurrent.{Await, ExecutionContext, Future, duration}
import scala.util.Success

class SuiteExample extends FunSuite {

  test("Drone gira a la derecha"){
    val drone = Drone(1,EstadoDrone(Coordenada(0,0),N()),10)
    val a: Drone = InterpretacionAlgebraServicioDrone.girarDerecha(drone)

    assert(a == Drone(1,EstadoDrone(Coordenada(0,0),E()),10))
  }

  test("Drone gira a la izquierda"){
    val drone = Drone(1,EstadoDrone(Coordenada(0,0),N()),10)
    val a: Drone = InterpretacionAlgebraServicioDrone.girarIzquierda(drone)

    assert(a == Drone(1,EstadoDrone(Coordenada(0,0),O()),10))
  }

  test("Drone avanza"){
    val drone = Drone(1,EstadoDrone(Coordenada(0,0),N()),10)
    val a: Drone = InterpretacionAlgebraServicioDrone.avanzar(drone)

    assert(a == Drone(1,EstadoDrone(Coordenada(0,1),N()),10))
  }

  test("Drone se mueve") {

    val drone = Drone(1,EstadoDrone(Coordenada(1, 1), N()),10)
    val a: Drone = InterpretacionAlgebraServicioDrone.mover(A(), drone)

    assert(a == Drone(1,EstadoDrone(Coordenada(1, 2), N()),10))
  }

  test("Drone realiza una entrega"){
    val drone = Drone(1,EstadoDrone(Coordenada(0, 0), N()),10)
    val entrega = Entrega(List(A(), A(), A(), A(), I(), A(), A(), D()))

    val a: Drone = InterpretacionAlgebraServicioDrone.realizarEntrega(entrega, drone)

    assert(a == Drone(1,EstadoDrone(Coordenada(-2, 4), N()),10))
  }

  /*test("Drone recorre una ruta realizando entregas"){
    val drone = Drone(1,EstadoDrone(Coordenada(0, 0), N()),10)
    val ruta = Ruta(List(
                  Entrega(List(A(), A(), A(), A(), I(), A(), A(), D())),
                  Entrega(List(D(), D(), A(), I(), A(), D())),
                  Entrega(List(A(), A(), I(), A(), D(), A(), D()))))


    val a = InterpretacionAlgebraServicioDrone.realizarEntregas(ruta, drone)

    assert(a == List(
                    Drone(1,EstadoDrone(Coordenada(-2, 4), N()),10),
                    Drone(1,EstadoDrone(Coordenada(-1, 3), S()),10),
                    Drone(1,EstadoDrone(Coordenada(0, 0), O()),10)
    ))
  }*/

  test("Drone recorre una lista de rutas realizando entregas"){

    val drone = Drone(1,EstadoDrone(Coordenada(0, 0), N()),10)
    val listaRutas = Periplo(List(
      Ruta(List(Entrega(List(A(), A(), A(), A(), I(), A(), A(), D())),
                Entrega(List(D(), D(), A(), I(), A(), D())),
                Entrega(List(A(), A(), I(), A(), D(), A(), D()))))))
  }

  println("----------------------------------------------------------")

  test("Leer archivo"){
    val url = "src/main/resources/in/in01.txt"
    val instrucciones = InterpretacionAlgebraServicioArchivo.leerArchivo(url)

    assert(instrucciones == List("AAAAIAAD", "DDAIAD", "AAIADAD", "AAADAIA", "DAAIAIAA", "AAIDIADDA", "AAADIDDA", "DAADAIIA", "IIIIDADDAA", "AADAAIAA"))
  }

  test("Convertir un caracter a una Instruccion"){
    val listaString = "AAIADAID"
    val instrucciones = InterpretacionAlgebraServicioArchivo.caracterAInstruccion(listaString)

    assert(instrucciones == Entrega(List(A(), A(), I(), A(), D(), A(), I(), D())))
  }

  test("Convertir archivo en Ruta (Lista de Entregas)"){
    val url = "src/main/resources/in/in01.txt"
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

  test("Realizar pedidos: Lista de archivos y realiza entregas de cada uno"){

    val rutas = List("src/main/resources/in/in01.txt", "src/main/resources/in/in02.txt")
    val hola = InterpretacionAlgebraServicioDrone.enviarDomicilios(rutas)
  }

  test("Prueba impresiones bien mi amor"){
  }

  /*test("El gran test con dos achivos"){
    implicit val executionC = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(20))

    val d1 = Drone(1, EstadoDrone(Coordenada(0,0),N()),10)
    val r1 = Ruta(List(Entrega(List(A(),A(),A(),A()))))

    val d2 = Drone(2,EstadoDrone(Coordenada(0,0),N()),10)
    val r2 = Ruta(List(Entrega(List(A(),A()))))

    val listaInput = List((d1,r1),(d2,r2))
    val res: List[Future[List[Drone]]] = listaInput.map(t => InterpretacionAlgebraServicioDrone.moverDronesAlTiempo(t._1,t._2))

    val res2: Future[List[List[Drone]]] = Future.sequence(res)

    val res3: List[List[Drone]] = Await.result(res2, 10 seconds)

    assert(res3.size == 2)
    assert(res3.head.size == 1)
    assert(res3.head.head.coord == Coord(0,4))

    assert(res3.tail.size == 1)
    assert(res3.tail.head.head.coord == Coord(0,2))


  }*/


}