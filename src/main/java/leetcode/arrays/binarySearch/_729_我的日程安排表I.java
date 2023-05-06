package leetcode.arrays.binarySearch;
//实现一个 MyCalendar 类来存放你的日程安排。如果要添加的日程安排不会造成 重复预订 ，则可以存储这个新的日程安排。
//
// 当两个日程安排有一些时间上的交叉时（例如两个日程安排都在同一时间内），就会产生 重复预订 。
//
// 日程可以用一对整数 start 和 end 表示，这里的时间是半开区间，即 [start, end), 实数 x 的范围为， start <= x <
//end 。
//
// 实现 MyCalendar 类：
//
//
// MyCalendar() 初始化日历对象。
// boolean book(int start, int end) 如果可以将日程安排成功添加到日历中而不会导致重复预订，返回 true 。否则，返回
//false 并且不要将该日程安排添加到日历中。
//
//
//
//
// 示例：
//
//
//输入：
//["MyCalendar", "book", "book", "book"]
//[[], [10, 20], [15, 25], [20, 30]]
//输出：
//[null, true, false, true]
//
//解释：
//MyCalendar myCalendar = new MyCalendar();
//myCalendar.book(10, 20); // return True
//myCalendar.book(15, 25); // return False ，这个日程安排不能添加到日历中，因为时间 15 已经被另一个日程安排预订了
//。
//myCalendar.book(20, 30); // return True ，这个日程安排可以添加到日历中，因为第一个日程安排预订的每个时间都小于 20
// ，且不包含时间 20 。
//
//
//
// 提示：
//
//
// 0 <= start < end <= 10⁹
// 每个测试用例，调用 book 方法的次数最多不超过 1000 次。
//
//
// Related Topics 设计 线段树 二分查找 有序集合 👍 236 👎 0

import inductiontoalgorithm.segment_tree.SegmentTree;
import leetcode.jzhoffer.剑指_Offer_II_058_日程表;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-10-23.
 * @see 剑指_Offer_II_058_日程表
 * @see SegmentTree
 */
public class _729_我的日程安排表I{
    class Solution1{
        class MyCalendar{
            /**
             * 时间复杂度 O(n^2), 空间复杂度 O(n)
             */
            List<int[]> booked;

            public MyCalendar() {
                booked = new ArrayList<>();
            }

            public boolean book(int start, int end) {
                for (int[] arr : booked) {
                    int l = arr[0], r = arr[1];
                    if (l < end && start < r) {
                        return false;
                    }
                }
                booked.add(new int[]{start, end});
                return true;
            }
        }

    }

    class Solution2{
        class MyCalendar{
            /**
             * 时间复杂度 O(nlogn), 空间复杂度 O(n)
             */
            // 通过二分查找日程安排的情况检查日程安排是否可以预定。
            // 需要一个数据结构能保持元素排序的支持快速插入，可以用 TreeSet 来构建。
            // 对于 [start, end)，我们每次查找起点大于等于 end 的第一个区间 [l1, r1), 同时
            // 紧挨着 [l1, r1)的前一个区间为 [l2,r2), 此时如果满足 r2 <= start < end <= l1,
            // 则区间可以预定
            //     r2
            // ─────
            //             l1
            //             ─────
            //      start  end
            //       ──────    这个可以预定

            TreeSet<int[]> booked;

            public MyCalendar() {
                booked = new TreeSet<int[]>((a, b) -> a[0] - b[0]);
            }

            public boolean book(int start, int end) {
                if (booked.isEmpty()) {
                    booked.add(new int[]{start, end});
                    return true;
                }

                int[] tmp = {end, 0};
                int[] arr = booked.ceiling(tmp);
                int[] prev = arr == null ? booked.last() :booked.lower(arr);
                if (arr == booked.first() || booked.lower(tmp)[1] <= start) {
                    booked.add(new int[]{start, end});
                    return true;
                }
                return false;
            }
        }
    }

    static class Solution3{
        public static class MyCalendar{
            /**
             * 时间复杂度 O(nlogC), 空间发负载度 O(nlogC)，线段树的最大深度为logC，在此 C 取固定值 10^9
             */
            // 利用线段树，假设开辟了数组 arr[0, ...,  10^9], 初始时每个元素的值都是0，对于每次行程预订的区间 [start,end)
            // 则我们将区间中的元素 arr[start, ..., end-1] 中的每个元素都标记为1，每次调用book时，我们只需要检测 arr[start, end-1]
            // 区间内是否有元素标记为1。实际上我们不必实际开辟数组 arr,可采用线段树，懒标记 lazy 标记区间 [l,r]已经被预订了，tree记录
            // 区间 [l,r]是否存在标记为1 的元素。
            // 每次进行book操作时，首先判断区间 [start, end-1]是否存在元素被标记，如果存在被标记为1的元素，则表明该区间不可预订。
            // 否则，将可以预订。预订完成后，将 arr[start, ..., end-1] 进行标记为1，并同时更新线段树。
            Set<Integer> tree;
            Set<Integer> lazy;

            public MyCalendar() {
                tree = new HashSet<>();
                lazy = new HashSet<>();
            }

            public boolean book(int start, int end) {
                if (query(start, end - 1, 0, 1000000000, 1)) {
                    return false;
                }
                update(start, end - 1, 0, 1000000000, 1);
                return true;
            }

            public boolean query(int start, int end, int l, int r, int idx) {
                if (start > r || end < l) {
                    return false;
                }
                /*如果该区间已被预订，则直接返回 */
                if (lazy.contains(idx)) {
                    return true;
                }

                if (start <= l && r <= end) {
                    return tree.contains(idx);
                }
                else {
                    int mid = (l + r) >> 1;
                    if (end <= mid) {
                        return query(start, end, l, mid, 2 * idx);
                    }
                    else if (start > mid) {
                        return query(start, end, mid + 1, r, 2 * idx + 1);
                    }
                    else {
                        return query(start, end, l, mid, 2 * idx) | query(start, end, mid + 1, r, 2 * idx + 1);
                    }
                }
            }

            public void update(int start, int end, int l, int r, int idx) {
                if (r < start || end < l) {
                    return;
                }
                if (start <= l && r <= end) {
                    tree.add(idx);
                    lazy.add(idx);
                }
                else {
                    int mid = (l + r) >> 1;
                    update(start, end, l, mid, 2 * idx);
                    update(start, end, mid + 1, r, 2 * idx + 1);
                    tree.add(idx);
                }
            }
        }
    }

    @Test
    public void test(){
        Solution3.MyCalendar solution3 = new Solution3.MyCalendar();
        //[null, true, false, true]
        int [][] data = {{10, 20}, {15, 25}, {20, 30}};
        for (int [] arr : data) {
                System.out.println(solution3.book(arr[0], arr[1]));
        }
    }

}
