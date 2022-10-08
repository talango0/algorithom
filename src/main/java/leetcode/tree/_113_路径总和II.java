package leetcode.tree;
//给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
//
// 叶子节点 是指没有子节点的节点。
//
//
//
//
//
//
//
// 示例 1：
//
//
//输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
//输出：[[5,4,11,2],[5,8,4,5]]
//
//
// 示例 2：
//
//
//输入：root = [1,2,3], targetSum = 5
//输出：[]
//
//
// 示例 3：
//
//
//输入：root = [1,2], targetSum = 0
//输出：[]
//
//
//
//
// 提示：
//
//
// 树中节点总数在范围 [0, 5000] 内
// -1000 <= Node.val <= 1000
// -1000 <= targetSum <= 1000
//
//
// Related Topics 树 深度优先搜索 回溯 二叉树 👍 851 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import leetcode.tree.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-10-08.
 * @see _437_路径总和III
 * @see _112_路径总和
 * @see _666_路径和IV_树的遍历
 */
public class _113_路径总和II{
    class Solution{
        List<List<Integer>> res = new LinkedList<>();

        public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            if (root == null) {
                return res;
            }
            traverse(root, targetSum, new LinkedList<Integer>());
            return res;
        }

        private void traverse(TreeNode root, int targetSum, LinkedList<Integer> path) {
            if (root == null) {
                return;
            }
            int remain = targetSum - root.val;
            if (root.left == null && root.right == null) {
                // 找到一条路径
                if (remain == 0) {
                    path.addLast(root.val);
                    res.add(new LinkedList(path));
                    path.removeLast();
                }
                return;
            }
            //维护路径列表
            path.addLast(root.val);
            traverse(root.left, remain, path);
            path.removeLast();

            path.addLast(root.val);
            traverse(root.right, remain, path);
            path.removeLast();

        }
    }


    class Solution2{
        List<List<Integer>> res = new LinkedList<>();

        public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            visit(root, targetSum, new LinkedList<Integer>());
            return res;
        }

        private TreeNode visit(TreeNode root, int targetSum, LinkedList<Integer> path) {
            if (root == null) {
                return null;
            }
            int val = root.val;
            path.add(val);
            TreeNode left = visit(root.left, targetSum - val, path);
            TreeNode right = visit(root.right, targetSum - val, path);

            if (left == null && right == null && targetSum == val) {
                res.add(new LinkedList(path));
            }
            path.removeLast();
            return root;
        }
    }
}
