package leetcode.arrays;
//n 对情侣坐在连续排列的 2n 个座位上，想要牵到对方的手。
//
// 人和座位由一个整数数组 row 表示，其中 row[i] 是坐在第 i 个座位上的人的 ID。情侣们按顺序编号，第一对是 (0, 1)，第二对是 (2,
//3)，以此类推，最后一对是 (2n-2, 2n-1)。
//
// 返回 最少交换座位的次数，以便每对情侣可以并肩坐在一起。 每次交换可选择任意两人，让他们站起来交换座位。
//
//
//
// 示例 1:
//
//
//输入: row = [0,2,1,3]
//输出: 1
//解释: 只需要交换row[1]和row[2]的位置即可。
//
//
// 示例 2:
//
//
//输入: row = [3,2,0,1]
//输出: 0
//解释: 无需交换座位，所有的情侣都已经可以手牵手了。
//
//
//
//
// 提示:
//
//
// 2n == row.length
// 2 <= n <= 30
// n 是偶数
// 0 <= row[i] < 2n
// row 中所有元素均无重复
//
//
// Related Topics 贪心 深度优先搜索 广度优先搜索 并查集 图 👍 368 👎 0


/**
 * @author mayanwei
 * @date 2022-09-09.
 */
public class _765_情侣牵手{

    /**
     *    ┌──────┐
     * ┌──┼──────┼──────┐
     * │ ┌│────┐┌▼───┐  │
     * │ │0, 2,││1, 3│  │
     * │ └───▲─┘└───┬┘  │
     * └─────┼──────┼───┘
     *       └──────┘
     * 坐错的两对情侣其实可以组成一个环，即一个联通分量
     *
     *     exchange 1
     *       times
     *       ┌───┐
     * ┌─────┼───┼─────┐    ┌──────────────┐
     * │ ┌┈──┴─┐┌▼───┐ │    │┌┈────┐┌─────┐│
     * │ │0, 2,││1, 3│ │───▶││0, 1,││2, 3 ││
     * │ └─────┘└────┘ │    │└─────┘└─────┘│
     * └───────────────┘    └──────────────┘
     *
     * 最少交换的次数 = 情侣对数-1
     *
     * 至少交换的次数 = 所有情侣对数 - 并查集离连通分量的个数
     */
    public class Solution{

        public int minSwapsCouples(int[] row) {
            int len = row.length;
            int N = len / 2;
            UnionFind unionFind = new UnionFind(N);
            for (int i = 0; i < len; i += 2) {
                unionFind.union(row[i] / 2, row[i + 1] / 2);
            }
            return N - unionFind.getCount();
        }

        private class UnionFind{

            private int[] parent;

            private int count;

            public int getCount() {
                return count;
            }

            public UnionFind(int n) {
                this.count = n;
                this.parent = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                }
            }

            public int find(int x) {
                while (x != parent[x]) {
                    parent[x] = parent[parent[x]];
                    x = parent[x];
                }
                return x;
            }

            public void union(int x, int y) {
                int rootX = find(x);
                int rootY = find(y);
                if (rootX == rootY) {
                    return;
                }

                parent[rootX] = rootY;
                count--;
            }
        }
    }
}
