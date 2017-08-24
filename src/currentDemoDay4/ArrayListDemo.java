package currentDemoDay4;

import java.util.ArrayList;

/**
 * @author Zhou
 * @Date:creat Time 2017/8/25
 * ArrayList是一个线程不安全的容器！
 */
public class ArrayListDemo {
    static ArrayList<Integer> list=new ArrayList<Integer>(10);
    public  static class AddThread implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                list.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new AddThread());
        Thread t2=new Thread(new AddThread());
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(list.size());
        /**
         * 执行结果1：

         Exception in thread "Thread-1" java.lang.ArrayIndexOutOfBoundsException: 15
         at java.util.ArrayList.add(ArrayList.java:459)
         at currentDemoDay4.ArrayListDemo$AddThread.run(ArrayListDemo.java:17)
         at java.lang.Thread.run(Thread.java:748)

         出现这个异常是因为ArrayList在扩容过程中，内部一致性遭到破坏，由于没有锁的保护能一个线程访问到了不一致的内存状态导致越界！


         执行结果2：
            200000

         执行结果3：
         小于200000

         加锁或者使用线程安全的Vetctor
         */

    }
}
