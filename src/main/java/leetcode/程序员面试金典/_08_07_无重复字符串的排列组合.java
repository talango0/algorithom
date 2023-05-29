package leetcode.程序员面试金典;
//无重复字符串的排列组合。编写一种方法，计算某字符串的所有排列组合，字符串每个字符均不相同。
//
//示例1:
//
// 输入：S = "qwe"
// 输出：["qwe", "qew", "wqe", "weq", "ewq", "eqw"]
//示例2:
//
// 输入：S = "ab"
// 输出：["ab", "ba"]
//提示:
//
//字符都是英文字母。
//字符串长度在[1, 9]之间。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/permutation-i-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author mayanwei
 * @date 2023-05-29.
 */
public class _08_07_无重复字符串的排列组合{
    class Solution {
        List<String> res;
        public String[] permutation(String S) {
            res = new ArrayList<>();
            backtrace(S.toCharArray(), new LinkedHashSet<Character>());
            return res.toArray(new String[0]);
        }
        private void backtrace(char [] chs,   LinkedHashSet<Character> set) {
            if (set.size() == chs.length) {
                StringBuilder sb = new StringBuilder();
                for (Character ch:set){
                    sb.append(ch);
                }
                res.add(sb.toString());
            }
            for(int i = 0; i<chs.length; i++) {
                if (!set.contains(chs[i])) {
                    set.add(chs[i]);
                    backtrace(chs, set);
                    set.remove(chs[i]);
                }
            }
        }
    }


    /**
     * 组合法1：
     * 从第n-1个字符的排列组合开始构造
     */
    class Solution2{
        public String[] permutation(String S) {
            List<String> permutations = getPerms(S);
            return permutations.toArray(new String[0]);
        }

        private List<String> getPerms(String s) {
            if (s == null) {
                return null;
            }
            ArrayList<String> permutations = new ArrayList<>();
            // base case
            if (s.length() == 0) {
                permutations.add("");
                return permutations;
            }
            // 获取第一个字符
            char first = s.charAt(0);
            // 移除第一个字符
            String remainder = s.substring(1);
            List<String> perms = getPerms(remainder);
            for (String word : perms) {
                for (int j = 0; j <=word.length(); j++) {
                    String w = insertCharAt(word, first, j);
                    permutations.add(w);
                }
            }
            return permutations;
        }

        private String insertCharAt(String word, char c, int i) {
            String start = word.substring(0, i);
            String end = word.substring(i);
            return start + c + end;
        }
    }

    /**
     * <pre>
     * 组合法2：
     * 从第n-1个字符的所有子序列的排列组合开始构建。
     * ┌───────────────────────────────────────────┐
     * │ P(a1) = a1                                │
     * │ p(a1,a2)=a1,a2  a2,a1                     │
     * │ p(a2,a3)=a2,a3  a3,a2                     │
     * │ p(a1,a3)=a1,a3  a3,a1                     │
     * │                                           │
     * │ p(a1,a2,a3)=                              │
     * │     {(a1 + p(a2,a3))}    a1,a2,a3 a1,a3,a2│
     * │    +{(a2 + p(a1,a3))}    a2,a1,a3 a2,a3,s1│
     * │    +{(a3 + p(a1,a2))}    a3,a1,a2 a3,a2,a1│
     * └───────────────────────────────────────────┘
     * </pre>
     */
    class Solution3{
        public String[] permutation(String S) {
            List<String> permutations = getPerms(S);
            return permutations.toArray(new String[0]);
        }

        private List<String> getPerms(String s) {
            if (s == null) {
                return null;
            }
            ArrayList<String> permutations = new ArrayList<>();
            int len = s.length();
            // base case
            if (len == 0) {
                permutations.add(""); // 返回空字符串
                return permutations;
            }

            for (int i = 0; i < len; i++) {
                // 移除字符i，继续寻找剩下字符的排列组合
                String before = s.substring(0, i);
                String after = s.substring(i+1,len);
                List<String> partials = getPerms(before + after);
                // 将字符i添加到每个组合中
                for (String str : partials) {
                    permutations.add(s.charAt(i) + str);
                }
            }
            return permutations;
        }
    }
    // 此外还可以把前缀通过调用栈来传递，这样不用每次都把排列组合返回。
    // 当递归走到 base case是，前缀就已经是一个全排列了
    class Solution4{
        public String[] permutation(String S) {
            ArrayList<String> result = new ArrayList<>();
            getPerms("", S, result);
            return result.toArray(new String[0]);
        }

        private void getPerms(String prefix, String remainder, ArrayList<String> result) {
            if (remainder.length() == 0) {
                result.add(prefix);
            }
            int len = remainder.length();
            for (int i = 0; i < len; i++) {
                String before = remainder.substring(0, i);
                String after = remainder.substring(i + 1, len);
                char c = remainder.charAt(i);
                getPerms(prefix + c, before + after, result);
            }
        }
    }

}
