package collectfanxing;

import java.util.HashMap;
import java.util.Map;

public class FxMap<K,V> {

	public Map<K, V>m=new HashMap<K,V>();			//����һ��hashmapʵ��
	
	public void put(K k,V v){						//����put()����������Ӧ�ļ�ֵ��������뼯�϶�����
		m.put(k, v);
	}
	
	public V get(K k){								//���ݼ�����ȡ��ֵ
		return m.get(k);
	}
	
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		FxMap<Integer, String>mu=new FxMap<Integer,String>();  //ʵ�������������
		for (int i = 0; i < 5; i++) {
			mu.put(i, "���Ǽ��ϳ�Ա"+(i+1)); 			//���ݼ��ϵĳ��Ƚ����������ֵ���뼯����
		}
		for (int i = 0; i < mu.m.size(); i++) {
			System.out.println(mu.get(i));			//����get()������ȡ�����е�ֵ
		}
		
		
	}

}
