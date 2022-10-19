package leetcode.tree;
//[LeetCode] 742. Closest Leaf in a Binary Tree
//Given a binary tree where every node has a unique value, and a target key k, find the value of the nearest leaf node to target k in the tree.
//
//Here, nearest to a leaf means the least number of edges travelled on the binary tree to reach any leaf of the tree. Also, a node is called a leaf if it has no children.
//
//In the following examples, the input tree is represented in flattened form row by row. The actual root tree given will be a TreeNode object.
//
//Example 1:
//
//Input:
//root = [1, 3, 2], k = 1
//Diagram of binary tree:
//          1
//         / \
//        3   2
//
//Output: 2 (or 3)
//
//Explanation: Either 2 or 3 is the nearest leaf node to the target of 1.
//Example 2:
//
//Input:
//root = [1], k = 1
//Output: 1
//
//Explanation: The nearest leaf node is the root node itself.
//Example 3:
//
//Input:
//root = [1,2,3,4,null,null,null,5,null,6], k = 2
//Diagram of binary tree:
//             1
//            / \
//           2   3
//          /
//         4
//        /
//       5
//      /
//     6
//
//Output: 3
//Explanation: The leaf node with value 3 (and not the leaf node with value 6) is nearest to the node with value 2.
//Note:
//
//root represents a binary tree with at least 1 node and at most 1000 nodes.
//Every node has a unique node.val in range [1, 1000].
//There exists some node in the given binary tree for which node.val == k.
//二叉树最近的叶节点。
//给定一个 每个结点的值互不相同 的二叉树，和一个目标值 k，找出树中与目标值 k 最近的叶结点。
//这里，与叶结点 最近 表示在二叉树中到达该叶节点需要行进的边数与到达其它叶结点相比最少。而且，当一个结点没有孩子结点时称其为叶结点。

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author mayanwei
 * @date 2022-10-19.
 */
public class _742_二叉树最近的叶节点{
    class Solution {
        TreeNode target;
        public int findClosestLeaf(TreeNode root, int k) {
            HashMap<TreeNode, TreeNode> map = new HashMap<>();
            dfs(root, null, map, k);
            if (target == null) {
                return -1;
            }

            Queue<TreeNode> queue = new LinkedList<>();
            HashSet<Integer> visited = new HashSet<>();
            queue.offer(target);
            visited.add(k);
            while (!queue.isEmpty()) {
                TreeNode cur = queue.poll();
                if (cur.left == null && cur.right == null) {
                    return cur.val;
                }
                if (cur.left != null && !visited.contains(cur.left.val)) {
                    queue.offer(cur.left);
                    visited.add(cur.left.val);
                }
                if (cur.right != null && !visited.contains(cur.right.val)) {
                    queue.offer(cur.right);
                    visited.add(cur.right.val);
                }
                if (map.containsKey(cur) && !visited.contains(map.get(cur).val)) {
                    queue.offer(map.get(cur));
                    visited.add(map.get(cur).val);
                }
            }
            return -1;
        }

        private void dfs(TreeNode root, TreeNode parent, HashMap<TreeNode, TreeNode> map, int k) {
            if (root == null) {
                return;
            }
            if (parent != null) {
                map.put(root, parent);
            }
            if (root.val == k) {
                target = root;
            }
            dfs(root.left, root, map, k);
            dfs(root.right, root, map, k);
        }
    }
}
