package leetcode;

import sun.plugin2.message.CookieReplyMessage;

import java.util.Arrays;

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
// 👍 258 👎 0


    //leetcode submit region begin(Prohibit modification and deletion)
    static class Solution {
        static int res = 0;
        public static int reversePairs(int[] nums) {
            if(nums == null|| nums.length ==0){
                return 0;
            }
            reversePairs(nums,0, nums.length-1);
            return res;
        }

        private static void reversePairs(int[] nums, int L, int R) {
            if(L == R){
                return;
            }
            int M = L+((R-L)>>1);
            reversePairs(nums, L, M);
            reversePairs(nums, M+1, R);
            merge(nums, L, M, R);
        }

        private static void merge(int[] nums, int l, int m, int r) {
            int p1 = l;
            int p2 = m+1;
            int mid = m;
            int [] tmp = new int[r-l+1];
            int i =0;
            while (p1<=m && p2<= r){
                if(nums[p1] > nums[p2]){
                    res += (m-p1+1);
                    tmp[i++] = nums[p2++];
                }else{
                    tmp[i++] = nums[p1++];
                }
            }
            while (p1<= m){
                tmp[i++] = nums[p1++];
            }
            while (p2<=r){
                tmp[i++] = nums[p2++];
            }
            for(int j =0;j< tmp.length; ){
                nums[l++] = tmp[j++];
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
//        int [] nums = {7,5,6,4};
        int [] nums = {1,3,2,3,1};
        int i = Solution.reversePairs(nums);
        System.out.println(i);
    }

}



class Reverse{

    public int [] reverse(int [] originArr, int [] reverseArr, int power){

        int [] reverse = copyArray(originArr);
        int[] recordDown = new int[power+1];
        int[] recordUp = new int[power+1];
        process(originArr, 0, originArr.length-1, power, recordDown);
        process(reverse, 0, reverse.length-1, power, recordUp);
        return  ans(recordDown, recordUp,reverseArr, power);
    }

    private void process(int[] originArr, int L, int R, int power, int[] record) {
        if( L == R){
            return;
        }
        int mid = L+(R-L)>>1;
        process(originArr, L, mid, power-1, record);
        process(originArr, mid+1, R, power-1,record);
        record [power] += merge(originArr, L, mid, R);

    }

    private int merge(int[] arr, int l, int m, int r) {
        int [] help = new int[r-l+1];
        int i = 0;
        int p1 = l;
        int p2 = m+1;
        int ans = 0;
        while (p1 <= m && p2 <= r){
            ans += arr[p1] > arr[p2] ? (m-p1+1):0;
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while ( p1 <= m){
            help[i++] = arr[p1++];
        }
        while ( p2 <= r){
            help[i++] = arr[p2++];
        }
        for(i = 0; i< help.length; i++){
            arr[l+i] = help[i];
        }
        return ans;
    }

    private int[] copyArray(int[] originArr) {
        int[] reverse = new int[originArr.length];
        for(int i = 0; i< originArr.length; i++){
            reverse[i] = originArr[originArr.length-i-1];
        }
        return reverse;
    }

    /**
     *
     * @param recordDown
     * @param recordUp
     * @param reverseArr
     * @param power 2^power = reverseArr.length
     * @return
     */
    public int [] ans(int [] recordDown, int [] recordUp, int [] reverseArr, int power){
        int[] ans = new int[reverseArr.length];
        for (int i = 0; i < reverseArr.length; i++) {
            int curPower = reverseArr[i];
            for(int p=1; p<= curPower; p++){
                int tmp = recordDown[p];
                recordDown[p] = recordUp[p];
                recordUp[p] = tmp;
            }
            for(int p =1; p <= power; p++){
                ans[i] += recordDown[p];
            }
        }
        return ans;
    }
}