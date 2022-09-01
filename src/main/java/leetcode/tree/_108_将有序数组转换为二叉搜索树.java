package leetcode.tree;
//给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 高度平衡 二叉搜索树。
//
//高度平衡 二叉树是一棵满足「每个节点的左右两个子树的高度差的绝对值不超过 1 」的二叉树。
//
//
//
//示例 1：
//
//
//输入：nums = [-10,-3,0,5,9]
//输出：[0,-3,9,-10,null,5]
//解释：[0,-10,5,null,-3,null,9] 也将被视为正确答案：
//
//示例 2：
//
//
//输入：nums = [1,3]
//输出：[3,1]
//解释：[1,null,3] 和 [3,1] 都是高度平衡二叉搜索树。
//
//
//提示：
//
//1 <= nums.length <= 104
//-104 <= nums[i] <= 104
//nums 按 严格递增 顺序排列
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


public class _108_将有序数组转换为二叉搜索树 {

    class Solution {
        // 二叉树的构建问题，说白了就是：构造根节点，然后构建左右子树
        // 一个有序数组对于 BST来说是就是中序遍历，根节点在数组中心，数据左侧是左子树元素，右侧是右子树元素
        public TreeNode sortedArrayToBST(int[] nums) {
            return build(nums, 0, nums.length-1);
        }
        // 将闭区间 [left, right]中的元素转化成 BST，返回根节点
        TreeNode build(int [] nums, int left, int right) {
            if (left > right) {
                // 区间为空
                return null;
            }
            // 构造根节点
            // BST 节点做小右大，中间的元素就是根节点
            int mid = (left + right)/2;
            TreeNode root = new TreeNode(nums[mid]);
            // 递归构建左子树
            root.left = build(nums, left, mid-1);
            // 递归构建右子树
            root.right = build(nums, mid + 1, right);
            return root;

        }
    }
}
