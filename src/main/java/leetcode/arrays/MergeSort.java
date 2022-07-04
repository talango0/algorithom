package leetcode.arrays;

import java.util.Arrays;

/**
 * 分治法、二叉树的后序遍历
 */
public class MergeSort {
    /**
     * 归并排序的本质上是一个二叉树的后序遍历
     * 若要对 array[start, end] 排序，我们先对 nums[start, mid] 进行排序，然后再对 nums[mid+1, end] 进行排序，最后把这两个有序的子数组
     * 合并，整个数据就排序好了。
     * @param array
     * @param start
     * @param end
     */
    void MergeSort(int[] array, int start, int end){
        if(start >= end) {
            return;
        }
        int mid = start+(end-start)/2;
        MergeSort(array, start, mid);
        MergeSort(array, mid+1, end);
        Merge(array, start, mid, end);

    }
    private void Merge(int[] array, int start, int mid, int end){
        int n1 = mid-start+1;
        int n2 = end-mid;
        int[] L = new int[n1+2];
        int[] R = new int[n2+2];
        for (int i=1; i<=n1; i++) {
            L[i] = array[start+i-1];
        }
        for (int j=1; j<=n2; j++) {
            R[j] = array[mid+j];
        }

        L[n1+1] = Integer.MAX_VALUE;
        R[n2+1] = Integer.MAX_VALUE;

        int i = 1, j = 1;
        for(int k = start; k<=end; k++) {
            if(L[i] <= R[j]){
                array[k] = L[i];
                i += 1;
            }
            else {
                array[k] = R[j];
                j+=1;
            }
        }

    }

    public static void main(String [] args) {
        int [] nums = {13, 19, 9, 5, 12, 8, 7, 4, 21, 2, 5, 11};
        QuickSort.Solution solution = new QuickSort.Solution();
        solution.quickSort(nums, 0, nums.length-1);
        System.out.println(Arrays.toString(nums));


    }
}
