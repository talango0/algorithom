package inductiontoalgorithm.segment_tree;

/**
 * @author mayanwei
 * @date 2022-09-17.
 */
public interface ISegmentTree{
    /**
     * arr 在区间 [L,R] 每个元素增加 v
     *
     * @param L
     * @param R
     * @param V
     */
    void add(int L, int R, int V);

    /**
     * 在区间[L,R] 的每一个元素都设置为 v
     *
     * @param L
     * @param R
     * @param V
     */
    void update(int L, int R, int V);


    /**
     * 区间 [L, R] 的区间和等于多少
     *
     * @param L
     * @param R
     * @return
     */
    long getSum(int L, int R);


}
