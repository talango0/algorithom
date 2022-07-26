package leetcode.design.monotonous.queue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
//给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
//
//返回 滑动窗口中的最大值 。
//
//示例 1：
//
//输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
//输出：[3,3,5,5,6,7]
//解释：
//滑动窗口的位置                最大值
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7
//示例 2：
//
//输入：nums = [1], k = 1
//输出：[1]
//提示：
//
//1 <= nums.length <= 105
//-104 <= nums[i] <= 104
//1 <= k <= nums.length
//Related Topics
//队列
//数组
//滑动窗口
//单调队列
//堆（优先队列）
//
//👍 1752
//👎 0


/**
 * 单调队列结构解决滑动窗口问题
 * @author mayanwei
 * @date 2022-07-23.
 */
public class _239_滑动窗口最大值{
    class Solution {
        public int[] maxSlidingWindow(int[] nums, int k) {
            MonotonicQueue window = new MonotonicQueue();
            List<Integer> res = new ArrayList<>();

            for (int i = 0; i < nums.length; i++) {
                if (i < k-1) {
                    // 先把窗口的前 k-1 填满
                    window.push(nums[i]);
                }
                else {
                    // 窗口开始向前滑动
                    // 移入新的元素
                    window.push(nums[i]);
                    // 将当前窗口中的最大元素计入结果
                    res.add(window.max());
                    // 移除最后的元素
                    window.pop(nums[i-k+1]);
                }
            }

            // 将 list 类型转化为 int[] 数组作为返回值
            int [] arr = new int[res.size()];
            for (int i = 0; i<res.size(); i++) {
                arr[i] = res.get(i);
            }
            return arr;
        }

        /**
         * 实现单调栈的思路：
         * 1. 必须使用一种数据结构支持头部和尾部的插入和删除，很明显双链表是满足这个条件的
         * 2. 单调队列和单调栈类似，push方法依然在队尾添加元素，但是要把前面比自己小的元素都删除。
         */
        class MonotonicQueue {
            // 双链表，支持头部和尾部增删元素
            // 维护其中的元素自尾部到头部单调递增
            private LinkedList<Integer> maxq = new LinkedList<>();
            // 在尾部添加一个元素 n，维护 maxq 的单调性质
            public void push(int n) {
                // 将前面小于 n 的元素都删除,小于n的都压扁了
                while (!maxq.isEmpty() && maxq.getLast() < n) {
                    maxq.pollLast();
                }
                maxq.addLast(n);
            }

            public int max() {
                return maxq.peekFirst();
            }

            public void pop(int n) {
                if (n == maxq.peekFirst()) {
                    maxq.pollFirst();
                }
            }


        }
    }
}
