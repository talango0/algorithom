package leetcode.jzhoffer;

import java.util.ArrayList;

/**
 * 题目描述
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
 * 所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 */
public class JZ13调整数组顺序使奇数位于偶数前面 {
    public class Solution {
        public void reOrderArray(int [] array) {
            if(array == null && array.length == 0){
                return;
            }
            ArrayList<Integer> oddList = new ArrayList<>();
            ArrayList<Integer> evenList = new ArrayList<>();
            for(int i =0 ;i<array.length; i++){
                if(array[i] % 2 == 0){
                    evenList.add(array[i]);
                }else{
                    oddList.add(array[i]);
                }
            }
            if(oddList.size()>0){
                for(int i = 0; i< oddList.size(); i++){
                    array[i] = (int) oddList.get(i);
                }
            }
            if(evenList.size()>0){
                int k = 0;
                if(oddList.size() != 0){
                    k = oddList.size();
                }
                for(int j=0;j<evenList.size();j++){
                    array[j+k] = (int) evenList.get(j);
                }
            }
        }
    }


    /**
     * [1,2,3,4,2]
     */
    static class Solution2{
        public void reOrderArray(int [] array) {
            int length = array.length;
            int odd = 0;
            int even = 0;                           //even用于遍历数组使用，odd用于定位
            while (even < length){
                if (array[even]%2 != 0){
                    int temp = array[even];
                    for (int i = even; i>odd; i--){
                        array[i] = array[i-1];
                    }
                    array[odd] = temp;
                    odd++;
                    even++;
                }else {
                    even++;
                }
            }
        }
    }
}

