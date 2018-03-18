//偏应用函数  Partial applied Function---------- 科里化是偏应用函数的特殊形式

object PartialApplyFunctionDemo {
  def main(args: Array[String]): Unit = {
    def add(x:Int,y:Int,z:Int) = x+y+z

    def addX = add(1,_:Int,_:Int) // x 已知
    println(addX(2,3))

    def addXAndY = add(10,100,_:Int) // x 和 y 已知
    println(addXAndY(1))
    
    def addZ = add(_:Int,_:Int,10) // z 已知
    println(addZ(1,2))
    
    //def fadd = add(_:Int, _:Int, _:Int)
    def fadd = add _
    
    val f3= fadd(1,2,_:Int)
    println(f3(3))
  }
}