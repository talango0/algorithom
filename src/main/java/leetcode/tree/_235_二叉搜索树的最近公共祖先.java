package leetcode.tree;
//给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
//
// 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（
//一个节点也可以是它自己的祖先）。”
//
// 例如，给定如下二叉搜索树: root = [6,2,8,0,4,7,9,null,null,3,5]
//
// 示例 1:
//
// 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
//输出: 6
//解释: 节点 2 和节点 8 的最近公共祖先是 6。
//
//
// 示例 2:
//
// 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
//输出: 2
//解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。
//
//
//
// 说明:
//
//
// 所有节点的值都是唯一的。
// p、q 为不同节点且均存在于给定的二叉搜索树中。
//
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 👍 881 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * 在标准的最近公共祖先问题中，我们要在后续位置通过左右子树的搜索结果来判断当前节点是不是LCA
 * <pre>
 * TreeNode left = find(root.left, val1, val2);
 * TreeNode right = find(root.right, val1, val2);
 * // 后序位置，判断当前节点是不是 LCA 节点
 * if (left != null && right != null) {
 *     return root;
 * }
 * </pre>
 * 但对于 BST 来说，根本不需要老老实实去遍历子树，由于 BST 左小右大的性质，将当前节点的值与val1和val2作对比即可判断当前节点是不是LCA：<p>
 * 假设val1 < val2，那么val1 <= root.val <= val2则说明当前节点就是LCA；<p>
 * 若root.val比val1还小，则需要去值更大的右子树寻找LCA；<p>
 * 若root.val比val2还大，则需要去值更小的左子树寻找LCA。<p>
 *
 * @see _236_二叉树的最近公共祖先
 * @see _1644_二叉树的最近公共祖先2
 * @see _1650_二叉树的最近公共祖先3
 * @see _1676_二叉树的最近公共祖先4
 */
public class _235_二叉搜索树的最近公共祖先{

    class Solution{
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            //保证val1 比 val2 小
            int val1 = Math.min(p.val, q.val);
            int val2 = Math.max(p.val, q.val);
            return find(root, val1, val2);
        }

        // 在 BST 中寻找 val1 和 val2 的最近公共祖先节点
        public TreeNode find(TreeNode root, int val1, int val2) {
            if (root == null) {
                return null;
            }
            // 当前节点就是最近公共祖先
            if (root.val == val1 || root.val == val2) {
                return root;
            }
            if (root.val > val2) {
                // 当前节点太大，去左子树找
                return find(root.left, val1, val2);
            }
            else if (root.val < val1) {
                // 当前节点太小，去右子树找
                return find(root.right, val1, val2);
            }
            return root;
        }

    }
}
