package leetcode.tree;

/**
 * 236和 1676 都明确告诉了我们这些节点都必定存在于二叉树中，如果没有这个前提条件，就需要调整了，比如这道
 * @see _236_二叉树的最近公共祖先
 * @see _1676_二叉树的最近公共祖先4
 */
public class _1644_二叉树的最近公共祖先2 {
    boolean foundP = false, foundQ = false;
    private TreeNode find(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        //因为 p 和 q 可能不在树中，因此要对数进行完全搜索（遍历每一个节点）
//        if ((root.val == p.val || root.val == q.val)) {
//            return root;
//        }
        TreeNode left = find(root, p, q);
        TreeNode right = find(root, p, q);

        //后序位置：如果左右子树中均存在，则返回
        if (left != null && right != null) {
            return root;
        }

        //后序位置：判断当前节点是不是目标值
        if (root.val == p.val || root.val == q.val) {
            //找到了，标记一下是哪一个
            foundP = root.val == p.val ? true:false;
            foundQ = root.val == q.val ? true:false;
            return root;
        }
        return left != null ? left:right;
    }

    /**
     * 对二叉树进行完全搜索，同时记录 p 和 q 是否存在树中。
     * 下面在改一下题目，让在二叉搜索树中寻找 p 和 q 的最近公共祖先，应该如何做，见 235
     * @param root
     * @param p
     * @param q
     * @return
     */
    TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode res = find(root, p, q);
        if (!foundP || !foundQ) {
            return null;
        }
        //p和q都存在二叉树中，才有公共祖先
        return res;
    }
}
