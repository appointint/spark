package spark1018_JoinOpt;
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
  




import java.util.ArrayList;
import java.util.Arrays;  
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class SparkWordCount {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().
        		                  setMaster("local").
        		                  setAppName("wordcount");
        sparkConf.set("spark.default.parallelism", "4");
        JavaSparkContext ctx = new JavaSparkContext(sparkConf);

        ArrayList<String> lines = new ArrayList<String>();
        lines.add("Hello Java Hi Ok");
        lines.add("Ok No House Hello");
        lines.add("Helo Java Hi Ok");
        lines.add("Ok Yo House Hello");
        lines.add("Helo Java Hi Ok");
        lines.add("Ok House Hello");
        JavaRDD<String> linesRdd = ctx.parallelize(lines, 2);
        System.out.println("linesRdd:" + linesRdd.toDebugString());
        System.out.println("linesRdd:" + linesRdd.getNumPartitions());

        JavaRDD<String> words = linesRdd.flatMap(
                         (s) -> Arrays.asList(s.split(" ")).iterator());

          JavaPairRDD<String, Integer> ones = words.mapToPair(
        		             s->new Tuple2<String, Integer>(s, 1));
          ones.foreach(System.out::println);
          JavaPairRDD<String, Integer> newOnes = ones.partitionBy(new MyPartitioner());
          System.out.println("newOnes:" + newOnes.getNumPartitions());
          System.out.println("newOnes:" + newOnes.partitioner());

        JavaPairRDD<String, Integer> counts =  ones.reduceByKey((x,y)->x+y); 
        List<Tuple2<String, Integer>> results = counts.collect();
        System.out.println(results);
        ctx.close();
    }

    static class MyPartitioner extends Partitioner{
		@Override
		public int getPartition(Object key) {
//			int hash = key.hashCode();
//			int index = hash % numPartitions();
//			if(index < 0){
//				index = 0;
//			}
			int index = 0;
			String url = (String)key;
			if(url.startsWith("http://www.baidu.com/")){
				index = 0;}
//			}else if(){
//				
//			}
			
			return index;
		}

		@Override
		public int numPartitions() {
			return 3;
		}
    }
}  