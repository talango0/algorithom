package leetcode.程序员面试金典;
//魔术索引。 在数组A[0...n-1]中，有所谓的魔术索引，满足条件A[i] = i。给定一个有序整数数组，编写一种方法找出魔术索引，
// 若有的话，在数组A中找出一个魔术索引，如果没有，则返回-1。若有多个魔术索引，返回索引值最小的一个。
//
//示例1:
// 输入：nums = [0, 2, 3, 4, 5]
// 输出：0
// 说明: 0下标的元素为0
//
//示例2:
// 输入：nums = [1, 1, 1]
// 输出：1
//说明:
//
//nums长度在[1, 1000000]之间
//此题为原书中的 Follow-up，即数组中可能包含重复元素的版本
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/magic-index-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.Assert;
import org.junit.Test;

/**
 * @author mayanwei
 * @date 2023-05-25.
 */
public class _08_03_魔术索引{
    class Solution{
        public int findMagicIndex(int[] nums) {
            if (nums == null) {
                return -1;
            }
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == i) {
                    return i;
                }
            }
            return -1;
        }
    }


    class Solution2{
        /**
         * <pre>
         * 因为数组是有序的，可以利用这个条件进行二分查找。
         * 如果数字都不重复
         * ┌──────────────────────────────┐
         * │ -40 -20 -1 1 2 3 5 7 9 12 13 │
         * │  0   1   2 3 4 5 6 7 8 9  10 │
         * └────────────────▲─────────────┘
         * A[5] = 3 < 5,则魔术索引一定在右侧。
         * </pre>
         */
        public int findMagicIndex0(int[] nums) {
            if (nums == null) {
                return -1;
            }
            int l = 0, r = nums.length - 1;
            while (l <= r) {
                int mid = l + ((r - l) >> 1);
                if (nums[mid] == mid) {
                    return mid;
                }
                else if (nums[mid] < mid) {
                    l++;
                }
                else if (nums[mid] > mid) {
                    r--;
                }
            }
            return -1;
        }

        /**
         * <pre>
         * 如果数组由重复元素
         * ┌──────────────────────────────┐
         * │ -40 -20  2 2 2 3 5 7 9 12 13 │
         * │  0   1   2 3 4 5 6 7 8 9  10 │
         * └────────────▲───▲─────────────┘
         * 先比较 midIndex 和 midValue 是否相同，然后，若两者不同，则按如下方式递归搜索左半部分和右半部分。
         * 左半部分：搜索索引从 start 到 Math.min(midIndex-1, midValue) 的元素
         * 右半部分：搜素索引从 Math.max(midIndex+1,midValue) 到 end 的元素。
         *
         * </pre>
         *
         * @param nums
         * @return
         */
        public int findMagicIndex1(int[] nums) {
            if (nums == null) {
                return -1;
            }
            return findMagicIndex(nums, 0, nums.length - 1);
        }

        int findMagicIndex(int[] nums, int start, int end) {
            if (end < start) {
                return -1;
            }
            int midIndex = start + ((end - start) >> 1);
            int midValue = nums[midIndex];
            // 这里要搜索左边界
            //if (midValue == midIndex) {
            //    return midIndex;
            //}
            //搜索左半部分
            int leftIndex = Math.min(midIndex - 1, midValue);
            int left = findMagicIndex(nums, start, leftIndex);
            // 如果左边有更小的索引，返回左边的
            if (left >= 0) {
                return left;
            }
            // 再判断当前的索引
            if (midValue == midIndex) {
                return midIndex;
            }
            int rightIndex = Math.max(midIndex + 1, midValue);
            int right = findMagicIndex(nums, rightIndex, end);
            return right;
        }
    }

    @Test
    public void testSolution2 (){
        Solution2 solution2 = new Solution2();
        int magicIndex1 = solution2.findMagicIndex1(new int[]{-99, -87, -69, -51, -40, -38, -31, -30, -18, -1, 10, 11, 41, 63, 71, 72, 72, 78, 86, 88});
        Assert.assertEquals(magicIndex1, 10);


    }
}
