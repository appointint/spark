package spark1101_Space;

import java.util.ArrayList;
import java.util.List;
import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;
public class AccumulatorDemo {
    public static void main(String[] xx){
    	SparkConf conf = new SparkConf();
    	conf.setMaster("local[4]");
    	conf.setAppName("WordCounter");
    	conf.set("spark.default.parallelism", "4");
    	conf.set("spark.testing.memory", "2147480000");
    	JavaSparkContext ctx = new JavaSparkContext(conf);    	
    	Person[] persons = new Person[10000];
    	Broadcast<Person []> persons_br =  ctx.broadcast(persons);    	
    	Accumulator<Integer> count = ctx.accumulator(0);
    	List<String> data1 = new ArrayList<String>();
    	data1.add("Cake");
    	data1.add("Bread");
    	data1.add("");
    	data1.add("Cheese");
    	data1.add("Milk");    	
    	data1.add("Toast");
    	data1.add("Bread");
    	data1.add("");
    	data1.add("Egg");
    	data1.add("");
    	JavaRDD<String> rdd1 = ctx.parallelize(data1, 2);
    	System.out.println(rdd1.glom().collect());
    	
    	rdd1.mapToPair(new PairFunction<String, String, Integer>() {
			@Override 
            public Tuple2<String, Integer> call(String s) throws Exception {
				long id = Thread.currentThread().getId();
				System.out.println("s:" + s + " in thread:" + id);
				if(s.equals("")){
					count.add(1);
				}
                return new Tuple2<String, Integer>(s, 1);  
            }
		}).collect();
    	System.out.println(count.value());
    	
    	rdd1.mapToPair(new PairFunction<String, String, Integer>() {
			@Override 
            public Tuple2<String, Integer> call(String s) throws Exception {
				long id = Thread.currentThread().getId();
				System.out.println("s:" + s + " in thread:" + id);
				if(s.equals("")){
					count.add(1);
//			    	System.out.println("c:"+count.value());±¨´í
				}
				System.out.println(persons_br.value().length);
                return new Tuple2<String, Integer>(s, 1);  
            }
		}).collect();

    	System.out.println(count.value());

    	ctx.stop();
    }
}
class Person{}