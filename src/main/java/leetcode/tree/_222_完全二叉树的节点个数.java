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

    class Solution {
        // public int countNodes(TreeNode root) {
        //     if (root == null) return 0;
        //     //如果是普通的二叉树显然只要向下遍历一遍即可，时间复杂度为 O(N)
        //     return 1 + countNodes(root.left) + countNodes(root.right);
        // }
        // public int countNodes(TreeNode root) {
        //     if (root == null) return 0;
        //     int h = 0;
        //     //如果是一颗满二叉树
        //     while (root != null){
        //         root = root.left;
        //         h++;
        //     }
        //     return (int)Math.pow(2, h) - 1;
        // }

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
