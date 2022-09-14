package leetcode.design;
//我们把无限数量 ∞ 的栈排成一行，按从左到右的次序从 0 开始编号。每个栈的的最大容量 capacity 都相同。
//
// 实现一个叫「餐盘」的类 DinnerPlates：
//
//
// DinnerPlates(int capacity) - 给出栈的最大容量 capacity。
// void push(int val) - 将给出的正整数 val 推入 从左往右第一个 没有满的栈。
// int pop() - 返回 从右往左第一个 非空栈顶部的值，并将其从栈中删除；如果所有的栈都是空的，请返回 -1。
// int popAtStack(int index) - 返回编号 index 的栈顶部的值，并将其从栈中删除；如果编号 index 的栈是空的，请返回 -
//1。
//
//
//
//
// 示例：
//
// 输入：
//["DinnerPlates","push","push","push","push","push","popAtStack","push","push",
//"popAtStack","popAtStack","pop","pop","pop","pop","pop"]
//[[2],[1],[2],[3],[4],[5],[0],[20],[21],[0],[2],[],[],[],[],[]]
//输出：
//[null,null,null,null,null,null,2,null,null,20,21,5,4,3,1,-1]
//
//解释：
//DinnerPlates D = DinnerPlates(2);  // 初始化，栈最大容量 capacity = 2
//D.push(1);
//D.push(2);
//D.push(3);
//D.push(4);
//D.push(5);         // 栈的现状为：    2  4
//                                    1  3  5
//                                    ﹈ ﹈ ﹈
//D.popAtStack(0);   // 返回 2。栈的现状为：      4
//                                          1  3  5
//                                          ﹈ ﹈ ﹈
//D.push(20);        // 栈的现状为：  20  4
//                                   1  3  5
//                                   ﹈ ﹈ ﹈
//D.push(21);        // 栈的现状为：  20  4 21
//                                   1  3  5
//                                   ﹈ ﹈ ﹈
//D.popAtStack(0);   // 返回 20。栈的现状为：       4 21
//                                            1  3  5
//                                            ﹈ ﹈ ﹈
//D.popAtStack(2);   // 返回 21。栈的现状为：       4
//                                            1  3  5
//                                            ﹈ ﹈ ﹈
//D.pop()            // 返回 5。栈的现状为：        4
//                                            1  3
//                                            ﹈ ﹈
//D.pop()            // 返回 4。栈的现状为：    1  3
//                                           ﹈ ﹈
//D.pop()            // 返回 3。栈的现状为：    1
//                                           ﹈
//D.pop()            // 返回 1。现在没有栈。
//D.pop()            // 返回 -1。仍然没有栈。
//
//
//
//
// 提示：
//
//
// 1 <= capacity <= 20000
// 1 <= val <= 20000
// 0 <= index <= 100000
// 最多会对 push，pop，和 popAtStack 进行 200000 次调用。
//
//
// Related Topics 栈 设计 哈希表 堆（优先队列） 👍 38 👎 0


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-09-14.
 */
public class _1172_餐盘栈{
    class DinnerPlates {
        //因为最大index为100000，所以100000之后的数都可以只用一个栈存储
        int N = 100010;
        //对每个栈计数
        int[] cnt = new int[N];
        //用数组和size索引维护每个栈
        Deque<Integer>[] stacks = new ArrayDeque[N];
        //用堆保存除了索引为size的栈以外每个不满栈的索引
        PriorityQueue<Integer> q = new PriorityQueue<>();
        int max, size;
        public DinnerPlates(int capacity) {
            this.max = capacity;
            this.size = 0;
        }

        public void push(int val) {
            if(!q.isEmpty()){
                //循环如果当前size的计数为空，则-1，直到所有栈为空，或者出现第一个不为空的栈
                while(cnt[size] == 0 && size > 0) size--;
                if(q.peek() >= size){
                    //如果堆中的索引大于等于size，则将索引移出
                    while(!q.isEmpty() && q.peek() > size){
                        q.poll();
                    }
                    //将元素入栈
                    stacks[size].push(val);
                    cnt[size]++;
                }else{
                    //弹出堆中第一个栈索引，并将当前值加入到该栈中
                    int cur = q.peek();
                    cnt[cur]++;
                    stacks[cur].push(val);
                    if(cnt[cur] == max) q.poll();
                }

            }else{
                //因为题目最大index为100000，所以之后的的数据全放一个栈里面
                //如果当前栈等于最大容量，则将栈数组size + 1
                if(cnt[size] == max && size <= 100000) size++;
                //创建新的栈，并将新的值入栈
                if(cnt[size] == 0) stacks[size] = new ArrayDeque<>();
                stacks[size].push(val);
                cnt[size]++;
            }
        }

        public int pop() {
            //如果所有栈为空，则返回-1
            if(size == 0 && cnt[size] == 0) return -1;
            while(cnt[size] == 0 && size > 0) size--;
            int ans = stacks[size].pop();
            cnt[size]--;
            return ans;
        }

        public int popAtStack(int index) {
            if(cnt[index] == 0) return -1;
            int ans = stacks[index].pop();
            //用堆来维护每个不等于最大容量的栈
            if(cnt[index] == max) q.offer(index);
            cnt[index]--;
            return ans;
        }
    }

/**
 * Your DinnerPlates object will be instantiated and called as such:
 * DinnerPlates obj = new DinnerPlates(capacity);
 * obj.push(val);
 * int param_2 = obj.pop();
 * int param_3 = obj.popAtStack(index);
 */

}
