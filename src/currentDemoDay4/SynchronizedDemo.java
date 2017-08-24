package currentDemoDay4;

/**
 * @author Zhou
 * @Date:creat Time 2017/8/24
 * 关键字 Synchornized的作用是实现线程间的同步。它的工作是对同步的代码块加锁，使得每一次，只能有一个线程进入同步块，
 * 从而保证线程间的安全性
 *
 * synchornized的用法：
 * 1.指定加锁对象：对给定的对象加锁，进入同步代码前要获得给定对象的锁。
 * 2.直接作用实例方法：相当于对当前实例加锁，进入同步代码前要获得当前实例的锁
 * 3.直接作用静态方法：相当于对当前类加锁，进入同步代码前要获得当前类的锁
 *
 * 除了线程同步、安全外，synchornized还可以保证线程间可见性及有序性，换言之 在synchornized同步代码块内线程是串行执行的！
 */

public class SynchronizedDemo {
    public static  class AddSyncDemo implements  Runnable{
        static AddSyncDemo instance=new AddSyncDemo();
        static int i=0;
        @Override
        public void run() {
            for (int j = 0; j <100000 ; j++) {
                synchronized (instance) {//当线程进入就会请求instance实例的锁
                    i++;
                }
            }
        }
    }

    public static class AddSyncTwoDemo implements  Runnable{
        static AddSyncTwoDemo instance=new AddSyncTwoDemo();
        static int i=0;
        public synchronized void increase(){i++;}//同步实例方法，在进入increase()前线程必须获得当前对象实例锁
        @Override
        public void run() {
            for (int j = 0; j <100000 ; j++) {
                increase();
            }

        }
        public static void main(String[] args) throws InterruptedException {
            /**
             * 注意！这里使用Runnable接口创建两个实例，并且这两个实例都指向同一个Runnable接口实例（instance对象）
             * 这样才能保证两个线程在工作时，能跟作用到同一个对象锁上去，从而保证线程安全！
             */
            Thread t1=new Thread(instance);
            Thread t2=new Thread(instance);
            /****************************/
            t1.start();t2.start();
            t1.join();t2.join();
            System.out.println(i);//线程安全输出 200000

        }
    }


    /**
     * 错误案例
     */
    public static class  AddSyncBadDemo implements  Runnable{
        static int i=0;

        public synchronized void increase(){
            i++;
        }
        @Override
        public void run() {
            for (int j = 0; j <10000 ; j++) {
                increase();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            /**
             * 严重错误！执行这段代码的两个线程指向了不同的Runnable对象，这两个线程使用的是俩把不同的锁，因此线程不安全！
             */
            Thread t1=new Thread(new AddSyncBadDemo());
            Thread t2=new Thread(new AddSyncBadDemo());
            /****************************/
            t1.start();t2.start();
            t1.join();t2.join();
            System.out.println(i);//线程不安全输出

        }

        /**
         * 这样可使得两个不同线程指向不同Runnable对象，但由于该方法块需要请求的时当前类的锁，所以时线程安全的 正确同步
         */
        public static synchronized void increaseSafe(){
            i++;
        }
    }
}
