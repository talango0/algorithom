package leetcode.dp;
//给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，
// 使得两个子集的元素和相等。
// 示例 1：
//输入：nums = [1,5,11,5]
//输出：true
//解释：数组可以分割成 [1, 5, 5] 和 [11] 。
//
// 示例 2：
//输入：nums = [1,2,3,5]
//输出：false
//解释：数组不能分割成两个元素和相等的子集。
//
// 提示：
// 1 <= nums.length <= 200
// 1 <= nums[i] <= 100
//
// Related Topics 数组 动态规划 👍 1430 👎 0
/**
 * @author mayanwei
 * @date 2022-08-03.
 */
public class _416_分割等和子集{
    class Solution {
        // 给一个可以装载重量为 sum/2 的背包和 N 个物品，每个物品的重量为 num[i]。
        // 现在让你装物品，是否存在一种装法，能够恰好将背包装满
        // dp[i][j] = x 表示，对于前 i 个物品（i 从 1 开始计数），当前背包的容量为 j 时，若 x 为 true，
        // 则说明可以恰好将背包装满，若 x 为 false，则说明不能恰好将背包装满。
        public boolean canPartition(int[] nums) {
            int sum = 0;
            int n = nums.length;
            for (int i = 0; i<n ; i++) {
                sum += nums[i];
            }
            if (sum%2 != 0) {
                // 和为奇数时，不可能划分成两个和相等的集合
                return false;
            }
            sum = sum>>1;
            boolean [][] dp = new boolean[n+1][sum+1];

            //base case
            for (int i = 0; i<= n; i++) {
                dp[i][0] = true;
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j<=sum; j++) {
                    if( j - nums[i-1] <0) {
                        // 容量不足，只能不装
                        dp[i][j] = dp[i-1][j];
                    }
                    else {
                        // 装入或者不装入背包
                        dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i-1]];
                    }
                }
            }
            return dp[n][sum];
        }
    }

    //时间复杂度 O(n*sum)，空间复杂度 O(sum)
    class Solution2 {
        // 给一个可以装载重量为 sum/2 的背包和 N 个物品，每个物品的重量为 num[i]。
        // 现在让你装物品，是否存在一种装法，能够恰好将背包装满
        // dp[i][j] = x 表示，对于前 i 个物品（i 从 1 开始计数），当前背包的容量为 j 时，
        // 若 x 为 true，则说明可以恰好将背包装满，
        // 若 x 为 false，则说明不能恰好将背包装满。
        //注意到 dp[i][j] 都是通过上一行 dp[i-1][..] 转移过来的，之前的数据都不会再使用了。
        public boolean canPartition(int[] nums) {
            int sum = 0;
            int n = nums.length;
            for (int i = 0; i<n ; i++) {
                sum += nums[i];
            }
            if (sum%2 != 0) {
                // 和为奇数时，不可能划分成两个和相等的集合
                return false;
            }
            sum = sum/2;
            boolean dp[] = new boolean[sum+1];

            //base case
            dp[0] = true;

            for (int i = 0; i < n; i++) {
                for (int j = sum; j>=0; j--) {
                    if( j - nums[i] >= 0) {
                        dp[j] = dp[j] || dp[j-nums[i]];
                    }
                }
            }
            return dp[sum];
        }
    }
}
