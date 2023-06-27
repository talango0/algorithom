package leetcode.程序员面试金典;
//字符串有三种编辑操作:插入一个英文字符、删除一个英文字符或者替换一个英文字符。
// 给定两个字符串，编写一个函数判定它们是否只需要一次(或者零次)编辑。
//
//
//
//示例1:
//
//输入: 
//first = "pale"
//second = "ple"
//输出: True
//
//
//示例2:
//
//输入: 
//first = "pales"
//second = "pal"
//输出: False
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/one-away-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _01_05_一次编辑{
    /**
     * 替换：bale， pale，它们之间相差一词替换，意味着，两个字符串仅在一个字符位置上有所不同。
     * 插入/删除，意味着对比两个字符串，会发现除了在字符串上的某一位置需要整体移动一次以外，它们是完全相同的。
     */
    class Solution{
        public boolean oneEditAway(String first, String second) {
            if (first.length() == second.length()) {
                return oneEditReplace(first, second);
            }
            else if (first.length() + 1 == second.length()) {
                return oneEditInsert(first, second);
            }
            else if (first.length() - 1 == second.length()) {
                return oneEditInsert(second, first);
            }
            return false;
        }

        /**
         * 检测是否可以通过向 s1 插入一个字符构造 s2
         *
         * @return
         */
        private boolean oneEditInsert(String s1, String s2) {
            int index1 = 0, index2 = 0;
            while (index2 < s2.length() && index1 < s1.length()) {
                if (s1.charAt(index1) != s2.charAt(index2)) {
                    if (index1 != index2) {
                        return false;
                    }
                }
                else {
                    index1++;
                }
                index2++;
            }
            return true;
        }

        private boolean oneEditReplace(String s1, String s2) {
            boolean foundDifference = false;
            for (int i = 0; i < s1.length(); i++) {
                if (s1.charAt(i) != s2.charAt(i)) {
                    if (foundDifference) {
                        return false;
                    }
                    foundDifference = true;
                }
            }
            return true;
        }
    }


    class Solution1{
        public boolean oneEditAway(String first, String second) {
            if (first.equals(second)) {
                return true;
            }
            int n = first.length(), m = second.length();
            if (n > m) {
                return oneEditAway(second, first);
            }
            else if (n + 1 < m) {
                return false;
            }
            int cnt = 0;
            int i = 0, j = 0;
            while (i < n && j < m && cnt <= 1) {
                if (first.charAt(i) == second.charAt(j)) {
                    i++;
                    j++;
                }
                else {
                    if (n == m) {
                        i++;
                    }
                    j++;
                    cnt++;
                }
            }
            return cnt <= 1;
        }
    }
}
