package currentDemo1;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * @author Zhou
 * @Date:creat Time 2017/8/21
 * interrupt 方法他会发送给线程一个通知，告知线程将要退出（设置标志位，中断标志位表示当前线程已经中断）
 * 目标线程接到通知后由目标线程自己决定如何处理！从而避免了Thread.stop()的一致性问题。
 *
 * public void Thread.interrupt()    中断线程
 * public boolean Thread.isInterrupted()  判断是否被中断
 * public static boolean Thread.interrupted() 判断是否被中断，并清除当前中断状态！
 */
public class InterruptedDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t=new Thread(){
            @Override
            public void run(){
                while (true){
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("线程中断生效");
                        break;//得到中断通知 自行处理！
                    }
                    Thread.yield();//谦让
                }
            }
        };
        t.start();
        Thread.sleep(2000);
        t.interrupt();//中断


        Thread t2=new Thread(){
          @Override
          public   void run(){
              while (true){
                  if(Thread.currentThread().isInterrupted()){
                      System.out.println("线程中断生效");
                      break;//得到中断通知 自行处理！
                  }
                  try {
                      Thread.sleep(2000);
                  } catch (InterruptedException e) {
                      System.out.println("睡眠时出现中断，抛出中断异常！");
                      /**
                       * 设置中断标志！！！！
                       * Thread.sleep()方法由于中断会抛出中断异常，并切会清除线程的中断标志！！！若不加处理，下次循环时线程还是未中断状态。
                       * 为了保证数据一致性及完整性，再次执行了interrupt方法。只有这样下次循环时才能发现线程被中断了！
                       */
                      Thread.currentThread().interrupt();
                  }
                  Thread.yield();
              }
          }
        };
        t2.start();
        Thread.sleep(2000);
        t2.interrupt();

    }
}
