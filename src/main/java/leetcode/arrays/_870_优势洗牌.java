package leetcode.arrays;
//给定两个大小相等的数组 nums1 和 nums2，nums1 相对于 nums 的优势可以用满足 nums1[i] > nums2[i] 的索引 i 的数
//目来描述。
//
// 返回 nums1 的任意排列，使其相对于 nums2 的优势最大化。
//
//
//
// 示例 1：
//
//
//输入：nums1 = [2,7,11,15], nums2 = [1,10,4,11]
//输出：[2,11,7,15]
//
//
// 示例 2：
//
//
//输入：nums1 = [12,24,8,32], nums2 = [13,25,32,11]
//输出：[24,32,8,12]
//
//
//
//
// 提示：
//
//
// 1 <= nums1.length <= 10⁵
// nums2.length == nums1.length
// 0 <= nums1[i], nums2[i] <= 10⁹
//
// Related Topics 贪心 数组 排序 👍 192 👎 0

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-07-01.
 * @see <a href='https://labuladong.github.io/algo/2/19/30/'>田忌赛马</a>
 */
public class _870_优势洗牌{

    class Solution {
        public int[] advantageCount(int[] nums1, int[] nums2) {
            int n = nums1.length;
            //给 nums2 将序排列
            PriorityQueue<int[]> maxpq = new PriorityQueue<>((o1, o2)->o2[1] - o1[1]);
            for (int i = 0;i < nums2.length; i++) {
                maxpq.offer(new int[]{i, nums2[i]});
            }
            //给 nums1升序排列
            Arrays.sort(nums1);

            //nums1[left] 是最小值， nums1[right] 是最大值
            int left = 0;
            int right = nums1.length-1;
            int [] res = new int[n];
            while (!maxpq.isEmpty()) {
                int [] pair = maxpq.poll();
                // maxval 是 nums2 中的最大值， i是对应的索引。
                int i = pair[0], maxval = pair[1];
                if (maxval < nums1[right]) {
                    //如果 nums1[right] 能胜过 maxval, 那就自己上
                    res[i] = nums1[right];
                    right --;
                }
                else {
                    //否则用最小值混一下，养精蓄锐
                    res[i] = nums1[left];
                    left ++;
                }
            }
            return res;
        }
    }
}
