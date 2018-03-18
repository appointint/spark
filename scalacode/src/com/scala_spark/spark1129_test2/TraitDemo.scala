

object TraitDemo {
  def main(args: Array[String]): Unit = {
    val t = new TestTClass
    t.func1
    println(t.func2())
  }
}

trait TestT{
  def func1
  def func2():Int=2
  def func3(x:Int, y:Int) = x * y
}

class TestTClass extends TestT{
  override def func1 = println("TestClass func1 ")
  override def func2():Int = 5
}