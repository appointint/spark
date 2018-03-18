package spark0925_forkjoin;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;


public class ParellelStreamTest1 {
    public static void main(String[] args){
        test1();
    }
    
    private static void test1(){
        Instant start=Instant.now();

        Long sum = LongStream.rangeClosed(0L, 10000L)
                             .parallel()
                             .reduce(0,Long::sum);
        System.out.println(sum);

        Instant end=Instant.now();
        System.out.println("消耗时间" + Duration.between(start, end).toMillis()+"ms");//消耗时间2418ms
    }    
}
