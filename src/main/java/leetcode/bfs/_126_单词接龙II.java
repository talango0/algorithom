package leetcode.bfs;
//按字典 wordList 完成从单词 beginWord 到单词 endWord 转化，一个表示此过程的 转换序列 是形式上像 beginWord ->
//s1 -> s2 -> ... -> sk 这样的单词序列，并满足：
//
//
//
//
// 每对相邻的单词之间仅有单个字母不同。
// 转换过程中的每个单词 si（1 <= i <= k）必须是字典 wordList 中的单词。注意，beginWord 不必是字典 wordList 中的单
//词。
// sk == endWord
//
//
//
//
// 给你两个单词 beginWord 和 endWord ，以及一个字典 wordList 。请你找出并返回所有从 beginWord 到 endWord 的
// 最短转换序列 ，如果不存在这样的转换序列，返回一个空列表。每个序列都应该以单词列表 [beginWord, s1, s2, ..., sk] 的形式返回。
//
//
//
// 示例 1：
//
//
//输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot",
//"log","cog"]
//输出：[["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
//解释：存在 2 种最短的转换序列：
//"hit" -> "hot" -> "dot" -> "dog" -> "cog"
//"hit" -> "hot" -> "lot" -> "log" -> "cog"
//
//
// 示例 2：
//
//
//输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot",
//"log"]
//输出：[]
//解释：endWord "cog" 不在字典 wordList 中，所以不存在符合要求的转换序列。
//
//
//
//
// 提示：
//
//
// 1 <= beginWord.length <= 5
// endWord.length == beginWord.length
// 1 <= wordList.length <= 500
// wordList[i].length == beginWord.length
// beginWord、endWord 和 wordList[i] 由小写英文字母组成
// beginWord != endWord
// wordList 中的所有单词 互不相同
//
//
// Related Topics 广度优先搜索 哈希表 字符串 回溯 👍 610 👎 0

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-09-11.
 * @see _127_单词接龙
 */
public class _126_单词接龙II{
    class Solution{
        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
            List<List<String>> res = new ArrayList<>();
            //需要快速地判断扩展出的单词是否在 wordList 中
            Set<String> set = new HashSet<String>(wordList);
            // 特殊用例判断
            if (!set.contains(endWord)) {
                return res;
            }
            set.remove(beginWord);
            //1.广度优先搜索建图
            // 记录扩展出的单词在第几次扩展的时候得到的, key:单词，value: 在广度优先搜索的第几层
            Map<String, Integer> steps = new HashMap<>();
            steps.put(beginWord, 0);
            // 记录单词是从哪些单词扩展而来, key:单词， value:单词列表，这些单词可以变换到key， 它们是一对多的关系
            Map<String, List<String>> from = new HashMap<>();
            int step = 1;
            boolean found = false;
            int wordLen = beginWord.length();
            Queue<String> queue = new ArrayDeque<>();
            queue.offer(beginWord);
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    String currWord = queue.poll();
                    char[] charArray = currWord.toCharArray();
                    // 将每一个替换成26个小写英文字母
                    for (int j = 0; j < wordLen; j++) {
                        char origin = charArray[j];
                        for (char c = 'a'; c <= 'z'; c++) {
                            charArray[j] = c;
                            String nextWord = String.valueOf(charArray);
                            if (steps.containsKey(nextWord) && step == steps.get(nextWord)) {
                                from.get(nextWord).add(currWord);
                            }
                            if (!set.contains(nextWord)) {
                                continue;
                            }
                            // 如果从一个单词扩展出来的单词以前遍历过，距离一定更远，为了避免搜索到已经遍历到，且距离更远的单词，需要将它从set中删除
                            set.remove(nextWord);
                            // 这一层扩展出的单词进入队列
                            queue.offer(nextWord);

                            // 记录 nextWord 从 currWord 而来
                            from.putIfAbsent(nextWord, new ArrayList<>());
                            from.get(nextWord).add(currWord);
                            // 记录nextWord的 step
                            steps.put(nextWord, step);
                            if (nextWord.equals(endWord)) {
                                found = true;
                            }
                        }
                        charArray[j] = origin;
                    }
                }
                step++;
                if (found) {
                    break;
                }
            }
            if (found) {
                Deque<String> path = new ArrayDeque<>();
                path.add(endWord);
                backtrace(from, path, beginWord, endWord, res);
            }
            return res;
        }

        public void backtrace(Map<String, List<String>> from, Deque<String> path, String beginWord, String cur, List<List<String>> res) {
            if (cur.equals(beginWord)) {
                res.add(new ArrayList<>(path));
                return;
            }
            for (String precursor : from.get(cur)) {
                path.addFirst(precursor);
                backtrace(from, path, beginWord, precursor, res);
                path.removeFirst();
            }
        }
    }
}
