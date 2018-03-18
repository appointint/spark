package spark0918_stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Zstreamcreatetest {
	public static void main(String[] args) {

		List<String>list=new ArrayList<>();
		list.add("how");
		list.add("are");
		Stream<String>stream1=list.stream();
		//stream1.forEach(x->System.out.println(x));
		stream1.forEach(System.out::println);
		
		Employee[] emps=new Employee[5];
		Stream<Employee>stream2=Arrays.stream(emps);
		stream2.forEach(x->System.out.println(x));
		
		Stream<String> stream3=Stream.of("a","b","c");
		stream3.forEach(x->System.out.println(x));
		
		Stream<Integer>stream4=Stream.iterate(0, x->x+2);
		stream4.limit(10).forEach(x->System.out.println(x));
		
		Stream.generate(()->Math.random()).limit(5).forEach(System.out::println);
		
	}

}
