package leetcode.list;
//给定循环单调非递减列表中的一个点，写一个函数向这个列表中插入一个新元素insertVal ，使这个列表仍然是循环升序的。
//
//给定的可以是这个列表中任意一个顶点的指针，并不一定是这个列表中最小元素的指针。
//
//如果有多个满足条件的插入位置，可以选择任意一个位置插入新的值，插入后整个列表仍然保持有序。
//
//如果列表为空（给定的节点是 null），需要创建一个循环有序列表并返回这个节点。否则。请返回原先给定的节点。
//
//
//
//示例 1：
//
//
//
//
//输入：head = [3,4,1], insertVal = 2
//输出：[3,4,1,2]
//解释：在上图中，有一个包含三个元素的循环有序列表，你获得值为 3 的节点的指针，我们需要向表中插入元素 2 。新插入的节点应该在 1 和 3 之间，插入之后，整个列表如上图所示，最后返回节点 3 。
//
//
//示例 2：
//
//输入：head = [], insertVal = 1
//输出：[1]
//解释：列表为空（给定的节点是 null），创建一个循环有序列表并返回这个节点。
//示例 3：
//
//输入：head = [1], insertVal = 0
//输出：[1,0]
//
//
//提示：
//
//0 <= Number of Nodes <= 5 * 10^4
//-10^6 <= Node.val <= 10^6
//-10^6 <=insertVal <= 10^6
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/4ueAj6
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2022-10-27.
 */
public class _708_在循环有序的链表中插入结点{

    class Solution{
        /**
         * 如果循环链表为空，则插入一个新节点并将新节点的next指向自身， 插入新节点之后得到只有一个节点的循环链表。
         * 如果循环链表的头节点的next指针指向自身，则循环链表只有一个节点，在节点之后插入新节点，将头节点的next指针指向新节点，将新节点的next指针指向头节点。
         * 如果循环链表中节点数大于1，则需要从头节点开始遍历循环链表，寻找插入新节点的位置，使得插入新节点之后的循环链表仍然保持有序。
         * 用curr 和 next分别表示当前节点和下一个节点，初识时curr 位于 head， next 位于head的下一个节点，由于链表中的节点数大于1，因此
         * curr ！= next。遍历的过程中，判断值 insertVal的新节点是否可以在curr 和 next之间插入，如果符合插入要求则在curr 和 next 之间插入新节点，否则将 curr 和 next 同时向后移动，知道找到插入新节点的位置或者遍历完循环链表的所有节点。
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
