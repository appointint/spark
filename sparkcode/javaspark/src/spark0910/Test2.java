package spark0910;
public class Test2 {
		public static void main(String[] args) {
			
			//�������Ὣlambda���ʽʽת��Ϊ�ڲ���
			
			/*�޷���ֵ������*/
			Foo1 foo1=(a,b)->System.out.println("hello spark;"+(a+b));     //lambda���ʽ���Լ����ͣ�Ҳ�ܲ������ͣ������ƶ�
			foo1.show(4,5);
			
			/*�ַ���ֵ������*/
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

@FunctionalInterface           //java8������ע�⣬�����ʱ������ʾ
interface Foo1 {
	void show(int a,int b);
}

interface Foo2 {
	int fun(int a,int b);
}
