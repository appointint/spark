

class Swing_Type{
  def  wantLearned(sw : String) = println("兔子已经学会了"+sw)
}

package swimmingPage{
  object swimming{
    implicit def learningType(s : Aminal_Type) = new Swing_Type  //将转换函数定义在包中
  }
}

class Aminal_Type

object Aminal_Type extends  App{
  import swimmingPage.swimming._  //使用时显示的导入
  val rabbit = new Aminal_Type
  rabbit.wantLearned("breast stroke")         //蛙泳
}