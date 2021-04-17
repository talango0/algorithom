package leetcode.jzhoffer;

import common.RandomListNode;

import java.util.HashMap;

public class JZ25复杂链表的复制 {
    /*
题目描述
输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针random指向一个随机节点），
请对此链表进行深拷贝，并返回拷贝后的头结点。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
     */

    public class Solution {
        public RandomListNode Clone(RandomListNode pHead) {

            if (pHead == null) {
                return null;
            }
            RandomListNode p1 = pHead;
            RandomListNode p2 = pHead;
            HashMap<RandomListNode, RandomListNode> refMap = new HashMap<>();
            while (p1 != null) {
                refMap.put(p1, new RandomListNode(p1.label));
                p1 = p1.next;
            }
            while (p2 != null) {
                RandomListNode newNode = refMap.get(p2);
                newNode.next = refMap.get(p2.next);
                newNode.random = refMap.get(p2.random);
                p2 = p2.next;
            }
            return refMap.get(pHead);
        }
    }
}
