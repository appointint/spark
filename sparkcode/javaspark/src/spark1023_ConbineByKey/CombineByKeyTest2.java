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
public class CombineByKeyTest2 {
    public static void main(String[] xx){
    	SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.set("spark.testing.memory", "2147480000");
    	conf.setAppName("WordCounter");
    	conf.set("spark.default.parallelism", "4");
    	JavaSparkContext ctx = new JavaSparkContext(conf);
    	//创建RDD：1）通过读取外部存储 ----- 集群环境使用 2）通过内存中的集合
    	List<Tuple2<String, Integer>> data = new ArrayList<Tuple2<String, Integer>>();
    	data.add(new Tuple2<>("Cake", 2));
    	data.add(new Tuple2<>("Bread", 3));
    	data.add(new Tuple2<>("Cheese", 4));
    	data.add(new Tuple2<>("Milk", 1));    	
    	data.add(new Tuple2<>("Toast", 2));
    	data.add(new Tuple2<>("Bread", 2));
    	data.add(new Tuple2<>("Egg", 6));
    	
    	int index="Code".hashCode() % 4;
    	
    	JavaRDD<Tuple2<String, Integer>> rdd1 = ctx.parallelize(data, 2);

      JavaPairRDD<String, Integer> mapRdd = rdd1.mapToPair(new PairFunction<Tuple2<String, Integer>, String, Integer>() {  
	      @Override
	      public Tuple2<String, Integer> call(Tuple2<String, Integer> t) throws Exception {  
	          return t;
	      }
      }).partitionBy(new HashPartitioner(4)).persist(StorageLevel.MEMORY_ONLY());
      
      

//      JavaPairRDD<String, Tuple2<Integer, Integer>> results =  mapRdd.combineByKey(
//    		                         (value) -> new Tuple2<Integer, Integer>(value,1), 
//    		                         (acc, value) -> new Tuple2<Integer, Integer>(acc._1() + value, acc._2() + 1), 
//    		                         (acc1, acc2) -> new Tuple2<Integer, Integer>(acc1._1() + acc2._1(), acc1._2() + acc2._2()),
//    		                         new HashPartitioner(2),
//    		                         false,
//                                     null
//    		                       );

//      JavaPairRDD<String, Tuple2<Integer, Integer>> results =  mapRdd.aggregateByKey(
//    		  new Tuple2<Integer, Integer>(0,0),
//              (acc, value) -> new Tuple2<Integer, Integer>(acc._1() + value, acc._2() + 1), 
//              (acc1, acc2) -> new Tuple2<Integer, Integer>(acc1._1() + acc2._1(), acc1._2() + acc2._2())	  
//    		  );
      
//      JavaPairRDD<String, Tuple2<Integer, Integer>> mapRdd1 = mapRdd.mapToPair(new PairFunction<Tuple2<String,Integer>, String, Tuple2<Integer, Integer>>() {
//		@Override
//		public Tuple2<String, Tuple2<Integer, Integer>> call(Tuple2<String, Integer> t) throws Exception {
//			return new Tuple2<String, Tuple2<Integer, Integer>>(t._1(), new Tuple2<Integer, Integer>(t._2() , 1));
//		}
//	  });
//      mapRdd1.foreach(System.out::println);
      
//       JavaPairRDD<String, Tuple2<Integer, Integer>> results =  mapRdd1.reduceByKey(new Function2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>() {
//			@Override
//			public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> acc1, Tuple2<Integer, Integer> acc2) throws Exception {
//				return new Tuple2<Integer, Integer>(acc1._1() + acc2._1(), acc1._2() + acc2._2());
//			}
//	   });
       //results.foreach(System.out::println);

//       results = mapRdd1.foldByKey(new Tuple2<Integer, Integer>(0, 0), new Function2<Tuple2<Integer,Integer>, Tuple2<Integer,Integer>, Tuple2<Integer,Integer>>() {
//			@Override
//			public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> acc1, Tuple2<Integer, Integer> acc2) throws Exception {
//				return new Tuple2<Integer, Integer>(acc1._1() + acc2._1(), acc1._2() + acc2._2());
//			}
//	   });
       //results.foreach(System.out::println);

       //思考：如何用combineByKey实现groupByKey
//        mapRdd.groupByKey().foreach(System.out::println);

        Function<Integer, List<Integer>> createCombiner=new Function<Integer, List<Integer>>() {

			@Override
			public List<Integer> call(Integer arg0) throws Exception {
				List<Integer>list=new ArrayList<Integer>();
				list.add(arg0);
				return list;
			}
		};
      
		Function2<List<Integer>, Integer, List<Integer>> mergeValue=new Function2<List<Integer>, Integer, List<Integer>>() {

			@Override
			public List<Integer> call(List<Integer> list, Integer value) throws Exception {
				list.add(value);
				return list;
			}
		};
		
		Function2< List<Integer>,List<Integer> ,List<Integer> > mergeCombiners=new Function2<List<Integer>, List<Integer>, List<Integer>>() {

			@Override
			public List<Integer> call(List<Integer> list1, List<Integer> list2) throws Exception {
				List<Integer> list=new ArrayList<Integer>();
//				list.addAll(list1);
//				list.addAll(list2);
				
				list1.forEach(list::add);
				list2.forEach(list::add);
				
				return list;
			}
		};
				
		JavaPairRDD<String, List<Integer>> results=mapRdd.combineByKey(createCombiner, mergeValue, mergeCombiners);
		
		results.foreach(x->System.out.println(x));
		
		JavaPairRDD<String, Integer> re=mapRdd.partitionBy(new HashPartitioner(2));
		System.out.println(re.glom().collect());
		
		//第四个参数是分区数，glom()打印分区状态
		JavaPairRDD<String, List<Integer>> results2=mapRdd.combineByKey(createCombiner, mergeValue, mergeCombiners, 2);
		System.out.println(results2.glom().collect());
		System.out.println(results2.getNumPartitions());
		
		//第四个参数自定义分区器
		JavaPairRDD<String, List<Integer>> results3=mapRdd.combineByKey(createCombiner, mergeValue, mergeCombiners,new HashPartitioner(3));
		System.out.println(results3.glom().collect());
		System.out.println(results3.getNumPartitions());
		
		//第四个参数自定义分区器，第五个参数Boolean类型（map短是否merge），第六个参数定义序列化规则，null为默认序列化规则
		JavaPairRDD<String, List<Integer>> results4=mapRdd.combineByKey(createCombiner, mergeValue, mergeCombiners, new HashPartitioner(3), true, null);
		System.out.println(results4.glom().collect());
		System.out.println(results4.getNumPartitions());
		
       
//       mapRdd1.combineByKey(
//    		   new Function<Tuple2<Integer,Integer>, Tuple2<Integer,Integer>>() {
//		           @Override
//						public Tuple2<Integer,Integer> call(Tuple2<Integer, Integer> arg0) throws Exception {
//							return arg0;
//						}
//	           }, 
//	           
//	           new Function2<Tuple2<Integer, Integer>, Integer, Tuple2<Integer, Integer>[]>() {
//				@Override
//				public Tuple2<Integer, Integer>[] call(Tuple2<Integer, Integer> arg0, Integer arg1) throws Exception {
//					return null;
//				}
//	           }, 
//	           mergeCombiners);

       //其实，distinct 基于 reduceByKey实现
       
//        mapRdd1.distinct();
       
        ctx.stop(); 
    }
}
