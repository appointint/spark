package collectfanxing;

public class Fanxing2<T> {                   //���巺����
	
	private T over;							//���巺�ͳ�Ա����

	public T getOver() {
		return over;
	}

	public void setOver(T over) {
		this.over = over;
	}


	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		
		Fanxing2<Boolean >over1=new Fanxing2<Boolean>();	//ʵ����һ��Boolean�͵Ķ���
		Fanxing2<Float >over2=new Fanxing2<Float>();		//ʵ����һ��float�Ķ���
		over1.setOver(true);								//����Ҫ��������ת��
		over2.setOver(12.3f);
		Boolean boolean1=new Boolean(over1.getOver());		//����Ҫ��������ת��
		Float float1=over2.getOver();
		System.out.println(boolean1);
		System.out.println(float1);
		

	}


}
