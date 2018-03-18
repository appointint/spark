package collectfanxing;

public class Fanxing1 {						//向上转型和向下转型。

	private Object b;						//定义object类型的成员变量

	public Object getB() {
		return b;
	}

	public void setB(Object b) {
		this.b = b;
	}
	
	public static void main(String[] args) {
		
		Fanxing1 t=new Fanxing1();
		t.setB(new Boolean(true));				//向上转型操作
		System.out.println(t.getB());
		
		t.setB(new Float(12.3));
//		System.out.println(t.getB());
		Float float1=(Float)t.getB();           //向下转型操作
		System.out.println(float1);
				
	}

}
 