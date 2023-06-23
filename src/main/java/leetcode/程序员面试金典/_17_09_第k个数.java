package leetcode.程序员面试金典;
//有些数的素因子只有 3，5，7，请设计一个算法找出第 k 个数。注意，不是必须有这些素因子，而是必须不包含其他的素因子。
// 例如，前几个数按顺序应该是 1，3，5，7，9，15，21。
//
//示例 1:
//
//输入: k = 5
//
//输出: 9
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/get-kth-magic-number-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author mayanwei
 * @date 2023-06-23.
 */
public class _17_09_第k个数{
    /**
     * 蛮力法： 满足 3^a * 5^b * 7^c 这一形式的第k小哦的值，先试试蛮力法
     * 第 k 个最大值可以是  3^a * 5^b * 7^c  。因此，将a,b,c 赋值为0 和 k 之间的所有数，并计算 3^a * 5^b * 7^c 的值，将结果
     * 全部放入一个列表，对列表进行排序，然后选择第k小的值。
     * <p>
     * 时间复杂度： O(k^3 log (k^3))
     */
    class Solution{
        public int getKthMagicNumber(int k) {
            List<Integer> possibilities = allPosisibleFactors(k);
            Collections.sort(possibilities);
            return possibilities.get(k - 1);
        }

        private List<Integer> allPosisibleFactors(int k) {
            ArrayList<Integer> values = new ArrayList<>();
            for (int a = 0; a <= k; a++) { // 3 的循环
                int powA = (int) Math.pow(3, a);
                for (int b = 0; b <= k; b++) {
                    int powB = (int) Math.pow(5, b);
                    for (int c = 0; c <= k; c++) {
                        int powC = (int) Math.pow(7, c);
                        int value = powA * powB * powC;
                        // 检查溢出
                        if (value < 0 || powA == Integer.MAX_VALUE || powB == Integer.MAX_VALUE || powC == Integer.MAX_VALUE) {
                            value = Integer.MAX_VALUE;
                        }
                        values.add(value);
                    }
                }
            }
            return values;
        }
    }


    /**
     * <pre>
     * ┌────────────────────────────┐
     * │  1    -    3^0 * 5^0 * 7^0 │
     * │  3    3    3^1 * 5^0 * 7^0 │
     * │  5    5    3^0 * 5^1 * 7^0 │
     * │  7    7    3^0 * 5^0 * 7^1 │
     * │  9   3*3   3^2 * 5^0 * 7^0 │
     * │ 15   3*5   3^1 * 5^1 * 7^0 │
     * │ 21   3*7   3^1 * 5^0 * 7^1 │
     * │ 25   5*5   3^0 * 5^2 * 7^0 │
     * │ 27   3*9   3^3 * 5^0 * 7^1 │
     * │ 35   5*7   3^0 * 5^1 * 7^1 │
     * │ 45   5*9   3^2 * 5^1 * 7^0 │
     * │ 49   7*7   3^0 * 5^0 * 7^2 │
     * │ 63   3*21  3^2 * 5^0 * 7^1 │
     * └────────────────────────────┘
     * 无论下一个值是多少，我们称之为nv，将其除以3。这个数字出现过了吗？只要nv 的因数有 3，那么答案就是肯定的。
     * 除以 5 和除以 7 是一样的。
     *
     * 因此，A_k 可以表示为 （3, 5, 7) * ({A_1, ... ,A_{k-1}} 中的某个值)。
     * 根据定义 A_k 是列表中的下一个数字。因此 A_k 将是最小的“新”数字（{A_1,...,A_{k-1}}中以存在的数字），
     * 它可以通过将列表中的每个值乘以3、5、7来生成。
     *
     * 怎么找到 A_k？把列表中的每个元素乘以3、5、7，然后找到最小的还没有被添加到列表中的元素。这个解法的负载读为O(k^2)
     *
     * 还能更优，与试图从列表中“取出”一个存在元素（通过将他们全部乘以3、5、7）来计算A_k不同的是 ，我们可以考虑通过列表
     * 中存在的值“压入”三个后续值，也就是说，每个数字A_i最终将会以下列形式出现在列表中。
     * 3*A_i
     * 5*A_i
     * 7_A_i
     * 可以利用这一思路提前进行规划，每当在列表中添加一个数字A_i时，就会在某个临时列表中存入 3*A_i， 5*A_i，7_A_i。
     * 为了生成A_{i+1},我们通过临时列表查找最小值。
     * </pre>
     */
    class Solution2{
        public int getKthMagicNumber(int k) {
            if (k < 0) {
                return 0;
            }
            int val = 1;
            Queue<Integer> q = new LinkedList<>();
            addProducts(q, 1);
            for (int i = 0; i < k - 1; i++) {
                val = removeMin(q);
                addProducts(q, val);
            }
            return val;
        }

        private int removeMin(Queue<Integer> q) {
            int min = q.peek();
            for (Integer v : q) {
                if (min > v) {
                    min = v;
                }
            }
            while (q.contains(min)) {
                q.remove(min);
            }
            return min;
        }

        private void addProducts(Queue<Integer> q, int v) {
            q.add(v * 3);
            q.add(v * 5);
            q.add(v * 7);
        }
    }

    /**
     * <pre>
     * 最优解法：
     * ┌────────────────────────────────────────────┐
     * │ Q_36 = {3*A_4}                             │
     * │ Q_56 = {5*A_2, 5*A_4, 5*A_5}               │
     * │ Q_76 = {7*A_1, 7*A_2, 7*A_3, 7*A_4, 7*A_5} │
     * └────────────────────────────────────────────┘
     * y = min(Q3.head(),Q5.head(),Q7.head())
     * 计算得到 y,需要将3y 插入到 Q3,5y 插入到Q5，7y插入到Q7。
     * 但是，只有当这些元素不在另一个列表中时，才可进行插入操作。
     * 为什么像 3y 这样的数字会有可能已经存在于等待队列中?这是因为，如果 y 来自于 Q7，
     * 那么意味着 y = 7x，其中 x 是较小的数字。如果 7x 是最小值，那么我们一定已经在队列中遇到过 3x。
     * 看到 3x 时，做了些什么呢?我们将 7*3x 插入到了 Q7 当中。注意，7*3x = 3*7x = 3y。
     * 换句话说，如果从 Q7 中提取一个元素，那么该元素应形如 7*suffix，此时，我们已经处理了 3*suffix
     * 和 5*suffix 这两个元素。在处理 3*suffix 时，已经将 7*3*suffix 插入到 Q7 中。 在处理 5*suffix 时，
     * 已经在 Q7 中插入了 7*5*suffix。我们唯一还没有看到的值是 7*7*suffix，
     * 因此，只需在 Q7 中插入 7*7*suffix 即可。
     * </pre>
     */
    class Solution3{
        public int getKthMagicNumber(int k) {
            if (k < 0) {
                return 0;
            }
            int val = 0;
            LinkedList<Integer> queue3 = new LinkedList<>();
            LinkedList<Integer> queue5 = new LinkedList<>();
            LinkedList<Integer> queue7 = new LinkedList<>();
            queue3.add(1);
            ArrayList<Integer> res = new ArrayList<>();

            for (int i = 0; i < k; i++) {
                int v3 = queue3.size() > 0 ? queue3.peek() :Integer.MAX_VALUE;
                int v5 = queue5.size() > 0 ? queue5.peek() :Integer.MAX_VALUE;
                int v7 = queue7.size() > 0 ? queue7.peek() :Integer.MAX_VALUE;
                val = Math.min(v3, Math.min(v5, v7));
                res.add(val);
                if (val == v3) { // 加入到3、5、7的队列中
                    queue3.remove();
                    queue3.add(3 * val);
                    if (!queue5.contains(5 * val)) {
                        queue5.add(5 * val);
                    }
                }
                else if (val == v5) { // 加入到5，7的队列中
                    queue5.remove();
                    queue5.add(5 * val);
                }
                else if (val == v7) { // 加入到 7 的队列中
                    queue7.remove();
                }
                queue7.add(7 * val);

            }

            return val;
        }
    }

    /**
     * 最小堆
     */
    class Solution4 {
        public int getKthMagicNumber(int k) {
            int[] factors = {3, 5, 7};
            Set<Long> seen = new HashSet<Long>();
            PriorityQueue<Long> heap = new PriorityQueue<Long>();
            seen.add(1L);
            heap.offer(1L);
            int magic = 0;
            for (int i = 0; i < k; i++) {
                long curr = heap.poll();
                magic = (int) curr;
                for (int factor : factors) {
                    long next = curr * factor;
                    if (seen.add(next)) {
                        heap.offer(next);
                    }
                }
            }
            return magic;
        }
    }


    /**
     * 动态规划
     * dp[0] = 1, k = 1
     * dp[i] = min(dp[p3]*3, dp[p5]*5, dp[p7]*7), 2 <= i <= k
     */
    class Solution5{
        public int getKthMagicNumber(int k) {
            int[] dp = new int[k];
            dp[0] = 1;
            int p3 = 0;
            int p5 = 0;
            int p7 = 0;
            for (int i = 1; i < k; i++) {
                int num1 = dp[p3] * 3;
                int num2 = dp[p5] * 5;
                int num3 = dp[p7] * 7;
                int next = Math.min(num1, Math.min(num2, num3));
                dp[i] = next;
                if (num1 == next) {
                    p3++;
                }
                if (num2 == next) {
                    p5++;
                }
                if (num3 == next) {
                    p7++;
                }
            }
            return dp[k - 1];
        }
    }

    @Test
    public void test() {
        Solution4 solution = new Solution4();
        Assert.assertEquals(1937102445, solution.getKthMagicNumber(643));
    }
}
