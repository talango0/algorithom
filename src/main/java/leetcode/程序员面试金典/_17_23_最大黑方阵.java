package leetcode.程序员面试金典;
//给定一个方阵，其中每个单元(像素)非黑即白。设计一个算法，找出 4 条边皆为黑色像素的最大子方阵。
//
//返回一个数组 [r, c, size] ，其中r,c分别代表子方阵左上角的行号和列号，size 是子方阵的边长。若有多个满足条件的子方阵，返回 r 最小的，
// 若 r 相同，返回 c 最小的子方阵。若无满足条件的子方阵，返回空数组。
//
//示例 1:
//
//输入:
//[
//  [1,0,1],
//  [0,0,1],
//  [0,0,1]
//]
//输出: [1,0,2]
//解释: 输入中 0 代表黑色，1 代表白色，标粗的元素即为满足条件的最大子方阵
//示例 2:
//
//输入:
//[
//  [0,1,1],
//  [1,0,1],
//  [1,1,0]
//]
//输出: [0,0,1]
//提示：
//
//matrix.length == matrix[0].length <= 200
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/max-black-square-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2023-06-25.
 */
public class _17_23_最大黑方阵{
    /**
     * 蛮力法 O(N^4)
     * 最大子方阵长度可能为 N,而且 N*N 的方阵只有一个，很容易就能检查这个方阵，符合要求则返回。
     * 如果找不到 N*N 的方阵，可以尝试第二大的子方阵：(N-1)*(N-1),我们迭代所有可能尺寸的方阵，一旦
     * 找到符合要求的子方阵，理解返回。如果还未找到，则继续尝试 N-2、N-3.. 由于我们由大到小的搜索，
     * 因此第一个找到的必定是最大方阵。
     */
    class Solution{
        public int[] findSquare(int[][] matrix) {
            for (int i = matrix.length; i >= 1; i--) {
                int[] square = findSquareWithSize(matrix, i);
                if (square != null) {
                    return square;
                }
            }
            return null;
        }

        private int[] findSquareWithSize(int[][] matrix, int sz) {
            // 在长度为 N 的边中，有（N-sz+1）个长度为sz 的方阵。
            int count = matrix.length - sz + 1;
            // 对所有边长为 sz 的方阵进行迭代
            for (int row = 0; row < count; row++) {
                for (int col = 0; col < count; col++) {
                    if (isSquare(matrix, row, col, sz)) {
                        return new int[]{row, col, sz};
                    }
                }
            }
            return null;
        }

        private boolean isSquare(int[][] matrix, int row, int col, int sz) {
            // 检查上下边界
            for (int j = 0; j < sz; j++) {
                if (matrix[row][col + j] == 1) {
                    return false;
                }
                if (matrix[row + sz - 1][col + j] == 1) {
                    return false;
                }
            }
            // 检查左右边界
            for (int i = 1; i < sz - 1; i++) {
                if (matrix[row + i][col] == 1) {
                    return false;
                }
                if (matrix[row + i][col + sz - 1] == 1) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * <pre>
     * 上面算法执行缓慢的原因在于：每次检查一个可能的符合要求的方阵，都要执行 O(N) 的工作。通过预先做些处理，就可以
     * 把 isSquare 的时间复杂度将为 O(1)，而整个算法的时间复杂度降至O(N^3)
     * 仔细分析 isSquare 方法，就会发现它只需要直到特定单元下方即右边的 squareSize 项是否为零。我们预先以直接、迭代的方式算好这些数据
     * ┌────────────────────────────────────────────────────────┐
     * │  if A[r][c] is white, zeros right and zero below are 0 │
     * │  else A[r][c].zerosRight = A[r][c+1].zeroRight + 1     │
     * │       A[r][c].zerosBelow = A[r+1][c].zeroBelow + 1     │
     * │                                                        │
     * │                                                        │
     * │   W   B   W          0,0  1,3  0,0                     │
     * │                                                        │
     * │   B   B   W          2,2  1,2  0,0                     │
     * │                                                        │
     * │   B   B   W          2,1  1,1  0,0                     │
     * └────────────────────────────────────────────────────────┘
     * </pre>
     */
    class Solution1{
        public class SquareCell{
            public int zeroRight = 0;
            public int zeroBelow = 0;

            public SquareCell(int rightZeros, int belowZeros) {
                this.zeroRight = rightZeros;
                this.zeroBelow = belowZeros;
            }

            public int getZeroRight() {
                return zeroRight;
            }

            public void setZeroRight(int zeroRight) {
                this.zeroRight = zeroRight;
            }

            public int getZeroBelow() {
                return zeroBelow;
            }

            public void setZeroBelow(int zeroBelow) {
                this.zeroBelow = zeroBelow;
            }
        }

        public int[] findSquare(int[][] matrix) {
            SquareCell[][] processed = processSquare(matrix);
            for (int i = matrix.length; i >= 1; i--) {
                int[] square = findSquareWithSize(processed, i);
                if (square != null) {
                    return square;
                }
            }
            return new int[0];
        }

        private SquareCell[][] processSquare(int[][] matrix) {
            SquareCell[][] processed = new SquareCell[matrix.length][matrix[0].length];
            for (int r = matrix.length - 1; r >= 0; r--) {
                for (int c = matrix.length - 1; c >= 0; c--) {
                    int rightZeros = 0;
                    int belowZeros = 0;
                    // 只有是黑色单元格式才需要处理
                    if (matrix[r][c] == 0) {
                        rightZeros++;
                        belowZeros++;
                        // 下一列在同一行上
                        if (c + 1 < matrix.length) {
                            SquareCell previous = processed[r][c + 1];
                            rightZeros += previous.zeroRight;
                        }
                        if (r + 1 < matrix.length) {
                            SquareCell previous = processed[r + 1][c];
                            belowZeros += previous.zeroBelow;
                        }
                    }

                    processed[r][c] = new SquareCell(rightZeros, belowZeros);
                }
            }
            return processed;
        }

        private int[] findSquareWithSize(SquareCell[][] matrix, int sz) {
            // 在长度为 N 的边中，有（N-sz+1）个长度为sz 的方阵。
            int count = matrix.length - sz + 1;
            // 对所有边长为 sz 的方阵进行迭代
            for (int row = 0; row < count; row++) {
                for (int col = 0; col < count; col++) {
                    if (isSquare(matrix, row, col, sz)) {
                        return new int[]{row, col, sz};
                    }
                }
            }
            return null;
        }

        private boolean isSquare(SquareCell[][] matrix, int row, int col, int sz) {
            SquareCell topLeft = matrix[row][col];
            SquareCell topRight = matrix[row][col + sz - 1];
            SquareCell bottomLeft = matrix[row + sz - 1][col];
            if (topLeft.zeroRight < sz || topLeft.zeroBelow < sz || topRight.zeroBelow < sz || bottomLeft.zeroRight < sz) {
                return false;
            }
            return true;
        }
    }


    class Solution3{
        public int[] findSquare(int[][] matrix) {
            if (matrix == null || matrix.length == 0) {
                return new int[]{};
            }
            final int n = matrix.length;
            int[][] preX = new int[matrix.length][matrix.length + 1];
            int[][] preY = new int[matrix.length + 1][matrix.length];
            for (int i = n - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    if (matrix[i][j] == 0) {
                        preX[i][j] = preX[i][j + 1] + 1;
                        preY[i][j] = preY[i + 1][j] + 1;
                    }
                    else {
                        preX[i][j] = 0;
                        preY[i][j] = 0;
                    }
                }
            }
            final int[] res = new int[3];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    final int min = Math.min(preX[i][j], preY[i][j]);
                    if (res[2] < min) {
                        for (int size = min; size > res[2]; size--) {
                            final int endX = i + size - 1;
                            final int endY = j + size - 1;
                            if (preX[endX][j] >= size && preY[i][endY] >= size) {
                                res[0] = i;
                                res[1] = j;
                                res[2] = size;
                                break;
                            }
                        }
                    }
                }
            }
            return res[2] == 0 ? new int[]{} :res;
        }
    }
    class Solution4 {
        public int[] findSquare(int[][] matrix) {
            int n = matrix.length;
            int[][] left = new int[n + 1][n + 1];
            int[][] up = new int[n + 1][n + 1];
            int r = 0, c = 0, size = 0;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (matrix[i - 1][j - 1] == 0) {
                        left[i][j] = left[i][j - 1] + 1;
                        up[i][j] = up[i - 1][j] + 1;
                        int border = Math.min(left[i][j], up[i][j]);
                        while (left[i - border + 1][j] < border || up[i][j - border + 1] < border) {
                            border--;
                        }
                        if (border > size) {
                            r = i - border;
                            c = j - border;
                            size = border;
                        }
                    }
                }
            }
            return size > 0 ? new int[]{r, c, size} : new int[0];
        }
    }


}
