package leetcode.dp;
//你会得到一个字符串 text 。你应该把它分成 k 个子字符串 (subtext1, subtext2，…， subtextk) ，要求满足:
//
//
// subtexti 是 非空 字符串
// 所有子字符串的连接等于 text ( 即subtext1 + subtext2 + ... + subtextk == text )
// subtexti == subtextk - i + 1 表示所有 i 的有效值( 即 1 <= i <= k )
//
//
// 返回k可能最大值。
//
//
//
// 示例 1：
//
//
//输入：text = "ghiabcdefhelloadamhelloabcdefghi"
//输出：7
//解释：我们可以把字符串拆分成 "(ghi)(abcdef)(hello)(adam)(hello)(abcdef)(ghi)"。
//
//
// 示例 2：
//
//
//输入：text = "merchant"
//输出：1
//解释：我们可以把字符串拆分成 "(merchant)"。
//
//
// 示例 3：
//
//
//输入：text = "antaprezatepzapreanta"
//输出：11
//解释：我们可以把字符串拆分成 "(a)(nt)(a)(pre)(za)(tpe)(za)(pre)(a)(nt)(a)"。
//
//
//
//
// 提示：
//
//
// 1 <= text.length <= 1000
// text 仅由小写英文字符组成
//
//
// Related Topics 贪心 双指针 字符串 动态规划 哈希函数 滚动哈希 👍 46 👎 0

import java.util.Arrays;

/**
 * 字节
 * @author mayanwei
 * @date 2022-09-01.
 */
public class _1147_段式回文{
    /**
     *        ┌──────┐                                                   ┌──────┐
     *        │ g h i│a b c d e f h e l l o z c z m h e l l o a b c d e f│g h i │
     *        └──────┘                                                   └──────┘
     *
     *               ┌───────────┐                           ┌───────────┐
     *               │a b c d e f│h e l l o z c z m h e l l o│a b c d e f│
     *               └───────────┘                           └───────────┘
     *
     *                           ┌─────────┐       ┌─────────┐
     *                           │h e l l o│z c z m│h e l l o│
     *                           └─────────┘       └─────────┘
     *
     *                                     ┌───────┐
     *                                     │z c z m│
     *                                     └───────┘
     */
    class Solution{
        public int longestDecomposition(String text) {
            if (text == null) {
                return -1;
            }
            int n = text.length();
            for (int i = 1; i <= n / 2; ++i) {
                // 将字符串分成 [0,i) ~ [n-i, n)
                // 如果上面相等表示有2份回文串
                // 并将剩余的字段投入到下一轮迭代 [i, n-i)
                // i 其实表示比较字段的长度
                if (text.substring(0, i).equals(text.substring(n - i, n))) {
                    return 2 + longestDecomposition(text.substring(i, n - i));
                }
            }
            return n == 0 ? 0 :1;
        }
    }

    class Solution2 {
        public int longestDecomposition(String text) {
            //four pointers;
            if(text == null || text.length() == 0) return 0;
            int left = 0, right = text.length();
            int low = left + 1, high = right - 1;
            int count = 0;
            while(low <= high) {
                String sl = text.substring(left, low);
                String sr = text.substring(high, right);
                if(sl.equals(sr)) {
                    count += 2;
                    left = low;
                    right = high;
                }
                low++;
                high--;
            }
            if(left < right) {
                count += 1;
            }
            return count;
        }
    }


    class Solution1 {
        int[][] dp;
        String text;

        public int longestDecomposition(String text) {
            int n = text.length();
            dp = new int[n][n];

            // 初始化为-1 用以表示该位置是否被处理
            for (int i = 0; i < n; i++) Arrays.fill(dp[i], -1);
            this.text = text;

            // 求整个字符串的回文字段k
            return helper(0, n-1);
        }

        /**
         * 求回文字段数 text的子串
         * @param s 起始位置
         * @param e 终止位置
         * @return 该字段的回文字段数
         **/
        int helper(int s, int e) {
            // 非法字段 起始位置大于终止位置
            if (s > e) return 0;
            // 特殊情况 刚好只剩下一个字符
            if (s == e) return dp[s][e] = 1;
            // 不为-1 则表示以及处理过 直接拿来用就可以
            if (dp[s][e] != -1) return dp[s][e];

            // 字符串本身算一回文字段
            int res = 1;
            // 枚举长度 1 ~ (e-s+1)/2 从s开始的子串
            for (int l = 1; l <= (e-s+1)/2; l++) {
                String st = text.substring(s, s+l);
                String ed = text.substring(e-l+1, e+1);
                // 如果两子串相等 表示找到回文字段 +2
                if (st.equals(ed)) {
                    int tmp = helper(s+l, e-l);
                    // 更新结果值
                    res = tmp+2;
                }
            }

            return dp[s][e] = res;
        }
    }

}
