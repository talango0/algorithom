package leetcode.程序员面试金典;
//假设你有两个数组，一个长一个短，短的元素均不相同。找到长数组中包含短数组所有的元素的最短子数组，其出现顺序无关紧要。
//
//返回最短子数组的左端点和右端点，如有多个满足条件的子数组，返回左端点最小的一个。若不存在，返回空数组。
//
//示例 1:
//
//输入:
//big = [7,5,9,0,2,1,3,5,7,9,1,1,5,8,8,9,7]
//small = [1,5,9]
//输出: [7,10]
//示例 2:
//
//输入:
//big = [1,2,3]
//small = [4]
//输出: []
//提示：
//
//big.length <= 100000
//1 <= small.length <= 100000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/shortest-supersequence-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.jzhoffer.剑指_Offer_II_014_字符串中的变位词;
import leetcode.jzhoffer.剑指_Offer_II_015_字符串中的所有变位词;
import leetcode.jzhoffer.剑指_Offer_II_017_含有所有字符的字段字符串;
import leetcode.slidewindow._567_字符串的排列;
import leetcode.slidewindow._76_最小覆盖子串;
import leetcode.string._438_找到字符串中所有字母异位词;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2023-06-25.
 * @see _76_最小覆盖子串
 * @see 剑指_Offer_II_017_含有所有字符的字段字符串
 * 定长滑动窗口
 * @see _567_字符串的排列
 * @see 剑指_Offer_II_014_字符串中的变位词
 * @see 剑指_Offer_II_015_字符串中的所有变位词
 * @see _438_找到字符串中所有字母异位词
 */
public class _17_18_最短超串{
    /**
     * 滑动窗口
     * 时间复杂度 O(m+n),m和n 分别为 长数组和短数组的长度，哈希表需要O(n)的空间。
     */
    class Solution{
        public int[] shortestSeq(int[] big, int[] small) {
            int shortestStart = -1;
            int shortestLength = Integer.MAX_VALUE;
            Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
            int m = big.length, n = small.length;
            for (int i = 0; i < n; i++) {
                int num = small[i];
                counts.put(num, -1);
            }
            int meets = 0;
            int start = 0, end = 0;
            while (end < m) {
                int curr = big[end];
                if (counts.containsKey(curr)) {
                    counts.put(curr, counts.getOrDefault(curr, 0) + 1);
                    if (counts.get(curr) == 0) {
                        meets++;
                    }
                }
                while (meets == n) {
                    if (end - start + 1 < shortestLength) {
                        shortestStart = start;
                        shortestLength = end - start + 1;
                    }
                    int prev = big[start];
                    if (counts.containsKey(prev)) {
                        counts.put(prev, counts.get(prev) - 1);
                        if (counts.get(prev) < 0) {
                            meets--;
                        }
                    }
                    start++;
                }
                end++;
            }
            return shortestStart < 0 ? new int[0] :new int[]{shortestStart, shortestStart + shortestLength - 1};
        }
    }

}
