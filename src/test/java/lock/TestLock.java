package lock;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {


//
    public static void main(String[] args) throws IOException {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        List<Integer> products =  new ArrayList<>(1);

        for(int i = 0;i< 5;i++){
            new Thread(()->{
                while (true){
                    lock.lock();
                    try {
                        products.add(1);
                        System.out.println(Thread.currentThread().getName()+"===> await");
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                    System.out.println(Thread.currentThread().getName()+"===> await end start to producer");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"producer_thread_"+i).start();
        }

        new Thread(()->{
            while (true){
                lock.lock();
                try{
                    Iterator<Integer> iterator = products.iterator();
                    while (iterator.hasNext()){
                        int i = iterator.next().intValue();
                        System.out.println("消费了"+i);
                        iterator.remove();
                    }
                    if(products.size()<1){
                        condition.signalAll();
                    }
                }finally {
                    lock.unlock();
                }
            }
        },"consumer_thread").start();


        System.in.read();
    }
//
//    public static void main(String[] args) throws IOException {
//        Lock lock = new ReentrantLock();
//        Condition condition = lock.newCondition();
//        new Thread(()->{
//            lock.lock();
//            try {
//                System.out.println("await begin");
//                condition.await();
//                System.out.println("await end");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }finally {
//                lock.unlock();
//            }
//        }).start();
//
//        new Thread(()->{
//            lock.lock();
//            try {
//                System.out.println("singal begin");
//                condition.signalAll();
//                System.out.println("singal end");
//            }finally {
//                lock.unlock();
//            }
//        }).start();
//
//        System.in.read();
//    }
}
