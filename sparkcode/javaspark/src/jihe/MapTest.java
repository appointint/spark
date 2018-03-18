package jihe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapTest {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Map<String, String>map=new HashMap<>();
		map.put("01", "李同学");
		map.put("02", "魏同学");
		
		Set<String> set=map.keySet();
		Iterator<String> iterator=set.iterator();
		System.out.println("key集合中的元素：");
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		Collection<String> collection=map.values();
		iterator=collection.iterator();
		System.out.println("value集合中的元素：");
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
	}

}
