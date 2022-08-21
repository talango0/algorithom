package leetcode.arrays;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
