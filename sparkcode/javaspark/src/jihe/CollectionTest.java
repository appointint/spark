package jihe;
import java.util.*;
public class CollectionTest {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		
		Collection<String> collection=new ArrayList<>();
		collection.add("a");
		collection.add("b");
		collection.add("c");
		
		Iterator<String> iterator=collection.iterator();
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
			System.out.println(string);
		}

	}

}
