package chapter4

/**
  * Created by osocron on 3/18/16.
  * The factory principle states that object creation should be delegated to an object whose whole purpose is object creation
  * In this example we make use of this principle by creating an abstract PizzaStore class that has a createPizza method.
  * This method is responsible of creating Pizza objects.
  * There is a Pizza interface and Pizza objects that implement the Pizza interface.
  * The NYPizzaStore implements the PizzaStore interface and is responsible for implementing the factory principle.
  */
abstract class Pizza {
  val name: String
  val dough: String
  val sauce: String
  val toppings: List[String]
  def prepare() = {
    println("Preparing" + name)
    println("Tossing " + dough + " dough...")
    println("Adding " + sauce + " sauce...")
    println("Adding toppings: ")
    toppings.foreach(t => println(" " + t))
  }
  def bake() = println("Bake for 20 minutes at 350")
  def cut() = println("Cutting the pizza into diagonal slices!")
  def box() = println("Place pizza in official pizza store box!")
}

abstract class PizzaStore {
  def orderPizza(_type: String) = {
    val pizza = createPizza(_type)
    pizza.prepare()
    pizza.bake()
    pizza.cut()
    pizza.box()
    pizza
  }
  def createPizza(_type: String): Pizza
}

class NYPizzaStore extends PizzaStore {
  override def createPizza(_type: String): Pizza = _type match {
    case "cheese" => new NYStyleCheesePizza
    case "veggie" => new NYStyleVeggiePizza
    case "clam" => new NYStyleClamPizza
    case "pepperoni" => new NYStylePepperoniPizza
    case _ => sys.error("Wrong pizza type!")
  }
}

class NYStyleCheesePizza extends Pizza {
  override val name: String = "NY Style Cheese Pizza"
  override val sauce: String = "Marinera"
  override val toppings: List[String] = List("Mozzarella cheese","American cheese","Framaggio cheese")
  override val dough: String = "Thin"
}

class NYStyleVeggiePizza extends Pizza {
  override val name: String = "NY Style Veggie Pizza"
  override val sauce: String = "Tomato"
  override val toppings: List[String] = List("Tomato","Cabbage","Carrot","Red Pepper","Mushrooms","Cream Cheese")
  override val dough: String = "Thin"
}

class NYStyleClamPizza extends Pizza {
  override val name: String = "NY Style Clam Pizza"
  override val sauce: String = "Marinera"
  override val toppings: List[String] = List("Clam","Mozzarella cheese","Sausage")
  override val dough: String = "Thin"
}

class NYStylePepperoniPizza extends Pizza {
  override val name: String = "NY Style Pepperoni Pizza"
  override val sauce: String = "Special Tomato"
  override val toppings: List[String] = List("Pepperoni","Mozzarella cheese")
  override val dough: String = "Thin"
}

object Franchise {
  def main(args: Array[String]) {
    val pizzaStore = new NYPizzaStore
    pizzaStore.orderPizza("cheese")
    pizzaStore.orderPizza("pepperoni")
  }
}

