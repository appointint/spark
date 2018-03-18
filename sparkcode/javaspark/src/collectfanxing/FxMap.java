package collectfanxing;

import java.util.HashMap;
import java.util.Map;

public class FxMap<K,V> {

	public Map<K, V>m=new HashMap<K,V>();			//定义一个hashmap实例
	
	public void put(K k,V v){						//设置put()方法，将对应的键值与键名存入集合对象中
		m.put(k, v);
	}
	
	public V get(K k){								//根据键名获取键值
		return m.get(k);
	}
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		FxMap<Integer, String>mu=new FxMap<Integer,String>();  //实例化泛型类对象
		for (int i = 0; i < 5; i++) {
			mu.put(i, "我是集合成员"+(i+1)); 			//根据集合的长度将键名与具体值放入集合中
		}
		for (int i = 0; i < mu.m.size(); i++) {
			System.out.println(mu.get(i));			//调用get()方法获取集合中的值
		}
		
		
	}

}
