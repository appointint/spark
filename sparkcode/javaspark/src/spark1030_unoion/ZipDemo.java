package spark1030_unoion;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.spark.Partitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;
import scala.collection.Iterator;


public class ZipDemo {
    public static void main(String[] xx){
    	SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.set("spark.testing.memory", "2147480000");
    	conf.setAppName("WordCounter");
    	JavaSparkContext ctx = new JavaSparkContext(conf);

    	List<String> lines1 = new ArrayList<String>();
    	lines1.add("Hello");
    	lines1.add("How");
    	lines1.add("Moon");
//    	lines1.add("Hope");
//    	lines1.add("Dog");
//    	lines1.add("House");
    	JavaRDD<String> rdd1 = ctx.parallelize(lines1, 2);
    	System.out.println(rdd1.glom().collect());

    	List<String> lines2 = new ArrayList<String>();
    	lines2.add("1");
    	lines2.add("2");
    	lines2.add("3");
    	JavaRDD<String> rdd2 = ctx.parallelize(lines2, 2);
    	System.out.println(rdd2.glom().collect());
    	
    	
    	//使用zip必须数量和分区数相同，不然会报错
    	
    	JavaPairRDD<String, String> rdd31 = rdd1.zip(rdd2);
    	rdd31.foreach(x->System.out.println(x));
//    	(Hello,1)
//    	(How,2)
//    	(Moon,3)
    	
    	JavaRDD<HashMap<String, String>> rdd3 = rdd1.zipPartitions(rdd2,
    			( x, y)-> {
    		         System.out.println("*****************");
    				 List<HashMap<String, String>> lines = new ArrayList<HashMap<String, String>>();
//    		         List<String> list1 = new ArrayList<String>(); 
    		         while(x.hasNext()){												
//    		        	 list1.add(x.next());
    		        	 
    		        	 System.out.println(x.next());
    		         }
//    		         List<String> list2 = new ArrayList<String>(); 
    		         while(y.hasNext()){
//    		        	 list2.add(y.next());
    		        	 System.out.println(y.next());
    		         }
    				 
    		         return lines.iterator();
    		    });
    	rdd3.foreach(x->System.out.println(x));
    	
//    	*****************
//    	Hello
//    	1
//    	*****************
//    	How
//    	Moon
//    	2
//    	3
    	
//    	JavaRDD<String> rdd3 = rdd1.zipPartitions(rdd2,
//    			new FlatMapFunction2<
//    			                Iterator<String>,
//    			                Iterator<String>,
//    			                Iterator<String>>(){
//
//					@Override
//					public java.util.Iterator<Iterator<String>> call(
//							Iterator<String> x, Iterator<String> y)
//							throws Exception {
//						return null;
//					}
//    		
//    	});
//    	System.out.println(rdd3.collect());
//    	rdd3.foreach(x->System.out.println(x));
    }
}
