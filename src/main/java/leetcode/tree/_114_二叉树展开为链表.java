package leetcode.tree;
//给你二叉树的根结点 root ，请你将它展开为一个单链表：
//
//
// 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
// 展开后的单链表应该与二叉树 先序遍历 顺序相同。
//
//
//
//
// 示例 1：
//
//
//输入：root = [1,2,5,3,4,null,6]
//输出：[1,null,2,null,3,null,4,null,5,null,6]
//          ┌───┐                 ┌───┐
//          │ 1 │                 │ 1 │
//          └───┘                 └───┘
//            │                     │
//       ┌────┴─────┐               └───┐
//     ┌─▼─┐      ┌─▼─┐                ┌▼──┐
//     │ 2 │      │ 5 │                │ 2 │
//     └───┘      └───┘                └───┘
//       │          │                    │
//  ┌────┴───┐      └───┐                └───┐
//┌─▼─┐    ┌─▼─┐      ┌─▼─┐                  ▼
//│ 3 │    │ 4 │      │ 6 │                ┌───┐
//└───┘    └───┘      └───┘                │ 3 │
//                                         └───┘
//                                           │
//                                           └───┐
//                                               ▼
//                                             ┌───┐
//                                             │ 4 │
//                                             └───┘
//                                               │
//                                               └───┐
//                                                   ▼
//                                                 ┌───┐
//                                                 │ 5 │
//                                                 └───┘
//                                                   │
//                                                   └────┐
//                                                        ▼
//                                                      ┌───┐
//                                                      │ 6 │
//                                                      └───┘
//
// 示例 2：
//
//
//输入：root = []
//输出：[]
//
//
// 示例 3：
//
//
//输入：root = [0]
//输出：[0]
//
//
//
//
// 提示：
//
//
// 树中结点数在范围 [0, 2000] 内
// -100 <= Node.val <= 100
//
//
//
//
// 进阶：你可以使用原地算法（O(1) 额外空间）展开这棵树吗？
// Related Topics 栈 树 深度优先搜索 链表 二叉树 👍 1226 👎 0


public class _114_二叉树展开为链表 {

    /**
     * 分解的思路
     */
    class Solution {
        //定义，输入节点root，然后 root 为根的二叉树就会被拉成一条链条
        //对于一个节点 x， 可以执行如下流程
        // 1. 先利用flatten(x.left) 和 flatten(x.right) 将左右子树拉平
        // 2. 然后将x的右子树连接到左子树下方，然后将整个左子树作为x的右子树。
        // 这样，以 x 为根的整颗二叉树就拉平了，恰好完成了 flatten(X) 的定义。
        public void flatten(TreeNode root) {
            //base case
            if (root == null) {return;}
            //利用定义，把左右子树拉平
            flatten(root.left);
            flatten(root.right);

            /**后序遍历的位置 */
            //1. 左右子树已经被拉成了一条链表
            TreeNode left = root.left;
            TreeNode right = root.right;

            //2. 将左子树作为右子树
            root.left = null;
            root.right = left;

            //3. 将原先的右子树连接到当前有节点的末端
            TreeNode p = root;
            while (p.right != null) {
                p = p.right;
            }
            p.right = right;

        }
    }

    /**
     * 遍历思路
     */
    class Solution1 {
        // 虚拟头节点，dummy.right 就是结果
        TreeNode dummy = new TreeNode(-1);
        // 用来构建链表的指针
        TreeNode p = dummy;

        void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            // 前序位置
            p.right = new TreeNode(root.val);
            p = p.right;

            traverse(root.left);
            traverse(root.right);
        }

    }
}
