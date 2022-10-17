package leetcode.jzhoffer;

import leetcode.dp._233_数字1的个数;

/**
 * @author mayanwei
 * @date 2022-10-17.
 * @see _233_数字1的个数
 */
public class 剑指Offer43_1至n整数中1出现的次数{
    class Solution {
        public int countDigitOne(int n) {
            long mulk = 1;
            int ans = 0;
            for (int k = 0; n >= mulk; ++k) {
                ans += (n / (mulk * 10)) * mulk + Math.min(Math.max(n % (mulk * 10) - mulk + 1, 0), mulk);
                mulk *= 10;
            }
            return ans;
        }
    }
}

