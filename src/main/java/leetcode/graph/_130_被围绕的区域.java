package leetcode.graph;
//给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充
//。
//
//
//
//
// 示例 1：
//
//
//输入：board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O",
//"X","X"]]
//输出：[["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
//解释：被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都
//会被填充为 'X'。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
//
//
// 示例 2：
//
//
//输入：board = [["X"]]
//输出：[["X"]]
//
//
//
//
// 提示：
//
//
// m == board.length
// n == board[i].length
// 1 <= m, n <= 200
// board[i][j] 为 'X' 或 'O'
//
//
//
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 矩阵 👍 824 👎 0

public class _130_被围绕的区域 {
    class Solution {
        // 首先对边界上每一个'O'做深度优先搜索，将与其相连的所有'O'改为'-'。然后遍历矩阵，将矩阵中所有'O'改为'X',将矩阵中所有'-'变为'O'
        int row,col;
        public void solve(char[][] board) {
            if(board==null||board.length==0)
                return ;
            row=board.length;
            col=board[0].length;
            for(int i=0;i<row;i++){
                dfs(board,i,0);
                dfs(board,i,col-1);
            }
            for(int j=0;j<col;j++){
                dfs(board,0,j);
                dfs(board,row-1,j);
            }
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    if(board[i][j]=='O')
                        board[i][j]='X';
                    if(board[i][j]=='-')
                        board[i][j]='O';
                }
            }
            return ;
        }
        public void dfs(char[][] board,int i,int j){
            if(i<0||j<0||i>=row||j>=col||board[i][j]!='O')
                return;
            board[i][j]='-';
            dfs(board,i-1,j);
            dfs(board,i+1,j);
            dfs(board,i,j-1);
            dfs(board,i,j+1);
            return ;
        }

    }

    class Solution2 {
        // 这个问题应该归为 岛屿系列问题 使用DFS解决
        // 先用 for 循环遍历棋盘的四边，用DFS算法把那些与边界相连的 O 换成 # ，然后再遍历整个棋盘，把剩下的 O 换成 X ， 再把 # 换成 O。
        // 也可用 union-find算法解决，可以把那些 不需要被替换的 O 看出一个门派，它们有一个共同的【祖师爷】 dummy,这些 O 和 dummy互相联通，而那些需要被替换的 O 与 dummy不连通。
        public void solve(char[][] board) {
            // 二维坐标(x,y) 可以转换成 x*n+y 这个数（m是棋盘的行数，n是棋盘的列数），这也是二维坐标映射到以为的常用技巧。 [0, m*n-1] 都是棋盘内坐标的一维映射，dummy 占据 m*n 的位置
            if (board.length == 0) return;
            int m = board.length;
            int n = board[0].length;
            // 给dummy 留一额外的位置
            UF uf = new UF(m*n+1);
            int dummy = m*n;
            // 将首列和尾列的 O 与 dummy相连
            for (int i = 0; i<m; i++) {
                if (board[i][0] == 'O') {
                    uf.union( i*n, dummy );
                }
                if (board[i][n-1] == 'O') {
                    uf.union(i*n+n-1, dummy);
                }
            }
            // 将首行和尾行的 O 与 dummy 相连
            for (int j = 0; j<n; j++) {
                if (board[0][j] == 'O') {
                    uf.union(j, dummy);
                }
                if (board[m-1][j] == 'O') {
                    uf.union((m-1)*n + j, dummy);
                }
            }

            // 方向数组d 是上下左右常用的手法
            int [][] d = new int[][]{{1, 0}, {0, 1},  {0, -1}, {-1, 0}};
            for (int i = 1; i < m-1; i++) {
                for (int j = 1; j < n-1; j++) {
                    if (board[i][j] == 'O') {
                        // 将此0 与上下左右的 O  相连通
                        for (int k = 0; k < 4; k++) {
                            int x = i + d[k][0];
                            int y = j + d[k][1];
                            if (board[x][y] == 'O') {
                                uf.union( x * n + y, i * n + j );
                            }
                        }
                    }
                }
            }

            // 把所有不和 dumy 连通的 O ，都要被替换
            for (int i = 1; i<m-1; i++) {
                for (int j = 1; j < n-1; j++) {
                    if (!uf.connected(i*n+j, dummy)) {
                        board[i][j] = 'X';
                    }
                }
            }
        }

        class UF {
            //连通分量个数
            private int count;
            //存每个节点的父节点
            private int [] parent;
            public UF(int num){
                parent = new int[num];
                for (int i = 0; i<num; i++) {
                    parent[i] = i;
                }
            }
            public void union(int p, int q) {
                int rootP = find(p);
                int rootQ = find(q);
                if ( rootP == rootQ) {
                    return;
                }
                parent[rootP] = rootQ;
                // 连通分量合并成一个连通分量
                count--;
            }

            public boolean connected(int p, int q) {
                return find(p) == find(q);
            }

            public int find(int x) {
                if (parent[x] != x) {
                    parent[x] = find(parent[x]);
                }
                return parent[x];
            }
        }

    }

}
