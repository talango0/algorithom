package leetcode.程序员面试金典;
//设计一个函数把两个数字相加。不得使用 + 或者其他算术运算符。
//
//示例:
//
//输入: a = 1, b = 1
//输出: 2
//
//
//提示：
//
//a,b均可能是负数或 0
//结果不会溢出 32 位整数
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/add-without-plus-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.jupiter.api.Test;

/**
 * @author mayanwei
 * @date 2023-06-21.
 */
public class _17_01_不用加号的加法{
    /**
     * <pre>
     * ┌──────────────────────────────────────┐
     * │    759           1001    9+13 = 22   │
     * │   +674           1101                │
     * │ ─────────     ─────────              │
     * │    323           0100  XOR           │
     * │   1110          10010  AND <<1       │
     * │   1333          10110    16+6=22     │
     * └──────────────────────────────────────┘
     * </pre>
     */
    class Solution {
        public int add(int a, int b) {
            if (b == 0 ) {
                return a;
            }
            int sum = a ^ b;
            int carry = (a & b) << 1;
            return add (sum, carry);
        }
    }

    class Solution1 {
        public int add(int a, int b) {
            while (b!=0) {
                int sum = a ^ b; // 两数相加不进位
                int carry = (a & b) << 1; // 进位，但不对两数相加
                a = sum;
                b = carry;
            }
            return a;
        }
    }

    @Test
    public void test() {
        Solution1 solution1 = new Solution1();
        int add = solution1.add(9, 13);
        System.out.println(add);
    }
}
