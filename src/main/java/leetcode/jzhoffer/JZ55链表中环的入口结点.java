package leetcode.jzhoffer;

import common.ListNode;

public class JZ55链表中环的入口结点 {
    /**
     * 题目描述
     * 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
     */

    public class Solution {


        public ListNode EntryNodeOfLoop(ListNode pHead){

            if(pHead == null){
                return null;
            }
            //判断链表中有环
            ListNode l = pHead, r = pHead;
            boolean flag = false;
            while (r != null && r.next!=null){
                l = l.next;
                r = r.next.next;
                if(l == r){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                return null;
            }else {
                //2.得到环中结点的数目
                int n = 1;
                r= r.next;
                while (l != r){
                    r = r.next;
                    n++;
                }
                //3. 找到环中的入口结点
                l=pHead;
                r = pHead;
                for(int i=0; i<n; i++){
                    r = r.next;
                }

                while (r != l){
                    l = l.next;
                    r = r.next;
                }
                return l;
            }

        }
    }

}
