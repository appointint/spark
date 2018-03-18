package spark1009_Partition;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.spark.Partitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class PairSame {

	public static void main(String[] args) {
		
		SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.setAppName("WordCounter").set("spark.testing.memory", "2147480000");

    	JavaSparkContext ctx = new JavaSparkContext(conf);
    	//创建RDD：1）通过读取外部存储 ----- 集群环境使用 2）通过内存中的集合

    	List<String> lines1 = new ArrayList<String>();
    	lines1.add("http://www.baidu.com/about.html");
    	lines1.add("http://www.ali.com/index.html");
    	lines1.add("http://www.sina.com/first.html");
    	lines1.add("http://www.sohu.com/index.html");
    	lines1.add("http://www.baidu.com/index.jsp");
    	lines1.add("http://www.sina.com/help.html");
    	
    	JavaRDD<String> ones=ctx.parallelize(lines1,3);
    	
    	JavaPairRDD<String, Integer> two=ones.mapToPair(x->new Tuple2<String, Integer>(x, 1));
    	
    	JavaPairRDD<String, Integer> twonews = two.partitionBy(new Mypartitioner());
    	
    	System.out.println("twonews:"+twonews.getNumPartitions());
    	
    	 JavaPairRDD<String, Integer> counts = two.reduceByKey((x,y)->x+y);
    	
    	 List<Tuple2<String,Integer>> results=counts.collect();
    	 System.out.println(results.toString());
    	 
    	 Scanner scanner=new Scanner(System.in);
    	 scanner.next();
    	 scanner.close();
    	 
    	 ctx.stop();

	}
	
	static class Mypartitioner extends Partitioner{

		@Override
		public int getPartition(Object key) {
			
			String xString=key.toString();
			String []  list=xString.split(".");
			
			if(list[2]=="baidu"){
				return 0;
			}else if(list[2]=="ali"){
				return 1;
				
			}else if(list[2]=="souhu"){
				return 2;
			}else{
				return 3;
			}
		}

		@Override
		public int numPartitions() {
			// TODO 自动生成的方法存根
			return 4;
		}
		
	}

}
