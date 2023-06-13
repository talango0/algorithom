package leetcode.程序员面试金典;
//你有一个用于表示一片土地的整数矩阵land，该矩阵中每个点的值代表对应地点的海拔高度。若值为0则表示水域。由垂直、水平或对角连接的水域为池塘。
// 池塘的大小是指相连接的水域的个数。编写一个方法来计算矩阵中所有池塘的大小，返回值需要从小到大排序。
//
//示例：
//
//输入：
//[
//  [0,2,1,0],
//  [0,1,0,1],
//  [1,1,0,1],
//  [0,1,0,1]
//]
//输出： [1,2,4]
//提示：
//
//0 < len(land) <= 1000
//0 < len(land[i]) <= 1000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/pond-sizes-lcci

//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.dfs._200_岛屿数量;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mayanwei
 * @date 2023-06-13.
 * @see _200_岛屿数量
 */
public class _16_19_水域大小{
    class Solution{
        int[][] dirs = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {-1, 1}, {1, 1}, {1, -1}};

        public int[] pondSizes(int[][] land) {
            if (land == null) {
                return new int[]{};
            }

            int m = land.length;
            int n = land[0].length;
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (land[i][j] == 0) {
                        list.add(dfs(land, i, j, m, n));
                    }
                }
            }
            int[] res = new int[list.size()];
            for (int i = 0; i < res.length; i++) {
                res[i] = list.get(i);
            }
            Arrays.sort(res);
            return res;
        }

        private int dfs(int[][] land, int i, int j, int m, int n) {
            if (i < 0 || i >= m || j < 0 || j >= n || land[i][j] != 0) {
                return 0;
            }
            int count = 1;
            land[i][j] = 1;
            for (int[] dir : dirs) {
                count += dfs(land, i + dir[0], j + dir[1], m, n);
            }
            return count;
        }
    }


    class Solution2{
        //0表示水域
        int m, n;
        int area = 0;
        List<Integer> res = new ArrayList<Integer>();

        public int[] pondSizes(int[][] land) {
            int m = land.length;
            if (m == 0) return new int[0];
            int n = land[0].length;
            this.m = m;
            this.n = n;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    area = 0;
                    if (land[i][j] == 0) {
                        dfs(i, j, land);
                        if (area != 0) {
                            res.add(area);
                        }
                    }
                }
            }
            int[] ret = new int[res.size()];
            for (int i = 0; i < res.size(); i++) {
                ret[i] = res.get(i);
            }
            Arrays.sort(ret);
            return ret;
        }

        public void dfs(int i, int j, int[][] land) {
            if (i < 0 || j < 0 || i == m || j == n || land[i][j] != 0) {
                return;
            }
            area++;
            land[i][j] = -1;
            //上下左右
            dfs(i + 1, j, land);
            dfs(i - 1, j, land);
            dfs(i, j - 1, land);
            dfs(i, j + 1, land);
            //四个对角线
            dfs(i + 1, j + 1, land);
            dfs(i - 1, j - 1, land);
            dfs(i - 1, j + 1, land);
            dfs(i + 1, j - 1, land);
        }
    }
}
