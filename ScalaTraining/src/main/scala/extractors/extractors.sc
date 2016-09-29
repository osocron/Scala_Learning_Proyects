/**
  * Interesting to say, the case class has a companion object that implements
  * the unapply method which is in charge of decomposing or extracting the
  * parameters of a given class. This is useful for pattern matching.
  * The Option and Some classes are used, these are used for implementing monads!
  */

trait User {
  def name: String
  def score: Int
}

class FreeUser(val name: String, val score: Int) extends User
object FreeUser {
  def unapply(user: FreeUser): Option[(String, Int)] = Some((user.name, user.score))
}

class PremiumUser(val name:String, val score: Int, val upgradeProbability: Double)
  extends User
object PremiumUser {
  def unapply(user: PremiumUser): Option[(String, Int, Double)] =
    Some((user.name, user.score, user.upgradeProbability))
}

val user: User = new PremiumUser("Jose", 12, 0.12)
user match {
  case FreeUser(name, _ ) => "Hello" + name
  case PremiumUser(name, _ , p) => "Welcome back, master chief " + name
}

val xs = 58 #:: 23 #:: 12 #:: Stream.empty

xs match {
  case first #:: second #:: _ => first - second
  case _ => -1
}

val xs1 = 3 :: 6 :: 12 :: Nil
xs1 match {
  case List(a, b) => a * b
  case List(a, b, c) => a + b + c
  case _ => 0
}

val xs3 = 3 :: 6 :: 12 :: 24 :: Nil
xs3 match {
  case List(a, b, _*) => a * b
  case _ => 0
}

object GivenNames {
  def unapplySeq(name: String): Option[Seq[String]] = {
    val names = name.trim.split(" ")
    if (names.forall(_.isEmpty)) None else Some(names)
  }
}

def greetWithFirstName(name: String) = name match {
  case GivenNames(firstName, _*) => "Good morning, " + firstName + "!"
  case _ => "Welcome! Please make sure to fill in your name!"
}

greetWithFirstName("Daniel")

greetWithFirstName("Katheryn Michael's")

greetWithFirstName("")

object Names {
  def unapplySeq(name: String): Option[(String, String, Array[String])] = {
    val names = name.trim.split(" ")
    if (names.size < 2) None
    else Some((names.last, names.head, names.drop(1).dropRight(1)))
  }
}

def greet(fullName: String) = fullName match {
  case Names(lastName, firstName, _*) => "Good morning, " + firstName + " " + lastName + "!"
  case _ => "Welcome! Please make sure to fill in your name!"
}

greet("Noe Alejandro Perez")

greet("Johannes")

//More pattern matching

def gameResult(): (String, Int) = ("Daniel", 3500)

val (name, score) = gameResult()
println(name + ": " + score)

case class Player(name: String, score: Int)

def currentPlayer(): Player = Player("Daniel", 3500)

val Player(names, _) = currentPlayer()

def gameResults(): Seq[(String, Int)] =
  ("Daniel", 3500) :: ("Melissa", 13000) :: ("John", 7000) :: Nil

def hallOfFame = for {
  (name, score) <- gameResults()
  if score > 5000
} yield name

val lists = List(1, 2, 3) :: List.empty :: List(5, 3) :: Nil

for {
  list @ head :: _ <- lists
} yield list.size

/**
  * Thankfully, Scala provides an alternative way of writing anonymous functions:
  * A pattern matching anonymous function is an anonymous function that is
  * defined as a block consisting of a sequence of cases, surrounded as usual
  * by curly braces, but without a match keyword before the block.
  * Let’s rewrite our function, making use of this notation:
  */

val wordFrequencies = ("habitual", 6) :: ("and", 56) :: ("consuetudinary", 2) ::
  ("additionally", 27) :: ("homely", 5) :: ("society", 13) :: Nil

def wordsWithoutOutliers(wordFrequencies: Seq[(String, Int)]): Seq[String] =
  wordFrequencies.collect { case (word, freq) if freq > 3 && freq < 25 => word }

wordsWithoutOutliers(wordFrequencies)

/**
  *So what is a partial function? In short, it’s a unary function that is known to be
  * defined only for certain input values and that allows clients to check whether it
  * is defined for a specific input value.
  */


