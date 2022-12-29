package leetcode.arrays;

import org.junit.Assert;

/**
 * 基数排序
 * <p>
 * 将所有待比较数值（正整数）统一为同样的数位长度，数位较短的数前面补零。然后，从最低位开始，依次进行一次排序。这样从最低位排序一直到最高位排序
 * 完成以后，数列就变成了一个有序序列。
 */
public class RadixSort {
    public void radixSort(int[] arr) {
        if (null == arr || 0 == arr.length) {
            return;
        }
        // 首先确定排序的趟数;
        int k = maxBit(arr);
        int radix = 1;
        for (int i = 0; i < k; i++) {
            countingSort(arr, radix);
            radix *= 10;
        }
    }

    private void countingSort(int[] arr, int radix) {
        int[] counts = new int[10];
        int len = arr.length;
        int[] buffer = new int[len];

        for (int i = 0; i < len; i++) {
            counts[(arr[i] / radix) % 10]++;
        }
        for (int i = 1; i < counts.length; i++) {
            counts[i] = counts[i] + counts[i - 1];
        }

        for (int i = len - 1; i >= 0; i--) {
            buffer[counts[(arr[i] / radix) % 10] - 1] = arr[i];
            counts[(arr[i] / radix) % 10] = counts[(arr[i] / radix) % 10] - 1;
        }

        for (int i = 0; i < len; i++) {
            arr[i] = buffer[i];
        }
    }


    private int maxBit(int[] arr) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        // 判断位数;
        int d = 1;
        while ((max = max / 10) > 0) {
            d++;
        }
        return d;
    }

    public static void main(String[] args) {
        int[] a = new int[]{9,10,7,8,1,2,3,4};
        RadixSort radixSort = new RadixSort();
        radixSort.radixSort(a);
        Assert.assertArrayEquals(new int[]{1,2,3,4,7,8,9,10}, a);
    }
}
