package spark1023_ConbineByKey;
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
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.storage.StorageLevel;

import scala.Tuple2;

//JavaPairRDD<String, Integer> results = mapRdd.reduceByKey((x, y)->x+y);
public class CogroupApiTest {
    public static void main(String[] xx){
    	SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.set("spark.testing.memory", "2147480000");
    	conf.setAppName("WordCounter");
    	conf.set("spark.default.parallelism", "4");
    	JavaSparkContext ctx = new JavaSparkContext(conf);
    	//创建RDD：1）通过读取外部存储 ----- 集群环境使用 2）通过内存中的集合
    	List<Tuple2<String, Integer>> data1 = new ArrayList<Tuple2<String, Integer>>();
    	data1.add(new Tuple2<>("Cake", 2));
    	data1.add(new Tuple2<>("Bread", 3));
    	data1.add(new Tuple2<>("Cheese", 4));
    	data1.add(new Tuple2<>("Milk", 1));    	
    	data1.add(new Tuple2<>("Toast", 2));
    	data1.add(new Tuple2<>("Bread", 2));
    	data1.add(new Tuple2<>("Egg", 6));
    	
//    	JavaPairRDD<String, Integer> mapRdd1=ctx.parallelizePairs(data1);
    	
    	JavaRDD<Tuple2<String, Integer>> rdd1 = ctx.parallelize(data1, 2);

	      JavaPairRDD<String, Integer> mapRdd1 = rdd1.mapToPair(new PairFunction<Tuple2<String, Integer>, String, Integer>() {  
		      @Override
		      public Tuple2<String, Integer> call(Tuple2<String, Integer> t) throws Exception {  
		          return t;
		      }
	      }).partitionBy(new HashPartitioner(2)).persist(StorageLevel.MEMORY_ONLY());
	      
	  	List<Tuple2<String, Integer>> data2 = new ArrayList<Tuple2<String, Integer>>();
	  	data2.add(new Tuple2<>("Cake", 2));
	  	data2.add(new Tuple2<>("Bread", 3));
	  	data2.add(new Tuple2<>("Cheese", 4));
	  	data2.add(new Tuple2<>("Milk", 1));    	
	  	data2.add(new Tuple2<>("Toast", 2));
	  	JavaRDD<Tuple2<String, Integer>> rdd2 = ctx.parallelize(data2, 2);
	
	    JavaPairRDD<String, Integer> mapRdd2 = rdd2.mapToPair(new PairFunction<Tuple2<String, Integer>, String, Integer>() {  
		      @Override
		      public Tuple2<String, Integer> call(Tuple2<String, Integer> t) throws Exception {  
		          return t;
		      }
	    }).partitionBy(new HashPartitioner(2)).persist(StorageLevel.MEMORY_ONLY());

	  //groupWith，和cogroup是一样的效果  (Bread,([3, 2],[3]))
//	    JavaPairRDD<String, Tuple2<Iterable<Integer>, Iterable<Integer>>>  mapRdd3 = mapRdd1.cogroup(mapRdd2);
//	    mapRdd3.foreach(x->System.out.println(x));
	    
	  //(Bread,(3,3)),(Bread,(2,3)),(Cake,(2,2))   聚合操作
//	    JavaPairRDD<String, Tuple2<Integer, Integer>> mapRdd3 = mapRdd1.join(mapRdd2);
//	    mapRdd3.foreach(x->System.out.println(x));
	    
	    //(Bread,(Optional[3],3)), (Bread,(Optional[3],2)),(Cake,(Optional[2],2))   聚合操作，主集合可以为optional.empty
//	    JavaPairRDD<String, Tuple2<Optional<Integer>, Integer>>  mapRdd3 = mapRdd2.rightOuterJoin(mapRdd1);
//	    mapRdd3.foreach(x->System.out.println(x));

	    //(Cheese,(4,Optional[4])), (Toast,(2,Optional[2])), (Egg,(6,Optional.empty))
	    JavaPairRDD<String, Tuple2<Integer, Optional<Integer>>>  mapRdd4 = mapRdd1.leftOuterJoin(mapRdd2);
	    mapRdd4.foreach(x->System.out.println(x));    

	    //两边都能为空
//	    JavaPairRDD<String, Tuple2<Optional<Integer>, Optional<Integer>>> mapRdd5 = mapRdd1.fullOuterJoin(mapRdd2);
//	    mapRdd5.foreach(x->System.out.println(x));
	    
	    //groupWith，和cogroup是一样的效果   (Bread,([3, 2],[3]))
//	    JavaPairRDD<String, Tuple2<Iterable<Integer>, Iterable<Integer>>> mapRdd6 = mapRdd1.groupWith(mapRdd2);
//	    mapRdd6.foreach(x->System.out.println(x));
	    
	    //(Bread,(3,3)),(Bread,(2,3)),(Cake,(2,2))   聚合操作
//	    JavaPairRDD<String, Tuple2<Integer, Integer>> mapRdd7=mapRdd1.join(mapRdd2);
//	    mapRdd7.foreach(x->System.out.println(x));
	    
	    //聚合操作，将两个maprdd并集，重复元素不会被删掉
//	    JavaPairRDD<String,Integer>  mapRdd8=mapRdd1.union(mapRdd2);
//	    mapRdd8.foreach(x->System.out.println(x));
	    
	    //删除key相同的元素  (Egg,6)
	    JavaPairRDD<String, Integer> mapRdd9=mapRdd1.subtractByKey(mapRdd2);
	    mapRdd9.foreach(x->System.out.println(x));
	    
	    //求交集，只返回key，value相同的tuple  (Toast,2)  (Cake,2)  (Cheese,4)	    (Bread,3) (Milk,1)
//	    JavaPairRDD<String, Integer> mapRdd10=mapRdd1.intersection(mapRdd2);
//	    mapRdd10.foreach(x->System.out.println(x));
    }
}