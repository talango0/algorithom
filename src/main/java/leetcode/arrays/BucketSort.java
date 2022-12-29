package leetcode.arrays;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序算法
 * <p>
 * 基本思想：
 * 把数组arr划分为 n 个大小相同子区间（桶），每个子区间各自排序，最后合并。计数排序是桶排序的一种特殊情况，可以把计数排序看成每个桶里只有一个元素
 * 的情况。
 * <p>
 * 1. 找出待排序数组中的最大值max、最小值min
 * <p>
 * 2. 我们使用动态数组ArrayList作为桶，桶里放的元素也用ArrayList存储。桶的数量为 (max-min)/arr.length + 1
 * <p>
 * 3. 遍历arr，计算每个元素arr[i]放的桶
 * <p>
 * 4. 每个桶各自排序
 */
public class BucketSort {
    public void buckSort(int[] arr) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }

        // 创建桶
        int bucketNum = (max - min) / arr.length + 1;
        List<List<Integer>> bucketArr = new ArrayList<>(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            bucketArr.add(new ArrayList<>());
        }

        // 将每个元素放入桶
        for (int i = 0; i < arr.length; i++) {
            int num = (arr[i] - min) / arr.length;
            bucketArr.get(num).add(arr[i]);
        }

        // 对每个桶进行排序
        for (int i = 0; i < bucketArr.size(); i++) {
            Collections.sort(bucketArr.get(i));
        }
        int i = 0;
        for (List<Integer> bucket : bucketArr) {
            if (!bucket.isEmpty()) {
                for (Integer item : bucket) {
                    arr[i++] = item;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 5};
        BucketSort bucketSort = new BucketSort();
        bucketSort.buckSort(arr);
        Assert.assertArrayEquals(new int[]{1, 2, 3, 5}, arr);
    }
}
