/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spark1115_SparkSQL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
// $example off:schema_merging$

// $example on:basic_parquet_example$
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Encoders;
// $example on:schema_merging$
// $example on:json_dataset$
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
// $example off:json_dataset$
// $example off:schema_merging$
// $example off:basic_parquet_example$
import org.apache.spark.sql.SparkSession;

public class JavaSQLDataSourceExample {

  public static void main(String[] args) {
    SparkSession spark = SparkSession
      .builder()
      .appName("Java Spark SQL data sources example")
      .config("spark.some.config.option", "some-value")
      .master("local")
      .getOrCreate();

    runBasicDataSourceExample(spark);
//    runBasicParquetExample(spark);
//    runParquetSchemaMergingExample(spark);
//    runJsonDatasetExample(spark);
//    runJdbcDatasetExample(spark);

    spark.stop();
  }

  private static void runBasicDataSourceExample(SparkSession spark) {
    // parquet��ʽ�洢���ļ�
    Dataset<Row> usersDF = spark.read().load("users.parquet");
    usersDF.schema(); 				//��ṹ
    usersDF.foreach(x->System.out.println(x));
//    [Alyssa,null,WrappedArray(3, 9, 15, 20)]
//    		[Ben,red,WrappedArray()]
    
    //��ȡ�����ֶε� ��Ϣ��������ȡ����һ��parquetĿ¼��Ŀ¼���и�parquet�ļ�
    usersDF.select("name", "favorite_color").write().save("parquet/namesAndFavColors.parquet");

    Dataset<Row> peopleDF =	spark.read().format("json").load("people.json");
    //��json��ʽ��������parquet��ʽ���ļ�����
    peopleDF.select("name", "age").write().format("parquet").save("parquet/namesAndAges.parquet");

    //�˴����ð�Ÿֻ��Ҫ��parquet����������ˣ����÷����ڴ���ʹ��
    Dataset<Row> sqlDF = spark.sql("SELECT * FROM parquet.`users.parquet`");
    sqlDF.foreach(x->System.out.println(x));
//  [Alyssa,null,WrappedArray(3, 9, 15, 20)]
//	[Ben,red,WrappedArray()]
    
    Dataset<Row> sqlDF1 = spark.sql("SELECT * FROM json.`people.json`");
    sqlDF1.foreach(x->System.out.println(x));
//    [null,Michael]
//    		[30,Andy]
//    		[19,Justin]
    
  }

  private static void runBasicParquetExample(SparkSession spark) {
	  
    Dataset<Row> peopleDF = spark.read().json("people.json");
//	��json�ļ�����ΪparquetĿ¼�ļ�
    peopleDF.write().parquet("parquet/people.parquet");

    Dataset<Row> parquetFileDF = spark.read().parquet("people.parquet");

    parquetFileDF.createOrReplaceTempView("parquetFile");
    Dataset<Row> namesDF = spark.sql("SELECT name FROM parquetFile WHERE age BETWEEN 13 AND 19");
    Dataset<String> namesDS = namesDF.map((row)-> "Name: " + row.getString(0), Encoders.STRING());
//    new MapFunction<Row, String>() {
//        public String call(Row row) {
//          return "Name: " + row.getString(0);
//        }
//      }
    namesDS.show();
    // +------------+
    // |       value|
    // +------------+
    // |Name: Justin|
    // +------------+

  }

  private static void runParquetSchemaMergingExample(SparkSession spark) {

    List<Square> squares = new ArrayList<>();
    for (int value = 1; value <= 5; value++) {
      Square square = new Square();
      square.setValue(value);
      square.setSquare(value * value);
      squares.add(square);
    }

    Dataset<Row> squaresDF = spark.createDataFrame(squares, Square.class);
    squaresDF.write().parquet("parquet/data/test_table/key=1");				//��parquet��ʽ����squre  list

    List<Cube> cubes = new ArrayList<>();
    for (int value = 6; value <= 10; value++) {
      Cube cube = new Cube();
      cube.setValue(value);
      cube.setCube(value * value * value);
      cubes.add(cube);
    }

    Dataset<Row> cubesDF = spark.createDataFrame(cubes, Cube.class);
    cubesDF.write().parquet("parquet/data/test_table/key=2");
//	��ͬһĿ¼�µĶ��parquetĿ¼�ļ��ϲ���Ϊ1��    
    Dataset<Row> mergedDF = spark.read().option("mergeSchema", true).parquet("parquet/data/test_table");
    mergedDF.printSchema();
    // The final schema consists of all 3 columns in the Parquet files together
    // with the partitioning column appeared in the partition directory paths
    // root
    //  |-- value: int (nullable = true)
    //  |-- square: int (nullable = true)
    //  |-- cube: int (nullable = true)
    //  |-- key: int (nullable = true)
        
    mergedDF.foreach(x->System.out.println(x));
//    		[1,1,null,1]
//    		[4,2,null,1]
//    		[9,3,null,1]
//    		[16,4,null,1]
//    		[25,5,null,1]
//    		[null,6,216,2]
//    		[null,7,343,2]
//    		[null,8,512,2]
//    		[null,9,729,2]
//    		[null,10,1000,2]
    

  }

  private static void runJsonDatasetExample(SparkSession spark) {

    Dataset<Row> people = spark.read().json("people.json");

    people.printSchema();
    // root
    //  |-- age: long (nullable = true)
    //  |-- name: string (nullable = true)

    people.createOrReplaceTempView("people");		//���ڴ��д���һ����ʱ��

    Dataset<Row> namesDF = spark.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19");
    namesDF.show();
    // +------+
    // |  name|
    // +------+
    // |Justin|
    // +------+

  }

  private static void runJdbcDatasetExample(SparkSession spark) {
    Dataset<Row> jdbcDF = spark.read()
      .format("jdbc")
      .option("url", "jdbc:postgresql:dbserver")
      .option("dbtable", "schema.tablename")
      .option("user", "username")
      .option("password", "password")
      .load();
  }
  

  public static class Square implements Serializable {
    private int value;
    private int square;

    // Getters and setters...
    // $example off:schema_merging$
    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }

    public int getSquare() {
      return square;
    }

    public void setSquare(int square) {
      this.square = square;
    }
    // $example on:schema_merging$
  }
  
  public static class Cube implements Serializable {
    private int value;
    private int cube;

    // Getters and setters...
    // $example off:schema_merging$
    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }

    public int getCube() {
      return cube;
    }

    public void setCube(int cube) {
      this.cube = cube;
    }
    // $example on:schema_merging$
  }
 
  
}
