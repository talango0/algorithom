package leetcode.string;

import java.util.HashMap;
import java.util.Map;
//定义 str = [s, n] 表示 str 由 n 个字符串 s 连接构成。
//
//
// 例如，str == ["abc", 3] =="abcabcabc" 。
//
//
// 如果可以从 s2 中删除某些字符使其变为 s1，则称字符串 s1 可以从字符串 s2 获得。
//
//
// 例如，根据定义，s1 = "abc" 可以从 s2 = "abdbec" 获得，仅需要删除加粗且用斜体标识的字符。
//
//
// 现在给你两个字符串 s1 和 s2 和两个整数 n1 和 n2 。由此构造得到两个字符串，其中 str1 = [s1, n1]、str2 = [s2,
//n2] 。
//
// 请你找出一个最大整数 m ，以满足 str = [str2, m] 可以从 str1 获得。
//
//
//
// 示例 1：
//
//
//输入：s1 = "acb", n1 = 4, s2 = "ab", n2 = 2
//输出：2
//
//
// 示例 2：
//
//
//输入：s1 = "acb", n1 = 1, s2 = "acb", n2 = 1
//输出：1
//
//
//
//
// 提示：
//
//
// 1 <= s1.length, s2.length <= 100
// s1 和 s2 由小写英文字母组成
// 1 <= n1, n2 <= 10⁶
//
//
// Related Topics 字符串 动态规划 👍 168 👎 0

/**
 * @author mayanwei
 * @date 2022-09-18.
 */
public class _466_统计重复个数{
    class Solution{
        public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
            if (n1 == 0) {
                return 0;
            }
            int s1cnt = 0, index = 0, s2cnt = 0;
            // recall 用于寻找循环节的变量，它是一个哈希映射
            Map<Integer, int[]> recall = new HashMap<>();
            int[] preLoop = new int[2];
            int[] inLoop = new int[2];
            while (true) {
                // 多遍历一个 s1，看看能不能找到循环节
                ++s1cnt;
                for (int i = 0; i < s1.length(); i++) {
                    char ch = s1.charAt(i);
                    if (ch == s2.charAt(index)) {
                        index += 1;
                        if (index == s2.length()) {
                            s2cnt++;
                            index = 0;
                        }

                    }
                }
                // 还没有找到循环节，s1 就用完了
                if (s1cnt == n1) {
                    return s2cnt / n2;
                }

                // 出现了之前的index， 表示找到了循环节
                if (recall.containsKey(index)) {
                    int[] value = recall.get(index);
                    int s1cntPrime = value[0];
                    int s2cntPrime = value[1];
                    // 前 s1cnt' 个 s1 包含了 s2cnt' 个 s2
                    preLoop = new int[]{s1cntPrime, s2cntPrime};
                    // 以后的每 (s1cnt - s1cnt') 个 s1 包含了 (s2cnt - s2cnt') 个 s2
                    inLoop = new int[]{s1cnt - s1cntPrime, s2cnt - s2cntPrime};
                    break;
                }
                else {
                    recall.put(index, new int[]{s1cnt, s2cnt});
                }
            }
            // ans 存储的是 S1 包含的 s2 的数量，考虑的之前的 preLoop 和 inLoop
            int ans = preLoop[1] + (n1 - preLoop[0]) / inLoop[0] * inLoop[1];
            // S1 的末尾还剩下一些 s1，我们暴力进行匹配
            int rest = (n1 - preLoop[0]) % inLoop[0];
            for (int i = 0; i < rest; ++i) {
                for (int j = 0; j < s1.length(); ++j) {
                    char ch = s1.charAt(j);
                    if (ch == s2.charAt(index)) {
                        ++index;
                        if (index == s2.length()) {
                            ++ans;
                            index = 0;
                        }
                    }
                }
            }
            // S1 包含 ans 个 s2，那么就包含 ans / n2 个 S2
            return ans / n2;
        }
    }
}
