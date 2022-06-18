package leetcode.bfs;
//给定一个二叉树，找出其最小深度。
//
// 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
//
// 说明：叶子节点是指没有子节点的节点。
//
//
//
// 示例 1：
//
//
//输入：root = [3,9,20,null,null,15,7]
//输出：2
//
//
// 示例 2：
//
//
//输入：root = [2,null,3,null,4,null,5,null,6]
//输出：5
//
//
//
//
// 提示：
//
//
// 树中节点数的范围在 [0, 10⁵] 内
// -1000 <= Node.val <= 1000
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 778 👎 0

import leetcode.tree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author mayanwei
 * @date 2022-06-18.
 */
public class _111_二叉树的最小深度{


//leetcode submit region begin(Prohibit modification and deletion)


    class Solution {
        public int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            Queue<TreeNode> q = new LinkedList<TreeNode>();
            q.offer(root);
            int depth = 1; // root本身就是1
            TreeNode left, right;
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i<size; i++){
                    TreeNode node = q.poll();
                    left = node.left;
                    right = node.right;
                    if (left == null && right== null) {
                        return depth;
                    }
                    if (left != null) {
                        q.offer(left);
                    }
                    if(right != null) {
                        q.offer(right);
                    }
                }
                depth ++;
            }
            return depth;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
