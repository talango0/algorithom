package leetcode.程序员面试金典;
//栈排序。 编写程序，对栈进行排序使最小元素位于栈顶。最多只能使用一个其他的临时栈存放数据，
// 但不得将元素复制到别的数据结构（如数组）中。该栈支持如下操作：push、pop、peek 和 isEmpty。
// 当栈为空时，peek返回 -1。
//
//示例1:
//
// 输入：
//["SortedStack", "push", "push", "peek", "pop", "peek"]
//[[], [1], [2], [], [], []]
// 输出：
//[null,null,null,1,null,2]
//示例2:
//
// 输入： 
//["SortedStack", "pop", "pop", "push", "pop", "isEmpty"]
//[[], [], [], [1], [], []]
// 输出：
//[null,null,null,null,null,true]
//说明:
//
//栈中的元素数目在[0, 5000]范围内。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/sort-of-stacks-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Stack;

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _03_05_栈排序{
    class SortedStack{

        Stack<Integer> stk = new Stack<>();

        public SortedStack() {

        }

        public void push(int val) {
            stk.push(val);
            sort(stk);

        }

        /**
         * 时间复杂度 O(N^2) 空间复杂度 O(N)
         *
         * @param stk
         */
        private void sort(Stack<Integer> stk) {
            Stack<Integer> r = new Stack<>();
            while (!stk.isEmpty()) {
                // 将 stk 中的每个元素有序地插入到 r 中
                int tmp = stk.pop();
                while (!r.isEmpty() && r.peek() > tmp) {
                    stk.push(r.pop());
                }
                r.push(tmp);
            }
            // 将 r 中元素复制回 s
            while (!r.isEmpty()) {
                stk.push(r.pop());
            }
        }

        public void pop() {
            if (isEmpty()) {
                return;
            }
            stk.pop();
        }

        public int peek() {
            if (isEmpty()) {
                return -1;
            }
            return stk.peek();
        }

        public boolean isEmpty() {
            return stk.isEmpty();
        }
    }

    /**
     * 快速排序
     */
    class SortedStack1{
        int[] heap;
        int index;

        public SortedStack1() {
            heap = new int[5000];
            index = 0;
        }

        //heapInsert
        public void push(int val) {
            int i = index;
            heap[index++] = val;
            while (heap[i] < heap[(i - 1) / 2]) {
                swap(i, (i - 1) >> 1);
                i = (i - 1) >> 1;
            }
        }

        //heapify
        public void pop() {
            if (index != 0) {
                swap(0, --index);
                int i = 0;
                while ((i << 1) + 1 < index) {
                    if ((i << 1) + 2 < index) {
                        int min = Math.min(heap[(i << 1) + 1], heap[(i << 1) + 2]);
                        int x = heap[(i << 1) + 1] == min ? (i << 1) + 1 :(i << 1) + 2;
                        if (min < heap[i]) {
                            swap(i, x);
                            i = x;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        if (heap[i] > heap[(i << 1) + 1]) {
                            swap(i, (i << 1) + 1);
                            i = (i << 1) + 1;
                        }
                        else {
                            break;
                        }
                    }
                }
            }
        }

        public int peek() {
            return index == 0 ? -1 :heap[0];
        }

        public boolean isEmpty() {
            return index == 0;
        }

        public void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

    }

/**
 * Your SortedStack object will be instantiated and called as such:
 * SortedStack obj = new SortedStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.isEmpty();
 */
}

