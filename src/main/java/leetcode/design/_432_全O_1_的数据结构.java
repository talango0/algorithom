package leetcode.design;
//请你设计一个用于存储字符串计数的数据结构，并能够返回计数最小和最大的字符串。
//
// 实现 AllOne 类：
//
//
// AllOne() 初始化数据结构的对象。
// inc(String key) 字符串 key 的计数增加 1 。如果数据结构中尚不存在 key ，那么插入计数为 1 的 key 。
// dec(String key) 字符串 key 的计数减少 1 。如果 key 的计数在减少后为 0 ，那么需要将这个 key 从数据结构中删除。测试用例
//保证：在减少计数前，key 存在于数据结构中。
// getMaxKey() 返回任意一个计数最大的字符串。如果没有元素存在，返回一个空字符串 "" 。
// getMinKey() 返回任意一个计数最小的字符串。如果没有元素存在，返回一个空字符串 "" 。
//
//
// 注意：每个函数都应当满足 O(1) 平均时间复杂度。
//
//
//
// 示例：
//
//
//输入
//["AllOne", "inc", "inc", "getMaxKey", "getMinKey", "inc", "getMaxKey",
//"getMinKey"]
//[[], ["hello"], ["hello"], [], [], ["leet"], [], []]
//输出
//[null, null, null, "hello", "hello", null, "hello", "leet"]
//
//解释
//AllOne allOne = new AllOne();
//allOne.inc("hello");
//allOne.inc("hello");
//allOne.getMaxKey(); // 返回 "hello"
//allOne.getMinKey(); // 返回 "hello"
//allOne.inc("leet");
//allOne.getMaxKey(); // 返回 "hello"
//allOne.getMinKey(); // 返回 "leet"
//
//
//
//
// 提示：
//
//
// 1 <= key.length <= 10
// key 由小写英文字母组成
// 测试用例保证：在每次调用 dec 时，数据结构中总存在 key
// 最多调用 inc、dec、getMaxKey 和 getMinKey 方法 5 * 10⁴ 次
//
//
// Related Topics 设计 哈希表 链表 双向链表 👍 280 👎 0


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author mayanwei
 * @date 2022-09-15.
 *
 */
public class _432_全O_1_的数据结构{
    /**
     * 双线链表 + HashMap
     */
    class AllOne{
        Node root;
        Map<String, Node> nodes;


        public AllOne() {
            root = new Node();
            root.prev = root;
            root.next = root;
            nodes = new HashMap<String, Node>();
        }

        public void inc(String key) {
            if (nodes.containsKey(key)) {
                Node cur = nodes.get(key);
                Node nxt = cur.next;
                if (nxt == root || nxt.count > cur.count + 1) {
                    nodes.put(key, cur.insert(new Node(key, cur.count + 1)));
                }
                else {
                    nxt.keys.add(key);
                    nodes.put(key, nxt);
                }
                cur.keys.remove(key);
                if (cur.keys.isEmpty()) {
                    cur.remove();
                }
            }
            // key 不在链表中
            else {
                if (root.next == root || root.next.count > 1) {
                    nodes.put(key, root.insert(new Node(key, 1)));
                }
                else {
                    root.next.keys.add(key);
                    nodes.put(key, root.next);
                }
            }
        }

        public void dec(String key) {
            Node cur = nodes.get(key);
            // key仅出现1次
            if (cur.count == 1) {
                nodes.remove(key);
            }
            else {
                Node pre = cur.prev;
                if (pre == root || pre.count < cur.count - 1) {
                    nodes.put(key, cur.prev.insert(new Node(key, cur.count - 1)));
                }
                else {
                    pre.keys.add(key);
                    nodes.put(key, pre);
                }
            }
            cur.keys.remove(key);
            if (cur.keys.isEmpty()) {
                cur.remove();
            }
        }

        public String getMaxKey() {
            return root.prev != null ? root.prev.keys.iterator().next() :"";
        }

        public String getMinKey() {
            return root.next != null ? root.next.keys.iterator().next() :"";
        }

        class Node{
            Node prev;
            Node next;
            int count;
            Set<String> keys;

            public Node() {
                this("", 0);
            }

            public Node(String key, int count) {
                this.count = count;
                keys = new HashSet<String>();
                keys.add(key);
            }

            public Node insert(Node node) {  // 在 this 后插入 node
                node.prev = this;
                node.next = this.next;
                node.prev.next = node;
                node.next.prev = node;
                return node;
            }

            public void remove() {
                this.prev.next = this.next;
                this.next.prev = this.prev;
            }
        }
    }

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
}
