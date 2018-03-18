package spark1101_Space;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

/**
 * ʵ�������ù㲥���к��������ˣ� ����µ����� �����Ƿ��ڹ㲥����-�������ڣ��Ӷ�ʵ�ֹ������ݡ�
 */
public class BroadCastDemo {
	/**
	 * ����һ��List�Ĺ㲥����
	 *
	 */
	private static volatile Broadcast<List<String>> broadcastList = null;
	/**
	 * ��������
	 */
	private static volatile Accumulator<Integer> accumulator = null;

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("WordCountOnlineBroadcast");
		    	conf.set("spark.testing.memory", "2147480000");
		JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(5));

		/**
		 * ע�⣺�ַ��㲥��Ҫһ��action���������� ע�⣺�㲥����Arrays��asList
		 * ���Ƕ�������á��㲥Array����Ķ������û���� ʹ��broadcast�㲥��������ÿ��Executor�У�
		 */
		broadcastList = jsc.sparkContext().broadcast(Arrays.asList("Hadoop", "Mahout", "Hive"));
		/**
		 * �ۼ�����Ϊȫ�ּ�����������ͳ�����߹����˶��ٸ��������� ������ʵ������
		 */
		accumulator = jsc.sparkContext().accumulator(0, "OnlineBlackListCounter");

		JavaReceiverInputDStream<String> lines = jsc.socketTextStream("Master", 9999);

		/**
		 * ����ʡȥflatmap��Ϊ������һ�����ģ�
		 */
		JavaPairDStream<String, Integer> pairs = lines.mapToPair(new PairFunction<String, String, Integer>() {
			@Override
			public Tuple2<String, Integer> call(String word) {
				return new Tuple2<String, Integer>(word, 1);
			}
		});
		JavaPairDStream<String, Integer> wordsCount = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
			@Override
			public Integer call(Integer v1, Integer v2) {
				return v1 + v2;
			}
		});
		/**
		 * Funtion���� ǰ���������� ��Ρ� ����ĳ��Ρ� ������call�������棡
		 *
		 */
//		wordsCount.foreach(new Function2<JavaPairRDD<String, Integer>,Time,boolean>() {
//			@Override
//			public boolean call(JavaPairRDD<String, Integer> rdd, Time time) throws Exception {
//				rdd.filter(new Function<Tuple2<String, Integer>, Boolean>() {
//					@Override
//					public Boolean call(Tuple2<String, Integer> wordPair) throws Exception {
//						if (broadcastList.value().contains(wordPair._1)) {
//							/**
//							 * accumulator���������������� ����ͬʱд�����ݿ���߻����С�
//							 */
//							accumulator.add(wordPair._2);
//							return false;
//						} else {
//							return true;
//						}
//					};
//					/**
//					 * �㲥�ͼ�������ִ�У���Ҫ����һ��action������
//					 */
//				}).collect();
//				System.out.println("�㲥�������ֵ" + broadcastList.value());
//				System.out.println("��ʱ�������ֵ" + accumulator.value());
//				return null;
//			}
//		});

		jsc.start();
		try {
			jsc.awaitTermination();
		} catch (InterruptedException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		jsc.close();
	}
}