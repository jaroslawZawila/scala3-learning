// Simple Enum
enum Color:
  case Red, Green, Blue

// Enum with parameters
enum Direction(val degrees: Int):
  case North extends Direction(0)
  case East extends Direction(90)
  case South extends Direction(180)
  case West extends Direction(270)

// Enum with methods
enum DayOfWeek:
  case Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday

  def isWeekend: Boolean = this match
    case Saturday | Sunday => true
    case _ => false

// Parameterized enum (with type parameter)
enum Option[+T]:
  case Some(value: T)
  case None

// ADT (sum type) representing shapes
enum Shape:
  case Circle(radius: Double)
  case Rectangle(width: Double, height: Double)
  case Triangle(base: Double, height: Double)

  def area: Double = this match
    case Circle(r) => math.Pi * r * r
    case Rectangle(w, h) => w * h
    case Triangle(b, h) => 0.5 * b * h

// Usage examples
@main def examples =
  // Using simple enum
  val color = Color.Red
  println(s"Color: $color")

  // Using enum with parameters
  val direction = Direction.North
  println(s"Direction degrees: ${direction.degrees}")

  // Using enum with methods
  val day = DayOfWeek.Saturday
  println(s"Is $day weekend? ${day.isWeekend}")

  // Using parameterized enum
  val someNumber = Option.Some(42)
  val noValue = Option.None

  // Using ADT
  val circle = Shape.Circle(5.0)
  val rectangle = Shape.Rectangle(4.0, 3.0)

  println(s"Circle area: ${circle.area}")
  println(s"Rectangle area: ${rectangle.area}")

  // Pattern matching on ADT
  def describe(shape: Shape): String = shape match
    case Shape.Circle(r) => s"A circle with radius $r"
    case Shape.Rectangle(w, h) => s"A rectangle ${w}x$h"
    case Shape.Triangle(b, h) => s"A triangle with base $b and height $h"

  println(describe(circle))