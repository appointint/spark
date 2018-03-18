package com.scala_spark.spark1115_test1

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;



object hello1 {
  
  def main(args: Array[String]): Unit = {
   
     val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("scala_spark")
    conf.set("spark.testing.memory", "5646654654")
     val sc = new SparkContext(conf)
     val line = sc.textFile("words.txt")
 
     line.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).collect().foreach(println)
     
     sc.stop()
  }
  
}