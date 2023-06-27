package leetcode.程序员面试金典;
//字符串轮转。给定两个字符串s1和s2，请编写代码检查s2是否为s1旋转而成（比如，waterbottle是erbottlewat旋转后的字符串）。
//
//示例1:
//
// 输入：s1 = "waterbottle", s2 = "erbottlewat"
// 输出：True
//示例2:
//
// 输入：s1 = "aa", s2 = "aba"
// 输出：False
//提示：
//
//字符串长度在[0, 100000]范围内。
//说明:
//
//你能只调用一次检查子串的方法吗？
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/string-rotation-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

public class _01_09_字符串轮转{
    /**
     * <pre>
     * s1 = xy = waterbottle
     * x = wat
     * y = erbottle
     * s2 = yx = erbottlewat
     * 需要确认有没有办法将 s1 切分为 x 和 y,满足 xy= s1 和 yx =s2,不论 x 和 y 之间的分割点在何处，我们
     * 会发现 yx 肯定是 xyxy的子串。也即 s2 总是 s1s1 的子串。
     * 通过上面分析，直接调用 isSubstring(s1s1, s2）即可
     * </pre>
     */
    class Solution{
        public boolean isFlipedString(String s1, String s2) {
            if (s1 == null || s2 == null) {
                return false;
            }
            if (s1.isEmpty() && s2.isEmpty()){
                return true;
            }
            int len = s1.length();
            // 检查 s1 和 s2 长度相等且非空
            if (len == s2.length() && len > 0) {
                // 在新空间中将 s1 与 s1 合并
                String s1s1 = s1 + s1;
                return isSubString(s1s1, s2);
            }
            return false;
        }

        private boolean isSubString(String s1s1, String s2) {
            return s1s1.indexOf(s2) != -1;
        }
    }
}