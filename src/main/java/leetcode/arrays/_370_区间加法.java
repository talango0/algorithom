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
 * @see _1109_航班预订统计
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
                diff[i] = nums[i] = nums[i-1];
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
