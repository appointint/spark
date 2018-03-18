package spark1108_JSON;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;

import scala.Tuple2;
import au.com.bytecode.opencsv.CSVReader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SparkIO_JSON {
    public static void main(String[] args) {
    	SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("SparkIO").set("spark.testing.memory", "2147480000");
		JavaSparkContext sc = new JavaSparkContext(conf);
		sc.setLogLevel("WARN");
		readJsonTest(sc);
		sc.stop();
		sc.close();
	}

    //读JSON
    static void readJsonTest(JavaSparkContext sc){
    	//如果json文件中断了行就读不出来了，没截断的部分任然会显示
    	JavaRDD<String> input = sc.textFile("file:///d:/jsontext/jsonsong.json");
    	//使用wholetextfile就不会有断行的错误，因为读的是整个文件 
//    	JavaRDD<String> input = sc.wholeTextFiles("file:///d:/jsontext/jsonsong.json");
    	JavaRDD<Mp3Info> result = input.mapPartitions(new ParseJson());
//    	JavaRDD<Mp3Info> result = input.map(x->{
//    		ObjectMapper mapper=new ObjectMapper();
//    		return mapper.readValue(x, Mp3Info.class);
//    	});
    	result.foreach(x->System.out.println(x));
    }
    //写JSON
    static void writeJsonTest(JavaSparkContext sc){
    	JavaRDD<String> input = sc.textFile("file:///d:/jsontext/jsonsong.json");
    	JavaRDD<Mp3Info> result = input.mapPartitions(new ParseJson()).
    			                      filter(
    			                          x->x.getAlbum().equals("怀旧专辑")
    			                      );
//    	JavaRDD<String> formatted = result.mapPartitions(new WriteJson());
    	JavaRDD<String> formatted = result.map(x->{
    		ObjectMapper mapper=new ObjectMapper();
    		return mapper.writeValueAsString(x);
    	});
    	result.foreach(x->System.out.println(x));
    	formatted.saveAsTextFile("file:///d:/jsontext/jsonsongout");
    }
}

class ParseJson implements FlatMapFunction<Iterator<String>, Mp3Info>, Serializable {
	public Iterator<Mp3Info> call(Iterator<String> lines) throws Exception {
		ArrayList<Mp3Info> people = new ArrayList<Mp3Info>();
		ObjectMapper mapper = new ObjectMapper();
		while (lines.hasNext()) {
			String line = lines.next();
			try {
				people.add(mapper.readValue(line, Mp3Info.class));
			} catch (Exception e) {
			    e.printStackTrace();
			}
		}
		return people.iterator();
	}
}

class WriteJson implements FlatMapFunction<Iterator<Mp3Info>, String> {
	public Iterator<String> call(Iterator<Mp3Info> song) throws Exception {
		ArrayList<String> text = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		while (song.hasNext()) {
			Mp3Info person = song.next();
		    text.add(mapper.writeValueAsString(person));
		}
		return text.iterator();
	}
}

class Mp3Info implements Serializable{
	/*
{"name":"上海滩","singer":"叶丽仪","album":"香港电视剧主题歌","path":"mp3/shanghaitan.mp3"}
{"name":"一生何求","singer":"陈百强","album":"香港电视剧主题歌","path":"mp3/shanghaitan.mp3"}
{"name":"红日","singer":"李克勤","album":"怀旧专辑","path":"mp3/shanghaitan.mp3"}
{"name":"爱如潮水","singer":"张信哲","album":"怀旧专辑","path":"mp3/airucaoshun.mp3"}
{"name":"红茶馆","singer":"陈惠嫻","album":"怀旧专辑","path":"mp3/redteabar.mp3"}
	 */
	private String name;
    private String album;
    private String path;
    private String singer;

    public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
    @Override
	public String toString() {
		return "Mp3Info [name=" + name + ", album=" 
	             + album + ", path=" + path + ", singer=" + singer + "]";
	}
}

/*
{"name":"上海滩","singer":"叶丽仪","album":"香港电视剧主题歌","path":"mp3/shanghaitan.mp3"}
{"name":"一生何求","singer":"陈百强","album":"香港电视剧主题歌","path":"mp3/shanghaitan.mp3"}
{"name":"红日","singer":"李克勤","album":"怀旧专辑","path":"mp3/shanghaitan.mp3"}
{"name":"爱如潮水","singer":"张信哲","album":"怀旧专辑","path":"mp3/airucaoshun.mp3"}
{"name":"红茶馆","singer":"陈惠嫻","album":"怀旧专辑","path":"mp3/redteabar.mp3"}
 */