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

public class SparkStreamWC {
	   public static void main(String[] args) {
		   //专门分出去的task去取数据来执行（“local[4]”），至少为2，多个线程
	        SparkConf conf = new SparkConf().setMaster("local[4]").setAppName("wordcount").set("spark.testing.memory", "2147480000");
	        //设置时间间隔为3s
	        JavaStreamingContext jssc = new JavaStreamingContext(conf, new Duration(3000));
	        //设置服务器端口，从9999端口取数据                           nc -lp localhost
	        JavaDStream<String> lines = jssc.socketTextStream("localhost", 9999);
	        jssc.sparkContext().setLogLevel("WARN");
	        //JavaSparkContext ctx = new JavaSparkContext(conf);
	        //使用DStream离散化流
	        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
	            @Override
	            public Iterator<String> call(String s) throws Exception {
	            	System.out.println(s);
	                return Arrays.asList(s.split(" ")).iterator();
	            }
	        });

	        JavaPairDStream<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {  
	            @Override  
	            public Tuple2<String, Integer> call(String s) throws Exception {  
	                return new Tuple2<String, Integer>(s, 1);  
	            }  
	        });  

	        JavaPairDStream<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {  
	            @Override  
	            public Integer call(Integer integer, Integer integer2) throws Exception {  
	                return integer + integer2;  
	            }  
	        });  
	  
	        counts.print();                    //延迟操作
	        
	        jssc.start();                      //开启应用
	        try {
				jssc.awaitTermination();          //阻塞，等待作业完成
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
	    }      
}
