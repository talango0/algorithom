package common;

import org.junit.Assert;

/**
 * @author mayanwei
 * @date 2021-06-17.
 */
public class ListNodeBuilder{
    private final ListNode head = new ListNode( Integer.MIN_VALUE );
    private ListNode tail = head;

    public ListNodeBuilder add(ListNode node){
        Assert.assertNotNull(node);
        tail.next = node;
        tail = tail.next;
        tail.next = null;
        return this;
    }
    private ListNode newNode(int val){
        ListNode node = new ListNode( val );
        node.next = null;
        return node;
    }

    public ListNodeBuilder add (int ... valList ){
        for(int val : valList){
            add( newNode( val ) );
        }
        return this;
    }

    public static ListNodeBuilder builder(){
        return new ListNodeBuilder();
    }

    public ListNode build(){
        return this.head.next;
    }
}
