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
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.storage.StorageLevel;

import scala.Tuple2;

//JavaPairRDD<String, Integer> results = mapRdd.reduceByKey((x, y)->x+y);
public class CombineByKeyTest {
    @SuppressWarnings("serial")
	public static void main(String[] xx){
    	SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.setAppName("WordCounter");
    	conf.set("spark.testing.memory", "5435657567560");
    	conf.set("spark.default.parallelism", "4");
    	JavaSparkContext ctx = new JavaSparkContext(conf);
    	//创建RDD：1）通过读取外部存储 ----- 集群环境使用 2）通过内存中的集合
    	List<Tuple2<String, Integer>> data = new ArrayList<Tuple2<String, Integer>>();
    	data.add(new Tuple2<>("Cake", 2));
    	data.add(new Tuple2<>("Bread", 3));  //<"Bread", <3,1>>
    	data.add(new Tuple2<>("Cheese", 4));
    	data.add(new Tuple2<>("Milk", 1));    	
    	data.add(new Tuple2<>("Toast", 2));
    	data.add(new Tuple2<>("Bread", 2)); 
    	data.add(new Tuple2<>("Egg", 6));
    	
    	JavaRDD<Tuple2<String, Integer>> rdd1 = ctx.parallelize(data, 2);

      JavaPairRDD<String, Integer> mapRdd = rdd1.mapToPair(
    		  new PairFunction<Tuple2<String, Integer>, String, Integer>() {  
	      @Override
	      public Tuple2<String, Integer> call(Tuple2<String, Integer> t) throws Exception {  
	          return t;
	      }
      });
      
//    	JavaPairRDD<String, Integer> mapRdd=ctx.parallelizePairs(data,2);
    	
     JavaPairRDD<String, Iterable<Integer>>xwSW= mapRdd.groupByKey();//.foreach(x->System.out.println(x));
      

//      JavaPairRDD<String, Tuple2<Integer, Integer>> results = mapRdd.combineByKey(
//        new Function<Integer, Tuple2<Integer, Integer>>() {
//			@Override
//			public Tuple2<Integer, Integer> call(Integer v1) throws Exception {
//				return new Tuple2<Integer, Integer>(v1 ,1);
//			}
//		}, new Function2<Tuple2<Integer, Integer>, Integer, Tuple2<Integer, Integer>>() {
//			@Override
//			public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> v1, Integer v2) throws Exception {
//				return new Tuple2<Integer, Integer>(v1._1() + v2, v1._2() + 1);
//			}
//		}, new Function2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>() {
//			@Override
//			public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> v1, Tuple2<Integer, Integer> v2) throws Exception {
//				return new Tuple2<Integer, Integer>(v1._1() + v2._1(), v1._2() + v2._2());
//			}
//		});

     
      JavaPairRDD<String, Tuple2<Integer, Integer>> result2s =  mapRdd.combineByKey(
    		                          (Integer value) -> new Tuple2<Integer, Integer>(value,1), 
    		                         (Tuple2<Integer, Integer> acc, Integer value) -> new Tuple2<Integer, Integer>(acc._1() + value, acc._2() + 1), 
    		                         (Tuple2<Integer, Integer> acc1, Tuple2<Integer, Integer> acc2) -> new Tuple2<Integer, Integer>(acc1._1() + acc2._1(), acc1._2() + acc2._2()),
    		                         new HashPartitioner(3),
    		                         true,
    		                         null
    		                       );
      result2s.foreach(x->System.out.println(x));
      
      JavaPairRDD<String, Tuple2<Integer, Integer>> results3 =  mapRdd.aggregateByKey(
    		  new Tuple2<Integer, Integer>(0,0),
              (acc, value) -> new Tuple2<Integer, Integer>(acc._1() + value, acc._2() + 1), 
              (acc1, acc2) -> new Tuple2<Integer, Integer>(acc1._1() + acc2._1(), acc1._2() + acc2._2())	  
    		  );

      results3.foreach(x->System.out.println(x));
      
     
      JavaPairRDD<String, Tuple2<Integer, Integer>> mapRdd1 = mapRdd.mapToPair(new PairFunction<Tuple2<String,Integer>, String, Tuple2<Integer, Integer>>() {
		@Override
		public Tuple2<String, Tuple2<Integer, Integer>> call(Tuple2<String, Integer> t) throws Exception {
			return new Tuple2<String, Tuple2<Integer, Integer>>(t._1(), new Tuple2<Integer, Integer>(t._2() , 1));
		}
	  });
      
       JavaPairRDD<String, Tuple2<Integer, Integer>> results =  mapRdd1.reduceByKey(new Function2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>() {
			@Override
			public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> acc1, Tuple2<Integer, Integer> acc2) throws Exception {
				return new Tuple2<Integer, Integer>(acc1._1() + acc2._1(), acc1._2() + acc2._2());
			}
	   });
//       results.foreach(System.out::println);
      
      results.foreach(x->System.out.println(x));
       ctx.stop();
    }
}
