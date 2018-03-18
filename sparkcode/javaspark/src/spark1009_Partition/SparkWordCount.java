package spark1009_Partition;

import org.apache.spark.Partitioner;
import org.apache.spark.SparkConf;  
import org.apache.spark.api.java.JavaPairRDD;  
import org.apache.spark.api.java.JavaRDD;  
import org.apache.spark.api.java.JavaSparkContext;  
import org.apache.spark.api.java.function.FlatMapFunction;  
import org.apache.spark.api.java.function.Function2;  
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.storage.StorageLevel;

import scala.Tuple2;
import scala.tools.nsc.interpreter.Javap;

import java.util.ArrayList;
import java.util.Arrays;  
import java.util.Iterator;
import java.util.List;


public class SparkWordCount {  
    //private static final Pattern SPACE = Pattern.compile(" ");  

    public static void main(String[] args) {
        String srcPath = null;
        String desPath = null;
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("wordcount").set("spark.testing.memory", "2147480000");

        sparkConf.set("spark.default.parallelism", "4");
        
        JavaSparkContext ctx = new JavaSparkContext(sparkConf);
       
        ArrayList<String> list=new ArrayList<String>();
        list.add("java shell scala hello");
        list.add("java java sehll python");
        //设置分区数
       JavaRDD<String> lines = ctx.parallelize(list,3);
        
       //显示分区器
        System.out.println("lines:"+lines.partitioner());
        //显示分区数
        System.out.println("lines:"+lines.getNumPartitions());
        
        
        JavaRDD<String> words = lines.flatMap(s->Arrays.asList(s.split(" ")).iterator());

        System.out.println("words:"+words.partitioner());
        System.out.println("words:"+words.getNumPartitions());
        
/*        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        });
*/
        JavaPairRDD<String, Integer> ones = words.mapToPair(s->new Tuple2<String, Integer>(s, 1));
        
        //设置分区器和分区数。
        JavaPairRDD<String, Integer> newone=ones.partitionBy(new Mypartitioner());
        //重分区
        ones.repartition(3);
        
        System.out.println("newone:"+newone.partitioner());
        System.out.println("newone:"+newone.getNumPartitions());
        System.out.println("ones:"+ones.partitioner());
        System.out.println("one:"+newone.toDebugString());
        System.out.println("ones:"+ones.getNumPartitions());
 /*       JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {  
            @Override  
            public Tuple2<String, Integer> call(String s) throws Exception {  
                return new Tuple2<String, Integer>(s, 1);  
            }  
        });  
*/
        JavaPairRDD<String, Integer> counts = ones.reduceByKey((x,y)->x+y);
        //持久化（缓存），可以多次使用RDD
        counts.persist(StorageLevel.MEMORY_ONLY());
        System.out.println("counts:"+counts.partitioner());
        System.out.println("counts:"+counts.getNumPartitions());
 /*       JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {  
            @Override  
            public Integer call(Integer integer, Integer integer2) throws Exception {  
                return integer + integer2;  
            }  
        });  
 */ 
       // counts.saveAsTextFile(args[1]);
        
//        List<Tuple2<String,Integer>> results=counts.collect();
//        System.out.println(results.toString());
        
        counts.foreach((x)->System.out.println(x));
        
//        counts.foreach(System.out::println);
        
        
        ctx.stop();  
	    }
    
	static  class Mypartitioner extends Partitioner{
	
		@Override
		public int getPartition(Object key) {
			int x=key.hashCode();
			int index=x%numPartitions();
			if(index<0){
				index=0;
			}
			return index;
		}
	
		@Override
		public int numPartitions() {
			// TODO 自动生成的方法存根
			return 3;
		}
		
	}
}  












