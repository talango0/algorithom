package leetcode.tree;

import leetcode.jzhoffer.JZ61序列化二叉树;

import java.util.LinkedList;
import java.util.Queue;
//序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，同时也可以通过网络传输到另一个计算机环境，采取相反方
//式重构得到原数据。
//
// 请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串
//反序列化为原始的树结构。
//
// 提示: 输入输出格式与 LeetCode 目前使用的方式一致，详情请参阅 LeetCode 序列化二叉树的格式。你并非必须采取这种方式，你也可以采用其他的
//方法解决这个问题。
//
//
//
// 示例 1：
//
//
//输入：root = [1,2,3,null,null,4,5]
//输出：[1,2,3,null,null,4,5]
//
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
//输入：root = [1]
//输出：[1]
//
//
// 示例 4：
//
//
//输入：root = [1,2]
//输出：[1,2]
//
//
//
//
// 提示：
//
//
// 树中结点数在范围 [0, 10⁴] 内
// -1000 <= Node.val <= 1000
//
// Related Topics 树 深度优先搜索 广度优先搜索 设计 字符串 二叉树 👍 910 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * 字节
 *
 * @see JZ61序列化二叉树
 */
public class _297_二叉树的序列化和反序列化{
    /**
     * <pre>
     *     1. 如果序例化结果中不包含指针信息，且你只会给出一种遍历顺序，那么你无法还原出唯一的一颗二叉树。
     *     2. 如果你的序例化结果中不包含空指针信息，且会给出两种遍历顺序，前序和中序，后续和中序可以确定
     *         2.1. 前序+中序，后序+中序可以还原。
     *         2.2. 前序和后序，除非你的整颗树中不包含值相同的节点，否则你无法还原出一颗唯一二叉树。
     *     3. 如果序列化结果中包含空指针信息，且你给出一种遍历顺序，也要分两种情况：
     *         3.1. 前序、后序，可以还原出一颗唯一的二叉树。
     *         3.2. 中序，除非你的整颗树不包含相同的节点，否则无法还原出一棵二叉树
     *  ┌────────────────────┐
     *  │ inOrder: #,1,#,1,# │
     *  └────────────────────┘
     *     ┌───┐      ┌───┐
     *     │ 1 │      │ 1 │
     *     └───┘      └───┘
     *       │          │
     *   ┌───┘          └───┐
     * ┌─▼─┐              ┌─▼─┐
     * │ 1 │              │ 1 │
     * └───┘              └───┘
     *
     * </pre>
     *
     *
     */

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode(int x) { val = x; }
     * }
     */
    public class Codec1{
        String SEP = ",";
        String NULL = "#";
        StringBuilder sb = new StringBuilder();

        void traverse(TreeNode root) {
            if (root == null) {
                sb.append(NULL).append(SEP);
                return;
            }
            //前序遍历的位置
            sb.append(root.val).append(SEP);
            traverse(root.left);
            traverse(root.right);
        }

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            traverse(root);
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {

            LinkedList<String> nodes = new LinkedList<>();
            for (String s : data.split(SEP)) {
                nodes.addLast(s);
            }

            return construct(nodes);
        }

        TreeNode construct(LinkedList<String> nodes) {
            if (nodes.isEmpty()) {
                return null;
            }
            String treeVal = nodes.removeFirst();
            if (NULL.equals(treeVal)) {
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(treeVal));
            root.left = construct(nodes);
            root.right = construct(nodes);
            return root;
        }
    }

    public class Codec2{
        String SEP = ",";
        String NULL = "#";
        StringBuilder sb = new StringBuilder();

        void traverse(TreeNode root) {
            if (root == null) {
                sb.append(NULL).append(SEP);
                return;
            }

            traverse(root.left);
            traverse(root.right);
            //后序遍历的位置
            sb.append(String.valueOf(root.val)).append(SEP);
        }

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            traverse(root);
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {

            LinkedList<String> nodes = new LinkedList<>();
            for (String s : data.split(SEP)) {
                nodes.addLast(s);
            }

            return construct(nodes);
        }

        TreeNode construct(LinkedList<String> nodes) {
            if (nodes.isEmpty()) {
                return null;
            }
            String treeVal = nodes.removeLast();
            if (NULL.equals(treeVal)) {
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(treeVal));
            root.right = construct(nodes);
            root.left = construct(nodes);
            return root;
        }
    }

    /**
     * 层序遍历
     */
    public class Codec3{
        String SEP = ",";
        String NULL = "#";


        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) return "";
            StringBuilder sb = new StringBuilder();
            Queue<TreeNode> q = new LinkedList<TreeNode>();
            q.offer(root);
            while (!q.isEmpty()) {
                TreeNode cur = q.poll();
                //层序遍历代码位置
                if (cur == null) {
                    sb.append(NULL).append(SEP);
                    continue;
                }
                sb.append(cur.val).append(SEP);

                q.offer(cur.left);
                q.offer(cur.right);
            }
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {

            //反序列化的思路；用队列进行层序遍历，同时用索引i记录对应子节点的位置

            if (data.isEmpty()) return null;
            String[] nodes = data.split(SEP);
            //第一个元素就是 root 的值
            TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
            // 用队列q记录父节点，将root加入队列
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);
            for (int i = 1; i < nodes.length; ) {
                //队列中存的都是父节点
                TreeNode parent = q.poll();
                //父节点对应的左孩子节点的值
                String left = nodes[i++];
                if (!NULL.equals(left)) {
                    parent.left = new TreeNode(Integer.parseInt(left));
                    q.offer(parent.left);
                }
                else {
                    parent.left = null;
                }
                //父节点对应的右孩子节点的值
                String right = nodes[i++];
                if (!NULL.equals(right)) {
                    parent.right = new TreeNode(Integer.parseInt(right));
                    q.offer(parent.right);
                }
                else {
                    parent.right = null;
                }
            }

            return root;
        }
    }


}
