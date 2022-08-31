package leetcode.stack;

import java.util.ArrayList;
import java.util.List;
//给你一个由若干括号和字母组成的字符串 s ，删除最小数量的无效括号，使得输入的字符串有效。
//
//返回所有可能的结果。答案可以按 任意顺序 返回。
//
//示例 1：
//
//输入：s = "()())()"
//输出：["(())()","()()()"]
//示例 2：
//
//输入：s = "(a)())()"
//输出：["(a())()","(a)()()"]
//示例 3：
//
//输入：s = ")("
//输出：[""]
//提示：
//
//1 <= s.length <= 25
//s 由小写英文字母以及括号 '(' 和 ')' 组成
//s 中至多含 20 个括号
//Related Topics
//
//👍 760, 👎 0
/**
 * 字节
 * @author mayanwei
 * @date 2022-08-24.
 * @see _921_使括号有效的最少添加
 * @see _20_有效的括号
 */
public class _301_删除无效的括号{
    class Solution {
        private List<String> res = new ArrayList<String>();
        public List<String> removeInvalidParentheses(String s) {
            // 遍历完表达式应该删除的左括号和右括号数
            int left = 0, right = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') {
                    left ++;
                }
                else if(s.charAt(i) == ')') {
                    if (left > 0) {
                        left --;
                    }
                    else {
                        right ++;
                    }
                }
            }
            // 利用回溯尝试删除left和right 的所有情况
            backtrace(s, 0, left, right);
            return res;
        }
        public void backtrace(String s, int start, int left, int right) {
            // base case
            if (left == 0 && right == 0) {
                if (isValid(s)) {
                    res.add(s);
                }
            }
            // 从start开始尝试删除每一位
            for (int i = start; i < s.length(); i++) {
                if (i != start && s.charAt(i) == s.charAt(i-1)) {
                    continue;
                }
                if (left + right > s.length() - i) {
                    return;
                }
                // 尝试去掉一个左括号
                if (left > 0 && s.charAt(i) == '(') {
                    backtrace(s.substring(0, i) + s.substring(i+1), i, left-1, right);
                }
                // 尝试去掉一个右括号
                if (right > 0 && s.charAt(i) == ')') {
                    backtrace(s.substring(0, i) + s.substring(i+1), i, left, right-1);
                }
            }
        }

        /**
         验证一个括号表达式是否有效
         */
        boolean isValid(String s) {
            int cnt = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') {
                    cnt ++;
                }
                else if(s.charAt(i) == ')') {
                    cnt --;
                    // 如果当前遍历到 ‘左括号’ 严格小于 ‘右括号’，否则表达式无效
                    if(cnt < 0) {
                        return false;
                    }
                }
            }
            return cnt == 0;
        }
    }

    // ["()())()" ] => ["(())()","()()()"]
}
