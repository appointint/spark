package spark0918_stream;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class StreamConert2 {
    static List<Employee> employees=Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 58, 5555.55),
            new Employee("王五", 26, 3333.33),
            new Employee("赵六", 36, 6666.66),
            new Employee("田七", 12, 8888.88),
            new Employee("田七", 12, 8888.88)
            );
	
	public static void main(String[] a){
		test7();
	}

	//中间操作
	/*
	 * 映射
	 * map--接收Lambda，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新元素。
	 * flatMap--接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */
	private static void test5(){
	    List<String> list = Arrays.asList("aaa","bbb","ccc","ddd");
	    list.stream()
	         //.map((str)->str.toUpperCase())
	         .map(String::toUpperCase)
	         .forEach(System.out::println);
	    System.out.println("------------------------");

	    employees.stream()
	             //.map(x -> x.getName())
	             .map(Employee::getName)
	             .forEach(System.out::println);
	    System.out.println("------------------------");

//	    Stream<Stream<Character>> stream = list.stream().map(StreamConert2::filterChatacter);
//	    stream.forEach((sm)->{
//	        sm.forEach(System.out::println);
//	        //System.out.println();
//	    });
//
//	    System.out.println("------------------------");
//
//	    Stream<Character> sm=list.stream().flatMap(x->filterChatacter(x));
//	    sm.forEach(System.out::println);
	}
	
	private static Stream<Character> filterChatacter(String str){
	    List<Character> list = new ArrayList<>();
	    for (Character ch : str.toCharArray()) {
	        list.add(ch);
	    }
	    return list.stream();
	}

	private static void test6(){//map和flatMap的关系  类似于 add(Object)和addAll(Collection coll)
	    List<String> list=Arrays.asList("aaa","bbb","ccc","ddd");
	    List list2 = new ArrayList<>();
	    list2.add(11);
	    list2.add(22);
	    list2.addAll(list);
	    System.out.println(list2);
	}
	
	//中间操作
    /*
     * 排序
     * sorted()-自然排序（按照对象类实现Comparable接口的compareTo()方法 排序）
     * sorted(Comparator com)-定制排序（Comparator）
     */
    private static void test7(){
        List<String> list = Arrays.asList("ccc","bbb","eee","aaa");
        list.stream()
            .sorted((x1, x2) -> x2.compareTo(x1))
            //.sorted(String::compareTo)
            .forEach(System.out::println);

        System.out.println("------------------------");

        employees.stream()
                 .sorted((e1,e2)->{
                     if(e1.getAge() == (e2.getAge())){
                         return e1.getName().compareTo(e2.getName());
                     }else{
//                    	 System.out.println(e1.getAge() - e2.getAge());
//                    	 System.out.println(e1.getName());
                         return e1.getAge() - e2.getAge();
                     }
                 }).forEach(System.out::println); 
    }
}
