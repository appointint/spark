/*
 * //1.找出2011年发生的所有交易，并按交易额排序(从低到高)
 * //2.交易员都在哪些不同的城市工作过？
 * //3.查找所有来自剑桥的交易员，并按姓名排序
 * //4.返回所有交易员的姓名字符串，按字母顺序排序
 * //5.有没有交易员是在米兰工作的？
 * //6.打印生活在剑桥的交易员的所有交易额
 * //7.所有交易中，最高的交易额是多少
 * //8.找到交易额最小的交易
 */
package spark0927_optional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.derby.tools.sysinfo;

public class StreamExam {
	    static List<Transaction> transaction=null;

	    private static void init(){
	        Trader raoul=new Trader("Raoul","Cambridge");
	        Trader mario=new Trader("Mario","Milan");
	        Trader alan=new Trader("Alan","Cambridge");
	        Trader brian=new Trader("Brian","Cambridge");

	        transaction=Arrays.asList(
	                new Transaction(brian, 2011, 300),
	                new Transaction(raoul, 2012, 1000),
	                new Transaction(raoul, 2011, 400),
	                new Transaction(mario, 2012, 710),
	                new Transaction(mario, 2012, 700),
	                new Transaction(alan, 2012, 950)
	        );			
		}
	    

	    public static void main(String[] args){
			init();
			test4();
		}
		
	    //1.找出2011年发生的所有交易，并按交易额排序(从低到高)
	    private static void test1(){
//	        transaction.stream()
//	                   .filter((e)->e.getYear()==2012)
//	                   .sorted((e1,e2)->Integer.compare(e1.getValue(), e2.getValue()))
//	                   .forEach(System.out::println);
	    	
	    	Stream<Transaction>   stream=transaction.stream()
	    		.filter((t)->t.getYear()==2011)
	    		.sorted((e1,e2)->Integer.compare(e1.getValue(), e2.getValue()));
	    	
	    	
	    	stream.forEach(System.out::println);
	    		
	    	
	    }

	    //2.交易员都在哪些不同的城市工作过？
	    private static void test2(){
//	        transaction.stream()
//	                   .map((e)->e.getTrader().getCity())
//	                   .distinct()//去重
//	                   .forEach(System.out::println);
//	        
	        
	    	Stream<String> stream= transaction.stream()
	    			.map((e)->e.getTrader().getCity())
	    			.distinct();
	    			
	    			stream.forEach(System.out::println);
	        
	        
	        
	    }

	    //3.查找所有来自剑桥的交易员，并按姓名排序
	    private static void test3(){
//	        transaction.stream()
//	                   .filter((e)->e.getTrader().getCity().equals("Cambridge"))
//	                   .map(Transaction::getTrader)
//	                   .sorted((e1,e2)->e1.getName().compareTo(e2.getName()))
//	                   .distinct()
//	                   .forEach(System.out::println);
//	        

	    	Stream<Trader> stream=transaction.stream()
	    			.filter((e)->e.getTrader().getCity().equals("Cambridge"))
	    			.map((e)->e.getTrader())
	    			 .sorted((e1,e2)->e1.getName().compareTo(e2.getName()));
	    			
	        stream.forEach(System.out::println);
	        
	    }

	    //4.返回所有交易员的姓名字符串，按字母顺序排序
	    private static void test4(){
//	        transaction.stream()
//	                   .map(Transaction::getTrader)
//	                   .map(Trader::getName)
//	                   .distinct()
//	                   .sorted()
//	                   .forEach(System.out::println);
//
//	        System.out.println("-------------------------");
//
//	        String str=transaction.stream()
//	                              .map((e)->e.getTrader().getName())
//	                              .distinct()
//	                              .sorted()
//	                              .reduce("", String::concat);
//	        System.out.println(str);//AlanBrianMarioRaoul
//
//	        System.out.println("-------------------------");

//	         transaction.stream()
//	                    .map((t)->t.getTrader().getName())
//	                    .flatMap(StreamExam::filterCharacter)//返回的每个String合成一个流
//	                    .sorted((s1,s2)->s1.compareToIgnoreCase(s2))
//	                    .forEach(System.out::print);//aaaaaAaBiiilllMMnnoooorRRrruu
//	         
	         
	    	Stream<String>stream=transaction.stream()
//	    			.map(Transaction::getTrader)
//	    			.map(Trader::getName)
	    			.map(x->x.getTrader().getName())
	    			.distinct()
	    			.sorted();
	    	
	    	
	    	stream.forEach(System.out::println);
	  
	    }

	    private static Stream<String> filterCharacter(String str){
	        List<String> list=new ArrayList<>();
	        for(Character ch:str.toCharArray()){
	            list.add(ch.toString());
	        }
	        return list.stream();
	    }

	    //5.有没有交易员是在米兰工作的？
	    private static void test5(){
	        boolean b1=transaction.stream()
	                              .anyMatch((t)->t.getTrader().getCity().equals("Milan"));
	        System.out.println(b1);
	        
	        
	        
	        
	    }

	    //6.打印生活在剑桥的交易员的所有交易额
	    private static void test6(){
	        Stream<Integer> all=transaction.stream()
	                                         .filter((e)->e.getTrader().getCity().equals("Cambridge"))
	                                         .map(Transaction::getValue);
//	         Optional<Integer>                                .reduce(Integer::sum);
	        all.forEach(System.out::println);
//	        System.out.println(sum.get());
	    }

	    //7.所有交易中，最高的交易额是多少
	    private static void test7(){
	        Optional<Integer> max=transaction.stream()
	                                         .map((t)->t.getValue())
	                                         .max(Integer::compare);
	        System.out.println(max.get());
	    }

	    //8.找到交易额最小的交易
	    private static void test8(){
	        Optional<Transaction> op=transaction.stream()
	                                            .min((t1,t2)->Integer.compare(t1.getValue(), t2.getValue()));
	        System.out.println(op.get());
	    }
	}

	
class Trader{
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Trader(String name, String city) {
		super();
		this.name = name;
		this.city = city;
	}
	String name;
	String city;
}

class Transaction{

	public Trader getTrader() {
		return trader;
	}
	public void setTrader(Trader trader) {
		this.trader = trader;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Transaction(Trader trader, int year, int value) {
		super();
		this.trader = trader;
		this.year = year;
		this.value = value;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trader == null) ? 0 : trader.hashCode());
		result = prime * result + value;
		result = prime * result + year;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (trader == null) {
			if (other.trader != null)
				return false;
		} else if (!trader.equals(other.trader))
			return false;
		if (value != other.value)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	Trader trader;
	int year;
	int value;
	
	public String toString(){
		return trader.getName()+" "+trader.getCity()+" "+year+" "+value;
	}
	
}
