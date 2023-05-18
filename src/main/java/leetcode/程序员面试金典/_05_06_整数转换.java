package leetcode.程序员面试金典;
//整数转换。编写一个函数，确定需要改变几个位才能将整数A转成整数B。
//
//示例1:
//
// 输入：A = 29 （或者0b11101）, B = 15（或者0b01111）
// 输出：2
//示例2:
//
// 输入：A = 1，B = 2
// 输出：2
//提示:
//
//A，B范围在[-2147483648, 2147483647]之间
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/convert-integer-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2023-05-18.
 */
public class _05_06_整数转换{

    class Solution {
        public int convertInteger(int A, int B) {
            int z = A ^ B;
            int count = 0;
            while (z != 0) {
                if ((z & 1) == 1) {
                    count++;
                }
                z >>>= 1;
            }
            return count;
        }
    }

    //另一种写法
    class Solution1 {
        public int convertInteger(int A, int B) {
            int z = A ^ B;
            int count = 0;

            for (int i = 0; i < Integer.SIZE; i++) {
                if ((z & (1 << i)) != 0) {
                    count++;
                }
            }
            return count;
        }
    }

    /**
     * 上面的做法是不断对 z 进行移位操作，然后检查最低有效位。但其实可以不断翻转最低有效位，计算要多少次 c 才会变成0.
     * 操作 c = c & (c-1) 会清除 c 的最低有效位。
     */
    class Solution3 {
        public int convertInteger(int A, int B) {
            int count = 0;
            for (int c = A^B; c!=0; c = c&(c-1)) {
                count++;
            }
            return count;
        }
    }


}
