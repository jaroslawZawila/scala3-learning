// Example 1: Basic Opaque Type
object Temperature:
  // Define an opaque type alias
  opaque type Celsius = Double

  // Companion object methods to create instances
  object Celsius:
    def apply(value: Double): Celsius = value

  // Extension methods available everywhere
  extension (c: Celsius)
    def toCelsius: Double = c
    def toFahrenheit: Double = (c * 9/5) + 32

// Example 2: Multiple Opaque Types with Conversions
object Money:
  opaque type USD = BigDecimal
  opaque type EUR = BigDecimal

  object USD:
    def apply(amount: BigDecimal): USD = amount
    def unapply(usd: USD): Option[BigDecimal] = Option(usd)

  object EUR:
    def apply(amount: BigDecimal): EUR = amount
    def unapply(eur: EUR): Option[BigDecimal] = Option(eur)

  extension (usd: USD)
    def amount: BigDecimal = usd
    def toEUR(using rate: BigDecimal): EUR = EUR(usd * rate)

  extension (eur: EUR)
//    def amount: BigDecimal = eur
    def toUSD(using rate: BigDecimal): USD = USD(eur * rate)

// Example 3: Opaque Type with Validation
object Email:
  opaque type EmailAddress = String

  object EmailAddress:
    def apply(address: String): Either[String, EmailAddress] =
      if isValid(address) then Right(address)
      else Left("Invalid email format")

    private def isValid(email: String): Boolean =
      email.matches("""^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$""")

  extension (email: EmailAddress)
    def value: String = email
    def domain: String = email.split("@").last

// Example 4: Opaque Type with Type Parameter
object Collections:
  opaque type NonEmptyList[A] = List[A]

  object NonEmptyList:
    def apply[A](head: A, tail: A*): NonEmptyList[A] = (head +: tail).toList

    def fromList[A](list: List[A]): Option[NonEmptyList[A]] =
      list match
        case head :: tail => Some(apply(head, tail*))
        case Nil => None

  extension [A](nel: NonEmptyList[A])
    def toList: List[A] = nel
    def head: A = nel.head
    def tail: List[A] = nel.tail

// Example 5: Usage demonstration
@main def opaqueTypesDemo =
  import Temperature.*
  import Money.*
  import Email.*
  import Collections.*

  // Temperature example
  val temp = Celsius(25.0)
  println(s"Temperature in F: ${temp.toFahrenheit}")

  // Money example
  given eurToUsdRate: BigDecimal = 1.1
  val euros = EUR(100)
  val dollars = euros.toUSD
  println(s"€100 = ${dollars.amount}")

  // Email example
  val emailResult = EmailAddress("user@example.com")
  emailResult match
    case Right(email) => println(s"Domain: ${email.domain}")
    case Left(error) => println(s"Error: $error")

  // NonEmptyList example
  val nonEmptyList = NonEmptyList(1, 2, 3, 4)
  println(s"Head: ${nonEmptyList.head}")
  println(s"Tail: ${nonEmptyList.tail}")

// Type safety demonstrations
// These would not compile:
// val wrongTemp: Celsius = 25.0  // Error: Found Double, required Celsius
// val mixed = euros + dollars    // Error: Can't add EUR and USD directly
// val rawString: EmailAddress = "invalid" // Error: Can't create directly
