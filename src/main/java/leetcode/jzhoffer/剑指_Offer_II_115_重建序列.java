package leetcode.jzhoffer;
//给定一个长度为 n 的整数数组 nums ，其中 nums 是范围为 [1，n] 的整数的排列。还提供了一个 2D 整数数组sequences，其中sequences[i]是nums的子序列。
//检查 nums 是否是唯一的最短超序列 。最短 超序列 是 长度最短 的序列，并且所有序列sequences[i]都是它的子序列。对于给定的数组sequences，可能存在多个有效的 超序列 。
//
//例如，对于sequences = [[1,2],[1,3]]，有两个最短的 超序列 ，[1,2,3] 和 [1,3,2] 。
//而对于sequences = [[1,2],[1,3],[1,2,3]]，唯一可能的最短 超序列 是 [1,2,3] 。[1,2,3,4] 是可能的超序列，但不是最短的。
//如果 nums 是序列的唯一最短 超序列 ，则返回 true ，否则返回 false 。
//子序列 是一个可以通过从另一个序列中删除一些元素或不删除任何元素，而不改变其余元素的顺序的序列。
//
//
//
//示例 1：
//
//输入：nums = [1,2,3], sequences = [[1,2],[1,3]]
//输出：false
//解释：有两种可能的超序列：[1,2,3]和[1,3,2]。
//序列 [1,2] 是[1,2,3]和[1,3,2]的子序列。
//序列 [1,3] 是[1,2,3]和[1,3,2]的子序列。
//因为 nums 不是唯一最短的超序列，所以返回false。
//示例 2：
//
//输入：nums = [1,2,3], sequences = [[1,2]]
//输出：false
//解释：最短可能的超序列为 [1,2]。
//序列 [1,2] 是它的子序列：[1,2]。
//因为 nums 不是最短的超序列，所以返回false。
//示例 3：
//
//输入：nums = [1,2,3], sequences = [[1,2],[1,3],[2,3]]
//输出：true
//解释：最短可能的超序列为[1,2,3]。
//序列 [1,2] 是它的一个子序列：[1,2,3]。
//序列 [1,3] 是它的一个子序列：[1,2,3]。
//序列 [2,3] 是它的一个子序列：[1,2,3]。
//因为 nums 是唯一最短的超序列，所以返回true。
//
//
//提示：
//
//n == nums.length
//1 <= n <= 104
//nums是[1, n]范围内所有整数的排列
//1 <= sequences.length <= 104
//1 <= sequences[i].length <= 104
//1 <= sum(sequences[i].length) <= 105
//1 <= sequences[i][j] <= n
//sequences的所有数组都是 唯一 的
//sequences[i]是nums 的一个子序列
//
//
//注意：本题与主站 444题相同：https://leetcode-cn.com/problems/sequence-reconstruction/
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/ur2n8P
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.graph._444_序列重建;

import java.util.*;

/**
 * @see _444_序列重建
 */
public class 剑指_Offer_II_115_重建序列{
    class Solution{
        /**
         * <pre>
         * 1.假设nums数组是唯一最短超序列，必须完全符合其排列顺序才算验证成功。
         * 2.最小确定顺序是两个相邻的数之间的顺序。
         * 3.由于子序列未改变原序列的顺序，因此不可能出现数顺序颠倒的情况，无需对此判断。
         * 例如[5,1,4,2,3]，可以出现子序列[1,2,3]，但不可能出现子序列[4,1,2,3]。
         * 4.如果原序列中的相邻两数没有在子序列中出现相邻，说明当前无法确定唯一顺序。
         * 例如[5,1,4,2,3]，对于1的相邻数只找到[1,2],[1,3]而没有找到[1,4]，那么4的位置可能紧跟在1或2或3之后，无法确定4一定紧跟在1之后。
         *
         * 若没有找到之后，在子序列中出现该数但是相邻的另一个数与原序列不同，要么仍然无法确定唯一顺序，要么确定唯一顺序是紧跟在其他数之后，这两种情况都是错的。
         *
         * 5.最终验证思路为遍历子序列数组，若其中出现所有原序列中两两相邻的数仍然都相邻，则顺序完全一致。
         * </pre>
         */
        public boolean sequenceReconstruction(int[] nums, int[][] sequences) {
            int n = nums.length;
            int[] next = new int[n + 1];
            for (int i = 0; i < n - 1; i++) {
                next[nums[i]] = nums[i + 1];
            }
            for (int[] s : sequences) {
                int m = s.length;
                for (int i = 0; i < m - 1; i++) {
                    if (next[s[i]] > 0 && next[s[i]] == s[i + 1]) {
                        next[s[i]] = 0;
                        n--;
                    }
                }
            }
            return n == 1;
        }
    }

    class Solution2{
        /**
         * 拓扑排序
         * <p>
         * 把 sequences 看成一个有向图，若按照拓扑排序，入度为 0 的点只有一个，则一定第一个最短
         * 超序列，否则不是。
         * 因此可以理解为能不能转换成一个唯一序列
         */
        public boolean sequenceReconstruction(int[] nums, int[][] sequences) {
            // 转化成边
            Map<Integer, Set<Integer>> edge = new HashMap<>();
            // 记录入度
            int[] inDegree = new int[nums.length + 1];
            for (int[] sequence : sequences) {
                // 注意这里是一个子序列，有多个元素，不是两个
                for (int i = 1; i < sequence.length; i++) {
                    int from = sequence[i - 1];
                    int to = sequence[i];
                    // 判断是否有次边
                    if (edge.containsKey(from) && edge.get(from).contains(to)) {
                        continue;
                    }
                    edge.putIfAbsent(from, new HashSet<>());
                    edge.get(from).add(to);
                    inDegree[to]++;
                }
            }
            // 记录入度为0的点
            Queue<Integer> queue = new ArrayDeque<>();
            for (int i = 1; i <= nums.length; i++) {
                if (inDegree[i] == 0) {
                    queue.offer(i);
                }
            }
            while (!queue.isEmpty()) {
                // 存在多个入度为 0 的点，会有多个超序列，直接返回false
                if (queue.size() > 1) {
                    return false;
                }
                int from = queue.poll();
                Set<Integer> set = edge.get(from);
                if (set == null) {
                    continue;
                }
                // 和此点连通的点入度减1
                for (int point : set) {
                    inDegree[point]--;
                    if (inDegree[point] == 0) {
                        queue.add(point);
                    }
                }
            }
            return true;
        }
    }
}
