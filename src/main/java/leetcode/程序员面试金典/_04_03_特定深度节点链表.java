package leetcode.程序员面试金典;

// 给定一棵二叉树，设计一个算法，创建含有某一深度上所有节点的链表（比如，若一棵树的深度为 D，则会创建出 D 个链表）。
// 返回一个包含所有深度的链表的数组。
// 示例：
// 输入：[1,2,3,4,5,null,7,8]
//         1
//        /  \ 
//       2    3
//      / \    \ 
//     4   5    7
//    /
//   8
// 输出：[[1],[2,3],[4,5,7],[8]]
// 
// 通过次数34,677提交次数42,964
// 来源：力扣（LeetCode）
// 链接：https://leetcode.cn/problems/list-of-depth-lcci
// 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.list.ListNode;
import leetcode.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class _04_03_特定深度节点链表 {
    class Solution {
        public ListNode[] listOfDepth(TreeNode tree) {
            int depth = 0;
            List<ListNode> arrList = new ArrayList<>();
            traverse(tree, arrList, depth);
            return arrList.toArray(new ListNode[arrList.size()]);
        }

        private void traverse(TreeNode root, List<ListNode> res, int depth) {
            if (root == null) {
                return;
            }
            if (depth == res.size()) {
                res.add(new ListNode(root.val));
            } else {
                addToLast(res.get(depth), root);
            }
            traverse(root.left, res, depth + 1);
            traverse(root.right, res, depth + 1);
        }
        private void addToLast(ListNode lNode, TreeNode tNode) {
            ListNode fast = lNode.next, slow = lNode;
            while (fast != null) {
                fast = fast.next;
                slow = slow.next;
            }
            slow.next = new ListNode(tNode.val);
        }
    }
}
