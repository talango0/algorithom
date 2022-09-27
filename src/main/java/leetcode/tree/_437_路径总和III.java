package leetcode.tree;
//给定一个二叉树的根节点 root，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
//
//路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
//
//
//
//示例 1：
//
//
//
//输入：root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
//输出：3
//解释：和等于 8 的路径有 3 条，如图所示。
//示例 2：
//
//输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
//输出：3
//
//
//提示:
//
//二叉树的节点个数的范围是 [0,1000]
//-109<= Node.val <= 109
//-1000<= targetSum<= 1000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/path-sum-iii
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.arrays._303_区域和检索_数组不可变;
import leetcode.arrays._560_和为K的子数组;

import java.util.HashMap;
import java.util.Map;

/**
 * 前缀和、和为 K 的子数组
 *
 * @see _303_区域和检索_数组不可变
 * @see _560_和为K的子数组
 */
public class _437_路径总和III {
    /** 前缀和、和为 k 的子数组 */
    class Solution {
        // 记录前缀和
        // 定义: 从二叉树的根节点开始，路径和为 pathSum 的路径有 preSumCount.get(pathSum) 个
        Map<Long, Integer> preSumCount = new HashMap<>();
        long pathSum, targetSum;
        int res = 0;
        public int pathSum(TreeNode root, int targetSum) {
            if (root == null) {
                return 0;
            }
            this.pathSum = 0;
            this.targetSum = targetSum;
            this.preSumCount.put(0l, 1);
            traverse(root);
            return res;
        }
        void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            // 前序遍历位置
            pathSum += root.val;
            // 从二叉树根节点开始，路径和为 pathSum - tagetSum 的路径条数就是路径和为 targetSum 的路径条数
            res += preSumCount.getOrDefault(pathSum-targetSum, 0);
            // 记录从二叉树的根节点开始，路径和为 pathSum 的路径条数
            preSumCount.put(pathSum, preSumCount.getOrDefault(pathSum, 0)  + 1);

            traverse(root.left);
            traverse(root.right);

            // 后序遍历的位置
            preSumCount.put(pathSum, preSumCount.get(pathSum) - 1);
            pathSum -= root.val;

        }
    }
}
