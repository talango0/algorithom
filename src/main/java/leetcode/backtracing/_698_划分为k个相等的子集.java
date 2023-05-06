package leetcode.backtracing;
//给定一个整数数组 nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
//
//
//
// 示例 1：
//
//
//输入： nums = [4, 3, 2, 3, 5, 2, 1], k = 4
//输出： True
//说明： 有可能将其分成 4 个子集（5），（1,4），（2,3），（2,3）等于总和。
//
// 示例 2:
//
//
//输入: nums = [1,2,3,4], k = 3
//输出: false
//
//
//
// 提示：
//
//
// 1 <= k <= len(nums) <= 16
// 0 < nums[i] < 10000
// 每个元素的频率在 [1,4] 范围内
//
//
// Related Topics 位运算 记忆化搜索 数组 动态规划 回溯 状态压缩 👍 630 👎 0

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author mayanwei
 * @date 2022-08-04.
 */
public class _698_划分为k个相等的子集{
    class Solution {
        public boolean canPartitionKSubsets(int[] nums, int k) {
            // 排除一些基本情况
            if (k > nums.length) {
                return false;
            }
            int sum = 0;
            for (int v: nums) {
                sum += v;
            }
            if (sum % k != 0) {
                return false;
            }
            boolean [] used = new boolean[nums.length];
            int target = sum / k;
            // k 号同初始什么都没装，从 nums[0] 开始做选择
            return backtrace(k, 0, nums, 0, used, target);
        }

        // backtrace 定义：现在 k 号桶正在思考是否应该把 nums[start] 这个元素装进来；
        // 目前 k 号桶里面已经装的数字之和为 bucket；
        // used 标志某一个元素是否已经被装到桶中；
        // target 是每个桶需要达成的目标和。
        // 从桶的视角：
        // 1. 需要遍历 nums 中的所有数字，决定哪些数字需要装到当前桶中
        // 2. 如果当前桶装满了（桶内的数字达到 target），则让下一个桶开始执行第1步
        boolean backtrace(int k, int bucket, int [] nums, int start, boolean [] used, int target) {
            // base case
            if (k == 0) {
                // 所有的桶都装满了，而且 nums 一定全部用完了
                // 因为 target = sum/k
                return true;
            }

            if (bucket == target) {
                // 装满了当前桶，递归穷举下一个桶的选择
                // 让下一个桶从 nums[0] 开始选数字
                return backtrace(k-1, 0, nums, 0, used, target);
            }

            // 从 start 开始向后探查有效的 num[i] 装入当前桶
            for (int i = start; i < nums.length; i++) {
                // 剪枝
                if (used[i]) {
                    // nums[i] 已经被装入别的桶中
                    continue;
                }
                if (nums[i] + bucket > target) {
                    // 当前桶装不下
                    continue;
                }
                // 做选择，将 num[i] 装入当前桶中
                used[i] = true;
                bucket += nums[i];
                // 递归穷举下一个数字是否装入当前桶
                if (backtrace(k, bucket, nums, i+1, used, target)) {
                    return true;
                }

                // 撤销选择
                used[i] = false;
                bucket -= nums[i];
            }
            // 穷举所有数字，都无法装满当前桶
            return false;
        }
    }


    // 带备忘录
    class Solution2 {
        public boolean canPartitionKSubsets(int[] nums, int k) {
            // 排除一些基本情况
            if (k > nums.length) {
                return false;
            }
            int sum = 0;
            for (int v: nums) {
                sum += v;
            }
            if (sum % k != 0) {
                return false;
            }
            boolean [] used = new boolean[nums.length];
            int target = sum / k;
            // k 号同初始什么都没装，从 nums[0] 开始做选择
            return backtrace(k, 0, nums, 0, used, target);
        }

        // 备忘录，存储 used 数组的状态
        HashMap<String, Boolean> memo = new HashMap<>();

        // backtrace 定义：现在 k 号桶正在思考是否应该把 nums[start] 这个元素装进来；目前 k 号桶里面已经装的数字之和为 bucket；used 标志某一个元素是否已经被装到桶中；target 是每个桶需要达成的目标和。
        // 从桶的视角：
        // 1. 需要遍历 nums 中的所有数字，决定哪些数字需要装到当前桶中
        // 2. 如果当前桶装满了（桶内的数字达到 target），则让下一个桶开始执行第1步
        boolean backtrace(int k, int bucket, int [] nums, int start, boolean [] used, int target) {
            // base case
            if (k == 0) {
                // 所有的桶都装满了，而且 nums 一定全部用完了
                // 因为 target = sum/k
                return true;
            }

            // 将used的状态转化为字符串，便于存入 HashMap
            String state = Arrays.toString(used);

            if (bucket == target) {
                // 装满了当前桶，递归穷举下一个桶的选择
                // 让下一个桶从 nums[0] 开始选数字
                boolean res =  backtrace(k-1, 0, nums, 0, used, target);
                // 将当前状态和结果存入备忘录
                memo.put(state, res);
                return res;
            }

            if (memo.containsKey(state)) {
                // 如果当前状态曾经计算过，就直接返回，不要在递归穷举了
                return memo.get(state);
            }

            // 从 start 开始向后探查有效的 num[i] 装入当前桶
            for (int i = start; i < nums.length; i++) {
                // 剪枝
                if (used[i]) {
                    // nums[i] 已经被装入别的桶中
                    continue;
                }
                if (nums[i] + bucket > target) {
                    // 当前桶装不下
                    continue;
                }
                // 做选择，将 num[i] 装入当前桶中
                used[i] = true;
                bucket += nums[i];
                // 递归穷举下一个数字是否装入当前桶
                if (backtrace(k, bucket, nums, i+1, used, target)) {
                    return true;
                }

                // 撤销选择
                used[i] = false;
                bucket -= nums[i];
            }
            // 穷举所有数字，都无法装满当前桶
            return false;
        }
    }


    /**
     * 利用位图作为memo优化提速
     */
    class Solution3 {
        // 因为每次递归都要把 used 数组转化成字符串，这对于编程语言来说也是一个不小的消耗，所以我们还可以进一步优化。
        // 注意题目给的数据规模 nums.length <= 16，也就是说 used 数组最多也不会超过 16，那么我们完全可以用「位图」的技巧，用一个 int 类型的 used 变量来替代 used 数组。
        // 具体来说，我们可以用整数 used 的第 i 位（(used >> i) & 1）的 1/0 来表示 used[i] 的 true/false。
        public boolean canPartitionKSubsets(int[] nums, int k) {
            // 排除一些基本情况
            if (k > nums.length) {
                return false;
            }
            int sum = 0;
            for (int v: nums) {
                sum += v;
            }
            if (sum % k != 0) {
                return false;
            }
            //使用位图技巧
            int used = 0;
            int target = sum / k;
            // k 号同初始什么都没装，从 nums[0] 开始做选择
            return backtrace(k, 0, nums, 0, used, target);
        }

        // 备忘录，存储 used 数组的状态
        HashMap<Integer, Boolean> memo = new HashMap<>();

        // backtrace 定义：现在 k 号桶正在思考是否应该把 nums[start] 这个元素装进来；目前 k 号桶里面已经装的数字之和为 bucket；used 标志某一个元素是否已经被装到桶中；target 是每个桶需要达成的目标和。
        // 从桶的视角：
        // 1. 需要遍历 nums 中的所有数字，决定哪些数字需要装到当前桶中
        // 2. 如果当前桶装满了（桶内的数字达到 target），则让下一个桶开始执行第1步
        boolean backtrace(int k, int bucket, int [] nums, int start, int used, int target) {
            // base case
            if (k == 0) {
                // 所有的桶都装满了，而且 nums 一定全部用完了
                // 因为 target = sum/k
                return true;
            }


            if (bucket == target) {
                // 装满了当前桶，递归穷举下一个桶的选择
                // 让下一个桶从 nums[0] 开始选数字
                boolean res =  backtrace(k-1, 0, nums, 0, used, target);
                // 将当前状态和结果存入备忘录
                memo.put(used, res);
                return res;
            }

            if (memo.containsKey(used)) {
                // 如果当前状态曾经计算过，就直接返回，不要在递归穷举了
                return memo.get(used);
            }

            // 从 start 开始向后探查有效的 num[i] 装入当前桶
            for (int i = start; i < nums.length; i++) {
                // 剪枝
                if (((used >> i) & 1) == 1) {
                    // nums[i] 已经被装入别的桶中
                    continue;
                }
                if (nums[i] + bucket > target) {
                    // 当前桶装不下
                    continue;
                }
                // 做选择，将 num[i] 装入当前桶中
                used  |= 1<< i;
                bucket += nums[i];
                // 递归穷举下一个数字是否装入当前桶
                if (backtrace(k, bucket, nums, i+1, used, target)) {
                    return true;
                }

                // 撤销选择
                used ^= 1<<i;
                bucket -= nums[i];
            }
            // 穷举所有数字，都无法装满当前桶
            return false;
        }
    }
}
