package leetcode.arrays;
//Given a sorted integer array where the range of elements are in the inclusive range [lower, upper],
// return its missing ranges.
//
//For example, given [0, 1, 3, 50, 75], lower = 0 and upper = 99,
// return ["2", "4->49", "51->74", "76->99"].

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-10-01.
 */
public class _163_缺失区间{
    class Solution{
        public List<String> findMissingRanges(int[] nums, int lower, int upper) {
            List<String> res = new ArrayList<>();
            long start = lower;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] > start) {
                    if (start == nums[i]-1) {
                        res.add("" + start);
                    }
                    else if (start < nums[i] - 1) {
                        res.add("" + start + "->" + (nums[i] - 1));
                    }
                }
                start = (long) nums[i] + 1L;
            }
            if (start == upper) {
                res.add("" + start);
            }
            else if (start < upper) {
                res.add ("" + start + "->" + upper);
            }
            return res;
        }
    }

    @Test
    public void test(){
        int[] arr = {0, 1, 3, 50, 75};
        Solution solution = new Solution();
        Assert.assertArrayEquals(new String[]{"2", "4->49", "51->74", "76->99"},
                solution.findMissingRanges(arr, 0, 99).toArray(new String[4]));
    }
}
