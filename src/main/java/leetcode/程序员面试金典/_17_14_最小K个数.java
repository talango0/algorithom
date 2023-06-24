package leetcode.程序员面试金典;
//设计一个算法，找出数组中最小的k个数。以任意顺序返回这k个数均可。
//
//示例：
//
//输入： arr = [1,3,5,7,2,4,6,8], k = 4
//输出： [1,2,3,4]
//提示：
//
//0 <= len(arr) <= 100000
//0 <= k <= min(100000, len(arr))
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/smallest-k-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2023-06-24.
 */
public class _17_14_最小K个数{
    /**
     * 排序，小顶堆，和选择排序（selection rank）,快速排序
     */
    class Solution{
        public int[] smallestK(int[] arr, int k) {
            if (k <= 0 || k > arr.length) {
                return new int[0];
            }
            Arrays.sort(arr);
            int[] smallest = new int[k];
            for (int i = 0; i < k; i++) {
                smallest[i] = arr[i];
            }
            return smallest;
        }
    }

    /**
     * 大顶堆
     */
    class Solution1{
        public int[] smallestK(int[] arr, int k) {
            if (k <= 0 || k > arr.length) {
                return new int[0];
            }
            PriorityQueue<Integer> heap = getKMaxHeap(arr, k);
            return heapToIntArray(heap);
        }

        private int[] heapToIntArray(PriorityQueue<Integer> heap) {
            int[] array = new int[heap.size()];
            while (!heap.isEmpty()) {
                array[heap.size() - 1] = heap.poll();
            }
            return array;
        }

        /**
         * 创建最小k个元素的大顶堆
         * @param arr
         * @param k
         * @return
         */
        private PriorityQueue<Integer> getKMaxHeap(int[] arr, int k) {
            PriorityQueue<Integer> heap = new PriorityQueue<>(k, (o1, o2) -> o2.compareTo(o1));
            for (int a : arr) {
                if (heap.size() < k) {
                    heap.add(a);
                }
                else if (a < heap.peek()) {
                    heap.poll();
                    heap.add(a);
                }
            }
            return heap;
        }

    }
    /**
     * 快速排序
     */
    class Solution2{
        int[] res;

        public int[] smallestK(int[] arr, int k) {
            res = new int[k];
            quick_sort(arr, 0, arr.length - 1, k);
            for (int i = 0; i < k; i++) {
                res[i] = arr[i];
            }
            return res;
        }

        void quick_sort(int[] arr, int l, int r, int k) {
            if (l >= r) return;
            int i = l - 1, j = r + 1, x = arr[(l + r) >> 1];
            while (i < j) {
                while (arr[++i] < x) ;
                while (arr[--j] > x) ;
                if (i < j) {
                    swap(arr, i, j);
                }
            }
            if (i >= k) quick_sort(arr, l, j, k);
            if (i < k) quick_sort(arr, j + 1, r, k);
        }

        void swap(int[] arr, int i, int j) {
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
    }

}
