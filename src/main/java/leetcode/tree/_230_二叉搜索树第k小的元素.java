package leetcode.tree;
//给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 个最小元素（从 1 开始计数）。
//
//
//
// 示例 1：
//
//
//输入：root = [3,1,4,null,2], k = 1
//输出：1
//
//
// 示例 2：
//
//
//输入：root = [5,3,6,2,4,null,null,1], k = 3
//输出：3
//
//
//
//
//
//
// 提示：
//
//
// 树中的节点数为 n 。
// 1 <= k <= n <= 10⁴
// 0 <= Node.val <= 10⁴
//
//
//
//
// 进阶：如果二叉搜索树经常被修改（插入/删除操作）并且你需要频繁地查找第 k 小的值，你将如何优化算法？
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 👍 632 👎 0


/**
 * BST 特性：
 * 1. 对于BST的每一个node，左子树节点的值都比 node 的值要小，右子树节点的值都比 node 值大。
 * 2. 对于 BST 的每一个 node，它的左侧子树和右侧子树都是 BST。
 * <p>
 * 二叉搜索树并不复杂，但我觉得它可以算是数据结构领域的半壁江山了，直接基于BST的数据结构如 AVL 树
 * 红黑树等拥有了自平珩的性质。还有 B+ 树、线段树都是基于 BST 的思想来设计的。
 * <p>
 * 还有一条性质在做算法题时需要注意，即二叉搜索树的 中序遍历 的结果是有序的（升序）。
 * <pre>
 *     void traverse(TreeNode root) {
 *         if(root == null) return;
 *         traverse(root.left);
 *         // 中序遍历的位置
 *         print(root.val);
 *         traverse(root.right);
 *     }
 * </pre>
 * @author mayanwei
 * @date 2022-06-11.
 * @see _538_把二叉搜索树转化为累加树
 * @see _1038_从二叉搜索树到累加树
 */
public class _230_二叉搜索树第k小的元素{

    /**
     * 这个时间复杂度，最坏的情况下时 O(n)
     */
    class Solution{
        int res, i = 0;

        public int kthSmallest(TreeNode root, int k) {
            visit(root, k);
            return res;
        }

        public void visit(TreeNode root, int k) {
            if (root == null) {
                return;
            }
            visit(root.left, k);
            // 中序遍历的位置
            i++;
            if (i == k) { // 找到第 k 小的元素
                res = root.val;
                return;
            }
            visit(root.right, k);
        }
    }

    /**
     * <pre>
     * 上面的算法时间复杂度比较高，像红黑树这种改良的自平衡二叉树，增删查改都是 O(logN)的复杂度，让你算一个第k小元素，时间负载读竟然要 O(N)
     * 这就显得有点低效了。
     *  BST 能够在对数时间找到该元素的根本原因还是在于其定义，左子树小右子树大，所以每个节点都可以通过比较自身的值判断左子树还是右子树搜索目标，
     * 从而避免了全树搜索，达到对数级别复杂度。
     *
     * 那么回到这个问题，想找到第 k 小的元素，或者说找到排名为 k 的元素，如果想达到对数级复杂度，关键也在于每个节点得知道他自己排第几。
     * 比如说你让我查找排名为 k 的元素，当前节点知道自己排名第 m，那么我可以比较 m 和 k 的大小：
     * 1、如果 m == k，显然就是找到了第 k 个元素，返回当前节点就行了。
     * 2、如果 k < m，那说明排名第 k 的元素在左子树，所以可以去左子树搜索第 k 个元素。
     * 3、如果 k > m，那说明排名第 k 的元素在右子树，所以可以去右子树搜索第 k - m - 1 个元素。
     * 每个节点维护额外信息，记录以自己为根的这颗树有多少个节点。
     * </pre>
     */
    class TreeNode {
        /**
         * 以该节点为根的节点总数
         */
        int size;
        int val;
        TreeNode left;
        TreeNode right;
    }

}
