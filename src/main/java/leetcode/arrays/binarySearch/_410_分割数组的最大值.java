package leetcode.arrays.binarySearch;
//给定一个非负整数数组 nums 和一个整数 m ，你需要将这个数组分成 m 个非空的连续子数组。
//
//设计一个算法使得这 m 个子数组各自和的最大值最小。
//
//示例 1：
//
//输入：nums = [7,2,5,10,8], m = 2
//输出：18
//解释：
//一共有四种方法将 nums 分割为 2 个子数组。
//其中最好的方式是将其分为 [7,2,5] 和 [10,8] 。
//因为此时这两个子数组各自的和的最大值为18，在所有情况中最小。
//示例 2：
//
//输入：nums = [1,2,3,4,5], m = 2
//输出：9
//示例 3：
//
//输入：nums = [1,4,4], m = 3
//输出：4
//提示：
//
//1 <= nums.length <= 1000
//0 <= nums[i] <= 106
//1 <= m <= min(50, nums.length)
//Related Topics
//
//👍 724, 👎 0
/**
 * @author mayanwei
 * @date 2022-08-17.
 */
public class _410_分割数组的最大值{
    class Solution {
        /**在每个子数组和不超过 max 的条件下，计算 nuns 至少可以分割成几个子数组，
         因此这种情况下，如果我们找到一个最小 max 值，满足 split(nums, max) 和 m 相等，那么这个最小的max 就是符合题意的【最小的最大子数组和】
         现在就简单了，我们只要对max进行穷举就行，那么最大子数组和max的取值范围是什么呢？
         显然，子数组至少包含一个元素，至多包含整个数组，所以「最大」子数组和的取值范围就是闭区间[max(nums), sum(nums)]，也就是最大元素值到整个数组和之间。 */
        public int splitArray(int[] nums, int m) {
            // int lo = Arrays.stream(nums).max().getAsInt(), hi = Arrays.stream(nums).sum() + 1;
            int lo = getMax(nums), hi = getSum(nums)+1;
            // for (int max = lo; max<= hi; max++) {
            //     // 如果最大子数组和时 max
            //     // 至少可以把nums分割成n个子数组
            //     int n = split(nums, max);
            //     // 为什么时 <= 而不是 ==

            //     if (n <= m) {
            //         return max;
            //     }
            // }
            // 可能存在多个max使得split(nums, max)算出相同的n，因为我们的算法会返回最小的那个max，所以应该使用搜索左侧边界的二分查找算法。
            // 在[lo, hi] 中搜索一个最小的 max，使得 split(nums, max) 恰好等于 m
            while (lo<hi) {
                int mid = lo + (hi-lo)/2;
                // 根据分割子数组的个数收缩搜索区间
                int n = split(nums, mid);
                if (n == m) {
                    // 收缩右边界，达到搜索左边界的目的
                    hi = mid;
                }
                else if ( n < m) {
                    // 最大数组和的上限高了，收缩右边界
                    hi = mid;
                }
                else if ( n > m) {
                    // 最大数组和的上限低了，增加一些
                    lo = mid + 1;
                }
            }
            return lo;
        }

        /**辅助函数，若限制最大子数组和为 max ， 计算至少可以被分割成几个子数组*/
        int split(int [] nums, int max){
            // 至少可以分割成1个
            int count = 1;
            // 记录每个子数组的元素和
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                if (sum + nums[i] > max) {
                    // 如果当前子数组和大于 max 限制
                    // 则这个子数组不能在添加元素了
                    count ++;
                    sum = nums[i];
                }
                else {
                    // 当前子数组和还没有达到 max 限制
                    // 还可以添加元素
                    sum += nums[i];
                }
            }
            return count;
        }
        // 计算数组中的最大值
        int getMax(int[] nums) {
            int res = 0;
            for (int n : nums)
                res = Math.max(n, res);
            return res;
        }

        // 计算数组元素和
        int getSum(int[] nums) {
            int res = 0;
            for (int n : nums)
                res += n;
            return res;
        }

        // 1、对max变量的穷举是从lo到hi即从小到大的。

        // 这是因为我们求的是「最大子数组和」的「最小值」，且split函数的返回值有单调性，所以从小到大遍历，第一个满足条件的值就是「最小值」。

        // 2、函数返回的条件是n <= m，而不是n == m。按照之前的思路，应该n == m才对吧？

        // 其实，split函数采用了贪心的策略，计算的是max限制下至少能够将nums分割成几个子数组。

        // 举个例子，输入nums = [2,1,1], m = 3，显然分割方法只有一种，即每个元素都认为是一个子数组，最大子数组和为 2。

        // 但是，我们的算法会在区间[2,4]穷举max，当max = 2时，split会算出nums至少可以被分割成n = 2个子数组[2]和[1,1]。

        // 当max = 3时算出n = 2，当max = 4时算出n = 1，显然都是小于m = 3的。

        // 所以我们不能用n == m而必须用n <= m来找到答案，因为如果你能把nums分割成 2 个子数组（[2],[1,1]），那么肯定也可以分割成 3 个子数组（[2],[1],[1]）。


    }
}
