package question;

public class StringTest {

	/*
	 * 1)声明一个函数接口，其中一个抽象方法，public String getValue(String str)	;
	 * 2)用lambda表达式定义这个方法；
	 *     a.该方法的功能是吧传入的字符串中的字符转换成大写字母
	 *     b.把传入字符串中的字符串转换成小写字母
	 *     c.吧传入的字符串取出一部分：从第二个字符到第五个字符
	 *     
	 *     HashMap HashSet ArrayList LinkedList
	 *     泛型
	 */
		
		public static void main(String[] args) {
			
			StringInter stringInter1=(s)->{
				return s.toUpperCase();
			};
			System.out.println(stringInter1.getValue("Hello String Test"));
			
			StringInter stringInter2=(s)->{
				return s.toLowerCase();
			};
			System.out.println(stringInter2.getValue("Hello String Test"));
			
			StringInter stringInter3=(s)->{
				return s.substring(1, 5);
			};
			System.out.println(stringInter3.getValue("Hello String Test"));

		}

	}

@FunctionalInterface
	interface StringInter{
		String  getValue(String str);
	}