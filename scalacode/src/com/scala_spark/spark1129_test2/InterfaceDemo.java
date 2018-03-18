
public class InterfaceDemo {
	public static void main(String[] args) {
	   Test t = new TestClass();
	   t.func2();		
	}

}

interface Test{
	void func1();
	int sum(int x, int y);
	
	//默认方法
	default void func2(){
		System.out.println("this is a default method");
	}
}

class TestClass implements Test{
	@Override
	public void func1() {
	}

	@Override
	public int sum(int x, int y) {
		return 0;
	}
	
	@Override
	public void func2(){
		System.out.println("TestClass func2");
	}
	
}