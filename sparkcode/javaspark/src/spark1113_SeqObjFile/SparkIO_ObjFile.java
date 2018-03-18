package spark1113_SeqObjFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class SparkIO_ObjFile {
    public static void main(String[] args) {
    	SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("SparkIO").set("spark.testing.memory", "2147480000");
		JavaSparkContext sc = new JavaSparkContext(conf);
		sc.setLogLevel("WARN");		
		writeObjFile(sc);
		//�ļ�����ȡ�Ķ�����person�����������ʽΪperson�����������û����person����foreach������ᱨ��
		readObjFile(sc);
		sc.stop();
		sc.close();
	}

	private static void readObjFile(JavaSparkContext sc) {
		//object�������ļ���ȡΪrdd
		JavaRDD<Object> input = sc.objectFile("file:///D:/jsontext/objFile");
		
		//���object�ļ�ʱ�Զ���ȡ���õ�person�������person���󲻴��ڣ����ᱨ����ֹ����
		input.foreach(x->System.out.println(x));
	}
    
	private static void writeObjFile(JavaSparkContext sc) {
	    List<Person> data = new ArrayList<Person>();
	    data.add(new Person("ABC", 1));
	    data.add(new Person("DEF", 3));
	    data.add(new Person("GHI", 2));
	    data.add(new Person("JKL", 4));
	    data.add(new Person("ABC", 1));

	    //���÷����������ٸ��������ж��ٸ�������ļ�
	    JavaRDD<Person> rdd = sc.parallelize(data, 2);
	    //���ļ�����ΪtextFile���ͣ����Ϊ�ı��ļ����ɼ����ı�Ϊtostring����
	    String dir = "file:///D:/jsontext/textFile";
        rdd.saveAsTextFile(dir);  
        
        //���ΪobjectFile���ͣ�Ϊ�������ļ������ļ�������Ƕ�������ͺ�ֵ������Ϊ�ı����ͣ�ֵΪ���������ͣ�ʹ��saveAsObject�����浽�ļ�
        //objectFile�洢ֻ����ֵ��rdd
	    String dir1 = "file:///D:/jsontext/objFile";
        rdd.saveAsObjectFile(dir1);
	}
	
	static class Person implements Serializable{
		public Person(String name, int id) {
			super();
			this.name = name;
			this.id = id;
		}
		@Override
		public String toString() {
			return "Person [name=" + name + ", id=" + id + "]";
		}
		String name;
		int id;
	}
}


