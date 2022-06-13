package leetcode.jzhoffer;


public class JZ37数字在排序数组中出现的次数 {
    /*

    题目描述
统计一个数字在排序数组中出现的次数。
     */

    /**
     * 暴力法
     * 时间复杂度 O(n)
     * 空间复杂度 O(1)
     */
    public class Solution {
        public int GetNumberOfK(int [] array , int k) {
            if(array == null || array.length == 0){
                return 0;
            }
            int count = 0;
            for (int num : array) {
                if(num == k){
                    count++;
                }
            }
            return count;
        }
    }

    /**
     * 二分查找法
     * 时间复杂度O(logn)
     * 空间复杂度O(1)
     * */
    static class Solution2{
        public int GetNumberOfK(int []array, int k){
            if(array == null || array.length == 0){
                return 0;
            }
            int i = 0, j = array.length-1;
            //搜索右边界
            while (i <= j){
                int mid = i + ((j-i+1)>>1);
                if(array[mid] <= k){
                    i = mid+1;
                }else{
                    j = mid-1;
                }
            }
            int right = i;
            //若数组中无target，则提前返回
            if(j >= 0 && array[j] != k){
                return 0;
            }
            //搜索左边界
            i = 0;
            j = array.length-1;
            while (i <= j){
                int mid = i + ((j-i+1)>>1);
                if(array[mid] < k){
                    i = mid + 1;
                }else{
                    j = mid - 1;
                }
            }
            int left = j;

            return right - left - 1;
        }
    }

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        int i = solution2.GetNumberOfK(new int[]{1, 2, 3, 4, 4}, 4);
        System.out.println(i);
    }
}
