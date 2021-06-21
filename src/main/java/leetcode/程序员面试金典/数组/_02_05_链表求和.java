package leetcode.程序员面试金典.数组;

import common.ListNode;
import common.ListNodeFactory;

/**
 * @author mayanwei
 * @date 2021-06-21.
 */
public class _02_05_链表求和{
    //给定两个用链表表示的整数，每个节点包含一个数位。
//
// 这些数位是反向存放的，也就是个位排在链表首部。
//
// 编写函数对这两个整数求和，并用链表形式返回结果。
//
//
//
// 示例：
//
// 输入：(7 -> 1 -> 6) + (5 -> 9 -> 2)，即617 + 295
//输出：2 -> 1 -> 9，即912
//
//
// 进阶：思考一下，假设这些数位是正向存放的，又该如何解决呢?
//
// 示例：
//
// 输入：(6 -> 1 -> 7) + (2 -> 9 -> 5)，即617 + 295
//输出：9 -> 1 -> 2，即912
//
// Related Topics 链表 数学
// 👍 78 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    static class Solution{

        public ListNode addTwoNumbers(ListNode l1, ListNode l2){
            if (l1 == null && l2 != null){
                return l2;
            }
            if (l1 != null && l2 == null){
                return l1;
            }
            if (l1 == null && l2 == null){
                return null;
            }

            int carry = 0;
            int sum = 0;
            ListNode res = new ListNode( Integer.MAX_VALUE );
            ListNode cur = res;
            while (l1 != null && l2 != null) {
                sum = l1.val + l2.val + carry;
                cur.next = new ListNode( sum % 10 );
                cur = cur.next;
                cur.next = null;
                carry = sum / 10;
                l1 = l1.next;
                l2 = l2.next;
            }
            while (l1 != null) {
                sum = l1.val + carry;
                cur.next = new ListNode( sum % 10 );
                cur = cur.next;
                cur.next = null;
                carry = sum / 10;
                l1 = l1.next;
            }
            while (l2 != null) {
                sum = l2.val + carry;
                cur.next = new ListNode( sum % 10 );
                cur = cur.next;
                cur.next = null;
                carry = sum / 10;
                l2 = l2.next;
            }
            if (carry != 0){
                cur.next = new ListNode( carry );
                cur = cur.next;
                cur.next = null;
            }
            return res.next;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    /**
     * 如果反向存储
     */
    static class Solution2{
        public ListNode addTwoNumbers(ListNode l1, ListNode l2){

            if (l1 == null && l2 != null){
                return l2;
            }
            if (l1 != null && l2 == null){
                return l1;
            }
            if (l1 == null && l2 == null){
                return null;
            }
            l1 = reverse( l1 );
            l2 = reverse( l2 );

            int carry = 0;
            int sum = 0;
            ListNode res = new ListNode( Integer.MAX_VALUE );
            ListNode cur = res;
            while (l1 != null && l2 != null) {
                sum = l1.val + l2.val + carry;
                cur.next = new ListNode( sum % 10 );
                cur = cur.next;
                cur.next = null;
                carry = sum / 10;
                l1 = l1.next;
                l2 = l2.next;
            }
            while (l1 != null) {
                sum = l1.val + carry;
                cur.next = new ListNode( sum % 10 );
                cur = cur.next;
                cur.next = null;
                carry = sum / 10;
                l1 = l1.next;
            }
            while (l2 != null) {
                sum = l2.val + carry;
                cur.next = new ListNode( sum % 10 );
                cur = cur.next;
                cur.next = null;
                carry = sum / 10;
                l2 = l2.next;
            }
            if (carry != 0){
                cur.next = new ListNode( carry );
                cur = cur.next;
                cur.next = null;
            }


            return reverse( res.next );


        }

        /**
         * 链表反转
         *
         * @param l
         * @return
         */
        public ListNode reverse(ListNode l){
            ListNode head = l, cur;
            l = l.next;
            head.next = null;
            while (l != null) {
                cur = l;
                l = l.next;
                cur.next = head;
                head = cur;
            }
            return head;
        }

    }

    public static void main(String[] args){
        ListNode l1 = ListNodeFactory.getListNodeBuilder().add( 2, 4, 3 ).build();
        ListNode l2 = ListNodeFactory.getListNodeBuilder().add( 5, 6, 4 ).build();
        ListNodeFactory.getListNodeVisitor().visit( l1 );
        ListNodeFactory.getListNodeVisitor().visit( l2 );

        ListNode res1 = new Solution().addTwoNumbers( l1, l2 );
        ListNodeFactory.getListNodeVisitor().visit( res1 );

        System.out.println( "=======" );
        Solution2 solution2 = new Solution2();
        ListNode res2 = solution2.addTwoNumbers( l1, l2 );
        ListNodeFactory.getListNodeVisitor().visit( res2 );


    }


}
