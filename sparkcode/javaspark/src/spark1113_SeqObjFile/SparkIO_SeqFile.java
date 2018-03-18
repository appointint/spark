package spark1113_SeqObjFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.serializer.KryoSerializer;

import scala.Tuple2;

public class SparkIO_SeqFile {
    public static void main(String[] args) {
    	//���̣߳����������߳�
    	SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("SparkIO")
    			.set("spark.testing.memory", "2147480000")
    			.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
		JavaSparkContext sc = new JavaSparkContext(conf);
		sc.setLogLevel("WARN");
		//sequenceFile��ȡ���Ǽ�ֵ�ԣ������л��ı��ļ���������ת��Ϊ��������ʽ��
		writeSeqFile(sc);
		readSeqFile(sc);
		sc.stop();
		sc.close();
	}
    
	private static void writeSeqFile(JavaSparkContext sc) {
	    List<Tuple2<String, Integer>> data = new ArrayList<Tuple2<String, Integer>>();
	    data.add(new Tuple2<String, Integer>("ABC", 1));
	    data.add(new Tuple2<String, Integer>("DEF", 3));
	    data.add(new Tuple2<String, Integer>("GHI", 2));
	    data.add(new Tuple2<String, Integer>("JKL", 4));
	    data.add(new Tuple2<String, Integer>("ABC", 1));

//	    JavaPairRDD<String, Integer> rdd1 = sc.parallelizePairs(Arrays.asList(("d",1)),1);
	    
	    //���÷��������ж��ٸ����������ж��ٸ�����ļ�
	    JavaPairRDD<String, Integer> rdd = sc.parallelizePairs(data, 1);
	    String dir = "file:///D:jsontext/sequenceFile";
	    //sequenceFile����ֵ��ʹ��maptoPairװ��Ϊ�ı����͵ļ�ֵ��
	    JavaPairRDD<Text, IntWritable> result = rdd.mapToPair(new ConvertToWritableTypes());
	    //�ĸ��������ļ����������ֵ�Ե����ͣ������ʽ          saveAsNewAPIHadoopFile���½ӿ�
	    result.saveAsNewAPIHadoopFile(dir, Text.class, IntWritable.class, SequenceFileOutputFormat.class);
	    
	}

	static class ConvertToWritableTypes implements PairFunction<Tuple2<String, Integer>, Text, IntWritable> {
		public Tuple2<Text, IntWritable> call(Tuple2<String, Integer> record) {
		    return new Tuple2<Text, IntWritable>(new Text(record._1), new IntWritable(record._2));
	    }
    }
	private static void readSeqFile(JavaSparkContext sc) {
		
		//��ȡsequenceFile�ļ��������PairRDD�������Һ����ļ����������ֵ������
		JavaPairRDD<Text, IntWritable> input = sc.sequenceFile(
					                               "file:///D:/jsontext/sequenceFile", 
					                               Text.class,
					                               IntWritable.class);
//		input.foreach(System.out::println);
		//����mapToPair���ļ��ļ�ֵ��װ��Ϊstring�ļ�ֵ�����ͣ����
	    JavaPairRDD<String, Integer> result = input.mapToPair(new ConvertToNativeTypes());
	    result.foreach(x->System.out.println(x));
	}

	private static class ConvertToNativeTypes implements PairFunction<Tuple2<Text, IntWritable>, String, Integer> {
	    public Tuple2<String, Integer> call(Tuple2<Text, IntWritable> record) {
	        return new Tuple2<String, Integer>(record._1.toString(), record._2.get());
	    }
	}
	

}


