package leetcode.程序员面试金典;
//你有两个字符串，即pattern和value。 pattern字符串由字母"a"和"b"组成，用于描述字符串中的模式。
// 例如，字符串"catcatgocatgo"匹配模式"aabab"（其中"cat"是"a"，"go"是"b"），该字符串也匹配像"a"、"ab"和"b"这样的模式。
// 但需注意"a"和"b"不能同时表示相同的字符串。编写一个方法判断value字符串是否匹配pattern字符串。
//
//示例 1：
//
//输入： pattern = "abba", value = "dogcatcatdog"
//输出： true
//示例 2：
//
//输入： pattern = "abba", value = "dogcatcatfish"
//输出： false
//示例 3：
//
//输入： pattern = "aaaa", value = "dogcatcatdog"
//输出： false
//示例 4：
//
//输入： pattern = "abba", value = "dogdogdogdog"
//输出： true
//解释： "a"="dogdog",b=""，反之也符合规则
//提示：
//
//1 <= len(pattern) <= 1000
//0 <= len(value) <= 1000
//你可以假设pattern只包含字母"a"和"b"，value仅包含小写字母。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/pattern-matching-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.jupiter.api.Test;

/**
 * @author mayanwei
 * @date 2023-06-12.
 */
public class _16_18_模式匹配{
    /**
     * <pre>
     *     蛮力法。尝试所有 a 和 b 可能的值，并检查他们是否与字符串匹配。
     * 对 a 的所有子串和 b 的所有子串进行迭代。对于长度为  n 的子串总共有n^2 个，因此
     * 该过程会花费 O(n^4) 的时间。但是在这之后，对与 a 和 b 的每一个值，我们需要构造一个长度
     * 与其一致的字符串并检查构造的字符串是否与该值相等。所以总复杂度为 O(n^5)
     * ┌───────────────────────────────────────────────┐
     * │ for each possible substring a:                │
     * │   for each possible substring b:              │
     * │     candidate = buildFromPattern(pattern,a,b) │
     * │     if candidate equals value                 │
     * │       return true;                            │
     * └───────────────────────────────────────────────┘
     * 一种优化方式是检查模式串是否以 a 作为起始字符，如果是的话，字符串 a 则必须以 value 的起始为
     * 最初的字符（否则，字符串 b 必须以 value 起始值为最初的字符。）这样对于 a 就不存在 O(n^2) 个
     * 可能的值了，只有 O(n) 中可能性。
     * 接下里，算法需要检查模式是以 a 为起始还是 b 为起始。如果模式串以 b 为起始，我们可以对其进行
     * 翻转以便字符串以 a 作为起始（将字符串中的 a 替换为b，所有 b 替换为a）。然后，对 a 的所有可能
     * 子串（所有子串必须起始于索引0）和 b 的所有可能子串（所有子串必须起始于 a 结束后的某个字符）进行迭代。
     * 和前面一样，我们需要将该模式的字符串与原字符串进行比较。复杂度为 O(n^4)
     * </pre>
     */
    class Solution{
        public boolean patternMatching(String pattern, String value) {
            if (pattern.length() == 0) {
                return value.length() == 0;
            }

            int size = value.length();
            for (int mainSize = 0; mainSize < size; mainSize++) {
                String main = value.substring(0, mainSize); // 模式的第一个字符定义为主字符
                for (int altStart = mainSize; altStart <= size; altStart++) {
                    for (int altEnd = altStart; altEnd <= size; altEnd++) {
                        String alt = value.substring(altStart, altEnd);
                        String cand = buildFromPattern(pattern, main, alt);
                        if (cand.equals(value)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        private String buildFromPattern(String pattern, String main, String alt) {
            StringBuffer sb = new StringBuffer();
            char first = pattern.charAt(0);
            for (char c : pattern.toCharArray()) {
                if (c == first) {
                    sb.append(main);
                }
                else {
                    sb.append(alt);
                }
            }
            return sb.toString();
        }
    }

    /**
     * 优化，一旦选择了a，b就确定了，不需要对b进行迭代，通过获取 pattern 的一些基本信息（a 的数量，b 的数量， a 和 b 的个数），对字符串
     * a 的可能只（或者main 字符串所对应的可能值）进行迭代即可。
     * 时间复杂度 O(n^2)
     */
    class Solution2{
        public boolean patternMatching(String pattern, String value) {
            if (pattern == null) {
                return value == null;
            }
            if (pattern.length() == 0) {
                return value.length() == 0;
            }
            char mainChar = pattern.charAt(0);
            char altChar = mainChar == 'a' ? 'b':'a';
            int size = value.length();

            int countOfMain = countOf(pattern, mainChar);
            int countOfAlt = pattern.length() - countOfMain;
            int firstAlt = pattern.indexOf(altChar);
            int maxMainSize = size / countOfMain;

            for (int mainSize = 0; mainSize <= maxMainSize; mainSize++) {
                int remainingLength = size - mainSize * countOfMain;
                String first = value.substring(0, mainSize);
                if (countOfAlt == 0 || remainingLength % countOfAlt == 0) {
                    int altIndex = firstAlt * mainSize;
                    int altSize = countOfAlt == 0 ? 0 : remainingLength / countOfAlt;
                    String second = countOfAlt == 0 ? "" : 
                            value.substring(altIndex, altSize + altIndex);
                    String cand = buildFromPattern(pattern, first, second);
                    if (cand.equals(value)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private String buildFromPattern(String pattern, String main, String alt) {
            StringBuffer sb = new StringBuffer();
            char first = pattern.charAt(0);
            for (char c : pattern.toCharArray()) {
                if (c == first) {
                    sb.append(main);
                }
                else {
                    sb.append(alt);
                }
            }
            return sb.toString();
        }

        private int countOf(String pattern, char c) {
            int count = 0;
            for (int i = 0; i <pattern.length(); i++) {
                if (pattern.charAt(i) == c) {
                    count ++;
                }
            }
            return count;
        }
    }

    class Solution3{
        public boolean patternMatching(String pattern, String value) {
            if (pattern == null) {
                return value == null;
            }
            if (pattern.length() == 0) {
                return value.length() == 0;
            }
            char mainChar = pattern.charAt(0);
            char altChar = mainChar == 'a' ? 'b' :'a';
            int size = value.length();

            int countOfMain = countOf(pattern, mainChar);
            int countOfAlt = pattern.length() - countOfMain;
            int firstAlt = pattern.indexOf(altChar);
            int maxMainSize = size / countOfMain;

            for (int mainSize = 0; mainSize <= maxMainSize; mainSize++) {
                int remainingLength = size - mainSize * countOfMain;
                if (countOfAlt == 0 || remainingLength % countOfAlt == 0) {
                    int altIndex = firstAlt * mainSize;
                    int altSize = countOfAlt == 0 ? 0 :remainingLength / countOfAlt;
                    if (matches(pattern, value, mainSize, altSize, altIndex)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * 对 pattern 和 value 进行迭代。对与pattern 中的每一个字符，检查其是 main 字符串还是
         * alternate 字符串。之后检查value 中的下一组字符是否与原始 main 或者 alternate字符串中的字符
         * 匹配。
         */
        private boolean matches(String pattern, String value, int mainSize, int altSize, int firstAlt) {
            int startIndex = mainSize;
            for (int i = 1; i < pattern.length(); i++) {
                int size = pattern.charAt(i) == pattern.charAt(0) ? mainSize :altSize;
                int offset = pattern.charAt(i) == pattern.charAt(0) ? 0 :firstAlt;
                if (!isEqual(value, offset, startIndex, size)) {
                    return false;
                }
                startIndex += size;
            }
            return true;
        }

        /**
         * 检查两个字符串从给定位移至给定长度出是否相等
         */
        private boolean isEqual(String s1, int offset1, int offset2, int size) {
            for (int i = 0; i < size; i++) {
                if (s1.charAt(offset1 + i) != s1.charAt(offset2+i)) {
                    return false;
                }
            }
            return true;
        }

        private int countOf(String pattern, char c) {
            int count = 0;
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) == c) {
                    count++;
                }
            }
            return count;
        }
    }

    /**
     * <pre>
     * ┌─────────────────────────────────────────────────────────────┐
     * │                                                             │
     * │ lv: the length of  value                                    │
     * │ lp: the length of  pattern                                  │
     * │                                                             │
     * │ la: the length of word represent by a                       │
     * │ lb: the length of word represent by b                       │
     * │                                                             │
     * │ ca: the count of word represent by a in value               │
     * │ cb: the count of word represent by b in value               │
     * │ Suppose there are ca a and (lp-ca) b in the pattern         │
     * │ ca * la + (lp-ca)*lb = lv                                   │
     * │                                                             │
     * │ la must in [0, lv/ca] ,enumrating la in [0,lv/ca],          │
     * │ then getting lb throught puting la into the above equation. │
     * │                                                             │
     * └─────────────────────────────────────────────────────────────┘
     * </pre>
     */
    class Solution1{
        public boolean patternMatching(String pattern, String value) {
            // 计算模式串中 a,b 的数量
            int countA = 0, countB = 0;
            for (char ch : pattern.toCharArray()) {
                if (ch == 'a') {
                    ++countA;
                }
                else {
                    ++countB;
                }
            }
            //如果 b 的数量大于 a 的数量，交换 a 和 b
            if (countA < countB) {
                int temp = countA;
                countA = countB;
                countB = temp;
                char[] array = pattern.toCharArray();
                for (int i = 0; i < array.length; i++) {
                    array[i] = array[i] == 'a' ? 'b' :'a';
                }
                pattern = new String(array);
            }
            //如果主串是空字符串，并且模式串只有一种子模式或模式串也是空字符串，则匹配成功，返回 true 。
            //否则失败，返回 false
            if (value.length() == 0) {
                return countB == 0;
            }
            for (int lenA = 0; countA * lenA <= value.length(); ++lenA) {
                //主串减去 countA 个长度为 lenA 的 a 子模式匹配值后剩余字符数量。
                //剩余部分是需要和 b 模式进行匹配，由于从模式串已知 b 子模式的数量，
                //从而可以计算出b的匹配值长度（必须是非负整数）
                int rest = value.length() - countA * lenA;
                // b 子模式匹配值长度为是非负整数时继续计算，否则匹配失败
                if ((countB == 0 && rest == 0) || (countB != 0 && rest % countB == 0)) {
                    //获取 b 子模式匹配值长度
                    int lenB = (countB == 0 ? 0 :rest / countB);
                    //每次分割起始位置
                    int pos = 0;
                    //记录当前子串是否匹配成功
                    boolean correct = true;
                    String valueA = "", valueB = "";
                    //根据a 子模式和b 子模式匹配值长度和 a,b 在模式串里出现的先后顺序去分割主串。
                    // 同时获得 a 子模式的匹配值和 b 子模式的匹配值
                    for (char ch : pattern.toCharArray()) {
                        if (ch == 'a') {
                            String sub = value.substring(pos, pos + lenA);
                            if (valueA.length() == 0) {
                                //a 子模式的匹配值
                                valueA = sub;
                            }
                            else if (!valueA.equals(sub)) {
                                correct = false;
                                break;
                            }
                            pos += lenA;
                        }
                        else {
                            String sub = value.substring(pos, pos + lenB);
                            if (valueB.length() == 0) {
                                //b 子模式的匹配值
                                valueB = sub;
                            }
                            else if (!valueB.equals(sub)) {
                                correct = false;
                                break;
                            }
                            pos += lenB;
                        }
                    }
                    //如果主串和模式串完全匹配，同时 a 子模式的匹配值和 b 子模式的匹配值不相同，则匹配成功
                    if (correct && !valueA.equals(valueB)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Test
    public void test() {
        //String pattern = "abbaa", value = "dogdogdogdogdog";
        //String pattern = "abbaabb", value = "dogdogdogdogdogdogdog";
        String pattern = "abbab", value = "dogcatcatdogcat";
        //String pattern = "aaaa", value = "dogcatcatdog";
        Solution3 solution = new Solution3();
        boolean b = solution.patternMatching(pattern, value);
        System.out.println(b);
    }
}
