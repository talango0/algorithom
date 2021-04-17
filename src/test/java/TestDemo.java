import common.ListNode;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class TestDemo implements Job{
    private static Logger log = LoggerFactory.getLogger(TestDemo.class);
    private static int count = 0;


    static  class  People{
        int age;
        String name;

        public List<Integer> prv = new ArrayList<Integer>();

        public People() {

        }

        public List<Integer> getPrv() {
            return prv;
        }

        public void setPrv(List<Integer> prv) {
            this.prv = prv;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public People(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "People{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", prv=" + prv +
                    '}';
        }
    }
    public void TestPeople(){
        People wang = new People(12, "wang");
        wang.getPrv().add(14);
        People ma = new People(13, "ma");
        ma.getPrv().add(10);
        List<People> list = new ArrayList<People>();
        list.add(wang );
        list.add(ma);
        Collections.sort(list, new Comparator<People>() {
            public int compare(People o1, People o2) {
//                return o1.getAge()-o2.getAge();
//                return  o2.getAge()-o1.getAge();
                return o2.getPrv().get(0)-o1.getPrv().get(0);
            }
        });
        System.out.println(list.toString());
    }



    @Test
    public void testDeadLock(){
        Object o1 = new Object();
        Object o2 = new Object();
        Thread t1 = new Thread(
                ()-> {
                synchronized (o1){

                    try {
                        System.out.println(Thread.currentThread().getName()+" has got 01");
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2){
                        try {
                            System.out.println(Thread.currentThread().getName() + " has got 02");
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        },"thread1");
        Thread t2 =  new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o2){
                    System.out.println(Thread.currentThread().getName()+" has got 02");
                    synchronized (o1){
                        System.out.println(Thread.currentThread().getName() + " has got 01");
                    }
                }
            }
        },"thread2");
        t1.start();
        t2.start();
    }


    @Test
    public void testComsumerProducer(){
        new Thread(()->{
            while (true) {
                synchronized (TestDemo.class){
                    if(count == 5){
                        try {
                            System.out.println(Thread.currentThread().getName()+" plate is full,waiting for consuming!");
                            TestDemo.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(500);
                        count += 1;
                        System.out.println(Thread.currentThread().getName()+ " has produced one" +"count=" +count);

                        TestDemo.class.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"producer").start();
        new Thread(()->{
           while (true){
               synchronized (TestDemo.class){
                   if(count == 0){
                       try {
                           System.out.println(Thread.currentThread().getName()+" plate is empty,waiting for producing!");
                           TestDemo.class.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
                   try {
                       Thread.sleep(500);
                       count -= 1;
                       System.out.println(Thread.currentThread().getName()+ " has consumed one" +"count=" +count);

                       TestDemo.class.notifyAll();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

               }

           }
        },"consumer").start();
    }

    @Test
    public  void testRemoveItemFromCollection(){
        List<String> strings = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();

            if (next.equals("a")){
                iterator.remove();
            }
        }
        System.out.println(strings.toString());
    }



    private  static TestDemo instance = null;
    public static  TestDemo getInstance(){
        if(instance == null){
           instance = new TestDemo();
           return  instance;
        }
        else {
            return  instance;
        }
    }

    /**
     * 定时任务
     */
    @Test
    public void testQuartz() throws SchedulerException, IOException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        scheduler.start();
        Class clazz = null;

        try {
            clazz = Class.forName("TestDemo");
            JobDetail job = newJob(clazz).withIdentity("TestDemo").build();
            CalendarIntervalTrigger tigger = newTrigger().startAt(Calendar.getInstance().getTime())
                    .withIdentity("TestDemo")
                    .withSchedule(calendarIntervalSchedule()
                    .withIntervalInSeconds(5)).build();
            scheduler.scheduleJob(job, tigger);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(clazz);
        System.in.read();
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Hello,world,This is my first Quartz");
    }


    /**
     * 线程池
     */
    @Test
    public void testThreadPool(){


    }

    private class TaskInfo {
        private String Id;              //主键
        private String appId;           //应用ID
        private String appName;         //应用名
        private String taskName;        //任务名称
        private Date startTime;         //定时任务开始时间
        private Date endTime;           //定时任务结束时间
        private String intervalTime;    //定时任务间隔时间
        private String intervalUnit;    //定时任务间隔单元
        private String operateUrl;      //任务操作URL
        private String operateClass;    //任务操作类
        private Date lastRunTime;      //定时任务上次执行的时间
        private String memo;    //备注
        private String creator; //创建者账号
        private Date createTime;//创建日期
        private Date updateTime;//最后更新时间

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public String getIntervalTime() {
            return intervalTime;
        }

        public void setIntervalTime(String intervalTime) {
            this.intervalTime = intervalTime;
        }

        public String getIntervalUnit() {
            return intervalUnit;
        }

        public void setIntervalUnit(String intervalUnit) {
            this.intervalUnit = intervalUnit;
        }

        public String getOperateUrl() {
            return operateUrl;
        }

        public void setOperateUrl(String operateUrl) {
            this.operateUrl = operateUrl;
        }

        public String getOperateClass() {
            return operateClass;
        }

        public void setOperateClass(String operateClass) {
            this.operateClass = operateClass;
        }

        public Date getLastRunTime() {
            return lastRunTime;
        }

        public void setLastRunTime(Date lastRunTime) {
            this.lastRunTime = lastRunTime;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }
    }

//    @Test
//    public Calendar futureDate(Calendar calendar, TaskInfo taskInfo, int unit){
//        Calendar instance = Calendar.getInstance();
//        instance.setLenient(true);
//        return calendar;
//    }
//    public static void main(String[] args) {
//        /**
//         * testComsumerProducer
//         */
////        TestDemo demo = new TestDemo();
//////        demo.testComsumerProducer();
////        ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
////        executor.execute();
//    //        getInstance();
////        try {
////            demo.testQuartz();
////        } catch (SchedulerException e) {
////            e.printStackTrace();
////        }
//        int a  = 2;
//        int b =-5;
////        System.out.println(Integer.toBinaryString(b));
////        System.out.println(Integer.toBinaryString(a));
////        float c =  0.33f;
////        System.out.println(Power(a,b));
//
//        int abs = Math.abs(b);
//        abs = abs>>1;
//        System.out.println(abs);
//
//    }
    public static double Power(double base, int exponent) {
        if(exponent==0)return 1.0;
        else if(exponent == 1)return base;
        if(base == 0)return 0;
        float res = 1.0f;
        for(int i=0,n = Math.abs(exponent); i<n; i++){
            res *= base;
        }
        if(exponent<0)return 1.0/res;
        return res;


//        long p = Math.abs((long)exponent);
//        double r = 1.0;
//        while(p != null){
//            if(p & 1) r *= base;
//            base *= base;
//            p >>= 1;
//        }
//        return exponent < 0 ? 1/ r : r;
    }


    @Test
    public void testHashMap_HashTable(){
        Map<String, String> map = new HashMap<String, String>();//HashMap对象
        Map<String, String> tableMap = new Hashtable<String, String>();//HashTable对象

        map.put(null, null);
        map.put(null,"3");
        System.out.println("hashMap的[key]和[value]均可以为null:" + map.get(null));

//        try {
//            tableMap.put(null, "3");
//            System.out.println(tableMap.get(null));
//        } catch (Exception e) {
//            System.out.println("【ERROR】：hashTable的[key]不能为null");
//        }

//        try {
//            tableMap.put("3", null);
//            System.out.println(tableMap.get("3"));
//        } catch (Exception e) {
//            System.out.println("【ERROR】：hashTable的[value]不能为null");
//        }

    }



    @Test
    public void testPriorityQueue(){
        Queue<Integer> queue = new PriorityQueue<>();
        queue.offer(3);
        queue.offer(2);

        queue.offer(1);
        queue.poll();


        queue.offer(5);
        queue.poll();

        queue.offer(6);
        queue.poll();

        queue.offer(4);
        queue.poll();



        System.out.println(Arrays.toString(queue.toArray()));
    }


    @Test
    public void testArraysToString(){
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(3);
//        list.add(4);
//        HashSet<String> set = new HashSet<>();
//        System.out.println(list.toString());
//        if(set.contains(list.toString())){
//            System.out.println(true);
//        }else {
//            set.add(list.toString());
//            System.out.println(set.contains(list.toString()));
//        }

        HashSet<Integer> set = new HashSet<>();
        set.add(1);
        set.add(3);
        Integer [] arr = new Integer[3];
        set.toArray(arr);
        int[] ints = set.stream().mapToInt(Integer::intValue).toArray();
//        System.out.println(Arrays.toString(ints));


        Integer[] s =null;
        s = Arrays.copyOf(arr,arr.length);
        System.out.println(s.length);
        s = Arrays.copyOfRange(arr,0,2);
        System.out.println(s.length);
    }
//    class Solution {
//        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//            ListNode res = l1;
//            int carry = 0;
//            while (l1 != null && l2 != null) {
//                int sum = l1.val + l2.val + carry;
//                l1.val = sum % 10;
//                carry = sum / 10;
//                if(l1.next == null){
//                    if(l2.next == null){
//                        if(carry>0){
//                            l1.next = new ListNode(carry);
//                            carry = 0;
//                        }
//                    }else {
//                        l1.next = l2.next;
//                        l1 = l1.next;
//                        break;
//                    }
//                }
//                l1 = l1.next;
//                l2 = l2.next;
//            }
//            while (l1 != null){
//                int sum = l1.val + carry;
//                l1.val = sum % 10;
//                carry = sum / 10;
//                if(l1.next == null && carry>0){
//                    l1.next = new ListNode(carry);
//                    carry = 0;
//                }
//                l1 = l1.next;
//            }
//            return res;
//        }
//    }
//    @Test
//    public void testLongest(){
//        ListNode l1 = new ListNode(5);
//        ListNode l2 = new ListNode(5);
//        l2.next = new ListNode(8);
//
//        Solution solution = new Solution();
//        ListNode listNode = solution.addTwoNumbers(l1, l2);
//        System.out.println();
//    }


    @Test
    public void testNewArrayList(){
        int[] a = {1,2,3};
//        Integer [] integers = Arrays.stream(a).boxed().toArray(Integer[]::new);
//        System.out.println(Arrays.asList(integers));

    }


//    class Solution {
//        private HashSet<String> set = new HashSet<String>();
//        private List<List<Integer>> res = new ArrayList<>();
//        private List<Integer> tmp = null;
//        private boolean [] visit = null;
//        public List<List<Integer>> permuteUnique(int[] nums) {
//            if(nums != null){
//                Arrays.sort(nums);
//                visit= new boolean[nums.length];
//                backtrack(nums, nums.length, 0);
//            }
//            return res;
//        }
//
//        public void swap(int [] nums, int i, int j){
//            int tmp = nums[i];
//            nums[i] = nums[j];
//            nums[j] = tmp;
//        }
//
//        public void backtrack(int [] nums, int n, int first){
//            if(n == first){
//                tmp = new ArrayList<>(n);
//                for (int num : nums) {
//                    tmp.add(num);
//                }
//                res.add(tmp);
//            }
//            Set<Integer> set = new HashSet<Integer>();
//            for(int i = first; i< n;i++){
//                if(set.contains(nums[i])){
//                    continue;
//                }else{
//                    set.add(nums[i]);
//                }
//                swap(nums, i, first);
//                backtrack(nums, n, first+1);
//                swap(nums, i, first);
//            }
//        }
//    }

//    @Test
//    public void testBackTrack(){
//        Solution solution = new Solution();
//        int []a = {1,1,2};
//        List<List<Integer>> permute = solution.permuteUnique(a);
//        System.out.println(permute.toString());
//    }


    public class Solution {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            if(headA == null || headB == null){
                return null;
            }
            ListNode p= headA,q = headB;
            while ((p!=null && p.next != null )||( q != null && q.next != null)){
                p = p.next;
                q = q.next;
            }
            if(p.next == null){
                p.next = headB;
            }
            if(q.next == null){
                q.next = headA;
            }
            while (q != null || p!=null ){
                if(q.next == null){
                    q.next = headA;
                }
                if(p.next == null){
                    p.next = headB;
                }
                if(p.val == q.val){
                    return q;
                }
                p = p.next;
                q = q.next;
            }
            return null;
        }
    }


//    @Test
//    public void testLongest(){
//        ListNode l1 = new ListNode(4);
//        l1.next = new ListNode(1);
//        l1.next.next = new ListNode(8);
//        l1.next.next.next = new ListNode(4);
//        l1.next.next.next.next = new ListNode(5);
//        ListNode l2 = new ListNode(1);
//        l2.next = new ListNode(5);
//        l2.next.next = new ListNode(0);
//        l2.next.next.next = new ListNode(1);
//        l2.next.next.next.next = new ListNode(8);
//        l2.next.next.next.next.next = new ListNode(4);
//        l2.next.next.next.next.next.next = new ListNode(6);
//
//        Solution solution = new Solution();
//        ListNode listNode = solution.getIntersectionNode(l1, l2);
//        System.out.println();
//    }


    /**
     * 超时ArrayList的byg
     */
    @Test
    public void initByCollection(){
        //Arrays.asList()返回的是具体的类型
        List<String> list = Arrays.asList("hello,world");
        Object[] objects = list.toArray();
        log.info(objects.getClass().getSimpleName());//===> String[]
        //objects 元素的类型是String，存储Object就会报错，因为JVM不清楚你存储的
        objects[0] = new Object();
    }


}
