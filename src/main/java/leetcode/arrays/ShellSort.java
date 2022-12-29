package leetcode.arrays;

import org.junit.Assert;

import java.util.Arrays;

public class ShellSort {
    /**
     * 思路:先将整个待排序的记录序列分割成若干子序列分别进行直接插入排序，待整个序列中的记录“基本有序”时，再对全体记录进行一次直接插入排序。
     * 选择一个增量序列 t1,t2,...,tk,其中 ti> tj, tk = 1;
     * 按增量序列个数k，对序列进行k趟排序
     * 每趟排序，根据对应的增量ti，将待排序分割成若干长度为 m 的子序列，分别对个子表直接插入排序，仅增量因子为1时，整个序列作为一个表俩处理，表长度
     * 即为整个序列的长度。
     *
     * @param arr
     */
    public void sort(int [] arr) {
        int dk = arr.length / 2;
        while (dk >= 1) {
            shellInsertSort(arr, dk);
            dk /= 2;
        }
    }

    private void shellInsertSort(int[] arr, int dk) {
        //类似插入排序，还是插入排序增量是1，这里增量是dk，把1换成dk就可以了
        for (int i = dk; i < arr.length; i++) {
            if (arr[i] < arr[i - dk]) {
                int j;
                // x 为待插入的元素
                int x = arr[i]; 
                arr[i] = arr[i - dk];
                for (j = i-dk; j >= 0 && x < arr[j]; j = j-dk) {
                    //通过循环，逐个后移一位找到要插入的位置
                    arr[j + dk] = arr[j];
                }
                arr[j+dk] = x;
            }
        }
    }

    public static void main(String[] args) {
        int [] arr = new int[]{3,1,2,5,4};
        ShellSort shellSort = new ShellSort();
        shellSort.sort(arr);
        Assert.assertArrayEquals(new int[]{1,2,3,4,5}, arr);
    }
}
