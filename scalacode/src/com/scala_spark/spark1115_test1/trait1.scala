package com

object trait1 {
  def main(args: Array[String]): Unit = {
    val num=new TestClass
    println(num.func1)
    println(num.func2(2,3))
    
    var num2=new Test(){
      
      def func2(x:Int,y:Int)={
        x+y
      }
    }
    
  }

}

trait Test{
  def func1
  def func2(x:Int,y:Int)= 5
  def func3()=2
}

class TestClass extends Test{
  def func1=print("TestClass func1")

  override def func2(x: Int, y: Int): Int = x+y
}