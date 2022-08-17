package leetcode.math;
//给你四个整数：n 、a 、b 、c ，请你设计一个算法来找出第 n 个丑数。
//
//丑数是可以被 a 或 b 或 c 整除的 正整数 。
//
//示例 1：
//
//输入：n = 3, a = 2, b = 3, c = 5
//输出：4
//解释：丑数序列为 2, 3, 4, 5, 6, 8, 9, 10... 其中第 3 个是 4。
//示例 2：
//
//输入：n = 4, a = 2, b = 3, c = 4
//输出：6
//解释：丑数序列为 2, 3, 4, 6, 8, 9, 10, 12... 其中第 4 个是 6。
//示例 3：
//
//输入：n = 5, a = 2, b = 11, c = 13
//输出：10
//解释：丑数序列为 2, 4, 6, 8, 10, 11, 12, 13... 其中第 5 个是 10。
//示例 4：
//
//输入：n = 1000000000, a = 2, b = 217983653, c = 336916467
//输出：1999999984
//提示：
//
//1 <= n, a, b, c <= 10^9
//1 <= a * b * c <= 10^18
//本题结果在 [1, 2 * 10^9] 的范围内
//Related Topics
//
//👍 114, 👎 0
/**
 * @author mayanwei
 * @date 2022-08-17.
 */
public class _1201_丑数III{
    /**
     * 超时
     */
    class Solution {
        public int nthUglyNumber(int n, int a, int b, int c) {
            // 可以理解为三个有序链表的头结点的值
            // 由于数据规模较大，所以采用long类型
            long productA = a, productB = b, productC = c;
            // 可以理解为合并之后的有序链表上的指针
            int p = 1;
            long min = -666;

            // 开始合并三个有序链表，获取第 n 个节点的值
            while (p <= n) {
                // 取三个链表的最小值
                min = Math.min(Math.min(productA, productB), productC);
                p ++;
                //前进最小结点对应链表的指针
                if (min == productA) {
                    productA += a;
                }
                if (min == productB) {
                    productB += b;
                }
                if (min == productC) {
                    productC += c;
                }
            }
            return (int)min;
        }
    }

    class Solution1 {
        public int nthUglyNumber(int n, int a, int b, int c) {
            //[1, 2 * 10^9]
            int left = 1, right = (int)2e9;
            while (left <= right) {
                int mid = left + (right-left)/2;
                if (f(mid, a, b, c) < n) {
                    // [1...mid] 中符合条件的不足n，所以目标在右半边
                    left = mid + 1;
                }
                else {
                    // [1...mid] 中符合条件的元素大于等于 n，所以目标在左半边
                    right = mid - 1;
                }
            }
            return left;
        }


        // f(num, a, b, c) 计算[1,...,num]中，能够整除 a,b,c的数字的个数，显然函数 f 的返回值时随着 num 的增加而增加的（单调递增）
        // 题目中让我们就第n个整数能够整除 a，b，c的数字是什么，也就是要找到一个最小的 num ，使得 f(num, a, b, c) == n

        // 函数 f 是一个单调函数
        // 计算 [1..num] 之间有多少个能够被 a 或 b 或 c 整除的数字
        long f(int num, int a, int b, int c) {
            long setA = num / a, setB = num / b, setC = num / c;
            long setAB = num / lcm(a, b);
            long setAC = num / lcm(a, c);
            long setBC = num / lcm(b, c);
            long setABC = num / lcm(lcm(a, b), c);
            // 集合论定理：A + B + C - A ∩ B - A ∩ C - B ∩ C + A ∩ B ∩ C
            return setA + setB + setC - setAB - setAC - setBC + setABC;
        }
        // 计算最大公因数（辗转相除/欧几里得算法）
        long gcd(long a, long b) {
            if (a < b) {
                // 保证 a > b
                return gcd(b, a);
            }
            if (b == 0) {
                return a;
            }
            return gcd(b, a % b);
        }

        // 最小公倍数
        long lcm(long a, long b) {
            // 最小公倍数就是乘积除以最大公因数
            return a * b / gcd(a, b);
        }


    }
}
