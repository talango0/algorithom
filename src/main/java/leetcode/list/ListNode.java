package leetcode.list;

/**
 * @author mayanwei
 * @date 2022-08-12.
 */
public class ListNode{
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
