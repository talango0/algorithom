package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class _136_只出现一次的数字 {
    static class Solution {
        public static int singleNumber(int[] nums) {
            if(nums == null || nums.length==0){
                return Integer.MAX_VALUE;
            }
            HashMap<Integer, Integer> ref = new HashMap<>(nums.length);
            for(int i = 0; i< nums.length; i++){
                ref.put(nums[i],ref.getOrDefault(nums[i], 0)+1);
            }
            Set<Map.Entry<Integer, Integer>> entries = ref.entrySet();
            for (Map.Entry<Integer, Integer> entry : entries) {
                if(entry.getValue() == 1){
                    return entry.getKey().intValue();
                }

            }
            return Integer.MAX_VALUE;
        }
    }

    /**
     * 时间复杂度O（n） 空间复杂度（1）
     */
    class Solution2{
        public int singleNumber(int[] nums) {
            if(nums == null || nums.length==0){
                return Integer.MAX_VALUE;
            }
            int res = nums[0];
            if(nums.length>1){
                for(int i = 1; i< nums.length; i++){
                    res ^= nums[i];
                }
            }
            return res;
        }
    }

    public static void main(String[] args) {
        int [] a = {4,1,2,1,2};
        int i = Solution.singleNumber(a);
        System.out.println(i);
    }
}
