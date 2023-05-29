package leetcode.程序员面试金典;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2023-05-29.
 */
public class _08_09_括号{
    // 思考：合法的括号，左括号个数 == 右括号个数
    class Solution{
        public List<String> generateParenthesis(int n) {
            backtrace(n, n, new StringBuilder());
            return res;
        }

        List<String> res = new ArrayList<>();

        private void backtrace(int left, int right, StringBuilder sb) {
            // 左括号多余右括号说明不合法。
            if (right < left) {
                return;
            }
            // 数量小于0肯定不合法
            if (left < 0 || right < 0) {
                return;
            }
            // 当所有括号都全部用完，得到一个合法的括号组合。
            if (left == 0 && right == 0) {
                res.add(sb.toString());
                return;
            }

            // 尝试放左括号
            sb.append('(');
            backtrace(left - 1, right, sb);
            // 撤销选择
            sb.deleteCharAt(sb.length() - 1);


            // 尝试放右括号
            sb.append(')');
            backtrace(left, right - 1, sb);
            sb.deleteCharAt(sb.length() - 1);

        }
    }
}
