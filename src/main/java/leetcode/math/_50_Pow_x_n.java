package leetcode.math;
//实现pow(x, n)，即计算 x 的整数n 次幂函数（即，xn ）。
//
//
//
//示例 1：
//
//输入：x = 2.00000, n = 10
//输出：1024.00000
//示例 2：
//
//输入：x = 2.10000, n = 3
//输出：9.26100
//示例 3：
//
//输入：x = 2.00000, n = -2
//输出：0.25000
//解释：2-2 = 1/22 = 1/4 = 0.25
//
//
//提示：
//
//-100.0 < x < 100.0
//-231 <= n <= 231-1
//-104 <= xn <= 104
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/powx-n
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


public class _50_Pow_x_n {
    class Solution {
        public double myPow(double x, int n) {
            if (n == 0) {
                return 1;
            }
            if (n == Integer.MIN_VALUE) {
                // 把n是INT_MIN 的情况单独拿出来处理
                // 避免 -n 整形移除
                return myPow(1/x, -(n+1))/x;
            }
            if (n < 0) {
                return myPow(1/x, -n);
            }
            if (n%2 == 1) {
                // n是奇数
                return (x*myPow(x, n-1));
            }
            else {
                // n 是偶数
                double sub = myPow(x, n/2);
                return sub*sub;
            }
        }
    }
}
