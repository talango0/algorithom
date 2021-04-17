package inductiontoalgorithm.union_find;


import java.util.Arrays;

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

        //小树接到大叔下，较平衡
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
