package leetcode.程序员面试金典;
//实现一个MyQueue类，该类用两个栈来实现一个队列。
//
//
//示例：
//
//MyQueue queue = new MyQueue();
//
//queue.push(1);
//queue.push(2);
//queue.peek();  // 返回 1
//queue.pop();   // 返回 1
//queue.empty(); // 返回 false
//
//说明：
//
//你只能使用标准的栈操作 -- 也就是只有 push to top, peek/pop from top, size 和 is empty 操作是合法的。
//你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
//假设所有操作都是有效的 （例如，一个空的队列不会调用 pop 或者 peek 操作）。
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/implement-queue-using-stacks-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Stack;

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _03_04_化栈为队{
    /**
     * 将一个栈当作输入栈，用于压入 push 传入的数据；另一个栈当作输出栈，用于 pop 和  peek 操作。
     * 每次 pop 或 peek 时，若输出栈为空则将输入栈的全部数据依次弹出并压入输出栈，
     * 这样输出栈从栈顶往栈底的顺序就是队列从队首往队尾的顺序。
     */
    class MyQueue{
        Stack<Integer> stackNewest, stackOldest;
        /**
         * Initialize your data structure here.
         */
        public MyQueue() {
            stackNewest = new Stack<>();
            stackOldest = new Stack<>();
        }

        /**
         * Push element x to the back of queue.
         */
        public void push(int x) {
            // 对 stackNewest压栈，其顶部元素时最新的
            stackNewest.push(x);
        }

        /**
         * Removes the element from in front of queue and returns that element.
         */
        public int pop() {
            shiftStack();
            return stackOldest.pop();
        }

        /**
         * Get the front element.
         */
        public int peek() {
            shiftStack();// 确保 stackOldest 有当前元素
            return stackOldest.peek();
        }

        /**
         * 将stackNewest的元素移动到stackOldest
         * 一般此操作可以让我们对 stackOldest做后续操作
         */
        private void shiftStack() {
            if (stackOldest.isEmpty()) {
                while (!stackNewest.isEmpty()) {
                    stackOldest.push(stackNewest.pop());
                }
            }
        }

        /**
         * Returns whether the queue is empty.
         */
        public boolean empty() {
            return stackNewest.isEmpty() && stackOldest.isEmpty();
        }
    }

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
}
