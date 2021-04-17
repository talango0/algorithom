package leetcode.arrays;

import java.util.*;

/**
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0?
 * Find all unique triplets in the array which gives the sum of zero.
 *
 * Note:
 *
 * The solution set must not contain duplicate triplets.
 *
 * Example:
 *
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 *
 * A solution set is:
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 *-1,0,1,2,-1,-4
 * -2,0,1,1,2
 * [-1,1,-1,1]
 */
public class ThreeSum {
    public static void main(String[] args) {
      int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
//        Arrays.sort(nums);
//        System.out.println(Arrays.toString(nums));
        Solution solution = new Solution();
        List<List<Integer>> lists = solution.threeSum(nums);
        System.out.println(lists.toString());
    }
}
class Solution1 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Set<String> set = new HashSet<String>();
        Arrays.sort(nums);
        for (int i=0;i<nums.length-2;i++){
            for (int j = i+1;j<nums.length-1;j++){
                int p = j;
                int q = nums.length;
                int k = (p+q)/2;
                while (k>p  && k<q){

                    int sum = nums[i] + nums[j] + nums[k];
                    if (sum>0){
                        q = k;
                        k = (k + p)/2;
                    }else if(sum<0){
                        p = k;
                        k = (q + k)/2;
                    }else{
                        addItem(result, set, nums[i], nums[j], nums[k]);
                        break;
                    }
                }
            }
        }
        return result;
    }

    private void addItem(List<List<Integer>> result, Set<String> set, int num, int num1, int num2) {
        List<Integer> subList;
        subList = new ArrayList<Integer>();
        subList.add(num);
        subList.add(num1);
        subList.add(num2);
        if (set.contains(subList.toString())) {

        } else {
            result.add(subList);
            set.add(subList.toString());
        }
        return;
    }

}

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> subList;
        Arrays.sort(nums);
        for (int i = 0;i<nums.length;i++){
            if(i ==0 || nums[i]>nums[i-1]){
                int l = i+1;
                int r = nums.length-1;
                while (l<r){
                    int sum = nums[i] + nums[l] + nums[r];
                    if(sum == 0){
                        subList = new ArrayList<Integer>();
                        subList.add(nums[i]);
                        subList.add(nums[l]);
                        subList.add(nums[r]);
                        result.add(subList);
                        l += 1;
                        r -= 1;
                        while (l<r && nums[l]==nums[l-1]){
                            l++;
                        }
                        while (r>l && nums[r] == nums[r+1]){
                            r--;
                        }
                    }else if(sum<0){
                        l++;
                    }else{
                        r--;
                    }
                }
            }
        }
        return result;
    }
}