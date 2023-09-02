package leetcode.tree;
//给你一棵 完全二叉树 的根节点 root ，求出该树的节点个数。
//
// 完全二叉树 的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。若最底层
//为第 h 层，则该层包含 1~ 2ʰ 个节点。
//
//
//
// 示例 1：
//
//
//输入：root = [1,2,3,4,5,6]
//输出：6
//
//
// 示例 2：
//
//
//输入：root = []
//输出：0
//
//
// 示例 3：
//
//
//输入：root = [1]
//输出：1
//
//
//
//
// 提示：
//
//
// 树中节点的数目范围是[0, 5 * 10⁴]
// 0 <= Node.val <= 5 * 10⁴
// 题目数据保证输入的树是 完全二叉树
//
//
//
//
// 进阶：遍历树来统计节点是一种时间复杂度为 O(n) 的简单解决方案。你可以设计一个更快的算法吗？
// Related Topics 树 深度优先搜索 二分查找 二叉树 👍 731 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
public class _222_完全二叉树的节点个数 {
    public int countNodesNormalTree(TreeNode root) {
        if (root == null) return 0;
        //如果是普通的二叉树显然只要向下遍历一遍即可，时间复杂度为 O(N)
        return 1 + countNodesNormalTree(root.left) + countNodesNormalTree(root.right);
    }
    public  int countNodesFullTree(TreeNode root) {
        if (root == null) return 0;
        int h = 0;
        //如果是一颗满二叉树
        while (root != null){
            root = root.left;
            h++;
        }
        return (int)Math.pow(2, h) - 1;
    }

    class Solution {
        /**
         * 对于一颗完全二叉树，则是二者的结合。<p>
         * 直觉感觉好像最坏情况下是 O(N*logN) 吧，因为之前的 while 需要 logN 的时间，最后要 O(N) 的时间向左右子树递归。
         * 由于完全二叉树的性质，其子树一定有一棵是满的，所以一定会触发 hl == hr，只消耗 O(logN) 的复杂度而不会继续递归。
         * 综上，算法的递归深度就是树的高度 O(logN)，每次递归所花费的时间就是 while 循环，需要 O(logN)，所以总体的时间复杂度是 O(logN*logN)。
         * @param root
         * @return
         */
        public int countNodes(TreeNode root) {
            if (root == null) return 0;
            //如果是完全二叉树，则是两种树的结合
            TreeNode l = root, r = root;
            int hl = 0, hr = 0;
            while(l != null) {
                l = l.left;
                hl++;
            }
            while(r != null) {
                r = r.right;
                hr ++;
            }
            //如果左右侧计算的高度相同，则是一个满二叉树
            if (hl == hr) {
                return (int)Math.pow(2, hl) - 1;
            }
            //如果左右侧的高度不同，则按照普通二叉树的逻辑计算
            return 1 + countNodes(root.left) + countNodes(root.right);
        }

    }
}
