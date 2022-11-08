package leetcode.jzhoffer;

import leetcode.dp._926_将字符串翻转到单调递增;

/**
 * @author mayanwei
 * @date 2022-11-04.
 * @see _926_将字符串翻转到单调递增
 */
public class 剑指_Offer_II_092_翻转字符{
    class Solution{
        public int minFlipsMonoIncr(String s) {
            int dp_0 = 0, dp_1 = 0;
            for (char ch : s.toCharArray()) {
                dp_1 = dp_1 > dp_0 ? dp_0 :dp_1 + '1' - ch;
                dp_0 += ch - '0';
            }
            return dp_0 > dp_1 ? dp_1 :dp_0;
        }
    }
}
