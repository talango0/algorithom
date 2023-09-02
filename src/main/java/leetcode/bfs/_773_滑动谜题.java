package leetcode.bfs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
//在一个 2 x 3 的板上（board）有 5 块砖瓦，用数字 1~5 来表示, 以及一块空缺用 0 来表示。一次 移动 定义为选择 0 与一个相邻的数字（
//上下左右）进行交换.
//
// 最终当板 board 的结果是 [[1,2,3],[4,5,0]] 谜板被解开。
//
// 给出一个谜板的初始状态 board ，返回最少可以通过多少次移动解开谜板，如果不能解开谜板，则返回 -1 。
//
//
//
// 示例 1：
//
//
//
//
//输入：board = [[1,2,3],[4,0,5]]
//输出：1
//解释：交换 0 和 5 ，1 步完成
//
//
// 示例 2:
//
//
//
//
//输入：board = [[1,2,3],[5,4,0]]
//输出：-1
//解释：没有办法完成谜板
//
//
// 示例 3:
//
//
//
//
//输入：board = [[4,1,2],[5,0,3]]
//输出：5
//解释：
//最少完成谜板的最少移动次数是 5 ，
//一种移动路径:
//尚未移动: [[4,1,2],[5,0,3]]
//移动 1 次: [[4,1,2],[0,5,3]]
//移动 2 次: [[0,1,2],[4,5,3]]
//移动 3 次: [[1,0,2],[4,5,3]]
//移动 4 次: [[1,2,0],[4,5,3]]
//移动 5 次: [[1,2,3],[4,5,0]]
//
//
//
//
// 提示：
//
//
// board.length == 2
// board[i].length == 3
// 0 <= board[i][j] <= 5
// board[i][j] 中每个值都 不同
//
// Related Topics 广度优先搜索 数组 矩阵 👍 261 👎 0


/**
 * 字节
 * @author mayanwei
 * @date 2022-06-18.
 */
public class _773_滑动谜题{
    /**
     * 对于这种计算最小步数的问题，我们就要敏感地想到 BFS 算法。
     * <p>
     * 这个题目转化成 BFS 问题是有一些技巧的，我们面临如下问题：
     * <p>
     * 1、一般的 BFS 算法，是从一个起点 start 开始，向终点 target 进行寻路，但是拼图问题不是在寻路，而是在不断交换数字，这应该怎么转化成 BFS 算法问题呢？ <br/>
     * <p>
     * 2、即便这个问题能够转化成 BFS 问题，如何处理起点 start 和终点 target？它们都是数组哎，把数组放进队列，套 BFS 框架，想想就比较麻烦且低效。<br/>
     * <p>
     * 首先回答第一个问题，BFS 算法并不只是一个寻路算法，而是一种暴力搜索算法，只要涉及暴力穷举的问题，BFS 就可以用，而且可以最快地找到答案。<br/>
     * <p>
     * 你想想计算机怎么解决问题的？哪有那么多奇技淫巧，本质上就是把所有可行解暴力穷举出来，然后从中找到一个最优解罢了。<br/>
     * <p>
     * 明白了这个道理，我们的问题就转化成了：如何穷举出 board 当前局面下可能衍生出的所有局面？这就简单了，看数字 0 的位置呗，和上下左右的数字进行交换就行了：
     * 这样其实就是一个 BFS 问题，每次先找到数字 0，然后和周围的数字进行交换，形成新的局面加入队列…… 当第一次到达 target 时，就得到了赢得游戏的最少步数。<br/>
     * <p>
     * 对于第二个问题，我们这里的 board 仅仅是 2x3 的二维数组，所以可以压缩成一个一维字符串。其中比较有技巧性的点在于，二维数组有「上下左右」的概念，压缩成一维后，如何得到某一个索引上下左右的索引？<br/>
     * <p>
     * 对于这道题，题目说输入的数组大小都是 2 x 3，所以我们可以直接手动写出来这个映射：<br/>
     * <p>
     * // 记录一维字符串的相邻索引
     * int[][] neighbor = new int[][]{
     * {1, 3},
     * {0, 4, 2},
     * {1, 5},
     * {0, 4},
     * {3, 1, 5},
     * {4, 2}
     * };
     * 这个含义就是，在一维字符串中，索引 i 在二维数组中的的相邻索引为 neighbor[i]：<br/>
     * <p>
     * 那么对于一个 m x n 的二维数组，手写它的一维索引映射肯定不现实了，如何用代码生成它的一维索引映射呢？<br/>
     * <p>
     * 观察上图就能发现，如果二维数组中的某个元素 e 在一维数组中的索引为 i，那么 e 的左右相邻元素在一维数组中的索引就是 i - 1 和 i + 1，而 e 的上下相邻元素在一维数组中的索引就是 i - n 和 i + n，其中 n 为二维数组的列数。
     * <p>
     * 这样，对于 m x n 的二维数组，我们可以写一个函数来生成它的 neighbor 索引映射
     */

    class Solution{

        //记录一维数组中的相邻下标
        int[][] neighors = new int[][]{
                {1, 3},
                {0, 4, 2},
                {1, 5},
                {0, 4},
                {3, 1, 5},
                {4, 2}
        };

        public int slidingPuzzle(int[][] board) {
            int m = 2, n = 3;
            StringBuilder sb = new StringBuilder();
            String target = "123450";
            //选择2*3 的数组转化成为字符串作为BFS的起点。
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    sb.append(board[i][j]);
                }
            }
            String start = sb.toString();

            //BFS 框架开始
            Queue<String> q = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            //从起点开始BFS搜索
            q.offer(start);
            visited.add(start);
            int step = 0;
            while (!q.isEmpty()) {
                int sz = q.size();
                for (int i = 0; i < sz; i++) {
                    String cur = q.poll();
                    if (target.equals(cur)) {
                        return step;
                    }

                    //找到数字 0 的位置
                    int idx = 0;
                    for (; cur.charAt(idx) != '0'; idx++) ;
                    //将数字 0 和相邻的位置交换
                    for (int adj : neighors[idx]) {
                        String new_board = swap(cur.toCharArray(), idx, adj);
                        if (!visited.contains(new_board)) {
                            q.offer(new_board);
                            visited.add(new_board);
                        }
                    }
                }
                step++;
            }

            return -1;

        }

        public String swap(char[] chars, int i, int j) {
            char tmp = chars[i];
            chars[i] = chars[j];
            chars[j] = tmp;
            return new String(chars);

        }
    }
}
