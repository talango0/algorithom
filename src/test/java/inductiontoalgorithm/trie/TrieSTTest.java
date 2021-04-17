package inductiontoalgorithm.trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrieSTTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void get() {
    }

    @Test
    void put() {
    }

    @Test
    void size() {
    }

    @Test
    void keys() {
        TrieST<Integer> trie = new TrieST<>();
        trie.put("hello",1);
        trie.put("world",2);
        trie.put("he",3);
        Iterable<String> keys = trie.keys();
        for (String key : keys) {
            System.out.println(key);
        }

        System.out.println("prifixWith he");
        for (String s : trie.keyWithPrefix("he")) {
            System.out.println(s);
        }
    }

    @Test
    void keyWithPrefix() {
    }

    @Test
    void keysThatMath() {
        TrieST<Integer> trie = new TrieST<>();
        trie.put("hello",1);
        trie.put("world",2);
        trie.put("he",3);
        Iterable<String> strings = trie.keysThatMath(".e");
        System.out.println(strings.toString());
    }

    @Test
    void longestPrefix() {
        TrieST<Integer> trie = new TrieST<>();
        trie.put("hello",1);
        trie.put("world",2);
        trie.put("he",3);
        String res = trie.longestPrefix("hello,world");
        System.out.println(res);
    }

    @Test
    void delete() {
        TrieST<Integer> trie = new TrieST<>();
        trie.put("hello",1);
        trie.put("world",2);
        trie.put("he",3);
        Iterable<String> keys = trie.keys();
        System.out.println(keys.toString());
        trie.delete("he");
        System.out.println(trie.keys().toString());
    }
}