class Car{
  class Engine{
    def fire = println("fire!!!")
  }
  def run() = {
    var engine = new Engine();
    engine.fire
    println("run.....")
  }

}
