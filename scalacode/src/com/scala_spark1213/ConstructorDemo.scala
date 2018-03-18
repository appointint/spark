object Hello{
	def main(args:Array[String]){
		//println("hello world")
		
		val m1 = new Man("Zhangsan")
		
		val list = List("aa", "bb")
		//调用的是伴生对象中的apply
		val m2 = Man("Wangwu")
		println("000000000000000000000");
		//调用的是class中的apply
		m2("liu", 33)
	}
}

//如果没有显示定义构造方法，编译器会分配一个默认的（不带参数）主构造器
//主构造器跟在类名后面
class Man(var name:String){
  //被主构造器调用  
  //println(name)

  var id:Int = 0
  var age:Int = 0
  
  //辅助构造器,第一行代码必须是对主构造器或者另外一个辅助构造器的调用
  def this(name:String, id:Int){
    this(name)
    this.id = id
  }
  
  def this(name:String, id:Int, age:Int){
    this(name,id)
    //this.id = id
    this.age = age
  }
  
  def apply(name:String, id:Int):Unit={
    println("apply*******")
  }
}

object Man{
  def apply(name:String):Man = new Man(name)
}
