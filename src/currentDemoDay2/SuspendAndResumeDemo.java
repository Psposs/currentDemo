package currentDemoDay2;

/**
 * 不建议使用，已作废
 * @author Zhou
 * @Date:creat Time 2017/8/21
 * 当suspend（）导致线程暂停的同时，并不会释放任何线程。直到resume（）操作，才能继续。若发生先resume后suspend，那么挂起的线程很难在有机会执行
 * 更严重的是他所占用的锁是不会释放的，导致死锁
 *
 * 而被挂起的线程，从线程状态上看居然是Runnable的，导致对线程的误判
 */
public class SuspendAndResumeDemo {

}
