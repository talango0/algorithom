package leetcode.tree;
//给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
//
//
//
// 示例 1：
//
//
//输入：root = [3,9,20,null,null,15,7]
//输出：[[3],[20,9],[15,7]]
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
// -100 <= Node.val <= 100
//
// Related Topics 树 广度优先搜索 二叉树 👍 650 👎 0


import java.util.*;

/**
 * @author mayanwei
 * @date 2022-06-12.
 */
public class _103_二叉树的锯齿层序遍历{
    class Solution{
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            if (root == null) {
                return Collections.EMPTY_LIST;
            }
            Deque<TreeNode> deque = new LinkedList<>();
            deque.offer(root);
            List<List<Integer>> res = new ArrayList<>();
            List<Integer> l;
            TreeNode node, left, right;
            for (int level = 0, size; deque.size() > 0; level++) {
                size = deque.size();
                l = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    if (level % 2 == 0) {
                        node = deque.poll();
                    }
                    else {
                        node = deque.pollLast();
                    }

                    l.add(node.val);
                    left = node.left;
                    right = node.right;

                    if (level % 2 == 0) {
                        if (left != null) {
                            deque.offer(left);
                        }
                        if (right != null) {
                            deque.offer(right);
                        }
                    }
                    else {
                        if (right != null) {
                            deque.offerFirst(right);
                        }
                        if (left != null) {
                            deque.offerFirst(left);
                        }
                    }
                }
                res.add(l);
            }
            return res;
        }
    }
}
