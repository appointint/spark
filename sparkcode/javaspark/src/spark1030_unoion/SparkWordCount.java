package spark1030_unoion;
import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;  
import org.apache.spark.api.java.JavaPairRDD;  
import org.apache.spark.api.java.JavaRDD;  
import org.apache.spark.api.java.JavaSparkContext;  
import org.apache.spark.api.java.function.FlatMapFunction;  
import org.apache.spark.api.java.function.Function2;  
import org.apache.spark.api.java.function.PairFunction;  

import scala.Tuple2;  
  


import java.io.Serializable;
import java.util.Arrays;  
import java.util.Iterator;

public class SparkWordCount {  
    //private static final Pattern SPACE = Pattern.compile(" ");  

	
	public static int number=0;
	
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("wordcount").set("spark.testing.memory", "2147480000");

        JavaSparkContext ctx = new JavaSparkContext(sparkConf);
        final JavaRDD<String> lines = ctx.textFile("words.txt");
        lines.foreach(x->System.out.println(x));

        Accumulator c = ctx.accumulator(0);
//        final Counter c  =  new Counter();

        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
            	
            	if(s.isEmpty()){
            		SparkWordCount.number++;
            		
            		System.out.println(s);
            	}
            	
            	
                return Arrays.asList(s.split(" ")).iterator();
            }
        });

        JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {  
            @Override  
            public Tuple2<String, Integer> call(String s) throws Exception {  
                return new Tuple2<String, Integer>(s, 1);  
            }  
        });  

        JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {  
            @Override  
            public Integer call(Integer integer, Integer integer2) throws Exception {  
                return integer + integer2;  
            }  
        });  
  
//        counts.filter(x->x!=null).foreach(x->System.out.println(x));
        
        counts.foreach(x->System.out.println(x));
      System.out.println("空行的行数：" + Counter.count+"          "+number+":c:"+c);
        //counts.saveAsTextFile(args[1]);  
        ctx.stop();  
    }  
}

class Counter implements Serializable{
	public static int count;
	
	void add(int i){
		count += i;
	}
	
	int value(){
		return count;
	}
}