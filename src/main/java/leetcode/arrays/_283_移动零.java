package leetcode.arrays;
//给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
//
// 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
//
//
//
// 示例 1:
//
//
//输入: nums = [0,1,0,3,12]
//输出: [1,3,12,0,0]
//
//
// 示例 2:
//
//
//输入: nums = [0]
//输出: [0]
//
//
//
// 提示:
//
//
//
// 1 <= nums.length <= 10⁴
// -2³¹ <= nums[i] <= 2³¹ - 1
//
//
//
//
// 进阶：你能尽量减少完成的操作次数吗？
// Related Topics 数组 双指针 👍 1633 👎 0

public class _283_移动零 {
    class Solution {
        public
        void moveZeroes(int [] nums) {
            int p = removeElement(nums, 0);
            for (;p < nums.length; p++){
                nums[p] = 0;
            }
        }
        int removeElement(int [] nums, int val) {
            int fast = 0, slow = 0;
            int n = nums.length;
            // fast 遇到值为 val 的元素，则直接跳过，否则赋值给 slow 指针，并让 slow 前进一步。
            while ( fast<n ) {
                if (nums[fast]!=val) {
                    //这里有个细节 和26题比较 ，先给nums[slow] 赋值，然后再给 slow+1
                    nums[slow] = nums[fast];
                    slow++;
                }
                fast++;
            }
            return slow;
        }
    };
}
