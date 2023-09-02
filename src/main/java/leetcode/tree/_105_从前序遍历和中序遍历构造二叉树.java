package leetcode.tree;
//给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉
//树并
//返回其根节点。
//
// 示例 1:
//输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
//输出: [3,9,20,null,null,15,7]
//
//
// 示例 2:
//输入: preorder = [-1], inorder = [-1]
//输出: [-1]
//
//
// 提示:
//
//
// 1 <= preorder.length <= 3000
// inorder.length == preorder.length
// -3000 <= preorder[i], inorder[i] <= 3000
// preorder 和 inorder 均 无重复 元素
// inorder 均出现在 preorder
// preorder 保证 为二叉树的前序遍历序列
// inorder 保证 为二叉树的中序遍历序列
//
// Related Topics 树 数组 哈希表 分治 二叉树 👍 1631 👎 0
//

import java.util.HashMap;

/**
 * @author mayanwei
 * @date 2022-06-11.
 * @see _105_从前序遍历和中序遍历构造二叉树
 * @see _106_从中序与后序遍历序列构造二叉树
 * @see _654_最大二叉树
 * @see _889_根据前序和后序遍历构造二叉树
 */
public class _105_从前序遍历和中序遍历构造二叉树{

    class Solution1 {
        TreeNode buildTree(int[] preorder, int[] inorder) {
            return build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
        }

        /**
         * build 函数的定义：
         * 若前序遍历数组为 preorder[preStart...preEnd]
         *   中序遍历数组为 inorder[inStart...inEnd]
         *   构造二叉树，返回该二叉树的根节点
         * @param preorder
         * @param preStart
         * @param preEnd
         * @param inorder
         * @param inStart
         * @param inEnd
         * @return
         */
        private TreeNode build(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
            if (preStart > preEnd) {
                return null;
            }
            // root 节点对应的值就是前序遍历数组的第一个元素
            int rootVal = preorder[preStart];
            // root 在中序遍历的索引
            int i = inStart;
            for (; i <= inEnd; i++) {
                if (inorder[i] == rootVal) {
                    break;
                }
            }
            int leftSize = i - inStart;
            TreeNode root = new TreeNode(rootVal);
            // 递归的构造左右子树
            root.left = build(preorder, preStart + 1, preStart + leftSize, inorder, inStart, i - 1);
            root.right = build(preorder, preStart + leftSize + 1, preEnd, inorder, i + 1, inEnd);
            return root;
        }
    }

    class Solution2 {
        HashMap<Integer, Integer> valToIndex = new HashMap<>();
        TreeNode buildTree(int[] preorder, int[] inorder) {
            for (int i = 0; i < inorder.length; i++) {
                valToIndex.put(inorder[i], i);
            }
            return build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
        }

        private TreeNode build(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
            if (preStart > preEnd) {
                return null;
            }
            // root 节点对应的值就是前序遍历数组的第一个元素
            int rootVal = preorder[preStart];
            // root 在中序遍历的索引
            int i = valToIndex.get(rootVal);
            int leftSize = i - inStart;
            TreeNode root = new TreeNode(rootVal);
            // 递归的构造左右子树
            root.left = build(preorder, preStart + 1, preStart + leftSize, inorder, inStart, i - 1);
            root.right = build(preorder, preStart + leftSize + 1, preEnd, inorder, i + 1, inEnd);
            return root;
        }
    }

}

