package leetcode.arrays.segment.tree;

import java.util.HashMap;
import java.util.Map;
//当 k 个日程安排有一些时间上的交叉时（例如 k 个日程安排都在同一时间内），就会产生 k 次预订。
//
// 给你一些日程安排 [start, end) ，请你在每个日程安排添加后，返回一个整数 k ，表示所有先前日程安排会产生的最大 k 次预订。
//
// 实现一个 MyCalendarThree 类来存放你的日程安排，你可以一直添加新的日程安排。
//
//
// MyCalendarThree() 初始化对象。
// int book(int start, int end) 返回一个整数 k ，表示日历中存在的 k 次预订的最大值。
//
//
//
//
// 示例：
//
//
//输入：
//["MyCalendarThree", "book", "book", "book", "book", "book", "book"]
//[[], [10, 20], [50, 60], [10, 40], [5, 15], [5, 10], [25, 55]]
//输出：
//[null, 1, 1, 2, 3, 3, 3]
//
//解释：
//MyCalendarThree myCalendarThree = new MyCalendarThree();
//myCalendarThree.book(10, 20); // 返回 1 ，第一个日程安排可以预订并且不存在相交，所以最大 k 次预订是 1 次预订。
//myCalendarThree.book(50, 60); // 返回 1 ，第二个日程安排可以预订并且不存在相交，所以最大 k 次预订是 1 次预订。
//myCalendarThree.book(10, 40); // 返回 2 ，第三个日程安排 [10, 40) 与第一个日程安排相交，所以最大 k 次预订是
// 2 次预订。
//myCalendarThree.book(5, 15); // 返回 3 ，剩下的日程安排的最大 k 次预订是 3 次预订。
//myCalendarThree.book(5, 10); // 返回 3
//myCalendarThree.book(25, 55); // 返回 3
//
//
//
//
// 提示：
//
//
// 0 <= start < end <= 10⁹
// 每个测试用例，调用 book 函数最多不超过 400次
//
//
// Related Topics 设计 线段树 二分查找 有序集合 👍 186 👎 0

/**
 * @author mayanwei
 * @date 2022-10-25.
 */
public class _732_我的日程安排表III{
    class MyCalendarThree{
        /**
         * 利用线段树，开辟数组 arr[0...10^9],初始时每个元素的值都为0，
         * 对于每次预订的区间[start, end) ，则将区间 arr[start, end) 中的
         * 每一个元素加1，最终只需要求出数组 arr 的最大元素即可。实际我们不需要
         * 开辟数组 arr，可以采用动态线段树，懒标记 lzay 标记区间 [l,r] 进行累加
         * 的次数，tree 记录区间 [l,r] 的最大值,最终返回 [0, 10^9] 中的最大元素即可。
         * <p>
         * 时间复杂度 O(nlogC)线段树的最大深度为logC， C=10^9
         * 空间复杂度 O(nlogC)
         */

        private Map<Integer, Integer> tree;
        private Map<Integer, Integer> lazy;

        public MyCalendarThree() {
            tree = new HashMap<Integer, Integer>();
            lazy = new HashMap<Integer, Integer>();
        }

        public int book(int start, int end) {
            update(start, end - 1, 0, 1000000000, 1);
            return tree.getOrDefault(1, 0);
        }

        public void update(int start, int end, int l, int r, int idx) {
            if (r < start || end < l) {
                return;
            }
            if (start <= l && r <= end) {
                tree.put(idx, tree.getOrDefault(idx, 0) + 1);
                lazy.put(idx, lazy.getOrDefault(idx, 0) + 1);
            }
            else {
                int mid = (l + r) >> 1;
                update(start, end, l, mid, 2 * idx);
                update(start, end, mid + 1, r, 2 * idx + 1);
                tree.put(idx, lazy.getOrDefault(idx, 0) + Math.max(tree.getOrDefault(2 * idx, 0), tree.getOrDefault(2 * idx + 1, 0)));
            }
        }
    }

    // 比较高效
    class Solution2{
        class MyCalendarThree{
            public int max = 0;

            public class SementTree{
                public int start;
                public int end;
                public int count;//次数
                public SementTree left;
                public SementTree right;//左右子树

                public SementTree(int start, int end) {
                    this.start = start;
                    this.end = end;
                }

                public SementTree(int start, int end, int count) {
                    this.start = start;
                    this.end = end;
                    this.count = count;
                }

                public void book(int start, int end) {
                    if (start == end) {
                        return;
                    }
                    //左右重合
                    if (start == this.start && end == this.end) {
                        this.count++;
                        if (this.count > max) {
                            max = this.count;
                        }
                        if (this.left == null) {
                            return;
                        }
                    }
                    if (this.left == null) {
                        if (start == this.start) {
                            this.left = new SementTree(start, end, this.count);
                            this.right = new SementTree(end, this.end, this.count);//左右
                            this.left.book(start, end);//递归插入
                            return;
                        }
                        this.left = new SementTree(this.start, start, this.count);
                        this.right = new SementTree(start, this.end, this.count);//左右
                        this.right.book(start, end);//递归插入
                        return;
                    }
                    if (start >= this.right.start) {
                        this.right.book(start, end);

                    }
                    else if (end <= this.left.end) {
                        this.left.book(start, end);

                    }
                    else {
                        this.left.book(start, this.left.end);
                        this.right.book(this.right.start, end);//夹逼
                    }
                }
            }

            public SementTree mytree;

            public MyCalendarThree() {
                mytree = new SementTree(0, (int) 1E9);//区间范围内  0 <= start < end <= 109
            }

            public int book(int start, int end) {
                mytree.book(start, end);
                return max;
            }
        }
    }


}
