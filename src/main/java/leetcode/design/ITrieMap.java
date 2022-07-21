package leetcode.design;

import java.util.List;

/**
 * 底层用Trie 树试下你的键值映射，键位 String 类型，值为类型V
 * @param <V>
 */
public interface ITrieMap <V>{
    /**
     * 增、改
     * @param key
     * @param value
     */
    void put(String key, V value);

    void remove(String key);

    V get(String key);

    boolean containsKey(String key);

    /**
     * 在 Map 中所有键中搜索 query 的最短前缀
     * @param query
     * @return
     */
    String shortestPrefixOf(String query);

    /**
     * 在 Map 的所有减重搜索 query 的最长前缀
     * @param query
     * @return
     */
    String longestPrefixOf(String query);

    /**
     * 搜索所有前缀为 prefix 的键
     * @param prefix
     * @return
     */
    List<String> keysWithPrefix(String prefix);

    /**
     * 判断是否存在前缀为 prefix 的键
     * @param prefix
     * @return
     */
    boolean hasKeyWithPrefix(String prefix);

    /**
     * 通配符 . 匹配任意字符，搜索所有匹配的键
     * keysWithPattern("t.a.") -> ["team", "that"]
     * @param pattern
     * @return
     */
    List<String> keysWithPattern(String pattern);

    /**
     * 通配符 . 匹配任意字符，判断是否存在匹配的键
     * @param pattern
     * @return
     */
    boolean hasKeyWithPattern(String pattern);

    /**
     * 返回 Map 中键值对的数量
     * @return
     */
    int size();
}
