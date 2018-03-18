package spark0910;
public class Test2 {
		public static void main(String[] args) {
			
			//编译器会将lambda表达式式转换为内部类
			
			/*无返回值，带参*/
			Foo1 foo1=(a,b)->System.out.println("hello spark;"+(a+b));     //lambda表达式可以加类型，也能不加类型，类型推断
			foo1.show(4,5);
			
			/*又返回值，带参*/
			Foo2 foo2=(int a,int b)->{
				System.out.println(a+b);
				return a*b;
			};
			System.out.println(foo2.fun(7, 8));
			
			Foo2 foo3=(a,b)->a+b;
			int out=foo3.fun(3, 4);
			System.out.println(out);
			
	}
}

@FunctionalInterface           //java8新增的注解，报错的时候有提示
interface Foo1 {
	void show(int a,int b);
}

interface Foo2 {
	int fun(int a,int b);
}
