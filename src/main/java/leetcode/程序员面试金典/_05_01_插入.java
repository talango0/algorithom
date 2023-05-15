package leetcode.程序员面试金典;
//给定两个整型数字 N 与 M，以及表示比特位置的 i 与 j（i <= j，且从 0 位开始计算）。
//
//编写一种方法，使 M 对应的二进制数字插入 N 对应的二进制数字的第 i ~ j 位区域，不足之处用 0 补齐。
//
//题目保证从 i 位到 j 位足以容纳 M， 例如： M = 10011，则 i～j 区域至少可容纳 5 位。
//
//
//
//示例1:
//
// 输入：N = 1024(10000000000), M = 19(10011), i = 2, j = 6
// 输出：N = 1100(10001001100)
//示例2:
//
// 输入： N = 0, M = 31(11111), i = 0, j = 4
// 输出：N = 31(11111)
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/insert-into-bits-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2023-05-15.
 */
public class _05_01_插入{
    /**
     * <pre>
     * ┌───────────────────┐
     * │         j=5   i=1 │
     * │           │   │   │
     * │           ▼   ▼   │
     * │  N = 10001111100  │
     * │  M =        101   │
     * │res = 10001001010  │
     * └───────────────────┘
     * 1. 将N中从j到i之间的位清 0
     * 2. 对M执行移位操作，与j 和 i之间的位对齐
     * 3. 合并M与N
     * </pre>
     *
      */
    class Solution {
        public int insertBits(int N, int M, int i, int j) {
            /*  步骤1最难，可以采用掩码的方式将 N 从i 到 j 之间的位清0。例如 i=2,j=4,结果应位 11100011
            为简便起见，此题目中我们使用8位 */
            int allOns = ~0; // 0的反码
            // j之前是1
            int left = allOns << (j + 1);
            // i之后是1
            int right = ((1 << i) - 1);
            int mask = left | right;

            // N从 i 到 j 清0
            int n_cleaned = N & mask;
            // 将M移到正确的位置
            int m_shifted = M << i;
            return n_cleaned | m_shifted; //或运算，成功
        }
    }

    class Solution0 {
        public int insertBits(int N, int M, int i, int j) {
            for (int k = i; k<= j; k++) {
                N &= ~(1 << k);
            }
            return N | (M << i);
        }
    }
}
