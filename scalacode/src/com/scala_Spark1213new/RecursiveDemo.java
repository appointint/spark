
public class RecursiveDemo {
    public static void main(String[] args) {
    	System.out.println(tail_func(5, 1));
	}

    int sum(int limit){
    	int res = 0;
    	for(int i = 0; i < limit; i++){
    		res += i;
    	}
    	return res;
    }
    
    //普通递归
	static int func(int n) {
	    if (n <= 1) 
	    	return 1;
	    return (n * func(n-1));
	}

	//尾递归是指递归调用是函数的最后一个语句，而且其结果被直接返回，这是一类特殊的递归调用。
	static int tail_func(int n, int res) {
	     if (n <= 1)
	    	 return res;
	     return tail_func(n - 1, n * res);
	}
/*
 * n(5)      res(1)
 * n(4)      res(5*4)
 * n(3)      res(5*4*3)
 * n(2)      res(5*4*3*2)
 * n(1)      res(5*4*3*2*1)
 */
}