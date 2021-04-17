package leetcode.jzhoffer;

import java.util.Stack;

/**
 * 题目描述
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。
 */
public class JZ20包含min函数的栈 {

    public class Solution {

        Stack<Integer> data = new Stack<Integer>(); // 数据栈
        Stack<Integer> mins = new Stack<Integer>(); // 辅助栈，存放最小值
        Integer tmpMin = null;

        // 数据进栈，最小值进栈
        public void push(int node) {
            if(tmpMin == null) {
                tmpMin = node;
                data.push(node);
                mins.push(node);
            } else {
                if(node <= tmpMin) {
                    tmpMin = node;
                    mins.push(node);
                }
                data.push(node);
            }
        }

        // 弹出栈顶元素
        public void pop() {
            int num1 = data.pop();
            int num2 = mins.pop();
            if(num1 != num2) {
                mins.push(num2);
            }
        }

        // 查看栈顶元素
        public int top() {
            return data.peek();
        }

        // 查看当前最小元素
        public int min() {
            int num = mins.pop();
            mins.push(num);
            return num;
        }
    }


    static class Solution2 {
        private Stack<Integer> data = new Stack<>();
        private Stack<Integer> min = new Stack<>();

        public void push(int node) {
            data.push(node);
            if(min.empty()){
                min.push(node);
            }else{
                min.push(node <= min.peek()? node : min.peek());
            }
        }

        public void pop() {
            data.pop();
            min.pop();
        }

        public int top() {
            return data.peek();
        }

        public int min() {
            return min.peek();
        }
    }
}
