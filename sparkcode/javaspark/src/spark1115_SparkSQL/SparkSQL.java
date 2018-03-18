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
//		|��۵��Ӿ������| �Ϻ�̲|mp3/shanghaitan.mp3|   Ҷ����|
//		|��۵��Ӿ������|һ������|mp3/shanghaitan.mp3|   �°�ǿ|
//		|    ����ר��|  ����|mp3/shanghaitan.mp3|   �����|
//		|    ����ר��|���糱ˮ|mp3/airucaoshun.mp3|   ������|
//		|    ����ר��| ����|  mp3/redteabar.mp3|   �»݋�|
//		+--------+----+-------------------+------+
		
		df.printSchema();   //��ӡ��ṹ
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
//		| �Ϻ�̲|
//		|һ������|
//		|  ����|
//		|���糱ˮ|
//		| ����|
//		+----+
	
		// Select everybody, but increment the age by 1
		//.plus(1)   ��1
		df.select("album", "path").show();  //ѡ������
//		+--------+-------------------+
//		|   album|               path|
//		+--------+-------------------+
//		|��۵��Ӿ������|mp3/shanghaitan.mp3|
//		|��۵��Ӿ������|mp3/shanghaitan.mp3|
//		|    ����ר��|mp3/shanghaitan.mp3|
//		|    ����ר��|mp3/airucaoshun.mp3|
//		|    ����ר��|  mp3/redteabar.mp3|
//		+--------+-------------------+
	
		
		df.filter(col("album").equalTo("����ר��")).show();     //���˳�ֻ�л���ר����row
//		+-----+----+-------------------+------+
//		|album|name|               path|singer|
//		+-----+----+-------------------+------+
//		| ����ר��|  ����|mp3/shanghaitan.mp3|   �����|
//		| ����ר��|���糱ˮ|mp3/airucaoshun.mp3|   ������|
//		| ����ר��| ����|  mp3/redteabar.mp3|   �»݋�|
//		+-----+----+-------------------+------+
		
		df.groupBy("album").count().show();    //�������
//		+--------+-----+
//		|   album|count|
//		+--------+-----+
//		|��۵��Ӿ������|    2|
//		|    ����ר��|    3|
//		+--------+-----+
		
		df.createOrReplaceTempView("song");                                          //����һ����ʱ����rdd�������
		Dataset<Row> sqlDF = spark.sql("SELECT * FROM song where album='����ר��'");    //���ɵײ��rdd�Ĵ��룬ʹ��SQL������
		sqlDF.show();
//		+-----+----+-------------------+------+
//		|album|name|               path|singer|
//		+-----+----+-------------------+------+
//		| ����ר��|  ����|mp3/shanghaitan.mp3|   �����|
//		| ����ר��|���糱ˮ|mp3/airucaoshun.mp3|   ������|
//		| ����ר��| ����|  mp3/redteabar.mp3|   �»݋�|
//		+-----+----+-------------------+------+
	}


	  private static void runDatasetCreationExample(SparkSession spark) {
	    Person person = new Person();
	    person.setName("Andy");
	    person.setAge(32);

	    //���������������л�Ϊ�ֽڣ�person���͵ı�����
	    Encoder<Person> personEncoder = Encoders.bean(Person.class);	    
	    Dataset<Person> javaBeanDS = spark.createDataset(
	      Collections.singletonList(person),						//��һ������Ϊ���յ���list���ͣ���list�����
	      personEncoder												//����DataSet���ͣ����ʹ�õ���person����		
	    );
	    javaBeanDS.show();    	     //���������valueֵ��person�������ģ�name��age�ֶζ��Ǵ�person�����getter��setter�����л�õ��ֶν���ת��
	    // +---+----+
	    // |age|name|
	    // +---+----+
	    // | 32|Andy|
	    // +---+----+

	    Encoder<Integer> integerEncoder = Encoders.INT();		//integer���͵ı�����
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
	      .javaRDD()									//��DataSetת��ΪjavaRdd
	      .map(new Function<String, Person>() {
	        @Override
	        public Person call(String line) throws Exception {
	          String[] parts = line.split(",");	          
	          Person person = new Person();
	          person.setName(parts[0]);
	          person.setAge(Integer.parseInt(parts[1].trim()));     //ȥ�����С��ո�
	          return person;
	        }
	      });
		 
		//��rddת��ΪDataSet������Ϊperson 
	    Dataset<Row> peopleDF = spark.createDataFrame(peopleRDD, Person.class);

	    peopleDF.createOrReplaceTempView("people");					//��һ����ʱ��
	    Dataset<Row> teenagersDF = spark.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19");

	    Encoder<String> stringEncoder = Encoders.STRING();		
	    Dataset<String> teenagerNamesByIndexDF = teenagersDF.map(new MapFunction<Row, String>() {
	      @Override
	      public String call(Row row) throws Exception {
	        return "Name: " + row.getString(0);					//���Ϊ0�����ԣ��±�
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
	        return "Name: " + row.<String>getAs("name");			//�����������ֵ
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
		  //����sparkContext������
	    JavaRDD<String> peopleRDD = spark.sparkContext()
	      .textFile("people.txt", 1)
	      .toJavaRDD();
	    //ʹ�ô����е��ֶ����
	    String schemaString = "name age";

	    List<StructField> fields = new ArrayList<>();
	    for (String fieldName : schemaString.split(" ")) {				//�����ṹ�����ֶΣ��������飬����������
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

	    // ������ϢҲ��������StructType
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
