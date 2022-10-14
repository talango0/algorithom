package leetcode.jzhoffer;
//在字典（单词列表） wordList 中，从单词 beginWord 和 endWord 的 转换序列 是一个按下述规格形成的序列：
//
//
// 序列中第一个单词是 beginWord 。
// 序列中最后一个单词是 endWord 。
// 每次转换只能改变一个字母。
// 转换过程中的中间单词必须是字典 wordList 中的单词。
//
//
// 给定两个长度相同但内容不同的单词 beginWord 和 endWord 和一个字典 wordList ，找到从 beginWord 到 endWord
//的 最短转换序列 中的 单词数目 。如果不存在这样的转换序列，返回 0。
//
//
//
// 示例 1：
//
//
//输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot",
//"log","cog"]
//输出：5
//解释：一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的长度 5。
//
//
// 示例 2：
//
//
//输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot",
//"log"]
//输出：0
//解释：endWord "cog" 不在字典中，所以无法进行转换。
//
//
//
// 提示：
//
//
// 1 <= beginWord.length <= 10
// endWord.length == beginWord.length
// 1 <= wordList.length <= 5000
// wordList[i].length == beginWord.length
// beginWord、endWord 和 wordList[i] 由小写英文字母组成
// beginWord != endWord
// wordList 中的所有字符串 互不相同
//
//
//
//
//
// 注意：本题与主站 127 题相同： https://leetcode-cn.com/problems/word-ladder/
//
// Related Topics 广度优先搜索 哈希表 字符串 👍 24 👎 0

import leetcode.bfs._127_单词接龙;

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-10-14.
 * @see _127_单词接龙
 */
public class 剑指_Offer_II_108_单词演变{
    /**
     * 广度优先搜索
     */
    class Solution{
        Map<String, Integer> wordId = new HashMap<>();
        List<List<Integer>> edge = new ArrayList<>();
        private int nodeNum = 0;

        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            for (String word : wordList) {
                addEdge(word);
            }
            addEdge(beginWord);
            if (!wordId.containsKey(endWord)) {
                return 0;
            }

            int[] dis = new int[nodeNum];
            Arrays.fill(dis, Integer.MAX_VALUE);
            int beginId = wordId.get(beginWord), endId = wordId.get(endWord);
            dis[beginId] = 0;
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(beginId);
            while (!queue.isEmpty()) {
                int x = queue.poll();
                if (x == endId) {
                    return dis[endId] / 2 + 1;
                }
                for (int it : edge.get(x)) {
                    if (dis[it] == Integer.MAX_VALUE) {
                        dis[it] = dis[x] + 1;
                        queue.offer(it);
                    }
                }
            }
            return 0;

        }

        private void addEdge(String word) {
            addWord(word);
            Integer id1 = wordId.get(word);
            char[] array = word.toCharArray();
            int length = array.length;
            for (int i = 0; i < length; i++) {
                char tmp = array[i];
                array[i] = '*';
                String newWord = new String(array);
                addWord(newWord);
                Integer id2 = wordId.get(newWord);
                edge.get(id1).add(id2);
                edge.get(id2).add(id1);
                array[i] = tmp;
            }

        }

        private void addWord(String word) {
            if (!wordId.containsKey(word)) {
                wordId.put(word, nodeNum++);
                edge.add(new ArrayList<Integer>());
            }
        }
    }
}
