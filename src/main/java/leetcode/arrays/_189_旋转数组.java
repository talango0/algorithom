package leetcode.arrays;

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
            newArr = null;
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
     * 我们以 n=7，k=3 为例进行如下展示：
     *
     * 操作	                        结果
     * 原始数组	                    1 2 3 4 5 6 7
     * 翻转所有元素	                7 6 5 4 3 2 1
     * 翻转 [0,k mod n−1] 区间的元素	5 6 7 4 3 2 1
     * 翻转 [k mod n,n−1] 区间的元素   5 6 7 1 2 3 4
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
