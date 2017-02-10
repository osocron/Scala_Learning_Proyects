package typelevel

/**
  * Created by osocron on 26/01/17.
  */
sealed trait Nat {
  type Plus[That <: Nat] <: Nat
}

sealed trait Nat0 extends Nat {
  override type Plus[That <: Nat] = That
}

sealed trait NatN[Prev <: Nat] extends Nat {
  override type Plus[That <: Nat] = NatN[Prev#Plus[That]]
}