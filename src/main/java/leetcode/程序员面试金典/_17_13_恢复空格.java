package leetcode.程序员面试金典;
//哦，不！你不小心把一个长篇文章中的空格、标点都删掉了，并且大写也弄成了小写。像句子"I reset the computer. It still didn’t boot!"
// 已经变成了"iresetthecomputeritstilldidntboot"。在处理标点符号和大小写之前，你得先把它断成词语。当然了，你有一本厚厚的词典dictionary，
// 不过，有些词没在词典里。假设文章用sentence表示，设计一个算法，把文章断开，要求未识别的字符最少，返回未识别的字符数。
//
//注意：本题相对原题稍作改动，只需返回未识别的字符数
//
//
//
//示例：
//
//输入：
//dictionary = ["looked","just","like","her","brother"]
//sentence = "jesslookedjustliketimherbrother"
//输出： 7
//解释： 断句后为"jess looked just like tim her brother"，共7个未识别字符。
//提示：
//
//0 <= len(sentence) <= 1000
//dictionary中总字符数不超过 150000。
//你可以认为dictionary和sentence中只包含小写字母。
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/re-space-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author mayanwei
 * @date 2023-06-24.
 */
public class _17_13_恢复空格{
    /**
     * <pre>
     * 解决这个问题的关键在于找到一种方法来定义解析（即解析字符串）的子问题，其中一种方法时对字符串进行
     * 递归操作。
     * 我们首先要做的选择就是在哪里插入空格，
     * 以 thisismikesfavoritefood 为例，
     * 在 t 之后插入1个空格，会得到 1 个无效的字符。
     * 在 th 之后插入1个空格，会得到 2 个无效对字符。
     * 在 thi 之后插入1个空格，会得到 3 个无效对字符。
     * 在 this 之后插入1个空格，会得到 1 个完整的次，此时没有无效字符。
     * 在 thisi 之后插入1个空格，会得到 5 个无效字符。
     * 依此类推，
     * 在选择第一个插入空格的位置后，可以递归地插入第二个插入空格的位置，然后时第三个插入空格的位置，依此类推，直到
     * 处理完这个字符串为止。
     *
     * 时间复杂度 O(2^n)
     * </pre>
     */
    class Solution{
        public int respace(String[] dictionary, String sentence) {
            HashSet<String> dictionarySet = new HashSet<String>(Arrays.asList(dictionary));
            ParseResult r = split(dictionarySet, sentence, 0);
            return r.invalid;
        }

        private ParseResult split(HashSet<String> dictionary, String sentence, int start) {
            if (start >= sentence.length()) {
                return new ParseResult(0, "");
            }

            int bestInvalid = Integer.MAX_VALUE;
            String bestParsing = null;
            String partial = "";
            int index = start;
            while (index < sentence.length()) {
                char c = sentence.charAt(index);
                partial += c;
                int invalid = dictionary.contains(partial) ? 0 :partial.length();
                if (invalid < bestInvalid) { // 短路
                    // 递归，再次字符后加入一个空格，如果此处比当前最好情况更好，则此处代替当前最好的情况
                    ParseResult result = split(dictionary, sentence, index + 1);
                    if (invalid + result.invalid < bestInvalid) {
                        bestInvalid = invalid + result.invalid;
                        bestParsing = partial + " " + result.parsed;
                        if (bestInvalid == 0) {
                            break;
                        }
                    }
                }
                index++;
            }
            return new ParseResult(bestInvalid, bestParsing);
        }

        class ParseResult{
            public int invalid = Integer.MAX_VALUE;
            public String parsed = " ";

            public ParseResult(int inv, String p) {
                invalid = inv;
                parsed = p;
            }
        }
    }


    /**
     * 可行的一种方法是，认识到 split(i)只会对每个 i 的􏲀进行一次计算。假设我们已经通过 split(n - 1)调用了split(i+1)，
     * 当调用split(i)时会发生什么?
     * split(i) -> calls:
     * split(i + 1)
     * split(i + 2)
     * split(i + 3)
     * split(i + 4)
     * ...
     * split(n - 1)
     * 每个􏱖归调用都已经计算过了，所以它们会立即􏰦回。做 n  i 次时间复杂度为 O(1)的调用 将总共花费 O(n  i)的时间。
     * 这意味着，split(i)将最多花费 O(i)的时间。
     * 我们现在可以将同一􏴭􏴮方法应用于 split(i - 1)、split(i - 2)等调用中去。如果我 们调用一次 split(n - 1)，
     * 调用两次 split(n - 2)，调用三次 split(n - 3)，􏴞􏴞调用 n 次 split(0)，那么总共会有多少次调用?这其实是从 1 到 n 所有数的和，即 O(n2)。
     * 因此，这个􏱷数的时间复杂度是 O(n2)。
     */
    class Solution2{
        public int respace(String[] dictionary, String sentence) {
            HashSet<String> dictionarySet = new HashSet<String>(Arrays.asList(dictionary));
            ParseResult r = bestSplit(dictionarySet, sentence, 0);
            return r.invalid;
        }

        private ParseResult bestSplit(HashSet<String> dictionary, String sentence, int start) {
            ParseResult[] memo = new ParseResult[sentence.length()];
            ParseResult r = split(dictionary, sentence, 0, memo);
            return r == null ? null :r;
        }

        private ParseResult split(HashSet<String> dictionary, String sentence, int start, ParseResult[] memo) {
            if (start >= sentence.length()) {
                return new ParseResult(0, "");
            }
            if (memo[start] != null) {
                return memo[start];
            }

            int bestInvalid = Integer.MAX_VALUE;
            String bestParsing = null;
            String partial = "";
            int index = start;
            while (index < sentence.length()) {
                char c = sentence.charAt(index);
                partial += c;
                int invalid = dictionary.contains(partial) ? 0 :partial.length();
                if (invalid < bestInvalid) {
                    ParseResult result = split(dictionary, sentence, index + 1, memo);
                    if (invalid + result.invalid < bestInvalid) {
                        bestInvalid = invalid + result.invalid;
                        bestParsing = partial + " " + result.parsed;
                        if (bestInvalid == 0) {
                            break;
                        }
                    }
                }
                index++;
            }
            memo[start] = new ParseResult(bestInvalid, bestParsing);
            return memo[start];
        }

        class ParseResult{
            public int invalid = Integer.MAX_VALUE;
            public String parsed = " ";

            public ParseResult(int inv, String p) {
                invalid = inv;
                parsed = p;
            }
        }
    }

    class Solution3{
        public int respace(String[] dictionary, String sentence) {
            int n = sentence.length();
            if (n == 0) {
                return 0;
            }
            int[] dp = new int[n + 1];
            Trie tree = new Trie();
            for (String word : dictionary) {
                tree.insert(word);
            }
            for (int i = 1; i <= n; i++) {
                dp[i] = dp[i - 1] + 1;
                Trie node = tree;
                for (int j = i; j >= 1; j--) {
                    int index = sentence.charAt(j - 1) - 'a';
                    if (node.children[index] == null) {
                        break;
                    }
                    else if (node.children[index].isEnd) {
                        dp[i] = Math.min(dp[i], dp[j - 1]);
                    }
                    if (dp[i] == 0) {
                        break;
                    }
                    node = node.children[index];
                }
            }
            return dp[n];
        }

        public class Trie{

            Trie[] children;
            boolean isEnd;

            public Trie() {
                children = new Trie[26];// 全为小写字母
                isEnd = false;
            }

            public void insert(String word) {
                Trie node = this;
                int len = word.length();
                for (int i = len - 1; i >= 0; i--) {
                    char ch = word.charAt(i);
                    int index = ch - 'a';
                    if (node.children[index] == null) {
                        node.children[index] = new Trie();
                    }
                    node = node.children[index];
                }
                node.isEnd = true;
            }

        }
    }

    @Test
    public void test() {
        Solution solution = new Solution();
        String[] dictionary = {"looked", "just", "like", "her", "brother"};
        String sentences = "jesslookedjustliketimherbrother";
        Assert.assertEquals(7, solution.respace(dictionary, sentences));
    }
}
