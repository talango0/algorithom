package leetcode.dfs;
//在一个2d的矩阵上，1表示是陆地，0表示是水， 要求返回总共有多少个不同形状的岛屿

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author mayanwei
 * @date 2022-08-05.
 */
public class _694_不同的岛屿数量{
    // 想办法把二维矩阵中岛屿进行转化，变成入字符串这样的类型，然后利用 HashSet 这样的数据结构去重，最终得到不同的岛屿的个数。
    // 对于形状相同的岛屿，如果从同一起点出发，dfs函数遍历的顺序肯定是一样的。因为遍历的顺序写死在代码中了。
    // 所以，遍历顺序从某种意义上说就可以用于描述岛屿的形状
    //假设它们的遍历顺序是：
    //
    //下，右，上，撤销上，撤销右，撤销下
    //
    //如果我用分别用 1, 2, 3, 4 代表上下左右，用 -1, -2, -3, -4 代表上下左右的撤销，那么可以这样表示它们的遍历顺序：
    //
    //        2, 4, 1, -1, -4, -2
    //
    //你看，这就相当于是岛屿序列化的结果，只要每次使用 dfs 遍历岛屿的时候生成这串数字进行比较，就可以计算到底有多少个不同的岛屿了。
    //
    //PS：细心的读者问到，为什么记录「撤销」操作才能唯一表示遍历顺序呢？不记录撤销操作好像也可以？实际上不是的。
    //
    //比方说「下，右，撤销右，撤销下」和「下，撤销下，右，撤销右」显然是两个不同的遍历顺序，但如果不记录撤销操作，那么它俩都是「下，右」，成了相同的遍历顺序，显然是不对的。

    class  Solution {

        public int numDistinctIslands(int [][] grid) {
            int m = grid.length, n = grid[0].length;
            // 记录所有岛屿的序列化结果
            HashSet<String> isLands = new HashSet<>();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        // 淹掉这个岛屿，同时存储岛屿的序列化结果
                        StringBuilder sb = new StringBuilder();
                        // 初始方法可以随便写，不影响正确性
                        dfs(grid, i, j, sb, 666);
                        isLands.add(sb.toString());
                    }
                }
            }
            // 不同岛屿的个数
            return isLands.size();
        }

        void dfs(int [][] grid, int i, int j, StringBuilder sb, int dir) {
            int m = grid.length, n = grid[0].length;
            if (i < 0 || j < 0 || i >= m || j >= n || grid[i][j] == 0) {
                return;
            }
            // 前序遍历位置： 进入 (i,j)
            grid[i][j] = 0;
            sb.append(dir).append(',');
            dfs(grid, i - 1, j, sb, 1); // 上
            dfs(grid, i + 1, j, sb, 2); // 下
            dfs(grid, i, j - 1, sb, 3); // 左
            dfs(grid, i, j + 1, sb, 4); // 右
            // 后序遍历位置，离开(i,j)
            sb.append(-dir).append(',');
        }
    }
}
