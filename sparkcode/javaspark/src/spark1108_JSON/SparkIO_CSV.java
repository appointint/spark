package spark1108_JSON;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;

import scala.Tuple2;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class SparkIO_CSV {
    public static void main(String[] args) {
    	SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("SparkIO").set("spark.testing.memory", "2147480000");
		JavaSparkContext sc = new JavaSparkContext(conf);
		sc.setLogLevel("WARN");
		readCsv2(sc);
		sc.stop();
		sc.close();
	}

    static void readCsv1(JavaSparkContext sc) {
    	JavaRDD<String> csvFile1 = sc.textFile("file:///d:/jsontext/csvsong.csv");
//    	csvFile1.foreach(x->System.out.println(x));
    	
    	
    	JavaRDD<String[]> csvData = csvFile1.map(new ParseLine());
//    	JavaRDD<String[]> csvData = csvFile1.map(new Function<String, String[]>(){
//
//			@Override
//			public String[] call(String line) throws Exception {
//				 CSVReader reader = new CSVReader(new StringReader(line));
//		    	 String[] lineData = reader.readNext();
//		    	 reader.close();    //关闭流资源
////	   	    	String[] lineData =line.split(","); //这样还有
//	   	    	return lineData;
//			}
//    		
//    	});
    	
    	csvData.foreach(x->{
	    	                  for(String s : x){
	    	                      System.out.println(s);
	    	                  }
    	                   }
    	               );
    }

    static void writeCsv1(JavaSparkContext sc) {
    	JavaRDD<String> csvFile1 = sc.textFile("file:///d:/jsontext/csvsong.csv");
    	JavaRDD<String[]> parsedData = csvFile1.map(new ParseLine());
    	parsedData = parsedData.filter(x->x[2].equals("怀旧专辑"));  //过滤   如果在这里存文件的话，存的是数组类型的对象
    	parsedData.foreach(
    			            x->{
    			                long id = Thread.currentThread().getId();
					            System.out.println("在线程 "+ id +" 中" + "打印当前数据元素：");
					            for(String s : x){
					                System.out.print(s+ " ");
					            }
					            System.out.println();
                            }
                        );
    	parsedData.map(x->{
    		 StringWriter stringWriter = new StringWriter();
    		 CSVWriter csvWriter = new CSVWriter(stringWriter);
    		 csvWriter.writeNext(x);  //把数组转换成为CSV的格式
    		 csvWriter.close();
    		 return stringWriter.toString();
    	}).saveAsTextFile("file:///d:/jsontext/csvout");
    }
    
    public static class ParseLine implements Function<String, String[]> {
   	    public String[] call(String line) throws Exception {
	    	 CSVReader reader = new CSVReader(new StringReader(line));
	    	 String[] lineData = reader.readNext();
	    	 reader.close();    //关闭流资源
//   	    	String[] lineData =line.split(","); //这样还有
   	    	return lineData;
   	    }
    }

    static void readCsv2(JavaSparkContext sc){
    	//如果文件中有断行，wholetextfile可以跳行
    	   JavaPairRDD<String, String> csvData = sc.wholeTextFiles("d:/jsontext/csvsong.csv");
    	   JavaRDD<String[]> keyedRDD = csvData.flatMap(new ParseLineWhole());
    	   keyedRDD.foreach(x->
				    	       {
					               for(String s : x){
					                   System.out.println(s);
					               }
				               }
                           );
    }
    public static class ParseLineWhole implements FlatMapFunction<Tuple2<String, String>, String[]> {
	    public Iterator<String[]> call(Tuple2<String, String> file) throws Exception {
		    CSVReader reader = new CSVReader(new StringReader(file._2()));
		    Iterator<String[]> data = reader.readAll().iterator();
		    reader.close();
		    return data;
        }
    }
}

/*
"上海滩","叶丽仪","香港电视剧主题歌","mp3/shanghaitan.mp3"
"一生何求","陈百强","香港电视剧主题歌","mp3/shanghaitan.mp3"
"红日","李克勤","怀旧专辑","mp3/shanghaitan.mp3"
"爱如潮水","张信哲","怀旧专辑","mp3/airucaoshun.mp3"
"红茶馆","陈惠嫻","怀旧专辑","mp3/redteabar.mp3"   
 */