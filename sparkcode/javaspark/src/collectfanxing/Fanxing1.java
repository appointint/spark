package collectfanxing;

public class Fanxing1 {						//����ת�ͺ�����ת�͡�

	private Object b;						//����object���͵ĳ�Ա����

	public Object getB() {
		return b;
	}

	public void setB(Object b) {
		this.b = b;
	}
	
	public static void main(String[] args) {
		
		Fanxing1 t=new Fanxing1();
		t.setB(new Boolean(true));				//����ת�Ͳ���
		System.out.println(t.getB());
		
		t.setB(new Float(12.3));
//		System.out.println(t.getB());
		Float float1=(Float)t.getB();           //����ת�Ͳ���
		System.out.println(float1);
				
	}

}
 