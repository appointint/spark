package collectfanxing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LimitClass <T extends List>{      		//限制泛型的类型

	public static void main(String[] args) {
		
		LimitClass<ArrayList> l1=new LimitClass<ArrayList>();   //可以实例化已经实现list接口的类
		LimitClass<LinkedList> l2=new LimitClass<LinkedList>();
		
//		LimitClass<HashMap> l3=new LimitClass<HashMap>();		//这句都是错误的，因为HashMap没有实现list()接口
		
		
		
		List<String>l4=new ArrayList<String	>();	//实例化list对象
		l4.add("成员");								//在集合中添加内容
		List<?>l5=l4;								//使用通配符
		List<?>l6=new LinkedList<Integer>();
		System.out.println(l5.get(0));				//获取集合中第一个值
		
		l4.set(0, "成员改变");							//没有使用通配符的对象调用set方法
//		l5.set(0, "成员改变");							//使用通配符的对象调永set方法，不能被调用
//		l6.set(0, 1);
		l5.get(0);									//可以使用l5的实例获取集合中的值
		l5.remove(0);								//根据键名删除集合中的值
		
		
	}

}
