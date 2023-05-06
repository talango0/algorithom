package leetcode.dp;
//一只青蛙想要过河。 假定河流被等分为若干个单元格，并且在每一个单元格内都有可能放有一块石子（也有可能没有）。
// 青蛙可以跳上石子，但是不可以跳入水中。
//
// 给你石子的位置列表 stones（用单元格序号 升序 表示）， 请判定青蛙能否成功过河（即能否在最后一步跳至
// 最后一块石子上）。开始时， 青蛙默认已站在第一块石子上，并可以假定它第一步只能跳跃 1 个单位
// （即只能从单元格 1 跳至单元格 2 ）。
//
// 如果青蛙上一步跳跃了 k 个单位，那么它接下来的跳跃距离只能选择为 k - 1、k 或 k + 1 个单位。
// 另请注意，青蛙只能向前方（终点的方向）跳跃。
//
//
//
//
// 示例 1：
//输入：stones = [0,1,3,5,6,8,12,17]
//输出：true
//解释：青蛙可以成功过河，按照如下方案跳跃：
// 跳 1 个单位到第 2 块石子,
// 然后跳 2 个单位到第 3 块石子,
// 接着 跳 2 个单位到第 4 块石子,
// 然后跳 3 个单位到第 6 块石子, 跳 4 个单位到第 7 块石子,
// 最后，跳 5 个单位到第 8 个石子（即最后一块石子）。
//
// 示例 2：
//输入：stones = [0,1,2,3,4,8,9,11]
//输出：false
//解释：这是因为第 5 和第 6 个石子之间的间距太大，没有可选的方案供青蛙跳跃过去。
//
//
// 提示：
//
//
// 2 <= stones.length <= 2000
// 0 <= stones[i] <= 2³¹ - 1
// stones[0] == 0
// stones 按严格升序排列
//
//
// Related Topics 数组 动态规划 👍 446 👎 0


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2022-09-08.
 */
public class _403_青蛙过河{
    /**
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(n^2)
     */
    class Solution {
        public boolean canCross(int[] stones) {
            int n = stones.length;
            boolean[][] dp = new boolean[n][n];
            dp[0][0] = true;
            for (int i = 1; i < n; ++i) {
                if (stones[i] - stones[i - 1] > i) {
                    return false;
                }
            }
            for (int i = 1; i < n; ++i) {
                for (int j = i - 1; j >= 0; --j) {
                    int k = stones[i] - stones[j];
                    if (k > j + 1) {
                        break;
                    }
                    //令 dp[i][k] 表示青蛙能否达到「现在所处的石子编号」为 i
                    // 且「上一次跳跃距离」为  k 的状态
                    dp[i][k] = dp[j][k - 1] || dp[j][k] || dp[j][k + 1];
                    if (i == n - 1 && dp[i][k]) {
                        return true;
                    }
                }
            }
            return false;
        }
    }


    /**
     * 时间复杂度 O(n^2logn)
     * 空间复杂度 O(n^2)
     */
    class Solution1 {
        private Boolean[][] rec;

        public boolean canCross(int[] stones) {
            int n = stones.length;
            rec = new Boolean[n][n];
            // 初始状态： 现在所处的石子编号为 0（石子的编号从0开始），上次跳跃的距离为 0（这样可以保证第一次跳跃的距离为1）
            return dfs(stones, 0, 0);
        }

        // 青蛙位于第i个石子，上一次的跳跃距离为 lastDis
        private boolean dfs(int[] stones, int i, int lastDis) {
            if (i == stones.length - 1) {
                return true;
            }
            if (rec[i][lastDis] != null) {
                return rec[i][lastDis];
            }
            // 它当前能够跳跃到的距离范围是 [lastDis-1, lastDis+1]
            for (int curDis = lastDis - 1; curDis <= lastDis + 1; curDis++) {
                if (curDis > 0) {
                    // 利用二分查找判断查找对应的三个位置是否存在石子
                    int j = Arrays.binarySearch(stones, i + 1, stones.length, curDis + stones[i]);
                    // 如果存在石子，则尝试进行递归搜索
                    if (j >= 0 && dfs(stones, j, curDis)) {
                        return rec[i][lastDis] = true;
                    }
                }
            }
            return rec[i][lastDis] = false;
        }
    }

    class Solution3 {
        Map<Integer, Integer> map = new HashMap<>();
        // int[][] cache = new int[2009][2009];
        Map<String, Boolean> cache = new HashMap<>();
        public boolean canCross(int[] ss) {
            int n = ss.length;
            for (int i = 0; i < n; i++) {
                map.put(ss[i], i);
            }
            // check first step
            if (!map.containsKey(1)) return false;
            return dfs(ss, ss.length, 1, 1);
        }
        boolean dfs(int[] ss, int n, int u, int k) {
            String key = u + "_" + k;
            // if (cache[u][k] != 0) return cache[u][k] == 1;
            if (cache.containsKey(key)) return cache.get(key);
            if (u == n - 1) return true;
            for (int i = -1; i <= 1; i++) {
                if (k + i == 0) continue;
                int next = ss[u] + k + i;
                if (map.containsKey(next)) {
                    boolean cur = dfs(ss, n, map.get(next), k + i);
                    // cache[u][k] = cur ? 1 : -1;
                    cache.put(key, cur);
                    if (cur) return true;
                }
            }
            // cache[u][k] = -1;
            cache.put(key, false);
            return false;
        }
    }

}
