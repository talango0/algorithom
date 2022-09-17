package inductiontoalgorithm.segment_tree;


/**
 * 线段树
 * @author mayanwei
 * @date 2022-09-17.
 */
public class SegmentTree implements ISegmentTree{

    private final int ORIGIN_LEN;
    /**
     * arr 为原序列的信息，但是在arr 里面是从1开始的
     */
    private final int[] arr;
    /**
     * 模拟线段树维护区间和
     */
    private final int[] sum;
    /**
     * 累加和懒惰标记
     */
    private final int[] lazy;
    /**
     * 为更新的值
     */
    private final int[] change;
    /**
     * 为更新懒惰标记
     */
    private final boolean[] update;


    public SegmentTree(int[] origin) {
        ORIGIN_LEN = origin.length;
        int MAXN = ORIGIN_LEN + 1;
        arr = new int[MAXN];
        System.arraycopy(origin, 0, arr, 1, origin.length);

        // 某一个范围的累加和信息
        sum = new int[MAXN << 2];
        // 某一个范围没有往下传递的累加和
        lazy = new int[MAXN << 2];
        // 某一个范围有没有更新操作任务
        change = new int[MAXN << 2];
        // 某一个范围更新任务更新成了什么
        update = new boolean[MAXN << 2];

        build(1, ORIGIN_LEN, 1);
    }

    /**
     * 获取节点 i 的左孩子下标
     *
     * @param i
     * @return
     */
    private int left(int i) {
        return i << 1;
    }

    /**
     * 获取节点 i 的右孩子下标
     *
     * @param i
     * @return
     */
    private int right(int i) {
        return (i << 1) | 1;
    }

    @Override
    public void add(int L, int R, int V) {
        add(L, R, V, 1, ORIGIN_LEN, 1);
    }

    /**
     * 将 [L, R] 区间的所有元素加 v 的任务向下分发到 [l,r] 的所有元素加 v
     *
     * @param L
     * @param R
     * @param V
     * @param r
     * @param root
     */
    public void add(int L, int R, int V, int l, int r, int root) {
        // 原任务如果把此时的子任务覆盖了
        if (L <= l && r <= R) {
            sum[root] += V * (r - l + 1);
            lazy[root] += V;
            return;
        }
        // 前序位置
        // 任务没有被覆盖
        int mid = l + ((r - l) >> 1);
        pushDown(root, mid - l + 1, r - mid);

        //L～R
        if (L <= mid) {
            add(L, R, V, l, mid, left(root));
        }
        if (R > mid) {
            add(L, R, V, mid + 1, r, right(root));
        }
        // 后序位置
        pushUp(root);
    }

    private void pushUp(int root) {
        sum[root] = sum[left(root)] + sum[right(root)];
    }

    /**
     * 之前的所有懒增加、懒更新，从父范围向左右两个子范围
     * 分发策略是什么
     *
     * @param root
     * @param ln   左子树的元素结点个数
     * @param rn   右子树结点个数
     */
    private void pushDown(int root, int ln, int rn) {
        int left = left(root);
        int right = right(root);
        if (update[root]) {
            update[left] = true;
            update[right] = true;
            change[left] = change[root];
            change[right] = change[root];
            lazy[left] = 0;
            lazy[right] = 0;
            sum[left] = change[root] * ln;
            sum[right] = change[root] * rn;
            update[root] = false;
        }
        if (lazy[root] != 0) {
            lazy[left] += lazy[root];
            sum[left] += lazy[root] * ln;
            lazy[right] += lazy[root];
            sum[right] += lazy[root] * rn;
            lazy[root] = 0;
        }
    }

    @Override
    public void update(int L, int R, int V) {
        update(L, R, V, 1, ORIGIN_LEN, 1);
    }

    /**
     * [L,R] 区间的所有元素都更新为 V
     * [l,r] 子任务区间索引范围 root 根结点索引
     *
     * @param L
     * @param R
     * @param V
     * @param l
     * @param r
     * @param root
     */
    public void update(int L, int R, int V, int l, int r, int root) {
        // 原任务如果把此时的子任务覆盖了
        if (L <= l && r <= R) {
            update[root] = true;
            change[root] = V;
            sum[root] = V * (r - l + 1);
            lazy[root] = 0;
            return;
        }
        // 当前任务躲不掉，无法懒更新，要往下发
        int mid = l + ((r - l) >> 1);
        pushDown(root, mid - l + 1, r - mid);
        if (L <= mid) {
            update(L, R, V, l, mid, left(root));
        }
        if (R > mid) {
            update(L, R, V, mid + 1, r, right(root));
        }
        pushUp(root);
    }

    /**
     * 在初始化阶段，先把 sum数组填好
     * 在 arr[l..r] 范围上去build， 1～N
     * 这个范围在 sum 中的下标
     *
     * @param l
     * @param r
     * @param root
     */
    public void build(int l, int r, int root) {
        if (l == r) {
            sum[root] = arr[l];
            return;
        }
        int mid = l + ((r - l) >> 1);
        build(l, mid, left(root));
        build(mid + 1, r, right(root));
        pushUp(root);
    }

    @Override
    public long getSum(int L, int R) {
        return query(L, R, 1, ORIGIN_LEN, 1);
    }

    /**
     * [L, R] 累加和任务
     * [l,r] 区间累加和子任务
     *
     * @param L
     * @param R
     * @param l
     * @param r
     * @param root
     * @return
     */
    public long query(int L, int R, int l, int r, int root) {
        if (L <= l && r <= R) {
            return sum[root];
        }
        int mid = l + ((r - l) >> 1);
        pushDown(root, mid - l + 1, r - mid);
        long ans = 0;
        if (L <= mid) {
            ans += query(L, R, l, mid, left(root));
        }
        if (R > mid) {
            ans += query(L, R, mid + 1, r, right(root));
        }
        return ans;
    }

    /**
     * 暴力法，用于测试算法正确性
     */
    static class DummyRightSolution{
        public int[] arr;

        public DummyRightSolution(int[] origin) {
            arr = new int[origin.length + 1];
            System.arraycopy(origin, 0, arr, 1, origin.length);
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }

}

