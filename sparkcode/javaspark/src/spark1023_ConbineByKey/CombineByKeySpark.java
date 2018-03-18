package spark1023_ConbineByKey;

import java.util.ArrayList;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class CombineByKeySpark {
	public static void main(String[] args) {
		
		SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.setAppName("JoinOpt");
    	conf.set("spark.testing.memory", "2147480000");
    

    	JavaSparkContext ctx = new JavaSparkContext(conf);
    	//创建RDD：1）通过读取外部存储 ----- 集群环境使用 2）通过内存中的集合

    	List<Tuple2<String,Integer>> users = new ArrayList<Tuple2<String,Integer>>();
    	Tuple2<String,Integer> user1 = new Tuple2( "zhangsan",3);
    	Tuple2<String,Integer> user2 = new Tuple2( "lisi",5);
    	Tuple2<String,Integer> user3 = new Tuple2( "wangwu",2);
    	Tuple2<String,Integer> user4 = new Tuple2( "zhaoliu",3);
    	Tuple2<String,Integer> user5 = new Tuple2("lisi",9);
    	users.add(user1);
    	users.add(user2);
    	users.add(user3);
    	users.add(user4);
    	users.add(user5);
    	
    	//直接使用键值对转换List的Tuple2位键值对
    	JavaPairRDD<String,Integer> usersRdd = ctx.parallelizePairs(users,2);
    	
//    	JavaRDD<Tuple2<String, Integer>> rdd1 = ctx.parallelize(data, 2);
//
//        JavaPairRDD<String, Integer> mapRdd = rdd1.mapToPair(
//      		  new PairFunction<Tuple2<String, Integer>, String, Integer>() {  
//  	      @Override
//  	      public Tuple2<String, Integer> call(Tuple2<String, Integer> t) throws Exception {  
//  	          return t;
//  	      }
//        });
    	
    	@SuppressWarnings("serial")
		JavaPairRDD<String, Tuple2<Integer, Integer>> mapPairRDD=usersRdd.combineByKey(
    			new Function<Integer,Tuple2<Integer, Integer>>() {
    					public Tuple2<Integer, Integer> call(Integer v1){
    						return new Tuple2<Integer, Integer>(v1, 1);
    					}
		}
    	,new Function2<Tuple2<Integer,Integer>, Integer, Tuple2<Integer,Integer>>() {
    		@Override
			public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> v2, Integer v1) {
				return new Tuple2<Integer, Integer>(v2._1+v1, v2._2+1);
			}
		} 
    	,new Function2<Tuple2<Integer,Integer>, Tuple2<Integer,Integer>, Tuple2<Integer,Integer>>() {

			@Override
			public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> arg0, Tuple2<Integer, Integer> arg1) {
				return new Tuple2<Integer, Integer>(arg0._1+arg1._1, arg0._2+arg1._2);
			}
		});
    	
    	mapPairRDD.foreach(x->System.out.println(x));
    	
		
	}

}
