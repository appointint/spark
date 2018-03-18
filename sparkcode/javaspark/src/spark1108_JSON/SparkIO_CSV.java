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
//		    	 reader.close();    //�ر�����Դ
////	   	    	String[] lineData =line.split(","); //��������
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
    	parsedData = parsedData.filter(x->x[2].equals("����ר��"));  //����   �����������ļ��Ļ���������������͵Ķ���
    	parsedData.foreach(
    			            x->{
    			                long id = Thread.currentThread().getId();
					            System.out.println("���߳� "+ id +" ��" + "��ӡ��ǰ����Ԫ�أ�");
					            for(String s : x){
					                System.out.print(s+ " ");
					            }
					            System.out.println();
                            }
                        );
    	parsedData.map(x->{
    		 StringWriter stringWriter = new StringWriter();
    		 CSVWriter csvWriter = new CSVWriter(stringWriter);
    		 csvWriter.writeNext(x);  //������ת����ΪCSV�ĸ�ʽ
    		 csvWriter.close();
    		 return stringWriter.toString();
    	}).saveAsTextFile("file:///d:/jsontext/csvout");
    }
    
    public static class ParseLine implements Function<String, String[]> {
   	    public String[] call(String line) throws Exception {
	    	 CSVReader reader = new CSVReader(new StringReader(line));
	    	 String[] lineData = reader.readNext();
	    	 reader.close();    //�ر�����Դ
//   	    	String[] lineData =line.split(","); //��������
   	    	return lineData;
   	    }
    }

    static void readCsv2(JavaSparkContext sc){
    	//����ļ����ж��У�wholetextfile��������
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
"�Ϻ�̲","Ҷ����","��۵��Ӿ������","mp3/shanghaitan.mp3"
"һ������","�°�ǿ","��۵��Ӿ������","mp3/shanghaitan.mp3"
"����","�����","����ר��","mp3/shanghaitan.mp3"
"���糱ˮ","������","����ר��","mp3/airucaoshun.mp3"
"����","�»݋�","����ר��","mp3/redteabar.mp3"   
 */