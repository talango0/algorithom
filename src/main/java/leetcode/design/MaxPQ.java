package leetcode.design;

/**
 * 用二叉堆实现最大优先级队列
 *
 * @author mayanwei
 * @date 2022-07-24.
 */
public class MaxPQ<Key extends Comparable<Key>>{
    /**
     * 存储元素数组
     */
    private Key[] pq;
    /**
     * 当前 PriorityQueue 中的元素个数
     */
    private int size = 0;

    public MaxPQ(int cap) {
        // 索引0 不用，可以多分配一个空间
        pq = (Key[]) new Comparable[cap + 1];
    }

    /**
     * 返回队列中的最大元素
     *
     * @return
     */
    public Key max() {
        return pq[1];
    }

    /**
     * 插入元素 e
     * 先把插入的元素添加到堆低的最后，然后让其上浮到正确的位置
     *
     * @param e
     */
    public void insert(Key e) {
        size++;
        // 先把新元素加到最后
        pq[size] = e;
        // 然后将其上浮到正确的位置
        swim(size);
    }

    /**
     * 删除并返回当前队列中的最大元素
     * 先把堆顶元素 A 和堆低最后的元素 B 对调，然后删除 A，最后让 B下沉到正确的位置
     *
     * @return
     */
    public Key delMax() {
        // 最大堆顶就是最大元素
        Key max = pq[1];
        // 把这个最大元素换到最后，删除之
        swap(1, size);
        pq[size] = null;
        size--;
        // 让 pq[1] 下沉到正确位置
        sink(1);
        return max;
    }

    //最大堆，每个节点都比它的两个子节点大，但是在插入元素和删除元素时，难免破坏堆的性质，这就需要通过这两个操作来恢复堆的性质了。
    //对于最大堆，会破坏堆性质的有两种情况：
    //1、如果某个节点 A 比它的子节点（中的一个）小，那么 A 就不配做父节点，应该下去，下面那个更大的节点上来做父节点，这就是对 A 进行下沉。
    //2、如果某个节点 A 比它的父节点大，那么 A 不应该做子节点，应该把父节点换下来，自己去做父节点，这就是对 A 的上浮。

    /**
     * 上浮第 x 个元素，以维护最大堆性质
     *
     * @param x
     */
    private void swim(int x) {
        // 如果上浮到堆顶，就不能在上浮了
        while (x > 1 && less(parent(x), x)) {
            // 如果第 x 个元素比上层大，将 x 换上去
            swap(parent(x), x);
            x = parent(x);
        }
    }

    /**
     * 下沉第 x 个元素，以维护最大堆性质
     * 下沉比上浮略微复杂一点，因为上浮某个节点A，只需要 A 和父节点比较大小即可；但是下沉某个节点A，需要 A和其两个子节点比较大小，如果A不是
     * 最大的就需要调整位置，需要把较大的子节点和 A 交换。
     *
     * @param x
     */
    private void sink(int x) {
        // 如果沉到堆低，就沉不下去了
        while (left(x) <= size) {
            // 先假设左边节点较大
            int max = left(x);
            // 如果右边节点存在 比一下大小
            if (right(x) <= size && less(max, right(x))) {
                max = right(x);
            }
            // 如果结点 x 都比两个孩子大，就不必下沉了
            if (less(max, x)) {
                break;
            }
            // 否则，不符合最大堆结构，下沉 x 节点
            swap(x, max);
            x = max;
        }
    }

    /**
     * pq[i] < pq[j] ?
     *
     * @param i
     * @param j
     * @return
     */
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    /**
     * 交换数组中的两个元素
     *
     * @param i
     * @param j
     */
    private void swap(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    // left right parent 三个方法

    /**
     * 左孩子的索引
     *
     * @param root
     * @return
     */
    private int left(int root) {
        return root << 1;
    }

    /**
     * 右孩子的索引
     *
     * @param root
     * @return
     */
    private int right(int root) {
        return (root << 1) + 1;
    }

    /**
     * 父节点的索引
     */
    private int parent(int root) {
        return root >> 1;
    }


}
