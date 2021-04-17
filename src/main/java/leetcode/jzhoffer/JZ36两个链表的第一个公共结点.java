package leetcode.jzhoffer;

import common.ListNode;

public class JZ36两个链表的第一个公共结点 {
    /*
    题目描述
    输入两个链表，找出它们的第一个公共结点。（注意因为传入数据是链表，所以错误测试数据的提示是用其他方式显示的，保证传入数据是正确的）
     */


    public class Solution {
        public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
            if(pHead1 == null || pHead2 == null)return null;
            ListNode pa = pHead1;
            ListNode pb = pHead2;
            while (pa != pb){
                pa = pa.next;
                pb = pb.next;
                if(pa != pb){
                    if( pa == null){
                        pa=pHead2;
                    }
                    if (pb == null){
                        pb = pHead1;
                    }
                }
            }
            return pa;
        }
    }
}
