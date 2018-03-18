package spark0918_stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class StreamConvert1 {
    static List<Employee> employees=Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 58, 5555.55),
            new Employee("王五", 26, 3333.33),
            new Employee("赵六", 36, 6666.66),
            new Employee("田七", 12, 8888.88),
            new Employee("田七", 12, 8888.88)
            );
    
	public static void main(String[] xxx){
		test7();
    }
    
    /*  筛选与切片
     *  filter--接收Lambda，从流中排除某些元素。
     *  limit--截断流，使其元素不超过给定数量。
     *  skip(n)--跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。与limit(n) 互补
     *  distinct--筛选，通过流所生成元素的 hashCode() 和 equals() 去掉重复元素
     */

    //内部迭代：迭代操作由 Stream API 完成
    private static void test1(){
        //中间操作：不会执行任何操作
        Stream<Employee> stream = employees.stream().filter((e) -> e.getAge() > 35);
        //终止操作：一次性执行全部内容，即 惰性求值
        stream.forEach(System.out::println);
//        李四       null     58  5555.55 null
//        赵六       null     36  6666.66 null
    }
    
    //外部迭代
    private static void test2(){
        Iterator<Employee> it = employees.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }

    private static void test3(){//发现“短路”只输出了两次，说明只要找到 2 个 符合条件的就不再继续迭代
        employees.stream()
                 .filter((e)->{
                     System.out.println("短路！");
                     return e.getSalary() > 6000;
                 })
                 .limit(2)//3,4
                 .forEach(System.out::println);
    }

    private static void test4(){
        employees.stream()
                 .filter((e)->e.getSalary()>5000)
                 .skip(2)//跳过前两个     6,7 
                 .distinct()//去重，注意：需要Employee重写hashCode 和 equals 方法
                 .forEach(System.out::println);
    }
	//中间操作
	/*
	 * 映射
	 * map--接收Lambda，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新元素。
	 * flatMap--接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */
	private static void test5(){
	    List<String> list = Arrays.asList("aaa","bbb","ccc","ddd");
//	    list.stream()
//	         //.map((str)->str.toUpperCase())
//	         .map(String::toUpperCase)
//	         .forEach(System.out::println);
//	    System.out.println("------------------------");
//
//	    employees.stream()
//	             //.map(x -> x.getName())
//	             .map(Employee::getName)
//	             .forEach(System.out::println);
//	    System.out.println("------------------------");

	    Stream<Stream<Character>> stream = list.stream().map(StreamConvert1::filterChatacter);
	    stream.forEach((sm)->{
	        sm.forEach(System.out::println);
	        //System.out.println();
	    });

	    System.out.println("------------------------");

	    Stream<Character> sm=list.stream().flatMap(StreamConvert1::filterChatacter);
	    sm.forEach(System.out::println);
	}
	
	@SuppressWarnings("unused")
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
        List<String> list = Arrays.asList("ccc","bbb","aaa");
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
                         return e1.getAge() - e2.getAge();
                     }
                 }).forEach(System.out::println); 
    }
}
