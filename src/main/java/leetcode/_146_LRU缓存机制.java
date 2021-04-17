package leetcode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class _146_LRU缓存机制 {

    //运用你所掌握的数据结构，设计和实现一个 LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。
//
// 获取数据 get(key) - 如果关键字 (key) 存在于缓存中，则获取关键字的值（总是正数），否则返回 -1。
//写入数据 put(key, value) - 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字/值」。当缓存容量达到上限时，它应该在
//写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
//
//
//
// 进阶:
//
// 你是否可以在 O(1) 时间复杂度内完成这两种操作？
//
//
//
// 示例:
//
// LRUCache cache = new LRUCache( 2 /* 缓存容量 */ );
//
//cache.put(1, 1);
//cache.put(2, 2);
//cache.get(1);       // 返回  1
//cache.put(3, 3);    // 该操作会使得关键字 2 作废
//cache.get(2);       // 返回 -1 (未找到)
//cache.put(4, 4);    // 该操作会使得关键字 1 作废
//cache.get(1);       // 返回 -1 (未找到)
//cache.get(3);       // 返回  3
//cache.get(4);       // 返回  4
//
// Related Topics 设计
// 👍 948 👎 0


    //leetcode submit region begin(Prohibit modification and deletion)
    class LRUCache extends LinkedHashMap<Integer, Integer> {
        private int capacity;

        public LRUCache( int capacity ) {
            super(capacity, 0.75F, true);
            this.capacity = capacity;
        }

        public int get( int key ) {
            return super.getOrDefault(key, -1);
        }

        // 这个可不写
        public void put( int key, int value ) {
            super.put(key, value);
        }

        @Override
        protected boolean removeEldestEntry( Map.Entry<Integer, Integer> eldest ) {
            return size() > capacity;
        }
    }


    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */
//leetcode submit region end(Prohibit modification and deletion)


    class LRUCache2 {

        /*
         * 思路：通过HashMap和双向链表实现。
         * 连表保存访问顺序，HashMap可以根据key以O(1)的时间复杂度获取到Node的位置
         */

        private int size;
        private int capacity;
        private Map<Integer, DNode> map;
        private DNode head;
        private DNode tail;

        class DNode {
            DNode next;
            DNode pre;
            int key;
            int val;

            DNode( int _key, int _val ) {
                key = _key;
                val = _val;
            }

            DNode( ) {
            }
        }

        public LRUCache2( int capacity ) {
            this.size = 0;
            this.capacity = capacity;
            this.head = new DNode();
            this.tail = new DNode();
            head.next = tail;
            tail.pre = head;
            map = new HashMap<Integer, DNode>(capacity);
        }

        public int get( int key ) {
            DNode node = map.getOrDefault(key, null);
            if (node == null) {
                return -1;
            } else {
                moveToHead(node);
                return node.val;
            }
        }

        public void put( int key, int value ) {
            DNode node = map.getOrDefault(key, null);

            if (node != null) {
                //存在
                node.val = value;
                moveToHead(node);
            } else {
                //不存在
                node = new DNode(key, value);
                addToHead(node);

            }
        }

        private void evictionOldest( ) {
            DNode node = tail.pre;
            node.pre.next = tail;
            tail.pre = node.pre;
            if (node != head) {
                map.remove(node.key);
            }

        }

        private void addToHead( DNode node ) {
            node.next = head.next;
            head.next.pre = node;
            head.next = node;
            node.pre = head;
            map.put(node.key, node);
            if (map.size() > capacity) {
                evictionOldest();
            }
        }

        private void moveToHead( DNode node ) {
            node.pre.next = node.next;
            node.next.pre = node.pre;

            node.next = head.next;
            node.next.pre = node;
            node.pre = head;
            head.next = node;
        }

    }
}
