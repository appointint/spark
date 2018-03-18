interface MyInter {
    default void df(){    //声明一个接口的默认方法
        System.out.println("MyInter df");
        sf();        //调用本接口的类方法
    }
    static void sf(){//声明一个接口的类方法
        System.out.println("i'am static f");
    }
    
    static int A = 8;
}

class Man implements MyInter{    //Man类实现MyInter接口
	@Override
	public void df(){//声明一个接口的类方法
        System.out.println("Man df");
    }
}

public class InterTest extends Man{
    public static void main(String[] args) {
    	MyInter man = new Man();
        //man.df();            //通过man对象调用MyInter接口的默认方法df()
        man.df();
    }
}