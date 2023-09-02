package leetcode.tree;
//给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
//
// 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（
//一个节点也可以是它自己的祖先）。”
//
//
//
// 示例 1：
//
//
//输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
//输出：3
//解释：节点 5 和节点 1 的最近公共祖先是节点 3 。
//
//
// 示例 2：
//
//
//输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
//输出：5
//解释：节点 5 和节点 4 的最近公共祖先是节点 5 。因为根据定义最近公共祖先节点可以为节点本身。
//
//
// 示例 3：
//
//
//输入：root = [1,2], p = 1, q = 2
//输出：1
//
//
//
//
// 提示：
//
//
// 树中节点数目在范围 [2, 10⁵] 内。
// -10⁹ <= Node.val <= 10⁹
// 所有 Node.val 互不相同 。
// p != q
// p 和 q 均存在于给定的二叉树中。
//
// Related Topics 树 深度优先搜索 二叉树 👍 1842 👎 0

/**
 * @see _236_二叉树的最近公共祖先
 * @see _1644_二叉树的最近公共祖先2
 * @see _1650_二叉树的最近公共祖先3
 * @see _1676_二叉树的最近公共祖先4
 */
public class _236_二叉树的最近公共祖先{

    /**
     * 最常见的从二叉树中找一个值为 val 的节点
     */
    class NormalFindNodeInBinaryTree{
        //定义，在以 root 为根的二叉树中寻找值为 val 的节点。
        public TreeNode find(TreeNode root, int val) {
            // base case
            if (root == null) {
                return null;
            }
            // 看看 root.val 是不是要找的
            if (root.val == val) {
                return root;
            }
            // root 不是目标节点，去左子树中找
            TreeNode left = find(root.left, val);
            // 左子树找到了，返回值为目标节点
            if (left != null) {
                return left;
            }
            // 左左树没找到，去右右子树看看
            TreeNode right = find(root.right, val);
            // 右子树找到了，返回值为目标节点
            if (right != null) {
                return right;
            }
            return null;
        }

        // 下面改一下返回值地方, 这段代码可以可达到目的，但是实际运行效率会低一点，
        // 原因在于，如果能够在左子树中找到，就没有必要去右子树中找了。
        public TreeNode find1(TreeNode root, int val) {
            // base case
            if (root == null) {
                return null;
            }
            // 前序位置
            if (root.val == val) {
                return root;
            }
            // root 不是目标节点，左右子树寻找
            TreeNode left = find1(root.left, val);
            TreeNode right = find1(root.right, val);
            return left != null ? left :right;
        }

        // 下面改一下返回值地方, 这段代码可以可达到目的，运行效率会更低一点
        public TreeNode find2(TreeNode root, int val) {
            // base case
            if (root == null) {
                return null;
            }
            // root 不是目标节点，左右子树寻找
            TreeNode left = find(root.left, val);
            TreeNode right = find(root.right, val);
            // 后序位置，看看 root 是不是目标节点，就算根节点是目标节点，
            // 你也要去左右子树遍历完所有节点才能判断出来。
            if (root.val == val) {
                return root;
            }
            return left != null ? left :right;
        }

        // 改一下题目，现在不让你找值为 val 的节点，而是寻找值为 val1 或 val2 的节点
        public TreeNode find3(TreeNode root, int val1, int val2) {
            // base case
            if (root == null) {
                return null;
            }
            // 后序位置，看看 root 是不是目标节点，就算根节点是目标节点，
            // 你也要去左右子树遍历完所有节点才能判断出来。
            if (root.val == val1 || root.val == val2) {
                return root;
            }
            // root 不是目标节点，左右子树寻找
            TreeNode left = find3(root.left, val1, val2);
            TreeNode right = find3(root.right, val1, val2);
            // 已经知道了左右子树是否存在目标值
            return left != null ? left :right;
        }
    }

    class Solution{
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            return find(root, p.val, q.val);
        }

        /**
         * 在二叉树中寻找 val1, val2的最近公共祖先
         * <pre>
         * ┌──────────────────────────┐
         * │           ┌───┐          │
         * │           │ 3 │          │
         * │           └─┬─┘          │
         * │        ┌────┴────┐       │
         * │  	q   ▼ LCA     ▼       │
         * │     ┏━━━━┓    ┌────┐     │
         * │     ┃ 5  ┃    │ 1  │     │
         * │     ┗━━━━┛    └────┘     │
         * │        │                 │
         * │    ┌───┴──┐              │
         * │ ┌──▼─┐  ┌─▼─┐            │
         * │ │ 4  │  │ 6 │            │
         * │ └────┘  └─┬─┘            │
         * │           └────┐         │
         * │                ▼         │
         * │              ┌───┐       │
         * │              │ 6 │ p     │
         * │              └───┘       │
         * └──────────────────────────┘
         *
         * 说明一下这种情况，在find函数的前序位置，如果找到一个值为 val1 或 val2 的节点，就直接返回。
         * 因为题目中说了，p 和 q 一定存在于二叉树中（这点很重要），所以即使我们遇到q就直接返回，，根本没有遍历到p，也依然可以断定p在 q底下。
         * </pre>
         *
         * @param root
         * @param val1
         * @param val2
         * @return
         */
        private TreeNode find(TreeNode root, int val1, int val2) {
            if (root == null) {
                return null;
            }
            // 前序位置
            if (root.val == val1 || root.val == val2) {
                // 如果遇到目标值，直接返回
                return root;
            }
            TreeNode left = find(root.left, val1, val2);
            TreeNode right = find(root.right, val1, val2);
            // 后续位置，已经知道左右子树是否存在目标值
            if (left != null && right != null) {
                // 当前节点是 LCA 节点
                return root;
            }
            return left == null ? right :left;
        }
    }
}
