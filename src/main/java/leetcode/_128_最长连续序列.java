package leetcode;

import java.util.HashMap;
import java.util.Set;

public class _128_最长连续序列 {
    //给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
//
//
//
// 进阶：
//
//
// 你可以设计并实现时间复杂度为 O(n) 的解决方案吗？
//
//
//
//
// 示例 1：
//
//
//输入：nums = [100,4,200,1,3,2]
//输出：4
//解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
//
// 示例 2：
//
//
//输入：nums = [0,3,7,2,5,8,4,6,0,1]
//输出：9
//
//
//
//
// 提示：
//
//
// 0 <= nums.length <= 104
// -231 <= nums[i] <= 231 - 1
//
// Related Topics 并查集 数组
// 👍 586 👎 0


    //leetcode submit region begin(Prohibit modification and deletion)
    static class  Solution {
        public static int longestConsecutive(int[] nums) {
            if(nums == null || nums.length ==0){
                return 0;
            }
            HashMap<Long, Long> map = new HashMap<>(nums.length);

            Long len = 0L;
            for(int i = 0; i< nums.length; i++){
                if(!map.containsKey(Long.valueOf(nums[i]))){
                    // num[i] 组成一个长度为1的序列
                    // 判断 map.keySet里面有没有 num[i] + 1 以及 num[i] - 1
                    // 如果存在，比较 num[i] + 1 对应的值和 num[i] - 1对应的值的大小
                    map.put(Long.valueOf(nums[i]), 1L);
                    Long preLen = map.containsKey(Long.valueOf(nums[i])-1L)?map.get(Long.valueOf(nums[i])-1L):0L;
                    Long posLen = map.containsKey(Long.valueOf(nums[i])+1L)?map.get(Long.valueOf(nums[i])+1L):0L;
                    Long all = posLen + preLen + 1;
                    map.put(Long.valueOf(nums[i]) - preLen, all);
                    map.put(Long.valueOf(nums[i]) + posLen, all);
                    len = Math.max(len, all);
                }
            }
            return len.intValue();
        }

    }
//leetcode submit region end(Prohibit modification and deletion)


    public static void main(String[] args) {
        int [] nums = {100,4,200,1,3,2};
//        int [] nums = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        final long i = Solution.longestConsecutive(nums);

        System.out.println(i);
    }
}
