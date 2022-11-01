package leetcode.jzhoffer;

import leetcode.list.Node;
import leetcode.list._708_在循环有序的链表中插入结点;

/**
 * @author mayanwei
 * @date 2022-10-27.
 * @see _708_在循环有序的链表中插入结点
 */
public class 剑指_Offer_II_029_排序的循环链表{

    class Solution{
        /**
         * 如果循环链表为空，则插入一个新节点并将新节点的next指向自身， 插入新节点之后得到只有一个节点的循环链表。
         * <p>
         * 如果循环链表的头节点的next指针指向自身，则循环链表只有一个节点，在节点之后插入新节点，将头节点的next指针指向新节点，
         * 将新节点的next指针指向头节点。
         * <p>
         * 如果循环链表中节点数大于1，则需要从头节点开始遍历循环链表，寻找插入新节点的位置，使得插入新节点之后的循环链表仍然保持有序。
         * 用curr 和 next分别表示当前节点和下一个节点，初识时curr 位于 head， next 位于head的下一个节点，由于链表中的节点数大于1，因此
         * curr ！= next。遍历的过程中，判断值 insertVal的新节点是否可以在curr 和 next之间插入，如果符合插入要求则在curr
         * 和 next 之间插入新节点，否则将 curr 和 next 同时向后移动，知道找到插入新节点的位置或者遍历完循环链表的所有节点。
         */
        public Node insert(Node head, int insertVal) {
            Node node = new Node(insertVal);
            if (head == null) {
                node.next = node;
                return node;
            }
            if (head.next == head) {
                head.next = node;
                node.next = head;
                return head;
            }

            Node cur = head, next = head.next;
            while (next != head) {
                if (insertVal >= cur.val && insertVal <= next.val) {
                    break;
                }
                if (cur.val > next.val) {
                    if (insertVal > cur.val || insertVal < next.val) {
                        break;
                    }
                }
                cur = cur.next;
                next = next.next;

            }
            cur.next = node;
            node.next = next;
            return head;
        }
    }
}
