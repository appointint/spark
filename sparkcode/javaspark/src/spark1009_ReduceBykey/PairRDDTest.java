package spark1009_ReduceBykey;


import java.util.ArrayList;
import java.util.List;




import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class PairRDDTest {
    public static void main(String[] args){
    	SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.setAppName("WordCounter");
    	conf.set("spark.testing.memory", "2147480000");
    	
    	JavaSparkContext ctx = new JavaSparkContext(conf);
    	//����RDD��1��ͨ����ȡ�ⲿ�洢 ----- ��Ⱥ����ʹ�� 2��ͨ���ڴ��еļ���

    	List<String> lines = new ArrayList<String>();
    	lines.add("Hello How are you");
    	lines.add("No Fine thanks a lot ddddddddddd");
    	lines.add("Good are ok");
    	JavaRDD<String> rdd1 = ctx.parallelize(lines);

    	//ӳ���һ����ֵ�� �����ǵ�һ������
//    	PairFunction<String, String, String> keyData =
//			new PairFunction<String, String, String>() {
//			public Tuple2<String, String> call(String x) {
//			    return new Tuple2(x.split(" ")[0], x);
//			}
//    	};
    	JavaPairRDD<String, String> pairs = rdd1.mapToPair(
    			               x -> new Tuple2(x.split(" ")[0], x));
    	pairs.foreach((x)->System.out.println(x));
    	
    	//�ҳ����ȴ���20���ַ���
//    	Function<Tuple2<String, String>, Boolean> longWordFilter =
//    			new Function<Tuple2<String, String>, Boolean>() {
//					@Override
//					public Boolean call(Tuple2<String, String> v1)
//							throws Exception {
//						return (v1._2().length() > 20);
//					}
//    	        };
    	JavaPairRDD<String, String> result = pairs.filter(x -> x._2().length() > 20);
    	result.foreach((x)->System.out.println(x));
    }
}
