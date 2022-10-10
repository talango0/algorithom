package leetcode.jzhoffer;

import leetcode.tree.TreeNode;
import leetcode.tree._297_二叉树的序列化和反序列化;

import java.util.LinkedList;

/**
 * @author mayanwei
 * @date 2022-10-10.
 * @see _297_二叉树的序列化和反序列化
 * @see JZ61序列化二叉树
 */
public class 剑指_Offer_II_048_序列化与反序列化二叉树{
    /**
     * 前序遍历
     */
    public class Codec{
        String SEP = ",";
        String NULL = "#";

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            serialize(root, sb);
            return sb.toString();
        }

        private void serialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append(NULL).append(SEP);
                return;
            }
            sb.append(root.val).append(SEP);
            serialize(root.left, sb);
            serialize(root.right, sb);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            // 将字符串转化成列表
            LinkedList<String> nodes = new LinkedList<>();
            for (String s : data.split(SEP)) {
                nodes.addLast(s);
            }
            return deserialize(nodes);
        }

        private TreeNode deserialize(LinkedList<String> nodes) {
            if (nodes.isEmpty()) {
                return null;
            }
            /** 前序遍历的位置 */
            // 列表最左侧就是根节点
            String first = nodes.removeFirst();
            if (NULL.equals(first)) {
                return null;
            }
            TreeNode root = new TreeNode(Integer.valueOf(first));
            /***/
            root.left = deserialize(nodes);
            root.right = deserialize(nodes);
            return root;
        }
    }
}
