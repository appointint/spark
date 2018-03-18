package com.scala_spark.spark1115_test1

object Hello {
  def main(args: Array[String]): Unit = {
    var a= 8
    a=99
    println(a)
    var i : Int = 0
    var k = i .+(4)       //.+是调用的方法
    var j = i + 4         //‘+’实际上是调用的上面的方法

    new Range(1,5,1)       //new 对象能new Range有三个参数的，而只有两个参数的不能new

    println(Range(1,10))
    //折两个等效，to方法返回的是Range类型的。
    println(1 to 10)

    for(i <- 1 to 10){
      println(i)
    }
    for(i <- Range(1,10)){
      println(i)
    }

    func
    println(func())     //没有返回值的返回（）,java中没有返回是System.out.println会报错，不能打印
    func()
    println(func3())
    
    func1(3,4)
    println(func2(5,6))
    
    var res = if(j<2) ("小于2") // else ()
    println(res)                //返回()，相当于else ()
    
  }
  def func3() ={
      println("func3")
  }

  def func(): Unit ={
    println("func")
  }
  def func1(x:Int,y:Int): Unit ={
      println(x+y)
  }
  def func2(x:Int,y:Int): Unit ={
    return x+y
//    x+y
  }

}