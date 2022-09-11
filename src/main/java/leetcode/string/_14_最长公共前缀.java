package leetcode.string;
//编写一个函数来查找字符串数组中的最长公共前缀。
//
// 如果不存在公共前缀，返回空字符串 ""。
//
//
//
// 示例 1：
//
//
//输入：strs = ["flower","flow","flight"]
//输出："fl"
//
//
// 示例 2：
//
//
//输入：strs = ["dog","racecar","car"]
//输出：""
//解释：输入不存在公共前缀。
//
//
//
// 提示：
//
//
// 1 <= strs.length <= 200
// 0 <= strs[i].length <= 200
// strs[i] 仅由小写英文字母组成
//
//
// Related Topics 字符串 👍 2440 👎 0

/**
 * @author mayanwei
 * @date 2022-09-10.
 */
public class _14_最长公共前缀{
    class Solution{
        public String longestCommonPrefix(String[] strs) {
            int min = strs[0].length();
            int index = 0;
            for (int i = 1; i < strs.length; i++) {
                if (strs[i].length() < min) {
                    min = strs[i].length();
                    index = i;
                }
            }
            String str = strs[index];
            for (int i = 0; i < strs.length; i++) {
                String demo = strs[i].substring(0, min);
                while (!demo.equals(str) && min != 0) {
                    str = str.substring(0, --min);
                    demo = demo.substring(0, min);
                }
            }
            return str;
        }
    }

    class Solution1 {
        public String longestCommonPrefix(String[] strs) {
            if (strs.length == 0) {
                return "";
            }

            String commonPrefix = strs[0];
            for (int i = 1; i < strs.length; i++) {
                while (strs[i].indexOf(commonPrefix) != 0) {
                    commonPrefix = commonPrefix.substring(0, commonPrefix.length() - 1);
                }
            }

            return commonPrefix;
        }
    }

    /**
     * 二分法
     *
     * 显然，最长公共子串长度不会超过字符串数组中的最短字符串的长度。用minLength 表示字符串数组中的最短字符串的长度，
     * 则可以在 [0, minLength] 的范围内通过二分查找的得到最长公共前缀的长度。 每次取查找范围的中间值mid， 判断每个
     * 字符串的长度为 mid 的前缀是否相同，如果相同则最长公共前缀的长度u一定大于或等于 mid， 如果不相同则最长公共前缀
     * 长度一定小于mid，通过上述方式可以将查找范围缩小一半。
     *
     * 时间复杂度 O(mnlog(m))
     * 空间复杂度 O(1)
     */
    class Solution2 {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) {
                return "";
            }
            int minLength = Integer.MAX_VALUE;
            for (int i = 0; i<strs.length; i++) {
                minLength = Math.min(minLength,strs[i].length());
            }
            int low = 0, high = minLength;
            while (low < high) {
                // 这里向上取整，否则会陷入死循环 ['a']
                int mid = low + (high-low+1)/2;
                if (isCommonPrefix(strs, mid)) {
                    // 搜索区间 [mid, right]
                    low = mid;
                }
                else {
                    // [left, mid-1]
                    high = mid-1;
                }
            }
            return strs[0].substring(0, low);
        }

        private boolean isCommonPrefix(String[] strs, int mid) {
            String str0 = strs[0].substring(0, mid);
            int count = strs.length;
            for (int i =1; i<count; i++) {
                String str = strs[i];
                for (int j = 0; j<mid; j++) {
                    if (str0.charAt(j) != str.charAt(j)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
