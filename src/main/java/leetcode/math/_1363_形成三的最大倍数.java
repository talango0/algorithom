package leetcode.math;
//给你一个整数数组 digits，你可以通过按任意顺序连接其中某些数字来形成 3 的倍数，请你返回所能得到的最大的 3 的倍数。
//
// 由于答案可能不在整数数据类型范围内，请以字符串形式返回答案。
//
// 如果无法得到答案，请返回一个空字符串。
//
//
//
// 示例 1：
//
// 输入：digits = [8,1,9]
//输出："981"
//
//
// 示例 2：
//
// 输入：digits = [8,6,7,1,0]
//输出："8760"
//
//
// 示例 3：
//
// 输入：digits = [1]
//输出：""
//
//
// 示例 4：
//
// 输入：digits = [0,0,0,0,0,0]
//输出："0"
//
//
//
//
// 提示：
//
//
// 1 <= digits.length <= 10^4
// 0 <= digits[i] <= 9
// 返回的结果不应包含不必要的前导零。
//
//
// Related Topics 贪心 数组 动态规划 👍 70 👎 0

/**
 * @author mayanwei
 * @date 2022-09-15.
 */
public class _1363_形成三的最大倍数{
    /**
     * 思路
     * 一个数能被 3 整除，当且仅当他的各位数字之和能被3整除。例如 981 它的各位 9+8+1 = 18 能被3整除，同时 981 也能被3 整除。
     *
     * 时间复杂度 O(n)
     * 空间复杂度 O(1)
     */
    class Solution{
        public String largestMultipleOfThree(int[] digits) {
            int[] cnt = new int[10];
            int[] modulo = new int[3];
            int sum = 0;
            for (int digit : digits) {
                cnt[digit]++;
                modulo[digit % 3]++;
                sum += digit;
            }
            // removeMod 表示要删除的元素 mod 3 余几
            // rest 表示删除个数
            int removeMod = 0, rest = 0;
            if (sum % 3 == 1) {
                if (modulo[1] >= 1) {
                    removeMod = 1;
                    rest = 1;
                }
                else {
                    removeMod = 2;
                    rest = 2;
                }
            }
            else if (sum % 3 == 2) {
                if (modulo[2] >= 1) {
                    removeMod = 2;
                    rest = 1;
                }
                else {
                    removeMod = 1;
                    rest = 2;
                }
            }
            StringBuilder ans = new StringBuilder();
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j < cnt[i]; ++j) {
                    if (rest > 0 && removeMod == i % 3) {
                        rest--;
                    }
                    else {
                        ans.append(i);
                    }
                }
            }
            if (ans.length() > 0 && ans.charAt(ans.length() - 1) == '0') {
                ans.setLength(0);
                ans.append('0');
            }
            ans.reverse();
            return ans.toString();
        }
    }


    class Solution1{
        public String largestMultipleOfThree(int[] digits) {
            int left = 0;
            int[] nums = new int[10];
            for (int d : digits) {
                nums[d]++;
                left = (left + d) % 3;
            }

            if (2 == left) {
                for (int i = 2; left > 0 && i < 10; i += 3) {
                    if (nums[i] > 0) {
                        nums[i]--;
                        left = 0;
                    }
                }
            }
            if (left > 0) {
                for (int i = 1; left > 0 && i < 10; i += 3) {
                    int t = Math.min(nums[i], left);
                    nums[i] -= t;
                    left -= t;
                }
            }
            if (left == 2) {
                return "";
            }
            else if (1 == left) {
                left = 2;
            }
            for (int i = 2; left > 0 && i < 10; i += 3) {
                int t = Math.min(nums[i], left);
                nums[i] -= t;
                left -= t;
            }
            if (left > 0) {
                return "";
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 9; i > 0; i--) {
                while (nums[i] > 0) {
                    builder.append((char) ('0' + i));
                    nums[i]--;
                }
            }
            if (nums[0] > 0 && builder.length() == 0) {
                return "0";
            }
            while (nums[0] > 0) {
                builder.append('0');
                nums[0]--;
            }
            return builder.toString();
        }
    }
}
