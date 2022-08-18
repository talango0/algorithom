package leetcode.tree;

//给你一棵以 root 为根的 二叉树 ，请你返回 任意 二叉搜索子树的最大键值和。
//
//二叉搜索树的定义如下：
//
//任意节点的左子树中的键值都 小于 此节点的键值。
//任意节点的右子树中的键值都 大于 此节点的键值。
//任意节点的左子树和右子树都是二叉搜索树。
//示例 1：
//
//
//
//输入：root = [1,4,3,2,4,2,5,null,null,null,null,null,null,4,6]
//输出：20
//解释：键值为 3 的子树是和最大的二叉搜索树。
//示例 2：
//
//
//
//输入：root = [4,3,null,1,2]
//输出：2
//解释：键值为 2 的单节点子树是和最大的二叉搜索树。
//示例 3：
//
//输入：root = [-4,-2,-5]
//输出：0
//解释：所有节点键值都为负数，和最大的二叉搜索树为空。
//示例 4：
//
//输入：root = [2,1,3]
//输出：6
//示例 5：
//
//输入：root = [5,4,8,3,null,6,3]
//输出：7
//提示：
//
//每棵树有 1 到 40000 个节点。
//每个节点的键值在 [-4 * 10^4 , 4 * 10^4] 之间。
//Related Topics
//
//👍 105, 👎 0

/**
 * 思路了
 * 我们定义一个 traverse 函数，traverse(root) 返回一个大小为 4 的 int 数组，我们暂且称它为 res，其中：
 * <p>
 * res[0] 记录以 root 为根的二叉树是否是 BST，若为 1 则说明是 BST，若为 0 则说明不是 BST；
 * <p>
 * res[1] 记录以 root 为根的二叉树所有节点中的最小值；
 * <p>
 * res[2] 记录以 root 为根的二叉树所有节点中的最大值；
 * <p>
 * res[3] 记录以 root 为根的二叉树所有节点值之和。
 *
 * @author mayanwei
 * @date 2022-08-18.
 * @see _1038_从二叉搜索树到累加树
 */
public class _1373_二叉搜索子树的最大键值和{
    class Solution{
        // 全局变量，记录 BST 最大结点之和
        int maxSum = 0;

        public int maxSumBST(TreeNode root) {
            traverse(root);
            return maxSum;
        }

        public int[] traverse(TreeNode root) {
            // base case
            if (root == null) {
                return new int[]{1, Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
            }
            int[] left = traverse(root.left);
            int[] right = traverse(root.right);

            // 后序遍历的位置
            int[] res = new int[4];
            // 这个if在判断以root 为根的二叉树是不是BST
            if (left[0] == 1 && right[0] == 1 && root.val > left[2] && root.val < right[1]) {
                // 以root为根的这棵是 BST
                res[0] = 1;
                // 计算以 root 为根的这棵 BST 的最小值
                res[1] = Math.min(left[1], root.val);
                // 计算以 root 为根的这棵 BST 的最大值
                res[2] = Math.max(right[2], root.val);
                // 计算以 root 为根的这棵 BST 所有节点之和
                res[3] = left[3] + right[3] + root.val;
                // 更新全局变量
                maxSum = Math.max(maxSum, res[3]);
            }
            else {
                // 以 root 为根的二叉树不是 BST
                res[0] = 0;
                // 其他的值都没必要计算了，因为用不到
            }
            return res;
        }
    }
}
