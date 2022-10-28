package leetcode.slidewindow;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author mayanwei
 * @date 2022-10-28.
 */
public class _346_滑动窗口平均值{
    class MovingAverage {
        Deque<Integer> deque;
        int windowSize = 0;
        int sum ;

        /** Initialize your data structure here. */
        public MovingAverage(int size) {
            deque = new LinkedList<>();
            windowSize = size;
            sum = 0;
        }

        public double next(int val) {
            deque.offer(val);
            sum += val;
            if (deque.size() > windowSize) {
                int d = deque.poll();
                sum -= d;
            }
            return (double)sum / deque.size();
        }
    }



}
