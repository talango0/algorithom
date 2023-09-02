package leetcode.tree;
//给定一个不重复的整数数组 nums 。 最大二叉树 可以用下面的算法从 nums 递归地构建:
//
//
// 创建一个根节点，其值为 nums 中的最大值。
// 递归地在最大值 左边 的 子数组前缀上 构建左子树。
// 递归地在最大值 右边 的 子数组后缀上 构建右子树。
//
//
// 返回 nums 构建的 最大二叉树 。

// 示例 1：
//输入：nums = [3,2,1,6,0,5]
//输出：[6,3,5,null,2,0,null,null,1]
//     ┌───┐
//     │ 6 │
//     └───┘
//  ┌────┴───────┐
//┌─▼─┐        ┌─▼─┐
//│ 3 │        │ 5 │
//└───┘        └───┘
//  └───┐     ┌──┘
//    ┌─▼─┐ ┌─▼─┐
//    │ 2 │ │ 0 │
//    └───┘ └───┘
//      └───┐
//        ┌─▼─┐
//        │ 1 │
//        └───┘
//解释：递归调用如下所示：
//- [3,2,1,6,0,5] 中的最大值是 6 ，左边部分是 [3,2,1] ，右边部分是 [0,5] 。
//    - [3,2,1] 中的最大值是 3 ，左边部分是 [] ，右边部分是 [2,1] 。
//        - 空数组，无子节点。
//        - [2,1] 中的最大值是 2 ，左边部分是 [] ，右边部分是 [1] 。
//            - 空数组，无子节点。
//            - 只有一个元素，所以子节点是一个值为 1 的节点。
//    - [0,5] 中的最大值是 5 ，左边部分是 [0] ，右边部分是 [] 。
//        - 只有一个元素，所以子节点是一个值为 0 的节点。
//        - 空数组，无子节点。
//
//
// 示例 2：
//
//
//输入：nums = [3,2,1]
//输出：[3,null,2,null,1]
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 1000
// 0 <= nums[i] <= 1000
// nums 中的所有整数 互不相同
//
// Related Topics 栈 树 数组 分治 二叉树 单调栈 👍 455 👎 0



public class _654_最大二叉树{
    class Solution{
        /**
         * 二叉树的构造问题一般都是使用 分解问题的思路： 构造整棵树 = 根节点 + 构造左子树 + 构造右子树
         *
         * @param nums
         * @return
         */
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            return build(nums, 0, nums.length - 1);
        }

        //定义；将 nums[lo ... hi] 构造成一颗二叉树
        private TreeNode build(int[] nums, int lo, int hi) {
            //base case
            if (lo > hi) {
                return null;
            }

            //找到数组中的最大值和对应的索引下标
            int index = -1, maxVal = Integer.MIN_VALUE;
            for (int i = lo; i <= hi; i++) {
                if (nums[i] > maxVal) {
                    index = i;
                    maxVal = nums[i];
                }
            }

            //先构造出根节点
            TreeNode root = new TreeNode(maxVal);
            //递归调用构造左右子树
            root.left = build(nums, lo, index - 1);
            root.right = build(nums, index + 1, hi);
            return root;
        }
    }
}
