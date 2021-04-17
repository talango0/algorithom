package leetcode.jzhoffer;

import java.util.*;

public class JZ数组中只出现一次的数字 {
    /**
     * 题目描述
     * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
     */

    //num1,num2分别为长度为1的数组。传出参数
    //将num1[0],num2[0]设置为返回结果
    public static class Solution {
        public void FindNumsAppearOnce( int[] array, int num1[], int num2[] ) {
            if (array == null || array.length == 0) {
                return;
            }
            if (array.length == 1) {
                num1[0] = array[0];
            }
            HashMap<Integer, Integer> countMap = new HashMap<>();

            for (int i = 0; i < array.length; i++) {
                if (countMap.keySet().contains(array[i])) {
                    Integer count = countMap.get(array[i]);
                    countMap.put(array[i], ++count);
                } else {
                    countMap.put(array[i], 1);
                }
            }
            Set<Map.Entry<Integer, Integer>> entries = countMap.entrySet();
            boolean first = true;
            for (Map.Entry<Integer, Integer> entry : entries) {
                if (entry.getValue() == 1) {
                    if (first) {
                        num1[0] = entry.getKey();
                        first = false;
                    } else {
                        num2[0] = entry.getKey();
                    }
                }
            }
        }
    }


    /**
     * 采用异或的思路：两个相同的数异或的结果为0
     * 所以，如果一组数组中除了一个数出现1次，其他的数字均出现了两次，这种情况直接对所有数求异或就可以得到这个数字。
     * <p>
     * 这道题目中出现两个出现一次的数字，其他的数字均出现了2次。如果能将这一组数字拆分成为两组，每一组中只出现1个出现一次数字。
     * 那么可以按照上面的结论找出每组的这个数。
     * <p>
     * 对这组输入的数从左到有一次遍历求异或，最终得到的结果就是这两个不同的数字的异或值，因为其他数字都出现了两次都已经被抵消掉了。
     * 由于这个两个数字肯定不一样，所以，异或的结果肯定不为0，也就是说在这个结果数字的二进制表示中至少就有1为1，我们在结果数字中
     * 找到第一个为1的位的位置，即为第N位，以第N位是不是1位标准把原数组中的数字拆分成两个子数组，第一个数组中每个数字第N位都为1，
     * 而第二个子数组的每个数字的第N位都为0。
     * <p>
     * 这样，就把原来的数字分成了两组，每个子数组都包含一个只出现一次的数字，而其他数字都出现了两次。
     * <p>
     * 例如：
     * 01 10 11 11 100 100  异或结果：11
     * <p>
     * 分组：
     * 01 11 11 异或 num1=01
     * 10 100 100 异或 num2=10.
     * 成功找到num1和num2.
     * <p>
     * 求最低位1：
     * int get_first_bit(int num){
     * return num &~(num - 1);
     * }
     */
    static class Solution2 {
        public void FindNumsAppearOnce( int[] array, int num1[], int num2[] ) {
            if (array == null || array.length < 2) {
                return;
            }
            int N = array.length;
            //异或所有数获取两个不同数的异或结果。
            int xorResult = 0;
            for (int i = 0; i < N; i++) {
                xorResult ^= array[i];
            }
            //找到低位到高位第一个为1的index
            int indexOf1 = findIndexOf1(xorResult);

            //将arr分为两组分别计算各自出现次数为1的元素
            num1[0] = num2[0] = 0;
            for (int i = 0; i < N; i++) {
                if (isBit1(array[i], indexOf1)) {
                    num1[0] ^= array[i];
                } else {
                    num2[0] ^= array[i];
                }
            }
        }

        private boolean isBit1( int num, int indexOf1 ) {
            num = num >> indexOf1;
            return ( num & 1 ) == 1;
        }

        private int findIndexOf1( int num ) {
            int indexBit = 0;
            while (( ( num & 1 ) == 0 ) && ( indexBit < 32 )) {
                num = num >> 1;
                ++indexBit;
            }
            return indexBit;
        }
    }


    public static void main( String[] args ) {
        int[] num = { 1, 2, 3, 4, 3, 4 };
        Solution solution = new Solution();
        int[] num1 = new int[1];
        int[] num2 = new int[1];
        solution.FindNumsAppearOnce(num, num1, num2);
        System.out.println("solution1");
        System.out.println(Arrays.toString(num1));
        System.out.println(Arrays.toString(num2));


        Solution2 solution2 = new Solution2();
        num1 = new int[1];
        num2 = new int[1];
        solution2.FindNumsAppearOnce(num, num1, num2);
        System.out.println("solution2");
        System.out.println(Arrays.toString(num1));
        System.out.println(Arrays.toString(num2));
    }

}
