package leetcode.mergesort;

public class 剑指Offer51数组中的逆序对 {

    //在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。
//
//
//
// 示例 1:
//
// 输入: [7,5,6,4]
//输出: 5
//
//
//
// 限制：
//
// 0 <= 数组长度 <= 50000
// 👍 257 👎 0


    //leetcode submit region begin(Prohibit modification and deletion)
    static class Solution {
        /**
         * 0,1,2,3
         * 3/2=1
         * [7, 5, 6, 4]
         * 归并排序，然后统计逆序对。
         * 分
         * [7, 5]; [6, 4]
         * [7], [5]; [6], [4]
         * 治
         * [5, 7]; [4, 6]
         * [4，5，6，7]
         */
        Integer ans = 0;
        public int reversePairs(int[] nums) {
            if(nums == null || nums.length == 0){
                return ans.intValue();
            }
            mergeSortAndCalAns(nums, 0, nums.length-1, new int[nums.length]);
            return ans.intValue();
        }
        public void mergeSortAndCalAns(int [] nums, int start, int end, int [] c){

            if(start < end){
                int mid = start+((end-start)>>1);
                mergeSortAndCalAns(nums, start, mid, c);
                mergeSortAndCalAns(nums, mid+1, end, c);
                merge(nums, start, mid, end, c);
            }
        }
        public void merge(int [] nums, int start, int mid, int end, int [] c){
            int i = start, j=mid+1, k=start;
            while (i <= mid && j <= end){
                if(nums[i] <= nums[j]){
                    c[k++] = nums[i++];
                }else {
                    ans += (mid-i+1);
                    c[k++] = nums[j++];
                }
            }
            while (i <= mid){
                c[k++] = nums[i++];
            }
            while (j <= end){
                c[k++] = nums[j++];
            }
            for(i = start; i<= end; i++){
                nums[i] = c[i];
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)


    public static void main(String[] args) {
        final Solution solution = new Solution();
        int [] arr ={7, 5, 6, 4};
        final int ans = solution.reversePairs(arr);
        System.out.println(ans);
    }
}
