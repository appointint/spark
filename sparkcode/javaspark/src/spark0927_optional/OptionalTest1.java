package spark0927_optional;

import java.util.Optional;

public class OptionalTest1 {
    public static void main(String[] args){
    	
    	String s = new String("Ha");
    	Optional<String> op = Optional.of(s);
    	System.out.println(op.get());
    	
//    	
//    	String s1 = op.get();
//    	System.out.println(s1);
    	
//    	Optional<String> op1 = Optional.empty();
//    	String s1 = op1.get();
//    	System.out.println(op1.get());   
    	
    	Optional<String> op1 = Optional.ofNullable(null);
    	System.out.println(op1.isPresent());
    	System.out.println(op1.orElse(new String("Google")));
//    	System.out.println(op1.orElseGet(() -> new String("Ali")));
    	
//    	Optional<String> op2 = op1.map((x) -> x.toLowerCase());
//    	String s2 = op2.get();
//    	System.out.println(s2);    	
    	
    	
    }
}

