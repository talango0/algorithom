package leetcode.程序员面试金典.数组;


//编写程序以 x 为基准分割链表，使得所有小于 x 的节点排在大于或等于 x 的节点之前。如果链表中包含 x，x 只需出现在小于 x 的元素之后(如下所示)。
//分割元素 x 只需处于“右半部分”即可，其不需要被置于左右两部分之间。
//
// 示例:
//
// 输入: head = 3->5->8->5->10->2->1, x = 5
//输出: 3->1->2->10->5->5->8
//
// Related Topics 链表 双指针
// 👍 37 👎 0


//leetcode submit region begin(Prohibit modification and deletion)


//leetcode submit region end(Prohibit modification and deletion)

import common.ListNode;
import common.ListNodeBuilder;
import common.ListNodeFactory;
import common.ListNodeVisitor;

public class 面试题02_04分割链表 {

    static class Solution {
        public ListNode partition(ListNode head, int x) {
            ListNode p1 = new ListNode( Integer.MAX_VALUE );
            ListNode p2 = new ListNode( Integer.MAX_VALUE );
            ListNode c1 = p1, c2 = p2;
            while (head != null){
                ListNode cur = head;
                head = head.next;
                cur.next = null;
                int val = cur.val;
                if(val < x){
                    c1.next = cur;
                    c1 = c1.next;
                }else {
                    c2.next = cur;
                    c2 = c2.next;
                }
            }
            c1.next = p2.next;
            c2.next = null;
            return p1.next;
        }
    }


    public static void main(String[] args) {
        ListNodeBuilder listNodeBuilder = ListNodeFactory.getListNodeBuilder();
        ListNodeVisitor listNodeVisitor = ListNodeFactory.getListNodeVisitor();
        ListNode node = listNodeBuilder.add( new int[]{ 1, 2, 2, 4, 3, 5 } ).build();
        listNodeVisitor.visit( node );
        Solution solution = new Solution();
        ListNode partition = solution.partition( node, 3 );
        listNodeVisitor.visit( partition );


    }
}
