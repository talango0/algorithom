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
    class Solution {
        public boolean isValidBST(TreeNode root) {
            if (root == null) {
                return true;
            }
            return isValidBST(root, null, null);
        }

        //限定以root 为根的结点必须满足 l.val < root.val < g.val
        public boolean isValidBST(TreeNode root, TreeNode l, TreeNode g) {
            if (root == null) {
                return true;
            }
            if (l != null && l.val >= root.val){
                return false;
            }
            if (g != null && g.val <= root.val){
                return false;
            }
            return isValidBST(root.left, l, root) && isValidBST(root.right, root, g);
        }
    }
}
