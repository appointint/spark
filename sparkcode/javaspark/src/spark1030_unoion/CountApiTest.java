package spark1030_unoion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.hive.com.esotericsoftware.kryo.serializers.JavaSerializer;
import org.apache.spark.HashPartitioner;
import org.apache.spark.Partitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.AbstractJavaRDDLike;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.partial.BoundedDouble;
import org.apache.spark.partial.PartialResult;
import org.apache.spark.storage.StorageLevel;





import scala.Tuple2;

//JavaPairRDD<String, Integer> results = mapRdd.reduceByKey((x, y)->x+y);
public class CountApiTest {
    public static void main(String[] xx){
    	SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.setAppName("Count API");
    	conf.set("spark.testing.memory", "2147480000");
//    	conf.set("spark.default.parallelism", "4");
    	JavaSparkContext ctx = new JavaSparkContext(conf);
    	//创建RDD：1）通过读取外部存储 ----- 集群环境使用 2）通过内存中的集合

    	List<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < 10000; i++){
    	    list.add(i);
        }
    	JavaRDD<Integer> rdd1 = ctx.parallelize(list, 2);
    	JavaRDD<Integer> rdd2 = rdd1.union(rdd1).union(rdd1).union(rdd1);
    	
    	//System.out.println(rdd2.count());                  //计算并集后的总数
    	PartialResult<BoundedDouble> result = rdd2.countApprox(450);//1000, 300  2秒内跑完给结果，若没有完，也要返回结果
    	System.out.println(result.initialValue().mean());
    	System.out.println(result.initialValue().low());
    	System.out.println(result.initialValue().high());
    	System.out.println(result.initialValue().confidence()); //自信程度
    	
//    	40000.0             使用2000
//    	40000.0
//    	40000.0
//    	1.0
    	
//    	40000.6       使用450
//    	39696.95761248283
//    	40304.242387517166
//    	0.95
    	
    	//0.01  0.1  偏移度的大致跑完了的任务      执行的更快
//        System.out.println(rdd2.countApproxDistinct(0.01));   //9945


	      
        
    }
}