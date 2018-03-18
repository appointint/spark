package spark1030_unoion;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.HashPartitioner;
import org.apache.spark.Partitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;


public class UnionDemo {
    public static void main(String[] xx){
    	SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.set("spark.testing.memory", "2147480000");
    	conf.setAppName("WordCounter");

    	JavaSparkContext ctx = new JavaSparkContext(conf);
    	//创建RDD：1）通过读取外部存储 ----- 集群环境使用 2）通过内存中的集合

    	List<Tuple2<String,Integer>> urls = new ArrayList<Tuple2<String,Integer>>();
    	urls.add(new Tuple2<String,Integer>("http://www.baidu.com/about.html", 3));
    	urls.add(new Tuple2<String,Integer>("http://www.ali.com/index.html", 2));
    	urls.add(new Tuple2<String,Integer>("http://www.sina.com/first.html", 4));
    	urls.add(new Tuple2<String,Integer>("http://www.sohu.com/index.html", 3));
    	urls.add(new Tuple2<String,Integer>("http://www.baidu.com/index.jsp",7));
    	urls.add(new Tuple2<String,Integer>("http://www.sina.com/help.html",1));
    	
    	JavaPairRDD<String, Integer> urlRdd1 = ctx.parallelizePairs(urls,2);
//    	JavaPairRDD<String, Integer> urlRdd1 = ctx.parallelizePairs(urls).
//    			                         partitionBy(new HashPartitioner(2));
    	
    	
    	System.out.println("urlRdd1:" + urlRdd1.partitioner());
    	System.out.println("urlRdd1:" + urlRdd1.glom().collect());

    	List<Tuple2<String,Integer>> anotherUrls = new ArrayList<Tuple2<String,Integer>>();
    	anotherUrls.add(new Tuple2<String,Integer>("http://www.163.com/about.html", 3));
    	anotherUrls.add(new Tuple2<String,Integer>("http://www.taobao.com/index.html", 2));
    	anotherUrls.add(new Tuple2<String,Integer>("http://www.sina.com/first.html", 4));
    	anotherUrls.add(new Tuple2<String,Integer>("http://www.csdn.com/index.html", 3));
    	anotherUrls.add(new Tuple2<String,Integer>("http://www.facebook.com/index.jsp",7));
    	anotherUrls.add(new Tuple2<String,Integer>("http://www.sina.com/help.html",1));
    	
    	JavaPairRDD<String, Integer> urlRdd2 = ctx.parallelizePairs(anotherUrls,2);
//    	JavaPairRDD<String, Integer> urlRdd2 = ctx.parallelizePairs(anotherUrls).
//    			                      partitionBy(new HashPartitioner(3));
    	System.out.println("urlRdd2:" + urlRdd2.partitioner());
    	System.out.println("urlRdd2:" + urlRdd2.glom().collect());
    	
    	//当设置了分区器和分区数相同，则union之后的分区是一样的
    	//若分区器没有设置，就算分区数相同，则union之后的分区是两分区之和
    	
    	JavaPairRDD<String, Integer> rdd3 = urlRdd1.union(urlRdd2);
    	System.out.println("rdd3:" + rdd3.partitioner());
    	System.out.println("rdd3:" + rdd3.getNumPartitions());
    	System.out.println("urlRdd3:" + rdd3.glom().collect());
    	
    	rdd3.foreach(x->System.out.println(x));
    	
    }
}
