package spark1009_conf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

public class WordCountOnSpark2 {

public static void main(String[] args) {
    SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCountTest").set("spark.testing.memory", "2147480000");
    JavaSparkContext sc = new JavaSparkContext(conf);
    String outputDir=args[0];

    List<String> list=new ArrayList<String>();
    list.add("1 1 2 a b");
    list.add("a b 1 2 3");
    JavaRDD<String> RddList=sc.parallelize(list);

    //���з�Ϊ���ʣ���ƽ������
    JavaRDD<String> flatMapRdd = RddList.flatMap(new FlatMapFunction<String, String>() {
        @Override
        public Iterator<String> call(String str) {
            return (Iterator<String>) Arrays.asList(str.split(" "));
        }
    });

    //��ת��Ϊ��ֵ��
    JavaPairRDD<String, Integer> pairRdd = flatMapRdd.mapToPair(new PairFunction<String, String, Integer>() {
        public Tuple2<String, Integer> call(String word) throws Exception {
            return new Tuple2<String, Integer>(word, 1);
        }
    });

    //��ÿ��������м���
    JavaPairRDD<String, Integer> countRdd = pairRdd.reduceByKey(new Function2<Integer, Integer, Integer>() {
        @Override
        public Integer call(Integer i1, Integer i2) {
            return i1 + i2;
        }
    });
    System.out.println("�����"+countRdd.collect());
//    countRdd.saveAsTextFile(outputDir);
    sc.close();
}
}