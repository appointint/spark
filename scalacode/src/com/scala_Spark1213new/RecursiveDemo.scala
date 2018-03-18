import scala.annotation.tailrec

object RecursiveDemo2 {
  def main(args: Array[String]): Unit = {
    println(factorial_(40))
  }
  
  def factorial(n: BigInt): BigInt = {
    if (n <= 1)
      1
    else
      n * factorial(n-1)
  }
  
  def factorial_(n: Int): BigInt = {
     @tailrec
     def loop(acc: BigInt, n: Int): BigInt = {
       println("loop n:" + n + " acc:" + acc);
       if (n == 1)
         acc
       else 
         loop(n * acc, n - 1) 
     }
     loop(1, n)
  }
}