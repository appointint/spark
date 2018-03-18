//隐式转换调用类中本不存在的方法
class SwingType{
  def  wantLearned(sw : String) = println("兔子已经学会了"+sw)
}

object swimming{
  implicit def learningType(s : AminalType) = new SwingType
}

class AminalType{
}

import swimming.learningType

object ImplicitDemo {
  def main(args: Array[String]): Unit = {
    //隐式值：
     implicit val p1 = "mobin1"
     //implicit val p2 = "mobin2"
     def person(implicit name : String) = name
     println(person)
     
     //隐式视图    隐式转换为目标类型：把一种类型自动转换到另一种类型
     def foo(msg : String) = println(msg)
     implicit def intToString(x : Int) = x.toString
     foo(3)
     
     val rab = new AminalType
     rab.wantLearned("仰泳")
  }
}


