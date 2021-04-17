package leetcode.jzhoffer;

import java.util.HashMap;

/**
 * @author mayanwei
 */
public class JZ28数组中出现次数超过一半的数字 {
    /*
    题目描述
数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
     */

    public class Solution {
        public int MoreThanHalfNum_Solution(int [] array) {
            if(array == null){
                return 0;
            }
            int length = array.length;
            HashMap<Integer, Integer> refMap = new HashMap<>(length);
            for (int num : array) {
                if(refMap.containsKey(num)){
                    Integer count = refMap.get(num);
                    if(count+1> length/2){
                        return num;
                    }else {
                        refMap.put(num,count+1);
                    }
                }else {
                    refMap.put(num, 1);
                }
            }

            return 0;
        }
    }
}
