package leetcode.程序员面试金典;
//给定字典中的两个词，长度相等。写一个方法，把一个词转换成另一个词， 但是一次只能改变一个字符。每一步得到的新词都必须能在字典中找到。
//
//编写一个程序，返回一个可能的转换序列。如有多个可能的转换序列，你可以返回任何一个。
//
//示例 1:
//
//输入:
//beginWord = "hit",
//endWord = "cog",
//wordList = ["hot","dot","dog","lot","log","cog"]
//
//输出:
//["hit","hot","dot","lot","log","cog"]
//示例 2:
//
//输入:
//beginWord = "hit"
//endWord = "cog"
//wordList = ["hot","dot","dog","lot","log"]
//
//输出: []
//
//解释:endWord "cog" 不在字典中，所以不存在符合要求的转换序列。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/word-transformer-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.*;

/**
 * @author mayanwei
 * @date 2023-06-25.
 */
public class _17_22_单词转换{
    class Solution{
        public List<String> findLadders(String beginWord, String endWord, List<String> wordList) {
            //定义BFS的队列
            Queue<String> queue = new LinkedList<String>();
            //ans存放答案
            List<String> ans = new LinkedList<String>();
            //标记是否被访问过
            boolean[] visited = new boolean[wordList.size()];
            //存放每个单词的前驱，比如hot的前驱可以是hit,lot等；
            HashMap<String, String> map = new HashMap<String, String>();
            //初步判断
            if (!wordList.contains(endWord)) {
                return ans;
            }
            //将第一个单词加入队列
            queue.add(beginWord);
            boolean flag = false;
            //BFS主要操作
            while (queue.size() != 0) {
                //先将头取出
                String queueHead = queue.poll();
                //如果队列头元素等于endword，代表已经找到，break同时设置flag = true;
                if (queueHead.equals(endWord)) {
                    flag = true;
                    break;
                }
                //寻找可能的元素加入队列，并且设置对应的前驱。
                for (int i = 0; i < wordList.size(); i++) {
                    //如果未被访问过并且可以直接转换，则加入队列，compare()函数用来判断是否可以转换。
                    if (visited[i] == false && compare(wordList.get(i), queueHead) == true) {
                        queue.add(wordList.get(i));
                        visited[i] = true;
                        //存储前驱
                        map.put(wordList.get(i), queueHead);
                    }
                }
            }
            if (flag == false) {
                return ans;
            }

            //遍历答案
            String key = endWord;
            while (map.get(key) != beginWord) {
                ans.add(key);
                key = map.get(key);
            }
            ans.add(key);
            ans.add(map.get(key));
            Collections.reverse(ans);
            return ans;
        }

        public boolean compare(String word1, String word2) {
            int diff = 0;
            for (int i = 0; i < word1.length(); i++) {
                if (word1.charAt(i) != word2.charAt(i)) {
                    diff++;
                    if (diff >= 2) {
                        return false;
                    }
                }
            }
            return true;
        }
    }


    /**
     * 时间复杂度O(nc²) n=单词数量
     * 空间复杂度O(nc)  c=单词长度
     */
    class Solution1{
        /**
         * 单词编号
         */
        private final Map<String, Integer> wordId = new HashMap<>();
        /**
         * 最短路径
         */
        private final Deque<String> path = new LinkedList<>();
        /**
         * 前驱单词
         */
        private String[] precursor;
        /**
         * 当前正在扩展的单词是否是从begin端得到的
         */
        private boolean begin;

        public List<String> findLadders(String beginWord, String endWord, List<String> wordList) {
            if (null == beginWord || null == endWord || null == wordList) {
                return new ArrayList<>();
            }
            // 映射单词编号
            int i = 0;
            wordId.put(beginWord, i);
            for (String word : wordList) {
                if (!wordId.containsKey(word)) {
                    wordId.put(word, ++i);
                }
            }
            // 特殊判断
            if (!wordId.containsKey(endWord)) {
                return new ArrayList<>();
            }
            wordId.put(endWord, -wordId.get(endWord));
            if (beginWord.equals(endWord)) {
                return new ArrayList<>(Arrays.asList(beginWord));
            }
            // 双向BFS
            precursor = new String[wordId.size()];
            bidBFS(beginWord, endWord);
            return new ArrayList<>(path);
        }

        /**
         * 最短距离
         */
        private void bidBFS(String beginWord, String endWord) {
            // 双向扩散的HashSet代替单向BFS的队列
            Set<String> beginVisited = new HashSet<>(Arrays.asList(beginWord));
            Set<String> endVisited = new HashSet<>(Arrays.asList(endWord));
            // 双向广度优先搜索
            while (!beginVisited.isEmpty() && !endVisited.isEmpty()) {
                if (beginVisited.size() <= endVisited.size()) {
                    begin = true;
                    if (extendSmaller(beginVisited, endVisited)) {
                        return;
                    }
                }
                else {
                    begin = false;
                    if (extendSmaller(endVisited, beginVisited)) {
                        return;
                    }
                }
            }
        }

        /**
         * 扩展并更新较小的HashSet
         */
        private boolean extendSmaller(Set<String> smaller, Set<String> larger) {
            Set<String> nextToVisit = new HashSet<>();
            for (String word : smaller) {
                if (changeEachLetter(word, larger, nextToVisit)) {
                    return true;
                }
            }
            // 扩展后往下走一层
            smaller.clear();
            smaller.addAll(nextToVisit);
            return false;
        }

        /**
         * 遍历原词构造新词
         * 时间复杂度O(c×26×c)=O(c²)
         * 计算String哈希值需要O(c)时间
         *
         * @param oppVisited  对向已访问的单词集
         * @param nextToVisit 下次将要访问的单词集
         * @return 扩展后是否会落在对向已访问词集中
         */
        private boolean changeEachLetter(String word, Set<String> oppVisited, Set<String> nextToVisit) {
            char[] array = word.toCharArray();
            for (int i = 0; i < array.length; ++i) {
                char tmp = array[i];
                for (char c = 'a'; c <= 'z'; ++c) {
                    // 构造新词
                    if (c == tmp) {
                        continue;
                    }
                    array[i] = c;
                    String newWord = String.valueOf(array);
                    // 验证新词是否在词表中
                    if (!wordId.containsKey(newWord)) {
                        continue;
                    }
                    // 落在对向已访问过的集合中则得到结果
                    if (oppVisited.contains(newWord)) {
                        if (begin) {
                            setResult(word, newWord);
                        }
                        else {
                            setResult(newWord, word);
                        }
                        return true;
                    }
                    // 未访问过则下次访问
                    int id = wordId.get(newWord);
                    if (id > 0) {
                        nextToVisit.add(newWord);
                        wordId.put(newWord, -id);
                        precursor[id] = word;
                    }
                }
                array[i] = tmp;
            }
            return false;
        }

        /**
         * begin ← ··· ← mid1 mid2 → ··· → end
         */
        private void setResult(String mid1, String mid2) {
            path.offerFirst(mid1);
            for (String pre = precursor[-wordId.get(mid1)]; null != pre; pre = precursor[-wordId.get(pre)]) {
                path.offerFirst(pre);
            }
            path.offerLast(mid2);
            for (String pre = precursor[-wordId.get(mid2)]; null != pre; pre = precursor[-wordId.get(pre)]) {
                path.offerLast(pre);
            }
        }
    }


}
