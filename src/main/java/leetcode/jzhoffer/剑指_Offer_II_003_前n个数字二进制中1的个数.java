package leetcode.jzhoffer;

import leetcode.math._338_比特位计数;

/**
 * @author mayanwei
 * @date 2022-10-29.
 * @see _338_比特位计数
 */
public class 剑指_Offer_II_003_前n个数字二进制中1的个数{
    class Solution {
        // 思路：
        // 1.定义正整数 x 的【最次设置为】为x的二进制中的最低的 1 的所在位。例如， 10 的二进制表示 1010(2), 其最低设置位为 2，对应的
        // 二进制表示是 10(2)
        // 2.令 y = x&(x-1) ，则 y 为将 x 的最低设置位从 1 变成 0 之后的数，显然 0 <= y < x,bits[x] = bits[y] + 1。
        // 因此，对于任意正整数 x ,都有 bits[x] = bits[x & (x-1)] + 1,
        // 遍历从 1 到 n 的每一个正整数 i， 计算bits值，最终得到的数组bits即为正确答案。
        public int[] countBits(int n) {
            int[] bits = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                bits[i] = bits[i & (i - 1)] + 1;
            }
            return bits;
        }
    }
}
