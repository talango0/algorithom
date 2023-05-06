package leetcode.arrays;
//给定一个整数数组 nums，将数组中的元素向右轮转 k个位置，其中k是非负数。
//
//示例 1:
//输入: nums = [1,2,3,4,5,6,7], k = 3
//输出: [5,6,7,1,2,3,4]
//解释:
//向右轮转 1 步: [7,1,2,3,4,5,6]
//向右轮转 2 步: [6,7,1,2,3,4,5]
//向右轮转 3 步: [5,6,7,1,2,3,4]

//示例2:
//输入：nums = [-1,-100,3,99], k = 2
//输出：[3,99,-1,-100]
//解释: 
//向右轮转 1 步: [99,-1,-100,3]
//向右轮转 2 步: [3,99,-1,-100]
//
//
//提示：
//
//1 <= nums.length <= 105
//-231 <= nums[i] <= 231 - 1
//0 <= k <= 105
//
//
//进阶：
//
//尽可能想出更多的解决方案，至少有 三种 不同的方法可以解决这个问题。
//你可以使用空间复杂度为O(1) 的原地算法解决这个问题吗？

public class _189_旋转数组 {
    /**
     * 空间复杂度 O(n)
     * 时间复杂度 O(n)
     */
    class Solution {
        public void rotate(int[] nums, int k) {
            int n = nums.length;
            int[] newArr = new int[n];
            for (int i = 0; i<n; i++) {
                newArr[(i+k)%n] = nums[i];
            }
            System.arraycopy(newArr, 0, nums, 0,n);
        }
    }


    class Solution1 {
        public void rotate(int[] nums, int k) {
            int n = nums.length;
            k %= n;
            int count = gcd(k, n);
            for(int start = 0; start < count; start ++) {
                int current = start;
                int prev = nums[start];
                do {
                    int next = (current + k) % n;
                    int temp = nums[next];
                    nums[next] = prev;
                    prev = temp;
                    current = next;
                } while (start!=current);
            }
        }
        //最大公约数
        public int gcd(int x, int y) {
            return y > 0 ? gcd(y, x%y) : x;
        }
    }


    /**
     * <pre>
     * 我们以 n=7，k=3 为例进行如下展示：
     *
     * 操作	                        结果
     * 原始数组	                    1 2 3 4 5 6 7
     * 翻转所有元素	                7 6 5 4 3 2 1
     * 翻转 [0,k mod n−1] 区间的元素	5 6 7 4 3 2 1
     * 翻转 [k mod n,n−1] 区间的元素   5 6 7 1 2 3 4
     * </pre>
     */
    class Solution2 {
        public void rotate(int[] nums, int k) {
            k %= nums.length;
            reverse(nums, 0, nums.length - 1);
            reverse(nums, 0, k-1);
            reverse(nums, k, nums.length - 1);
        }
        public void reverse(int [] nums, int start, int end) {
            while (start < end) {
                int tmp = nums[start];
                nums[start] = nums[end];
                nums[end] = tmp;
                start++;
                end--;
            }
        }
    }
}
