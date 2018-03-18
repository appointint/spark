package spark1009_conf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class WordCountOnSpark {
    public static void main(String[] args){
    	SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.setAppName("WordCounter").set("spark.testing.memory", "2147480000");
    	
    	JavaSparkContext ctx = new JavaSparkContext(conf);
    	//创建RDD：1）通过读取外部存储 ----- 集群环境使用 2）通过内存中的集合
    	
    	List<String> strs = new ArrayList<String>();
    	strs.add("Hello How are you");
    	strs.add("No Fine thanks a lot");
    	strs.add("Good are ok");
    	JavaRDD<String> rdd1 = ctx.parallelize(strs);
    	
    	
    	JavaRDD<String> rdd2 = rdd1.flatMap(new FlatMapFunction<String, String>() {
			@Override
			public Iterator<String> call(String t) throws Exception {
				return Arrays.asList(t.split(" ")).iterator();
			}
		});
    	
    	rdd2.foreach(new VoidFunction<String>() {
			@Override
			public void call(String t) throws Exception {
                System.out.println(t);
			}
		});
    	
    	JavaPairRDD<String, Integer> rdd3 = rdd2.mapToPair(new PairFunction<String, String, Integer>() {
			@Override
			public Tuple2<String, Integer> call(String t) throws Exception {
				return new Tuple2<String, Integer>(t, 1);
			}
		});
    	
    	rdd3.foreach(new VoidFunction<Tuple2<String, Integer>>() {
			@Override
			public void call(Tuple2<String, Integer> t) throws Exception {
                System.out.println(t);
			}
		});	
    	
//    	rdd3.reduceByKey(new Function2<Integer, Integer, Integer>() {
//			@Override
//			public Integer call(Integer v1, Integer v2) throws Exception {
//				return v1 + v2;
//			}
//		}).foreach(System.out::println);

    	rdd3.reduceByKey((x,y)->x+y).foreach(System.out::println);

//    	rdd4.foreach(new VoidFunction<Tuple2<String, Integer>>() {
//			@Override
//			public void call(Tuple2<String, Integer> t) throws Exception {
//                System.out.println(t);
//			}
//		});
    	//rdd4.foreach(System.out::println);
    }
}
