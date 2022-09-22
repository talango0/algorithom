package leetcode.tree;
//给定一个二叉树，我们在树的节点上安装摄像头。
//
// 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
//
// 计算监控树的所有节点所需的最小摄像头数量。
//
//
//
// 示例 1：
//
//
//
// 输入：[0,0,null,0,0]
//输出：1
//解释：如图所示，一台摄像头足以监控所有节点。
//
//
// 示例 2：
//
//
//
// 输入：[0,0,null,0,null,0,null,null,0]
//输出：2
//解释：需要至少两个摄像头来监视树的所有节点。 上图显示了摄像头放置的有效位置之一。
//
//
// 提示：
//
//
// 给定树的节点数的范围是 [1, 1000]。
// 每个节点的值都是 0。
//
//
// Related Topics 树 深度优先搜索 动态规划 二叉树 👍 478 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * @author mayanwei
 * @date 2022-09-15.
 */
public class _968_监控二叉树{
    /**
     * 时间复杂度 O(n)
     * 空间负载度 O(n)
     */
    class Solution{
        public int minCameraCover(TreeNode root) {
            int[] array = dfs(root);
            return array[1];
        }

        public int[] dfs(TreeNode root) {
            if (root == null) {
                return new int[]{Integer.MAX_VALUE >> 1, 0, 0};
            }
            int[] leftArr = dfs(root.left);
            int[] rightArr = dfs(root.right);
            // 后序遍历的位置
            int[] arr = new int[3];
            // 状态a : root 必须放置摄像头的情况下，覆盖整颗树需要的摄像头数目
            arr[0] = leftArr[2] + rightArr[2] + 1;
            // 状态b : 覆盖整颗树需要的摄像头数目，无论 root 是否放置摄像头
            arr[1] = Math.min(arr[0], Math.min(leftArr[0] + rightArr[1], rightArr[0] + leftArr[1]));
            // 状态c : 覆盖两棵子树需要的摄像头数目，无论节点root 本身是否被监控到
            arr[2] = Math.min(arr[0], leftArr[1] + rightArr[1]);
            return arr;
        }
    }
}
