package leetcode.graph;
//小镇里有 n 个人，按从 1 到 n 的顺序编号。传言称，这些人中有一个暗地里是小镇法官。
//
// 如果小镇法官真的存在，那么：
//
//
// 小镇法官不会信任任何人。
// 每个人（除了小镇法官）都信任这位小镇法官。
// 只有一个人同时满足属性 1 和属性 2 。
//
//
// 给你一个数组 trust ，其中 trust[i] = [ai, bi] 表示编号为 ai 的人信任编号为 bi 的人。
//
// 如果小镇法官存在并且可以确定他的身份，请返回该法官的编号；否则，返回 -1 。
//
//
//
// 示例 1：
//
//
//输入：n = 2, trust = [[1,2]]
//输出：2
//
//
// 示例 2：
//
//
//输入：n = 3, trust = [[1,3],[2,3]]
//输出：3
//
//
// 示例 3：
//
//
//输入：n = 3, trust = [[1,3],[2,3],[3,1]]
//输出：-1
//
//
//
// 提示：
//
//
// 1 <= n <= 1000
// 0 <= trust.length <= 10⁴
// trust[i].length == 2
// trust 中的所有trust[i] = [ai, bi] 互不相同
// ai != bi
// 1 <= ai, bi <= n
//
// Related Topics 图 数组 哈希表 👍 257 👎 0


public class _997_找到小镇的法官 {
    class Solution {
        boolean [][] trusted;
        public int findJudge(int n, int[][] trust) {
            trusted = new boolean[n][n];
            for (int i = 0; i<trust.length; i++) {
                int [] edge = trust[i];
                int v = edge[0];
                int w = edge[1];
                trusted[v-1][w-1] = true;

            }
            int index = findCelebrity(n);
            return index == -1?index:index+1;

        }
        int findCelebrity(int n) {
            // 先假设 cand 是名人
            int cand = 0;
            for (int other = 1; other < n; other++) {
                if (!knows(other, cand) || knows(cand, other)) {
                    // cand 不可能是名人，排除
                    // 假设 other 是名人
                    cand = other;
                } else {
                    // other 不可能是名人，排除
                    // 什么都不用做，继续假设 cand 是名人
                }
            }

            // 现在的 cand 是排除的最后结果，但不能保证一定是名人
            for (int other = 0; other < n; other++) {
                if (cand == other) continue;
                // 需要保证其他人都认识 cand，且 cand 不认识任何其他人
                if (!knows(other, cand) || knows(cand, other)) {
                    return -1;
                }
            }

            return cand;
        }

        boolean knows(int p, int q) {
            return trusted[p][q];
        }
    }
}
