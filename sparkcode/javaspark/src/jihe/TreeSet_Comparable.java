package jihe;

import java.util.Iterator;
import java.util.TreeSet;

public class TreeSet_Comparable {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		
		TreeSet<Man> ts = new TreeSet<Man>();
		ts.add(new Man("Java", 22));
		ts.add(new Man("Hello", 13));
		ts.add(new Man("Scala", 24));
		ts.add(new Man("Python", 18));
		ts.add(new Man("Zoo", 18));
		ts.add(new Man("Java", 18));
		
		Iterator<Man>it=ts.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
		
	}

}

class Man implements Comparable<Man>{
	String name;
	int age;

	Man(String name, int age){
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString(){
		System.out.println("打印");
		return name + " " + age;
	}
 	@Override
	public boolean equals(Object another){
		System.out.println("equals()");
		if(another == this){
			return true;
		}
		if(another instanceof Man){
			Man p = (Man)another;
			if(this.name.equals(p.name) == false){
				return false;
			}else if(this.age != p.age){
				return false;
			}else {
				return true;
			}	
		}else {
			return false;
		}
	}

	@Override
	public int hashCode(){
		int hash = (name.hashCode() + age) * 31 - 56;
		System.out.println("hash== " + hash);
		return hash;
	}

	//为两个元素设定比较规则                                            年龄越大在后面
	@Override
	public int compareTo(Man o) {
		// TODO 自动生成的方法存根
		//return this.age-o.age;    //升序
		System.out.println("compareto");
		if(o.name.compareTo(this.name)==0){
			return o.age-this.age;    //降序
		}else{
			return o.name.compareTo(this.name);
		}
		
		  
	}

	
	
	
	
	
}