package leetcode.程序员面试金典;
//设计一个算法，找出二叉搜索树中指定节点的“下一个”节点（也即中序后继）。
//如果指定节点没有对应的“下一个”节点，则返回null。
//示例 1:
//输入: root = [2,1,3], p = 1
//  2
// / \
//1   3
//输出: 2
//
//示例 2:
//输入: root = [5,3,6,2,4,null,null,1], p = 6
//      5
//     / \
//    3   6
//   / \
//  2   4
// /
//1
//输出: null
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/successor-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.tree.TreeNode;
import leetcode.tree._285_二叉搜索树中的中序后继节点;

/**
 * @author mayanwei
 * @date 2023-05-07.
 * @see _285_二叉搜索树中的中序后继节点
 */
public class _04_06_后继者{
    class Solution{
        private TreeNode res;
        private TreeNode pre;
        boolean find = false;

        public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
            if (root == null) {
                return null;
            }
            inOrder(root, p);
            return res;
        }

        public void inOrder(TreeNode root, TreeNode p) {
            if (root == null || find) {
                return;
            }
            inOrder(root.left, p);
            if (!find && pre != null && pre.val == p.val) {
                res = root;
                find = true;
                return;
            }
            pre = root;
            inOrder(root.right, p);
        }
    }
}
