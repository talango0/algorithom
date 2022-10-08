package leetcode.tree;

//对于一棵深度小于 5 的树，可以用一组三位十进制整数来表示。
//
//对于每个整数：
//
//百位上的数字表示这个节点的深度 D，1 <= D <= 4。
//十位上的数字表示这个节点在当前层所在的位置 P， 1 <= P <= 8。位置编号与一棵满二叉树的位置编号相同。
//个位上的数字表示这个节点的权值 V，0 <= V <= 9。
//给定一个包含三位整数的升序数组，表示一棵深度小于 5 的二叉树，
//请你返回从根到所有叶子结点的路径之和。
//
//样例 1:
//输入: [113, 215, 221]
//输出: 12
//解释:
//这棵树形状如下:
//    3
//   / \
//  5   1
//路径和 = (3 + 5) + (3 + 1) = 12.
//
//
//样例 2:
//输入: [113, 221]
//输出: 4
//解释:
//这棵树形状如下:
//    3
//     \
//      1
//路径和 = (3 + 1) = 4.

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2022-10-08.
 * @see _113_路径总和II
 */
public class _666_路径和IV_树的遍历{

    /**
     * id = 2_{i-1} + p -1
     * dfs 遍历
     */
    class Solution{
        private Map<Integer, Integer> m = new HashMap<>();
        private int sum = 0;

        public int pathSum(int[] nums) {
            for (int num : nums) {
                m.put((1 << (num / 100 - 1)) + ((num / 10) % 10) - 1, num % 10);
            }
            dfs(1, 0);
            return sum;
        }

        /**
         * 从 root 编号1，s为0开始dfs
         *
         * @param p
         * @param s
         */
        private void dfs(int p, int s) {
            if (!m.keySet().contains(p)) {
                return;
            }
            s += m.get(p);
            boolean l = m.keySet().contains(left(p)), r = m.keySet().contains(right(p));
            if (!l && !r) {
                sum += s;
                return;
            }
            dfs(left(p), s);
            dfs(right(p), s);
        }

        private int right(int p) {
            return (p << 1) + 1;
        }

        private int left(int p) {
            return p << 1;
        }

    }

    @Test
    public void test() {
        Assert.assertEquals(new Solution().pathSum(new int[]{113, 221}), 4);
        Assert.assertEquals(new Solution().pathSum(new int[]{113, 215, 221}), 12);
    }
}
