package leetcode.tree;
//给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
//
// 有效 二叉搜索树定义如下：
//
//
// 节点的左子树只包含 小于 当前节点的数。
// 节点的右子树只包含 大于 当前节点的数。
// 所有左子树和右子树自身必须也是二叉搜索树。
//
//
//
//
// 示例 1：
//
//
//输入：root = [2,1,3]
//输出：true
//
//
// 示例 2：
//
//
//输入：root = [5,1,4,null,null,3,6]
//输出：false
//解释：根节点的值是 5 ，但是右子节点的值是 4 。
//
//
//
//
// 提示：
//
//
// 树中节点数目范围在[1, 10⁴] 内
// -2³¹ <= Node.val <= 2³¹ - 1
//
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 👍 1619 👎 0


/**
 * @author mayanwei
 * @date 2022-06-11.
 */
public class _98_验证二叉搜索树{
    /**
     * <pre>
     * ┌─────────────────────────┐
     * │        ┌ ─ ┐            │
     * │          10             │
     * │        └ ─ ┘            │
     * │   ┌──────┴─────┐        │
     * │ ┌─▼─┐        ┌─▼─┐      │
     * │ │ 5 │        │ 15│      │
     * │ └───┘        └───┘      │
     * │          ┌─────┴─────┐  │
     * │        ┌ ▼ ┐       ┌─▼─┐│
     * │          6         │ 10││
     * │        └ ─ ┘       └───┘│
     * └─────────────────────────┘
     * 下面的错误在与只是检查了它的左右孩子节点是否符合左小右大的元素；
     * 但是根据BST的定义，root的整个左子树都要小于 root.val,整个右子树都要大于 root.val
     * </pre>
     */
    class Wrong{
        public boolean isValidBST(TreeNode root) {
            if (root == null) {
                return true;
            }
            // root 的左边更小
            if (root.left != null && root.left.val > root.val) {
                return false;
            }
            // root 的右边更大
            if (root.right != null && root.right.val < root.val) {
                return false;
            }
            return isValidBST(root.left) && isValidBST(root.right);
        }
    }

    /**
     * 对于一个root 只能管自己的左右节点，怎么把root 的约束传递给子树呢？
     */
    class Solution{
        public boolean isValidBST(TreeNode root) {
            if (root == null) {
                return true;
            }
            return isValidBST(root, null, null);
        }

        //限定以root 为根的结点必须满足 l.val < root.val < g.val
        //通过使用辅助函数，增减函数参数列表，在参数中携带额外信息，将这种约束传递给子树的所有节点，这也是二叉树算法的一个小技巧
        public boolean isValidBST(TreeNode root, TreeNode l, TreeNode g) {
            // base case
            if (root == null) {
                return true;
            }
            // 若 root 不符合 l 和 r 的限制，说明不是合法的 BST
            if (l != null && l.val >= root.val) {
                return false;
            }
            if (g != null && g.val <= root.val) {
                return false;
            }
            // 限定左子树的最大值为 root.val, 右子树的最小值为 root.val
            return isValidBST(root.left, l, root) && isValidBST(root.right, root, g);
        }
    }
}
