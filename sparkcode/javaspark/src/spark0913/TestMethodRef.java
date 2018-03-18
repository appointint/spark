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

    //1)����::ʵ��������
    private static void test1(){
        //PrintStream ps1 = System.out;
    	//Consumer<String> con = (x)->System.out.println(x);  //������һ��ʵ����Consumer�ӿڵ���Ķ���accept()�����������ͽӿڣ�һ��������û�з���ֵ
        Consumer<String> con = System.out::println;
        con.accept("Hello.....");
        
        //ʵ��out������PrintStream��        
//        PrintStream ps = System.out;
//        Consumer<String> con1 = ps::println;       //�൱�����棬������ps�����println()����
//        Consumer<String> con2 = System.out::println;
//        con2.accept("abcdef");
    }
    private static void test2(){
        final Employee emp = new Employee();
//        Supplier<String> sup = ()->new Employee().getName();			//�����ͽӿڣ�get()������û�в�����һ������ֵ
        Supplier<String> sup = emp::getName;
        String str = sup.get();
        System.out.println(str);
        
        Supplier<Integer> sup2 = emp::getAge;
        Integer num=sup2.get();
        System.out.println(num);
    }

    //2)��::��̬������
    private static void test3(){
    	//comparatorʵ�ֽӿڵķ�����compare�������ȽϽӿڡ�
    	
        //Comparator<Integer> com = (x,y)->Integer.compare(x,y);	
        Comparator<Integer> com1 = Integer::compare;
        System.out.println(com1.compare(33, 33));
    }

    //3)��::ʵ��������
    private static void test4(){
    	
    	//BiPredicate������������һ������ֵ
        //BiPredicate<String,String> bp = (x,y)->x.equals(y); //String::equals
        BiPredicate<String, String> bp2 = String::equals;
        System.out.println(bp2.test("Hell", "Hell"));
        
    }

    //����������
    public static void test5(){
        //Supplier<Employee> sup = ()->new Employee();
        //���������÷�ʽ
//        Supplier<Employee> sup2=Employee::new;//ʹ���޲ι�����
//        Employee emp = sup2.get();
//        System.out.println(emp);

    	//Function����һ��������һ������ֵ��
        //Function<Integer,Employee> fun2 = (x)->new Employee(x);
    	Function<Integer,Employee> fun2 = Employee::new;
        Employee emp2 = fun2.apply(101);
        System.out.println(emp2.toString());

        BiFunction<String,Integer,Employee> bf = (x, y) -> new Employee(x, y);
        //BiFunction<String,Integer,Employee> bf = Employee::new;
        Employee e = bf.apply("Tom", 22);
        System.out.println(e);
    }

    //��������
    private static void test6(){
    	//ʹ��Function���س���Ϊ��һ������������
        //Function<Integer,String[]> fun = (x)->new String[x];
        Function<Integer,String[]> fun = String[]::new;
        String[] strs = fun.apply(10);
        System.out.println(strs.length);

        Function<Integer,String[]> fun2 = String[]::new;
        String[] str2 = fun2.apply(20);
        System.out.println(str2.length);
    }
}

