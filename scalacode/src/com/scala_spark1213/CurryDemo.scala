class CurryDemo {
  //定义高阶函数
	def func2(op:(Int,Int) => Int, a:Int, b:Int):Int={
	  op.apply(a,b)
	}
  def func1(op:Function2[Int,Int,Int], a:Int, b:Int):Int = op(a,b)

 // println(func1((x,y)=>x*y, 3, 4))
  
  
  def func3(op:(Int, Int) => Int, a:Int, b:Int):(Int,Int)=>Int ={
    op
  }
  
  val f9 = func3((x,y)=>x*y,4,6)
  //println(f.toString())
  //println(f9(4,9))

	//调用高阶函数
	val f = func1(add,1,2)
	//println(f(6, 9))
	
	//调用函数
	def add(a:Int,b:Int) = a + b
	
	//科里化----------逻辑数学----- 这个世界基本的组成元素就是 a=>a
	def plusBy1(factor:Int) = (x:Int) => x + factor
	def plusBy(factor:Int) = {
	   (x:Int) => x + factor
	}
	
	val f1 = plusBy(3)
	println(f1(5))
	
	val r1 = plusBy(3)(5)
	println(r1)
//	
  def add2(a:Int) = (b:Int) => a + b
  println(add2(3)(4))

}

object CurryDemo{
  def main(args: Array[String]): Unit = {
    new CurryDemo
  }
}