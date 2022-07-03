package leetcode.arrays.monotonous.stack;
//给定一个循环数组 nums （ nums[nums.length - 1] 的下一个元素是 nums[0] ），返回 nums 中每个元素的 下一个更大元素
// 。
//
// 数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1
//。
//
//
//
// 示例 1:
//
//
//输入: nums = [1,2,1]
//输出: [2,-1,2]
//解释: 第一个 1 的下一个更大的数是 2；
//数字 2 找不到下一个更大的数；
//第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
//
//
// 示例 2:
//
//
//输入: nums = [1,2,3,4,3]
//输出: [2,3,4,-1,4]
//
//
//
//
// 提示:
//
//
// 1 <= nums.length <= 10⁴
// -10⁹ <= nums[i] <= 10⁹
//
// Related Topics 栈 数组 单调栈 👍 650 👎 0


import java.util.Deque;
import java.util.LinkedList;

/**
 * @author mayanwei
 * @date 2022-07-03.
 */
public class _503_下一个更大元素2{
    class Solution {
        public int[] nextGreaterElements(int[] nums) {
            int n = nums.length;
            int [] res = new int[n];
            Deque<Integer> stk = new LinkedList<Integer>();
            //假装数组长度翻倍了
            for (int i = 2*n-1; i>=0; i--){
                //索引要求模，其他的和模版一样
                while (!stk.isEmpty() && stk.peek() <= nums[i%n]) {
                    stk.pollFirst();
                }
                res[i%n] = stk.isEmpty()? -1:stk.peek();
                stk.addFirst(nums[i % n]);
            }
            return res;

        }
    }
}
