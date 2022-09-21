package leetcode.string;

import org.junit.Test;

import java.util.*;

//给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，在字符串中增加空格来构建一个句子，使得句子中所有的单词都在词典中。返回所有这些可能的
//句子。
//
// 说明：
//
//
// 分隔时可以重复使用字典中的单词。
// 你可以假设字典中没有重复的单词。
//
//
// 示例 1：
//
// 输入:
//s = "catsanddog"
//wordDict = ["cat", "cats", "and", "sand", "dog"]
//输出:
//[
//  "cats and dog",
//  "cat sand dog"
//]
//
//
// 示例 2：
//
// 输入:
//s = "pineapplepenapple"
//wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
//输出:
//[
//  "pine apple pen apple",
//  "pineapple pen apple",
//  "pine applepen apple"
//]
//解释: 注意你可以重复使用字典中的单词。
//
//
// 示例 3：
//
// 输入:
//s = "catsandog"
//wordDict = ["cats", "dog", "sand", "and", "cat"]
//输出:
//[]
//
// Related Topics 动态规划 回溯算法
// 👍 364 👎 0


/**
 * 字节
 * @see _472_连接词
 */
public class _140_单词拆分II {



    class Solution1 {
        public List<String> wordBreak(String s, List<String> wordDict) {
            List<String> res = new ArrayList<>();

            if(s == null || s.length() == 0){
                return res;
            }
            int len = s.length();
            boolean [] visited = new boolean[len+1];
            dfs(s, 0, len, visited, wordDict,new LinkedList<>(),res);
            return res;
        }
        private void dfs(String s, int start, int len, boolean[] visited, List<String> wordDict, Deque<String> path,List<String> res) {
            if(start == len){
                res.add(String.join(" ", path));
                return;
            }
            if(start > len){
                return;
            }
            for (String word : wordDict) {
                int nextStart = word.length() + start;
                if(nextStart > len){
                    continue;
                }
                if(word.equals(s.substring(start, nextStart))){
                    path.addLast(s.substring(start, nextStart));
                    dfs(s,nextStart,len, visited, wordDict, path,res);
                    path.removeLast();
                }

            }
        }

    }


    @Test
    public void testSolution1(){
        Solution1 solution1 = new Solution1();
        List<String> res = solution1.wordBreak("catsanddog", Arrays.asList(new String[]{"cat", "cats", "and", "sand", "dog"}));
        System.out.println(res.toString());
        List<String> res1 = solution1.wordBreak("pineapplepenapple", Arrays.asList(new String[]{"apple", "pen", "applepen", "pine", "pineapple"}));
        System.out.println(res1.toString());
        //Solution1会在该测试用例超时
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        List<String> res3 = solution1.wordBreak(s, Arrays.asList(new String[]{"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"}));
        System.out.println(res3.toString());

    }



    class Solution2 {
        public List<String> wordBreak(String s, List<String> wordDict) {
            List<String> res = new ArrayList<>();

            if(s == null || s.length() == 0){
                return res;
            }
            int len = s.length();
            boolean [] visited = new boolean[len+1];
            dfs(s, 0, len, visited, wordDict,new LinkedList<>(),res);
            return res;
        }
        private void dfs(String s, int start, int len, boolean[] visited, List<String> wordDict, Deque<String> path,List<String> res) {
            if(start == len){
                res.add(String.join(" ", path));
                return;
            }
            if(start > len){
                return;
            }
            for (String word : wordDict) {
                int nextStart = word.length() + start;
                if(nextStart > len || visited[nextStart] ){
                    continue;
                }
                if(s.indexOf(word,start) == start){
                    path.addLast(s.substring(start, nextStart));
                    dfs(s,nextStart,len, visited, wordDict, path,res);
                    path.removeLast();
                }

            }
        }

    }


    @Test
    public void testSolution2(){
        Solution2 solution2 = new Solution2();
        List<String> res = solution2.wordBreak("catsanddog", Arrays.asList(new String[]{"cat", "cats", "and", "sand", "dog"}));
        System.out.println(res.toString());
        List<String> res1 = solution2.wordBreak("pineapplepenapple", Arrays.asList(new String[]{"apple", "pen", "applepen", "pine", "pineapple"}));
        System.out.println(res1.toString());
        //Solution1会在该测试用例超时
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        List<String> res3 = solution2.wordBreak(s, Arrays.asList(new String[]{"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"}));
        System.out.println(res3.toString());

    }




    /*
    思路：
        动态规划得到了原始输入字符串的任意长度的前缀字串是否可以拆分为单词集合中的单词。
     */
    class Solution3{
        public List<String> wordBreak(String s, List<String> wordList){
            if(s == null || s.length() == 0){
                return new ArrayList<String>(0);
            }
            //1. 为了快速判读一个单词是否在单词集中，需要将他们加入到哈希表中
            Set<String> wordSet = new HashSet<>(wordList);
            int len = s.length();

            //2. 利用动态规划构建一个任意位置为后缀的前缀串是否可以拆分为wordList的单词
            boolean [] dp = new boolean[len+1];
            // 0 需要被后面的状态值参考，如果一个单词正好在wordList中，dp[0] 设置成true是合理的。
            dp[0] = true;
            for(int r = 1; r <= len; r++ ){
                for(int l = r-1; l >= 0; l--){
                    if(wordSet.contains(s.substring(l,r)) && dp[l]){
                        dp[r] = true;
                        //这个break很重要
                        break;
                    }
                }
            }
            //3. 回溯算法搜索所有符合条件的解
            ArrayList<String> res = new ArrayList<>();
            if(dp[len]){
                dfs(s, len, wordSet, dp, new ArrayDeque<>(), res);
                return res;
            }
            return res;
        }

        /**
         * s[0:len] 如果可以拆分成 WordSet 中的单词，把递归求解的结果加入到 res 中
         * @param s
         * @param len 长度为len的s的前缀字串
         * @param wordSet 单词集合，已经加入了哈希表
         * @param dp 预处理得到的dp数组
         * @param path 从叶节点到根节点的路径
         * @param res 保存所有结果变量
         */
        private void dfs(String s, int len, Set<String> wordSet, boolean[] dp, Deque<String> path, ArrayList<String> res) {
            if(len == 0){
                res.add(String.join(" ", path));
                return;
            }
            // 可以拆分的左边界从len-1 枚举到 0
            for(int i = len-1; i >= 0; i--){
                String suffix = s.substring(i, len);
                if(wordSet.contains(suffix) && dp[i]){
                    path.addFirst(suffix);
                    dfs(s, i, wordSet, dp, path, res);
                    path.removeFirst();
                }
            }
        }
    }
    @Test
    public void testSolution3(){
        Solution3 solution = new Solution3();
        List<String> res = solution.wordBreak("catsanddog", Arrays.asList(new String[]{"cat", "cats", "and", "sand", "dog"}));
        System.out.println(res.toString());
        List<String> res1 = solution.wordBreak("pineapplepenapple", Arrays.asList(new String[]{"apple", "pen", "applepen", "pine", "pineapple"}));
        System.out.println(res1.toString());
        //Solution1会在该测试用例超时
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        List<String> res3 = solution.wordBreak(s, Arrays.asList(new String[]{"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"}));
        System.out.println(res3.toString());

    }


}
