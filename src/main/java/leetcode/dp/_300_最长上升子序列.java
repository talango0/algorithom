package leetcode.dp;

import leetcode.dfs._329_矩阵中的最长递增路径;
import leetcode.greedy._334_递增的三元子序列;

import java.util.Arrays;
//给定一个无序的整数数组，找到其中最长上升子序列的长度。
//
// 示例:
//
// 输入: [10,9,2,5,3,7,101,18]
//输出: 4
//解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
//
// 说明:
//
//
// 可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
// 你算法的时间复杂度应该为 O(n2) 。
//
//
// 进阶: 你能将算法的时间复杂度降低到 O(n log n) 吗?
// Related Topics 二分查找 动态规划
// 👍 1120 👎 0

/**
 * @see _334_递增的三元子序列
 * @see _329_矩阵中的最长递增路径
 * @see _354_俄罗斯套娃信封问题
 *
 * @see _53_最大子数组和
 */
public class _300_最长上升子序列{
    class Solution{
        // 注意，子序列和子串不同，子序列可以不连续
        // 时间复杂度 O(n^2)
        // 1、明确 dp 数组的定义。这一步对于任何动态规划问题都很重要，如果不得当或者不够清晰，会阻碍之后的步骤。
        // 2、根据 dp 数组的定义，运用数学归纳法的思想，假设 dp[0...i-1] 都已知，想办法求出 dp[i]，一旦这一步完成，整个题目基本就解决了。
        // 但如果无法完成这一步，很可能就是 dp 数组的定义不够恰当，需要重新定义 dp 数组的含义；或者可能是 dp 数组存储的信息还不够，
        // 不足以推出下一步的答案，需要把 dp 数组扩大成二维数组甚至三维数组。
        public int lengthOfLIS(int[] nums) {
            // 定义：dp[i] 表示以 nums[i] 这个数结尾的最长递增子序列的长度
            int n = nums.length;
            int[] dp = new int[n];
            //base case
            Arrays.fill(dp, 1); // 因为以nums[i] 结尾的最长递增子序列起码要包含自己
            for (int i = 0; i < nums.length; i++) {
                for (int j = 0; j < i; j++) {
                    // 寻找 num[0..j-1]中比 num[i] 小的元素
                    // 且以 num[i] 为结尾的递增子序列
                    if (nums[i] > nums[j]) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
            }
            // 根据这个定义我们的最终结果（子序列的最大长度）应该是 dp数组中的最大值
            int res = 0;
            for (int i = 0; i < n; i++) {
                res = Math.max(res, dp[i]);
            }
            return res;
        }
    }


    /**
     * O(nlogn)
     */
    class Solution2{
        // patience sequence
        public int lengthOfLIS(int[] nums) {
            int[] top = new int[nums.length];
            // 牌堆初始化为 0
            int piles = 0;
            for (int i = 0; i < nums.length; i++) {
                // 要处理的扑克牌
                int poker = nums[i];
                // 搜索左侧边界的二分查找
                int left = 0, right = piles;
                while (left < right) {
                    int mid = (left + right) / 2;
                    if (top[mid] > poker) {
                        right = mid;
                    }
                    else if (top[mid] < poker) {
                        left = mid + 1;
                    }
                    else {
                        right = mid;
                    }
                }
                // 没有找到合适的，新建一堆
                if (left == piles) {
                    piles++;
                }
                // 把这张牌放在堆顶
                top[left] = poker;
            }
            // 牌堆数就是 LTS 长度
            return piles;
        }
    }


    /**
     * 给定一个未排序的整数数组 nums ， 以数组形式返回最长递增子序列，如果有多个最长递增子序列，返回一个即可 。
     * 注意 这个数列必须是 严格 递增的
     *
     * 输入: [1,3,5,4,7]
     * 解释: 有两个最长递增子序列，分别是 [1, 3, 4, 7] 和[1, 3, 5, 7]。
     */
    class Solution_2{
        public int[] generateLIS(int[] arr, int[] dp) {
            int len = 0; // 最长递增子序列的长度
            int index = 0; // 最长递增子序列的下标
            for (int i = 0; i < dp.length; i++) {
                if (dp[i] > len) {
                    len = dp[i];
                    index = i;
                }
            }
            int[] lis = new int[len]; // 准备长度为len的递增子序列
            lis[--len] = arr[index];
            for (int i = index; i >= 0; i--) { // 从后往前推
                if (arr[i] < arr[index] && dp[i] == dp[index] - 1) {
                    lis[--len] = arr[i];
                    index = i;
                }
            }
            return lis;
        }

        public int[] lis2(int[] arr) {
            if (arr == null || arr.length == 0) {
                return null;
            }
            int[] dp = getdp2(arr); // 生成最长递增子序列数组dp，每个位置代表当前位置的最长递增子序列长度
            return generateLIS(arr, dp); // 生成最长递增子序列
        }

        public int[] getdp2(int[] arr) {
            int[] dp = new int[arr.length];
            int[] ends = new int[arr.length];
            ends[0] = arr[0];
            dp[0] = 1;
            int right = 0; // 有效区  0....right  right往右无效
            int L = 0;
            int R = 0;
            int M = 0;
            for (int i = 1; i < arr.length; i++) {
                L = 0;
                R = right;
                while (L <= R) { // 二分查找的过程
                    M = (L + R) / 2;
                    if (arr[i] > ends[M]) {
                        L = M + 1;
                    } else {
                        R = M - 1;
                    }
                }
                // L -> right+1
                right = Math.max(right, L);
                ends[L] = arr[i];
                dp[i] = L + 1;
            }
            return dp;
        }
    }
}
