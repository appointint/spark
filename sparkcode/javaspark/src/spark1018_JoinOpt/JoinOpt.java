package spark1018_JoinOpt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.spark.HashPartitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;

import scala.Tuple2;


public class JoinOpt {
    public static void main(String[] xx){
    	SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.setAppName("JoinOpt");
    	conf.set("spark.testing.memory", "2147480000");
    

    	JavaSparkContext ctx = new JavaSparkContext(conf);
    	//创建RDD：1）通过读取外部存储 ----- 集群环境使用 2）通过内存中的集合

    	List<Tuple2<String,String>> users = new ArrayList<Tuple2<String,String>>();
    	Tuple2<String,String> user1 = new Tuple2("1121", "zhangsan");
    	Tuple2<String,String> user2 = new Tuple2("1221", "lisi");
    	Tuple2<String,String> user3 = new Tuple2("1221", "wangwu");
    	Tuple2<String,String> user4 = new Tuple2("1241", "zhaoliu");
    	Tuple2<String,String> user5 = new Tuple2("1221", "Tianqi");
    	users.add(user1);
    	users.add(user2);
    	users.add(user3);
    	users.add(user4);
    	users.add(user5);
    	
    	//直接使用键值对转换List的Tuple2位键值对
    	JavaPairRDD<String,String> usersRdd = ctx.parallelizePairs(users);
    	usersRdd.partitionBy(new HashPartitioner(3)).persist(StorageLevel.MEMORY_ONLY());
    	//将键值对进行预分区，分为3个区，然后在数据持久化缓存。
        JavaPairRDD<String,String> partitionedUserRdd = usersRdd.partitionBy(new HashPartitioner(3)).persist(StorageLevel.MEMORY_ONLY());
    	
    	List<Tuple2<String,String>> events = new ArrayList<Tuple2<String,String>>();
    	Tuple2<String,String> t1 = new Tuple2("1221", "http://www.baidu.com/about.html");
    	Tuple2<String,String> t2 = new Tuple2("1223", "http://www.ali.com/index.html");
    	Tuple2<String,String> t3 = new Tuple2("1241", "http://www.sohu.com/index.html");
    	Tuple2<String,String> t4 = new Tuple2("1231", "http://www.baidu.com/help.html");
    	Tuple2<String,String> t5 = new Tuple2("1121", "http://www.baidu.com/about.html");
        events.add(t1);
        events.add(t2);
        events.add(t3);
        events.add(t4);
        events.add(t5);    	
        JavaPairRDD<String,String> eventsRdd = ctx.parallelizePairs(events);
        
        
        while(true){
        	
        	//将预分区后的user进行join为窄依赖，在与url宽依赖的join操作
            JavaPairRDD<String, Tuple2<String, String>> joinedRdd  = partitionedUserRdd.join(eventsRdd);
            System.out.println(joinedRdd.partitioner());
            System.out.println(joinedRdd.collect());
            try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
       
        
    }
}
