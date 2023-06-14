package leetcode.程序员面试金典;
//在老式手机上，用户通过数字键盘输入，手机将提供与这些数字相匹配的单词列表。每个数字映射到0至4个字母。给定一个数字序列，
// 实现一个算法来返回匹配单词的列表。你会得到一张含有有效单词的列表。映射如下图所示：
//
//示例 1:
//
//输入: num = "8733", words = ["tree", "used"]
//输出: ["tree", "used"]
//示例 2:
//
//输入: num = "2", words = ["a", "b", "c", "d"]
//输出: ["a", "b", "c"]
//提示：
//
//num.length <= 1000
//words.length <= 500
//words[i].length == num.length
//num中不会出现 0, 1 这两个数字
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/t9-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.design.TrieSet;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author mayanwei
 * @date 2023-06-14.
 */
public class _16_20_T9键盘{
    /**
     * <pre>
     * ┌───────────────────┐
     * │    1    2      3  │
     * │        abc    def │
     * │    4    5      6  │
     * │   ghi  jkl    mlo │
     * │    7    8      9  │
     * │  pqrs  tuv   wxyz │
     * │         0         │
     * └───────────────────┘
     * </pre>
     */
    static class Solution{
        /**
         * 数字到字符的映射
         */
        char[][] t9Letter = new char[][]{null, null, {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'},
                {'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}};

        /**
         * 算法时间复杂度O(4^N)
         */
        public List<String> getValidT9Words(String num, String[] words) {
            List<String> result = new ArrayList<>();
            HashSet<String> wordSet = new HashSet<>(Arrays.asList(words));
            getValidT9Words(num, 0, "", wordSet, result);
            return result;
        }

        private void getValidT9Words(String number, int index, String prefix, HashSet<String> wordSet, List<String> result) {
            if (index == number.length() && wordSet.contains(prefix)) {
                result.add(prefix);
                return;
            }
            // 获取匹配该数位的字符
            char digit = number.charAt(index);
            char[] letters = getT9Chars(digit);

            // 遍历其与选项
            if (letters != null) {
                for (char letter : letters) {
                    getValidT9Words(number, index + 1, prefix + letter, wordSet, result);
                }
            }
        }

        private char[] getT9Chars(char digit) {
            if (!Character.isDigit(digit)) {
                return null;
            }
            return t9Letter[Character.getNumericValue(digit) - Character.getNumericValue('0')];
        }
    }

    // 利用Trie剪枝
    class Solution1{
        /**
         * 数字到字符的映射
         */
        char[][] t9Letter = new char[][]{null, null, {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'},
                {'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}};
        TrieSet trieSet = new TrieSet();

        /**
         * 算法时间复杂度O(4^N)
         */
        public List<String> getValidT9Words(String num, String[] words) {
            for (String word : words) {
                trieSet.add(word);
            }
            List<String> result = new ArrayList<>();
            HashSet<String> wordSet = new HashSet<>(Arrays.asList(words));
            getValidT9Words(num, 0, "", wordSet, result);
            return result;
        }

        private void getValidT9Words(String number, int index, String prefix, HashSet<String> wordSet, List<String> result) {
            if (index == number.length() && wordSet.contains(prefix)) {
                result.add(prefix);
                return;
            }
            // 获取匹配该数位的字符
            char digit = number.charAt(index);
            char[] letters = getT9Chars(digit);

            // 遍历其与选项
            if (letters != null) {
                for (char letter : letters) {
                    if (trieSet.hasKeyWithPrefix(prefix + letter)) {
                        getValidT9Words(number, index + 1, prefix + letter, wordSet, result);
                    }
                }
            }
        }

        private char[] getT9Chars(char digit) {
            if (!Character.isDigit(digit)) {
                return null;
            }
            return t9Letter[Character.getNumericValue(digit) - Character.getNumericValue('0')];
        }
    }

    /**
     * （1）预处理，构造一个散列表，将一串数组映射到一组字符串上
     * （2）遍历字典中的每个单词。并将这些单词转化为T9形式的表达式，（APPLE->27753）,将每个结果都存于步骤1的散列表中。
     * 例如：8733 会映射为{used, tree}
     */
    static class Solution2{

        char[][] t9Letter = new char[][]{null, null, {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'},
                {'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}};

        public List<String> getValidT9Words(String num, String[] words) {
            HashMap<String, List<String>> dictionary = initializeDictionary(words);
            return dictionary.getOrDefault(num, new ArrayList<>(0));
        }

        // 创建一个散列表，从一个数字映射到其代表的所有单词
        HashMap<String, List<String>> initializeDictionary(String[] words) {
            // 创建一个列表，从一个字符映射到数字
            HashMap<Character, Character> letterToNumberMap = createLetterToNumberMap();
            // 创建单词到数字的映射
            HashMap<String, List<String>> wordsToNumbers = new HashMap<>();
            for (String word : words) {
                String number = convertToT9(word, letterToNumberMap);
                List<String> list
                        = wordsToNumbers.getOrDefault(number, new ArrayList<String>());
                list.add(word);
                wordsToNumbers.put(number, list);
            }
            return wordsToNumbers;
        }

        //将数字到子母的映射转化为子母到数字的映射
        private HashMap<Character, Character> createLetterToNumberMap() {
            HashMap<Character, Character> letterToNumberMap = new HashMap<>();
            for (int i = 0; i < t9Letter.length; i++) {
                char[] letters = t9Letter[i];
                if (letters != null) {
                    for (char letter : letters) {
                        char c = Character.forDigit(i, 10);
                        letterToNumberMap.put(letter, c);
                    }
                }

            }
            return letterToNumberMap;
        }

        // 将字符串转化为T9
        String convertToT9(String word, HashMap<Character, Character> letterToNumberMap) {
            StringBuilder sb = new StringBuilder();
            for (char c : word.toCharArray()) {
                if (letterToNumberMap.containsKey(c)) {
                    sb.append(letterToNumberMap.get(c));
                }
            }
            return sb.toString();
        }

    }

    class Soltion4{
        // 建立字母对数字的映射
        int[] char2num = new int[]{2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9};

        public List<String> getValidT9Words(String num, String[] words) {
            int n = num.length();
            int[] nums = new int[n];
            // 为减少计算，把num字符串转换成int数组
            for (int i = 0; i < n; i++) {
                nums[i] = num.charAt(i) - '0';
            }

            List<String> ans = new ArrayList<>();
            for (String word : words) {
                if (isValid(nums, word)) {
                    ans.add(word);
                }
            }
            return ans;
        }

        // 根据映射判断单词是否合法
        private boolean isValid(int[] nums, String word) {
            for (int i = 0; i < nums.length; i++) {
                if (char2num[word.charAt(i) - 'a'] != nums[i]) {
                    return false;
                }
            }
            return true;
        }

    }

    @Test
    public void test() {
        String number = "8733";
        String[] words = new String[]{"tree", "used"};
        Solution2 solution2 = new Solution2();
        List<String> validT9Words = solution2.getValidT9Words(number, words);
        System.out.println(Arrays.toString(validT9Words.toArray()));
    }

}
