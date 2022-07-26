package leetcode.design.monotonous.queue;

/**
 * 单调队列的通用实现，可以高效维护最大值和最小值
 *
 * @author mayanwei
 * @date 2022-07-23.
 */
public interface MonotonicQueue<E extends Comparable<E>>{

    /**
     * 标准队列 API，向队尾加入元素
     *
     * @param elem
     */
    void push(E elem);

    /**
     * 标准队列 API，从队头弹出元素，符合先进先出的顺序
     *
     * @return
     */
    E pop();

    /***
     * 标准队列 API，返回队列中的元素个数
     * @return
     */
    int size();

    /**
     * 单调队列特有 API，O(1) 时间计算队列中元素的最大值
     *
     * @return
     */
    E max();

    /**
     * 单调队列特有 API，O(1) 时间计算队列中元素的最小值
     *
     * @return
     */
    E min();


}
