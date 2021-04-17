package leetcode.jzhoffer;

import common.ListNode;

public class JZ56删除链表中重复的结点 {
    /**
     * 题目描述
     * 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。
     * 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
     */

    public static class Solution {
        public ListNode deleteDuplication(ListNode pHead){
//            HashMap<Integer, ListNode> pMap = new HashMap<>();
//            ListNode p = pHead;
//            ListNode q;
//            pMap.put(p.val, null);
//            while (p.next!=null){
//                //让 q 指向 p.next 的父节点
//                q = p;
//                p = p.next;
//
//                if(pMap.keySet().contains(p.val)){
//                    ListNode qNode = pMap.get(p.val);
//                    //如果是根结点
//                    if(qNode == null){
//                        while (pHead.next!=null && pHead.val == p.val ){
//                          pHead = pHead.next;
//                        }
//                        p = pHead;
//                        pMap.put(pHead.val, null);
//                        continue;
//                    }else {
//                        qNode.next = qNode.next.next;
//                    }
//                    q.next = p.next;
//                    p = p.next;
//                }else {
//                    pMap.put(p.val, q);
//                }
//            }
//            return pHead;
            /**
             * 方法1
             */
//            if(pHead == null){
//                return null;
//            }
//
//            //先找出排序列表相同的结点，存入set
//            HashSet<Integer> set = new HashSet<>();
//            ListNode pre = pHead;
//            ListNode cur = pHead.next;
//            while (cur != null){
//                if(cur.val == pre.val){
//                    set.add(cur.val);
//                }
//                pre = cur;
//                cur = cur.next;
//            }
//
//            //再根据相同结点删除
//            //先删头部
//            while (pHead != null && set.contains(pHead.val) ){
//                pHead = pHead.next;
//            }
//
//            if(pHead == null){
//                return  null;
//            }
//
//            pre = pHead;
//            cur = pHead.next;
//            while (cur != null){
//                if(set.contains(cur.val)){
//                    pre.next = cur.next;
//                    cur = cur.next;
//                }else {
//                    pre = cur;
//                    cur = cur.next;
//                }
//            }
//            return pHead;

            /**
             * 方法2
             * 借助辅助头结点，可避免单独讨论头结点的情况。设置两个结点pre和 cur， 当cur和cur.next值相等，cur一直向前走，知道不等退出退出循环
             */
            if(pHead == null || pHead.next == null){
                return pHead;
            }
            //自己构建辅助头结点
            ListNode head = new ListNode(Integer.MIN_VALUE);
            head.next = pHead;
            ListNode pre = head;
            ListNode cur = head.next;
            while (cur!=null){
                if(cur.next != null && cur.next.val == cur.val){
                    //相同结点一直前进
                    while (cur.next != null && cur.next.val == cur.val){
                        cur = cur.next;
                    }
                    //退出循环是cur指向重复的值，也需要删除
                    cur = cur.next;
                    //pre连接新结点
                    pre.next = cur;
                }else {
                    pre = cur;
                    cur = cur.next;
                }
            }
            return head.next;
        }
    }

    public static void main(String[] args) {
        int [] arr = {6,6, 2, 2, 3, 5, 4, 4};
        ListNode root = new ListNode(arr[0]);
        ListNode p = root;
        for (int i = 1; i < arr.length; i++) {
            ListNode node = new ListNode(arr[i]);
            p.next = node;
            p = node;
        }

        Solution solution = new Solution();
        ListNode res = solution.deleteDuplication(root);
        while (res!=null){
            System.out.println(res.val);
            res = res.next;
        }

    }

}
