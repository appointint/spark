package spark1023_ConbineByKey;import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class SortByKeyApiTest {
    public static void main(String[] xx){
    	SparkConf conf = new SparkConf();
    	conf.setMaster("local");
    	conf.setAppName("WordCounter");
    	conf.set("spark.default.parallelism", "4");
    	conf.set("spark.testing.memory", "2147480000");
    	JavaSparkContext ctx = new JavaSparkContext(conf);
    	//创建RDD：1）通过读取外部存储 ----- 集群环境使用 2）通过内存中的集合
    	List<Person> data1 = 
    			new ArrayList<Person>();
    	data1.add(new Person("Cake",32));
    	data1.add(new Person("Bread",21));
    	data1.add(new Person("Smith",32));
    	data1.add(new Person("Hourse",21));
    	data1.add(new Person("Mary",32));
    	data1.add(new Person("Greey",21));
    	data1.add(new Person("Greey",21));
    	data1.add(new Person("Tom",32));
    	data1.add(new Person("Gao",21));    	
       ctx.parallelize(data1)//.distinct().count());
                     .sortBy(x->x, true, 2).foreach(x->System.out.println(x));

    	List<Tuple2<Person, Integer>> data = 
    			new ArrayList<Tuple2<Person, Integer>>();
    	data.add(new Tuple2<Person, Integer>(new Person("Cake",32), 2));
    	data.add(new Tuple2<Person, Integer>(new Person("Bread",21), 3));
    	data.add(new Tuple2<Person, Integer>(new Person("Smith",32), 2));
    	data.add(new Tuple2<Person, Integer>(new Person("Hourse",21), 3));
    	data.add(new Tuple2<Person, Integer>(new Person("Mary",32), 2));
    	data.add(new Tuple2<Person, Integer>(new Person("Greey",21), 3));
    	data.add(new Tuple2<Person, Integer>(new Person("Greey",11), 3));
    	data.add(new Tuple2<Person, Integer>(new Person("Tom",32), 2));
    	data.add(new Tuple2<Person, Integer>(new Person("Gao",21), 3));    	

    	JavaPairRDD<Person, Integer> dataRdd = ctx.parallelizePairs(data);
    	dataRdd.sortByKey().foreach(x->System.out.println(x));
        dataRdd.sortByKey(new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				int res = o1.name.compareTo(o2.name);
				if(res == 0){
					res = o1.age - o2.age;
				}
				return res;
			}
		});
    	ctx.close();
    	ctx.stop();
    }
}

class Person implements Serializable, Comparable<Person>{
	private static final long serialVersionUID = 1L;

	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (age != other.age)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	String name;
	int age;

	@Override
	public int compareTo(Person p) {
		int res = this.name.compareTo(p.name);
		if(res == 0){
			res = this.age - p.age;
		}
		return res;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
}
