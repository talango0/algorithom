package leetcode.string;


//给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words，找出所有同时在二维网格和字典中出现的单词。
//
// 单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使
//用。
//
//
//
// 示例 1：
//
//
//输入：board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l"
//,"v"]], words = ["oath","pea","eat","rain"]
//输出：["eat","oath"]
//
//
// 示例 2：
//
//
//输入：board = [["a","b"],["c","d"]], words = ["abcb"]
//输出：[]
//
//
//
//
// 提示：
//
//
// m == board.length
// n == board[i].length
// 1 <= m, n <= 12
// board[i][j] 是一个小写英文字母
// 1 <= words.length <= 3 * 104
// 1 <= words[i].length <= 10
// words[i] 由小写英文字母组成
// words 中的所有字符串互不相同
//
// Related Topics 字典树 回溯算法
// 👍 280 👎 0


import java.util.*;

public class _212_单词搜索II {
    int [][] direction ={{0, -1},{0, 1}, {-1,0}, {1,0}};
    class Solution1 {
        public List<String> findWords(char[][] board, String[] words) {
            if(board == null || board.length == 0){
                return new ArrayList<>(0);
            }
            Trie trie = new Trie();
            for(String w:words){
                trie.insert(w, w);
            }

            Set<String> ans = new HashSet<>();
            for(int i = 0; i< board.length; i++){
                for(int j = 0; j < board[i].length; j++){
                    char c = board[i][j];
                    if(trie.root.next[c] != null){
                        dfs(board,new int [board.length][board[i].length], trie.root.next[c], i, j, board.length, board[i].length, 0, ans);
                    }
                }
            }
            return new ArrayList<>(ans);
        }

        private void dfs(char[][] board,int [][] visited, Trie.Node x, int i, int j, int m, int n, int d,  Set<String> ans) {
            if(i<0 || j< 0|| i >= m || j>= n || x == null || visited[i][j] == 1){
                if(visited[i][j] != 1 && x!= null && x.value != null){
                    ans.add(x.value);
                }
                return;
            }
            visited[i][j] = 1;

            if(x!=null && x.value != null){
                ans.add(x.value);
            }

            //向左搜索
            if(j > 0){
                char c = board[i][j - 1];
                dfs(board,visited,x.next[c],i, j-1, m, n, d+1, ans);
            }

            //向右搜索
            if(j < n-1){
                char c = board[i][j+1];
                dfs(board, visited, x.next[c],i, j+1, m, n, d+1, ans);
            }

            //向上搜索
            if(i > 0){
                char c = board[i-1][j];
                dfs(board,visited, x.next[c],i-1, j, m, n, d+1, ans);
            }

            //向下搜索
            if(i < m-1){
                char c = board[i+1][j];
                dfs(board,visited,x.next[c],i+1, j, m, n, d+1, ans);
            }
        }
    }

     static class Trie{
        Node root;
        private static int R = 256;
        static class Node{
            String value;
            Node [] next = new Node[R];
        }
        public void insert(String k, String v){
            root = insert(root, k, v, 0);
        }

        private Node insert(Node x, String k, String v, int d) {
            if(x == null){
                x = new Node();
            }
            if(d == k.length()){
                x.value = v;
                return x;
            }
            char c = k.charAt(d);
            x.next[c] = insert(x.next[c], k, v, d+1);
            return x;
        }

    }

    void testSolution1(){
        char[][] board = new char[][]{{'o','a','a','n'},{'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}};
        String [] words = {"oath","pea","eat","rain"};

        Solution1 solution1 = new Solution1();
        List<String> ans = solution1.findWords(board, words);
        System.out.println(ans);

        char [][] board2 = {{'a','a'}};
        String [] words2 = {"aaa"};
        List<String> words1 = solution1.findWords(board2, words2);
        System.out.println(words1);

        //			Runtime:4 ms, faster than 98.89% of Java online submissions.
        //			Memory Usage:38.8 MB, less than 95.83% of Java online submissions.
    }
}
