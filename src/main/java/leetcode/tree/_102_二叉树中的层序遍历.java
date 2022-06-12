package leetcode.tree;
//给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
//
//
//
// 示例 1：
//
//
//输入：root = [3,9,20,null,null,15,7]
//输出：[[3],[9,20],[15,7]]
//
//
// 示例 2：
//
//
//输入：root = [1]
//输出：[[1]]
//
//
// 示例 3：
//
//
//输入：root = []
//输出：[]
//
//
//
//
// 提示：
//
//
// 树中节点数目在范围 [0, 2000] 内
// -1000 <= Node.val <= 1000
//
// Related Topics 树 广度优先搜索 二叉树 👍 1354 👎 0

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-06-12.
 */
public class _102_二叉树中的层序遍历{


    class Solution{
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> res = new LinkedList<>();
            if (root == null) {
                return res;
            }
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);
            TreeNode cur;
            List<Integer> level;
            // while 循环控制从上向下一层层遍历
            while (!q.isEmpty()) {
                int sz = q.size();
                // 记录这一层的节点值
                level = new ArrayList<>(sz);
                // for 循环控制每一层从左向右遍历
                for (int i = 0; i < sz; i++) {
                    cur = q.poll();
                    level.add(cur.val);
                    if (cur.left != null) {
                        q.offer(cur.left);
                    }
                    if (cur.right != null) {
                        q.offer(cur.right);
                    }
                }
                if (level != null && level.size() > 0) {
                    res.add(level);
                }
            }
            return res;
        }
    }


    @Deprecated
    class Solution2{
        public List<List<Integer>> levelOrder(TreeNode root) {
            if (root == null) {
                return Collections.EMPTY_LIST;
            }
            Deque<TreeNode> queue1 = new LinkedList<TreeNode>();
            Deque<TreeNode> queue2 = new LinkedList<TreeNode>();
            queue1.addLast(root);
            List<List<Integer>> res = new LinkedList<List<Integer>>();
            int level = 0;
            TreeNode root1, root2, left1, right1, left2, right2;
            do {
                if ((level % 2) == 0) {
                    level++;
                    List<Integer> l1 = new ArrayList<>();
                    while (queue1.size() > 0) {
                        root2 = queue1.poll();
                        l1.add(root2.val);
                        left2 = root2.left;
                        right2 = root2.right;
                        if (left2 != null) {
                            queue2.addLast(left2);
                        }
                        if (right2 != null) {
                            queue2.addLast(right2);
                        }
                    }
                    if (l1 != null && l1.size() > 0) {
                        res.add(l1);
                    }
                }
                else {
                    level++;
                    List<Integer> l2 = new ArrayList<>();
                    while (queue2.size() > 0) {
                        root1 = queue2.poll();
                        l2.add(root1.val);
                        left1 = root1.left;
                        right1 = root1.right;
                        if (left1 != null) {
                            queue1.addLast(left1);
                        }
                        if (right1 != null) {
                            queue1.addLast(right1);
                        }
                    }
                    if (l2 != null && l2.size() > 0) {
                        res.add(l2);
                    }
                }
            } while (queue1.size() > 0 || queue2.size() > 0);
            return res;
        }
    }
}
