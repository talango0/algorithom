package leetcode.tree;
//完全二叉树 是每一层（除最后一层外）都是完全填充（即，节点数达到最大）的，并且所有的节点都尽可能地集中在左侧。
//
// 设计一种算法，将一个新节点插入到一个完整的二叉树中，并在插入后保持其完整。
//
// 实现 CBTInserter 类:
//
//
// CBTInserter(TreeNode root) 使用头节点为 root 的给定树初始化该数据结构；
// CBTInserter.insert(int v) 向树中插入一个值为 Node.val == val的新节点 TreeNode。使树保持完全二叉树的状态
//，并返回插入节点 TreeNode 的父节点的值；
// CBTInserter.get_root() 将返回树的头节点。
//
//
//
//
//
//
//
// 示例 1：
//
//
//
//
//输入
//["CBTInserter", "insert", "insert", "get_root"]
//[[[1, 2]], [3], [4], []]
//输出
//[null, 1, 2, [1, 2, 3, 4]]
//
//解释
//CBTInserter cBTInserter = new CBTInserter([1, 2]);
//cBTInserter.insert(3);  // 返回 1
//cBTInserter.insert(4);  // 返回 2
//cBTInserter.get_root(); // 返回 [1, 2, 3, 4]
//
//
//
// 提示：
//
//
// 树中节点数量范围为 [1, 1000]
// 0 <= Node.val <= 5000
// root 是完全二叉树
// 0 <= val <= 5000
// 每个测试用例最多调用 insert 和 get_root 操作 10⁴ 次
//
//
// Related Topics 树 广度优先搜索 设计 二叉树 👍 146 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author mayanwei
 * @date 2022-10-30.
 */
public class _919_完全二叉树插入器{
    class CBTInserter {
        /**二叉树的层序遍历，用队列维护底部可以插入的节点即可 */

        // 这个队列只记录完全二叉树底部可以插入的节点
        private Queue<TreeNode> q = new LinkedList<>();
        private TreeNode root;

        public CBTInserter(TreeNode root) {
            this.root = root;
            // 进行普通的BFS，目的是找出底部可插入的节点
            Queue<TreeNode> temp = new LinkedList<>();
            temp.offer(root);
            while (!temp.isEmpty()) {
                TreeNode cur = temp.poll();
                if (cur.left != null) {
                    temp.offer(cur.left);
                }
                if (cur.right != null) {
                    temp.offer(cur.right);
                }
                if (cur.right == null || cur.left == null) {
                    // 找到完全二叉树的底部可以进行插入的节点
                    q.offer(cur);
                }
            }
        }

        public int insert(int val) {
            TreeNode node = new TreeNode(val);
            TreeNode cur = q.peek();
            if (cur.left == null) {
                cur.left = node;
            }
            else if (cur.right == null) {
                cur.right = node;
                q.poll();
            }
            // 新节点的左右节点也是可以插入的
            q.offer(node);
            return cur.val;
        }

        public TreeNode get_root() {
            return root;
        }
    }
}
