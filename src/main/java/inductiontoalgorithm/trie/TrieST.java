package inductiontoalgorithm.trie;



import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 单词查找树
 * @author mayanwei
 */
public class TrieST<Value> {

    /**
     * 基数
     */
    private static int R = 256;

    /**
     * 单词查找树的根结点
     */
    private Node root;

    private static class Node{
        //因为Java 中不支持范型数组，因此val需要定义为Object对象，在使用是可以通过类型转换为 Value
        private Object val;
        private Node [] next = new Node[R];
    }

    /**
     * 根据 key 查找
     * @param key
     * @return
     */
    public Value get(String key){
        Node v = get(root, key, 0);
        return v == null ? null : (Value) v.val;
    }

    /**
     * 返回以 x 作为根结点的子单词查找树中与key相关联的值
     * @param x
     * @param key
     * @param d
     * @return
     */
    private Node get(Node x, String key, int d) {
        if(x == null){
            return null;
        }
        if(d == key.length()){
            return x;
        }
        // 找到第d个字符所对应的子单词查找树
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }


    public void put(String key, Value val){
        root = put(root, key, val, 0);
    }

    /**
     * 插入 key, 如果key存在，则更新与它相关联的值。
     * @param x
     * @param key
     * @param val
     * @param d
     * @return
     */
    private Node put(Node x, String key, Value val, int d) {
        if(x == null){
            x = new Node();
        }
        if(d == key.length()){
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }


    /**
     * 该方法只是指导意义，会有性能问题。实际上应该用全局变量表示大小。
     * @return
     */
    public int size(){
        return size(root);
    }

    private int size(Node x) {
        if(root == null){
            return 0;
        }
        int cnt = 0;
        if(x.val != null){
            cnt++;
        }
        for(int c = 0; c < R; c++){
            cnt += size(x.next[c]);
        }
        return cnt;
    }


    /**
     * 查找所有键
     *
     * 字符和键被隐式地表示在单词查找树中。在二叉搜索树中，将字符串键保存在一个对里面，但是对于单词查找树，不仅要能够在数据结构中找到这些键，还需要
     * 显示地表示这些键。
     * 思路：用一个递归函数 collect(） 完成这个任务，它维护一个字符串用来保存从根节点出发的路径上的一系列字符。
     * 每当我们在collect调用中访问一个节点时，如果它的值为非空，我们就将和他关联的字符串加入到队列中，然后（递归地）访问它的连接数据所指向的所有可能的字符结点。
     * 在每次调用之前，都将链接对应的字符附加到当前键的末尾作为参数调用的参数键。
     *
     */
    public Iterable<String> keys(){
        return keyWithPrefix("");
    }

    public Iterable<String> keyWithPrefix(String pre) {
        Deque<String> q = new ArrayDeque();
        collect(get(root,pre,0), pre, q);
        return q;
    }

    private void collect(Node x, String pre, Deque<String> q) {
        if(x == null){
            return;
        }
        if(x.val != null){
            q.addLast(pre);
        }
        for(char c = 0; c < R; c ++){
            collect(x.next[c],pre + c, q);
        }
    }



    /**
     * 通配符匹配
     */
    public Iterable<String> keysThatMath(String pat){
        Deque <String> q = new ArrayDeque<>();
        collect(root, "", pat, q);
        return q;
    }

    private void collect(Node x, String pre, String pat, Deque<String> q) {
        int d = pre.length();
        if(x == null){
            return;
        }
        if(d == pat.length() ){
            if(x.val != null){
                q.addLast(pre);
            }
            return;
        }
        char next = pat.charAt(d);
        for(char c = 0; c < R; c++){
            if(next == '.' || next == c){
                collect(x.next[c], pre + c, pat, q);
            }
        }

    }


    /**
     * 最长前缀
     * 为了找到给定字符串的最长键前缀，需要定一个类似与get()的递归调用。它会记录查找路径上锁找到的最长键的长度（将它作为递归方法的参数在遇到值非空的结点时更新它）。
     * 查找会在被查找的字符串结束或是遇到空链接时终止。
     * @param s
     * @return
     */
    public String longestPrefix( String s){
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length) {
        if(x == null){
            return length;
        }
        if(x.val != null){
            length = d;
        }
        if(d == s.length()){
            return length;
        }
        char c = s.charAt(d);
        return search(x.next[c], s, d+1, length);
    }


    /**
     * 删除操作
     */
    public void delete(String key){
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if(x == null){
            return null;
        }
        if(d == key.length()){
            x.val = null;
        }else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }

        if(x.val != null){
            return x;
        }
        for(char c = 0; c < R; c++){
            if(x.next[c] != null){
                return x;
            }
        }
        return null;
    }


}
