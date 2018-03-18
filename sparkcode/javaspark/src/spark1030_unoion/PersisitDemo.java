package spark1030_unoion;
import java.util.Iterator;

import org.apache.spark.SparkConf;  
import org.apache.spark.api.java.JavaPairRDD;  
import org.apache.spark.api.java.JavaRDD;  
import org.apache.spark.api.java.JavaSparkContext;  
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.Function3;  
import org.apache.spark.api.java.function.PairFunction;  
import org.apache.spark.storage.StorageLevel;

import scala.Tuple2;
import scala.Tuple3;  
  


import java.util.Arrays;  
import java.util.Iterator;
import java.util.Scanner;


public class PersisitDemo {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local[3]").setAppName("wordcount").set("spark.testing.memory", "3147480000");
        JavaSparkContext ctx = new JavaSparkContext(sparkConf);
//        ctx.setCheckpointDir("file:///d:/checkpoint");
        final JavaRDD<String> lines = ctx.textFile("words.txt").repartition(3);

        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
            	long th=Thread.currentThread().getId();
            	System.out.println("flatMap...  thread  id:"+th);
                return Arrays.asList(s.split(" ")).iterator();
            }
        }).repartition(3)
        //.persist(StorageLevel.MEMORY_ONLY());
        .cache();
        
        //使用了缓存第二次调用的时候不会再次执行
        
        while(true){
        	Scanner sc = new Scanner(System.in);
        	String line = sc.next();
        	if(line.equals("END")){
        		break;
        	}
        	
	        JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {  
	            @Override
	            public Tuple2<String, Integer> call(String s) throws Exception {  
	            	long th=Thread.currentThread().getId();
	            	System.out.println("mapToPair...  thread  id:"+th);
	                return new Tuple2<String, Integer>(s, 1);
	            }
	        }).repartition(3);
	        
	        JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {  
	            public Integer call(Integer integer, Integer integer3) throws Exception {  
	            	long th=Thread.currentThread().getId();
	            	System.out.println("reduceByKey...  thread  id:"+th);
	                return integer + integer3;  
	            }  
	        }).repartition(3);
	        //counts.saveAsTextFile(args[1]);
	        counts.foreach(x->System.out.println(x));

	        
//	        lines.unpersist();
        }
        ctx.stop();
    }  
}
