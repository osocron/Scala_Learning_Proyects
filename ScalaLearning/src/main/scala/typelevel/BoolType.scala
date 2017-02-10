package typelevel

/**
  * Created by osocron on 26/01/17.
  */
sealed trait BoolType {

  type Not <: BoolType

  type Or[That <: BoolType] <: BoolType

}

sealed trait TrueType extends BoolType {

  override type Not = FalseType

  override type Or[That <: BoolType] = TrueType

}

sealed trait FalseType extends BoolType {

  override type Not = TrueType

  override type Or[That <: BoolType] = That

}

object BoolType {
  type \/[A <: BoolType, B <: BoolType] = A#Or[B]
}

object BoolTypeSpecs {

  //Test for equelity
  implicitly[TrueType =:= TrueType]
  implicitly[FalseType =:= FalseType]

  //Test for the type level equivalent of functions using the # operator
  implicitly[TrueType#Not =:= FalseType]
  implicitly[FalseType#Not =:= TrueType]
  implicitly[TrueType#Or[TrueType] =:= TrueType]
  implicitly[TrueType#Or[FalseType] =:= TrueType]
  implicitly[FalseType#Or[TrueType] =:= TrueType]
  implicitly[FalseType#Or[FalseType] =:= FalseType]

}
