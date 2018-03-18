package com.scala_spark.spark1115_test1

object LambdaTest {
  
  def main(args: Array[String]): Unit = {
    
    val Fum=(x,y)=>x+y;
    
    println(Fum(3,4))
    
  val sum = new Function2[Int,Int,Int](){
       def apply(x:Int, y:Int):Int = {
         x+y
       }
     }
    println(sum(5,6))
    
  }
  
  
  
  
}