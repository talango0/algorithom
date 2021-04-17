package leetcode.jzhoffer;

import common.ListNode;


/***
 * 题目描述
 * 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
 * 示例1
 * 输入
 * {1,3,5},{2,4,6}
 * 返回值
 * {1,2,3,4,5,6}
 */
public class JZ16合并两个排序的链表 {
    public class Solution {
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
            tmp.next = list1!=null ? list1:list2;
            return res;
        }
    }
}
