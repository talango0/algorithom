package leetcode.math;
//给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
//
// 整数除法仅保留整数部分。
//
// 你可以假设给定的表达式总是有效的。所有中间结果将在 [-2³¹, 2³¹ - 1] 的范围内。
//
// 注意：不允许使用任何将字符串作为数学表达式计算的内置函数，比如 eval() 。
//
//
//
// 示例 1：
//
//
//输入：s = "3+2*2"
//输出：7
//
//
// 示例 2：
//
//
//输入：s = " 3/2 "
//输出：1
//
//
// 示例 3：
//
//
//输入：s = " 3+5 / 2 "
//输出：5
//
//
//
//
// 提示：
//
//
// 1 <= s.length <= 3 * 10⁵
// s 由整数和算符 ('+', '-', '*', '/') 组成，中间由一些空格隔开
// s 表示一个 有效表达式
// 表达式中的所有整数都是非负整数，且在范围 [0, 2³¹ - 1] 内
// 题目数据保证答案是一个 32-bit 整数
//
//
// Related Topics 栈 数学 字符串 👍 598 👎 0

import java.util.Stack;

/**
 * @author mayanwei
 * @date 2022-08-09.
 */
public class _227_基本计算器II{
    class Solution {
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
                if ((!Character.isDigit(ch) && ch != ' ' ) || index == chs.length) {
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
                            stk.push(stk.pop() / num);
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
}
