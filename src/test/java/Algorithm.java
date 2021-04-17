import java.util.ArrayList;
import java.util.List;

public class Algorithm {

    public static int maxSubArray(int[] nums) {
        int sum = Integer.MIN_VALUE;
        int n = nums.length;
        for(int l=1; l<=n; l++){
            for(int i = 0; i<n-l+1; i++){
                int tmp = 0;
                for(int j=i; j<i+l&& j<n; j++){
                    tmp+=nums[j];
                }
                sum = Math.max(sum,tmp);
            }
        }
        return sum;
    }

//    public static void main(String[] args) {

//        int []nums={0,1,2,4,5,6};
//        int missing = nums.length;
//        for (int i = 0; i < nums.length; i++) {
//            missing ^= i ^ nums[i];
//        }
//        System.out.println(missing);
//
//        int [] nums = {-2,1,-3,4,-1,2,1,-5,4};
//        System.out.println( Algorithm.maxSubArray(nums));
//
//    }
//    class Solution {
//        class Status {
//            public int lSum, rSum, mSum, iSum;
//            public Status(int lSum_, int rSum_, int mSum_, int iSum_) {
//                lSum = lSum_; rSum = rSum_; mSum = mSum_; iSum = iSum_;
//            }
//        }
//
//        public Status pushUp(Status l, Status r) {
//            int iSum = l.iSum + r.iSum;
//            int lSum = Math.max(l.lSum, l.iSum + r.lSum);
//            int rSum = Math.max(r.rSum, r.iSum + l.rSum);
//            int mSum = Math.max(Math.max(l.mSum, r.mSum), l.rSum + r.lSum);
//            return new Solution.Status(lSum, rSum, mSum, iSum);
//        }
//
//        public Status getInfo(int[] a, int l, int r) {
//            if (l == r) return new Solution.Status(a[l], a[l], a[l], a[l]);
//            int m = (l + r) >> 1;
//            Status lSub = getInfo(a, l, m);
//            Status rSub = getInfo(a, m + 1, r);
//            return pushUp(lSub, rSub);
//        }
//
//        public int MaxSubArray(int[] nums) {
//            return getInfo(nums, 0, nums.length - 1).mSum;
//        }
//    }

    /**
     * 全排列
     */
//    static class Solution {
//        private List<List<Integer>> res = new ArrayList<>();
//        private List<Integer> tmp = new ArrayList<>();
//
//        public List<List<Integer>> permute(int[] nums) {
//            backTracing(nums, 0, nums.length);
//            return res;
//        }
//        public void backTracing(int [] nums, int cur, int n){
//            if(cur == n){
//                tmp = new ArrayList<>();
//                for (int num : nums) {
//                    tmp.add(num);
//                }
//                res.add(tmp);
//                return;
//            }
//
//            for(int i = cur; i < n ; i++){
//                swap(nums, cur, i);
//                backTracing(nums, cur+1, n);
//                swap(nums, cur, i);
//            }
//
//        }
//        public void swap(int [] nums, int i, int j){
//            int tmp = nums[i];
//            nums[i] = nums[j];
//            nums[j] = tmp;
//        }
//    }
//    public static void main(String[] args) {
//        int []nums={1,2,3};
//        Solution solution = new Solution();
//        List<List<Integer>> permute = solution.permute(nums);
//        System.out.println(permute.toString());
//
//    }

    /**
     * 组合
     */
    static class Solution {
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
        Solution solution = new Solution();
        List<List<Integer>> permute = solution.combine(3,2);
        System.out.println(permute.toString());

    }

}


