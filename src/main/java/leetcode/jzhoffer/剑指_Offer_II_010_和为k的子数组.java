package leetcode.jzhoffer;
//给定一个整数数组和一个整数k ，请找到该数组中和为k的连续子数组的个数。
//
//
//
//示例 1：
//
//输入:nums = [1,1,1], k = 2
//输出: 2
//解释: 此题 [1,1] 与 [1,1] 为两种不同的情况
//示例 2：
//
//输入:nums = [1,2,3], k = 3
//输出: 2
//
//
//提示:
//
//1 <= nums.length <= 2 * 104
//-1000 <= nums[i] <= 1000
//-107<= k <= 107
//
//
//
//注意：本题与主站 560题相同：https://leetcode-cn.com/problems/subarray-sum-equals-k/
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/QTMn0o
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.arrays._560_和为K的子数组;

import java.util.HashMap;
import java.util.Map;

/**
 * @see _560_和为K的子数组
 */
public class 剑指_Offer_II_010_和为k的子数组 {
    class Solution {
        public int subarraySum(int[] nums, int k) {
            int n = nums.length;
            int [] preSum = new int[n+1];
            for(int i = 0; i< n; i++) {
                preSum[i+1] = preSum[i] + nums[i];
            }
            Map<Integer, Integer> map = new HashMap<>();
            int ans = 0;
            for (int i = 0; i < preSum.length; i++) {
                ans += map.getOrDefault(preSum[i] - k, 0);
                map.put(preSum[i], map.getOrDefault(preSum[i], 0) + 1);
            }
            return ans;
        }
    }
}
