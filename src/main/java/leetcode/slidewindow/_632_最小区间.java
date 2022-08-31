package leetcode.slidewindow;
//你有 k 个 非递减排列 的整数列表。找到一个 最小 区间，使得 k 个列表中的每个列表至少有一个数包含在其中。
//
// 我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
//
//
//
// 示例 1：
//
//
//输入：nums = [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
//输出：[20,24]
//解释：
//列表 1：[4, 10, 15, 24, 26]，24 在区间 [20,24] 中。
//列表 2：[0, 9, 12, 20]，20 在区间 [20,24] 中。
//列表 3：[5, 18, 22, 30]，22 在区间 [20,24] 中。
//
//
// 示例 2：
//
//
//输入：nums = [[1,2,3],[1,2,3],[1,2,3]]
//输出：[1,1]
//
//
//
//
// 提示：
//
//
// nums.length == k
// 1 <= k <= 3500
// 1 <= nums[i].length <= 50
// -10⁵ <= nums[i][j] <= 10⁵
// nums[i] 按非递减顺序排列
//
//
//
//
// Related Topics 贪心 数组 哈希表 排序 滑动窗口 堆（优先队列） 👍 370 👎 0


import java.util.*;

/**
 * @author mayanwei
 * @date 2022-08-31.
 */
public class _632_最小区间{
    class Solution{
        //哈希表 + 滑动窗口
        public int[] smallestRange(List<List<Integer>> nums) {
            int size = nums.size();
            Map<Integer, List<Integer>> indices = new HashMap<Integer, List<Integer>>();
            int xMin = Integer.MAX_VALUE, xMax = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                for (int x : nums.get(i)) {
                    List<Integer> list = indices.getOrDefault(x, new ArrayList<Integer>());
                    list.add(i);
                    indices.put(x, list);
                    xMin = Math.min(xMin, x);
                    xMax = Math.max(xMax, x);
                }
            }
            int[] freq = new int[size];
            int inside = 0;
            int left = xMin, right = xMin - 1;
            int ansL = xMin, ansR = xMax;
            while (right < xMax) {
                right++;
                if (indices.containsKey(right)) {
                    for (int x : indices.get(right)) {
                        freq[x]++;
                        if (freq[x] == 1) {
                            inside++;
                        }
                    }
                    while (inside == size) {
                        if (right - left < ansR - ansL) {
                            ansL = left;
                            ansR = right;
                        }

                        if (indices.containsKey(left)) {
                            for (int x : indices.get(left)) {
                                freq[x]--;
                                if (freq[x] == 0) {
                                    inside--;
                                }
                            }
                        }
                        left++;
                    }
                }
            }
            return new int[]{ansL, ansR};
        }
    }


    class Solution2{
        //贪心+最小堆
        public int[] smallestRange(List<List<Integer>> nums) {
            int rangeLeft = 0, rangeRight = Integer.MAX_VALUE;
            int minRange = rangeRight - rangeLeft;
            int max = Integer.MIN_VALUE;
            int size = nums.size();
            int[] next = new int[size];
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>((a, b) -> nums.get(a).get(next[a]) - nums.get(b).get(next[b]));

            for (int i = 0; i < size; i++) {
                priorityQueue.offer(i);
                max = Math.max(max, nums.get(i).get(0));
            }

            while (true) {
                int minIndex = priorityQueue.poll();
                int curRange = max - nums.get(minIndex).get(next[minIndex]);
                if (curRange < minRange) {
                    minRange = curRange;
                    rangeLeft = nums.get(minIndex).get(next[minIndex]);
                    rangeRight = max;
                }
                next[minIndex]++;
                if (next[minIndex] == nums.get(minIndex).size()) {
                    break;
                }
                priorityQueue.offer(minIndex);
                max = Math.max(max, nums.get(minIndex).get(next[minIndex]));
            }
            return new int[]{rangeLeft, rangeRight};
        }
    }


    /**
     * 执行用时： 100.00%
     * 内存消耗： 86.15%
     */
    class Solution3{
        class Node{
            public List<Integer> list;
            public Integer index;
            public Integer num;

            public Node(List<Integer> list, Integer index, Integer num) {
                this.list = list;
                this.index = index;
                this.num = num;
            }
        }

        public int[] smallestRange(List<List<Integer>> lists) {
            TreeSet<Node> set = new TreeSet<>(new Comparator<Node>(){
                @Override
                public int compare(Node o1, Node o2) {
                    return Objects.equals(o1.num, o2.num) ? o1.list.hashCode() - o2.list.hashCode() :o1.num - o2.num;

                }
            });
            for (List<Integer> list : lists) {
                set.add(new Node(list, 0, list.get(0)));
            }
            int less = set.first().num;
            int more = set.last().num;
            while (true) {
                Node least = set.pollFirst();
                if (least.index < least.list.size() - 1) {
                    set.add(new Node(least.list,
                            least.index + 1,
                            least.list.get(least.index + 1)));
                    if (set.last().num - set.first().num < more - less) {
                        more = set.last().num;
                        less = set.first().num;
                    }
                }
                else {
                    return new int[]{less, more};
                }
            }
        }
    }
}
