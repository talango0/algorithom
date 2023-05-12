package leetcode.tree;
//给出二叉 搜索 树的根节点，该树的节点值各不相同，请你将其转换为累加树（Greater Sum Tree），使每个节点 node 的新值等于原树中大于或等于
// node.val 的值之和。
//
// 提醒一下，二叉搜索树满足下列约束条件：
//
//
// 节点的左子树仅包含键 小于 节点键的节点。
// 节点的右子树仅包含键 大于 节点键的节点。
// 左右子树也必须是二叉搜索树。
//
//
// 注意：本题和 1038: https://leetcode-cn.com/problems/binary-search-tree-to-greater-
//sum-tree/ 相同
//
// 示例 1：
//┌────────────────────────────────────────┐
//│                ┌───┐                   │
//│                │ 4 │30                 │
//│                └───┘                   │
//│        ┌─────────┴────────┐            │
//│      ┌─▼─┐              ┌─▼─┐          │
//│      │ 1 │36            │ 6 │ 21       │
//│      └───┘              └───┘          │
//│  ┌─────┴────┐       ┌─────┴─────┐      │
//│┌─▼─┐      ┌─▼─┐   ┌─▼─┐       ┌─▼─┐    │
//││ 0 │36    │ 2 │35 │ 5 │26     │ 7 │ 15 │
//│└───┘      └───┘   └───┘       └───┘    │
//│             └─────┐             └───┐  │
//│                 ┌─▼─┐             ┌─▼─┐│
//│                 │ 33│33         8 │ 8 ││
//│                 └───┘             └───┘│
//└────────────────────────────────────────┘
//
// 输入：[4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
//输出：[30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
//
//
// 示例 2：
//
// 输入：root = [0,null,1]
//输出：[1,null,1]
//
//
// 示例 3：
//
// 输入：root = [1,0,2]
//输出：[3,3,2]
//
//
// 示例 4：
//
// 输入：root = [3,2,4,1]
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
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 👍 730 👎 0

/**
 * @see _1038_从二叉搜索树到累加树
 * @see _230_二叉搜索树第k小的元素
 * @author mayanwei
 * @date 2022-06-11.
 */
public class _538_把二叉搜索树转化为累加树{

    /**
     * 思考：BST 的每个节点左小右大，既然累加和是计算大于等于当前值的所有元素之和，那么每个节点都去计算右子树的和，不就行了吗？
     * 这是不行的。对于一个节点来说，确实右子树都是比它大的元素，但问题是它的父节点也可能是比它大的元素呀？这个没法确定的，我们又没有触达父节点的指针，所以二叉树的通用思路在这里用不了。
     * 其实，正确的解法很简单，还是利用 BST 的中序遍历特性.
     * 这道题的本质上时BST 的中序遍历特性，我们在这里修改了递归顺序，降序遍历 BST 的元素值。
     */
    class Solution{
        public TreeNode convertBST(TreeNode root) {
            travers(root);
            return root;
        }

        int sum;

        public void travers(TreeNode root) {
            if (root == null) {
                return;
            }
            travers(root.right);
            // 维护累加和
            sum += root.val;
            // 将 BST 转化成累加树
            root.val = sum;
            travers(root.left);

        }
    }

}
