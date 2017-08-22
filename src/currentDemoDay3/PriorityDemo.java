package currentDemoDay3;

/**
 * @author Zhou
 * @Date:creat Time 2017/8/23
 * java 中可以设置线程的优先级 优先级高的线程在竞争资源时会更具优势 更可能得到cpu执行资源
 * 易产生线程饥饿状态 低优先级线程一直未能抢占到cpu资源
 *
 *
 */
public class PriorityDemo {
   public  static class  HightProity extends Thread{
       static int count=0;
       @Override
       public void run(){
           while (true) {
               synchronized (PriorityDemo.class){
                   count++;
                   if(count>100000) {
                       System.out.println("高优先级执行结束！");
                       break;
                   }
               }
           }
       }
   }
    public  static class  LowerProity extends Thread{
        static int count=0;
        @Override
        public void run(){
            while (true) {
                synchronized (PriorityDemo.class){
                    count++;
                    if(count>100000) {
                        System.out.println("低优先级执行结束！");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        /**
         * 绝大多数情况下 高优先级先执行完 但不保证都是这样
         */
        Thread high=new HightProity();
        LowerProity low=new LowerProity();
        high.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);
        low.start();
        high.start();
    }
}
