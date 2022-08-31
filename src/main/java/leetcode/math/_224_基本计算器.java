package leetcode.math;
//给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
//
// 注意:不允许使用任何将字符串作为数学表达式计算的内置函数，比如 eval() 。
//
//
//
// 示例 1：
//
//
//输入：s = "1 + 1"
//输出：2
//
//
// 示例 2：
//
//
//输入：s = " 2-1 + 2 "
//输出：3
//
//
// 示例 3：
//
//
//输入：s = "(1+(4+5+2)-3)+(6+8)"
//输出：23
//
//
//
//
// 提示：
//
//
// 1 <= s.length <= 3 * 10⁵
// s 由数字、'+'、'-'、'('、')'、和 ' ' 组成
// s 表示一个有效的表达式
// '+' 不能用作一元运算(例如， "+1" 和 "+(2 + 3)" 无效)
// '-' 可以用作一元运算(即 "-1" 和 "-(2 + 3)" 是有效的)
// 输入中不存在两个连续的操作符
// 每个数字和运行的计算将适合于一个有符号的 32位 整数
//
//
// Related Topics 栈 递归 数学 字符串 👍 806 👎 0

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * 字节
 * @see _227_基本计算器II
 * @see _772_基本计算器III
 * @author mayanwei
 * @date 2022-08-09.
 */
public class _224_基本计算器{
    class Solution{
        Integer index = 0;
        public int calculate(String s) {
            char[] chs = s.toCharArray();
            index = 0;
            return calculate(chs);
        }

        private int calculate(char[] chs) {
            Stack<Integer> stk = new Stack<Integer>();
            char sign = '+';
            int num = 0;
            while (index <= chs.length) {
                char ch = 0;
                if (index < chs.length) {
                    ch = chs[index];
                }
                if (Character.isDigit(ch)) {
                    num = num * 10 + (ch - '0');
                }
                if (ch == '(') {
                    index ++;
                    num = calculate(chs);
                }
               if ((!Character.isDigit(ch) && ch != ' ' )|| index == chs.length) {
                    switch (sign){
                        case '+':
                            stk.push(num);
                            break;
                        case '-':
                            stk.push(-num);
                            break;
                        case '*':
                            stk.push((int) stk.pop() * num);
                            break;
                        case '/':
                            stk.push(stk.pop()/num);
                            break;
                        default:
                            break;
                    }
                    num = 0;
                    sign = ch;
                }
                if (ch == ')') {
                    break;
                }
                index++;
            }
            return sum(stk);
        }

        private int sum(Stack<Integer> stk) {
            int sum = 0;
            while (!stk.isEmpty()) {
                sum += stk.pop();
            }
            return sum;
        }
    }

    @Test
    public void testSolution() {
        Solution solution = new Solution();
        String input = "(1+(4+5+2)-3)+(6+8)";
        //Assert.assertEquals(solution.calculate(input), 23);
        input = "2147483647";
        //Assert.assertEquals(solution.calculate(input), 2147483647);
        input = " 3/2 ";
        Assert.assertEquals(solution.calculate(input), 1);
    }
}
