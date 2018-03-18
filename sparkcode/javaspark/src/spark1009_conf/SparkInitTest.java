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

package spark1009_conf;

import java.util.Scanner;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;


public final class SparkInitTest {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("1st Spark App");
//        		.set("spark.testing.memory", "2147480000");
        JavaSparkContext sc = new JavaSparkContext(conf);
        System.out.println(sc);
        
        Scanner scanner = new Scanner(System.in);
        scanner.next();
        scanner.close();
    }
}