package leetcode.math;
//你的任务是计算 aᵇ 对 1337 取模，a 是一个正整数，b 是一个非常大的正整数且会以数组形式给出。
//
//
//
// 示例 1：
//
//
//输入：a = 2, b = [3]
//输出：8
//
//
// 示例 2：
//
//
//输入：a = 2, b = [1,0]
//输出：1024
//
//
// 示例 3：
//
//
//输入：a = 1, b = [4,3,3,8,5,2]
//输出：1
//
//
// 示例 4：
//
//
//输入：a = 2147483647, b = [2,0,0]
//输出：1198
//
//
//
//
// 提示：
//
//
// 1 <= a <= 2³¹ - 1
// 1 <= b.length <= 2000
// 0 <= b[i] <= 9
// b 不含前导 0
//
//
// Related Topics 数学 分治 👍 276 👎 0

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-08-06.
 * @see _50_Pow_x_n
 */
public class _372_超级次方{
    class Solution{
        // 这个算法其实就是广泛应用于离散数学的模幂算法
        // 1. 如何处理用数组表示的指数
        // 2. 如何得到求模之后的结果
        // 3. 如何高效进行幂运算
        public int superPow(int a, int[] b) {
            List _b = new ArrayList<>(b.length);
            for (int item : b) {
                _b.add(item);
            }
            return superPow(a, _b);

        }

        int superPow(int a, List<Integer> b) {
            // 递归的base case
            if (b.isEmpty()) {
                return 1;
            }
            // 取出最后一个数
            int last = b.remove(b.size() - 1);
            // 将原问题化简，缩小规模递归求解
            int part1 = mypow(a, last);
            int part2 = mypow(superPow(a, b), 10);
            // 合并结果
            // 每次乘法都要求求模
            return (part1 * part2) % base;

        }

        int base = 1337;
        //// 计算 a 的 k 次方，然后与 base 求模的结果
        //int mypow(int a, int k){
        //    a %= base;
        //    int res = 1;
        //    for (int i = 0; i<k; i++) {
        //        // 这里有乘法是潜在的溢出点
        //        res *= a;
        //        // 对乘法求模
        //        res %= base;
        //    }
        //    return res;
        //}

        int mypow(int a, int k) {
            if (k == 0) {
                return 1;
            }
            a %= base;
            if (k % 2 == 1) {
                // k 是奇数
                return (a * mypow(a, k - 1)) % base;
            }
            else {
                // k 是偶数
                int sub = mypow(a, k / 2);
                return (sub * sub) % base;
            }
        }
    }
}
