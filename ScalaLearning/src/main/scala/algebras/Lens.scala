package algebras

/**
  * Created by osocron on 10/01/17.
  */
case class Lens[O, V](get: O => V,
                      set: (O, V) => O)

case class Address(no: String,
                   street: String,
                   city: String,
                   state: String,
                   zip: String)

case class Customer(id: Int, name: String, address: Address)

object LensTest extends App {

  val addressNoLens = Lens[Address, String] (
    get = _.no,
    set = (o, v) => o.copy(no = v)
  )

  val custAddressLens = Lens[Customer, Address] (
    get = _.address,
    set = (o, v) => o.copy(address = v)
  )

  def compose[Outer, Inner, Value](outer: Lens[Outer, Inner],
                                   inner: Lens[Inner, Value]): Lens[Outer, Value] = {
    Lens[Outer, Value](
      get = outer.get andThen inner.get,
      set = (o, v) => outer.set(o, inner.set(outer.get(o), v))
    )
  }

  val a = Address(no = "B-12", street = "Monroe Street",
    city = "Denver", state = "CO", zip = "80231")

  val c = Customer(12, "John D Cook", a)

  val custAddrNoLens = compose(custAddressLens, addressNoLens)

  println(custAddrNoLens.get(c))

  val c2 = custAddrNoLens.set(c, "B675")

  println(custAddrNoLens.get(c2))

}