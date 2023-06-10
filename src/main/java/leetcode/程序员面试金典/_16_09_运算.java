package leetcode.程序员面试金典;
//请实现整数数字的乘法、减法和除法运算，运算结果均为整数数字，程序中只允许使用加法运算符和逻辑运算符，
// 允许程序中出现正负常数，不允许使用位运算。
//
//你的实现应该支持如下操作：
//
//Operations() 构造函数
//minus(a, b) 减法，返回a - b
//multiply(a, b) 乘法，返回a * b
//divide(a, b) 除法，返回a / b
//示例：
//
//Operations operations = new Operations();
//operations.minus(1, 2); //返回-1
//operations.multiply(3, 4); //返回12
//operations.divide(5, -2); //返回-2
//提示：
//
//你可以假设函数输入一定是有效的，例如不会出现除法分母为0的情况
//单个用例的函数调用次数不会超过1000次
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/operations-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


import org.apache.zookeeper.Op;
import org.junit.jupiter.api.Test;

/**
 * @author mayanwei
 * @date 2023-06-09.
 */
public class _16_09_运算{
    class Operations{

        public Operations() {

        }

        /**
         * 正常 a-b = a +(-1)*b 但是由于不能使用乘法，因此必须自己实现取负（negate）函数
         *
         * @param a
         * @param b
         * @return
         */
        public int minus(int a, int b) {
            return a + negate(b);
        }

        // 将正号翻转为负号，或将负号翻转为正号
        private int negate(int a) {
            int neg = 0;
            int newSign = a < 0 ? 1 :-1;
            // a = -b ==> a+b = 0
            //while (a != 0) {
            //    neg += newSign;
            //    a += newSign;
            //}
            //
            // 这个while的复杂度位 O(K) ,此次优化一下，我们可以时a 更快地接近 0 (假设a是一个整数)
            // a 首先减去1，之后减去2，之后减去4，之后减去8，以此类推。我们可以将词值称为 value。我们
            // 希望精确地减到0，因此，当a减去下一个 delta 后会改变a 的符号时，我们将a重置为1并重复该过程.
            // 下面做法的时间复杂度
            //log(xy) = log x + log y log(x/y) = log x - log y
            //如果我们将这两条定理应用于上述表达式，可以得到如下表达式。
            //(1) O(log a + log( a/2 ) + log( a/4 ) + ...)
            //(2) O(log a + (log a - log 2) + (log a - log 4) + (log a - log 8) + ...
            //(3) O((log a)\*(log a) - (log 2 + log 4 + log 8 + ... + log a)) // 􏳏 O(log a)􏳐 (4) O((log a)\*(log a) - (1 + 2 + 3 + ... + log a)) // 计算对数值
            //(5) O((log a)\*(log a) - (log a)(1 + log a)/2) // 使用 1 至 k 的求和公式
            //(6) O((log a)^2) //第 5 步中消除了第 2 项
            int delta = newSign;
            while (a != 0) {
                boolean differentSings = (a + delta > 0) != (a > 0);
                if (a + delta != 0 && differentSings) {
                    delta = newSign;
                }
                neg += delta;
                a += delta;
                delta += delta; // 将delta放大一倍
            }
            return neg;
        }

        public int multiply(int a, int b) {
            if (a < b) {
                return multiply(b, a); // 如果 b < a， 则算法更快
            }
            int sum = 0;
            for (int i = abs(b); i > 0; i = minus(i, 1)) {
                sum += a;
            }
            if (b < 0) {
                sum = negate(sum);
            }
            return sum;
        }

        private int abs(int num) {
            if (num < 0) {
                return negate(num);
            }
            else {
                return num;
            }
        }

        /**
         * 通过观察方程 a = xb，我们会发现通过不断地将 b 与其自身相加可以得到 a 的值。需要重复的次数即为 x。
         * 当然，a 或许不能被 b 整除，这无关紧要。实现整数除法本来就会截断运算结果
         *
         * @param a
         * @param b
         * @return
         */
        public int divide(int a, int b) {
            if (b == 0) {
                throw new java.lang.ArithmeticException("ERROR");
            }
            int absa = abs(a);
            int absb = abs(b);
            int product = 0;
            int x = 0;
            while (product + absb <= absa) {// 不要超过 a
                product += absb;
                x++;
            }
            if ((a < 0 && b < 0) || (a > 0 && b > 0)) {
                return x;
            }
            return negate(x);
        }
    }

    class Operations2{

        public Operations2() {

        }

        public int minus(int a, int b) {
            return a + (~b + 1);
        }

        public int multiply(int a, int b) {
            int d = b, e = a;
            if (d > 0) b = (~b + 1);
            if (e > 0) a = (~a + 1);
            int sum = 0, i = -1;
            while (b < 0) {
                int c = b & (~b + 1);
                if (c > 0) c = ~c + 1;
                while (i > c) {
                    i += i;
                    a += a;
                }
                sum += a;
                b &= b + (~1 + 1);
            }
            if ((d > 0 && e < 0) || (d < 0 && e > 0)) {
                return ~sum + 1;
            }
            return sum;

        }

        public int divide(int a, int b) {
            int MIN = Integer.MIN_VALUE, MAX = Integer.MAX_VALUE;
            int MIN_LIMIT = MIN >> 1; // -1073741824
            if (a == MIN && b == -1) return MAX;
            boolean flag = (a < 0 && b > 0) || (a > 0 && b < 0) ? false :true;
            if (a > 0) a = (~a + 1);
            if (b > 0) b = (~b + 1);
            int res = 0;
            while (a <= b) {
                int x = b, y = 1;
                while (x >= MIN_LIMIT && x + x >= a) {
                    x += x;
                    y += y;
                }
                a -= x;
                res += y;
            }
            return flag ? res :-res;
        }
    }

/**
 * Your Operations object will be instantiated and called as such:
 * Operations obj = new Operations();
 * int param_1 = obj.minus(a,b);
 * int param_2 = obj.multiply(a,b);
 * int param_3 = obj.divide(a,b);
 */

    /**
     * Your Operations object will be instantiated and called as such:
     * Operations obj = new Operations();
     * int param_1 = obj.minus(a,b);
     * int param_2 = obj.multiply(a,b);
     * int param_3 = obj.divide(a,b);
     */
    @Test
    public void test() {
        Operations operations = new Operations();
        int minus = operations.negate(-6);
        System.out.println(minus);
    }
}
