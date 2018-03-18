package spark1009_ReduceBykey;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.Partitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;


public class Test1 {
    public static void main(String[] args){
    	List<Integer> data = Arrays.asList(1, 2, 2, 2,4, 3, 4, 5, 6, 1, 7);
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("wordcount");  
        JavaSparkContext ctx = new JavaSparkContext(sparkConf);    	
    	JavaRDD<Integer> javaRDD = ctx.parallelize(data);

    	//转化为K，V格式
    	JavaPairRDD<Integer,Integer> javaPairRDD = javaRDD.mapToPair(new PairFunction<Integer, Integer, Integer>() {    
    	    @Override    
    	    public Tuple2<Integer, Integer> call(Integer integer) throws Exception {        
    	      return new Tuple2<Integer, Integer>(integer,1);  
    	  }
    	});
//    	JavaPairRDD<Integer,Integer> reduceByKeyRDD = javaPairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {    
//    	    @Override      
//    	    public Integer call(Integer v1, Integer v2) throws Exception {        
//    	      return v1 + v2;
//    	  }
//    	});
//    	System.out.println(reduceByKeyRDD.collect());

    	//指定numPartitions
    	JavaPairRDD<Integer,Integer> reduceByKeyRDD2 = javaPairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {    
    	    @Override    
    	    public Integer call(Integer v1, Integer v2) throws Exception {        
    	      return v1 + v2;    
    	  }
    	},  2);
    	System.out.println(reduceByKeyRDD2.collect());

    	//自定义partition
    	JavaPairRDD<Integer,Integer> reduceByKeyRDD4 = javaPairRDD.reduceByKey(
    			                                new Partitioner() {    
										    	      @Override    
										    	      public int numPartitions() {return 2;}    

										    	      @Override    
										    	      public int getPartition(Object o) {
										    	    	  System.out.println("getPartition() " + o.toString());
										    	          return (o.toString()).hashCode() % numPartitions();    
										    	      }
										    	},
										    	new Function2<Integer, Integer, Integer>() {
										    	    @Override
										    	    public Integer call(Integer v1, Integer v2) throws Exception {    
										    	        return v1 + v2;
										    	    }
										        });
    	System.out.println(reduceByKeyRDD4.collect());

    	ctx.stop();
    	ctx.close();
    }
}
