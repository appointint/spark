package spark0910;

public class Test1 {

	public static void main(String[] args) {
		
		
//		int x=(int)Math.random()*4;
//		String string=String.valueOf(x);
		
		// TODO �Զ����ɵķ������
		//Foo f=new Fooimp();
		//f.fun();                             // hello spark
		
//		Foo f=new Foo() {                     //�����ڲ���
//											  //new��һ���࣬������
//			@Override
//			public void fun() {
//				// TODO �Զ����ɵķ������
//				System.out.println("hello super spark �ڲ���");
//			}
//		};
//		f.fun();
		
//		new Thread(() ->System.out.println("hello")).start();
		
		Foo foo=()->System.out.println("hello һ�������");
		foo.fun();
		
//		Foo f=()->{
//			System.out.println("hello java8 ");
//			System.out.println("hello lambda ����ʽ���");
//		};
//		
//		f.fun();
	}
}

@FunctionalInterface
 interface Foo {
	void fun();
}
// class Fooimp implements Foo {
//	public void fun(){
//		System.out.println("hello spark");
//	}
//}