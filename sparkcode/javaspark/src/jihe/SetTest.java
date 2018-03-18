package jihe;

import java.util.Iterator;
import java.util.TreeSet;

public class SetTest {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		
		UpdateStu stu1=new UpdateStu("李同学", 01011);
		UpdateStu stu2=new UpdateStu("陈同学", 01021);
		UpdateStu stu3=new UpdateStu("王同学", 01052);
		UpdateStu stu4=new UpdateStu("马同学", 01012);
		
		TreeSet<UpdateStu>treeSet=new  TreeSet<>();
		treeSet.add(stu1);
		treeSet.add(stu2);
		treeSet.add(stu3);
		treeSet.add(stu4);
		Iterator<UpdateStu>iterator=treeSet.iterator();
		System.out.println("set集合中的所有元素：");
		
		while (iterator.hasNext()) {
			UpdateStu updateStu = (UpdateStu) iterator.next();
			System.out.println(updateStu.getId()+"  "+updateStu.getName());			
		}
		
		iterator=treeSet.headSet(stu2).iterator();
		System.out.println("截取前面部分的集合：");
		while (iterator.hasNext()) {
			UpdateStu updateStu = (UpdateStu) iterator.next();
			System.out.println(updateStu.getId()+"  "+updateStu.getName());
		}
		
		iterator=treeSet.subSet(stu2,stu3).iterator();
		System.out.println("截取中间部分的集合：");
		while (iterator.hasNext()) {
			UpdateStu updateStu = (UpdateStu) iterator.next();
			System.out.println(updateStu.getId()+"  "+updateStu.getName());
		}
	}

}

class  UpdateStu implements Comparable<Object>{
	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	long id;
	public UpdateStu(String name,long id){
		this.id=id;
		this.name=name;
		
	}
	public int compareTo(Object o){
		UpdateStu updateStu=(UpdateStu)o;
		int result=id>updateStu.id ? 1:(id==updateStu.id?0:-1);
		return result;
		
	}
}
