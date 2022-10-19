package leetcode.tree;
//给定一个二叉树（具有根结点 root）， 一个目标结点 target ，和一个整数值 k 。
//
// 返回到目标结点 target 距离为 k 的所有结点的值的列表。 答案可以以 任何顺序 返回。
//
//
//
//
//
//
// 示例 1：
//
//
//
//
//输入：root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
//输出：[7,4,1]
//解释：所求结点为与目标结点（值为 5）距离为 2 的结点，值分别为 7，4，以及 1
//
//
// 示例 2:
//
//
//输入: root = [1], target = 1, k = 3
//输出: []
//
//
//
//
// 提示:
//
//
// 节点数在 [1, 500] 范围内
// 0 <= Node.val <= 500
// Node.val 中所有值 不同
// 目标结点 target 是树上的结点。
// 0 <= k <= 1000
//
//
//
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 597 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-10-19.
 */
public class _863_二叉树中所有距离为K的结点{
    class Solution {
        //记录父节点：node.val -> parentNode
        // 题目说树中的节点都是唯一的，所以可以用 node.val 代表 TreeNode
        HashMap<Integer, TreeNode> parent = new HashMap<>();

        public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
            // 遍历所有节点，记录每个节点的父节点
            traverse(root, null);

            //开始从 target结点释放BFS算法，找到距离为 k 的节点
            Queue<TreeNode> q = new LinkedList<>();
            HashSet<Integer> visited = new HashSet<>();
            q.offer(target);
            visited.add(target.val);
            // 记录 target的距离
            int dist = 0;
            List<Integer> res = new LinkedList<>();
            while (!q.isEmpty()) {
                int sz =  q.size();
                for(int i = 0; i< sz; i++) {
                    TreeNode cur = q.poll();
                    if (dist == k) {
                        // 找到距离起点target距离为k的节点
                        res.add(cur.val);
                    }
                    // 向父节点、左右子节点扩散
                    TreeNode parentNode = parent.get(cur.val);
                    if (parentNode != null && !visited.contains(parentNode.val)) {
                        visited.add(parentNode.val);
                        q.offer(parentNode);
                    }
                    if (cur.left != null && !visited.contains(cur.left.val)) {
                        visited.add(cur.left.val);
                        q.offer(cur.left);
                    }
                    if (cur.right != null && !visited.contains(cur.right.val)) {
                        visited.add(cur.right.val);
                        q.offer(cur.right);
                    }
                }
                // 向外扩展一圈
                dist++;
            }
            return res;
        }

        private void traverse(TreeNode root, TreeNode parentNode) {
            if (root == null) {
                return ;
            }
            parent.put(root.val, parentNode);
            traverse(root.left, root);
            traverse(root.right, root);
        }
    }
}
