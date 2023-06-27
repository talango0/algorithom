package leetcode.程序员面试金典;
//请设计一个栈，除了常规栈支持的pop与push函数以外，还支持min函数，该函数返回栈元素中的最小值。
// 执行push、pop和min操作的时间复杂度必须为O(1)。
//
//
//示例：
//
//MinStack minStack = new MinStack();
//minStack.push(-2);
//minStack.push(0);
//minStack.push(-3);
//minStack.getMin();   --> 返回 -3.
//minStack.pop();
//minStack.top();      --> 返回 0.
//minStack.getMin();   --> 返回 -2.
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/min-stack-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Stack;

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _03_02_栈的最小值{
    class MinStack{

        /**
         * initialize your data structure here.
         */
        public MinStack() {

        }

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


    public class MinStack1{
        private Node head;

        public void push(int val) {
            if (head == null) {
                head = new Node(val, val);
            }
            else {
                // 头插法
                head = new Node(val, Math.min(val, head.min), head);
            }
        }

        public void pop() {
            head = head.next;
        }

        public int top() {
            return head.val;
        }

        public int getMin() {
            return head.min;
        }

        private class Node{
            int val;
            int min;
            Node next;

            public Node(int val, int min) {
                this.val = val;
                this.min = min;
            }

            public Node(int val, int min, Node next) {
                this.val = val;
                this.min = min;
                this.next = next;
            }
        }
    }
/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
}
