package currentDemoDay2;

/**
 * @author Zhou
 * @Date:creat Time 2017/8/22
 * 让一个线程等待另一个线程执行完后继续执行 jion（）
 *  public final void join() 无限等待 他会一直阻塞当前线程 知道目标线程执行完
 *  public final synchronized void join(long millis)超时后继续执行
 *  join()本质是让调用线程wait（）在当前线程对象实例上,当线程执行完毕后被等待的线程会退出前调用notifyAll（）通知所有线程继续执行
 *  所以，不要在应用程序中，在Thread对象实例上调用wait（）或notify（）等方法
 *
 *  JDK join核心源码
 *  while(isAlive()){ wait(0);}
 *
 *
 *  public static native void yield()谦让
 *  让当前线程让出CPU，但是让出后还是会去争夺CPU资源
 */
public class JoinAndYieldDemo {
    public  volatile static int i=0;
    public static class  AddThread extends Thread{
        @Override
        public  void run(){
            for ( i = 0; i <1000000 ; i++);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread add=new AddThread();
        add.start();
        add.join();//当前主线程join（）add线程 陷入阻塞 当add线程结束后主线程继续执行
        System.out.println(i);//1000000
    }
}
