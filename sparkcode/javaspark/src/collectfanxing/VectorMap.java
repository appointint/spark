package collectfanxing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class VectorMap {

	public static void main(String[] args) {
		
		ArrayList<Integer> a=new ArrayList<Integer>();      //����arraylist���������������ڵ�ֵ����Ϊinteger
		a.add(1);                                            //Ϊ���������ֵ
		for (int i = 0; i < a.size(); i++) {
			System.out.println("��ȡArraylist��ֵ��"+a.get(i));  //���������ĳ���ѭ����ʾ�����ڵ�ֵ
		}
		
		
		Map<Integer , String>m=new HashMap<Integer,String>();  //����HashMap���������������ļ������ֵ���ͷֱ�Ϊinteger��String��
		for (int i = 0; i < 5; i++) {
			m.put(i, "��Ա"+i);									//Ϊ�������������ֵ
		}
		for (int i = 0; i < m.size(); i++) {
			System.out.println("��ȡMap������ֵ"+m.get(i));			//���ݼ�����ȡ��ֵ
		}
		
		Vector<String>v=new Vector<String>();					//����Vector������ʹ������ ������ΪString��
		for (int i = 0; i < 5; i++) {
			v.addElement("��Ա"+i);								//ΪVector�����������
		}
		for (int i = 0; i < v.size(); i++) {
			System.out.println("��ȡMap������ֵ"+v.get(i));			//��ʾ�����ڵ�����
		}
		
	}

}
