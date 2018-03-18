package collectfanxing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class VectorMap {

	public static void main(String[] args) {
		
		ArrayList<Integer> a=new ArrayList<Integer>();      //定义arraylist容器，设置容器内的值类型为integer
		a.add(1);                                            //为容器添加新值
		for (int i = 0; i < a.size(); i++) {
			System.out.println("获取Arraylist的值："+a.get(i));  //根据容器的长度循环显示容器内的值
		}
		
		
		Map<Integer , String>m=new HashMap<Integer,String>();  //定义HashMap容器，设置容器的键名与键值类型分别为integer与String型
		for (int i = 0; i < 5; i++) {
			m.put(i, "成员"+i);									//为容器填充键名与键值
		}
		for (int i = 0; i < m.size(); i++) {
			System.out.println("获取Map容器的值"+m.get(i));			//根据键名获取键值
		}
		
		Vector<String>v=new Vector<String>();					//定义Vector容器，使容器中 的内容为String型
		for (int i = 0; i < 5; i++) {
			v.addElement("成员"+i);								//为Vector容器添加内容
		}
		for (int i = 0; i < v.size(); i++) {
			System.out.println("获取Map容器的值"+v.get(i));			//显示容器内的内容
		}
		
	}

}
