package leetcode.list;

import org.junit.jupiter.api.Test;

public class ListNodeQuestions {
    /**
     * ListNode Struct
     */
    public static class ListNode {
        int val;
        ListNode next = null;
        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * Solutions
     * 1. FindKthToTail
     * 2. ReverseList
     * 3. Merge
     */
    public static class Solution {
        public ListNode FindKthToTail(ListNode head,int k) {
            if(head == null || k==0)return null;
            ListNode res = null;//用于显示结果的引用链表
            ListNode p = head;//用于遍历的引用链表
            int i= 0;
            while(p != null){
                p = p.next;
                i++;
                if(i<k) continue;
                else {
//                    res = new ListNode(head.val);
                    res = head;
                    head = head.next;
                }

            }
            return k>(i+1)?null:res;
        }

        /**
         * ReverseList
         * @param head
         * @return
         */
        public ListNode ReverseList(ListNode head) {
            if(head == null||head.next==null)return head;
            ListNode res = new ListNode(head.val);
            head = head.next;
            while(head != null){
                ListNode tmp = res;
                res = head;
                head = head.next;
                res.next = tmp;
            }
            return res;
        }


        /**
         * Merge
         * @param list1
         * @param list2
         * @return
         */
        public ListNode Merge(ListNode list1,ListNode list2) {
            if(list1 == null)return list2;
            if(list2 == null)return list1;
            ListNode tmp;
            if(list1.val > list2.val){
                tmp = list2;
                list2 = list2.next;
            }else{
                tmp = list1;
                list1 = list1.next;
            }
            ListNode res = tmp;
            while(list1 != null && list2 != null){
                if(list1.val > list2.val){
                    tmp.next = list2;
                    list2 = list2.next;
                }else{
                    tmp.next = list1;
                    list1 = list1.next;
                }
                tmp = tmp.next;
            }
            if(list1!=null)tmp.next = list1;
            if(list2!=null)tmp.next = list2;
            return res;
        }
    }



    @Test
    public void testMerge(){
        int [] arr1 = {1, 3, 5};
        int [] arr2 = {2, 4, 6};
        ListNode list1 = null;
        ListNode list2 = null;
        ListNode tmp = null;
        for(int i=0;i<arr1.length;i++){
            if(i == 0){
                list1 = new ListNode(arr1[i]);
                tmp = list1;
                continue;
            }
            tmp.next = new ListNode(arr1[i]);
            tmp = tmp.next;
        }
        for(int i=0;i<arr2.length;i++){
            if(i == 0){
                list2 = new ListNode(arr2[i]);
                tmp = list2;
                continue;
            }
            tmp.next = new ListNode(arr2[i]);
            tmp = tmp.next;
        }

//        while (list1 != null){
//            System.out.print(list1.val);
//            list1 = list1.next;
//        }
//        while (list2 != null){
//            System.out.print(list2.val);
//            list2 = list2.next;
//        }

        Solution solution = new Solution();
        ListNode res = solution.Merge(list1, list2);
        while (res != null){
            System.out.println();
            System.out.print(res.val);
            res = res.next;
        }


    }


    public static void main(String[] args) {
        Solution solution = new Solution();


        int i = 0;
        ListNode head = new ListNode(++i);
        ListNode tmp = head;
        while (i<0){
            tmp.next = new ListNode(++i);
            tmp = tmp.next;
        }


//        while (head!=null){
//            System.out.print(head.val);
//            head = head.next;
//        }
//        ListNode listNode = solution.FindKthToTail(head, 5);
        ListNode listNode = solution.ReverseList(head);

        while (listNode != null){
            System.out.print(listNode.val);
            listNode = listNode.next;
        }


    }

}
