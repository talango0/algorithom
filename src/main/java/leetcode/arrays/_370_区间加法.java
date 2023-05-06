package leetcode.arrays;

import org.junit.Assert;

/**
 * Assume you have an array of length n initialized with all 0's and are given k update operations.
 * Each operation is represented as a triplet: [startIndex, endIndex, inc] which increments
 * each element of subarray A[startIndex ... endIndex] (startIndex and endIndex inclusive) with inc.
 *
 * Return the modified array after all k operations were executed.
 * Example:
 *
 * Given:
 *     length = 5,
 *     updates = [
 *         [1,  3,  2],
 *         [2,  4,  3],
 *         [0,  2, -2]
 *     ]
 * Output:
 *     [-2, 0, 3, 5, 3]
 * Explanation:
 *
 * Initial state:
 * [ 0, 0, 0, 0, 0 ]
 *
 * After applying operation [1, 3, 2]:
 * [ 0, 2, 2, 2, 0 ]
 *
 * After applying operation [2, 4, 3]:
 * [ 0, 2, 5, 5, 3 ]
 *
 * After applying operation [0, 2, -2]:
 * [-2, 0, 3, 5, 3 ]
 * Hint:
 *
 * Thinking of using advanced data structures? You are thinking it too complicated.
 * For each update operation, do you really need to update all elements between i and j?
 * Update only the first and end element is sufficient.
 * The optimal time complexity is O(k + n) and uses O(1) extra space.
 */


/**
 * 差分数组：
 * @see _370_区间加法
 * @see _1109_航班预订统计
 * @see _1094_拼车
 */
public class _370_区间加法 {

    static class Solution{
        public int [] getModifyArray(int length, int [][] updates) {
            int[] nums = new int[length];
            Differance differance = new Differance(nums);
            for (int[] update : updates) {
                differance.increment(update[0], update[1], update[2]);
            }
            return differance.result();
        }
    }


    /**
     * <pre>
     * ┌───────────────────────────────────────┐
     * │          nums 8  5  9  6  1           │
     * │          diff 8 -3  4 -3 -5           │
     * │                                       │
     * │          diff[i]=nums[i]-nums[i-1]    │
     * │  update:(i,j,val)                     │
     * │          diff[i]+=val                 │
     * │          diff[j+1]-=val               │
     * │  restore:nums[i]=diff[i-1]+diff[i]    │
     * └───────────────────────────────────────┘
     * 差分数组的主要适用场景是频繁对原始数组的某个区间的元素进行增减。
     * 比如说，我给你输入一个数组 nums，然后又要求给区间 nums[2..6] 全部加 1，
     * 再给 nums[3..9] 全部减 3，再给 nums[0..4] 全部加 2，再给…
     *
     * 一通操作猛如虎，然后问你，最后 nums 数组的值是什么？
     *
     * 常规的思路很容易，你让我给区间 nums[i..j] 加上 val，那我就一个 for 循环给它们都加上呗，还能咋样？
     * 这种思路的时间复杂度是 O(N)， 由于这个场景下对 nums 的修改非常频繁，所以效率会很低下。
     *
     * 这里就需要差分数组的技巧，类似前缀和技巧构造的 prefix 数组，
     * 我们先对 nums 数组构造一个 diff 差分数组，
     * diff[i] 就是 nums[i] 和 nums[i-1] 之差：
     *
     * {@code
     * int[] diff = new int[nums.length];
     * // 构造差分数组
     * diff[0] = nums[0];
     * for (int i = 1; i < nums.length; i++) {
     *     diff[i] = nums[i] - nums[i - 1];
     * }
     * }
     *
     * 通过这个 diff 差分数组是可以反推出原始数组 nums 的，代码逻辑如下：
     * {@code
     * int[] res = new int[diff.length];
     * // 根据差分数组构造结果数组
     * res[0] = diff[0];
     * for (int i = 1; i < diff.length; i++) {
     *     res[i] = res[i - 1] + diff[i];
     * }
     * }
     *
     * </pre>
     */
    //差分数组工具类
    static class Differance {
        //差分数组
        private int [] diff;
        //输入一个初始数组，区间操作将在这个数组进行
        public Differance(int [] nums){
            assert nums.length> 0;
            diff = new int[nums.length];
            diff[0] = nums[0];
            for (int i = 1; i<nums.length; i++){
                diff[i] = nums[i] - nums[i-1];
            }
        }

        //给数组nums[i,...,j] 增加 val,（val 可为负数）
        public void increment(int i, int j, int val) {
            diff[i] += val;
            //当j+1>= diff.length 时，说明对 nums[i] 以后的整个数组都要进行修改，这时不需要在给diff减 val
            if ( j+1 < diff.length ) {
                diff[j+1] -= val;
            }
        }

        //返回结果数组
        public int [] result(){
            int [] res = new int [diff.length];
            res[0] = diff[0];
            for (int i = 1; i<diff.length; i++) {
                res[i] = res[i-1] + diff[i];
            }
            return res;
        }
    }

    public static void main(String[] args) {
        int length = 5;
        int [][]updates = {
                {1,  3,  2},
                {2,  4,  3},
                {0,  2, -2}
        };
        Solution solution = new Solution();
        int[] modifyArray = solution.getModifyArray(length, updates);
        Assert.assertArrayEquals(new int[]{-2, 0, 3, 5, 3}, modifyArray);
    }
}
