package leetcode.jzhoffer;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class JZ29最小的K个数 {
    /*
题目描述
输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
     */

    public class Solution {
        public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
            if(input == null || k>input.length){
                return new ArrayList<>();
            }
            PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o1-o2);
            for (int num : input) {
                queue.offer(num);
            }
            ArrayList<Integer> res = new ArrayList<>();
            for(int i= 0; i<5; i++){
                if(queue.isEmpty()){
                    break;
                }else {
                    res.add(queue.poll());
                }
            }
            return res;
        }
    }
}
