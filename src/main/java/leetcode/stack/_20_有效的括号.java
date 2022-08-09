package leetcode.stack;

import java.util.Deque;
import java.util.LinkedList;
//给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
//
// 有效字符串需满足：
//
//
// 左括号必须用相同类型的右括号闭合。
// 左括号必须以正确的顺序闭合。
//
//
//
//
// 示例 1：
//
//
//输入：s = "()"
//输出：true
//
//
// 示例 2：
//
//
//输入：s = "()[]{}"
//输出：true
//
//
// 示例 3：
//
//
//输入：s = "(]"
//输出：false
//
//
// 示例 4：
//
//
//输入：s = "([)]"
//输出：false
//
//
// 示例 5：
//
//
//输入：s = "{[]}"
//输出：true
//
//
//
// 提示：
//
//
// 1 <= s.length <= 10⁴
// s 仅由括号 '()[]{}' 组成
//
//
// Related Topics 栈 字符串 👍 3445 👎 0
/**
 * @see _921_使括号有效的最少添加
 * @see _1541_平衡括号字符串的最少插入次数
 * @author mayanwei
 * @date 2022-08-09.
 */
public class _20_有效的括号{
    class Solution {
        public boolean isValid(String s) {
            Deque<Character> left = new LinkedList<Character>();
            char [] chs = s.toCharArray();
            for (char c:chs) {
                if (c == '(' || c == '[' || c == '{') {
                    left.push(c);
                }
                else { // 字符串 c 是右括号
                    if (!left.isEmpty() && leftOf(c) == left.peek()) {
                        left.pop();
                    }
                    else {
                        // 和最近的左括号不匹配
                        return false;
                    }
                }
            }
            // 是否所有的左括号都被匹配了
            return left.isEmpty();
        }
        char leftOf(char c) {
            if (c == '}') {
                return '{';
            }
            else if (c == ')') {
                return '(';
            }
            return '[';
        }
    }
}
