package leetcode.design;

import java.util.Stack;

//设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
//
//实现 MinStack 类:
//
//MinStack() 初始化堆栈对象。
//void push(int val) 将元素val推入堆栈。
//void pop() 删除堆栈顶部的元素。
//int top() 获取堆栈顶部的元素。
//int getMin() 获取堆栈中的最小元素。
//示例 1:
//
//输入：
//["MinStack","push","push","push","getMin","pop","top","getMin"]
//[[],[-2],[0],[-3],[],[],[],[]]
//
//输出：
//[null,null,null,null,-3,null,0,-2]
//
//解释：
//MinStack minStack = new MinStack();
//minStack.push(-2);
//minStack.push(0);
//minStack.push(-3);
//minStack.getMin();   --> 返回 -3.
//minStack.pop();
//minStack.top();      --> 返回 0.
//minStack.getMin();   --> 返回 -2.
//提示：
//
//-231 <= val <= 231 - 1
//pop、top 和 getMin 操作总是在 非空栈 上调用
//push, pop, top, and getMin最多被调用 3 * 104 次

public class _155_最小栈 {
    // 原始思路
    class MinStack1 {
        // 记录栈中的所有元素
        Stack<Integer> stk = new Stack<>();
        // 阶段性记录栈中的最小元素
        Stack<Integer> minStk = new Stack<>();

        public void push(int val) {
            stk.push(val);
            // 维护 minStk 栈顶为全栈最小元素
            if (minStk.isEmpty() || val <= minStk.peek()) {
                // 新插入的这个元素就是全栈最小的
                minStk.push(val);
            } else {
                // 插入的这个元素比较大
                minStk.push(minStk.peek());
            }
        }

        public void pop() {
            stk.pop();
            minStk.pop();
        }

        public int top() {
            return stk.peek();
        }

        public int getMin() {
            // minStk 栈顶为全栈最小元素
            return minStk.peek();
        }
    }

    // 优化版
    class MinStack {
        // 记录栈中的所有元素
        Stack<Integer> stk = new Stack<>();
        // 阶段性记录栈中的最小元素
        Stack<Integer> minStk = new Stack<>();

        public void push(int val) {
            stk.push(val);
            // 维护 minStk 栈顶为全栈最小元素
            if (minStk.isEmpty() || val <= minStk.peek()) {
                // 新插入的这个元素就是全栈最小的
                minStk.push(val);
            }
        }

        public void pop() {
            // 注意 Java 的语言特性，比较 Integer 相等要用 equals 方法
            if (stk.peek().equals(minStk.peek())) {
                // 弹出的元素是全栈最小的
                minStk.pop();
            }
            stk.pop();
        }

        public int top() {
            return stk.peek();
        }

        public int getMin() {
            // minStk 栈顶为全栈最小元素
            return minStk.peek();
        }
    }
}
