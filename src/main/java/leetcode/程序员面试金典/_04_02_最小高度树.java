package leetcode.程序员面试金典;
//给定一个有序整数数组，元素各不相同且按升序排列，编写一个算法，创建一棵高度最小的二叉搜索树。
//
//示例:
//给定有序数组: [-10,-3,0,5,9],
//
//一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
//
//          0
//         / \
//       -3   9
//       /   /
//     -10  5
//通过次数47,681提交次数60,464
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/minimum-height-tree-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


import leetcode.tree.TreeNode;

/**
 * @author mayanwei
 * @date 2023-05-06.
 */
public class _04_02_最小高度树{
    class Solution{
        public TreeNode sortedArrayToBST(int[] nums) {
            int n = nums.length;
            return buildBST(nums, 0, n - 1);
        }

        private TreeNode buildBST(int[] nums, int start, int end) {
            if (start > end) {
                return null;
            }
            int mid = start + ((end - start) >> 1);
            TreeNode root = new TreeNode(nums[mid]);
            root.left = buildBST(nums, start, mid - 1);
            root.right = buildBST(nums, mid + 1, end);
            return root;
        }
    }
}
