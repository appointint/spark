package spark1106_StreamSpark;

import org.apache.spark.SparkConf;  
import org.apache.spark.api.java.JavaPairRDD;  
import org.apache.spark.api.java.JavaRDD;  
import org.apache.spark.api.java.JavaSparkContext;  
import org.apache.spark.api.java.function.FlatMapFunction;  
import org.apache.spark.api.java.function.Function2;  
import org.apache.spark.api.java.function.PairFunction;  
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;  
  

import java.util.Arrays;  
import java.util.Iterator;

public class WC_Stream_Window {
	   public static void main(String[] args) {
	        SparkConf conf = new SparkConf().setMaster("local[4]").setAppName("wordcount").set("spark.testing.memory", "2147480000");
	        //���μ��1��
	        JavaStreamingContext jssc = new JavaStreamingContext(conf, new Duration(1000));
	        jssc.sparkContext().setLogLevel("WARN");
	        jssc.checkpoint("file:///d:/checkpoint");
	        JavaDStream<String> lines = jssc.socketTextStream("localhost", 9999);
	        //JavaDStream<String> lines = jssc.textFileStream("file:///F:/workspace/PartitionDemo/src/bigdata12_1103");
	        //����ʱ��6��,��������3�룬ÿ�μ���6�����εĴ��ڣ�ÿ�ĸ����μ���һ��
//	        lines = lines.window(new Duration(6000), new Duration(3000));
	        //����ʱ��6�룬��������4��
	        //lines = lines.window(new Duration(6000), new Duration(4000));

	        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
	            @Override
	            public Iterator<String> call(String s) throws Exception {
	            	System.out.println("flatMap��" + s);
	                return Arrays.asList(s.split(" ")).iterator();
	            }
	        });
	        JavaPairDStream<String, Integer> ones = words.mapToPair(s->new Tuple2<String, Integer>(s, 1));  

	        //������������ִ�й�Լ   ������Ļ�����������ͬ��Ч��  ��6000,4000�����ظ�����    ��4000,4000�����ܹ�û���ظ�����
	        JavaPairDStream<String, Integer> counts = ones.reduceByKeyAndWindow(
	        		                          (x, y) ->{
	        		                        	  System.out.println("��Լ���ݡ�����");
	        	                                  return x + y;
	        	                              }, 
	        		                          new Duration(6000), new Duration(4000));

	        //ֻ�����½��봰�ڵ����ݣ����뿪���ڵ����ݣ����������Լ���   
	        //��ȥ���뿪���ڵ����� 
//	        ���ⷴ�������ظ�������
//	        JavaPairDStream<String, Integer> counts = ones.reduceByKeyAndWindow(
//											        		(x, y) -> x + y, 
//											        		(x, y) -> x-y,
//											        		new Duration(6000),
//											        		new Duration(4000));

//	        JavaPairDStream<String, Integer> counts = ones.reduceByKey(
//	        		                   (x, y)->{
//	        		                       System.out.println("��Լ���ݣ� x:" + x + " y:" + y);
//	        		                       return x + y;
//	        		                   }
//	        		               );

	        counts.print();
	        
	        jssc.start();
	        try {
				jssc.awaitTermination();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
	        jssc.close();
	    }
}
