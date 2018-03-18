package spark1009_conf;

import org.apache.spark.SparkConf;  
import org.apache.spark.api.java.JavaPairRDD;  
import org.apache.spark.api.java.JavaRDD;  
import org.apache.spark.api.java.JavaSparkContext;  
import org.apache.spark.api.java.function.FlatMapFunction;  
import org.apache.spark.api.java.function.Function2;  
import org.apache.spark.api.java.function.PairFunction;  

import scala.Tuple2;  
  
import java.util.Arrays;  
import java.util.Iterator;
import java.util.List;


public class SparkWordCount {  
    //private static final Pattern SPACE = Pattern.compile(" ");  

    public static void main(String[] args) {
        String srcPath = null;
        String desPath = null;
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("wordcount");
//        		.set("spark.testing.memory", "2147480000");  

        sparkConf.set("spark.default.parallelism", "4");
        
        JavaSparkContext ctx = new JavaSparkContext(sparkConf);
        
        final JavaRDD<String> lines = ctx.textFile("words.txt");
        
        System.out.println("lines:"+lines.partitioner());
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
        
        System.out.println("ones:"+ones.partitioner());
        System.out.println("ones:"+ones.getNumPartitions());
 /*       JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {  
            @Override  
            public Tuple2<String, Integer> call(String s) throws Exception {  
                return new Tuple2<String, Integer>(s, 1);  
            }  
        });  
*/
        JavaPairRDD<String, Integer> counts = ones.reduceByKey((x,y)->x+y);
        
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
}  