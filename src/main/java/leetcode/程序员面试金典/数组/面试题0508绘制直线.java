package leetcode.程序员面试金典.数组;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author mayanwei
 */
public class 面试题0508绘制直线 {


    static class Solution {
        public int[] drawLine(int length, int w, int x1, int x2, int y) {
            int[] ret = new int[length];
            // 注意根据所在行数计算偏移量
            int offset = y * w / 32;
            // 首位数字下标
            int head = x1 / 32 + offset;
            // 末位数字下标
            int rear = x2 / 32 + offset;
            // 把涉及到的数全部置 -1 也就是 0b11111111111111111111111111111111
            for (int i = head; i <= rear; i++) {
                ret[i] = -1;
            }
            // 调整首位数字
            ret[head] = ret[head] & -1 >>> x1 % 32;
            // 调整末位数字
            ret[rear] = ret[rear] & Integer.MIN_VALUE >> x2 % 32;
            return ret;
        }
    }



    public static void main(String[] args) {
//        int i = new BigInteger("11111111111111111111111111111111", 2).intValue();
        Solution solution = new Solution();
        int[] ints = solution.drawLine(3, 96, 25, 95, 0);
        System.out.println(Arrays.toString(ints));
//        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));

    }


}
