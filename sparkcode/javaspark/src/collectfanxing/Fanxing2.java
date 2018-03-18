package collectfanxing;

public class Fanxing2<T> {                   //定义泛型类
	
	private T over;							//定义泛型成员变量

	public T getOver() {
		return over;
	}

	public void setOver(T over) {
		this.over = over;
	}


	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		
		Fanxing2<Boolean >over1=new Fanxing2<Boolean>();	//实例化一个Boolean型的对象
		Fanxing2<Float >over2=new Fanxing2<Float>();		//实例化一个float的对象
		over1.setOver(true);								//不需要进行类型转换
		over2.setOver(12.3f);
		Boolean boolean1=new Boolean(over1.getOver());		//不需要进行类型转换
		Float float1=over2.getOver();
		System.out.println(boolean1);
		System.out.println(float1);
		

	}


}
