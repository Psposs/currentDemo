package currentDemoDay2;

/**
 * @author Zhou
 * @Date:creat Time 2017/8/21
 * 若一个线程调用了Object.wait（）方法，那么他就会进入object对象的等待队列，这个等待队列可能会有多个线程，因为系统运行多个线程同时等待某一个对象
 * 当调用notify（）时，他会随机！！从队列中唤醒一个线程
 * 这里notify（）方法的唤醒是不公平的、完全随机的!!!
 * notifyAll（）唤醒等待队列中的全部线程
 *
 * wait()方法必须包含synchornized中，再调用wait（）后线程陷入等待并释放该对象的锁！！Thread.sleep()不会释放任何锁！
 * 在另一个线程调用notify（）唤醒线程时，线程还是会先去获得锁再去执行！
 */
public class WaitAndNotifyDemo {
    final static Object obj=new Object();
    public static class ThreadOne extends Thread{
        @Override
        public void run(){
            synchronized (obj){
                System.out.println("ThreadOne 线程一启动："+System.currentTimeMillis());
                try {
                    System.out.println("ThreadOne 线程一等待Object对象");
                    obj.wait();//释放锁，并进入obj的等待队列
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //被通知后并不能立即执行，需等待obj锁
                System.out.println("ThreadOne 线程一终止："+System.currentTimeMillis());
            }
        }
    }

    public static class ThreadTwo extends Thread{
        @Override
        public void run(){
            synchronized (obj){
                System.out.println("ThreadTwo 线程二启动！唤醒一个线程："+System.currentTimeMillis());
                obj.notify();
                System.out.println("ThreadTwo 线程二终止"+System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        /**
         *执行结果：
         *ThreadOne 线程一启动：              1503329263523
         *ThreadOne 线程一等待Object对象
         *ThreadTwo 线程二启动！唤醒一个线程：1503329263523
         *
         *ThreadTwo 线程二终止：           1503329263524
         *ThreadOne 线程一终止：          1503329265524
         *
         * 这里线程二终止2秒后 线程一才执行完，这说明ThreadOne在得到现场ThreadTwo通知后 （notify（））
         * ThreadOne并不能立即执行！！，而是要等待ThreadTwo释放Object的锁，并获得锁后才能继续执行！
         *
         */
        ThreadOne one=new ThreadOne();
        ThreadTwo two=new ThreadTwo();
        one.start();
        two.start();
    }

}
