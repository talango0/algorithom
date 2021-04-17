package leetcode.backtracing;

import java.util.ArrayList;
import java.util.List;

public class BackTracing {

    /**
     * 全排列
     */
    static class Solution1 {
        private List<List<Integer>> res = new ArrayList<>();
        private List<Integer> tmp = new ArrayList<>();

        public List<List<Integer>> permute(int[] nums) {
            backTracing(nums, 0, nums.length);
            return res;
        }
        public void backTracing(int [] nums, int cur, int n){
            if(cur == n){
                tmp = new ArrayList<>();
                for (int num : nums) {
                    tmp.add(num);
                }
                res.add(tmp);
                return;
            }

            for(int i = cur; i < n ; i++){
                swap(nums, cur, i);
                backTracing(nums, cur+1, n);
                swap(nums, cur, i);
            }

        }
        public void swap(int [] nums, int i, int j){
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }
    public static void solution1main(String[] args) {
        int []nums={1,2,3};
        Solution1 solution = new Solution1();
        List<List<Integer>> permute = solution.permute(nums);
        System.out.println(permute.toString());
    }

    /**
     * 组合
     */
    static class Solution2 {
        private List<List<Integer>> res = new ArrayList<>();
        private List<Integer> tmp = new ArrayList<>();

        public List<List<Integer>> combine(int n, int k){
            backTracing(n, 1, k);
            return res;
        }
        public void backTracing(int n, int cur, int k){
            if(tmp.size() + (n-cur+1) < k){
                return;
            }
            if(tmp.size() == k){
                res.add(new ArrayList<>(tmp));
                return;
            }
            tmp.add(cur);
            backTracing(n, cur+1, k);
            tmp.remove(tmp.size()-1);
            backTracing(n, cur+1, k);
        }
    }
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        List<List<Integer>> permute = solution.combine(3,2);
        System.out.println(permute.toString());
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

}
