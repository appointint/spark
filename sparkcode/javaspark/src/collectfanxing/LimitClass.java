package collectfanxing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LimitClass <T extends List>{      		//���Ʒ��͵�����

	public static void main(String[] args) {
		
		LimitClass<ArrayList> l1=new LimitClass<ArrayList>();   //����ʵ�����Ѿ�ʵ��list�ӿڵ���
		LimitClass<LinkedList> l2=new LimitClass<LinkedList>();
		
//		LimitClass<HashMap> l3=new LimitClass<HashMap>();		//��䶼�Ǵ���ģ���ΪHashMapû��ʵ��list()�ӿ�
		
		
		
		List<String>l4=new ArrayList<String	>();	//ʵ����list����
		l4.add("��Ա");								//�ڼ������������
		List<?>l5=l4;								//ʹ��ͨ���
		List<?>l6=new LinkedList<Integer>();
		System.out.println(l5.get(0));				//��ȡ�����е�һ��ֵ
		
		l4.set(0, "��Ա�ı�");							//û��ʹ��ͨ����Ķ������set����
//		l5.set(0, "��Ա�ı�");							//ʹ��ͨ����Ķ������set���������ܱ�����
//		l6.set(0, 1);
		l5.get(0);									//����ʹ��l5��ʵ����ȡ�����е�ֵ
		l5.remove(0);								//���ݼ���ɾ�������е�ֵ
		
		
	}

}
