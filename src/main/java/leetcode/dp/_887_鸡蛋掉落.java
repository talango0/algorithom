package leetcode.dp;
//给你 k 枚相同的鸡蛋，并可以使用一栋从第 1 层到第 n 层共有 n 层楼的建筑。
// 已知存在楼层 f ，满足 0 <= f <= n ，任何从 高于 f 的楼层落下的鸡蛋都会碎，
// 从 f 楼层或比它低的楼层落下的鸡蛋都不会破。
// 每次操作，你可以取一枚没有碎的鸡蛋并把它从任一楼层 x 扔下（满足 1 <= x <= n）。
// 如果鸡蛋碎了，你就不能再次使用它。如果某枚鸡蛋扔下后没有摔碎，则可以在之后的操作中
// 重复使用 这枚鸡蛋。
//
// 请你计算并返回要确定 f 确切的值的 最小操作次数 是多少？
//
// 示例 1：
//输入：k = 1, n = 2
//输出：2
//解释：
//鸡蛋从 1 楼掉落。如果它碎了，肯定能得出 f = 0 。
//否则，鸡蛋从 2 楼掉落。如果它碎了，肯定能得出 f = 1 。
//如果它没碎，那么肯定能得出 f = 2 。
//因此，在最坏的情况下我们需要移动 2 次以确定 f 是多少。
//
//
// 示例 2：
//输入：k = 2, n = 6
//输出：3
//
//
// 示例 3：
//输入：k = 3, n = 14
//输出：4
//
// 提示：
//
//
// 1 <= k <= 100
// 1 <= n <= 10⁴
//
//
// Related Topics 数学 二分查找 动态规划 👍 852 👎 0

import java.util.Arrays;

/**
 * 字节
 * @author mayanwei
 * @date 2022-07-30.
 */
public class _887_鸡蛋掉落{
    class Solution1{

        // 思路，这个问题有什么状态，有什么选择，然后穷举
        // 状态 ：就是当前拥有的鸡蛋数 K 和需要测试的楼层数 N。 随着测试的进行鸡蛋数会减少，楼层的搜索范围会减少，这就是状态的变化
        // 选择 ：其实就是去选择哪层楼扔鸡蛋
        // 有了这个思路，动态规划的基本思路就形成了
        // 肯定是二维 dp 数组或者带有两个状态参数的 dp 函数来表示状态转移；外加一个for循环遍历所有的选择，择最优的选择更新状态。
        // 当前状态为 k 个鸡蛋,n 个楼层，返回这个状态下的最优结果
        // int dp(int k, int n) {
        //     int res;
        //     for (int i = 1; i<=n; i++) {
        //         res = Math.min(res, 这次在第i层扔鸡蛋);
        //     }
        //     res;
        // }

        int[][] dp;

        public int superEggDrop(int k, int n) {
            dp = new int[k + 1][n + 1];
            for (int i = 0; i <= k; i++) {
                Arrays.fill(dp[i], Integer.MAX_VALUE);

            }
            return dp(k, n);
        }

        /**
         * 最坏的情况下需要是多少次就一定能够找出来哪个楼层
         * @param k 鸡蛋个数
         * @param n 楼层高度
         * @return
         */
        private int dp(int k, int n) {
            if (dp[k][n] != Integer.MAX_VALUE) {
                return dp[k][n];
            }
            // base case
            if (k == 1) {
                // 最坏情况，逐层试才能试出来
                return n;
            }
            if (n == 0) {
                return 0;
            }
            int res = Integer.MAX_VALUE;
            // for (int i = 1; i<=n; i++) {
            //     res = Math.min(res, Math.max(
            //             dp(k-1, i-1),
            //             dp(k, n-i))
            //              + 1
            //      );
            // }
            // 用二分搜索代替线下搜索
            int lo = 1, hi = n;
            while (lo <= hi) {
                int mid = lo + ((hi - lo) >> 1);
                int broken = dp(k - 1, mid - 1);
                int not_broken = dp(k, n - mid);
                // res = max(max(碎， 没碎)+1)
                if (broken > not_broken) {
                    hi = mid - 1;
                    res = Math.min(res, broken + 1);
                }
                else {
                    lo = mid + 1;
                    res = Math.min(res, not_broken + 1);
                }
            }
            dp[k][n] = res;
            return res;
        }
    }


    class Solution2{

        /**
         * <pre>
         * 思路, 重新定义dp的含义
         * dp[k][m] = n
         * 当前有 k 个鸡蛋，可以尝试扔 m 次鸡蛋
         * 这个状态下，最坏的情况下最多能确切测试一栋 n 层的楼
         * 比如，dp[1][7] = 7 表示，
         * 现在有一个鸡蛋，允许你扔 7  次，这个状态下最多给你 7 层楼，使得你可以确定楼层 F使得鸡蛋恰好摔不碎
         * （一层一层线性探测）
         * </pre>
         * @param k
         * @param n
         * @return
         */

        public int superEggDrop(int k, int n) {
            // m 最多不会超过 n 次（线下探测）
            int[][] dp = new int[k + 1][n + 1];
            // base case, java 默认初始数组值为0
            // dp[0][...] = 0;
            // dp[...][0] = 0;
            int m = 0;
            while (dp[k][m] < n) {
                m++;
                for (int i = 1; i <= k; i++) {
                    dp[i][m] = dp[i][m - 1] + dp[i - 1][m - 1] + 1;
                }
            }
            return m;
        }

    }

    class Solution3 {

        public int superEggDrop(int K, int N) {
            int remainTestCount = 1;//穷举移动次数（测试的次数）
            while (getConfirmFloors(remainTestCount, K) < N){
                ++remainTestCount;
            }
            return remainTestCount;
        }

        //在remainTestCount个测试机会（扔鸡蛋的机会 或者移动的次数），
        // eggsCount个鸡蛋可以确定的楼层数量
        public int getConfirmFloors(int remainTestCount, int eggsCount){
            if (remainTestCount == 1 || eggsCount == 1){
                //如果remainTestCount == 1你只能移动一次，则你只能确定第一楼是否，
                // 也就是说鸡蛋只能放在第一楼，如果碎了，则F == 0，如果鸡蛋没碎，则F == 1
                //如果eggsCount == 1鸡蛋数为1，它碎了你就没有鸡蛋了，为了保险，
                // 你只能从第一楼开始逐渐往上测试，如果第一楼碎了（同上），
                // 第一楼没碎继续测第i楼，蛋式你不可能无限制的测试，因为你只能测试remainTestCount次
                return remainTestCount;
            }
            return getConfirmFloors(remainTestCount - 1, eggsCount - 1)
                    + 1
                    + getConfirmFloors(remainTestCount - 1, eggsCount);
        }
    }
}
