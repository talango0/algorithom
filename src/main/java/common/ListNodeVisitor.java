package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mayanwei
 * @date 2021-06-17.
 */
public class ListNodeVisitor{
    public void visit(ListNode head){
        List itemList = new ArrayList();
        while (head != null){
            itemList.add( head.val );
            head = head.next;
        }
        System.out.println( itemList.toString());
    }
}
