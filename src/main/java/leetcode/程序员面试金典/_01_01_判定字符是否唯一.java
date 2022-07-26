package leetcode.程序员面试金典;
//实现一个算法，确定一个字符串 s 的所有字符是否全都不同。
//
// 示例 1：
//
//
//输入: s = "leetcode"
//输出: false
//
//
// 示例 2：
//
//
//输入: s = "abc"
//输出: true
//
//
// 限制：
//
//
// 0 <= len(s) <= 100
// s[i]仅包含小写字母
// 如果你不使用额外的数据结构，会很加分。
//
// Related Topics 位运算 哈希表 字符串 排序 👍 222 👎 0

/**
 * @author mayanwei
 * @date 2022-07-23.
 */
public class _01_01_判定字符是否唯一{
    class Solution {
        public boolean isUnique(String astr) {
            if (astr == null || astr.length()>26) {
                return false;
            }
            int mask = 0;
            for (int i = 0; i<astr.length(); i++) {
                int k = astr.charAt(i) - 'a';
                if ((mask & (1 << k)) != 0) {
                    return false;
                }
                else {
                    mask |= (1 << k);
                }
            }
            return true;

        }
    }
}
