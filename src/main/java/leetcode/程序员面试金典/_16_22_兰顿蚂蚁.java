package leetcode.程序员面试金典;
//一只蚂蚁坐在由白色和黑色方格构成的无限网格上。开始时，网格全白，蚂蚁面向右侧。每行走一步，蚂蚁执行以下操作。
//
//(1) 如果在白色方格上，则翻转方格的颜色，向右(顺时针)转 90 度，并向前移动一个单位。
//(2) 如果在黑色方格上，则翻转方格的颜色，向左(逆时针方向)转 90 度，并向前移动一个单位。
//
//编写程序来模拟蚂蚁执行的前 K 个动作，并返回最终的网格。
//
//网格由数组表示，每个元素是一个字符串，代表网格中的一行，黑色方格由'X'表示，白色方格由'_'表示，蚂蚁所在的位置由'L', 'U', 'R', 'D'表示，
// 分别表示蚂蚁左、上、右、下 的朝向。只需要返回能够包含蚂蚁走过的所有方格的最小矩形。
//
//示例 1:
//
//输入: 0
//输出: ["R"]
//示例 2:
//
//输入: 2
//输出:
//[
// "_X",
// "LX"
//]
//示例 3:
//
//输入: 5
//输出:
//[
// "_U",
// "X_",
// "XX"
//]
//说明：
//
//K <= 100000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/langtons-ant-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.括号匹配;
import org.apache.zookeeper.Op;

import java.util.*;

/**
 * @author mayanwei
 * @date 2023-06-14.
 */
public class _16_22_兰顿蚂蚁{
    /**
     * 题目解法似乎简单，即构造网络，记录蚂蚁的位置和方向，反转单元格的颜色，转向，移动即可。难点在于如何处理网格的无限性
     * 解法1，固定数组
     * 理论上，由于只进行了前K步移动，可以得到网络的最大尺寸。在任意方向上，蚂蚁并不能超过K步的距离。构造一个 2K*2K的网格（将蚂蚁至于网格
     * 中央），即可满足题目要求。
     * 但是扩展性差，移动了 K 步之后在想要移动K步，则该方法就不行了。
     * 另外，该方法会占用大量的内存空间，在一个方向山个，最大的高度有可能达到 K 步的距离，但是蚂蚁有可能只在一个小的环状路线中转圈，或许不用
     * 浪费这么多的空间。
     * <p>
     * 解法2，可变大小数组。
     * Java的ArrayList 提供可变数组。允许按需增加数组的尺寸。而且平均插入时间认为O(1)。问题在于，网格需要两个方向增长，但是 ArrayList 类
     * 只提供了一位数组的功能。另外，我们需要向”反方向“增加元素，而ArrayList无法提供这样的功能。
     * 然而可以使用类似的方法常见尺寸可变得网格。每当蚂蚁到达网络的边界时，我们将将该方向的网络大小增加一倍。
     * 如果向反方向拓展？创建一些伪索引，假设蚂蚁位于坐标（-3， -10）处，可以记录一个位移量以便将坐标转化为数组的索引。
     * 其实，并不一定要这么做，蚂蚁的位置不需要外界所知，也不需要始终保持一致，当蚂蚁进入到负值坐标区域之后，只需要将数组的大小增加一倍，并
     * 将所有的单元格信息和蚂蚁移入正值坐标区域。本质上，我们对所有的索引值都进行了重新设定。
     * 无论如何都要创建一个新的矩阵，因此重新设定索引值并不会影响以O表示的时间复杂度。
     */
    static class Solution{

        public List<String> printKMoves(int K) {
            Grid grid = new Grid();
            for (int i = 0; i < K; i++) {
                grid.move();
            }

            return null;
        }

        class Grid{
            private boolean[][] grid;
            private Ant ant = new Ant();

            public Grid() {
                this.grid = new boolean[1][1];
            }

            // 将旧的值复制到新的数组中，对其行和列进行移位
            void copyWithShift(boolean[][] oldGrid, boolean[][] newGrid, int shiftRow, int shiftColumn) {
                for (int r = 0; r < oldGrid.length; r++) {
                    for (int c = 0; c < oldGrid[0].length; c++) {
                        newGrid[r + shiftRow][c + shiftColumn] = oldGrid[r][c];
                    }
                }
            }

            /**
             * 取保给定的位置满足数组大小。如果需要，则对方阵的大小进行翻倍，复制旧的值并调整蚂蚁位置
             */
            private void ensureFit(Position position) {
                int shiftRow = 0;
                int shiftColumn = 0;
                int numRows = grid.length;

                // 计算行的总数
                if (position.row < 0) {
                    shiftRow = numRows;
                    numRows *= 2;
                }
                else if (position.row >= numRows) {
                    numRows *= 2;
                }

                // 计算列的总数
                int numColumns = grid[0].length;
                if (position.column < 0) {
                    shiftColumn = numRows;
                    numColumns *= 2;
                }
                else if (position.row >= numRows) {
                    numColumns *= 2;
                }

                // 如果需要扩展数组，同时移动蚂蚁的位置
                if (numRows != grid.length || numColumns != grid[0].length) {
                    boolean[][] newGrid = new boolean[numRows][numColumns];
                    copyWithShift(grid, newGrid, shiftRow, shiftColumn);
                    ant.adjustPosition(shiftRow, shiftColumn);
                    grid = newGrid;
                }
            }


            /**
             * 变换单元格的颜色
             */
            private void flip(Position position) {
                int row = position.row;
                int column = position.column;
                grid[row][column] = !grid[row][column];
            }

            /**
             * 移动蚂蚁
             */
            public void move() {
                ant.turn(grid[ant.position.row][ant.position.column]);
                flip(ant.position);
                ant.move();
                ensureFit(ant.position);// 扩展
            }

            /**
             * 打印
             *
             * @return
             */
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (int r = 0; r < grid.length; r++) {
                    for (int c = 0; c < grid[0].length; c++) {
                        if (r == ant.position.row && c == ant.position.column) {
                            sb.append(ant.orientation);
                        }
                        else if (grid[r][c]) {
                            sb.append('X');
                        }
                        else {
                            sb.append("_");
                        }

                    }
                    sb.append("\n");
                }
                sb.append(ant.orientation).append(".\n");
                return sb.toString();
            }
        }

        /**
         * 把与蚂蚁相关的所有代码放置在一个单独的类中，这样做的好处在于，如果因为某些原因需要在题目中使用多只蚂蚁，可以易于扩展。
         */
        class Ant{
            Position position = new Position(0, 0);
            Orientation orientation = Orientation.right;

            void turn(boolean clockwise) {
                orientation = orientation.getTurn(clockwise);
            }

            void move() {
                if (orientation == Orientation.left) {
                    position.column--;
                }
                else if (orientation == Orientation.right) {
                    position.column++;
                }
                else if (orientation == Orientation.down) {
                    position.row++;
                }
                else if (orientation == Orientation.up) {
                    position.row--;
                }
            }

            void adjustPosition(int shiftRow, int shiftColum) {
                position.row += shiftRow;
                position.column += shiftColum;
            }

        }

        class Position{
            int row;
            int column;

            public Position(int row, int column) {
                this.row = row;
                this.column = column;
            }
        }

        enum Orientation{
            left, up, right, down;

            Orientation getTurn(boolean clockwise) {
                if (this == left) {
                    return clockwise ? up :down;
                }
                else if (this == up) {
                    return clockwise ? right :left;
                }
                else if (this == right) {
                    return clockwise ? down :up;
                }
                else if (this == down) {
                    return clockwise ? left :right;
                }
                return null;
            }
        }


    }


    /**
     * 尽管二维数组表示网格是显而易见的，但是不使用该方法实际上更简单。
     * 只需要使用一组白色方格及蚂蚁的位置与方向即可。
     * 可以采用散列集合存储白色方格，入股哦某个位置处于集合中，则表示该处方格为白色。否则，该处方格为黑色。
     * 唯一剩下棘手的问题在于如何打印网格。应该从哪里开始，从哪里结束？
     * 需要打印网格，因此，需要记录网格左上角和右下角的位置。每当移动蚂蚁时，都需要将蚂蚁的位置与左上角和右下角的位置进行对比，按需更新它们的值。
     */
    static class Solution2{
        public class Board{
            private final HashSet<Position> whites = new HashSet<>();
            private final Ant ant = new Ant();
            Position topLeftCorner = new Position(0, 0);
            Position bottomRightCorner = new Position(0, 0);

            public Board() {
            }

            public void move() {
                ant.turn(isWhite(ant.position)); // 转向
                flip(ant.position); // 翻转颜色
                ant.move(); // 移动
                ensureFit(ant.position);

            }

            /**
             * 跟踪左上角和右下角的位置并拓展表格
             */
            private void ensureFit(Position position) {
                int row = position.row;
                int column = position.column;
                topLeftCorner.row = Math.min(topLeftCorner.row, row);
                topLeftCorner.column = Math.min(topLeftCorner.column, column);

                bottomRightCorner.row = Math.max(bottomRightCorner.row, row);
                bottomRightCorner.column = Math.max(bottomRightCorner.column, column);

            }

            /**
             * 变换单元格的颜色
             */
            private void flip(Position position) {
                if (whites.contains(position)) {
                    whites.remove(position);
                }
                else {
                    whites.add(position.clone());
                }
            }

            // 检查单元格树否为白色
            private boolean isWhite(Position position) {
                return whites.contains(position);
            }
        }


        /**
         * 把与蚂蚁相关的所有代码放置在一个单独的类中，这样做的好处在于，如果因为某些原因需要在题目中使用多只蚂蚁，可以易于扩展。
         */
        class Ant{
            Position position = new Position(0, 0);
            Orientation orientation = Orientation.right;

            void turn(boolean clockwise) {
                orientation = orientation.getTurn(clockwise);
            }

            void move() {
                if (orientation == Orientation.left) {
                    position.column--;
                }
                else if (orientation == Orientation.right) {
                    position.column++;
                }
                else if (orientation == Orientation.down) {
                    position.row++;
                }
                else if (orientation == Orientation.up) {
                    position.row--;
                }
            }

            void adjustPosition(int shiftRow, int shiftColum) {
                position.row += shiftRow;
                position.column += shiftColum;
            }

        }


        enum Orientation{
            left, up, right, down;

            Orientation getTurn(boolean clockwise) {
                if (this == left) {
                    return clockwise ? up :down;
                }
                else if (this == up) {
                    return clockwise ? right :left;
                }
                else if (this == right) {
                    return clockwise ? down :up;
                }
                else if (this == down) {
                    return clockwise ? left :right;
                }
                return null;
            }
        }

        public class Position{
            public int row;
            public int column;

            public Position(int row, int column) {
                this.row = row;
                this.column = column;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Position)) return false;
                Position position = (Position) o;
                return row == position.row && column == position.column;
            }

            @Override
            public int hashCode() {
                //散列函数有很多，此为一种
                return (row * 31) ^ column;
            }

            public Position clone() {
                return new Position(row, column);
            }
        }
    }


    class Solution3{
        private class Position{

            // 横坐标 x 纵坐标 y
            int x, y;

            public Position(int x, int y) {
                this.x = x;
                this.y = y;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this) return true;
                if (!(obj instanceof Position)) return false;
                Position o = (Position) obj;
                return x == o.x && y == o.y;
            }

            // 改写哈希算法，使两个 Position 对象可以比较坐标而不是内存地址
            @Override
            public int hashCode() {
                int result = x;
                result = 31 * result + y;
                return result;
            }
        }

        public List<String> printKMoves(int K) {
            char[] direction = {'L', 'U', 'R', 'D'};
            // 用“向量”记录方向，顺序与上一行方向的字符顺序保持一致，每个元素的后一个元素都是可以90°向右变换得到的
            int[][] offset = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
            // 蚂蚁的位置
            Position antPos = new Position(0, 0);
            // 蚂蚁方向的向量序号
            int antDir = 2;
            // 用集合存储所有黑块的坐标，一开始想再定义一个路径的坐标集合，发现可以直接用黑块+蚂蚁位置也能过
            Set<Position> blackSet = new HashSet<>();
            while (K > 0) {
                // 新的坐标对象用于放入集合
                Position t = new Position(antPos.x, antPos.y);
                // 如果黑块集合能存入，说明脚下的块不在集合中，也就意味着是白色，方向序号循环自增1
                if (blackSet.add(t)) {
                    antDir = (antDir + 1) % 4;
                }
                else {
                    // 否则说明脚下的块已经在集合中，也就意味着是黑色，方向序号循环自增3，相当于自减1，但是Math.floorMod取模可能消耗大？用+3替代
                    antDir = (antDir + 3) % 4;
                    // 别忘了删除，即将黑块变白
                    blackSet.remove(t);
                }
                // 蚂蚁移动位置
                antPos.x += offset[antDir][0];
                antPos.y += offset[antDir][1];
                K--;
            }
            // 计算边界，即输出网格的行数和列数
            int left = antPos.x, top = antPos.y, right = antPos.x, bottom = antPos.y;
            for (Position pos : blackSet) {
                left = Math.min(pos.x, left);
                top = Math.min(pos.y, top);
                right = Math.max(pos.x, right);
                bottom = Math.max(pos.y, bottom);
            }
            char[][] grid = new char[bottom - top + 1][right - left + 1];
            // 填充白块
            for (char[] row : grid)
                Arrays.fill(row, '_');
            // 替换黑块
            for (Position pos : blackSet)
                grid[pos.y - top][pos.x - left] = 'X';
            // 替换蚂蚁
            grid[antPos.y - top][antPos.x - left] = direction[antDir];
            // 利用网格生成字符串列表
            List<String> result = new ArrayList<>();
            for (char[] row : grid)
                result.add(String.valueOf(row));
            return result;
        }
    }
}
