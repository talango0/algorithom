package leetcode.arrays;
//给定一个非负整数数组，你最初位于数组的第一个位置。
// 数组中的每个元素代表你在该位置可以跳跃的最大长度。
// 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
//
// 示例:
//
// 输入: [2,3,1,1,4]
//输出: 2
//解释: 跳到最后一个位置的最小跳跃数是 2。
//    从下标为 0 跳到下标为 1 的位置，跳1步，然后跳3步到达数组的最后一个位置。
//
// 说明:
//
// 假设你总是可以到达数组的最后一个位置。
// Related Topics 贪心算法 数组
// 👍 756 👎 0

public class _45_跳跃游戏II {

    //leetcode submit region begin(Prohibit modification and deletion)

    /**
     * <pre>
     * ┌────────────────────────────────┐
     * │ ────────────▶                  │
     * │ ────────▶                      │
     * │ ────▶                          │
     * │ 0   1   2   3   4   5   6   7  │
     * │ 3   1   4   2  ... ... ... ... │
     * │     ────▶                      │
     * │         ────────────────▶      │
     * │             ────────▶          │
     * │     i       end    maxPosition │
     * └────────────────────────────────┘
     * </pre>
     */
    static class Solution {
        public int jump(int[] nums) {
            int maxPosition= 0, steps = 0, end = 0;
            for(int i = 0; i < nums.length-1; i++){
                maxPosition = Math.max(maxPosition, nums[i] + i);
                 if(i == end){
                    end = maxPosition;
                    steps++;
                }
            }
            return steps;
        }
    }


}
