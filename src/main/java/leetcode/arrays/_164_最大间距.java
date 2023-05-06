package leetcode.arrays;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
//给定一个无序的数组nums，返回 数组在排序之后，相邻元素之间最大的差值 。如果数组元素个数小于 2，则返回 0 。
//
//您必须编写一个在「线性时间」内运行并使用「线性额外空间」的算法。
//
//
//
//示例1:
//
//输入: nums = [3,6,9,1]
//输出: 3
//解释: 排序后的数组是 [1,3,6,9], 其中相邻元素 (3,6) 和 (6,9) 之间都存在最大差值 3。
//示例2:
//
//输入: nums = [10]
//输出: 0
//解释: 数组元素个数小于 2，因此返回 0。
//
//
//提示:
//
//1 <= nums.length <= 105
//0 <= nums[i] <= 109
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/maximum-gap
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
/**
 * 字节
 *
 * @author mayanwei
 * @date 2022-08-21.
 */
public class _164_最大间距{
    class Solution{
        public int maximumGap(int[] nums) {
            if (nums == null || nums.length <= 1) {
                return 0;
            }
            Arrays.sort(nums);
            int n = nums.length;
            int maxDiff = Integer.MIN_VALUE;
            for (int i = 0, j = 1; j < n; i++, j++) {
                maxDiff = Math.max(maxDiff, nums[j] - nums[i]);
            }
            return maxDiff;
        }
    }
    /**
     * 基数排序
     */
    class Solution3 {
        public int maximumGap(int[] nums) {
            int maxGap = 0;
            //通过基数排序
            int n = maxBit(nums);
            for (int i = 0, radix = 1; i< n; i++){
                countSorting(nums, radix);
                radix *= 10;
            }

            for (int i = 0,j = 1; j<nums.length; j++) {
                maxGap = Math.max(maxGap, nums[j] - nums[i]);
            }
            return maxGap;
        }

        private void countSorting(int[] nums, int radix) {
            int [] counts = new int[10];
            int len = nums.length;
            int [] buffer = new int[len];
            for (int num : nums) {
                counts[ (num / radix) % 10 ]++;
            }
            for (int i = 1; i < counts.length; i++) {
                counts[i] += counts[i-1];
            }

            for (int i = len-1; i>=0; i--) {
                buffer[counts[(nums[i] / radix) % 10] - 1] = nums[i];
                counts[(nums[i] / radix) % 10]--;
            }
            System.arraycopy(buffer, 0,nums, 0, len);
        }

        public int maxBit(int []nums) {
            int max = 0;
            for (int num : nums) {
                max = Math.max( num, max);
            }
            int d = 1;
            while ((max = max / 10) > 0) {
                d ++;
            }
            return d;
        }
    }

    /**
     * 桶排序
     */
    class Solution2{
        public int maximumGap(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
            for (int num : nums) {
                max = Math.max(num, max);
                min = Math.min(num, min);
            }
            //每个桶中最大值最小值之间的差不允许超过gap
            if (max == min) {
                return 0;
            }
            int gap = (int) Math.ceil((double) (max - min) / (nums.length - 1));
            int size = (max - min) / gap + 1;
            int[] bucketMax = new int[size];
            int[] bucketMin = new int[size];
            Arrays.fill(bucketMax, Integer.MIN_VALUE);
            Arrays.fill(bucketMin, Integer.MAX_VALUE);
            for (int val : nums) {
                //本元素该落在的桶号
                int idx = (val - min) / (gap + 1);
                //这个题特殊点：只记录这个桶号里元素的最大值最小值
                bucketMax[idx] = Math.max(bucketMax[idx], val);
                bucketMin[idx] = Math.min(bucketMin[idx], val);
            }
            for (int i = 0; i < size; i++) {
                if (!(bucketMin[i] == Integer.MAX_VALUE && bucketMax[i] == Integer.MIN_VALUE)) {
                    //有可能相邻元素最大差值的元素分别在两个桶，所以需要遍历桶
                    gap = Math.max(gap, bucketMin[i] - min);
                    min = bucketMax[i]; // 上一个最大的距离下一个最小的是相邻节点
                }
            }
            return gap;
        }
    }

    @Test
    public void test() {
        int[] arr1 = {3, 6, 9, 1};
        Solution2 solution2 = new Solution2();
        Assert.assertEquals(solution2.maximumGap(arr1), 3);
    }
}
