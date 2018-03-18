package spark1115_SparkSQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.io.Serializable;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import static org.apache.spark.sql.functions.col;

public class SparkSQL {

	public static void main(String[] args) {
		SparkSession spark = SparkSession
				  .builder()				  
				  .appName("Java Spark SQL basic example")
				  .config("spark.some.config.option", "some-value")
				  .master("local")				  
				  .getOrCreate();	

//		runBaseFrameShow(spark);		
//		runDatasetCreationExample(spark);
//		runInferSchemaExample(spark);
		runProgrammaticSchemaExample(spark);
				
		spark.stop();		
	}
	
	  private static void runBaseFrameShow(SparkSession spark){
		
		Dataset<Row> df = spark.read().json("jsonsong.json");

		df.show();
//		+--------+----+-------------------+------+
//		|   album|name|               path|singer|
//		+--------+----+-------------------+------+
//		|香港电视剧主题歌| 上海滩|mp3/shanghaitan.mp3|   叶丽仪|
//		|香港电视剧主题歌|一生何求|mp3/shanghaitan.mp3|   陈百强|
//		|    怀旧专辑|  红日|mp3/shanghaitan.mp3|   李克勤|
//		|    怀旧专辑|爱如潮水|mp3/airucaoshun.mp3|   张信哲|
//		|    怀旧专辑| 红茶馆|  mp3/redteabar.mp3|   陈惠嫻|
//		+--------+----+-------------------+------+
		
		df.printSchema();   //打印表结构
//		root
//		 |-- album: string (nullable = true)
//		 |-- name: string (nullable = true)
//		 |-- path: string (nullable = true)
//		 |-- singer: string (nullable = true)

		// Select only the "name" column
		df.select(col("name")).show();
//		+----+
//		|name|
//		+----+
//		| 上海滩|
//		|一生何求|
//		|  红日|
//		|爱如潮水|
//		| 红茶馆|
//		+----+
	
		// Select everybody, but increment the age by 1
		//.plus(1)   加1
		df.select("album", "path").show();  //选择两行
//		+--------+-------------------+
//		|   album|               path|
//		+--------+-------------------+
//		|香港电视剧主题歌|mp3/shanghaitan.mp3|
//		|香港电视剧主题歌|mp3/shanghaitan.mp3|
//		|    怀旧专辑|mp3/shanghaitan.mp3|
//		|    怀旧专辑|mp3/airucaoshun.mp3|
//		|    怀旧专辑|  mp3/redteabar.mp3|
//		+--------+-------------------+
	
		
		df.filter(col("album").equalTo("怀旧专辑")).show();     //过滤出只有怀旧专辑的row
//		+-----+----+-------------------+------+
//		|album|name|               path|singer|
//		+-----+----+-------------------+------+
//		| 怀旧专辑|  红日|mp3/shanghaitan.mp3|   李克勤|
//		| 怀旧专辑|爱如潮水|mp3/airucaoshun.mp3|   张信哲|
//		| 怀旧专辑| 红茶馆|  mp3/redteabar.mp3|   陈惠嫻|
//		+-----+----+-------------------+------+
		
		df.groupBy("album").count().show();    //分组计数
//		+--------+-----+
//		|   album|count|
//		+--------+-----+
//		|香港电视剧主题歌|    2|
//		|    怀旧专辑|    3|
//		+--------+-----+
		
		df.createOrReplaceTempView("song");                                          //建立一张临时表，将rdd放入表中
		Dataset<Row> sqlDF = spark.sql("SELECT * FROM song where album='怀旧专辑'");    //生成底层的rdd的代码，使用SQL语句查找
		sqlDF.show();
//		+-----+----+-------------------+------+
//		|album|name|               path|singer|
//		+-----+----+-------------------+------+
//		| 怀旧专辑|  红日|mp3/shanghaitan.mp3|   李克勤|
//		| 怀旧专辑|爱如潮水|mp3/airucaoshun.mp3|   张信哲|
//		| 怀旧专辑| 红茶馆|  mp3/redteabar.mp3|   陈惠嫻|
//		+-----+----+-------------------+------+
	}


	  private static void runDatasetCreationExample(SparkSession spark) {
	    Person person = new Person();
	    person.setName("Andy");
	    person.setAge(32);

	    //编码器将对象序列化为字节，person类型的编码器
	    Encoder<Person> personEncoder = Encoders.bean(Person.class);	    
	    Dataset<Person> javaBeanDS = spark.createDataset(
	      Collections.singletonList(person),						//第一个参数为接收的是list类型，将list打包，
	      personEncoder												//创建DataSet类型，最后使用的是person对象		
	    );
	    javaBeanDS.show();    	     //这里输出的value值是person对象来的，name和age字段都是从person对象的getter和setter对象中获得的字段进行转换
	    // +---+----+
	    // |age|name|
	    // +---+----+
	    // | 32|Andy|
	    // +---+----+

	    Encoder<Integer> integerEncoder = Encoders.INT();		//integer类型的编码器
	    Dataset<Integer> primitiveDS = spark.createDataset(Arrays.asList(1, 2, 3), integerEncoder);
	    Dataset<Integer> transformedDS = primitiveDS.map(new MapFunction<Integer, Integer>() {
	      @Override
	      public Integer call(Integer value) throws Exception {
	        return value + 1;
	      }
	    }, integerEncoder);
	    transformedDS.collect(); // Returns [2, 3, 4]

	    String path = "people.json";
	    Dataset<Person> peopleDS = spark.read().json(path).as(personEncoder);
	    peopleDS.show();
//	     +----+-------+
//	     | age|   name|
//	     +----+-------+
//	     |null|Michael|
//	     |  30|   Andy|
//	     |  19| Justin|
//	     +----+-------+
	     
	  }

	  private static void runInferSchemaExample(SparkSession spark) {
	    
		  JavaRDD<Person> peopleRDD = spark.read()
	      .textFile("people.txt")
	      .javaRDD()									//将DataSet转换为javaRdd
	      .map(new Function<String, Person>() {
	        @Override
	        public Person call(String line) throws Exception {
	          String[] parts = line.split(",");	          
	          Person person = new Person();
	          person.setName(parts[0]);
	          person.setAge(Integer.parseInt(parts[1].trim()));     //去掉空行。空格
	          return person;
	        }
	      });
		 
		//将rdd转换为DataSet，类型为person 
	    Dataset<Row> peopleDF = spark.createDataFrame(peopleRDD, Person.class);

	    peopleDF.createOrReplaceTempView("people");					//建一张临时表
	    Dataset<Row> teenagersDF = spark.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19");

	    Encoder<String> stringEncoder = Encoders.STRING();		
	    Dataset<String> teenagerNamesByIndexDF = teenagersDF.map(new MapFunction<Row, String>() {
	      @Override
	      public String call(Row row) throws Exception {
	        return "Name: " + row.getString(0);					//标号为0的属性，下标
	      }
	    }, stringEncoder);
	    teenagerNamesByIndexDF.show();
	    // +------------+
	    // |       value|
	    // +------------+
	    // |Name: Justin|
	    // +------------+

	    // or by field name
	    Dataset<String> teenagerNamesByFieldDF = teenagersDF.map(new MapFunction<Row, String>() {
	      @Override
	      public String call(Row row) throws Exception {
	        return "Name: " + row.<String>getAs("name");			//获得属性名的值
	      }
	    }, stringEncoder);
	    teenagerNamesByFieldDF.show();
	    // +------------+
	    // |       value|
	    // +------------+
	    // |Name: Justin|
	    // +------------+
	    // $example off:schema_inferring$
	  }

	  private static void runProgrammaticSchemaExample(SparkSession spark) {
		  //调用sparkContext方法。
	    JavaRDD<String> peopleRDD = spark.sparkContext()
	      .textFile("people.txt", 1)
	      .toJavaRDD();
	    //使用代码中的字段设计
	    String schemaString = "name age";

	    List<StructField> fields = new ArrayList<>();
	    for (String fieldName : schemaString.split(" ")) {				//创建结构化的字段，传的数组，，数据类型
	      StructField field = DataTypes.createStructField(fieldName, DataTypes.StringType, true);
	      fields.add(field);
	    }
	    StructType schema = DataTypes.createStructType(fields);

	    // Convert records of the RDD (people) to Rows
	    JavaRDD<Row> rowRDD = peopleRDD.map(new Function<String, Row>() {
	      public Row call(String record) throws Exception {
	        String[] attributes = record.split(",");
	        return RowFactory.create(attributes[0], attributes[1].trim());
	      }
	    });

	    // 类型信息也可以来自StructType
	    Dataset<Row> peopleDataFrame = spark.createDataFrame(rowRDD, schema);

	    peopleDataFrame.createOrReplaceTempView("people");

	    Dataset<Row> results = spark.sql("SELECT name FROM people");

	    Dataset<String> namesDS = results.map(new MapFunction<Row, String>() {
	      public String call(Row row) throws Exception {
	        return "Name: " + row.getString(0);
	      }
	    }, Encoders.STRING());
	    namesDS.show();
	    // +-------------+
	    // |        value|
	    // +-------------+
	    // |Name: Michael|
	    // |   Name: Andy|
	    // | Name: Justin|
	    // +-------------+
	    // $example off:programmatic_schema$
	  }
	  
	  public static class Person implements Serializable {
	    private String name;
	    private int age;

	    public String getName() {
	      return name;
	    }

	    public void setName(String name) {
	      this.name = name;
	    }

	    public int getAge() {
	      return age;
	    }

	    public void setAge(int age) {
	      this.age = age;
	    }
	  }
	
}
