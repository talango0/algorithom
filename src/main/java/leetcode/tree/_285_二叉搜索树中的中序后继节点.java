package leetcode.tree;
//Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
//
//The successor of a node p is the node with the smallest key greater than p.val.
//Example 1:
//
//
//
//Input: root = [2,1,3], p = 1
//Output: 2
//Explanation: 1's in-order successor node is 2. Note that both p and the return value is of TreeNode type.
//Example 2:
//
//
//
//Input: root = [5,3,6,2,4,null,null,1], p = 6
//Output: null
//Explanation: There is no in-order successor of the current node, so the answer is null
//Note:
//
//If the given node has no in-order successor in the tree, return null.
//It's guaranteed that the values of the tree are unique.
//
//

/**
 * @author mayanwei
 * @date 2022-10-03.
 */
public class _285_二叉搜索树中的中序后继节点{

    class Solution{
        /**
         * 题目要找到一个节点，该节点是大于p的所有节点中最小的那个。首先考虑BST中哪些节点大于p：
         * <p>
         * p的右子节点都大于p。
         * 从节点p向上寻找根节点，如果p是该根节点的左子节点，那么该根节点大于p。
         * 接下来，考虑所有大于p的节点中，哪个是最小的，根据上面2条结果：
         * <p>
         * 由于p的右子树中的节点都大于p，那么右子树中最小的一定是该子树最左边的元素。
         * 距离p最近的根节点，并且p是该根节点的左子树中的节点。
         * 对于上述1和2两个节点，由于节点p和节点p的右子树都是2中描述的根节点的左子节点，所以结果2一定大于结果1，因为取所有大于p中的最小值，
         * 因此优先返回结果1，即p的右子树中最靠左的元素，如果p的右子树为空，则需要返回结果2的根节点。
         */
        TreeNode inOrderSuccessor(TreeNode root, TreeNode p) {
            // 递归求解
            return help(root, null, p.val);
        }

        /**
         * @param root   当前节点
         * @param parent 距离当前节点最近的根节点
         * @param target 目标节点 p 的值
         * @return
         */
        private TreeNode help(TreeNode root, TreeNode parent, int target) {
            // 如果target大于当前节点，在当前节点的右子树中继续寻找
            if (target > root.val) {
                return help(root.right, parent, target);
            }
            // 如果 target 小于当前节点，在当前节点的做子树中继续寻找
            // 由于当前节点大于左子树的值，parent 为当前节点。
            else if (target < root.val) {
                return help(root.left, root, target);
            }

            /**下面就是当前节点为 p 的情况*/
            // 如果右节点不为空，返回右子树的最小的元素
            if (root.right != null) {
                return findMin(root.right);
            }
            // 如果右节点为空，返回 parent
            return parent;
        }

        /**
         * 找到当前节点中的最靠左的子节点。=
         *
         * @param root
         * @return
         */
        private TreeNode findMin(TreeNode root) {
            if (root.left == null) {
                return root;
            }
            return findMin(root.left);
        }
    }


    /**
     * 思路： 如果p比当前节点小，说明在左子树，res=root；否则去右子树搜索
     * 引申一下，如果求前面一个节点，也是一样做，只不过left right反一反。
     */
    class Solution2{
        TreeNode inOrderSuccessor(TreeNode root, TreeNode p) {
            if (root == null) {
                return null;
            }
            if (p.val < root.val) {
                TreeNode left = inOrderSuccessor(root.left, p);
                return left != null ? left :root;
            }
            else {
                return inOrderSuccessor(root.right, p);
            }
        }

    }

    /**
     * 迭代
     */
    class Solution3{
        TreeNode inOrderSuccessor(TreeNode root, TreeNode p) {
            TreeNode res = null;
            while (root != null) {
                if (p.val < root.val) {
                    res = root;
                    root = root.left;
                }
                else {
                    root = root.right;
                }
            }
            return res;
        }
    }
}
