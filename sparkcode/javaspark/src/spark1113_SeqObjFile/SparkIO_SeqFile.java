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
    	//多线程，开了两个线程
    	SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("SparkIO")
    			.set("spark.testing.memory", "2147480000")
    			.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
		JavaSparkContext sc = new JavaSparkContext(conf);
		sc.setLogLevel("WARN");
		//sequenceFile存取的是键值对，是序列化文本文件（将对象转换为二进制形式）
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
	    
	    //设置分区数，有多少个分区数就有多少个输出文件
	    JavaPairRDD<String, Integer> rdd = sc.parallelizePairs(data, 1);
	    String dir = "file:///D:jsontext/sequenceFile";
	    //sequenceFile将键值对使用maptoPair装换为文本类型的键值对
	    JavaPairRDD<Text, IntWritable> result = rdd.mapToPair(new ConvertToWritableTypes());
	    //四个参数，文件名，输出键值对的类型，输出格式          saveAsNewAPIHadoopFile是新接口
	    result.saveAsNewAPIHadoopFile(dir, Text.class, IntWritable.class, SequenceFileOutputFormat.class);
	    
	}

	static class ConvertToWritableTypes implements PairFunction<Tuple2<String, Integer>, Text, IntWritable> {
		public Tuple2<Text, IntWritable> call(Tuple2<String, Integer> record) {
		    return new Tuple2<Text, IntWritable>(new Text(record._1), new IntWritable(record._2));
	    }
    }
	private static void readSeqFile(JavaSparkContext sc) {
		
		//读取sequenceFile文件，输出到PairRDD，三个惨呼，文件名，输入键值对类型
		JavaPairRDD<Text, IntWritable> input = sc.sequenceFile(
					                               "file:///D:/jsontext/sequenceFile", 
					                               Text.class,
					                               IntWritable.class);
//		input.foreach(System.out::println);
		//调用mapToPair将文件的键值对装换为string的键值对类型，输出
	    JavaPairRDD<String, Integer> result = input.mapToPair(new ConvertToNativeTypes());
	    result.foreach(x->System.out.println(x));
	}

	private static class ConvertToNativeTypes implements PairFunction<Tuple2<Text, IntWritable>, String, Integer> {
	    public Tuple2<String, Integer> call(Tuple2<Text, IntWritable> record) {
	        return new Tuple2<String, Integer>(record._1.toString(), record._2.get());
	    }
	}
	

}


