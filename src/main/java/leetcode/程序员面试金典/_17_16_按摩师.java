package leetcode.程序员面试金典;
//一个有名的按摩师会收到源源不断的预约请求，每个预约都可以选择接或不接。在每次预约服务之间要有休息时间，因此她不能接受相邻的预约。
// 给定一个预约请求序列，替按摩师找到最优的预约集合（总预约时间最长），返回总的分钟数。
//
//注意：本题相对原题稍作改动
//
//示例 1：
//输入： [1,2,3,1]
//输出： 4
//解释： 选择 1 号预约和 3 号预约，总时长 = 1 + 3 = 4。
//
//示例 2：
//输入： [2,7,9,3,1]
//输出： 12
//解释： 选择 1 号预约、 3 号预约和 5 号预约，总时长 = 2 + 9 + 1 = 12。
//
//示例 3：
//输入： [2,1,4,5,3,1,1,3]
//输出： 12
//解释： 选择 1 号预约、 3 号预约、 5 号预约和 8 号预约，总时长 = 2 + 4 + 3 + 3 = 12。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/the-masseuse-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2023-06-25.
 */
public class _17_16_按摩师{
    /**
     * <pre>
     * ┌──────────────────────────────────────────────┐
     * │dp[i] dp[0...i]                               │
     * │                                              │
     * │dp[i] = Math.max(dp[i-2]+dp[i], dp[i-1],  i>=2│
     * │                                              │
     * │dp[0] = arr[0]                                │
     * │dp[1] = Math.max(arr[0],arr[1])               │
     * └──────────────────────────────────────────────┘
     * </pre>
     */
    class Solution{
        public int massage(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            if (nums.length == 1) {
                return nums[0];
            }
            if (nums.length == 2) {
                return Math.max(nums[0], nums[1]);
            }
            int[] dp = new int[nums.length];
            dp[0] = nums[0];
            dp[1] = Math.max(nums[0], nums[1]);
            for (int i = 2; i < nums.length; i++) {
                dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
            }
            return dp[dp.length - 1];
        }
    }

    class Solution1{
        public int massage(int[] nums) {
            int no = 0;//第一个不接的初始时间
            int yes = 0;//第一个接的初始时间
            for (int num : nums) {//
                int noTmp = Math.max(no, yes);//第i天不接的总时间，由第i-1天的接和i-1天不接总时间的最大值决定
                int yesTmp = no + num;//第i天接的总时间，由第i-1天不接的总时间加上第i的时间决定
                no = noTmp;
                yes = yesTmp;
            }
            return Math.max(no, yes);
        }
    }
}
