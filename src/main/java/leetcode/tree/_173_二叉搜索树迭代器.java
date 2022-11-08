package leetcode.tree;
//实现一个二叉搜索树迭代器类BSTIterator ，表示一个按中序遍历二叉搜索树（BST）的迭代器：
//
//
//
//
// BSTIterator(TreeNode root) 初始化 BSTIterator 类的一个对象。BST 的根节点 root 会作为构造函数的一部分给出
//。指针应初始化为一个不存在于 BST 中的数字，且该数字小于 BST 中的任何元素。
// boolean hasNext() 如果向指针右侧遍历存在数字，则返回 true ；否则返回 false 。
// int next()将指针向右移动，然后返回指针处的数字。
//
//
//
//
// 注意，指针初始化为一个不存在于 BST 中的数字，所以对 next() 的首次调用将返回 BST 中的最小元素。
//
// 你可以假设 next() 调用总是有效的，也就是说，当调用 next() 时，BST 的中序遍历中至少存在一个下一个数字。
//
//
//
// 示例：
//
//
//输入
//["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next",
//"hasNext", "next", "hasNext"]
//[[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
//输出
//[null, 3, 7, true, 9, true, 15, true, 20, false]
//
//
//解释
//BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]);
//bSTIterator.next(); // 返回 3
//bSTIterator.next(); // 返回 7
//bSTIterator.hasNext(); // 返回 True
//bSTIterator.next(); // 返回 9
//bSTIterator.hasNext(); // 返回 True
//bSTIterator.next(); // 返回 15
//bSTIterator.hasNext(); // 返回 True
//bSTIterator.next(); // 返回 20
//bSTIterator.hasNext(); // 返回 False
//
//
//
//
// 提示：
//
//
// 树中节点的数目在范围 [1, 10⁵] 内
// 0 <= Node.val <= 10⁶
// 最多调用 10⁵ 次 hasNext 和 next 操作
//
//
//
//
// 进阶：
//
//
// 你可以设计一个满足下述条件的解决方案吗？next() 和 hasNext() 操作均摊时间复杂度为 O(1) ，并使用 O(h) 内存。其中 h 是树的高
//度。
//
//
// Related Topics 栈 树 设计 二叉搜索树 二叉树 迭代器 👍 650 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.Stack;

/**
 * @author mayanwei
 * @date 2022-11-01.
 */
public class _173_二叉搜索树迭代器{
    class BSTIterator{
        // 模拟递归栈
        private Stack<TreeNode> stk = new Stack<>();

        // 左侧树枝一撸到底
        private void pushLeftBranch(TreeNode p) {
            while (p != null) {
                stk.push(p);
                p = p.left;
            }
        }

        public BSTIterator(TreeNode root) {
            pushLeftBranch(root);
        }

        public int next() {
            TreeNode p = stk.pop();
            pushLeftBranch(p.right);
            return p.val;
        }

        public boolean hasNext() {
            return !stk.isEmpty();
        }
    }
}
