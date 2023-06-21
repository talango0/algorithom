package leetcode.程序员面试金典;
//给定一个放有字母和数字的数组，找到最长的子数组，且包含的字母和数字的个数相同。
//
//返回该子数组，若存在多个最长子数组，返回左端点下标值最小的子数组。若不存在这样的数组，返回一个空数组。
//
//示例 1:
//
//输入: ["A","1","B","C","D","2","3","4","E","5","F","G","6","7","H","I","J","K","L","M"]
//
//输出: ["A","1","B","C","D","2","3","4","E","5","F","G","6","7"]
//示例 2:
//
//输入: ["A","A"]
//
//输出: []
//提示：
//
//array.length <= 100000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/find-longest-subarray-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2023-06-21.
 */
public class _17_05_字母与数字{
    /**
     * <pre>
     * [A, B, A, A, A, B, B, B, A, B, A, A, B, B, A, A, A, A, A, A]
     * 需要寻找最长的子数组(subarray)，使其满足 count(A, subarray) = count(B, subarray)。
     * </pre>
     * 暴力法： 只需遍历所有的子数组，计算A和B（字母和数字）的数量，找出最长的一个即可。
     * 对此，稍作优化，可以从最长的子数组开始，只要找到符合条件的子数组，就返回它。
     * 负载读 O(n^2)
     */
    class Solution{
        public String[] findLongestSubarray(String[] array) {
            if (array == null || array.length == 0) {
                return new String[0];
            }
            for (int len = array.length; len > 1; len--) {
                for (int i = 0; i <= array.length - len; i++) {
                    if (hasEqualLettersNumbers(array, i, i + len)) {
                        return extractSubArray(array, i, i + len);
                    }
                }
            }
            return new String[0];
        }

        private String[] extractSubArray(String[] array, int start, int end) {
            return Arrays.copyOfRange(array, start, end);
        }

        private boolean hasEqualLettersNumbers(String[] array, int start, int end) {
            int counter = 0;
            for (int i = start; i < end; i++) {
                if (Character.isDigit(array[i].charAt(0))) {
                    counter++;
                }
                else {
                    counter--;
                }
            }
            return counter == 0;
        }
    }


    /**
     * <pre>
     * 最优解
     * ┌──────────────────────────────┐
     * │    a 1 a a a 1 | a 1 1 a 1 a │
     * │ #a 1 1 2 3 4 4 | 5 5 5 6 7 7 │
     * │ #1 0 1 1 1 1 2 | 2 3 4 4 5 5 │
     * └──────────────────────────────┘
     *  4-2 = 7-5 是有道理的，由于两处分别增加了相同数量的数字和字母
     * ┌─────────────────────────────────────────────────┐
     * │    a a a a 1 1 a 1 1 a a 1 a a 1 a  a  a  a  a  │
     * │ #a 1 2 3 4 4 4 5 5 5 6 7 7 8 9 9 10 11 12 13 14 │
     * │ #1 0 0 0 0 1 2 2 3 4 4 4 5 5 5 6 6  6  6  6  6  │
     * │ -  1 2 3 4 3 2 3 2 1 2 3 2 3 4 3 4  5  6  7  8  │
     * └─────────────────────────────────────────────────┘
     * 每当返回相同的差值是，即找到了一个相等的子数组。要找到最大的子数组，只需要找到
     * 两个相距最远的且具有相同差值的索引。
     *
     * </pre>
     */
    class Solution1{
        public String[] findLongestSubarray(String[] array) {
            int[] deltas = computeDeltaArray(array);
            int[] match = findLongestMath(deltas);
            return extractSubArray(array, match[0] + 1, match[1] + 1);
        }

        /**
         * 寻找具有最大返回的且具有相同差值的项目
         *
         * @param deltas
         * @return
         */
        private int[] findLongestMath(int[] deltas) {
            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(0, -1);
            int[] max = new int[2];
            for (int i = 0; i < deltas.length; i++) {
                if (!map.containsKey(deltas[i])) {
                    map.put(deltas[i], i);
                }
                else {
                    int match = map.get(deltas[i]);
                    int distance = i - match + 1;
                    int longest = max[1] - max[0] + 1;
                    if (distance > longest) {
                        max[1] = i;
                        max[0] = match;
                    }
                }
            }
            return max;
        }

        /**
         * 计算数字和字母的数量差值
         *
         * @param array
         * @return
         */
        private int[] computeDeltaArray(String[] array) {
            int[] deltas = new int[array.length];
            int delta = 0;
            for (int i = 0; i < array.length; i++) {
                if (Character.isLetter(array[i].charAt(0))) {
                    delta++;
                }
                else {
                    delta--;
                }
                deltas[i] = delta;
            }
            return deltas;
        }

        private String[] extractSubArray(String[] array, int start, int end) {
            return Arrays.copyOfRange(array, start, end);
        }
    }

    class Solution3{
        public String[] findLongestSubarray(String[] array) {
            Map<Integer, Integer> indices = new HashMap<Integer, Integer>();
            indices.put(0, -1);
            int sum = 0;
            int maxLength = 0;
            int startIndex = -1;
            int n = array.length;
            for (int i = 0; i < n; i++) {
                if (Character.isLetter(array[i].charAt(0))) {
                    sum++;
                }
                else {
                    sum--;
                }
                if (indices.containsKey(sum)) {
                    int firstIndex = indices.get(sum);
                    if (i - firstIndex > maxLength) {
                        maxLength = i - firstIndex;
                        startIndex = firstIndex + 1;
                    }
                }
                else {
                    indices.put(sum, i);
                }
            }
            if (maxLength == 0) {
                return new String[0];
            }
            String[] ans = new String[maxLength];
            System.arraycopy(array, startIndex, ans, 0, maxLength);
            return ans;
        }
    }

    class Solution4{
        public String[] findLongestSubarray(String[] array) {
            int length = array.length, currentCount = length, resultLength = -2, resultStartIndex = 0;
            int[] countFirstIndex = new int[2 * length + 1];
            countFirstIndex[currentCount] = 1;
            for (int i = 0; i < array.length; i++) {
                int index = countFirstIndex[currentCount += ((array[i].charAt(0) >> 6) * 2 - 1)];
                if (index == 0) {
                    countFirstIndex[currentCount] = i + 2;
                }
                else {
                    int currentLength = i - index;
                    if (currentLength > resultLength) {
                        resultLength = currentLength;
                        resultStartIndex = index - 1;
                    }
                }
            }
            String[] result = new String[resultLength += 2];
            System.arraycopy(array, resultStartIndex, result, 0, resultLength);
            return result;
        }
    }

    @Test
    public void test() {
        Solution1 solution = new Solution1();
        System.out.println(Arrays.toString(solution.findLongestSubarray(new String[]{"A", "A"})));
        Assert.assertEquals(new String[]{"A", "1"},
                solution.findLongestSubarray(new String[]{"A", "1"}));

        Assert.assertEquals(new String[]{"A", "1", "B", "C", "D", "2", "3", "4", "E", "5", "F", "G", "6", "7"},
                solution.findLongestSubarray(new String[]{"A", "1", "B", "C", "D", "2", "3", "4", "E", "5", "F", "G", "6", "7", "H", "I", "J", "K", "L", "M"}));
    }
}
