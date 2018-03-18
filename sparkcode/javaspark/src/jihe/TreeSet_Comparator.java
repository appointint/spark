package jihe;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class TreeSet_Comparator {

	public static void main(String[] args) {

		TreeSet<String> treeSet = new TreeSet<String>(new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		TreeSet<String> ts = new TreeSet<String>(new StringComparator());
		ts.add("hello");
		ts.add("java");
		ts.add("scala");
		ts.add("python");
		
		Iterator<String>it=ts.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
		
	}

}

class HuManComparator implements Comparator<HuMan> {

	@Override
	public int compare(HuMan o1, HuMan o2) {

		return o2.id - o1.id;

	}

}

class StringComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {

		return o1.compareTo(o2);

	}

}

class HuMan implements Comparator<String> {
	String name;
	int id;

	HuMan(String name, int id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String toString() {
		return name + " " + id;
	}

	public boolean equals(Object another) {
		// System.out.println("equals()");
		if (another == this) {
			return true;
		}
		if (another instanceof HuMan) {
			HuMan p = (HuMan) another;
			if (this.name.equals(p.name) == false) {
				return false;
			} else if (this.id != p.id) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hash = (name.hashCode() + id) * 31 - 56;
		// System.out.println("hash== " + hash);
		return hash;
	}

	@Override
	public int compare(String o1, String o2) {
		// TODO 自动生成的方法存根
		return o1.compareTo(o2);
	}

}