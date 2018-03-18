package spark0910;

public class Test1 {

	public static void main(String[] args) {
		
		
//		int x=(int)Math.random()*4;
//		String string=String.valueOf(x);
		
		// TODO 自动生成的方法存根
		//Foo f=new Fooimp();
		//f.fun();                             // hello spark
		
//		Foo f=new Foo() {                     //匿名内部类
//											  //new了一个类，匿名类
//			@Override
//			public void fun() {
//				// TODO 自动生成的方法存根
//				System.out.println("hello super spark 内部类");
//			}
//		};
//		f.fun();
		
//		new Thread(() ->System.out.println("hello")).start();
		
		Foo foo=()->System.out.println("hello 一个输出。");
		foo.fun();
		
//		Foo f=()->{
//			System.out.println("hello java8 ");
//			System.out.println("hello lambda 函数式编程");
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