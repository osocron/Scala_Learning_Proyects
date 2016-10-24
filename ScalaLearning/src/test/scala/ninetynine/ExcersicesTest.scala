package ninetynine

import org.scalatest.FunSuite

/**
  * Created by osocron on 7/30/16.
  */
class ExcersicesTest extends FunSuite {

  val claseEjercicios = new Excersices

  test("regresaUltimo debe regresar 8 dado List(1,2,3,4,5,6,7,8)") {
    assert(claseEjercicios.regresaUltimo(List(1,2,3,4,5,6,7,8)) == 8)
  }

  test("regresaUltimo debe regresar 1 dado List(1)") {
    assert(claseEjercicios.regresaUltimo(List(1)) == 1)
  }

  test("regresaUltimo debe mandar error cuando el parametro sea List()"){
    intercept[IllegalArgumentException] {
      claseEjercicios.regresaUltimo(List())
    }
  }

}
