package question;

public class StringTest {

	/*
	 * 1)����һ�������ӿڣ�����һ�����󷽷���public String getValue(String str)	;
	 * 2)��lambda���ʽ�������������
	 *     a.�÷����Ĺ����ǰɴ�����ַ����е��ַ�ת���ɴ�д��ĸ
	 *     b.�Ѵ����ַ����е��ַ���ת����Сд��ĸ
	 *     c.�ɴ�����ַ���ȡ��һ���֣��ӵڶ����ַ���������ַ�
	 *     
	 *     HashMap HashSet ArrayList LinkedList
	 *     ����
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