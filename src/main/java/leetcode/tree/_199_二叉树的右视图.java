package leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author mayanwei
 * @date 2022-10-30.
 */
public class _199_二叉树的右视图{
    class Solution{
        /**
         * 两个思路：
         * 1. 用BFS层序遍历算法，每一层的最后一个节点就是二叉树的右视图，也可以把 BFS 反过来，从右往左遍历每一行，进一步提升效率。
         * 2. 用DFS 递归遍历算法，先递归 root.right ,在递归 root.left,同时用 res 记录每一层的最右侧作为右侧视图。
         */

        // BFS
        public List<Integer> rightSideView_bfs(TreeNode root) {
            List<Integer> res = new LinkedList<>();
            if (root == null) {
                return res;
            }
            // BFS 层序遍历，计算右侧视图
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);
            // while 循环控制从上向下一层层遍历
            while (!q.isEmpty()) {
                int sz = q.size();
                // 每一层头部就是最右侧的元素
                TreeNode last = q.peek();
                for (int i = 0; i < sz; i++) {
                    TreeNode cur = q.poll();
                    // 控制每一层从右向左遍历
                    if (cur.right != null) {
                        q.offer(cur.right);
                    }
                    if (cur.left != null) {
                        q.offer(cur.left);
                    }
                }
                // 每一层的最后一个节点就是二叉树的右侧视图
                res.add(last.val);
            }
            return res;
        }

        // DFS
        List<Integer> res = new ArrayList<>();
        // 记录递归的层数
        int depth = 0;

        public List<Integer> rightSideView(TreeNode root) {
            traverse(root);
            return res;
        }

        // 二叉树遍历函数
        void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            // 前序遍历位置
            depth++;
            if (res.size() < depth) {
                // 这一层还没有记录值
                // 说明root 就是右侧视图的第一个节点
                res.add(root.val);
            }
            // 注意，这里反过来，先遍历右子树再遍历左子树
            // 这样首先遍历的一定是右侧节点
            traverse(root.right);
            traverse(root.left);
            // 后序遍历位置
            depth--;
        }

    }

}
