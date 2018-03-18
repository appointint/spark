package jihe;

import java.util.*;
import java.util.Map.Entry;

public class TreeMapTest {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		
		Map<String, String>map=new HashMap<>();
		Emp emp1=new Emp("001", "����");
		Emp emp2=new Emp("005", "����");
		Emp emp3=new Emp("004", "��һ");
		map.put(emp1.getE_id(), emp1.getE_name());
		map.put(emp2.getE_id(), emp2.getE_name());
		map.put(emp3.getE_id(), emp3.getE_name());
		Set<String> set=map.keySet();
		Iterator<String> it=set.iterator();
		System.out.println("HashMap��ʵ�ֵ�Map���ϣ�����");
		while (it.hasNext()) {
			String string = (String) it.next();
			String name=(String)map.get(string);
			System.out.println(string+"  "+name);
		}
		
		TreeMap<String, String>treeMap=new TreeMap<>();
		treeMap.putAll(map);  
		Iterator<String> iterator=treeMap.keySet().iterator();
		System.out.println("treeMap��ʵ�ֵ�Map���ϣ�����������");
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
			String name=(String)treeMap.get(string);
			System.out.println(string+"  "+name);
		}
		
	}

}

class Emp{
	private String e_id;
	private String e_name;
	public Emp(String e_id,String e_name){
		this.e_id=e_id;
		this.e_name=e_name;
	}
	public String getE_id() {
		return e_id;
	}
	public void setE_id(String e_id) {
		this.e_id = e_id;
	}
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	
	
}


