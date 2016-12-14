package CatTheory

import CatTheory.Categories._
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import cats.implicits._

/**
  * Created by osocron on 13/12/16.
  */
object ComposotionProof  extends Properties("Composition") {

  /**
    * Given an algebra A, B, C and D
    * where A = String
    *       B = Int
    *       C = Double
    *       D = Boolean
    *
    * And functions f, g, and h
    *
    * We can prove some properties of function composition.
    */
  type A = String
  type B = Int
  type C = Double
  type D = Boolean

  property("function composition") = forAll {
    (f: A => B, g: B => C, a: A) => compose(f, g)(a) == g(f(a))
  }

  //This is just awesome. Proving map in coproducts is just cool
  property("fun with Coproducts") = forAll {
    (la: List[A], f: A => B) => Coproduct(Right(la)).map(f).run match {
      case Right(v) => v.sum == la.map(f).sum
    }
  }

}
