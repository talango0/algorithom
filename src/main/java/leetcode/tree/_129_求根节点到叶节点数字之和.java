package leetcode.tree;
//给你一个二叉树的根节点 root ，树中每个节点都存放有一个 0 到 9 之间的数字。
//
//
//
// 每条从根节点到叶节点的路径都代表一个数字：
//
//
//
//
// 例如，从根节点到叶节点的路径 1 -> 2 -> 3 表示数字 123 。
//
//
// 计算从根节点到叶节点生成的 所有数字之和 。
//
// 叶节点 是指没有子节点的节点。
//
//
//
// 示例 1：
//
//
//输入：root = [1,2,3]
//输出：25
//解释：
//从根到叶子节点路径 1->2 代表数字 12
//从根到叶子节点路径 1->3 代表数字 13
//因此，数字总和 = 12 + 13 = 25
//
// 示例 2：
//
//
//输入：root = [4,9,0,5,1]
//输出：1026
//解释：
//从根到叶子节点路径 4->9->5 代表数字 495
//从根到叶子节点路径 4->9->1 代表数字 491
//从根到叶子节点路径 4->0 代表数字 40
//因此，数字总和 = 495 + 491 + 40 = 1026
//
//
//
//
// 提示：
//
//
// 树中节点的数目在范围 [1, 1000] 内
// 0 <= Node.val <= 9
// 树的深度不超过 10
//
//
// Related Topics 树 深度优先搜索 二叉树 👍 589 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-10-31.
 */
public class _129_求根节点到叶节点数字之和{
    class Solution{
        /**
         * 二叉树的递归分为 【遍历】 和 【分解问题】 两种思维模式
         */
        StringBuilder path = new StringBuilder();
        int res = 0;

        public int sumNumbers(TreeNode root) {
            // 遍历一遍二叉树就能出结果
            traverse(root);
            return res;
        }

        private void traverse(TreeNode node) {
            if (node == null) {
                return;
            }
            //前序遍历的位置，记录节点值
            path.append(node.val);
            if (node.left == null && node.right == null) {
                // 到达叶子节点，累加路径和
                res += Integer.parseInt(path.toString());
            }
            // 二叉树递归框架，遍历左右子树
            traverse(node.left);
            traverse(node.right);
            path.deleteCharAt(path.length() - 1);
        }
    }


    class Solution2{
        //深度优先遍历  时间复杂度O(n)  空间复杂度O(n)
        public int sumNumbers(TreeNode root) {
            return dfs(root, 0);
        }

        public int dfs(TreeNode root, int prevSum) {
            if (root == null) {
                return 0;
            }
            int sum = prevSum * 10 + root.val;
            if (root.left == null && root.right == null) {
                return sum;
            }
            else {
                return dfs(root.left, sum) + dfs(root.right, sum);
            }
        }
    }
}
