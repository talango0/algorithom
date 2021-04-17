package leetcode.jzhoffer;

import java.util.*;
import java.util.stream.Collectors;

public class JZ32把数组排成最小的数 {
    /*
    题目描述
输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
     */

    public static class Solution {
        public String PrintMinNumber(int [] numbers) {
            List<Integer> numList = Arrays.stream(numbers).boxed().collect(Collectors.toList());
            Collections.sort(numList, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return (String.valueOf(o1) + o2).compareTo(String.valueOf(o2)+o1);
                }
            });
            String res ="";
            for (Integer integer : numList) {

                res+= String.valueOf(integer);
            }

            return res;
        }
    }

    public static void main(String[] args) {
        int [] a = {3,32,321};
        Solution solution = new Solution();
        String s = solution.PrintMinNumber(a);



        System.out.println(s);



    }
}
