package spark1030_unoion;
import java.util.Iterator;

import org.apache.spark.SparkConf;  
import org.apache.spark.api.java.JavaPairRDD;  
import org.apache.spark.api.java.JavaRDD;  
import org.apache.spark.api.java.JavaSparkContext;  
import org.apache.spark.api.java.function.FlatMapFunction;  
import org.apache.spark.api.java.function.Function2;  
import org.apache.spark.api.java.function.PairFunction;  
import org.apache.spark.storage.StorageLevel;

import scala.Tuple2;  
  


import java.util.Arrays;  
import java.util.Iterator;
import java.util.Scanner;


public class CheckpointDemo {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("wordcount").set("spark.testing.memory", "2147480000");
        JavaSparkContext ctx = new JavaSparkContext(sparkConf);
        ctx.setCheckpointDir("file:///d:/checkpoint");
        final JavaRDD<String> lines = ctx.textFile("words.txt");

        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
            	System.out.println("flatMap...");
                return Arrays.asList(s.split(" ")).iterator();
            }
        });
        
        JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {  
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {  
            	System.out.println("mapToPair...");
                return new Tuple2<String, Integer>(s, 1);
            }
        });
        ones.checkpoint();            //…Ë÷√ºÏ≤Èµ„       ’∂∂œ“¿¿µ
        
        JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {  
            @Override  
            public Integer call(Integer integer, Integer integer2) throws Exception {  
            	System.out.println("reduceByKey...");
                return integer + integer2;  
            }
        });
        System.out.println(counts.toDebugString());
//        counts.saveAsTextFile(args[1]);
        counts.foreach(x->System.out.println(x));
        System.out.println("after action:" + counts.toDebugString());
        ctx.stop();
    }  
}
