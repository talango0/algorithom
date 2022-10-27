package leetcode.jzhoffer;
//请实现一个 MyCalendar 类来存放你的日程安排。如果要添加的时间内没有其他安排，则可以存储这个新的日程安排。
//
// MyCalendar 有一个 book(int start, int end)方法。它意味着在 start 到 end 时间内增加一个日程安排，注意，这里
//的时间是半开区间，即 [start, end), 实数 x 的范围为， start <= x < end。
//
// 当两个日程安排有一些时间上的交叉时（例如两个日程安排都在同一时间内），就会产生重复预订。
//
// 每次调用 MyCalendar.book方法时，如果可以将日程安排成功添加到日历中而不会导致重复预订，返回 true。否则，返回 false 并且不要将该
//日程安排添加到日历中。
//
// 请按照以下步骤调用 MyCalendar 类: MyCalendar cal = new MyCalendar(); MyCalendar.book(
//start, end)
//
//
//
// 示例:
//
//
//输入:
//["MyCalendar","book","book","book"]
//[[],[10,20],[15,25],[20,30]]
//输出: [null,true,false,true]
//解释:
//MyCalendar myCalendar = new MyCalendar();
//MyCalendar.book(10, 20); // returns true
//MyCalendar.book(15, 25); // returns false ，第二个日程安排不能添加到日历中，因为时间 15 已经被第一个日程安排预
//定了
//MyCalendar.book(20, 30); // returns true ，第三个日程安排可以添加到日历中，因为第一个日程安排并不包含时间 20
//
//
//
//
//
//
// 提示：
//
//
// 每个测试用例，调用 MyCalendar.book 函数最多不超过 1000次。
// 0 <= start < end <= 10⁹
//
//
//
//
//
// 注意：本题与主站 729 题相同： https://leetcode-cn.com/problems/my-calendar-i/
//
// Related Topics 设计 线段树 二分查找 有序集合 👍 40 👎 0

import leetcode.arrays.binarySearch._729_我的日程安排表I;
import leetcode.arrays.segment.tree._731_我的日程安排表II;
import leetcode.arrays.segment.tree._732_我的日程安排表III;

import java.util.TreeSet;

/**
 * @author mayanwei
* @date 2022-10-23.
 * @see _729_我的日程安排表I
 * @see _731_我的日程安排表II
 * @see _732_我的日程安排表III
 */
public class 剑指_Offer_II_058_日程表{
    class MyCalendar{
        /**
         * 时间复杂度 O(nlogn), 空间发复杂度 O(n)
         */
        // 通过二分查找日程安排的情况检查日程安排是否可以预定。
        // 需要一个数据结构能保持元素排序的支持快速插入，可以用 TreeSet 来构建。
        // 对于 [start, end)，我们每次查找起点大于等于 end 的第一个区间 [l1, r1), 同时
        // 紧挨着 [l1, r1)的前一个区间为 [l2,r2), 此时如果满足 r2 <= start < end <= l1,
        // 则区间可以预定
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
