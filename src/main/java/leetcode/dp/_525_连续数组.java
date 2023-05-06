package leetcode.dp;

import java.util.HashMap;
import java.util.Map;

//给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
//
//
//
//示例 1:
//
//输入: nums = [0,1]
//输出: 2
//说明: [0, 1] 是具有相同数量 0 和 1 的最长连续子数组。
//示例 2:
//
//输入: nums = [0,1,0]
//输出: 2
//说明: [0, 1] (或 [1, 0]) 是具有相同数量0和1的最长连续子数组。
//
//
//提示：
//
//1 <= nums.length <= 105
//nums[i] 不是 0 就是 1
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/contiguous-array
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
public class _525_连续数组 {
    class Solution {
        public int findMaxLength(int[] nums) {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0, -1);
            int preSum = 0;
            int len = 0;

            for (int i = 0; i < nums.length; i++) {

                if (nums[i] == 0) {
                    preSum--;
                } else {
                    preSum++;
                }

                Integer j = map.get(preSum);
                if (j != null) {
                    len = Math.max(len, i - j);
                } else {
                    map.put(preSum, i);
                }
            }

            return len;
        }
    }

    class Solution2 {

        //由于求的是0和1个数相等的最长连续子数组，我们把数组中的0看成-1，题目就转化成了和为0的最长子数组
        public int findMaxLength(int[] nums) {
            int n = nums.length;
            int [] preSum = new int[n+1];
            for (int i = 0; i<n; i++) {
                preSum[i+1] = preSum[i] + ((nums[i] == 0)?-1:1);
            }
            int ans = 0;
            Map<Integer, Integer> map  = new HashMap<>();
            for(int i = 1; i <= n; i++){
                if (map.containsKey(preSum[i])){
                    ans = Math.max(ans, i - map.get(preSum[i]));
                }else{
                    map.put(preSum[i],i);
                }
                if(preSum[i] == 0){
                    ans = Math.max(ans, i);
                    map.put(preSum[i],i);
                }

            }
            return ans;
        }
    }
}
