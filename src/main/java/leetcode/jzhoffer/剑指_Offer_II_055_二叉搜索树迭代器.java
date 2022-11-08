package leetcode.jzhoffer;

import leetcode.tree.TreeNode;
import leetcode.tree._173_二叉搜索树迭代器;

import java.util.LinkedList;

/**
 * @author mayanwei
 * @date 2022-11-01.
 * @see _173_二叉搜索树迭代器
 */
public class 剑指_Offer_II_055_二叉搜索树迭代器{
    class BSTIterator {
        LinkedList<Integer> list = new LinkedList<Integer>();
        public BSTIterator(TreeNode root) {
            dfs(root);
        }

        private void dfs(TreeNode root) {
            if (root==null) {
                return;
            }
            dfs(root.left);
            list.addLast(root.val);
            dfs(root.right);
        }

        public int next() {
            return list.removeFirst();
        }

        public boolean hasNext() {
            return !list.isEmpty();
        }
    }

}
