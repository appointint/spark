//偏函数 ------- Partial Function

object PartialFunctionDemo2 {
  def main(args: Array[String]): Unit = {
    val signal:PartialFunction[Int,Int] = {
      case x if x > 0 => 1
      case x if x < 0 => -1
    }
    if(signal.isDefinedAt(0) == true){
       println(signal(0))
    }
    
    val compose_signal:PartialFunction[Int,Int] = signal.orElse{
      case 0 => 0
    }
    
    println(compose_signal(6))
    //println(signal(0))
    
    val new_signal:Function[Int,Int] = signal.compose{
      case x => x - 1
    }
    println(signal(4))
    println(new_signal(4))
    
    
  }
}