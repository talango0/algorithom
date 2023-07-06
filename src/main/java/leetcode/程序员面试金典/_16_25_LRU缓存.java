package leetcode.程序员面试金典;
//设计和构建一个“最近最少使用”缓存，该缓存会删除最近最少使用的项目。缓存应该从键映射到值(允许你插入和检索特定键对应的值)，
// 并在初始化时指定最大容量。当缓存被填满时，它应该删除最近最少使用的项目。
//
//它应该支持以下操作： 获取数据 get 和 写入数据 put 。
//
//获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
//写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。当缓存容量达到上限时，它应该在写入新数据之前删除最近最少使用的数据值，从而为新的数据值留出空间。
//
//示例:
//
//LRUCache cache = new LRUCache( 2 /* 缓存容量 */ );
//
//cache.put(1, 1);
//cache.put(2, 2);
//cache.get(1);       // 返回  1
//cache.put(3, 3);    // 该操作会使得密钥 2 作废
//cache.get(2);       // 返回 -1 (未找到)
//cache.put(4, 4);    // 该操作会使得密钥 1 作废
//cache.get(1);       // 返回 -1 (未找到)
//cache.get(3);       // 返回  3
//cache.get(4);       // 返回  4
//通过次数47,123提交次数84,988
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/lru-cache-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2023-06-16.
 */
public class _16_25_LRU缓存{
    /**
     * <pre>
     * ┌──────────────────┐
     * │ 1 2 1 3 4 3 1 5 6│
     * ├──────────────────┤
     * │ 1 2 1 3 4 3 5 6  │
     * │   1 2 1 3 4 3 5  │
     * │       2 1 1 4 3  │
     * └──────────────────┘
     * capacity = 3
     * </pre>
     */
    class LRUCache{

        int cap;
        LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();

        public LRUCache(int capacity) {
            this.cap = capacity;
        }

        public int get(int key) {
            if (!cache.containsKey(key)) {
                return -1;
            }
            //将key 变成最近使用
            makeRecently(key);
            return cache.get(key);
        }

        public void put(int key, int val) {
            if (cache.containsKey(key)) {
                // 修改key的值
                cache.put(key, val);
                // 将key 变为最近使用
                makeRecently(key);
                return;
            }
            if (cache.size() >= this.cap) {
                // 链表头部就是最久未使用的 key
                int oldestKey = cache.keySet().iterator().next();
                cache.remove(oldestKey);
            }
            //将新的key 添加到链表尾部
            cache.put(key, val);
        }

        private void makeRecently(int key) {
            int val = cache.get(key);
            // 删除key，然后重新插入到队尾
            cache.remove(key);
            cache.put(key, val);
        }
    }




    class LRUCache2{
        class ListNode{
            int key;
            int value;
            ListNode next;
            ListNode pre;

            public ListNode(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        int capacity;
        int num;
        Map<Integer, ListNode> map;
        ListNode tail;
        ListNode head;

        public LRUCache2(int capacity) {
            this.tail = new ListNode(-1, 0);
            this.head = new ListNode(-1, 0);
            this.head.next = this.tail;
            this.tail.pre = this.head;
            this.tail.next = null;
            this.num = 0;
            this.capacity = capacity;
            this.map = new HashMap<>();

        }

        public int get(int key) {
            ListNode node = map.get(key);
            if (node != null) {
                MoveToHead(node);
                return node.value;
            }
            else {
                return -1;
            }
        }

        public void put(int key, int value) {
            ListNode node = map.get(key);
            if (node != null) {
                node.value = value;
                MoveToHead(node);
            }
            else {
                node = new ListNode(key, value);
                map.put(key, node);
                AddToHead(node);
                num++;
                if (num > capacity) {
                    ListNode tailNode = removeTail();
                    map.remove(tailNode.key);
                    num--;
                }
            }
        }

        public void MoveToHead(ListNode node) {
            DeleteEle(node);
            AddToHead(node);
        }

        public void AddToHead(ListNode node) {
            ListNode tmp = head.next;
            head.next = node;
            node.next = tmp;
            tmp.pre = node;
            node.pre = head;
        }

        public void DeleteEle(ListNode node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        public ListNode removeTail() {
            ListNode node = tail.pre;
            DeleteEle(node);
            return node;
        }
    }

}
