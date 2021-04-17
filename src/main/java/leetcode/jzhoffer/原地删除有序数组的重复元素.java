package leetcode.jzhoffer;

import java.util.Arrays;

public class 原地删除有序数组的重复元素 {
    static class Solution {
        public void removeRepeatItem( int[] num ) {
            if (num == null || num.length < 2) {
                return;
            }
            //j表示处理完成的最后一个数的下标, r表示共有多少重复的元素
            for (int i = 1, j = 0, r = 0; i < num.length - r; i++, j++) {
                int k = 0;
                while (k < num.length - j && num[i + k] == num[j]) {
                    k++;
                }
                r += k;
                //本次遍历，从num[j+1+k] -> num[j+1]
                for (int l = j + 1; l < num.length - j - k; l++) {
                    num[l] = num[l + k];
                }

            }
        }
    }


    public static void main( String[] args ) {


        int[] arr = { 1, 1, 2, 2, 2, 2,3, 4, 5 };
        Solution solution = new Solution( );
        solution.removeRepeatItem(arr);
        System.out.println(Arrays.toString(arr));
    }

}
