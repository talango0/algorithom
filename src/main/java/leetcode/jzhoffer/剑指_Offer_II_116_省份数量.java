package leetcode.jzhoffer;

import leetcode.graph._547_省份数量;
//有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
//
//省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
//
//给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
//
//返回矩阵中 省份 的数量。
//
//
//
//示例 1：
//
//
//输入：isConnected = [[1,1,0],[1,1,0],[0,0,1]]
//输出：2
//示例 2：
//
//
//输入：isConnected = [[1,0,0],[0,1,0],[0,0,1]]
//输出：3
//
//
//提示：
//
//1 <= n <= 200
//n == isConnected.length
//n == isConnected[i].length
//isConnected[i][j] 为 1 或 0
//isConnected[i][i] == 1
//isConnected[i][j] == isConnected[j][i]
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/bLyHh0
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
/**
 * @see _547_省份数量
 */
public class 剑指_Offer_II_116_省份数量 {
    class Solution {
        public int findCircleNum(int[][] isConnected) {
            int n = isConnected.length;
            // 初始化并查集
            UF uf = new UF(n);
            // 遍历每个顶点，将当前顶点与其邻接点进行合并
            for (int i = 0; i <n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (isConnected[i][j] == 1) {
                        uf.union(i, j);
                    }
                }
            }
            // 返回最终合并后的集合的数量
            return uf.size;
        }
        // 并查集
        class UF{
            int [] roots;
            int size;
            public UF(int n){
                roots = new int[n];
                for (int i = 0; i < n; i++) {
                    roots[i] = i;
                }
                size = n;
            }
            public int find(int i) {
                return roots[i] = roots[i] == i ? i : find(roots[i]);
            }
            public void union(int i, int j) {
                int iRoot = find(i);
                int jRoot = find(j);
                if (iRoot!= jRoot) {
                    roots[iRoot] = jRoot;
                    size --;
                }
            }
        }
    }
}
