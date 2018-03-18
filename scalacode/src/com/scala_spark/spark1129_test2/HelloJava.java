
public class HelloJava {
   public static void main(String[] args) {
	   System.out.println("Hello Java.");
       
	   Student s1 = new Student();
//	   People p1 = (People)s1;
//	   
//	   People p2 = new People();
   
//	   System.out.println(s1 instanceof People);
//	   
	   Object o1 = s1.getClass();
	   Object o2 = People.class;
//	   
	   if(o1 == o2){
		   System.out.println("它绝对是People对象");
	   }else{
		   System.out.println("它绝对不是People对象");
	   }
	   
//	   System.out.println((Object)People.class == p1.getClass());
   }
   
   static void func(int x, int y){
	   System.out.println(x + y);
   }
}

class People{
	People(){
		
	}
	
	People(String name, int id){
		this.name = name;
		this.id = id;
	}
	String name;
	int id;
}


class Student extends People{
	String school;
	
	static String guoji = "中国";
}
