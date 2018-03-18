//.map(word -> word.replaceAll("[^a-zA-Z]", ""))
package spark0927_optional;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class WordCount {
    public static void main(String[] args){
    	stringWordCount("Hello Ok Hello Java Save File Save Hello");
    }

    private static void stringWordCount(String s){
    	Map<String, Long> result = Arrays.stream(s.split(" "))
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(result);
    }
    
    private static void stringWordCount2(String s){
    	Stream<String> words = (Stream<String>) (Arrays.stream(s.split(" "))
                .map(word -> word.replaceAll("[^a-zA-Z]", "")));
               
    	//words.forEach(System.out::println);
    	
    	Map<String, Long> result = words.collect(Collectors.groupingBy(t -> t, Collectors.counting()));
    	System.out.println(result);
    	
    }    
    

}
