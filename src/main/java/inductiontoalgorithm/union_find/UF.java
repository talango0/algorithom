package inductiontoalgorithm.union_find;


import leetcode.arrays._765_情侣牵手;
import leetcode.graph._1135_最低成本联通所有城市;
import leetcode.graph._1584_连接所有点的最小费用;
import leetcode.graph._323_无向图中连通分量的数目;
import leetcode.graph._990_等式方程的可满足性;

/**
 * 并查集问题背景。
 * 基本思路，使用森林（若干棵树）来表示图的动态连通性，用数组来具体实现这个森林。
 * 平衡性优化，使用树的重量来进行优化。
 * 路径压缩，并不关系路径如何，只关心根节点。
 *
 * @see _990_等式方程的可满足性
 * @see _323_无向图中连通分量的数目
 * @see _1135_最低成本联通所有城市
 * @see _1584_连接所有点的最小费用
 * @see _765_情侣牵手
 */

public class UF {

    /**
     * 连通分量个数
     */
    private int count;

    /**
     * 存储一棵树
     */
    private int [] parent;

    /**
     * 记录树的重量
     */
    private int [] size;

    public UF(int n) {
        this.count = n;
        parent = new int[n];
        size = new int[n];
        for(int i = 0; i < n ; i++){
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * 将p 和 q 连接，连通分量减1
     * @param p
     * @param q
     */
    public void union(int p, int q){
        int rootP = find(p);
        int rootQ = find(q);
        if(rootP == rootQ) {
            return;
        }

        //通过比较树的重量就可以保证树的生长相对平衡，树的高度大概在 logN 这个数量级，极大提高执行效率
        //小树接到大树下，较平衡
        if(size[rootP] > size[rootQ]){
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }else {
            parent[rootQ] = rootP;
            size[rootQ] += size[rootP];
        }
        count--;
    }

    /**
     * find 的主要功能从某个节点向上遍历到根，其时间复杂度就是树的高度，一般通过优化可以达到近似 logN
     * @param x
     * @return
     */
    private int find(int x){
        //路径压缩
        while (parent[x] != x){
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    /**
     * 第二种路径压缩方法，比 find 要压缩的彻底
     * @param x
     * @return
     */
    private int find0(int x){
        //路径压缩
        if (parent[x] != x) {
            parent[x] = find0(parent[x]);
        }
        return parent[x];
    }

    // 这段迭代代码方便你理解递归代码所做的事情
    public int find0_help(int x) {
        // 先找到根节点
        int root = x;
        while (parent[root] != root) {
            root = parent[root];
        }
        // 然后把 x 到根节点之间的所有节点直接接到根节点下面
        int old_parent = parent[x];
        while (x != root) {
            parent[x] = root;
            x = old_parent;
            old_parent = parent[old_parent];
        }
        return root;
    }

    /**
     * 测试q和q是否连通 ，通过优化时间复杂度可以降低为O(logN)
     * @param p
     * @param q
     * @return
     */
    public boolean connected(int p, int q){
        return find(p) == find(q);
    }

    /**
     * 返回连通分量的个数
     * @return
     */
    public int count(){
        return count;
    }

}
