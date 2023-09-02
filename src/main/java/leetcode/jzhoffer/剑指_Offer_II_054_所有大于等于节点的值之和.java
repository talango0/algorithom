package leetcode.jzhoffer;
//给定一个二叉搜索树，请将它的每个节点的值替换成树中大于或者等于该节点值的所有节点值之和。
//
//
//
// 提醒一下，二叉搜索树满足下列约束条件：
//
//
// 节点的左子树仅包含键 小于 节点键的节点。
// 节点的右子树仅包含键 大于 节点键的节点。
// 左右子树也必须是二叉搜索树。
//
//
//
//
// 示例 1：
//
//
//
//
//输入：root = [4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
//输出：[30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
//
//
// 示例 2：
//
//
//输入：root = [0,null,1]
//输出：[1,null,1]
//
//
// 示例 3：
//
//
//输入：root = [1,0,2]
//输出：[3,3,2]
//
//
// 示例 4：
//
//
//输入：root = [3,2,4,1]
//输出：[7,9,4,10]
//
//
//
//
// 提示：
//
//
// 树中的节点数介于 0 和 10⁴ 之间。
// 每个节点的值介于 -10⁴ 和 10⁴ 之间。
// 树中的所有值 互不相同 。
// 给定的树为二叉搜索树。
//
//
//
//
//
// 注意：
//
//
// 本题与主站 538 题相同： https://leetcode-cn.com/problems/convert-bst-to-greater-tree/
//
// 本题与主站 1038 题相同：https://leetcode-cn.com/problems/binary-search-tree-to-
//greater-sum-tree/
//
//
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 👍 41 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import leetcode.tree.TreeNode;
import leetcode.tree._1038_从二叉搜索树到累加树;
import leetcode.tree._230_二叉搜索树第k小的元素;
import leetcode.tree._538_把二叉搜索树转化为累加树;

/**
 * @author mayanwei
 * @date 2022-11-01.
 * @see _538_把二叉搜索树转化为累加树
 * @see _1038_从二叉搜索树到累加树
 * @see _230_二叉搜索树第k小的元素
 *
 */
public class 剑指_Offer_II_054_所有大于等于节点的值之和{
    /**
     * <pre>
     *           ┌───┐
     *           │ 4 │ 30
     *           └───┘
     *      ┌──────┴───────┐
     *    ┌─▼─┐          ┌─▼─┐
     *    │ 1 │ 36       │ 6 │ 21
     *    └───┘          └───┘
     *   ┌──┴───┐       ┌──┴───┐
     * ┌─▼─┐  ┌─▼─┐   ┌─▼─┐  ┌─▼─┐
     * │ 0 │36│ 2 │35 │ 5 │26│ 7 │ 15
     * └───┘  └───┘   └───┘  └───┘
     *          └──┐           └───┐
     *           ┌─▼─┐           ┌─▼─┐
     *           │ 3 │33         │ 8 │ 8
     *           └───┘           └───┘
     * 维护一个外部累加变量 sum，在遍历 BST 的过程中增加 sum，同时把 sum 赋值给 BST 中的每一个节点，就将 BST 转化成累加树了。
     * 但是注意顺序，正常的中序遍历顺序是先左子树后右子树，这里需要反过来，先右子树后左子树。
     * </pre>
     */
    class Solution{
        public TreeNode convertBST(TreeNode root) {
            traverse(root);
            return root;
        }

        int sum = 0;

        public void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            traverse(root.right);
            sum += root.val;
            root.val = sum;
            traverse(root.left);
        }
    }
}
