object ObjectDemo {
   def main(args: Array[String]): Unit = {
     //println(sum(4,8))
     
     //println(Fsum(3,9))
     
     val sum = new Function2[Int,Int,Int](){
       def apply(x:Int, y:Int):Int = {
         x+y
       }
     }
     println(sum.apply(9, 8));
     
     val MyFun = new SumFunction
     println(MyFun.func(22, 11))
     
     val MyFun1 = new MyFunction2[Int, Int, Int]{
       def func(x:Int, y:Int):Int={
         x + y
       }
     }
     //println(MyFun1.func(3, 7))
     
//     testFunction1(Fsum);
     testFunction2((x, y) => x * y);
   }

   def testFunction1(f : MyFunction2[Int,Int,Int]):Unit = {
     val a = 33
     val b = 55
     println(f.func(a, b));
   }

    def testFunction2(f : Function2[Int,Int,Int]):Unit = {
     val a = 3
     val b = 9
     println(f.apply(a, b));
   }
   
   //方法
   def sum(x:Int, y:Int):Int={
     x + y
   }

   //函数-----是一个Lambda表达式，它背后有就一个函数式接口
   val Fsum = (x:Int, y:Int)=> x + y
}

trait MyFunction2[X, Y, Z]{
  def func(a:X, b:Y):Z 
}

class SumFunction extends MyFunction2[Int, Int, Int]{
   def func(x:Int, y:Int):Int={
     x + y
   }
}
