package leetcode.tree;
//给你一个整数数组 nums 以及两个整数 lower 和 upper 。求数组中，值位于范围 [lower, upper] （包含 lower 和
//upper）之内的 区间和的个数 。
//
// 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
//
//
//示例 1：
//
//
//输入：nums = [-2,5,-1], lower = -2, upper = 2
//输出：3
//解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
//
//
// 示例 2：
//
//
//输入：nums = [0], lower = 0, upper = 0
//输出：1
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 10⁵
// -2³¹ <= nums[i] <= 2³¹ - 1
// -10⁵ <= lower <= upper <= 10⁵
// 题目数据保证答案是一个 32 位 的整数
//
// Related Topics 树状数组 线段树 数组 二分查找 分治 有序集合 归并排序 👍 461 👎 0


import leetcode.arrays.BucketSort;
import org.junit.Test;

import java.util.Arrays;

public class _327_区间和的个数 {
    /**
     * 题目的意思，计算元素和落在 [lower, upper] 的所有子数组的个数。
     * 暴力法:2层循环
     */
    class BruteSolution {
        public int countRangSum(int [] nums, int lower, int upper) {
            int res = 0;
            for (int i = 0; i< nums.length; i++) {
                for (int j = i + 1; j <= nums.length; j++) {
                    int sum = Arrays.stream(Arrays.copyOfRange(nums, i, j)).sum();
                    if(sum >= lower && sum <= upper) {
                        res++;
                    }
                }
            }
            return res;
        }
    }

    /**
     * 要实现快速计算子数组的和，可以采用前缀和计算区间和。
     * count[i] = COUNT(j) where lower <= preSum[j] - preSum[i] <= upper
     * 归并算法，本质上都是在遍历一棵（递归）树，然后在节点（前中后序位置）上执行代码，写递归，本质上就是告诉每个节点需要做什么
     * 比如，归并排序算法，递归的sort 函数就是二叉树的遍历函数，而merge函数就是在每个节点上做的事。
     */
    class Solution {
        private int lower, upper;
        private int count;
        private long [] tmp;
        public int countRangeSum(int[] nums, int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
            //构造前缀和, int 可能溢出，用long存储
            long [] preSum = new long [nums.length+1];
            for (int i = 0; i< nums.length; i++) {
                preSum[i+1] += preSum[i] + (long)nums[i];
            }
            //对前缀和数组进行归并排序
            sort(preSum);
            return count;
        }
        private void sort(long [] nums) {
            tmp = new long[nums.length];
            sort(nums, 0, nums.length-1);
        }
        private void sort(long [] nums, int lo, int hi) {
            if (lo == hi) {
                return;
            }
            int mid = lo + (hi-lo)/2;
            sort(nums, lo, mid);
            sort(nums, mid+1, hi);
            merge(nums, lo, mid, hi);
        }

        private void merge(long[] nums, int lo, int mid, int hi) {
            for (int i=lo; i<=hi; i++) {
                tmp[i] = nums[i];
            }
            //在归并前加点东西
            // 下面这段代码会超时
            // for (int i = lo; i<=mid; i++) {
            //     for (int j = mid+1; j<=hi; j++) {
            //         long sum = nums[j] - nums[i];
            //         if (sum < lower || sum > upper) {
            //             continue;
            //         }
            //         count++;
            //     }
            // }

            //进行效率优化
            //维护左闭右开区间 [start, end) 中的元素和 nums[i] 的差在 [lower, upper] 中
            int start = mid+1;
            int end = mid+1;
            for (int i = lo; i <= mid; i++) {
                //如果 nums[i] 对应的区间是 [start, end]
                //那么 nums[i+1] 对应的区间一定会整体右移，类似滑动窗口
                //让窗口中的元素和 nums[i] 的差落在 [lower, upper]中
                while (start <= hi && nums[start]-nums[i] < lower) {
                    start ++;
                }
                while (end <= hi && nums[end] - nums[i] <= upper) {
                    end ++;
                }
                count += end-start;
            }



            //进行归并
            int i = lo, j = mid+1;
            for (int p = lo; p <= hi; p++) {
                if (i == mid+1) {
                    nums[p] = tmp[j++];
                }
                else if (j == hi+1) {
                    nums[p] = tmp[i++];
                }
                else if (tmp[i] > tmp[j]) {
                    nums[p] = tmp[j++];
                }
                else {
                    nums[p] = tmp[i++];
                }
            }

        }
    }

    @Test
    public void test() {
        int [] nums = new int[] {-2,5,-1}; int lower = -2, upper = 2;
        BruteSolution bruteSolution = new BruteSolution();
        int count = bruteSolution.countRangSum(nums, lower, upper);

        System.out.println(count);

    }
}
