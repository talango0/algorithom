package leetcode.jzhoffer;

import leetcode.tree.TreeNode;
import leetcode.tree._285_二叉搜索树中的中序后继节点;
import leetcode.tree._700_二叉搜索树中的搜索;
//给定一棵二叉搜索树和其中的一个节点 p ，找到该节点在树中的中序后继。如果节点没有中序后继，请返回 null 。
//
// 节点 p 的后继是值比 p.val 大的节点中键值最小的节点，即按中序遍历的顺序节点 p 的下一个节点。
//
//
//
// 示例 1：
//
//
//
//
//输入：root = [2,1,3], p = 1
//输出：2
//解释：这里 1 的中序后继是 2。请注意 p 和返回值都应是 TreeNode 类型。
//
//
// 示例 2：
//
//
//
//
//输入：root = [5,3,6,2,4,null,null,1], p = 6
//输出：null
//解释：因为给出的节点没有中序后继，所以答案就返回 null 了。
//
//
//
//
// 提示：
//
//
// 树中节点的数目在范围 [1, 10⁴] 内。
// -10⁵ <= Node.val <= 10⁵
// 树中各节点的值均保证唯一。
//
//
//
//
//
// 注意：本题与主站 285 题相同： https://leetcode-cn.com/problems/inorder-successor-in-bst/
//
//
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 👍 65 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-11-01.
 * @see _285_二叉搜索树中的中序后继节点
 * @see _700_二叉搜索树中的搜索
 */
public class 剑指_Offer_II_053_二叉搜索树中的中序后继{
    /**
     * 一个拍脑袋的解法就是去中序遍历所有节点获得有序结果，从而找到 successor，但这样时间复杂度是 O(N)，
     * 格局就低了。一般来说 BST 的算法都希望我们的复杂度保持在 O(logN)。
     */
    class Solution{
        public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
            if (root == null) {
                return null;
            }
            TreeNode successor = null;
            if (root.val > p.val) {
                //父节点收到null的话说明自己是successor
                successor = inorderSuccessor(root.left, p);
                if (successor == null) {
                    successor = root;
                }
            }
            if (root.val < p.val) {
                successor = inorderSuccessor(root.right, p);
            }
            if (root.val == p.val) {
                // 我是目标节点我的successor要么是右子树的最小节点，要么是父节点
                successor = findMindNode(root.right);
            }

            return successor;
        }

        private TreeNode findMindNode(TreeNode p) {
            while (p != null && p.left != null) {
                p = p.left;
            }
            return p;
        }
    }

    class Solution2{
        //精简代码装逼版解法
        public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
            if (root == null) {
                return null;
            }
            if (root.val > p.val) {
                TreeNode successor = inorderSuccessor(root.left, p);
                return successor == null ? root :successor;
            }
            // root.val == p.val || root.val < p.val
            return inorderSuccessor(root.right, p);
        }

    }
}
