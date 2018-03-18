package spark1101_Space;
import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;  
import org.apache.spark.api.java.JavaPairRDD;  
import org.apache.spark.api.java.JavaRDD;  
import org.apache.spark.api.java.JavaSparkContext;  
import org.apache.spark.api.java.function.FlatMapFunction;  
import org.apache.spark.api.java.function.Function2;  
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.broadcast.Broadcast;

import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;  
import java.util.Iterator;

public class SpacePrint {  
    //private static final Pattern SPACE = Pattern.compile(" ");  

    public static int number=0;

	@SuppressWarnings({ "serial", "deprecation" })
	public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("wordcount").set("spark.testing.memory", "2147480000");  

        JavaSparkContext ctx = new JavaSparkContext(sparkConf);
        final JavaRDD<String> lines = ctx.textFile("words.txt");
        lines.foreach(x->System.out.println(x));
        
        System.out.println(lines.filter(s->s.equals("")).count());              //5
        
        Accumulator<Integer> accumulator = ctx.accumulator(0);                  //累加器
        String[]strings=new String[10000];
        Broadcast<String[]> broadcast=ctx.broadcast(strings);                  //广播
        
        final Counter c  =  new Counter();

        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @SuppressWarnings("deprecation")
			@Override
            public Iterator<String> call(String s) throws Exception {
            	System.out.println("flatmap...");
            	if(s.equals("") || s.length() == 0){
            		//return Arrays.asList("#").iterator();
            		c.count++;
            		SpacePrint.number++;
            		
            		accumulator.add(1);
            		
            		System.out.println(c.value());
//            		1   2   3   4   5
//            		只能在执行节点取值，不能在驱动器取值，没有回返到驱动器。
            		
//            		System.out.println(accumulator.value());
//            		accumulator不能在执行节点取值，只能在驱动器取值，（只写）            
            	}

        		System.out.println("broadcast:"+broadcast.value().length);
//        		广播是只读的，
        		
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
  
        counts.foreach(x->System.out.println(x));
      System.out.println("空行的行数Counter：" + Counter.count   +"..."+"number: "+number+"...Accumulator:"+accumulator);
      
      //而static count在一个独立的jvm中，没有独立的进程，只有独立的线程，所以没有多个节点           
      
      //主方法是一个driver   flatmap和maptopair和reducebykey分别在三台节点上跑，c在task内部计算，没有返回主程序（回传），不在同一个空间
      //比如形参和实参使用来交换值
      
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