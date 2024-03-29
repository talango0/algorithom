package leetcode.tree;
//给定两个整数数组，preorder 和 postorder ，其中 preorder 是一个具有 无重复 值的二叉树的前序遍历，postorder 是同一棵
//树的后序遍历，重构并返回二叉树。
//
// 如果存在多个答案，您可以返回其中 任何 一个。
//
//
//
// 示例 1：
//
//
//
//
//输入：preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
//输出：[1,2,3,4,5,6,7]
//
//
// 示例 2:
//
//
//输入: preorder = [1], postorder = [1]
//输出: [1]
//
//
//
//
// 提示：
//
//
// 1 <= preorder.length <= 30
// 1 <= preorder[i] <= preorder.length
// preorder 中所有值都 不同
// postorder.length == preorder.length
// 1 <= postorder[i] <= postorder.length
// postorder 中所有值都 不同
// 保证 preorder 和 postorder 是同一棵二叉树的前序遍历和后序遍历
//
// Related Topics 树 数组 哈希表 分治 二叉树 👍 260 👎 0


import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
public class _889_根据前序和后序遍历构造二叉树{
    /**
     * 前序和中序，后序和中序可以唯一确定一棵二叉树。但是前序和后续不能唯一确定。
     * <pre>
     *          ┌──────┐
     *          │  2   │
     *          └──┬───┘
     *        ┌────┴────┐
     *        ▼         ▼
     *     ┌────┐    ┌────┐                   │  │
     *     │  1 │    │ 3  │      ┌────────────▼──▼───────────┐
     *     └────┘    └────┘      │ preorder : 2, 1, 4, 6, 3  │
     *        │                  │ postorder: 4, 6, 1, 3, 2  │
     *    ┌───┴──┐               └──────────────────▲─────▲──┘
     * ┌──▼─┐  ┌─▼─┐                                │     │
     * │ 4  │  │ 6 │
     * └────┘  └───┘
     * 1. 首先前序遍历结果的第一个元素或者后续遍历结果的最后一个元素确定为一个根节点的值。
     * 2. 然后把前序遍历结果的第二个元素作为左子树的根节点的值。
     * 3. 在后续遍历结果中寻找左子树的值，从而确定了左子树的索引边界，进而确定右子树的索引边界，递归构造左右子树。
     *
     *             ┌──────────────────────────────────────────────────────┐
     *             │ preStart             preStart+leftSize        preEnd │
     *             │                                                      │
     *             │ ┌ ─ ─ ─ ─┌─┬───────────────────┬─┬─────────────┐     │
     *             │   rootVal│ │root.left          │ │root.right   │     │
     *  preOrder   │ └ ─ ─ ─ ─└▲┴───────────────────┴─┴─────────────┘     │
     *             │           │                                          │
     *             │           │                                          │
     *             │     leftRootVal                                      │
     *             └──────────────────────────────────────────────────────┘
     *             ┌──────────────────────────────────────────────────────┐
     *             │                                                      │
     *             │  preStart        index                      postEnd  │
     *             │ ┌────────────────┬─┬───────────────────┬ ─ ─ ─ ─     │
     *             │ │root.left       │ │root.right         │ rootVal│    │
     * postOrder   │ └────────────────┴▲┴───────────────────┴ ─ ─ ─ ─     │
     *             │                   │                                  │
     *             │                   │                                  │
     *             │                leftRootVal                           │
     *             └──────────────────────────────────────────────────────┘
     * </pre>
     */
    class Solution{
        // 存储 postorder 中值到索引的映射
        Map<Integer, Integer> valToIndex = new HashMap<>();

        public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
            for (int i = 0; i < postorder.length; i++) {
                valToIndex.put(postorder[i], i);
            }
            return build(preorder, 0, preorder.length - 1,
                    postorder, 0, postorder.length - 1);
        }

        // 定义：根据 preorder[preStart..preEnd] 和 postorder[postStart..postEnd]
        // 构建二叉树，并返回根节点。
        TreeNode build(int[] preorder, int preStart, int preEnd,
                       int[] postorder, int postStart, int postEnd) {
            if (preStart > preEnd) {
                return null;
            }
            if (preStart == preEnd) {
                return new TreeNode(preorder[preStart]);
            }

            // root 节点对应的值就是前序遍历数组的第一个元素
            int rootVal = preorder[preStart];
            // root.left 的值是前序遍历第二个元素
            // 通过前序和后序遍历构造二叉树的关键在于通过左子树的根节点
            // 确定 preorder 和 postorder 中左右子树的元素区间
            int leftRootVal = preorder[preStart + 1];
            // leftRootVal 在后序遍历数组中的索引
            int index = valToIndex.get(leftRootVal);
            // 左子树的元素个数
            int leftSize = index - postStart + 1;

            // 先构造出当前根节点
            TreeNode root = new TreeNode(rootVal);
            // 递归构造左右子树
            // 根据左子树的根节点索引和元素个数推导左右子树的索引边界
            root.left = build(preorder, preStart + 1, preStart + leftSize,
                    postorder, postStart, index);
            root.right = build(preorder, preStart + leftSize + 1, preEnd,
                    postorder, index + 1, postEnd - 1);

            return root;
        }
    }

}
