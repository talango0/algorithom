package leetcode.程序员面试金典;
//有个马戏团正在设计叠罗汉的表演节目，一个人要站在另一人的肩膀上。出于实际和美观的考虑，在上面的人要比下面的人矮一点且轻一点。已知马戏团每个
// 人的身高和体重，请编写代码计算叠罗汉最多能叠几个人。
//
//示例：
//
//输入：height = [65,70,56,75,60,68] weight = [100,150,90,190,95,110]
//输出：6
//解释：从上往下数，叠罗汉最多能叠 6 层：(56,90), (60,95), (65,100), (68,110), (70,150), (75,190)
//提示：
//
//height.length == weight.length <= 10000
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/circus-tower-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-06-22.
 */
public class _17_08_马戏团人塔{
    /**
     * 递归法
     * 思路：我们可以尝试以某一项对所有元素进行排序，这实际上是有帮助的，但不能直接找到答案。
     * 对于元素高度排序，会得到一个所有元素该出现的相对顺序，不过任要以重量排序的最长递增序列。
     * <p>
     * 尝试所有的可能性。以高度进行排序，遍历数组，对于每一个元素，我们分为两个选择：将这个元素
     * 添加到子序列（如果情况有效）， 或不添加。
     * <p>
     * 这个算法将花费 O(2n)的时间。我们可以使用保存记录的方法(即缓存最好的序列)来优化 该算法。
     */
    class Solution{
        class HW{
            int height;
            int weight;

            public HW(int height, int weight) {
                this.height = height;
                this.weight = weight;
            }

            public boolean isBefore(HW value) {
                return this.height < value.height && this.weight < value.weight;
            }
        }

        public int bestSeqAtIndex(int[] height, int[] weight) {
            HW[] items = buildWH(height, weight);
            Arrays.sort(items, (o1, o2) -> {
                return o1.height == o2.height ?
                        ((Integer) o1.weight).compareTo(o2.weight) :
                        ((Integer) o1.height).compareTo(o2.height);
            });
            ArrayList<HW> res = bestSeqAtIndex(items, new ArrayList<HW>(), 0);
            return res.size();

        }

        private ArrayList<HW> bestSeqAtIndex(HW[] items, ArrayList<HW> sequences, int index) {
            if (index >= items.length) {
                return sequences;
            }
            HW value = items[index];
            ArrayList<HW> bestWith = null;
            if (canAppend(sequences, value)) {
                ArrayList<HW> sequenceWith = (ArrayList<HW>) sequences.clone();
                sequenceWith.add(value);
                bestWith = bestSeqAtIndex(items, sequenceWith, index + 1);
            }
            ArrayList<HW> bestWithout = bestSeqAtIndex(items, sequences, index + 1);
            return max(bestWith, bestWithout);
        }

        private ArrayList<HW> max(ArrayList<HW> seq1, ArrayList<HW> seq2) {
            if (seq1 == null) {
                return seq2;
            }
            else if (seq2 == null) {
                return seq1;
            }
            return seq1.size() > seq2.size() ? seq1 :seq2;
        }

        private boolean canAppend(ArrayList<HW> solution, HW value) {
            if (solution == null) {
                return false;
            }
            if (solution.size() == 0) {
                return true;
            }
            HW last = solution.get(solution.size() - 1);
            return last.isBefore(value);
        }

        private HW[] buildWH(int[] height, int[] weight) {
            int n = height.length;
            HW[] res = new HW[n];
            for (int i = 0; i < n; i++) {
                res[i] = new HW(height[i], weight[i]);
            }
            return res;
        }
    }


    /**
     * <pre>
     * 迭代法
     * 数组: 13, 14, 10, 11, 12
     * Longest(以A[0]结尾): 13
     * Longest (以A[1]结尾): 13, 14
     * Longest (以A[2]结尾): 10
     * Longest (以A[3]结尾): 10, 11
     * Longest (以A[4]结尾): 10, 11, 12
     * 一致以 A[0] ~ A[3] 位结尾的最长的子序列上，那么要找到 A[4]为结尾的最长子序列，则只需要
     * 将 A[4] 附加到可能的最长子序列上。
     * </pre>
     */
    class Solution1{
        class HW{
            int height;
            int weight;

            public HW(int height, int weight) {
                this.height = height;
                this.weight = weight;
            }

            public boolean isBefore(HW value) {
                return this.height < value.height && this.weight < value.weight;
            }
        }

        public int bestSeqAtIndex(int[] height, int[] weight) {
            HW[] items = buildWH(height, weight);
            Arrays.sort(items, (o1, o2) -> o1.height == o2.height ?
                    ((Integer) o1.weight).compareTo(o2.weight) :
                    ((Integer) o1.height).compareTo(o2.height));
            ArrayList<ArrayList<HW>> solutions = new ArrayList<>();
            ArrayList<HW> bestSequences = null;
            // 计算在每个元素截止的最长序列，跟踪记录总体上的最长序列。
            for (int i = 0; i < items.length; i++) {
                ArrayList<HW> longestAtIndex = bestSeqAtIndex(items, solutions, i);
                solutions.add(i, longestAtIndex);
                bestSequences = max(bestSequences, longestAtIndex);
            }
            return bestSequences.size();
        }

        private ArrayList<HW> max(ArrayList<HW> seq1, ArrayList<HW> seq2) {
            if (seq1 == null) {
                return seq2;
            }
            else if (seq2 == null) {
                return seq1;
            }
            return seq1.size() > seq2.size() ? seq1 :seq2;
        }


        // 计算在每个元素截止的最长序列
        private ArrayList<HW> bestSeqAtIndex(HW[] items, ArrayList<ArrayList<HW>> solutions, int index) {
            HW value = items[index];
            ArrayList<HW> bestSequence = new ArrayList<>();
            //寻找我们可以连接该元素的最长序列
            for (int i = 0; i < index; i++) {
                ArrayList<HW> solution = solutions.get(i);
                if (canAppend(solution, value)) {
                    bestSequence = max(solution, bestSequence);
                }
            }

            // 在尾部增添该元素
            ArrayList<HW> best = (ArrayList<HW>)bestSequence.clone();
            best.add(value);
            return best;
        }

        private boolean canAppend(ArrayList<HW> solution, HW value) {
            if (solution == null) {
                return false;
            }
            if (solution.size() == 0) {
                return true;
            }
            HW last = solution.get(solution.size() - 1);
            return last.isBefore(value);
        }

        private HW[] buildWH(int[] height, int[] weight) {
            int n = height.length;
            HW[] res = new HW[n];
            for (int i = 0; i < n; i++) {
                res[i] = new HW(height[i], weight[i]);
            }
            return res;
        }
    }
    
    
    class Solution2 {
        class Solution {
            private class HW implements Comparable<HW> {

                private final int height;
                private final int weight;

                public HW(int height, int weight) {
                    this.height = height;
                    this.weight = weight;
                }

                @Override
                public int compareTo(HW o) {
                    int diff = this.height - o.height;
                    if (diff != 0) {
                        return diff;
                    } else {
                        return o.weight - this.weight; // 倒序
                    }
                }
            }

            public int bestSeqAtIndex(int[] height, int[] weight) {
                HW[] mans = new HW[height.length];
                for (int i = 0; i < height.length; i++) {
                    mans[i] = new HW(height[i], weight[i]);
                }
                Arrays.sort(mans);

                // 定义 tails[i] 为长度为 i + 1 的最小体重
                int[] tails = new int[mans.length];
                int nextIndex = 0;

                for (int i = 0; i < mans.length; i++) {
                    int insertIndex = findInsertIndex(tails, 0, nextIndex - 1, mans[i].weight);
                    if (insertIndex == nextIndex) {
                        nextIndex++;
                    }
                    tails[insertIndex] = mans[i].weight;
                }

                return nextIndex;
            }

            private int findInsertIndex(int[] tails, int startIndex, int endIndex, int target) {
                if (startIndex > endIndex) {
                    return startIndex;
                }

                int i = startIndex, j = endIndex;
                while (i <= j) {
                    int mid = (i + j) >>> 1;
                    if (tails[mid] < target) {
                        i = mid + 1;
                    } else if (tails[mid] > target) {
                        j = mid - 1;
                    } else {
                        // tails[mid] == target
                        j = mid - 1;
                    }
                }

                // now: [j, i]
                return i;
            }
        }
    }

    class Solution3 {
        public int bestSeqAtIndex(int[] height, int[] weight) {
            int len = height.length;
            int[][] person = new int[len][2];
            for (int i = 0; i < len; ++i)
                person[i] = new int[]{height[i], weight[i]};
            Arrays.sort(person, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
            int[] dp = new int[len];
            int res = 0;
            for (int[] pair : person) {
                int i = Arrays.binarySearch(dp, 0, res, pair[1]);
                if (i < 0)
                    i = -(i + 1);
                dp[i] = pair[1];
                if (i == res)
                    ++res;
            }
            return res;
        }
    }



    @Test
    public void test() {
        int[] height = {65, 70, 56, 75, 60, 68}, weight = {100, 150, 90, 190, 95, 110};
        Solution solution = new Solution();
        Assert.assertEquals(solution.bestSeqAtIndex(height, weight), 6);
    }
}
