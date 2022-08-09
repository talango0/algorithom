package leetcode.math;
//Implement a basic calculator to evaluate a simple expression string.
//
//The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
//
//The expression string contains only non-negative integers, +, -, *, / operators , open ( and closing parentheses ) and empty spaces . The integer division should truncate toward zero.
//
//You may assume that the given expression is always valid. All intermediate results will be in the range of [-2147483648, 2147483647].
//
//Some examples:
//
//"1 + 1" = 2
//" 6-4 / 2 " = 4
//"2*(5+5*2)/3+(6/2+8)" = 21
//"(2+6* 3+5- (3*14/7+2)*5)+3"=-12
//
//
//Note: Do not use the eval built-in library function.

import java.util.Stack;

/**
 * @author mayanwei
 * @date 2022-08-09.
 */
public class _772_基本计算器III{
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
