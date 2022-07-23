package leetcode.design;

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
    class LRUCache3{
        int cap;
        LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();
        public LRUCache3(int capacity) {
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
            if(cache.containsKey(key)) {
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

        /**
         * 链表元素个数
         */
        private int size;
        private int capacity;
        private Map<Integer, DNode> map;
        /**
         * 头尾虚节点
         */
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

    class LRUCache4{
        class Node{
            Node next, prev;
            int key, val;

            public Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }
        //根据 Node 节点构造双链表
        class DoubleList {
            // 头尾虚节点
            private Node head, tail;
            // 链表元素数
            private int size;

            public DoubleList() {
                // 初始化双向链表的数据
                head = new Node(0, 0);
                tail = new Node(0, 0);
                head.next = tail;
                tail.prev = head;
                size = 0;
            }

            // 在链表尾部添加节点 x，时间 O(1)
            public void addLast(Node x) {
                x.prev = tail.prev;
                x.next = tail;
                tail.prev.next = x;
                tail.prev = x;
                size++;
            }

            // 删除链表中的 x 节点（x 一定存在）
            // 由于是双链表且给的是目标 Node 节点，时间 O(1)
            public void remove(Node x) {
                x.prev.next = x.next;
                x.next.prev = x.prev;
                size--;
            }

            // 删除链表中第一个节点，并返回该节点，时间 O(1)
            public Node removeFirst() {
                if (head.next == tail)
                    return null;
                Node first = head.next;
                remove(first);
                return first;
            }
            // 返回链表长度，时间 O(1)
            public int size() { return size; }
        }


        // key -> Node(key, val)
        private HashMap<Integer, Node> map;
        // Node(k1, v1) <-> Node(k2, v2)...
        private DoubleList cache;
        // 最大容量
        private int cap;

        public LRUCache4(int capacity) {
            this.cap = capacity;
            map = new HashMap<>();
            cache = new DoubleList();
        }

        /* 将某个 key 提升为最近使用的 */
        private void makeRecently(int key) {
            Node x = map.get(key);
            // 先从链表中删除这个节点
            cache.remove(x);
            // 重新插到队尾
            cache.addLast(x);
        }

        /* 添加最近使用的元素 */
        private void addRecently(int key, int val) {
            Node x = new Node(key, val);
            // 链表尾部就是最近使用的元素
            cache.addLast(x);
            // 别忘了在 map 中添加 key 的映射
            map.put(key, x);
        }

        /* 删除某一个 key */
        private void deleteKey(int key) {
            Node x = map.get(key);
            // 从链表中删除
            cache.remove(x);
            // 从 map 中删除
            map.remove(key);
        }

        /* 删除最久未使用的元素 */
        private void removeLeastRecently() {
            // 链表头部的第一个元素就是最久未使用的
            Node deletedNode = cache.removeFirst();
            // 同时别忘了从 map 中删除它的 key
            int deletedKey = deletedNode.key;
            map.remove(deletedKey);
        }
        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            }
            // 将该数据提升为最近使用的
            makeRecently(key);
            return map.get(key).val;
        }

        public void put(int key, int val) {
            if (map.containsKey(key)) {
                // 删除旧的数据
                deleteKey(key);
                // 新插入的数据为最近使用的数据
                addRecently(key, val);
                return;
            }

            if (cap == cache.size()) {
                // 删除最久未使用的元素
                removeLeastRecently();
            }
            // 添加为最近使用的元素
            addRecently(key, val);
        }

    }
}
