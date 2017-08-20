package currentDemo1;

/**
 * @author Zhou
 * @Date:creat Time 2017/8/21
 * 不要使用sotp（）去终止线程，Thread.stop()在结束线程时会直接终止线程，并会立即释放！！这个线程所持有的所有锁。
 */
public class StopThreadUnsafe {
    public  static User user=new User();
    public static class User{
        private int id;
        private  String name;
        public User(){
            id=0;
            name="0";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    public  static class  ChangeUserThread extends Thread{
        //对外提供停止线程的方法，自行决定线程何时退出
        volatile boolean stopme=false;//volatile关键字 保证变量一致性 ！！！不保证原子性！！！

        public  void stopMe(){
            stopme=true;
        }
        @Override
        public void run(){
            while (true){
                //若调用线程stop将引起一致性问题
                if(stopme){
                    System.out.println("由自己调用终止线程，这时线程还未获得对象锁，并不会影响对象一致性！");
                    break;
                }


                synchronized (user){//利用synchronized锁控制User对象一致性
                    int v=(int)(System.currentTimeMillis()/1000);
                    user.setId(v);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                    user.setName(String.valueOf(v));
                }
                Thread.yield();//线程优先级谦让
            }
        }


    }

    public static class  ReadUserThread extends Thread{
        @Override
        public void run(){
            while (true){
                synchronized (user){
                    if(user.getId()!=Integer.parseInt(user.getName())){
                        System.out.println("出现不一致问题！"+user.toString());
                    }
                    Thread.yield();//线程谦让
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadUserThread().start();
        while(true){
            ChangeUserThread t=new ChangeUserThread();
            t.start();
            Thread.sleep(150);
            t.stopMe();
          //  t.stop();//线程直接停止，放开所有持有的锁，引起对象不一致！！

        }
    }
}
