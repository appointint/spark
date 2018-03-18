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
		//文件所读取的对象是person对象，输出的形式为person对象，所以如果没有了person对象，foreach输出将会报错
		readObjFile(sc);
		sc.stop();
		sc.close();
	}

	private static void readObjFile(JavaSparkContext sc) {
		//object二进制文件读取为rdd
		JavaRDD<Object> input = sc.objectFile("file:///D:/jsontext/objFile");
		
		//输出object文件时自动读取引用的person对象，如果person对象不存在，将会报错，终止操作
		input.foreach(x->System.out.println(x));
	}
    
	private static void writeObjFile(JavaSparkContext sc) {
	    List<Person> data = new ArrayList<Person>();
	    data.add(new Person("ABC", 1));
	    data.add(new Person("DEF", 3));
	    data.add(new Person("GHI", 2));
	    data.add(new Person("JKL", 4));
	    data.add(new Person("ABC", 1));

	    //设置分区数，多少个分区数有多少个个输出文件
	    JavaRDD<Person> rdd = sc.parallelize(data, 2);
	    //将文件保存为textFile类型，输出为文本文件，可见的文本为tostring方法
	    String dir = "file:///D:/jsontext/textFile";
        rdd.saveAsTextFile(dir);  
        
        //输出为objectFile类型，为二进制文件，此文件保存的是对象的类型和值，类型为文本类型，值为二进制类型，使用saveAsObject方法存到文件
        //objectFile存储只包含值的rdd
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


