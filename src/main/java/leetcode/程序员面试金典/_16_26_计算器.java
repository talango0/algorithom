package leetcode.程序员面试金典;
//给定一个包含正整数、加(+)、减(-)、乘(*)、除(/)的算数表达式(括号除外)，计算其结果。
//
//表达式仅包含非负整数，+， - ，*，/ 四种运算符和空格。 整数除法仅保留整数部分。
//
//示例1:
//
//输入: "3+2*2"
//输出: 7
//示例 2:
//
//输入: " 3/2 "
//输出: 1
//示例 3:
//
//输入: " 3+5 / 2 "
//输出: 5
//说明：
//
//你可以假设所给定的表达式都是有效的。
//请不要使用内置的库函数 eval。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/calculator-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Stack;

/**
 * @author mayanwei
 * @date 2023-06-16.
 */
public class _16_26_计算器{
    /**
     * 两种思路：
     * 1. 利用迭代思路
     * 定义两个变量 process 和 result
     * 3. 利用双栈思路
     * 符号栈、操作数栈
     */

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
                    index++;
                    num = calculate(chs);
                }
                if ((!Character.isDigit(ch) && ch != ' ') || index == chs.length) {
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

    class Solution2{
        public int calculate(String s) {
            char[] cs = s.toCharArray();
            int[] nums = new int[cs.length];
            char[] ops = new char[cs.length];
            int n = 0;
            for (int i = 0; i < cs.length; i++) {
                if (cs[i] == ' '){

                }
                else if (cs[i] == '+' || cs[i] == '-') {
                    while (n > 1) {
                        calc(nums, ops, n);
                        n--;
                    }
                    ops[0] = cs[i];
                }
                else if (cs[i] == '*' || cs[i] == '/') {
                    while (n > 1 && (ops[n - 2] == '*' || ops[n - 2] == '/')) {
                        calc(nums, ops, n);
                        n--;
                    }
                    ops[n - 1] = cs[i];
                }
                else {
                    int j = 0;
                    for (; i < cs.length && cs[i] >= '0' && cs[i] <= '9'; i++) {
                        j = j * 10 + cs[i] - '0';
                    }
                    nums[n++] = j;
                    i--;
                }
            }
            while (n > 1) {
                calc(nums, ops, n--);
            }
            return nums[0];
        }

        private void calc(int[] nums, char[] ops, int n) {
            char c = ops[n - 2];
            if (c == '+') {
                nums[n - 2] += nums[n - 1];
            }
            else if (c == '-') {
                nums[n - 2] -= nums[n - 1];
            }
            else if (c == '*') {
                nums[n - 2] *= nums[n - 1];
            }
            else {
                nums[n - 2] /= nums[n - 1];
            }
        }
    }




}
