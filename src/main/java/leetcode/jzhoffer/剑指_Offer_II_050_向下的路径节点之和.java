package leetcode.jzhoffer;

import leetcode.tree.TreeNode;
import leetcode.tree._437_路径总和III;

import java.util.HashMap;
//给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
//
// 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
//
//
//
// 示例 1：
//
//
//
//
//输入：root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
//输出：3
//解释：和等于 8 的路径有 3 条，如图所示。
//
//
// 示例 2：
//
//
//输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
//输出：3
//
//
//
//
// 提示:
//
//
// 二叉树的节点个数的范围是 [0,1000]
//
// -10⁹
// -1000 <= targetSum <= 1000
//
//
//
//
//
// 注意：本题与主站 437 题相同：https://leetcode-cn.com/problems/path-sum-iii/
//
// Related Topics 树 深度优先搜索 二叉树 👍 67 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-10-31.
 * @see _437_路径总和III
 */
public class 剑指_Offer_II_050_向下的路径节点之和{
    class Solution{
        // 记录前缀和
        // 定义：从二叉树的根节点开始，路径和为 pathSum 的路径有 preSumCount.get(pathSum)个
        HashMap<Long, Integer> preSumCount = new HashMap<>();
        long pathSum, targetSum;
        int res = 0;

        public int pathSum(TreeNode root, int targetSum) {
            if (root == null) {
                return 0;
            }
            this.pathSum = 0;
            this.targetSum = targetSum;
            this.preSumCount.put(0L, 1);
            traverse(root);
            return res;
        }

        void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            // 前序遍历的位置
            pathSum += root.val;
            // 从二叉树的根节点开始，路径和为 pathSum - targetSum 的路径条数
            res += preSumCount.getOrDefault(pathSum - targetSum, 0);
            // 记录从二叉树的跟节点开始，路径和为 pathSum 的路径条数
            preSumCount.put(pathSum, preSumCount.getOrDefault(pathSum, 0) + 1);
            traverse(root.left);
            traverse(root.right);

            // 后序遍历的位置
            preSumCount.put(pathSum, preSumCount.get(pathSum) - 1);
            pathSum -= root.val;

        }

    }
}
