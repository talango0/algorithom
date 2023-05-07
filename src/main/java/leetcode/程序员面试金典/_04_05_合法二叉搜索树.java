package leetcode.程序员面试金典;
//实现一个函数，检查一棵二叉树是否为二叉搜索树。
//
//示例1:
//输入:
//    2
//   / \
//  1   3
//输出: true
//
//示例2:
//输入:
//    5
//   / \
//  1   4
//    / \
//   3   6
//输出: false
//解释: 输入为: [5,1,4,null,null,3,6]。
//    根节点的值为 5 ，但是其右子节点值为 4 。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/legal-binary-search-tree-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.tree.TreeNode;

/**
 * @author mayanwei
 * @date 2023-05-07.
 */
public class _04_05_合法二叉搜索树{
    /**
     * 提示：
     * 如果使用前序遍历来遍历树，元素的顺序是正确的，这是否表明树实际上是有序的？有重复元素会发生什么？如果允许重复元素，它们必须位于特定的一边（通常是左边）。
     * 作为一个二叉搜索树，并不是说每个节点都满足left.value <= current.value < right就够了。左边的每个节点必须小于当前节点，该节点还必须小于右边的所有节点。
     * 如果左边的每个节点必须小于或等于当前节点，那么这就等于左边最大的节点必须小于或等于当前节点。
     * 相比于根据leftTree.max和rightTree.min来验证当前节点的值，我们可以翻转逻辑吗？验证左子树的节点以确保其小于current.value。
     * 把checkBST函数当作一个递归函数，保证每个节点在允许范围内(最小, 最大)。首先，这个范围是无限的。当我们遍历左边，最小的是负无穷大，最大的是root.value。你能实现这个递归函数，并且随着遍历而适当调整这些范围吗？
     */

    class Solution{
        public boolean isValidBST(TreeNode root) {
            if (root == null) {
                return true;
            }
            return process(root).isBST;
        }

        public Info process(TreeNode x) {
            if (x == null) {
                return null;
            }
            Info leftInfo = process(x.left);
            Info rightInfo = process(x.right);
            int max = x.val;
            int min = x.val;
            if (leftInfo != null) {
                max = Math.max(leftInfo.max, max);
                min = Math.min(leftInfo.min, min);
            }
            if (rightInfo != null) {
                max = Math.max(rightInfo.max, max);
                min = Math.min(rightInfo.min, min);
            }
            boolean isBST = false;
            boolean leftIsBst = leftInfo == null ? true :leftInfo.isBST;
            boolean rightIsBst = rightInfo == null ? true :rightInfo.isBST;
            boolean leftMaxLessX = leftInfo == null ? true :(leftInfo.max < x.val);
            boolean rightMinMoreX = rightInfo == null ? true :(rightInfo.min > x.val);
            if (leftIsBst && rightIsBst && leftMaxLessX && rightMinMoreX) {
                isBST = true;
            }
            return new Info(isBST, max, min);
        }

        public class Info{
            public boolean isBST;
            public int max;
            public int min;

            public Info(boolean is, int ma, int mi) {
                isBST = is;
                max = ma;
                min = mi;
            }
        }
    }

    class Solution2{
        public boolean isValidBST(TreeNode root) {
            if (root == null) {
                return true;
            }
            return isValidBST(root, null, null);
        }

        //限定以root 为根的结点必须满足 l.val < root.val < g.val
        //通过使用辅助函数，增减函数参数列表，在参数中携带额外信息，将这种约束传递给子树的所有节点，这也是二叉树算法的一个小技巧
        public boolean isValidBST(TreeNode root, TreeNode l, TreeNode g) {
            if (root == null) {
                return true;
            }
            if (l != null && l.val >= root.val) {
                return false;
            }
            if (g != null && g.val <= root.val) {
                return false;
            }
            return isValidBST(root.left, l, root) && isValidBST(root.right, root, g);
        }
    }
}
