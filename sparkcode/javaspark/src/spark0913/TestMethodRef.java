package spark0913;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestMethodRef { 

	public static void main(String[] args){
		test4();
	}

    //1)对象::实例方法名
    private static void test1(){
        //PrintStream ps1 = System.out;
    	//Consumer<String> con = (x)->System.out.println(x);  //生成了一个实现了Consumer接口的类的对象，accept()方法，消费型接口，一个参数，没有返回值
        Consumer<String> con = System.out::println;
        con.accept("Hello.....");
        
        //实现out的类是PrintStream类        
//        PrintStream ps = System.out;
//        Consumer<String> con1 = ps::println;       //相当于上面，引用了ps对象的println()方法
//        Consumer<String> con2 = System.out::println;
//        con2.accept("abcdef");
    }
    private static void test2(){
        final Employee emp = new Employee();
//        Supplier<String> sup = ()->new Employee().getName();			//供给型接口，get()方法，没有参数，一个返回值
        Supplier<String> sup = emp::getName;
        String str = sup.get();
        System.out.println(str);
        
        Supplier<Integer> sup2 = emp::getAge;
        Integer num=sup2.get();
        System.out.println(num);
    }

    //2)类::静态方法名
    private static void test3(){
    	//comparator实现接口的方法是compare方法。比较接口。
    	
        //Comparator<Integer> com = (x,y)->Integer.compare(x,y);	
        Comparator<Integer> com1 = Integer::compare;
        System.out.println(com1.compare(33, 33));
    }

    //3)类::实例方法名
    private static void test4(){
    	
    	//BiPredicate待两个参数，一个返回值
        //BiPredicate<String,String> bp = (x,y)->x.equals(y); //String::equals
        BiPredicate<String, String> bp2 = String::equals;
        System.out.println(bp2.test("Hell", "Hell"));
        
    }

    //构造器引用
    public static void test5(){
        //Supplier<Employee> sup = ()->new Employee();
        //构造器引用方式
//        Supplier<Employee> sup2=Employee::new;//使用无参构造器
//        Employee emp = sup2.get();
//        System.out.println(emp);

    	//Function含有一个参数和一个返回值，
        //Function<Integer,Employee> fun2 = (x)->new Employee(x);
    	Function<Integer,Employee> fun2 = Employee::new;
        Employee emp2 = fun2.apply(101);
        System.out.println(emp2.toString());

        BiFunction<String,Integer,Employee> bf = (x, y) -> new Employee(x, y);
        //BiFunction<String,Integer,Employee> bf = Employee::new;
        Employee e = bf.apply("Tom", 22);
        System.out.println(e);
    }

    //数组引用
    private static void test6(){
    	//使用Function返回长度为第一个参数的数组
        //Function<Integer,String[]> fun = (x)->new String[x];
        Function<Integer,String[]> fun = String[]::new;
        String[] strs = fun.apply(10);
        System.out.println(strs.length);

        Function<Integer,String[]> fun2 = String[]::new;
        String[] str2 = fun2.apply(20);
        System.out.println(str2.length);
    }
}

