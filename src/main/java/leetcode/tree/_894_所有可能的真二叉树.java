package leetcode.tree;
//给你一个整数 n ，请你找出所有可能含 n 个节点的 真二叉树 ，并以列表形式返回。答案中每棵树的每个节点都必须符合 Node.val == 0 。
//
// 答案的每个元素都是一棵真二叉树的根节点。你可以按 任意顺序 返回最终的真二叉树列表。
//
// 真二叉树 是一类二叉树，树中每个节点恰好有 0 或 2 个子节点。
//
//
//
// 示例 1：
//
//
//输入：n = 7
//输出：[[0,0,0,null,null,0,0,null,null,0,0],[0,0,0,null,null,0,0,0,0],[0,0,0,0,0,0
//,0],[0,0,0,0,0,null,null,null,null,0,0],[0,0,0,0,0,null,null,0,0]]
//
//
// 示例 2：
//
//
//输入：n = 3
//输出：[[0,0,0]]
//
//
//
//
// 提示：
//
//
// 1 <= n <= 20
//
//
// Related Topics 树 递归 记忆化搜索 动态规划 二叉树 👍 277 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-10-20.
 */
public class _894_所有可能的真二叉树{
    class Solution{
        /**
         * 思路：想要生成一颗n个节点的满二叉树，首先要固定根节点，然后组装左右子树，根节点加上左右
         * 子树之和应该等于n。
         * 定义helper能够生成节点数为n的所有可能的二叉树，然后利用这个定义生成左右子树，然后
         * 通过子树组装出结果即可。
         */
        // 备忘录，记录n个节点能够组合成的所有可能的二叉树
        List<TreeNode>[] memo;

        public List<TreeNode> allPossibleFBT(int n) {
            if (n % 2 == 0) {
                return new ArrayList<>(0);
            }
            memo = new LinkedList[n + 1];
            return helper(n);
        }

        public List<TreeNode> helper(int n) {
            List<TreeNode> res = new LinkedList<>();
            // base case
            if (n == 1) {
                res.add(new TreeNode(0));
                return res;
            }
            // avoid repeat sub task
            if (memo[n] != null) {
                return memo[n];
            }
            // 递归生成所有符合条件的左右子树
            for (int i = 1; i < n; i += 2) {
                int j = n - i - 1;
                // 利用函数定义，生成左右子树
                List<TreeNode> leftSubTrees = helper(i);
                List<TreeNode> rightSubTrees = helper(j);
                for (TreeNode left : leftSubTrees) {
                    for (TreeNode right : rightSubTrees) {
                        // 生成根节点
                        TreeNode root = new TreeNode(0);
                        // 组装出一种可能的二叉树形状
                        root.left = left;
                        root.right = right;
                        // 加入结果列表
                        res.add(root);
                    }
                }
            }
            //存入备忘录
            memo[n] = res;
            return res;
        }
    }
}
