package currentDemoDay3;

/**
 * @author Zhou
 * @Date:creat Time 2017/8/23
 * volatile 易变的/不稳定的
 * 它可以保证数据的可见性 有序性
 * 不保证原子性！！
 */
public class VolatileDemo {
    /**
     * 例子：原子性
     */
    static volatile int i=0;
    public static class PlusTask implements Runnable{

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads=new Thread[10];
        for (int j = 0; j < 10; j++) {
            threads[j]=new Thread(new PlusTask());
            threads[j].start();
        }
        for (int j = 0; j < 10; j++) {
            threads[j].join();//阻塞main主线程 无限等待 当threads数组所有线程执行完后 主线程在执行
        }
        /**
         * 若无多线程问题 执行结果应为100000 但实际输出小于100000
         * volatile不保证原子性！
         */
        System.out.println("多线程下i="+i);//
    }

    /**
     * 例子：可见性
     */
    public static class Novisibility{
        /**
         *
         */
        private volatile  static boolean ready;
        private static int number;
        private static class ReadThread extends Thread{
            @Override
            public void run(){
                while (!ready);
                System.out.println(number);
            }
        }

        public static void main(String[] args) throws InterruptedException {
            /**
             * 若 ready不是volatile readThread线程并不能发现主线程对read的修改 将陷入死循环 执行结果如下
                 主线程睡眠
                 主线程睡眠结束
                 主线程第二次睡眠
                 主线程第二次睡眠end

             *
             * 若read为volatile 将为其保证可见性 任何对volatile修饰的变量写操作将及时从工作内存刷新到主内存中 保证可见性 执行结果：
             *
                 主线程睡眠
                 主线程睡眠结束
                 主线程第二次睡眠
                 111
                 主线程第二次睡眠end
             */
            new ReadThread().start();
            System.out.println("主线程睡眠");
            Thread.sleep(1000);
            System.out.println("主线程睡眠结束");
            number=111;
            ready=true;
            System.out.println("主线程第二次睡眠");
            Thread.sleep(1000);
            System.out.println("主线程第二次睡眠end");

        }
    }


}
