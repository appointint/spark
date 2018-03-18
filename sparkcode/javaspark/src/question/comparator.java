package question;

import java.util.Comparator;
import java.util.TreeSet;

import scala.collection.Iterator;
import spark0910.Test2;

public class comparator {
	public static void main(String[] args) {
		test2();
	}

	  public void test1(){
	        Comparator<Integer> com=new Comparator<Integer>(){
	            @Override
	            public int compare(Integer o1, Integer o2) {
	                return Integer.compare(o1, o2);
	            }
	        };
	        TreeSet<Integer> ts=new TreeSet<>(com);
	  }
	  public static void test2(){
	        Comparator<Integer> com=(x,y)->Integer.compare(y,x);
	        TreeSet<Integer> ts=new TreeSet<>(com);
	        ts.add(33);
	        ts.add(44);
	        ts.add(22);
	        java.util.Iterator<Integer> iterator=ts.iterator();
	        while (iterator.hasNext()) {
				Integer it = (Integer) iterator.next();
				System.out.println(it);
			}
	    }

	
	
	
	
	
	
}
