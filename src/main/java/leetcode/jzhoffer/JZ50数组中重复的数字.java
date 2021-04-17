package leetcode.jzhoffer;

import java.util.*;

/**
 * @author mayanwei
 */
public class JZ50数组中重复的数字 {

    /**
     * 题目描述
     * 在一个长度为n的数组里的所有数字都在0到n-1的范围内。
     * 数组中某些数字是重复的，但不知道有几个数字是重复的。也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
     * 例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是第一个重复的数字2。
     */


    public class Solution {
        // Parameters:
        //    numbers:     an array of integers
        //    length:      the length of array numbers
        //    duplication: (Output) the duplicated number in the array number,length of duplication array is 1,so using duplication[0] = ? in implementation;
        //                  Here duplication like pointor in C/C++, duplication[0] equal *duplication in C/C++
        //    这里要特别注意~返回任意重复的一个，赋值duplication[0]
        // Return value:       true if the input is valid, and there are some duplications in the array number
        //                     otherwise false
        public boolean duplicate(int numbers[],int length,int [] duplication) {
            boolean isDuplicated = false;
            if(numbers == null || length==0){
                return false;
            }
            LinkedHashMap<Integer,Integer> referMap = new LinkedHashMap<>(length);
            for (int i : numbers) {

                //返回任意一个重复的数字即可
                if(referMap.keySet().contains(i)){
                    duplication[0] =  i;
                    isDuplicated =true;
                    break;
//                    Integer v = referMap.get(i);
//                    referMap.put(i, ++v);
                }else {
                    referMap.put(i, 1);
                }
            }

//            for (Map.Entry<Integer, Integer> entry : referMap.entrySet()) {
//                if(entry.getValue()>1){
//                    duplication[0] = entry.getKey();
//                    isDuplicated = true;
//                    break;
//                }
//            }
            return isDuplicated;
        }
    }

    public static void main(String[] args) {
        int [] a =  new int[10];
        System.out.println();
    }
}
