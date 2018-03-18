package spark0918_stream;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import spark0910.Test1;
import spark0918_stream.Employee.Status;

public class StreamAction1 {
    static List<Employee> employees = Arrays.asList(
							            new Employee("张三", 18, 9999.99, Status.FREE),
							            new Employee("李四", 58, 5555.55, Status.BUSY),
							            new Employee("王五", 26, 3333.33, Status.VOCATION),
							            new Employee("赵六", 36, 6666.66, Status.FREE),
							            new Employee("田七", 12, 8888.88, Status.BUSY)
                              );
    
	public static void main(String[] args){
    	test1();
    }
    
    /*
     * 查找与匹配
     */
    public static void test1(){
        boolean b1=employees.stream()//allMatch-检查是否匹配所有元素
                            .allMatch((e)->e.getStatus().equals(Status.BUSY));
        System.out.println(b1);//false

        boolean b2=employees.stream()//anyMatch-检查是否至少匹配一个元素
                            .anyMatch((e)->e.getStatus().equals(Status.BUSY));
        System.out.println(b2);//true

        boolean b3=employees.stream()//noneMatch-检查是否没有匹配所有元素
                            .noneMatch((e)->e.getStatus().equals(Status.BUSY));
        System.out.println(b3);//false

        Optional<Employee> op=employees.stream()//findFirst-返回第一个元素//Optional是Java8中避免空指针异常的容器类
                 .sorted((e1,e2)->Double.compare(e1.getSalary(), e2.getSalary()))
                 .findFirst();
//        op.forEach(System.out::println);
//        System.out.println(op.get());//Employee [name=王五, age=26, salary=3333.33, Status=VOCATION]

        Optional<Employee> op2=employees.parallelStream() //findAny-返回当前流中的任意元素
                                        .filter((e)->e.getStatus().equals(Status.FREE))
                                        .findAny();
        System.out.println(op2.get());//Employee [name=赵六, age=36, salary=6666.66, Status=FREE]

        Long count=employees.stream()//count-返回流中元素的总个数
                            .count();
        System.out.println(count);//5

        Optional<Employee> op3=employees.stream()//max-返回流中最大值
                                        .max((e1,e2)->Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(op3.get());//Employee [name=张三, age=18, salary=9999.99, Status=FREE]

        Optional<Double> op4=employees.stream()//min-返回流中最小值
                                      .map(Employee::getSalary)
                                      .min(Double::compare);
        System.out.println(op4.get());//3333.33
    }

    /*
     * 归约
     * reduce(T identity,BinaryOperator b) / reduce(BinaryOperator b)-可以将流中元素反复结合起来，得到一个值。
     */
    public static void test3(){
        List<Integer> list=Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Integer sum=list.stream()//reduce(T identity,BinaryOperator b)
                        .reduce(0, (x,y)->x+y);//0为起始值
        System.out.println(sum);//55

        System.out.println("--------------------------");

        Optional<Double> op=employees.stream()//reduce(BinaryOperator b)//没有起始值，map返回可能为空，所以返回Optional类型
                                     .map(Employee::getSalary)
                                     .reduce(Double::sum);
//                                     .reduce((x1,x2)->Double.sum(x1, x2));
        System.out.println(op.get());//        34444.41
    }
    /*
     * 收集
     * collect-将流转换为其他形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法。
     */
    public static void test4(){
        List<String> list=employees.stream()
                                   .map(Employee::getName)
                                   .collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("----------------------------");

        Set<String> set=employees.stream()
                                 .map(Employee::getName)
                                 .collect(Collectors.toSet());
        set.forEach(System.out::println);

        System.out.println("----------------------------");

        HashSet<String> hs=employees.stream()
                                    .map(Employee::getName)
                                    .collect(Collectors.toCollection(HashSet::new));
        hs.forEach(System.out::println);

        System.out.println("----------------------------");

        //总和
        Long count=employees.stream()
                            .collect(Collectors.counting());
        System.out.println(count);
      
        
        Long count1=employees.stream()//value值为1
                .collect(Collectors.reducing(0L, e -> 1L, Long::sum));
        System.out.println(count1);

        //平均值
        Double avg=employees.stream()
                            .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        //总和
        Double sum=employees.stream()
                            .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        //最大值
        Optional<Employee> max=employees.stream()
                                        .collect(Collectors.maxBy((e1,e2)->Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(max.get());

        //最小值
        Optional<Double> min=employees.stream()
                                      .map(Employee::getSalary)
                                      .collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());

        System.out.println("----------------------------");

        //分组
        Map<Status,List<Employee>> map=employees.stream()
                                                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(map);//{FREE=[Employee [name=张三, age=18, salary=9999.99, Status=FREE], Employee [name=赵六, age=36, salary=6666.66, Status=FREE]], VOCATION=[Employee [name=王五, age=26, salary=3333.33, Status=VOCATION]], BUSY=[Employee [name=李四, age=58, salary=5555.55, Status=BUSY], Employee [name=田七, age=12, salary=8888.88, Status=BUSY]]}

        //多级分组
        Map<Status, Map<String, List<Employee>>> map2=employees.stream()
                                                            .collect( Collectors.groupingBy( Employee::getStatus,Collectors.groupingBy((e)->{
                                                                if(((Employee) e).getAge() <= 35){
                                                                    return "青年";
                                                                }else if(((Employee) e).getAge() <= 50){
                                                                    return "中年";
                                                                }else{
                                                                    return "老年";
                                                                }
                                                            }
                                                            
                                                            		
                                                            		)) );
        System.out.println(map2);//{FREE={青年=[Employee [name=张三, age=18, salary=9999.99, Status=FREE]], 中年=[Employee [name=赵六, age=36, salary=6666.66, Status=FREE]]}, VOCATION={青年=[Employee [name=王五, age=26, salary=3333.33, Status=VOCATION]]}, BUSY={青年=[Employee [name=田七, age=12, salary=8888.88, Status=BUSY]], 老年=[Employee [name=李四, age=58, salary=5555.55, Status=BUSY]]}}

        //分区
        Map<Boolean,List<Employee>> map3=employees.stream()
                                                 .collect(Collectors.partitioningBy((e)->e.getSalary()>8000));
        System.out.println(map3);//{false=[Employee [name=李四, age=58, salary=5555.55, Status=BUSY], Employee [name=王五, age=26, salary=3333.33, Status=VOCATION], Employee [name=赵六, age=36, salary=6666.66, Status=FREE]], true=[Employee [name=张三, age=18, salary=9999.99, Status=FREE], Employee [name=田七, age=12, salary=8888.88, Status=BUSY]]}

        System.out.println("--------------------------------");

        DoubleSummaryStatistics dss=employees.stream()
                                             .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(dss.getSum());
        System.out.println(dss.getAverage());
        System.out.println(dss.getMax());
        System.out.println(dss.getMin());
        System.out.println(dss.getCount());

        System.out.println("--------------------------------");
        String strr=employees.stream()
                             .map(Employee::getName)
                             .collect(Collectors.joining(","));
        System.out.println(strr);//张三李四王五赵六田七
     }
}
