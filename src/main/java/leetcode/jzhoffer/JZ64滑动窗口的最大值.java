package leetcode.jzhoffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，
 他们的最大值分别为{4,4,6,6,6,5}； 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
 {[2,3,4],2,6,2,5,1}，
 {2,[3,4,2],6,2,5,1}，
 {2,3,[4,2,6],2,5,1}，
 {2,3,4,[2,6,2],5,1}，
 {2,3,4,2,[6,2,5],1}，
 {2,3,4,2,6,[2,5,1]}。
 */
public class JZ64滑动窗口的最大值 {
    public static class Solution {
        public ArrayList<Integer> maxInWindows(int [] num, int size){
            if(num == null|| num.length<0 || size>num.length || size<1){
                return new ArrayList<>(0);
            }
            ArrayList<Integer> res = new ArrayList<>();

            for(int i=0;i<(num.length-size+1);i++){
                int max = Arrays.stream(Arrays.copyOfRange(num, i, i + size)).max().getAsInt();
                res.add(max);
            }
            return res;
        }

    }

    static class Solution2{
        public int[] maxSlidingWindow(int[] nums, int k) {
            if(nums.length == 0 || k == 0) return new int[0];
            Deque<Integer> deque = new LinkedList<>();
            int[] res = new int[nums.length - k + 1];
            // 未形成窗口
            for(int i = 0; i < k; i++) {
                while(!deque.isEmpty() && deque.peekLast() < nums[i])
                    deque.removeLast();
                deque.addLast(nums[i]);
            }
            res[0] = deque.peekFirst();
            // 形成窗口后
            for(int i = k; i < nums.length; i++) {
                if(deque.peekFirst() == nums[i - k])
                    deque.removeFirst();
                while(!deque.isEmpty() && deque.peekLast() < nums[i])
                    deque.removeLast();
                deque.addLast(nums[i]);
                res[i - k + 1] = deque.peekFirst();
            }
            return res;

        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int [] num = {2,3,4,2,6,2,5,1};

        ArrayList<Integer> res = solution.maxInWindows(num, 3);
        System.out.println(res.toString());


    }
}
