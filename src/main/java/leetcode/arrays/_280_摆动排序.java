package leetcode.arrays;
// Given an unsorted array nums, reorder it in-place
// such that nums[0] <= nums[1] >= nums[2] <= nums[3]....
//
//Example:
//
//Input:
//nums = [3,5,2,1,6,4]
//
//Output: One possible answer is [3,5,1,6,2,4]

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.xml.transform.Source;
import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2022-09-25.
 * @see _324_摆动排序II
 * @see _75_颜色分类
 */
public class _280_摆动排序{
    /**
     * 跟 {@link _324_摆动排序II} 相比起来，这道题的条件宽松很多，只因为多了一个等号。
     * 由于等号的存在，当数组中有重复数字存在的情况时，也很容易满足题目的要求
     */

    /**
     * O(nlogn),思路：先给数组排序，然后每次只需要把 第二个数和第三个数互环，第四个数和第5个数互换，知道数组末尾即可。
     */
    class Solution{
        public void wiggleSort(int[] nums) {
            Arrays.sort(nums);
            if (nums.length < 2) {
                return;
            }
            for (int i = 2; i < nums.length; i += 2) {
                swap(nums, i, i - 1);
            }
        }

        private void swap(int[] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }

    /**
     * 这道题还有一种 O(n) 的解法，根据题目要求的 nums[0] <= nums[1] >= nums[2] <= nums[3]....，
     * 可以总结出如下规律：
     * <p>
     * 当i为奇数时，nums[i] >= nums[i - 1]
     * <p>
     * 当i为偶数时，nums[i] <= nums[i - 1]
     * <p>
     * 那么只要对每个数字，根据其奇偶性，跟其对应的条件比较，如果不符合就和前面的数交换位置即可
     */
    class Solution2{
        public void wiggleSort(int[] nums) {
            if (nums.length <= 1) {
                return;
            }
            for (int i = 1; i < nums.length; i++) {
                if ((i % 2 == 1 && nums[i] < nums[i - 1])
                        || (i % 2 == 0 && nums[i] > nums[i-1])) {
                    swap(nums, i, i-1);
                }
            }

        }
        private void swap(int[] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }

    @Test
    public void test() {
        int []nums =new int[]{3,5,2,1,6,4};
        Solution2 solution = new Solution2();
        solution.wiggleSort(nums);
        System.out.println(Arrays.toString(nums));
        Assert.assertArrayEquals(nums, new int[]{3,5,1,6,2,4});

    }
}
