package leetcode.程序员面试金典;
//三合一。描述如何只用一个数组来实现三个栈。
//
//你应该实现push(stackNum, value)、pop(stackNum)、isEmpty(stackNum)、peek(stackNum)方法。
// stackNum表示栈下标，value表示压入的值。
//
//构造函数会传入一个stackSize参数，代表每个栈的大小。
//
//示例1:
//
// 输入：
//["TripleInOne", "push", "push", "pop", "pop", "pop", "isEmpty"]
//[[1], [0, 1], [0, 2], [0], [0], [0], [0]]
// 输出：
//[null, null, null, 1, -1, -1, true]
//说明：当栈为空时`pop, peek`返回-1，当栈满时`push`不压入元素。
//示例2:
//
// 输入：
//["TripleInOne", "push", "push", "push", "pop", "pop", "pop", "peek"]
//[[2], [0, 1], [0, 2], [0, 3], [0], [0], [0], [0]]
// 输出：
//[null, null, null, null, 2, 1, -1, -1]
//
//
//提示：
//
//0 <= stackNum <= 2
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/three-in-one-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _03_01_三合一{
    class TripleInOne0{
        int N = 3;
        // 3 * n 的数组，每一行代表一个栈
        int[][] data;
        // 记录每个栈「待插入」位置
        int[] locations;

        public TripleInOne0(int stackSize) {
            data = new int[N][stackSize];
            locations = new int[N];
        }

        public void push(int stackNum, int value) {
            int[] stk = data[stackNum];
            int loc = locations[stackNum];
            if (loc < stk.length) {
                stk[loc] = value;
                locations[stackNum]++;
            }
        }

        public int pop(int stackNum) {
            int[] stk = data[stackNum];
            int loc = locations[stackNum];
            if (loc > 0) {
                int val = stk[loc - 1];
                locations[stackNum]--;
                return val;
            }
            else {
                return -1;
            }
        }

        public int peek(int stackNum) {
            int[] stk = data[stackNum];
            int loc = locations[stackNum];
            if (loc > 0) {
                return stk[loc - 1];
            }
            else {
                return -1;
            }
        }

        public boolean isEmpty(int stackNum) {
            return locations[stackNum] == 0;
        }
    }


    /**
     * 和许多问题一样，这个问题的解法基本上取决于你要对栈支持到什么程度。若每个栈分配的空间大小固定，就能
     * 满足需要，那么照做便是。不过，这么做的话，有可能其中一个栈的空间不够用了，其他的栈确实空的。
     * 另一种做法是弹性处理栈的空间分配，但这么一来，问题的复杂度又会大大增加。
     * <p>
     * 方法1.固定分割
     * 栈1使用 [0, n/3)
     * 栈2使用 [n/3, 2n/3)
     * 栈3使用 [2n/3, n)
     */
    class TripleInOne{
        private int numberOfStacks = 3;
        private int stackCapacity;
        private int[] values;
        private int[] sizes;

        public TripleInOne(int stackSize) {
            stackCapacity = stackSize;
            values = new int[stackSize * numberOfStacks];
            sizes = new int[numberOfStacks];

        }

        public void push(int stackNum, int value) {
            // 检查栈空间容纳下一个元素
            if (isFull(stackNum)) {
                return;
            }
            sizes[stackNum]++;
            values[indexOf(stackNum)] = value;
        }

        private boolean isFull(int stackNum) {
            return sizes[stackNum] == stackCapacity;
        }

        public int pop(int stackNum) {
            if (isEmpty(stackNum)) {
                return -1;
            }
            int topIndex = indexOf(stackNum);
            int value = values[topIndex];
            values[topIndex] = 0;//清零
            sizes[stackNum]--;
            return value;
        }

        private int indexOf(int stackNum) {
            int offset = stackNum * stackCapacity;
            int size = sizes[stackNum];
            return offset + size - 1;
        }

        public int peek(int stackNum) {
            if (isEmpty(stackNum)) {
                return -1;
            }
            return values[indexOf(stackNum)];
        }

        public boolean isEmpty(int stackNum) {
            return sizes[stackNum] == 0;
        }
    }


    /**
     * 弹性分割
     * 允许栈快的大小灵活可变。当一个栈的元素个数超出其初始容量时，就将这个栈扩容至许可的容量。必要时
     * 还要迁移元素。
     * 此外，我们可以将数组设计成环状的，最后一个栈可能从数组末尾处开始，环绕到数组起始处。
     */
    class TripleInOne2{
        private StackInfo[] info;
        private int[] values;

        private class StackInfo{
            public int start, size, capacity;

            public StackInfo(int start, int capacity) {
                this.start = start;
                this.capacity = capacity;
            }

            /**
             * 检查索引是否在界限内。栈可以从数组头部重新开始
             */
            public boolean isWithinStackCapacity(int index) {
                if (index < 0 || index >= values.length) {
                    return false;
                }
                // 如果首尾相接，则调整索引
                int contiguousIndex = index < start ? index + values.length :index;
                int end = start + capacity;
                return start <= contiguousIndex && contiguousIndex < end;
            }

            public int lastCapacityIndex() {
                return adjustIndex(start + capacity - 1);
            }

            public int lastElementIndex() {
                return adjustIndex(start + size - 1);
            }

            public boolean isFull() {
                return size == capacity;
            }

            public boolean isEmpty() {
                return size == 0;
            }
        }

        private int adjustIndex(int i) {
            return 0;
        }

        public TripleInOne2(int stackSize) {
            // 对所有的栈创建元数据
            info = new StackInfo[3];
            for (int i = 0; i < 3; i++) {
                info[i] = new StackInfo(stackSize * i, stackSize);
            }
            values = new int[3 * stackSize];

        }

        /**
         * 将 value 入栈，如果必要则对栈进行移动、扩展。若所有栈均已满，则抛出异常
         *
         * @param stackNum
         * @param value
         */
        public void push(int stackNum, int value) {
            if (allStackAreFull()) {
                return;
            }
            StackInfo stack = info[stackNum];
            if (stack.isFull()) {
                expand(stackNum);
            }
            // 找到数组中顶部元素的索引，对栈的指针加1
            stack.size++;
            values[stack.lastElementIndex()] = value;
        }

        /**
         * 从栈顶溢出元素
         *
         * @param stackNum
         * @return
         */
        public int pop(int stackNum) {
            StackInfo stack = info[stackNum];
            if (stack.isEmpty()) {
                return -1;
            }
            // 移除最后元素
            int value = values[stack.lastElementIndex()];
            values[stack.lastElementIndex()] = 0; // 清空元素
            stack.size--; // 缩减大小
            return value;
        }


        /**
         * 获取栈顶元素
         *
         * @param stackNum
         * @return
         */
        public int peek(int stackNum) {
            StackInfo stack = info[stackNum];
            return values[stack.lastElementIndex()];
        }

        public boolean isEmpty(int stackNum) {
            StackInfo sk = info[stackNum];
            return sk.isEmpty();
        }


        private void expand(int stackNum) {
            shift((stackNum + 1) % info.length);
            info[stackNum].capacity++;
        }

        /**
         * 将栈中元素移动一位。如果仍有空间，那么我们会最终将栈的尺寸缩减一个元素。
         * 如果没有空间，我们则还需要移动下一个栈
         *
         * @param stackNum
         */
        private void shift(int stackNum) {
            StackInfo stack = info[stackNum];
            // 如果当前栈已满，那么我们需要移动下一个栈，此战则可以声明为被时放的索引
            if (stack.size >= stack.capacity) {
                int nextStack = (stackNum + 1) % info.length;
                shift(nextStack);
                stack.capacity++;// 声明下一个栈释放的索引
            }

            // 将所有元素移动一位
            int index = stack.lastCapacityIndex();
            while (stack.isWithinStackCapacity(index)) {
                values[index] = values[previouIndex(index)];
                index = previouIndex(index);
            }

            // 调整栈的数据
            values[stack.start] = 0; //清空
            stack.start = nextIndex(stack.start); // 移动起始元素
            stack.capacity--; // 缩减尺寸

        }

        /**
         * 获取此索引的后一个索引，调整期值是的首尾相接
         */
        private int nextIndex(int index) {
            return ajdustIndex(index + 1);
        }

        private int ajdustIndex(int index) {
            // java 求余运算会返回负数，例如(-11%5)会返回-1，而不是4
            int max = values.length;
            return ((index % max) + max) % max;
        }

        /**
         * 获取􏰄索引的前一个索引，调整其值使得首尾相接
         */
        private int previouIndex(int index) {
            return adjustIndex(index-1);
        }

        private boolean allStackAreFull() {
            return numberOfElements() == values.length;
        }

        private int numberOfElements() {
            int size = 0;
            for (StackInfo sd : info) {
                size += sd.size;
            }
            return size;
        }


    }


}
