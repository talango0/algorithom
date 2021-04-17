package leetcode.jzhoffer;


import java.util.Comparator;
import java.util.PriorityQueue;

/**
 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
 如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。我们使用Insert()方法读取数据流，
 使用GetMedian()方法获取当前读取数据的中位数。
 */
public class JZ63数据流中的中位数 {


    static public class Solution {
        /*
        思路，
        1. 通过堆来实现，要求堆的左右分支个数之差小于1
        2. 采用两个优先队列PriorityQueue,一个是最小优先队列，一个是最大优先队列。
            插入第奇数个数据时，优先插入最小优先队列；插入之前需要判断num和最大优先队列的第一元素元max0进行比较
            插入第偶数个数据时，优先插入最小优先队列；插入之前需要判断num和最大优先队列的第一个元素min0进行比较
        3. 获取数据时：
            如果为奇数，则直接返回最小有效队列的第一个元素；
            如果为偶数，则直接返回两个队列的第一个元素的均值。
         */


        int cnt = 0;
        PriorityQueue <Integer> minPriorityQueue = new PriorityQueue<>();
        PriorityQueue <Integer> maxPriorityQueue = new PriorityQueue(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        public void Insert(Integer num) {
            if(num == null){
                return;
            }
            cnt ++ ;
            //如果为奇数，存入最大优先队列
            if((cnt&1) == 1){

                if(!minPriorityQueue.isEmpty() && (Integer)minPriorityQueue.peek() < num){
                    minPriorityQueue.offer(num);
                    num = (Integer) minPriorityQueue.poll();
                }
                maxPriorityQueue.offer(num);

            }else{
                //如果为偶数，存入最小优先队列
                if(!maxPriorityQueue.isEmpty() && (Integer) maxPriorityQueue.peek() > num){
                    maxPriorityQueue.offer(num);
                    num = (Integer) maxPriorityQueue.poll();
                }
                minPriorityQueue.offer(num);

            }

        }


        public Double GetMedian() {
            if((cnt & 1) == 1){
                Integer media = (Integer) maxPriorityQueue.peek();
                return Double.valueOf(media);
            }else {
                Integer min0 = (Integer) minPriorityQueue.peek();
                Integer max0 = (Integer) maxPriorityQueue.peek();
                if(min0 != null && max0 != null){
                    return (Double.valueOf(min0)+Double.valueOf(max0))/2.0;
                }
            }
            return null;
        }

    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int [] num = {1,2};
        for(int n :num){
            solution.Insert(n);
        }
        Double media = solution.GetMedian( );
        System.out.println(media );


    }
}
