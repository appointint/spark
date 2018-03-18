package spark1009_ReduceBykey;
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
    	//创建RDD：1）通过读取外部存储 ----- 集群环境使用   2）通过内存中的集合
    	
    	List<String> strs = new ArrayList<String>();
    	strs.add("Hello How are you");
    	strs.add("No Fine thanks a lot");
    	strs.add("Good are ok");
    	JavaRDD<String> rdd1 = ctx.parallelize(strs);
    	
    	JavaRDD<String> rdd2 = rdd1.flatMap(x -> Arrays.asList(x.split(" ")).iterator());
//    	JavaRDD<String> rdd2 = rdd1.flatMap(new FlatMapFunction<String, String>() {
//			@Override
//			public Iterator<String> call(String t) throws Exception {
//				return Arrays.asList(t.split(" ")).iterator();
//			}
//		});
    	
    	rdd2.foreach(x->System.out.println(x));
//    	rdd2.foreach(new VoidFunction<String>() {
//			@Override
//			public void call(String t) throws Exception {
//                System.out.println(t);
//			}
//		});
    	
    	JavaPairRDD<String, Integer> rdd3 = rdd2.mapToPair(t -> new Tuple2<String, Integer>(t, 1));
//    	JavaPairRDD<String, Integer> rdd3 = rdd2.mapToPair(new PairFunction<String, String, Integer>() {
//			@Override
//			public Tuple2<String, Integer> call(String t) throws Exception {
//				return new Tuple2<String, Integer>(t, 1);
//			}
//		});
    	
    	JavaPairRDD<String, Integer> rdd4 = rdd3.reduceByKey(Integer::sum);
//    	JavaPairRDD<String, Integer> rdd4 = rdd3.reduceByKey(new Function2<Integer, Integer, Integer>() {
//			@Override
//			public Integer call(Integer v1, Integer v2) throws Exception {
//				return v1 + v2;
//			}
//		});

    	rdd4.foreach(x->System.out.println(x));
    	rdd4.foreach(x->System.out.println(x));
    }
}
