package leetcode.arrays;

import java.util.Arrays;

/**
 * 堆排序
 *
 * @author mayanwei
 */
public class HeapSort {
    public static int left(int i){
        return i<<1;
    }
    public static int right(int i){
        return (i<<1)+1;
    }
    public static int parent(int i){
        return i>>1;
    }
    public static void exchage(int [] args, int i, int j){
        int tmp = args[i];
        args[i] = args[j];
        args[j] = tmp;
    }

    static class Solution{
        /**
         * 最大堆：
         * maxHeapify，时间复杂度O(lgn),它是维护最大堆性质的关键。
         * buildMaxHeap, 具有线性复杂度，功能是从无序的数据中构造一个最大堆。
         * heapSort, 其时间复杂度为O(lgn)，功能是一个数组进行原址排序。
         */
        /**
         * 维护一个最大堆
         * @param args
         * @param i
         */
        public void maxHeapify(int [] args, int i, int heapSize){
            int l = left(i);
            int r = right(i);
            int largest;
            if(l <= heapSize && args[l-1] > args[i-1]){
                largest = l;
            }else {
                largest = i;
            }

            if(r <= heapSize && args[r-1] > args[largest-1]){
                largest = r;
            }
            if(largest != i){
                exchage(args, i-1, largest-1);
                maxHeapify(args, largest,heapSize);
            }
        }

        /**
         * 构建最大堆
         * @param A
         */
        public void buildMaxHeap(int [] A, int heapSize){
            for(int i = (int) Math.floor(heapSize/2); i>=1; i--){
                maxHeapify(A, i, heapSize);
            }
        }

        /**
         * 堆排序
         * @param A
         */
        public void heapSort(int [] A, int heapSize){
            buildMaxHeap(A,heapSize);
            for(int i=heapSize; i >= 2; i--){
                exchage(A,0, i-1);
                heapSize--;
                maxHeapify(A, 1, heapSize);
            }
        }
    }

    public static void main(String[] args) {
//        int [] nums = {4, 16, 10, 14, 7, 9, 3, 2, 8, 1};
//        System.out.println(Arrays.toString(nums));
        Solution solution = new Solution();
//        solution.maxHeapify(nums,1);
//        System.out.println(Arrays.toString(nums));

        int [] nums = { 2,3,1,6,5,4};
//        solution.buildMaxHeap(nums,nums.length);

        solution.heapSort(nums, nums.length);
        System.out.println(Arrays.toString(nums));

    }
}
