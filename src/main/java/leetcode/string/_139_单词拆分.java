package leetcode.string;

import org.junit.Test;

import java.util.*;


//给定一个非空字符串 s 和一个包含非空单词的列表 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
//
// 说明：
//
//
// 拆分时可以重复使用字典中的单词。
// 你可以假设字典中没有重复的单词。
//
//
// 示例 1：
//
// 输入: s = "leetcode", wordDict = ["leet", "code"]
//输出: true
//解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
//
//
// 示例 2：
//
// 输入: s = "applepenapple", wordDict = ["apple", "pen"]
//输出: true
//解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
//     注意你可以重复使用字典中的单词。
//
//
// 示例 3：
//
// 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
//输出: false
//
// Related Topics 动态规划
// 👍 754 👎 0

/**
 * @see _472_连接词
 * @see _140_单词拆分II
 * @see _472_连接词
 */
public class _139_单词拆分 {

    /*
    分析：
        dp[i] 表示字符串s前i个字符组成的字符串 s[0..i-1] 是否能够被空格拆分成若干字典中出现的单词。
    从前往后计算考虑状态转移方程，每次转移的时候需要枚举包含位置 i-1 的最后一个单词。看它是否出现在字典中以及除去这部分的字符串是否合法即可。
        枚举 s[0..i-1] 中的分割点j， 看 s[0..j-1] 组成的字符串s1（默认 j=0 时为空串）和 s[j..i-1]组成的字符串 s2 是否都合法，如果两个字符串
    均合法，那么按照定义 s1 和 s2 拼接成的字符串也合法。
        由于计算到 dp[i] 时我们计算出了 dp[0..i-1] 的值，因此字符串s1是否合法可以直接由dp[j]得知，剩下的我们只需要看s2是否合法即可，因此得出如下
    的状态转移方程：
        dp[i] = dp[j] && check(s[j..i-1])
    其中 check(s[j..i-1]) 表示字串 s[j..i-1] 是否出现在字典中。边界条件 dp[0] = true

    总结：
        对于检查一个字符串是否出现在给定字符串列表里面一般可以考虑哈希表来快速判断，同时也可以做一些剪枝，枚举分割点的时候倒着枚举，如果分割点
        j到i的长度已经大于字典表里最长的单词的长度，那么就结束枚举。
     */

    /**
     * 动态规划
     */
    class Solution1 {
        public boolean wordBreak(String s, List<String> wordDict) {
            if(s == null || s.length() == 0){
                return false;
            }
            Set<String> set = new HashSet<String>(wordDict);
            boolean[] dp = new boolean[s.length() + 1];
            dp[0] = true;
            for(int i = 1; i<= s.length(); i++){
                for(int j = 0; j < i; j++){
                    if(dp[j] && set.contains(s.substring(j,i))){
                        dp[i] = true;
                        break;
                    }
                }
            }
            return dp[s.length()];
        }
    }

    /**
     * DFS
     */
    class Solution2{
        public boolean wordBreak(String s, List<String> wordDict) {
            if(s == null || s.length() == 0){
                return false;
            }
            boolean[] visited = new boolean[s.length() + 1];
            return dfs(s, 0, wordDict, visited);

        }
        private boolean dfs(String s, int start, List<String> wordDict, boolean[] visited) {
            for (String word : wordDict) {
                int nextStart = start + word.length();
                if (nextStart > s.length() || visited[nextStart]) {
                    continue;
                }

                if (s.indexOf(word, start) == start) {
                    if (nextStart == s.length() || dfs(s, nextStart, wordDict, visited)) {
                        return true;
                    }
                    visited[nextStart] = true;
                }
            }
            return false;
        }
    }

    @Test public void testSolution2(){
        Solution2 solution2 = new Solution2();
        solution2.wordBreak("leetcode",Arrays.asList(new String[]{"leet", "code"}));
    }


    /**
     * BFS
     */
    class Solution3 {
        public boolean wordBreak(String s, List<String> wordDict) {
            if(s == null || s.length() == 0){
                return false;
            }
            Deque<Integer> queue = new LinkedList<>();
            queue.addLast(0);

            int len = s.length();
            boolean[] visited = new boolean[len+1];
            while (!queue.isEmpty()){
                int size = queue.size();
                for(int i = 0 ; i<size; i++){
                    int start = queue.removeFirst().intValue();
                    for (String word : wordDict) {
                        int nextStart = start + word.length();
                        if(nextStart > len || visited[nextStart]){
                            continue;
                        }
                        if(s.indexOf(word, start) == start){
                            if(nextStart == len){
                                return true;
                            }
                            queue.addLast(nextStart);
                            visited[nextStart] = true;
                        }
                    }
                }
            }
            return false;
        }
    }

    /**
     *字典树
     */
    class Solution4 {
        public boolean wordBreak(String s, List<String> wordDict) {
            Trie trie = new Trie();
            for(int i=0;i<wordDict.size();i++){
                //减少字典中重复可匹配的词,注释后还快了···
                //if(!trie.searchall(wordDict.get(i))){
                //System.out.println(wordDict.get(i));
                trie.insert(wordDict.get(i));
                //}
            }
            return trie.searchall(s);
        }
    }
    //先设计一个TireNode类
    class TrieNode{
        //TrieNode由hash+链表组成
        //这里数组索引作用是hash函数的作用，用空间换时间
        //保存该node的next
        private TrieNode[] links;
        //这里只需要26个小写字母，所以R=26
        private final int R = 26;
        //保存是否是末尾
        private boolean isEnd;
        //初始化links
        public TrieNode() {
            links = new TrieNode[R];
        }
        //如果有ch则links[ch -'a'] != null
        public boolean containsKey(char ch) {
            return links[ch -'a'] != null;
        }
        //返回ch节点
        public TrieNode get(char ch) {
            return links[ch -'a'];
        }
        //ch的节点为node
        public void put(char ch, TrieNode node) {
            links[ch -'a'] = node;
        }
        //设置结束
        public void setEnd() {
            isEnd = true;
        }
        //返回是否结束
        public boolean isEnd() {
            return isEnd;
        }
    }
    //这里相当于把所有的从根节点的路径全部保存，然后进行标记
    class Trie {
        //根节点
        private TrieNode root;
        //回溯返回结果
        private boolean ret;//返回结果ret
        //不符合起始串减枝
        private int[] startidx;//保存起始索引到最远处的长度
        //保存遍历searchall()输入
        private String word_temp;
        public Trie() {
            root = new TrieNode();
        }
        public void insert(String word) {
            //递归插入
            TrieNode node = root;
            for(int i=0;i<word.length();i++){
                char ch=word.charAt(i);
                //从根节点开始，如果node没有这个字母就插入
                if(!node.containsKey(ch)) {
                    //生成一个新的26数组的节点，并使当前node的ch保存这个节点的地址
                    node.put(ch, new TrieNode());
                }
                //返回数组索引ch出的TrieNode类的引用
                node=node.get(ch);
            }
            //完成时node节点没有保存任何子节点，保存结尾接点isEnd为空
            node.setEnd();
        }
        //true返回否则直到结束返回false
        public boolean searchall(String word) {
            word_temp=word;
            ret=false;
            startidx=new int[word.length()];
            backtrack(0,root);
            return ret;
        }
        void backtrack(int idx,TrieNode cru){
            if (ret||idx==word_temp.length()) return;
            int i=idx;
            while(i<word_temp.length()){
                char ch = word_temp.charAt(i);
                if (cru.containsKey(ch)) {
                    cru = cru.get(ch);
                }else{
                    //这里进行检查无法通过的字符串是否可以匹配
                    return;
                }
                if(cru.isEnd()){
                    if(i==word_temp.length()-1){
                        ret=true;
                    }
                    startidx[idx]=Math.max(startidx[idx],i+1);
                    //减少重复计算，startidx[i+1]!=0的说明已经计算过了
                    if(i<word_temp.length()-1&&startidx[i+1]==0){
                        backtrack(i+1,root);
                    }
                }
                i++;
            };
        }
    }


}
