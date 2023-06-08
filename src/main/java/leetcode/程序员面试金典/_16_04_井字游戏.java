package leetcode.程序员面试金典;
//设计一个算法，判断玩家是否赢了井字游戏。输入是一个 N x N 的数组棋盘，由字符" "，"X"和"O"组成，其中字符" "代表一个空位。
//
//以下是井字游戏的规则：
//
//玩家轮流将字符放入空位（" "）中。
//第一个玩家总是放字符"O"，且第二个玩家总是放字符"X"。
//"X"和"O"只允许放置在空位中，不允许对已放有字符的位置进行填充。
//当有N个相同（且非空）的字符填充任何行、列或对角线时，游戏结束，对应该字符的玩家获胜。
//当所有位置非空时，也算为游戏结束。
//如果游戏结束，玩家不允许再放置字符。
//如果游戏存在获胜者，就返回该游戏的获胜者使用的字符（"X"或"O"）；如果游戏以平局结束，则返回 "Draw"；如果仍会有行动（游戏未结束），则返回 "Pending"。
//
//示例 1：
//
//输入： board = ["O X"," XO","X O"]
//输出： "X"
//示例 2：
//
//输入： board = ["OOX","XXO","OXO"]
//输出： "Draw"
//解释： 没有玩家获胜且不存在空位
//示例 3：
//
//输入： board = ["OOX","XXO","OX "]
//输出： "Pending"
//解释： 没有玩家获胜且仍存在空位
//提示：
//
//1 <= board.length == board[i].length <= 100
//输入一定遵循井字棋规则
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/tic-tac-toe-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.design._348_设计井字棋;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2023-06-07.
 * @see _348_设计井字棋
 */
public class _16_04_井字游戏{
    //乍一看，可能会觉得此题很简单，不就是直接􏰑查􏱈字􏱉􏱊，这会有多难􏰩?细一想，此
    //题还是有点复杂的，而且没有唯一的“完美”答案。你的喜好不同，最佳解法也会不一样。
    //  解决此题，有几个重要的设计决􏱌需要考虑。
    //(1) hasWon 只会调用一次还是很多次(比如，放在网站上的􏱈字游􏱍)?如果答案是后者， 我们可能会增加一些预处理，以优化 hasWon 的运行时间。
    //(2) 我们知道最后一步吗?
    //(3) 􏱈字游􏱍通常是 3 × 3 􏱉􏱊。我们只是针对 3 × 3 大小的棋盘进行设计，还是要实现一个 N × N的解法?
    //(4) 对于程序大小、执行速度和代码清􏱎度，一般如何区分它们的优先级􏰩?记住:最高效 的代码不一定是最好的。代码是否易于理解且易维护也很重要。
    enum Piece{
        Empty, Blue, Red;
    }

    class Solution{
        // 如果是3*3棋盘 总共有 3^9，约 20000 种井字游戏棋盘（假设为3*3的棋盘）。因此一个int 就能表示，其中每个数位代表棋盘的一格（0为空，1
        // 为 #（红） ，2为 O（蓝））我们会事先设定好一个散列表或数组，将所有可能的棋盘作为键，值则代表谁赢了。这样，hasWon 函数就比较简单
        // 可以运用 3 进位 表示法，每个棋盘可以表示为 3^0 * v_0 + 3^1 * v_1 + ... + 3^8 * V_8, 若格子为空则 v_i 为0，格子为蓝 v_i = 1
        // 格子为红,则v_i = 2
        // 至此，要判断谁是赢家，要判断谁是赢家，只需查询散列表即可。
        // 当然，如果每次判断谁赢了都要转换成这种格式，那么跟其他解法相比，其实并没有什么优势。但是，如果一开始就是以这种格式存储棋盘，那那么
        // 查询效率会是非常高效的。
        Map<Integer, Piece> winnerHashtable = new HashMap<Integer, Piece>();

        Piece hasWon(int board) {
            return winnerHashtable.get(board);
        }

        int convertBoardToInt(Piece[][] board) {
            int sum = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    // 每个枚举类型的值都有整数与之对于，我们可以直接使用
                    int value = board[i][j].ordinal();
                    sum = sum * 3 + value;
                }
            }
            return sum;
        }
    }

    // 如果我们知道最后一步
    // 如果我们知道最后一步（并且至此为止都在不断地检查是否有人胜出），那么只需要检查与最后一步所走的位置相重叠的行、列和对角线即可
    class Solution2{
        Piece hasWon(Piece[][] board, int row, int col) {
            if (board.length != board[0].length) {
                return Piece.Empty;
            }
            Piece piece = board[row][col];
            if (piece == Piece.Empty) {
                return Piece.Empty;
            }

            if (hasWonRow(board, row) || hasWonCol(board, col)) {
                return piece;
            }

            if (row == col && hasWonDiagonal(board, 1)) {
                return piece;
            }
            if (row == (board.length - col - 1) && hasWonDiagonal(board, -1)) {
                return piece;
            }
            return Piece.Empty;
        }

        private boolean hasWonDiagonal(Piece[][] board, int direction) {
            int row = 0;
            int col = direction == 1 ? 0 :board.length - 1;
            Piece first = board[0][col];
            for (int i = 0; i < board.length; i++) {
                if (board[row][col] != first) {
                    return false;
                }
                row += 1;
                col += direction;
            }
            return true;
        }

        private boolean hasWonCol(Piece[][] board, int col) {
            for (int r = 0; r < board.length; r++) {
                if (board[r][col] != board[0][col]) {
                    return false;
                }
            }
            return true;
        }

        private boolean hasWonRow(Piece[][] board, int row) {
            for (int c = 0; c < board.length; c++) {
                if (board[row][c] != board[row][c]) {
                    return false;
                }
            }
            return true;
        }
        // 有一种方法清理上述代码中重复的部分，后面介绍
    }


    // 专为 3 * 3 棋盘设计
    // 如果只想为3*3棋盘设计一种解法，代码比较简单。
    // 该算法可行，这是因为理解起来比较简单，但是是以硬编码的方式实现的，不易扩展。
    class Solution3{
        Piece hasWon(Piece[][] board) {
            for (int i = 0; i < board.length; i++) {
                // 检查行
                if (hasWinner(board[i][0], board[i][1], board[i][2])) {
                    return board[i][0];
                }
                // 检查列
                if (hasWinner(board[0][i], board[1][i], board[2][i])) {
                    return board[0][i];
                }

            }
            // 检查对角线
            if (hasWinner(board[0][2], board[1][1], board[2][0])) {
                return board[0][2];
            }
            if (hasWinner(board[2][0], board[1][1], board[0][2])) {
                return board[2][0];
            }
            return Piece.Empty;
        }

        private boolean hasWinner(Piece p1, Piece p2, Piece p3) {
            if (p1 == Piece.Empty) {
                return false;
            }
            return p1 == p2 && p2 == p3;
        }
    }

    // 面向 n*n 棋盘设计
    class Solution4{
        // 嵌套for循环
        Piece hasWon(Piece[][] board) {
            int size = board.length;
            Piece first;
            // 检查行
            for (int i = 0; i < size; i++) {
                first = board[i][0];
                for (int j = 0; j < size; j++) {
                    if (first != board[i][j]) {
                        break;
                    }
                    else if (j == size - 1) { // 最后一个元素
                        return first;
                    }
                }
            }
            // 检查列
            for (int i = 0; i < size; i++) {
                first = board[0][i];
                for (int j = 0; j < size; j++) {
                    if (first != board[i][j]) {
                        break;
                    }
                    else if (j == size - 1) { // 最后一个元素
                        return first;
                    }
                }
            }
            //检查对角线
            first = board[0][0];
            if (first != Piece.Empty) {
                for (int i = 0; i < size; i++) {
                    if (first != board[i][i]) {
                        break;
                    }
                    else if (i == size - 1) { // 最后一个元素
                        return first;
                    }
                }
            }

            first = board[0][size - 1];
            if (first != Piece.Empty) {
                for (int i = 0; i < size; i++) {
                    if (first != board[i][size - i - 1]) {
                        break;
                    }
                    else if (i == size - 1) { // 最后一个元素
                        return first;
                    }
                }
            }
            return Piece.Empty;
        }
    }

    // 上述代码实现的比较粗错。我们每次基本上在做相同的事情。应该能够找到重用代码的方式。
    class Solution6{
        // increment, decrement 函数法
        // check 本质上承担了迭代器的任务
        class Check{
            public int row, col;
            private int rowIncrement, colIncrement;

            public Check(int row, int col, int rowI, int colI) {
                this.row = row;
                this.col = col;
                this.rowIncrement = rowI;
                this.colIncrement = colI;
            }

            public void increment() {
                row += rowIncrement;
                col += colIncrement;
            }

            public boolean inBound(int size) {
                return row >= 0 && row < size && col >= 0 && col < size;
            }
        }

        Piece hasWon(Piece[][] board) {
            if (board.length != board[0].length) {
                return Piece.Empty;
            }
            int size = board.length;
            // 创建一个链表
            ArrayList<Check> instructions = new ArrayList<>();
            for (int i = 0; i < board.length; i++) {
                instructions.add(new Check(0, i, 1, 0));
                instructions.add(new Check(i, 0, 1, 0));
            }
            instructions.add(new Check(0, 0, 1, 1));
            instructions.add(new Check(0, size - 1, 1, -1));

            // 检查每个元素
            for (Check instr : instructions) {
                Piece winner = hasWon(board, instr);
                if (winner != Piece.Empty) {
                    return winner;
                }
            }
            return Piece.Empty;
        }

        private Piece hasWon(Piece[][] board, Check instr) {
            Piece first = board[instr.row][instr.col];
            while (instr.inBound(board.length)) {
                if (first != board[instr.row][instr.col]) {
                    return Piece.Empty;
                }
                instr.increment();
            }
            return first;
        }
    }

    // 采用迭代器方式,创建一个真正迭代器。
    class Solution7{
        class PositionIterator implements Iterator<Position>{
            private int rowIncrement, colIncrement, size;
            private Position current;

            public PositionIterator(Position p, int rowIncrement, int colIncrement, int size) {
                this.rowIncrement = rowIncrement;
                this.colIncrement = colIncrement;
                this.size = size;
                this.current = new Position(p.row - rowIncrement, p.col - colIncrement);
            }

            @Override
            public boolean hasNext() {
                return current.row + rowIncrement < size &&
                        current.col + colIncrement < size;
            }

            @Override
            public Position next() {
                current = new Position(current.row + rowIncrement, current.col + colIncrement);
                return current;
            }
        }

        class Position{
            public int row, col;

            public Position(int row, int col) {
                this.row = row;
                this.col = col;
            }
        }

        Piece hasWon(Piece[][] board) {
            if (board.length != board[0].length) {
                return Piece.Empty;
            }
            int size = board.length;
            // 创建一个链表
            ArrayList<PositionIterator> instructions = new ArrayList<>();
            for (int i = 0; i < board.length; i++) {
                instructions.add(new PositionIterator(new Position(0, i), 1, 0, size));
                instructions.add(new PositionIterator(new Position(i, 0), 0, 1, size));
            }
            instructions.add(new PositionIterator(new Position(0, 0), 1, 1, size));
            instructions.add(new PositionIterator(new Position(0, size - 1), 1, -1, size));


            // 检查每个元素
            for (PositionIterator iterator : instructions) {
                Piece winner = hasWon(board, iterator);
                if (winner != Piece.Empty) {
                    return winner;
                }
            }
            return Piece.Empty;
        }

        private Piece hasWon(Piece[][] board, PositionIterator iterator) {
            Position firstPosition = iterator.next();
            Piece first = board[firstPosition.row][firstPosition.col];
            while (iterator.hasNext()) {
                Position position = iterator.next();
                if (board[position.row][position.col] != first) {
                    return Piece.Empty;
                }
            }
            return first;
        }
    }


    class Solution0{
        public String tictactoe(String[] board) {

            int length = board.length;
            int row = 0; //横的和
            int col = 0; //纵的和
            int left = 0; //左斜线
            int right = 0; //右斜线
            boolean flag = false; //记录有没有空格

            for (int i = 0; i < length; i++) {
                row = 0;
                col = 0;
                for (int j = 0; j < length; j++) {
                    row = row + (int) board[i].charAt(j);
                    col = col + (int) board[j].charAt(i);
                    if (board[i].charAt(j) == ' ') {
                        flag = true;
                    }
                }

                //横纵检查
                if (row == (int) 'X' * length || col == (int) 'X' * length) return "X";
                if (row == (int) 'O' * length || col == (int) 'O' * length) return "O";

                //两条斜线上的相加
                left = left + (int) board[i].charAt(i);
                right = right + (int) board[i].charAt(length - i - 1);

            }

            //两条斜线检查
            if (left == (int) 'X' * length || right == (int) 'X' * length) return "X";
            if (left == (int) 'O' * length || right == (int) 'O' * length) return "O";

            if (flag) return "Pending";
            return "Draw";

        }
    }
}

























