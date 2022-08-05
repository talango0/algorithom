package leetcode.backtracing;

import java.util.ArrayList;
import java.util.List;
//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
//
//
//
// 示例 1：
//
//
//输入：n = 3
//输出：["((()))","(()())","(())()","()(())","()()()"]
//
//
// 示例 2：
//
//
//输入：n = 1
//输出：["()"]
//
//
//
//
// 提示：
//
//
// 1 <= n <= 8
//
//
// Related Topics 字符串 动态规划 回溯 👍 2792 👎 0
/**
 * @author mayanwei
 * @date 2022-08-05.
 */
public class _22_括号生成{
    class Solution {
        // 一个【合法】括号组合的左括号数量一定等于右括号数量
        // 对于一个合法的括号字符串组合 p，必然对于 0<=i<len(p) 都有：子串 p[0..i] 中左括号的数量都大于或等于右括号的数量
        public List<String> generateParenthesis(int n) {
            // 可用的左括号和右括号数量为n
            backtrace(n, n, new StringBuilder());
            return res;
        }

        List<String> res = new ArrayList<>();

        // 可用的做括号数量为 left 个 可用的右括号的数量为 right 个
        void backtrace(int left, int right, StringBuilder sb) {
            //左括号剩余的多说明不合法
            if ( right < left ) {
                return;
            }
            //数量小于0肯定是不合法的
            if (left < 0 || right < 0) {
                return;
            }
            // 当所有括号都全部用完时，得到一个合法的括号组合
            if (left == 0 && right == 0) {
                res.add(sb.toString());
                return;
            }

            // 尝试放一个左括号
            // 选择
            sb.append('(');
            backtrace(left-1, right, sb);
            // 撤销选择
            sb.deleteCharAt(sb.length()-1);

            // 尝试放一个右括号
            // 选择
            sb.append(')');
            backtrace(left, right-1, sb);
            // 撤销选择
            sb.deleteCharAt(sb.length()-1);

        }
    }
}
