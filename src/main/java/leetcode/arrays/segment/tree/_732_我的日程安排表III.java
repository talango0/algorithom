package leetcode.arrays.segment.tree;

import java.util.HashMap;
import java.util.Map;

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
