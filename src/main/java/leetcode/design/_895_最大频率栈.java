package leetcode.design;
//设计一个类似堆栈的数据结构，将元素推入堆栈，并从堆栈中弹出出现频率最高的元素。
//
//实现 FreqStack 类:
//
//FreqStack() 构造一个空的堆栈。
//void push(int val) 将一个整数 val 压入栈顶。
//int pop() 删除并返回堆栈中出现频率最高的元素。
//如果出现频率最高的元素不只一个，则移除并返回最接近栈顶的元素。
//示例 1：
//
//输入：
//["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
//[[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
//输出：[null,null,null,null,null,null,null,5,7,5,4]
//解释：
//FreqStack = new FreqStack();
//freqStack.push (5);//堆栈为 [5]
//freqStack.push (7);//堆栈是 [5,7]
//freqStack.push (5);//堆栈是 [5,7,5]
//freqStack.push (7);//堆栈是 [5,7,5,7]
//freqStack.push (4);//堆栈是 [5,7,5,7,4]
//freqStack.push (5);//堆栈是 [5,7,5,7,4,5]
//freqStack.pop ();//返回 5 ，因为 5 出现频率最高。堆栈变成 [5,7,5,7,4]。
//freqStack.pop ();//返回 7 ，因为 5 和 7 出现频率最高，但7最接近顶部。堆栈变成 [5,7,5,4]。
//freqStack.pop ();//返回 5 ，因为 5 出现频率最高。堆栈变成 [5,7,4]。
//freqStack.pop ();//返回 4 ，因为 4, 5 和 7 出现频率最高，但 4 是最接近顶部的。堆栈变成 [5,7]。
//提示：
//
//0 <= val <= 109
//push 和 pop 的操作数不大于 2 * 104。
//输入保证在调用 pop 之前堆栈中至少有一个元素。
//Related Topics
//
//👍 247, 👎 0

import java.util.HashMap;
import java.util.Stack;

/**
 * 字节
 * @author mayanwei
 * @date 2022-08-17.
 */
public class _895_最大频率栈{
    class FreqStack{
        // 记录 FreqStack 中元素的最大频率
        int maxFreq = 0;
        // 记录 FreqStack 中每个 val 对应的出现频率，VF表
        HashMap<Integer, Integer> valToFreq = new HashMap<>();
        // 记录频率freq对应的 val 列表，FV表
        HashMap<Integer, Stack<Integer>> freqToVals = new HashMap<>();

        public FreqStack() {

        }

        public void push(int val) {
            // 修改 VF 表： val 对应的 freq 加一
            int freq = valToFreq.getOrDefault(val, 0) + 1;
            valToFreq.put(val, freq);
            freqToVals.putIfAbsent(freq, new Stack<>());
            freqToVals.get(freq).push(val);
            // 更新maxFreq
            maxFreq = Math.max(maxFreq, freq);
        }

        public int pop() {
            // 修改 FV 表：pop 出一个 maxFreq 对应的元素
            Stack<Integer> vals = freqToVals.get(maxFreq);
            Integer v = vals.pop();
            // 修改 VF 表：V 对应的 freq 减1
            valToFreq.put(v, valToFreq.get(v) - 1);
            // 更新 maxFreq
            if (vals.isEmpty()) {
                maxFreq--;
            }
            return v;
        }
    }

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */
}
