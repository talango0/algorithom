package leetcode.程序员面试金典;
//堆盘子。设想有一堆盘子，堆太高可能会倒下来。因此，在现实生活中，盘子堆到一定高度时，
// 我们就会另外堆一堆盘子。请实现数据结构SetOfStacks，模拟这种行为。SetOfStacks应该由多个栈组成，
// 并且在前一个栈填满时新建一个栈。此外，SetOfStacks.push()和SetOfStacks.pop()应该与普通栈的操作方法相同
// （也就是说，pop()返回的值，应该跟只有一个栈时的情况一样）。 进阶：实现一个popAt(int index)方法，根据指定的子栈，执行pop操作。
//
//当某个栈为空时，应当删除该栈。当栈中没有元素或不存在该栈时，pop，popAt应返回 -1.
//
//示例1:
//
// 输入：
//["StackOfPlates", "push", "push", "popAt", "pop", "pop"]
//[[1], [1], [2], [1], [], []]
// 输出：
//[null, null, null, 2, 1, -1]
//示例2:
//
// 输入：
//["StackOfPlates", "push", "push", "push", "popAt", "popAt", "popAt"]
//[[2], [1], [2], [3], [0], [0], [0]]
// 输出：
//[null, null, null, null, 2, 1, 3]
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/stack-of-plates-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.*;

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _03_03_堆盘子{
    class StackOfPlates{

        ArrayList<Stack> stacks = new ArrayList<Stack>();
        int capacity;


        public StackOfPlates(int cap) {
            capacity = cap;
        }

        public Stack getLastStack() {
            if (stacks.size() == 0) {
                return null;
            }
            return stacks.get(stacks.size() - 1);
        }

        public void push(int val) {
            Stack last = getLastStack();
            if (last != null && !last.isFull()) {
                last.push(val);
            }
            else { // 必须创建新栈
                Stack stack = new Stack(capacity);
                stack.push(val);
                stacks.add(stack);
            }
        }

        public int pop() {
            Stack last = getLastStack();
            if (last == null) {
                return -1;
            }
            int v = last.pop();
            if (last.size == 0) {
                stacks.remove(stacks.size() - 1);
            }
            return v;
        }

        /**
         * 实现起来比较棘手，我们可以设想一个 “推入”动作。从栈1弹出元素时，我们需要移除栈2
         * 的栈底元素，并将其推入到栈1中，随后，将栈3的栈底元素推入栈2，将栈4的栈底元素
         * 推入到栈3，依此类推。
         * <p>
         * 你可能会指出，何必执行推入操作，有些栈不填满也挺好的。而且，还会改善时间复杂度（元素
         * 很多事尤为明显），但是，若之后有人假定所有栈（最后一个栈除外）都是填满的，就可能会让我们陷入
         * 束手无策的境地。这个问题没有标准答案，应该讨论各种方式的又略。
         *
         * @param index
         * @return
         */
        public int popAt(int index) {
            return leftShift(index, true);
        }

        public int leftShift(int index, boolean removeTop) {
            Stack stack = stacks.get(index);
            int removed_item;
            if (removeTop) {
                removed_item = stack.pop();
            }
            else {
                removed_item = stack.removeBottom();
            }
            if (stack.isEmpty()) {
                stacks.remove(index);
            }
            else if (stack.size > index + 1) {
                int v = leftShift(index + 1, false);
                stack.push(v);
            }
            return removed_item;
        }

        class Stack{
            class Node{
                public Node above, below;
                int val;

                public Node(int v) {
                    val = v;
                }
            }

            private int capacity;
            public Node top, bottom;
            public int size = 0;

            public Stack(int capacity) {
                this.capacity = capacity;
            }

            public boolean isFull() {
                return capacity == size;
            }

            private void join(Node above, Node below) {
                if (below != null) {
                    below.above = above;
                }
                if (above != null) {
                    above.below = below;
                }
            }

            public boolean push(int v) {
                if (size >= capacity) {
                    return false;
                }
                size++;
                Node n = new Node(v);
                if (size == 1) {
                    bottom = n;
                }
                join(n, top);
                top = n;
                return true;
            }

            public int pop() {
                Node t = top;
                top = top.below;
                size--;
                return t.val;

            }

            public boolean isEmpty() {
                return size == 0;
            }

            public int removeBottom() {
                Node b = bottom;
                bottom = bottom.above;
                if (bottom != null) {
                    bottom.below = null;
                }
                size--;
                return b.val;
            }
        }
    }

    /**
     * Your StackOfPlates object will be instantiated and called as such:
     * StackOfPlates obj = new StackOfPlates(cap);
     * obj.push(val);
     * int param_2 = obj.pop();
     * int param_3 = obj.popAt(index);
     */

    class Solution{
        class StackOfPlates{
            int capacity;// 每堆盘子的容量
            List<Deque<Integer>> allPlates; // 盘子堆

            // 初始化容量及盘子堆
            public StackOfPlates(int cap) {
                this.capacity = cap;
                allPlates = new LinkedList<>();
            }

            public void push(int val) {
                if (capacity <= 0) {// 容量为0，不需要继续放盘子
                    return;
                }
                // 没有盘子堆 或者 最后一堆的数量等于容量时，需要新开一堆盘子
                if (allPlates.isEmpty() || allPlates.get(allPlates.size() - 1).size() == capacity) {
                    allPlates.add(new ArrayDeque<>());
                }
                allPlates.get(allPlates.size() - 1).offerFirst(val);// 放最后
            }

            public int pop() {
                // 没有盘子了 返回-1
                if (allPlates.size() == 0) {
                    return -1;
                }
                // 从最后一堆盘子顶上拿一个盘子
                int result = allPlates.get(allPlates.size() - 1).pollFirst();
                // 拿完之后，如果空了，就把这堆删掉
                if (allPlates.get(allPlates.size() - 1).size() == 0) {
                    allPlates.remove(allPlates.size() - 1);
                }
                return result;
            }

            public int popAt(int index) {
                // 如果索引不合法，并且没有盘子了，就返回-1
                if (index < 0 || index >= allPlates.size() || allPlates.isEmpty()) {
                    return -1;
                }
                int result = allPlates.get(index).pollFirst();
                if (allPlates.get(index).size() == 0) {
                    allPlates.remove(index);
                }
                return result;
            }
        }
    }


    class Solution2{
        class StackOfPlates{

            private int cap;
            private ArrayList<ArrayList<Integer>> stack;

            public StackOfPlates(int cap) {
                this.stack = new ArrayList<>();
                this.cap = cap;
            }

            public void push(int val) {
                if (this.cap == 0)
                    return;

                if (this.stack.size() == 0 || this.stack.get(this.stack.size() - 1).size() == this.cap) {
                    ArrayList<Integer> temp = new ArrayList();
                    temp.add(val);
                    this.stack.add(temp);
                }
                else {
                    this.stack.get(this.stack.size() - 1).add(val);
                }

            }

            public int pop() {
                if (this.stack.size() == 0) {
                    return -1;
                }
                ArrayList<Integer> temp = this.stack.get(this.stack.size() - 1);
                if (temp.size() == 1) {
                    int ret = temp.get(0);
                    this.stack.remove(this.stack.size() - 1);
                    return ret;
                }
                else {
                    int ret = temp.get(temp.size() - 1);
                    temp.remove(temp.size() - 1);
                    return ret;
                }
            }

            public int popAt(int index) {
                if (index < 0 || index >= this.stack.size()) return -1;
                ArrayList<Integer> temp = this.stack.get(index);
                if (temp.size() == 1) {
                    int ret = temp.get(0);
                    this.stack.remove(index);
                    return ret;
                }
                else {
                    int ret = temp.get(temp.size() - 1);
                    temp.remove(temp.size() - 1);
                    return ret;
                }
            }
        }
    }
}
