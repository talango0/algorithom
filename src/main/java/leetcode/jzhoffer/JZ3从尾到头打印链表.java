package leetcode.jzhoffer;

import common.ListNode;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 题目描述
 * 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
 * 示例1
 * 输入
 * {67,0,24,58}
 * 返回值
 * [58,24,0,67]
 */
public class JZ3从尾到头打印链表 {

    public class Solution {
        public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
            ArrayList<Integer> res = new ArrayList<Integer>();
            if(listNode == null){
                return res;
            }
            Stack<Integer> tmpStcket = new Stack<Integer>();
            while(listNode != null){
                tmpStcket.push(listNode.val);
                listNode = listNode.next;
            }
            while(!tmpStcket.empty()){
                res.add(tmpStcket.pop());
            }
            return res;
        }
    }
}
