package leetcode.list;

/**
 * @author mayanwei
 * @date 2022-08-12.
 */
public class ListNode{
    public int val;
    public ListNode next;

    ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
