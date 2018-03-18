import scala.beans.BeanProperty

object Getter_Setter {
  def main(args: Array[String]): Unit = {
    val p1 = new People
    p1.name_=("Wang")
    p1.name = "Zhang"
    println(p1.name)
    
    p1.setName("Hoow")
    println(p1.getName)
  }
}

class People{
   @BeanProperty var name : String = null
  
  //为方法设别名
//  def getName = name
//  def setName(x:String) = name_=(x:String)
}