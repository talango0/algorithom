package leetcode.程序员面试金典;
//你正在使用一堆木板建造跳水板。有两种类型的木板，其中长度较短的木板长度为shorter，长度较长的木板长度为longer。
// 你必须正好使用k块木板。编写一个方法，生成跳水板所有可能的长度。
//
//返回的长度需要从小到大排列。
//
//示例 1
//
//输入：
//shorter = 1
//longer = 2
//k = 3
//输出： [3,4,5,6]
//解释：
//可以使用 3 次 shorter，得到结果 3；使用 2 次 shorter 和 1 次 longer，得到结果 4 。以此类推，得到最终结果。
//提示：
//
//0 < shorter <= longer
//0 <= k <= 100000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/diving-board-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import ch.qos.logback.core.pattern.Converter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author mayanwei
 * @date 2023-06-10.
 */
public class _16_11_跳水板{
    class Solution{
        public int[] divingBoard(int shorter, int longer, int k) {
            HashSet<Integer> lengths = new HashSet<>();
            for (int nShorter = 0; nShorter <= k; nShorter++) {
                int nLonger = k - nShorter;
                int length = nShorter * shorter + nLonger * longer;
                lengths.add(length);
            }
            return lengths.stream().mapToInt(Integer::intValue).toArray();
        }
    }

    class Solution1{
        public int[] divingBoard(int shorter, int longer, int k) {
            if (k == 0) {
                return new int[0];
            }
            if (shorter == longer) {
                return new int[]{shorter * k};
            }
            int[] ans = new int[k + 1];
            ans[0] = shorter * k;
            for (int i = 1; i <= k; i++) {
                ans[i] = ans[i - 1] + longer - shorter;
            }
            return ans;
        }
    }

    class Solution2{
        public int[] divingBoard(int shorter, int longer, int k) {
            HashSet<Integer> lengths = new HashSet<>();
            getAllLengths(k, 0, shorter, longer, lengths);
            return lengths.stream().mapToInt(Integer::intValue).toArray();
        }

        private void getAllLengths(int k, int total,
                                   int shorter, int longer, HashSet<Integer> lengths) {
            if (k == 0) {
                lengths.add(total);
                return;
            }
            getAllLengths(k - 1, total + shorter, shorter, longer, lengths);
            getAllLengths(k - 1, total + longer, shorter, longer, lengths);
        }
    }

    class Solution3{
        public int[] divingBoard(int shorter, int longer, int k) {
            if (k == 0) {
                return new int[0];
            }
            if (shorter == longer) {
                return new int[]{shorter * k};
            }
            HashSet<Integer> lengths = new HashSet<>();
            HashSet<String> visited = new HashSet<>();
            getAllLengths(k, 0, shorter, longer, lengths, visited);
            return lengths.stream().mapToInt(Integer::intValue).toArray();
        }

        private void getAllLengths(int k, int total,
                                   int shorter, int longer,
                                   HashSet<Integer> lengths, HashSet<String> visited) {
            if (k == 0) {
                lengths.add(total);
                return;
            }
            String key = k + " " + total;
            if (visited.contains(key)) {
                return;
            }

            getAllLengths(k - 1, total + shorter, shorter, longer, lengths, visited);
            getAllLengths(k - 1, total + longer, shorter, longer, lengths, visited);
            visited.add(key);
        }
    }


    @Test
    public void test() {
        Solution3 solution = new Solution3();
        int[] ints = solution.divingBoard(1, 2, 3);
        System.out.println(Arrays.toString(ints));
    }
}
