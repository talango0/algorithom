package leetcode.string;
//给你一个字符串 s ，颠倒字符串中 单词 的顺序。
//
//单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
//
//返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
//
//注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
//
//示例 1：
//
//输入：s = "
//the sky is blue
//"
//输出："
//blue is sky the
//"
//示例 2：
//
//输入：s = "  hello world  "
//输出："world hello"
//解释：颠倒后的字符串中不能存在前导空格和尾随空格。
//示例 3：
//
//输入：s = "a good   example"
//输出："example good a"
//解释：如果两个单词间有多余的空格，颠倒后的字符串需要将单词间的空格减少到仅有一个。
//提示：
//
//1 <= s.length <= 104
//s 包含英文大小写字母、数字和空格 ' '
//s 中 至少存在一个 单词
//进阶：如果字符串在你使用的编程语言中是一种可变数据类型，请尝试使用 O(1) 额外空间复杂度的 原地 解法。
//
//Related Topics
//
//👍 623, 👎 0


import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-08-14.
 */
public class _151_颠倒字符串中的单词{

    class Solution{
        public String reverseWords(String s) {
            if (s == null || s.length() == 0) {
                return s;
            }
            // 先清洗一下数据，把首位的空格和传中多余的空格删除
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != ' ') {
                    // 单词中的字母/数字
                    sb.append(s.charAt(i));
                }
                else if (!(sb.length() > 0) && sb.charAt(sb.length() - 1) != ' ') {
                    // 单词之间保留一个空格
                    sb.append(' ');
                }
            }
            // 末尾如果有空格，清除之
            if (sb.charAt(sb.length() - 1) == ' ') {
                sb.deleteCharAt(sb.length() - 1);
            }

            // 清除之后的字符串
            char[] chars = sb.toString().toCharArray();
            int n = chars.length;
            // 进行单词反转，先整体反转
            reverse(chars, 0, n - 1);

            // 再把每个单词反转
            for (int i = 0; i < n; ) {
                for (int j = i; j < n; j++) {
                    if (j + 1 == n || chars[j + 1] == ' ') {
                        // chars[i..j]是一个单词，翻转之
                        reverse(chars, i, j);
                        // 把 i 置为下一个字母的首字母
                        i = j + 2;
                        break;
                    }
                }
            }
            return new String(chars);
        }

        private void reverse(char[] chars, int i, int j) {
            while (i < j) {
                char tmp = chars[i];
                chars[i] = chars[j];
                chars[j] = tmp;
                i++;
                j--;
            }

        }
    }

    /**
     * 利用库函数
     */
    class Solution2 {
        public String reverseWords(String s) {
            if (s == null || s.length() == 0) {
                return s;
            }
            s = s.trim();
            String [] strs = s.split("\\s+");
            List<String> list = Arrays.asList(strs);
            Collections.reverse( list );
            return String.join(" ",list);
        }
    }

    /**
     * 利用双端队列
     */
    class Solution4 {
        public String reverseWords(String s) {
            if (s == null || s.length() == 0) {
                return s;
            }
            StringBuilder word = new StringBuilder();
            Deque<String> dq = new LinkedList<String>();
            char c = 0;
            int n = s.length();
            for (int i = 0; i < n; i++) {
                c = s.charAt(i);
                if (c != ' ') {
                    word.append(c);
                } else {
                    if (word.length() > 0) {
                        dq.offerFirst(word.toString());
                        word.setLength(0);
                    }
                }
                if (i+1 == n && word.length() >0){

                    word.setLength(0);
                }

            }
            return String.join(" ", dq);
        }
    }



    @Test
    public void test3(){
        String [] strs = " s   b".split("\\s+");
        System.out.println(strs.toString());
        Solution4 solution4 = new Solution4();
        String str = solution4.reverseWords("the sky is blue");
        System.out.println(str);
    }
}

