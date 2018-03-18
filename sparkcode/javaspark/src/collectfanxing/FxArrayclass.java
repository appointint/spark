package collectfanxing;

public class FxArrayclass<T> {

	private  T[] array;					//定义泛型数组

	public T[] getArray() {
		return array;
	}

	public void setArray(T[] array) {
		this.array = array;
	}

	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		FxArrayclass<String> arrayclass=new FxArrayclass<String>();
		String[] array={"1","2","3","4","5"};
		arrayclass.setArray(array);
		for (int i = 0; i < arrayclass.getArray().length; i++) {
			System.out.println(arrayclass.getArray()[i]);
			
		}

	}
}
