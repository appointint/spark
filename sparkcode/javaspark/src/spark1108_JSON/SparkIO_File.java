package spark1108_JSON;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;

import scala.Tuple2;
import au.com.bytecode.opencsv.CSVReader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SparkIO_File {
    public static void main(String[] args) {
    	SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("SparkIO").set("spark.testing.memory", "2147480000");
		JavaSparkContext sc = new JavaSparkContext(conf);
		sc.setLogLevel("WARN");
		fileTest(sc);
		sc.stop();
		sc.close();
	}

    static void fileTest(JavaSparkContext sc){
    	//ÿ�ж���rdd
//    	JavaRDD<String> rdd = sc.textFile("file:///E:/codes2016/workspace/Spark1/src/spark1106_StreamSpark/UpdateStateByKeyDemo.java");
		//wholeTextFiles����һ����ֵ�����ͣ���Ϊ�ļ�ȫ·����ֵΪ�ļ����ݣ���������2
    	
 
    	JavaPairRDD<String, String> rdd = sc.wholeTextFiles("file:///E:/codes2016/workspace/Spark1/src/spark1106_StreamSpark");
       	System.out.println("������:"+rdd.getNumPartitions());          //������Ϊ2
    	rdd.foreach(x->{
			System.out.println("��ǰԪ�أ�" + x);
		});
		System.out.println(rdd.count());
//		rdd.saveAsTextFile("file:///d:/jsontext/filewholetext");
    }
}

