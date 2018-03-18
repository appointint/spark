package spark1018_JoinOpt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.spark.HashPartitioner;
import org.apache.spark.Partitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.AbstractJavaRDDLike;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class CoalesceDemo {
    public static void main(String[] xx){
    	SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.setAppName("WordCounter").set("spark.testing.memory", "2147480000");
    	JavaSparkContext ctx = new JavaSparkContext(conf);
    	//创建RDD：1）通过读取外部存储 ----- 集群环境使用 2）通过内存中的集合

    	List<String> lines = new ArrayList<String>();
    	lines.add("Hello How are you Fine thanks a lot");
    	lines.add("No Fine thanks a lot ddddddddddd");
    	lines.add("Good are ok");
    	lines.add("Good is ok Fine thanks a lot");
    	lines.add("Hello How are you Fine thanks a lot");
    	lines.add("No Fine thanks a lot ddddddddddd");
    	lines.add("Good are ok");
    	lines.add("Good is ok Good are ok");
    	JavaRDD<String> rdd1 = ctx.parallelize(lines, 2);

        JavaRDD<String> words = rdd1.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        });

      JavaPairRDD<String, Integer> mapRdd = words.mapToPair(new PairFunction<String, String, Integer>() {  
	      @Override
	      public Tuple2<String, Integer> call(String s) throws Exception {  
	          return new Tuple2<String, Integer>(s, 1);  
	      }
      });

      JavaPairRDD<String, Integer> results = mapRdd.reduceByKey((x, y)->x+y);
      System.out.println("results partitioner:" + results.partitioner());
      System.out.println("results: " + results.getNumPartitions());
      //System.out.println("results :" + results.glom().collect());//glom显示分区的列表      

//      results.partitionBy(new HashPartitioner(4));
      results.repartition(4); //等效于：results.coalesce(4, true);
       //是否做shuffle（宽依赖）
       // results.coalesce(4, true);

      JavaPairRDD<String, Integer> coalescedRdd = results.coalesce(5, false);
      //JavaRDD<String> words1 = words.repartition(15);
      System.out.println("coalescedRdd : " + coalescedRdd.getNumPartitions());
      //System.out.println("coalescedRdd : " + coalescedRdd.toDebugString());
      //coalescedRdd.foreach(System.out::println);

//        Scanner sc = new Scanner(System.in);
//        sc.next();
//        sc.close();
        ctx.stop();
        
    }
}
