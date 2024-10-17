@main def day2(): Unit =
  println("Hello day 2")
  println("Check my need")
  val list = List(1,2,3,4)
  val needs = iNeedSomething(list)
  println(needs.map(_.show()))


// Given and using examples:
trait YourNeed[T]:
  def transform(item: T): T

given myLittleNeed: YourNeed[Int] = new YourNeed[Int]:
  override def transform(item: Int): Int =
    item * 2

def iNeedSomething[T](list: List[T])(using need: YourNeed[T]): List[T] =
  list.map(need.transform)

// extension
extension (i: Int)
  def show(): String = s"The number is $i.\n"

//enum
enum Color:
  case Red, Blue, Green


// transparent

// Regular inline method
inline def regularInline(x: Any): Any = x

// Transparent inline method
transparent inline def transparentInline(x: Any): Any = x

val a: Any = regularInline(42)        // a is of type Any
val b: Int = regularInline(42)        // Compilation error: Found: (a : Any), Required: Int

val c: Any = transparentInline(42)    // c is of type Any
val d: Int = transparentInline(42)    // d is of type Int, no error

// More complex example
transparent inline def typeOf[T](x: T): String =
  inline x match
    case _: Int => "Int"
    case _: String => "String"
    case _ => "Unknown"

val result1: String = typeOf(42)        // result1 is "Int"
val result2: String = typeOf("hello")   // result2 is "String"
val result3: String = typeOf(true)      // result3 is "Unknown"

// Using the result in a match expression
inline def processValue[T](x: T): String =
  typeOf(x) match
    case "Int" => "Processing an integer"
    case "String" => "Processing a string"
    case _ => "Processing an unknown type"

val processed1 = processValue(42)        // "Processing an integer"
val processed2 = processValue("hello")   // "Processing a string"
val processed3 = processValue(true)      // "Processing an unknown type"


