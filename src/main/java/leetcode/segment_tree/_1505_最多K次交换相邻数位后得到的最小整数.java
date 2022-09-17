package leetcode.segment_tree;

import inductiontoalgorithm.segment_tree.SegmentTree;

import java.util.LinkedList;
import java.util.Queue;
//给你一个字符串 num 和一个整数 k 。其中，num 表示一个很大的整数，字符串中的每个字符依次对应整数上的各个 数位 。
//
// 你可以交换这个整数相邻数位的数字 最多 k 次。
//
// 请你返回你能得到的最小整数，并以字符串形式返回。
//
//
//
// 示例 1：
//
//
//
//
//输入：num = "4321", k = 4
//输出："1342"
//解释：4321 通过 4 次交换相邻数位得到最小整数的步骤如上图所示。
//
//
// 示例 2：
//
//
//输入：num = "100", k = 1
//输出："010"
//解释：输出可以包含前导 0 ，但输入保证不会有前导 0 。
//
//
// 示例 3：
//
//
//输入：num = "36789", k = 1000
//输出："36789"
//解释：不需要做任何交换。
//
//
// 示例 4：
//
//
//输入：num = "22", k = 22
//输出："22"
//
//
// 示例 5：
//
//
//输入：num = "9438957234785635408", k = 23
//输出："0345989723478563548"
//
//
//
//
// 提示：
//
//
// 1 <= num.length <= 30000
// num 只包含 数字 且不含有 前导 0 。
// 1 <= k <= 10^9
//
//
// Related Topics 贪心 树状数组 线段树 字符串 👍 72 👎 0


/**
 * @author mayanwei
 * @date 2022-09-17.
 * @see SegmentTree
 */
public class _1505_最多K次交换相邻数位后得到的最小整数{
    class Solution{
        /**
         * 表示 0 - 9  10个数
         */
        private final int N = 10;

        public String minInteger(String num, int k) {
            int n = num.length();
            Queue<Integer>[] pos = new Queue[N];
            for (int i = 0; i < 10; ++i) {
                pos[i] = new LinkedList<Integer>();
            }
            for (int i = 0; i < n; ++i) {
                pos[num.charAt(i) - '0'].offer(i + 1);
            }
            StringBuffer ans = new StringBuffer();
            BIT bit = new BIT(n);
            for (int i = 1; i <= n; ++i) {
                for (int j = 0; j < N; ++j) {
                    if (!pos[j].isEmpty()) {
                        int behind = bit.query(pos[j].peek() + 1, n);
                        int dist = pos[j].peek() + behind - i;
                        if (dist <= k) {
                            bit.update(pos[j].poll());
                            ans.append(j);
                            k -= dist;
                            break;
                        }
                    }
                }
            }
            return ans.toString();
        }
    }

    /**
     * 树状数组
     */
    class BIT{
        int n;
        int[] tree;

        public BIT(int n) {
            this.n = n;
            this.tree = new int[n + 1];
        }


        public int lowbit(int x) {
            return x & (-x);
        }

        public void update(int x) {
            while (x <= n) {
                ++tree[x];
                x += lowbit(x);
            }
        }

        /**
         * 13 = 2^3 + 2^2 + 2^0
         *
         * prefixSum(13) = prefixSum(0b00001101)
         * = BIT[13] + BIT[12] + BIT[8]
         * = BIT[0b00001101] + BIT[0b00001100] + BIT[0b00001000]
         *
         * x = 13 = 0b00001101
         * -x = -13 = 0b11110011
         * x & (-x) = 0b00000001
         * x - (x & (-x)) = 0b00001100
         * @param x
         * @return
         */
        public int query(int x) {
            int ans = 0;
            while (x > 0) {
                ans += tree[x];
                x -= lowbit(x);
            }
            return ans;
        }

        public int query(int x, int y) {
            return query(y) - query(x - 1);
        }
    }

}
