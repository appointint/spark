package spark0925_forkjoin;


public class sum {

	public static void main(String[] args) {
		
		Long start=System.currentTimeMillis();
		Long sum=0L;
		for (int i = 0; i < 10000L; i++) {
			sum+=i;
		}
		Long end=System.currentTimeMillis();
		System.out.println(end-start);
		System.out.println(sum);

	}

}
