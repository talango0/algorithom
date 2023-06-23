package leetcode.程序员面试金典;
//数组中占比超过一半的元素称之为主要元素。给你一个 整数 数组，找出其中的主要元素。若没有，返回 -1 。
// 请设计时间复杂度为 O(N) 、空间复杂度为 O(1) 的解决方案。
//
//HW
//
//示例 1：
//
//输入：[1,2,5,9,5,9,5,5,5]
//输出：5
//示例 2：
//
//输入：[3,2]
//输出：-1
//示例 3：
//
//输入：[2,2,1,1,1,2,2]
//输出：2
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/find-majority-element-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2023-06-23.
 */
public class _17_10_主要元素{
    // 题目要求在 O(N) 的时间内和 O(1) 的空间内完成。
    // 我们先放宽时间，尝试用O(1) 的空间复杂度完成
    class Solution{
        public int majorityElement(int[] nums) {
            for (int num : nums) {
                if (validate(num, nums)) {
                    return num;
                }
            }
            return -1;
        }

        private boolean validate(int num, int[] nums) {
            int count = 0;
            for (int n : nums) {
                if (num == n) {
                    count += 1;
                }
            }
            return count > nums.length / 2;
        }
    }

    /**
     * 给定一个元素，开始检查它是否是一个子数组的开始，同时对于这个子数组，该元素是它的主要元素。
     * 一旦它变得“不太可能”(出现的次数少于一半)，就开始检查下一个元素(子数组之后的元素)。
     */
    class Solution1{

        public int majorityElement(int[] nums) {
            int candidate = getCandidate(nums);
            return validate(nums, candidate) ? candidate :-1;
        }

        private boolean validate(int[] nums, int majority) {
            int count = 0;
            for (int n : nums) {
                if (n == majority) {
                    count++;
                }
            }
            return count > nums.length / 2;
        }

        private int getCandidate(int[] nums) {
            int majority = 0;
            int count = 0;
            for (int n : nums) {
                if (count == 0) { // 前面的集合中没有主要元素
                    majority = n;
                }
                if (n == majority) {
                    count++;
                }
                else {
                    count--;
                }
            }
            return majority;
        }
    }

    /**
     * Boyer-Moore 投票算法的基本思想是：
     * <p>
     * 在每一轮投票过程中，从数组中删除两个不同的元素，
     * 直到投票过程无法继续，此时数组为空或者数组中剩下的元素都相等。
     * <p>
     * 如果数组为空，则数组中不存在主要元素；
     * 如果数组中剩下的元素都相等，则数组中剩下的元素可能为主要元素。
     */
    class Solution2{
        class Solution{
            public int majorityElement(int[] nums) {
                int cdt = nums[0];
                int count = 0;
                for (int num : nums) {
                    if (count == 0) {
                        cdt = num;
                    }
                    if (num == cdt) {
                        count++;
                    }
                    else {
                        count--;
                    }
                }
                count = 0;
                int len = nums.length;
                for (int num : nums) {
                    if (num == cdt) {
                        count++;
                    }
                }
                return count * 2 > len ? cdt :-1;
            }
        }
    }


}
