package leetcode.程序员面试金典;
//字符串压缩。利用字符重复出现的次数，编写一种方法，实现基本的字符串压缩功能。比如，字符串aabcccccaaa会变为a2b1c5a3。
// 若“压缩”后的字符串没有变短，则返回原先的字符串。你可以假设字符串中只包含大小写英文字母（a至z）。
//
//示例1:
//
// 输入："aabcccccaaa"
// 输出："a2b1c5a3"
//示例2:
//
// 输入："abbccd"
// 输出："abbccd"
// 解释："abbccd"压缩后为"a1b2c2d1"，比原字符串长度更长。
//提示：
//
//字符串长度在[0, 50000]范围内。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/compress-string-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _01_06_字符串压缩{
    /**
     * 迭代访问字符串，将字符复制到新的字符串，并数出重复字符，在遍历过程中的每一步，只需
     * 检查当前字符与下一个字符是否一致。如果不一致，则将压缩后的版本写入到结果中。
     * <p>
     * 该方法可行，但是效率低，只需时间为 O(p + k^2),其中p位i原始字符串长度，k 为字符序列的数量。
     * 比如，若字符串为 aabccdeeaa,则共计6个字符序列。执行慢的原因在与字符串拼接操作的时间复杂度为
     * O(n^2)
     */
    class Solution{
        public String compressString(String S) {
            String compressedString = "";
            int countConsecutive = 0;
            for (int i = 0; i < S.length(); i++) {
                countConsecutive++;
                // 如果下一个字符与当前字符不同，那么将当前字符添加到结果尾部
                if (i + 1 >= S.length() || S.charAt(i) != S.charAt(i + 1)) {
                    compressedString += "" + S.charAt(i) + countConsecutive;
                    countConsecutive = 0;
                }
            }
            return compressedString.length() < S.length() ? compressedString :S;
        }
    }

    /**
     * 使用StringBuilder 优化部分性能。
     */
    class Solution1{
        public String compressString(String S) {
            StringBuilder compressed = new StringBuilder();
            int countConsecutive = 0;
            for (int i = 0; i < S.length(); i++) {
                countConsecutive++;
                // 如果下一个字符与当前字符不同，那么将当前字符添加到结果尾部
                if (i + 1 >= S.length() || S.charAt(i) != S.charAt(i + 1)) {
                    compressed.append(S.charAt(i)).append(countConsecutive);
                    countConsecutive = 0;
                }
            }
            return compressed.length() < S.length() ? compressed.toString() :S;
        }
    }

    /**
     * 提前检查原字符串，然后返回愿字符串与压缩字符串中较短的一个。
     * 在没有很多重复字符串的情况下，该方法为上乘之选，因为其避免了构造一个最终不会被使用的字符串。
     * 而该方法的缺点在于，需要再次对字符串进行循环。
     * <p>
     * 该方法的另一个优点在于提前将 StringBuilder 初始化为所需的容量。如果没有这一步骤。StringBuilder
     * 需要在每次达到容过量时将其容亮翻倍（该过程阴式进行），其最终容量有可能会达到所需容量的两倍。
     */
    class Solution3{
        public String compressString(String S) {
            // 检查最终长度，如果其较长，则返回输入字符串
            int finalLength = countCompression(S);
            StringBuilder compressed = new StringBuilder(finalLength);
            int countConsecutive = 0;
            for (int i = 0; i < S.length(); i++) {
                countConsecutive++;
                // 如果下一个字符与当前字符不同，那么将当前字符添加到结果尾部
                if (i + 1 >= S.length() || S.charAt(i) != S.charAt(i + 1)) {
                    compressed.append(S.charAt(i)).append(countConsecutive);
                    countConsecutive = 0;
                }
            }
            return compressed.length() < S.length() ? compressed.toString() :S;
        }

        private int countCompression(String s) {
            int compressedLength = 0;
            int countConsecutive = 0;
            for (int i = 0; i < s.length(); i++) {
                countConsecutive++;
                // 如果下一个字符与当前字符不同，那么增加其长度
                if (i + 1 >= s.length() || s.charAt(i) != s.charAt(i + 1)) {
                    compressedLength += 1 + String.valueOf(countConsecutive).length();
                    countConsecutive = 0;
                }
            }
            return compressedLength;
        }
    }


    class Solution4{
        public String compressString(String S) {
            char[] cs = S.toCharArray();
            int n = cs.length;
            char[] ans = new char[n];
            int i = 0, j = 0, idx = 0;
            while (i < n) {
                while (j < n && cs[j] == cs[i]) {
                    j++;
                }
                if (idx >= n - 2) {
                    return S;
                }
                ans[idx++] = cs[i];
                int ln = j - i;
                int s = idx;
                while (ln > 0) {
                    ans[idx++] = (char) ('0' + ln % 10);
                    ln = ln / 10;
                }
                reverse(ans, s, idx - 1);
                i = j;
            }
            return new String(ans, 0, idx);
        }

        void reverse(char[] cs, int i, int j) {
            while (i < j) {
                char t = cs[i];
                cs[i] = cs[j];
                cs[j] = t;
                i++;
                j--;
            }
        }
    }
}
