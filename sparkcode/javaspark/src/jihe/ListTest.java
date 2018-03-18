package jihe;

import java.util.ArrayList;
import java.util.List;

import org.dmg.pmml.Array;

public class ListTest {

	public static void main(String[] args) {
//		// TODO 自动生成的方法存根
//		List< String > list=new ArrayList<>();
//		list.add("a");
//		list.add("b");
//		list.add("c");
//		int i=(int)Math.random()*(list.size()-1);
//		System.out.println("随机获取数组中的元素："+list.get(i));
//		for (int j = 0; j < list.size(); j++) {
//			System.out.println(list.get(j));
//		}
		
		
		long n=11;
		   long num=0;
	        long sum=1;
	        for(long i=1;i<=25;i++){
	            sum=sum*i;
	        }
	        System.out.println(String.valueOf(sum));
	        List<String> l=new ArrayList<String>();
	        for(int i=String.valueOf(sum).length()-1;i>=0;i--){
	            l.add(String.valueOf(sum).substring((i),(i+1)));
	        }
	        System.out.println(l.toString());
	        for(int i=0;i<l.size();i++){
	        	System.out.println(l.get(i));
	        	if(l.get(i).equals("0")){
	        		num++;
	        		System.out.println(i);
	        		if(l.get(i+1)!="0"){
	        			continue;
	        		}
	        	}
	        }
	        System.out.println(num);
		
		

	}

}
