package leetcode.程序员面试金典;
//二叉树数据结构TreeNode可用来表示单向链表（其中left置空，right为下一个链表节点）。
// 实现一个方法，把二叉搜索树转换为单向链表，要求依然符合二叉搜索树的性质，转换操作应是原址的，也就是在原始的二叉搜索树上直接修改。
//
//返回转换后的单向链表的头节点。
//
//注意：本题相对原题稍作改动
//
//
//
//示例：
//
//输入： [4,2,5,1,3,null,6,0]
//输出： [0,null,1,null,2,null,3,null,4,null,5,null,6]
//提示：
//
//节点数量不会超过 100000。
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/binode-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.tree.TreeNode;

/**
 * @author mayanwei
 * @date 2023-06-24.
 */
public class _17_12_BiNode{

    class Solution{
        TreeNode dummy = new TreeNode(-1); //虚拟节点
        TreeNode prev = null;

        public TreeNode convertBiNode(TreeNode root) {
            inOrder(root);
            return dummy.right;
        }

        private TreeNode inOrder(TreeNode root) {
            if (root == null) {
                return null;
            }
            inOrder(root.left);
            if (prev == null) { //第一个节点
                prev = root;    //记录第一个节点
                dummy.right = root;// 记录它，它将作为单链表的表头
            }
            else {// 第一个节点之后的节点
                prev.right = root;// 前一个节点的右指针指向当前节点
                prev = root;// 更新perv指向
            }
            root.left = null; // 当前节点的左指针设为null
            inOrder(root.right);
            return root;
        }
    }


}
