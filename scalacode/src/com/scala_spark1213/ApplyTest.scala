object ApplyTest {
  def main(args: Array[String]): Unit = {
//    val anyObject = new SomeClass
//    println(anyObject("key1"))
    
    val ss = SomeClass("aa")
    println(ss)
    
//    val email = EMail("fantasia", "sina.com")
//    println(email)
//    val email2 = new Email
//    email2("Test")
  }
}

class SomeClass {
    println("SomeClass ........");
    def apply(key: String): String = {
        println("SomeClass apply()" + key)
        key
    }
}

object SomeClass{
  def apply(a:String){
    println("SomClass object apply()" + a)
  }
}

class Email{
  def apply(x:String){
    println("Email apply");
    println(x);
  }
}

object EMail {
    def apply(user: String, domain: String): String = {
        println("Email Object apply()")
        user + "@" + domain
    }
}