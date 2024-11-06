@main def twoContainers(): Unit =
  println("Container with most water!")
//  val vector = Vector(1,8,6,2,5,4,8,3,7)
//  println("Result: " + calulcate(vector))

  val vector2 = Array(2,3,4,5,18,17,6)
  println("Result: " + calculateOptimized(vector2))


def calulcate(vector: Vector[Int]) =
  def c(vector: Vector[Int], first: Int, second:Int, max: Int): Any =
    if(first >= second) return max
    val v1 = vector(first)
    val v2 = vector(second)
    val m2 = calculateM2(Math.min(v1, v2), second - first)
    val newMax = Math.max(max, m2)
    if (v1 < v2) c(vector.drop(1), first, second - 1, newMax) else c(vector.take(second), first, second - 1, newMax)

  c(vector, 0, vector.size - 1, 0)

def calculateM2(height: Int, distance: Int) =
  height * distance


def calculateOptimized(vector: Array[Int]): Int = {
  @annotation.tailrec
  def calculate(left: Int, right: Int, maxArea: Int): Int = {
    if (left >= right) maxArea
    else {
      val height = Math.min(vector(left), vector(right))
      val width = right - left
      val area = height * width

      if (vector(left) < vector(right))
        calculate(left + 1, right, Math.max(maxArea, area))
      else
        calculate(left, right - 1, Math.max(maxArea, area))
    }
  }

  if (vector.isEmpty) 0
  else calculate(0, vector.size - 1, 0)
}


def calculateArea(height: Int, distance: Int): Int = height * distance
