package jihe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapTest {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		Map<String, String>map=new HashMap<>();
		map.put("01", "��ͬѧ");
		map.put("02", "κͬѧ");
		
		Set<String> set=map.keySet();
		Iterator<String> iterator=set.iterator();
		System.out.println("key�����е�Ԫ�أ�");
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		Collection<String> collection=map.values();
		iterator=collection.iterator();
		System.out.println("value�����е�Ԫ�أ�");
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
	}

}
