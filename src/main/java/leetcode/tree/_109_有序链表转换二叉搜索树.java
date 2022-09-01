package leetcode.tree;
//给定一个单链表的头节点 head，其中的元素 按升序排序 ，将其转换为高度平衡的二叉搜索树。
//
//本题中，一个高度平衡二叉树是指一个二叉树每个节点的左右两个子树的高度差不超过 1。
//
//
//
//示例 1:
//
//
//
//输入: head = [-10,-3,0,5,9]
//输出: [0,-3,9,-10,null,5]
//解释: 一个可能的答案是[0，-3,9，-10,null,5]，它表示所示的高度平衡的二叉搜索树。
//示例 2:
//
//输入: head = []
//输出: []
//
//
//提示:
//
//head中的节点数在[0, 2 * 104]范围内
//-105<= Node.val <= 105
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/convert-sorted-list-to-binary-search-tree
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import common.ListNode;

/**
 * @see _108_将有序数组转换为二叉搜索树
 */
public class _109_有序链表转换二叉搜索树 {
    class Solution {
        //通过中序遍历特点，有序
        public TreeNode sortedListToBST(ListNode head) {
            if(head == null) {
                return null;
            }
            int len = 0;
            for (ListNode p = head; p != null ;p = p.next){
                len++;
            }
            cur = head;
            return inOrderBuild(0, len-1);
        }
        ListNode cur;
        TreeNode inOrderBuild(int left, int right) {
            if (left > right) {
                return null;
            }
            int mid = (left + right)/2;
            // 构造左子树
            TreeNode leftNode = inOrderBuild(left, mid-1);
            // 构造根节点
            TreeNode root = new TreeNode(cur.val);
            cur = cur.next;

            // 构造右子树
            TreeNode rightNode = inOrderBuild(mid+1, right);
            // 将左子树和右子树接到 root
            root.left = leftNode;
            root.right = rightNode;
            return root;
        }
    }



    class Solution2 {
        //通过找链表中点的方式写出的解法
        public TreeNode sortedListToBST(ListNode head) {
            return build(head, null);
        }
        ListNode cur;
        // 把链表左闭右开区间 [begin, end) 的节点构造成 BST
        TreeNode build(ListNode begin, ListNode end) {
            if (begin == end) {
                // 因为是左闭右开区间，所以现在已经成空集了
                return null;
            }
            ListNode mid = getMid(begin, end);
            TreeNode root = new TreeNode(mid.val);
            root.left = build(begin, mid);
            root.right = build(mid.next, end);
            return root;
        }

        // 获取链表左闭右开区间 [begin, end) 的中心节点
        ListNode getMid(ListNode begin, ListNode end) {
            ListNode slow = begin, fast = begin;
            while (fast != end && fast.next != end) {
                slow = slow.next;
                fast = fast.next.next;
            }
            return slow;
        }

    }
}
