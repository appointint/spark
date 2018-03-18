package com.scala_spark1213

/**
  * Created by hp on 2017/11/29.
  */
object Construct {

  def main(args: Array[String]): Unit = {

    var m=new Man("zhu");
  //会报错
    //    m.apply("zhu")

    m.apply("zhu",34)
    m("zhu",45)

//使用半生对象不用添加new关键字，调用的参数要参考apply方法
    var m2=Man("zhu",89)
    
//    var x=Man(22);

  }

}
//主构造器
//
class  Man(var name:String){

  var id:Int=0;
  var age:Int=0;
  //不会报错
  println("********")

  def this(name:String,id:Int){
    //辅助构造器方法中第一行必须调用其他构造器
    this(name)
    this.id=id;
  }
  def this(name:String,id:Int,age:Int){
    this(name,id);
    this.age=age;
  }

//  def apply(name:String,id:Int): Unit = {
//    println("Unit")
//  }

  //也可以返回list
//  def apply(  name: String,id:Int): List[String] = List("ds","werfew")

  def apply(name:String,id:Int):Man= new Man(name,id)

}
object  Man{
  def apply(name:String,id:Int): Man = {
    new Man(name,id)
//    println("object Man")
  }
}
