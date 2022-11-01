package leetcode.jzhoffer;

import leetcode.design._146_LRU缓存机制;

import java.util.LinkedHashMap;

/**
 * @author mayanwei
 * @date 2022-10-30.
 * @see _146_LRU缓存机制
 */
public class 剑指_Offer_II_031_最近最少使用缓存{
    class LRUCache{
        /**
         * 思路：
         * 哈希表可以实现O(1)复杂度的查找和删除操作
         * 列表可以实现排序完成最久未使用删除操作
         */
        int cap;
        LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();

        public LRUCache(int capacity) {
            this.cap = capacity;
        }

        public int get(int key) {
            if (!cache.containsKey(key)) {
                return -1;
            }
            // 将 key 变位最近使用
            makeRecently(key);
            return cache.get(key);
        }

        public void put(int key, int val) {
            if (cache.containsKey(key)) {
                // 修改 key 的值
                cache.put(key, val);
                // 将 key 变为最近使用
                makeRecently(key);
                return;
            }
            if (cache.size() >= this.cap) {
                // 链表头部就是最久未使用的key
                int oldestKey = cache.keySet().iterator().next();
                cache.remove(oldestKey);
            }
            // 将新的 key 添加链表尾部
            cache.put(key, val);
        }

        private void makeRecently(Integer key) {
            int val = cache.get(key);
            // 删除 key，然后重新插入队尾
            cache.remove(key);
            cache.put(key, val);
        }
    }

}
