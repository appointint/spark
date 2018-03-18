package spark0913;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Jdk_Interface_Test {
	public static void main(String[] args){
//		test1();
		test2();
	}
	//Consumer<T> �����ͽӿڣ�void accept(T t);
	public static void test1(){
	       happy(1000,(m) ->System.out.println("ϲ���󱦽������ѣ�"+m+"Ԫ"));
	}
	public static void happy(double money,Consumer<Double> con){
	    con.accept(money);//����һ��money��û�з���ֵ��ֱ������
	}
	
    //Supplier<T> �����ͽӿ�: ���󣺲���ָ�������������������뼯���У�   T get();
    private static void test2(){
        List<Integer> numList=getNumList(3, ()->(int)(Math.random()*100));
        for (Integer num : numList) {
            System.out.println(num);
        }
        
        //�ڲ���
		 List<Integer> numList2=getNumList(10, new Supplier<Integer>() {
		 public Integer get(){
				 return (int)(Math.random()*100);
			 }
		});
		 
    }
    private static List<Integer> getNumList(int num,Supplier<Integer> sup){
        List<Integer> list=new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer n = sup.get();//nΪ����ֵ������һ��������������˵�����Ƿ���һ�����������ӵ�list��Ȼ�����list
            list.add(n);
        }
        return list;
    }

    //Function<T,R> �����ͽӿ�:R apply(T t);
    private static void test3(){
        String newStr = strHandler("\t\t\t ��������������  ", (str)->str.trim());
        System.out.println(newStr);

        String subStr = strHandler("�����ױȣ�Ϊ�ܳ���", (str)->str.substring(5,9));
        System.out.println(subStr);
    }
    private static String strHandler(String str,Function<String,String> fun){
        return fun.apply(str);//����һ���ַ������з���ֵ��
    }

    //Predicate<T> �����ͽӿڣ� boolean test(T t);
    private static void test4(){
        List<String> list=Arrays.asList("Hello","jj","Lambda","www","ok");
        List<String> strList = filterStr(list, s->s.startsWith("L"));        
        List<String> list2=filterStr(list, new Predicate<String>() {
			@Override
			public boolean test(String t) {
				// TODO �Զ����ɵķ������
				return t.length()>3;//((String)t).length()>3;
			}
		});        
        for (String string : strList) {
            System.out.println(string);
        }
    }
    //���󣺽������������ַ��������뼯����
    private static List<String> filterStr(List<String> list,Predicate<String> pre){
        List<String> strList = new ArrayList<>();
        for ( String str : list) {
            if(pre.test(str)){
                strList.add(str);
            }
        }
        return strList;
    }
}
