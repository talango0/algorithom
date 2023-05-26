package leetcode.程序员面试金典;
//递归乘法。 写一个递归函数，不使用 * 运算符， 实现两个正整数的相乘。可以使用加号、减号、位移，但要吝啬一些。
//
//示例1:
//
// 输入：A = 1, B = 10
// 输出：10
//示例2:
//
// 输入：A = 3, B = 4
// 输出：12
//提示:
//
//保证乘法范围不会溢出
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/recursive-mulitply-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
/**
 * @author mayanwei
 * @date 2023-05-26.
 */
public class _08_05_递归乘法{
    class Solution {
        public int multiply(int A, int B) {
            int bigger = A < B ? A : B;
            int smaller = A < B ? B : A;
            return multiplyHelper(smaller, bigger);
        }
        private int multiplyHelper(int smaller, int bigger) {
            // 0 * bigger = 0
            if (smaller == 0) {
                return 0;
            }
            // 1 * bigger = bigger
            else if (smaller == 1) {
                return bigger;
            }
            // 数到一半，如果数偶数就加倍，否则就继续数另一半。
            int s = smaller >> 1;
            int side1 = multiplyHelper(s, bigger);
            if ((smaller & 1) == 1) {
                int side2 = multiplyHelper(smaller - s, bigger);
                return side1 + side2;
            } else {
                return side1 << 1;
            }
        }
    }

    /**
     * 不难看出上述的递归算法有许多重复的部分
     * multiply(17, 23)
     *   multiply(8, 23)
     *      multiply(4, 23) * 2
     *          ...
     * + multiply(9, 23)
     *      multiply(4, 23)
     *          ...
     *    + multiply(5, 23)
     *          ...
     */
    class Solution1 {
        int [][] memo;
        public int multiply(int A, int B) {
            int bigger = A < B ? A : B;
            int smaller = A < B ? B : A;
            // 可能会超出内存限制
            memo = new int[smaller+1][bigger+1];
            return multiplyHelper(smaller, bigger);
        }
        private int multiplyHelper(int smaller, int bigger) {
            // 0 * bigger = 0
            if (smaller == 0) {
                return 0;
            }
            // 1 * bigger = bigger
            else if (smaller == 1) {
                return bigger;
            }
            if (memo[smaller][bigger] > 0) {
                return memo[smaller][bigger];
            }
            // 数到一半，如果数偶数就加倍，否则就继续数另一半。
            int s = smaller >> 1;
            int side1 = multiplyHelper(s, bigger);
            if ((smaller & 1) == 1) {
                int side2 = multiplyHelper(smaller - s, bigger);
                side1 += side2;
            } else {
                side1 <<= 1;
            }
            memo[smaller][bigger] = side1;
            return side1;
        }
    }

    /**
     * 上面的优化可能存在 memo 溢出的风险。因此还需要继续优化
     * multiply(31, 35) = 2 * multiply(15, 35) + 35
     * 31 = 2*15+1
     * 当smaller 为偶数是，只需要除以 2 再把递归调用的结果加倍。当smaller是奇数时，依然那么做，但要把 bigger 加到结果上。
     * 这样做，下面的递归就不会在出现重复调用，也就不需要缓存任何信息。
     */
    class Solution3 {
        public int multiply(int A, int B) {
            int bigger = A < B ? A : B;
            int smaller = A < B ? B : A;
            return multiplyHelper(smaller, bigger);
        }
        private int multiplyHelper(int smaller, int bigger) {
            if (smaller == 0) {
                return 0;
            }
            if (smaller == 1) {
                return bigger;
            }
            int s = smaller >> 1;
            int half = multiplyHelper(s, bigger);
            if ((smaller & 1)== 0) {
                return  half << 1;
            } else {
                return (half << 1) + bigger;
            }
        }
    }
}
