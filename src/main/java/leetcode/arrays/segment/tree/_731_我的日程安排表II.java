package leetcode.arrays.segment.tree;

import java.util.HashMap;
import java.util.Map;
//实现一个 MyCalendar 类来存放你的日程安排。如果要添加的时间内不会导致三重预订时，则可以存储这个新的日程安排。
//
// MyCalendar 有一个 book(int start, int end)方法。它意味着在 start 到 end 时间内增加一个日程安排，注意，这里
//的时间是半开区间，即 [start, end), 实数 x 的范围为， start <= x < end。
//
// 当三个日程安排有一些时间上的交叉时（例如三个日程安排都在同一时间内），就会产生三重预订。
//
// 每次调用 MyCalendar.book方法时，如果可以将日程安排成功添加到日历中而不会导致三重预订，返回 true。否则，返回 false 并且不要将该
//日程安排添加到日历中。
//
// 请按照以下步骤调用MyCalendar 类: MyCalendar cal = new MyCalendar(); MyCalendar.book(
//start, end)
//
//
//
// 示例：
//
// MyCalendar();
//MyCalendar.book(10, 20); // returns true
//MyCalendar.book(50, 60); // returns true
//MyCalendar.book(10, 40); // returns true
//MyCalendar.book(5, 15); // returns false
//MyCalendar.book(5, 10); // returns true
//MyCalendar.book(25, 55); // returns true
//解释：
//前两个日程安排可以添加至日历中。 第三个日程安排会导致双重预订，但可以添加至日历中。
//第四个日程安排活动（5,15）不能添加至日历中，因为它会导致三重预订。
//第五个日程安排（5,10）可以添加至日历中，因为它未使用已经双重预订的时间10。
//第六个日程安排（25,55）可以添加至日历中，因为时间 [25,40] 将和第三个日程安排双重预订；
//时间 [40,50] 将单独预订，时间 [50,55）将和第二个日程安排双重预订。
//
//
//
//
// 提示：
//
//
// 每个测试用例，调用 MyCalendar.book 函数最多不超过 1000次。
// 调用函数 MyCalendar.book(start, end)时， start 和 end 的取值范围为 [0, 10^9]。
//
//
// Related Topics 设计 线段树 二分查找 有序集合 👍 208 👎 0

/**
 * @author mayanwei
 * @date 2022-10-25.
 */
public class _731_我的日程安排表II{
    class MyCalendarTwo{
        Map<Integer, int[]> tree;

        public MyCalendarTwo() {
            tree = new HashMap<Integer, int[]>();
        }

        public boolean book(int start, int end) {
            update(start, end - 1, 1, 0, 1000000000, 1);
            tree.putIfAbsent(1, new int[2]);
            if (tree.get(1)[0] > 2) {
                update(start, end - 1, -1, 0, 1000000000, 1);
                return false;
            }
            return true;
        }

        public void update(int start, int end, int val, int l, int r, int idx) {
            if (r < start || end < l) {
                return;
            }
            tree.putIfAbsent(idx, new int[2]);
            if (start <= l && r <= end) {
                tree.get(idx)[0] += val;
                tree.get(idx)[1] += val;
            }
            else {
                int mid = (l + r) >> 1;
                update(start, end, val, l, mid, 2 * idx);
                update(start, end, val, mid + 1, r, 2 * idx + 1);
                tree.putIfAbsent(2 * idx, new int[2]);
                tree.putIfAbsent(2 * idx + 1, new int[2]);
                tree.get(idx)[0] = tree.get(idx)[1] + Math.max(tree.get(2 * idx)[0], tree.get(2 * idx + 1)[0]);
            }
        }
    }


    class Solution3{
        // 不能3重预订
        class MyCalendarTwo{
            public MyCalendarTwo() {
            }
            public boolean book(int start, int end) {
                // 先查询该区间是否为 0
                if (query(root, 0, N, start, end - 1) >= 2) {
                    return false;
                }
                // 更新该区间
                update(root, 0, N, start, end - 1, 1);
                return true;
            }

            // *************** 下面是模版 ***************
            class Node{
                // 左右孩子节点
                Node left, right;
                // 当前节点值，以及懒惰标记的值
                int val, add;
            }

            private int N = (int) 1e9;
            private Node root = new Node();

            public void update(Node node, int start, int end, int l, int r, int val) {
                if (l <= start && end <= r) {
                    node.val += val;
                    node.add += val;
                    return;
                }
                pushDown(node);
                int mid = (start + end) >> 1;
                if (l <= mid) {
                    update(node.left, start, mid, l, r, val);
                }
                if (r > mid) {
                    update(node.right, mid + 1, end, l, r, val);
                }
                pushUp(node);
            }

            public int query(Node node, int start, int end, int l, int r) {
                if (l <= start && end <= r) {
                    return node.val;
                }
                pushDown(node);
                int mid = (start + end) >> 1, ans = 0;
                if (l <= mid) {
                    ans = query(node.left, start, mid, l, r);
                }
                if (r > mid) {
                    ans = Math.max(ans, query(node.right, mid + 1, end, l, r));
                }
                return ans;
            }

            private void pushUp(Node node) {
                // 每个节点存的是当前区间的最大值
                node.val = Math.max(node.left.val, node.right.val);
            }

            private void pushDown(Node node) {
                if (node.left == null) {
                    node.left = new Node();
                }
                if (node.right == null) {
                    node.right = new Node();
                }
                if (node.add == 0) {
                    return;
                }
                node.left.val += node.add;
                node.right.val += node.add;
                node.left.add += node.add;
                node.right.add += node.add;
                node.add = 0;
            }
        }
    }

    class Solution2{
        class MyCalendarTwo{

            //平衡二叉树
            public class Node{
                public Node left, right;
                public int num, l, r;//标记范围

                public Node() {
                    //初始化
                }
            }

            public Node root;//根节点

            public MyCalendarTwo() {
                root = new Node();//初始化
            }

            public boolean book(int start, int end) {
                return book(root, start, end);//传递一下

            }

            public boolean book(Node node, int l, int r) {
                if (l == r) {
                    return true; //重合一个数据
                }
                if (node.num == 0) {
                    node.l = l;
                    node.r = r;
                    node.left = new Node();//新节点
                    node.right = new Node();//新节点
                    node.num = 1;
                    return true;//返回
                }
                else if (r <= node.l) {
                    return book(node.left, l, r);//递归判断
                }
                else if (l >= node.r) {
                    return book(node.right, l, r);
                }
                int lmin = Math.min(node.l, l);
                int lmax = Math.max(node.l, l);//抓取最大最小

                int rmin = Math.min(node.r, r);
                int rmax = Math.max(node.r, r);//抓取最大最小

                if (!isValid(node, l, r)) {
                    return false;
                }
                node.num++;//追加节点数量
                node.l = lmax;
                node.r = rmin;//夹逼
                book(node.left, lmin, lmax);
                book(node.right, rmin, rmax);//夹逼
                return true;
            }

            public boolean isValid(Node node, int l, int r) {
                if (node.num == 0) {
                    return true;
                }
                if (r <= node.l) {
                    return isValid(node.left, l, r);//验证

                }
                if (l >= node.r) {
                    return isValid(node.right, l, r);
                }


                if (node.num == 2) {
                    return false;
                }
                return isValid(node.left, Math.min(l, node.l), Math.max(l, node.l)) &&
                        isValid(node.right, Math.min(r, node.r), Math.max(r, node.r));

            }
        }

/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */
    }
}
