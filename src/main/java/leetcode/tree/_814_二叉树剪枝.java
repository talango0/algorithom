package leetcode.tree;
//给你二叉树的根结点 root ，此外树的每个结点的值要么是 0 ，要么是 1 。
//
// 返回移除了所有不包含 1 的子树的原二叉树。
//
// 节点 node 的子树为 node 本身加上所有 node 的后代。
//
//
//
// 示例 1：
//
//
//输入：root = [1,null,0,0,1]
//输出：[1,null,0,null,1]
//解释：
//只有红色节点满足条件“所有不包含 1 的子树”。 右图为返回的答案。
//
//
// 示例 2：
//
//
//输入：root = [1,0,1,0,0,0,1]
//输出：[1,null,1,null,1]
//
//
// 示例 3：
//
//
//输入：root = [1,1,0,1,1,0,1,0]
//输出：[1,1,0,1,1,null,1]
//
//
//
//
// 提示：
//
//
// 树中节点的数目在范围 [1, 200] 内
// Node.val 为 0 或 1
//
//
// Related Topics 树 深度优先搜索 二叉树 👍 309 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-10-30.
 */
public class _814_二叉树剪枝{
    class Solution{
        // 定义：输入一颗二叉树，返回的二叉树叶子节点都是1
        public TreeNode pruneTree(TreeNode root) {
            if (root == null) {
                return null;
            }
            // 二叉树的遍历框架
            root.left = pruneTree(root.left);
            root.right = pruneTree(root.right);
            // 后序遍历的位置，判断自己是否是值为 0 的叶子节点
            if (root.val == 0 && root.left == null && root.right == null) {
                // 返回值会被父节点接收，相当于把自己删掉了
                return null;
            }
            // 如果不是，正常返回
            return root;
        }
    }
}
