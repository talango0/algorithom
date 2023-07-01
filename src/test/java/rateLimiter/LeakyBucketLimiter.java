package rateLimiter;

import java.util.Date;
import java.util.LinkedList;

/**
 * 漏捅算法
 * 保证请求以恒定的速率流出
 * @author mayanwei
 * @date 2022-12-17.
 */
public class LeakyBucketLimiter{
    /**
     * 漏捅容量
     */
    private int capacity;

    /**
     * 以一定的速率请求，这个处理速率依赖于消费方，这个速率在极速的情况下无法控制。
     */
    private int rate;

    /**
     * 漏斗中剩余容量
     */
    private int left;

    private LinkedList<Request> requestList;

    private LeakyBucketLimiter() {}

    public LeakyBucketLimiter(int capacity, int rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.left = capacity;
        requestList = new LinkedList<>();

        //开启一个定时线程，以固定的速率将漏斗中的请求流出，进行处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(!requestList.isEmpty()){
                        Request request = requestList.removeFirst();
                        left++;
                        handleRequest(request);
                    }
                    try {
                        Thread.sleep(1000 / rate); //睡眠
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 处理请求
     * @param request
     */
    private void handleRequest(Request request){
        request.setHandleTime(new Date());
        System.out.println(request.getCode() + "号请求被处理，请求发起时间："
                + request.getLaunchTime() + ",请求处理时间：" + request.getHandleTime() + ",处理耗时："
                + (request.getHandleTime().getTime()  - request.getLaunchTime().getTime()) + "ms");
    }

    public synchronized boolean tryAcquire(Request request){
        if(left <= 0){
            return false;
        }else{
            left --;
            requestList.addLast(request);
            return true;
        }
    }


    /**
     * 请求类，属性包含编号字符串、请求达到时间和请求处理时间
     */
    static class Request{
        private int code;
        private Date launchTime;
        private Date handleTime;

        private Request() { }

        public Request(int code,Date launchTime) {
            this.launchTime = launchTime;
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public Date getLaunchTime() {
            return launchTime;
        }

        public void setLaunchTime(Date launchTime) {
            this.launchTime = launchTime;
        }

        public Date getHandleTime() {
            return handleTime;
        }

        public void setHandleTime(Date handleTime) {
            this.handleTime = handleTime;
        }
    }

    public static void main(String[] args) {
        LeakyBucketLimiter leakyBucketLimiter = new LeakyBucketLimiter(5,2);
        for(int i = 1;i <= 10;i ++){
            Request request = new Request(i,new Date());
            if(leakyBucketLimiter.tryAcquire(request)){
                System.out.println(i + "号请求被接受");
            }else{
                System.out.println(i + "号请求被拒绝");
            }
        }
    }

}
