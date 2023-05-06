package leetcode.arrays;


//给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
//
//
//
// 示例 1:
//
// 输入: [2,3,-2,4]
//输出: 6
//解释:子数组 [2,3] 有最大乘积 6。
//
//
// 示例 2:
//
// 输入: [-2,0,-1]
//输出: 0
//解释:结果不能为 2, 因为 [-2,-1] 不是子数组。
// Related Topics 数组 动态规划
// 👍 833 👎 0

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class _152_乘积最大子数组 {



    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int maxProduct(int[] nums) {
            int ans = Integer.MIN_VALUE;
            if(nums == null || nums.length == 0){
                return ans;
            }

            int len = nums.length;
            for(int l = 1; l <= len; l++){
                for(int i = 0; i<len-l+1; i++){
                    int tmp = 1;
                    for(int j = i; j < i+l; j++){
                        tmp *= nums[j];
                    }
                    ans = Math.max(ans, tmp);
                }
            }
            return ans;
        }
    }

    /**
     *<pre>
     * 如果当前是一个负数， 则希望以它一个位置结尾的某个段的积也是一个负数，
     * 这样就可以得到乘积的最大值，并且希望尽可能的小，即最小值。
     *
     * 如果当前的值时一个正数，则希望它前一个位置结尾的某个段的积也是正数，并且希望它尽可能地大。即最大值。
     *于是：
     *
     *      f_{max}(i) = max_\limits_{i =1}^{n}{f_{max}(i-1) * a_i, f_{min} (i-1)* a_i, a_i}
     *      f_{min}(i) = min_\limit_{i=1}^{n} {f_{max}(i-1) * a_i, f_{min}(i-1)* a_i, a_i}
     *</pre>
     */
    class Solution2 {
        public int maxProduct(int[] nums) {
            int maxF = nums[0], minF = nums[0], ans = nums[0];
            int length = nums.length;
            for (int i = 1; i < length; ++i) {
                //记录num[i]之前的最小值和最大值
                int mx = maxF, mn = minF;
                maxF = Math.max(mx * nums[i], Math.max(nums[i], mn * nums[i]));
                minF = Math.min(mn * nums[i], Math.min(nums[i], mx * nums[i]));
                ans = Math.max(maxF, ans);
            }
            return ans;
        }
    }

    public void testSolution(){
        Solution solution = new Solution();
        int ans = solution.maxProduct(new int[]{-2});
        System.out.println(ans);

        Solution2 solution2 = new Solution2();
        int i = solution2.maxProduct(new int[]{2, 3 , -2, 4});
        System.out.println(i);

    }

}
