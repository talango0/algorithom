package leetcode.arrays;
//给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
//
//不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
//
//
//
//说明：
//
//为什么返回数值是整数，但输出的答案是数组呢？
//
//请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
//
//你可以想象内部操作如下:
//
//// nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
//int len = removeDuplicates(nums);
//
//// 在函数里修改输入数组对于调用者是可见的。
//// 根据你的函数返回的长度, 它会打印出数组中 该长度范围内 的所有元素。
//for (int i = 0; i < len; i++) {
//  print(nums[i]);
//}
//
//
//示例 1：
//
//输入：nums = [1,1,1,2,2,3]
//输出：5, nums = [1,1,2,2,3]
//解释：函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。 不需要考虑数组中超出新长度后面的元素。
//示例 2：
//
//输入：nums = [0,0,1,1,1,1,2,3,3]
//输出：7, nums = [0,0,1,1,2,3,3]
//解释：函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为0, 0, 1, 1, 2, 3, 3 。 不需要考虑数组中超出新长度后面的元素。
//
//
//提示：
//
//1 <= nums.length <= 3 * 104
//-104 <= nums[i] <= 104
//nums 已按升序排列
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.jupiter.api.Test;

/**
 * @author mayanwei
 * @date 2023-07-15.
 */
public class _80_删除有序数组中的重复项II{
    class Solution{
        public int removeDuplicates(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            // 快慢指针，维护 nums[0...slow]为结果子数组
            int fast = 0, slow = 0;
            int n = nums.length;
            // 记录一个元素重复的次数
            int count = 0;
            while (fast < n) {
                if (nums[slow] != nums[fast]) {
                    nums[++slow] = nums[fast];
                }
                else if (slow < fast && count < 2) {
                    // 当一个元素重复次数不到2次时，也
                    nums[++slow] = nums[fast];
                }
                fast++;
                count++;
                if (fast < nums.length && nums[fast] != nums[fast - 1]) {
                    // 遇到不同的元素
                    count = 0;
                }
            }
            // 数组长度为索引+1
            return slow + 1;
        }
    }

    @Test
    public  void main() {
        int[] ar = {1, 1, 1, 2, 2, 3};
        Solution solution = new Solution();
        System.out.println(solution.removeDuplicates(ar));
    }
}
