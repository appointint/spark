package jihe;

import java.util.Iterator;
import java.util.TreeSet;

public class TreeSet_Comparable {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		
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
		System.out.println("��ӡ");
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

	//Ϊ����Ԫ���趨�ȽϹ���                                            ����Խ���ں���
	@Override
	public int compareTo(Man o) {
		// TODO �Զ����ɵķ������
		//return this.age-o.age;    //����
		System.out.println("compareto");
		if(o.name.compareTo(this.name)==0){
			return o.age-this.age;    //����
		}else{
			return o.name.compareTo(this.name);
		}
		
		  
	}

	
	
	
	
	
}