package leetcode.jzhoffer;

import leetcode.stack._150_逆波兰表达式;

import java.util.Stack;

/**
 * @author mayanwei
 * @date 2022-10-27.
 * @see _150_逆波兰表达式
 */
public class 剑指_Offer_II_036_后缀表达式{
    class Solution {
        public int evalRPN(String[] tokens) {
            Stack<Integer> stk = new Stack<>();
            for (String token : tokens) {
                // 不是数字
                if ("+-*/".contains(token)) {
                    int a = stk.pop(), b = stk.pop();
                    switch(token) {
                        case "+":
                            stk.push(a + b);
                            break;
                        case "*":
                            stk.push(a * b);
                            break;
                        case "-":
                            // 对于减法和出发顺序别反了
                            stk.push(b-a);
                            break;
                        case "/":
                            stk.push(b/a);
                            break;
                    }
                }
                // 是数字
                else {
                    stk.push(Integer.parseInt(token));
                }
            }
            // 最后栈中剩下一个数字，即结果
            return stk.pop();
        }
    }

    class Solution2 {
        int index;

        // 不使用栈，利用递归实现
        public int evalRPN(String[] tokens) {
            index = tokens.length - 1;
            return evalRPN1(tokens);
        }


        public int evalRPN1(String[] tokens) {
            String str = tokens[index];
            switch (str) {
                case "+": {
                    index--;
                    int right = evalRPN1(tokens);
                    index--;
                    int left = evalRPN1(tokens);
                    return right + left;
                }
                case "-": {
                    index--;
                    int right = evalRPN1(tokens);
                    index--;
                    int left = evalRPN1(tokens);
                    return left - right;
                }
                case "*": {
                    index--;
                    int right = evalRPN1(tokens);
                    index--;
                    int left = evalRPN1(tokens);
                    return right * left;
                }
                case "/": {
                    index--;
                    int right = evalRPN1(tokens);
                    index--;
                    int left = evalRPN1(tokens);
                    return left / right;
                }
                default: {
                    return Integer.parseInt(str);
                }
            }
        }

    }
}
