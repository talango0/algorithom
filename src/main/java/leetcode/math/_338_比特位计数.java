package leetcode.math;
//给你一个整数 n ，对于0 <= i <= n 中的每个 i ，计算其二进制表示中 1 的个数 ，返回一个长度为 n + 1 的数组 ans 作为答案。
//
//
//
//示例 1：
//
//输入：n = 2
//输出：[0,1,1]
//解释：
//0 --> 0
//1 --> 1
//2 --> 10
//示例 2：
//
//输入：n = 5
//输出：[0,1,1,2,1,2]
//解释：
//0 --> 0
//1 --> 1
//2 --> 10
//3 --> 11
//4 --> 100
//5 --> 101
//
//
//提示：
//
//0 <= n <= 105
//
//
//进阶：
//
//很容易就能实现时间复杂度为 O(n log n) 的解决方案，你可以在线性时间复杂度 O(n) 内用一趟扫描解决此问题吗？
//你能不使用任何内置函数解决此问题吗？（如，C++ 中的__builtin_popcount ）


public class _338_比特位计数 {
    /**
     * 时间复杂度 O(nlogn)
     * 空间复杂度 O(1)
     */
    class Solution {
        public int[] countBits(int n) {
            int[] bits = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                bits[i] = countOnes(i);
            }
            return bits;
        }

        public int countOnes(int x) {
            int ones = 0;
            while (x > 0) {
                x &= (x - 1);
                ones++;
            }
            return ones;
        }
    }

    /**
     * 时间复杂度 O(n)
     * 空间复杂度 O(1)
     */
    class Solution2 {
        // 思路：
        // 1.定义正整数 x 的【最次设置为】为x的二进制中的最低的 1 的所在位。例如， 10 的二进制表示 1010(2), 其最低设置位位 2，对应的
        // 二进制表示是 10(2)
        // 2.令 y = x&(x-1) ，则 y 为将 x 的最低设置位从 1 变成 0 之后的数，显然 0 <= y < x,bits[x] = bits[y] + 1。
        // 因此，对于任意正整数 x ,都有 bits[x] = bits[x & (X-1)] + 1,
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
