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

import netscape.security.UserTarget;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

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
         *
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


    /**
     * 选择排序算法（如果元素各不相同）
     * 选择排序在O(n)时间内找到第 i 个最小的元素。该算法的基本流程如下：
     * （1）在数组中随机挑选一个元素，将它用作 pivot(基准)。以 pivot 为基准划分所有元素，记录 pivot 左边的元素个数。
     * （2）如果左边刚好有i个元素，则直接返回左边最大的元素。
     * （3）如果哦左边元素个数大于 i ，则继续在数组左边部分重复执行该算法。
     * （4）如果做百年元素个数小于 i ，则在数组右边部分重复执行该算法，但只查找排 i-leftSize 的按个元素。
     * 一旦找到了第 i 个最小的元素，就能得知小于此值的元素将会在该元素的左边（因为你已经对数组进行了响应的分割）。现在只需要返回
     * 前 i 个元素。
     */
    class Solution3{
        public int[] smallestK(int[] arr, int k) {
            if (k < 0 || k > arr.length) {
                return new int[0];
            }
            int threshold = rank(arr, k - 1);
            int[] smallest = new int[k];
            int count = 0;
            for (int a : arr) {
                if (a <= threshold) {
                    smallest[count] = a;
                    count++;
                }
            }
            return smallest;
        }

        /**
         * 通过rank 获取元素
         *
         * @param array
         * @param rank
         * @return
         */
        private int rank(int[] array, int rank) {
            return rank(array, 0, array.length - 1, rank);
        }

        /**
         * 通过 rank 获取 left 和 right 间的元素
         *
         * @param array
         * @param left
         * @param right
         * @param rank
         * @return
         */
        private int rank(int[] array, int left, int right, int rank) {
            int pivot = array[randomIntRange(left, right)];
            int leftEnd = partition(array, left, right, pivot);
            int leftSize = leftEnd - left + 1;
            if (rank == leftSize - 1) {
                return max(array, left, leftEnd);
            }
            else if (rank < leftSize) {
                return rank(array, leftEnd + 1, right, rank - leftSize);
            }
            else {
                return rank(array, leftEnd + 1, right, rank - leftSize);
            }
        }

        private int max(int[] array, int left, int leftEnd) {
            int max = Integer.MIN_VALUE;
            for (int i = left; i <= leftEnd; i++) {
                max = Math.max(array[i], max);
            }
            return max;
        }

        /**
         * 以 pivot 为中心分组，所有小于等于 pivot 的元素均出现在大于 pivot 的元素之前。
         *
         * @param array
         * @param left
         * @param right
         * @param pivot
         * @return
         */
        private int partition(int[] array, int left, int right, int pivot) {
            while (left <= right) {
                if (array[left] > pivot) {
                    // left 大于 pivot，将其交换至右侧
                    swap(array, left, right);
                    right--;
                }
                else if (array[right] <= pivot) {
                    // right 小于pivot，将其交换至左侧
                    swap(array, left, right);
                    left++;
                }
                else {
                    // left 和 right 位置正确，扩展范围
                    left++;
                    right--;
                }
            }
            return left - 1;
        }

        void swap(int[] arr, int i, int j) {
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }

        /**
         * 获取指定范围内的随机整数
         *
         * @param min
         * @param max
         * @return
         */
        private int randomIntRange(int min, int max) {
            Random random = new Random();
            return random.nextInt(max + 1 - min) + min;
        }
    }


    /**
     * 选择排序算法（如果元素不是唯一的）
     * 先复制较小的元素，然后在数组􏲍部􏲗充相等的元素。
     */
    class Solution4{
        class PartitionResult{
            int leftSize, middleSize;

            public PartitionResult(int leftSize, int middleSize) {
                this.leftSize = leftSize;
                this.middleSize = middleSize;
            }
        }

        public int[] smallestK(int[] arr, int k) {
            if (k < 0 || k > arr.length) {
                return new int[0];
            }
            int threshold = rank(arr, k - 1);
            int[] smallest = new int[k];
            int count = 0;
            for (int a : arr) {
                if (a < threshold) {
                    smallest[count] = a;
                    count++;
                }
            }
            while (count < k) {
                smallest[count] = threshold;
                count++;
            }
            return smallest;
        }

        /**
         * 通过rank 获取元素
         *
         * @param array
         * @param rank
         * @return
         */
        private int rank(int[] array, int rank) {
            return rank(array, 0, array.length - 1, rank);
        }

        /**
         * 在 start  和 end 之前的子数组中查找排序为 k 的值
         *
         * @return
         */
        private int rank(int[] array, int k, int start, int end) {
            int pivot = array[randomIntRange(start, end)];
            PartitionResult partition = partition(array, start, end, pivot);
            int leftSize = partition.leftSize;
            int middleSize = partition.middleSize;
            if (k < leftSize) {
                return rank(array, k, start, start + leftSize - 1);
            }
            else if (k < leftSize + middleSize) { // 排序 k 的值在中间
                return pivot;//中间的值都为 pivot
            }
            else {
                return rank(array, k - leftSize - middleSize, start + leftSize + middleSize, end);
            }
        }


        /**
         * 按照小于pivot、等于pivot、大于pivot的顺序对数组进行􏰆组
         *
         * @return
         */
        private PartitionResult partition(int[] array, int start, int end, int pivot) {
            int left = start;
            int right = end;
            int middle = start;
            while (middle <= right) {
                if (array[middle] < pivot) {
                    swap(array, middle, left);
                    middle++;
                    left++;
                }
                else if (array[middle] > pivot) {
                    swap(array, middle, right);
                    right--;
                }
                else if (array[middle] == pivot) {
                    middle++;
                }
            }
            return new PartitionResult(left - start, right - left + 1);
        }

        void swap(int[] arr, int i, int j) {
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }

        /**
         * 获取指定范围内的随机整数
         *
         * @param min
         * @param max
         * @return
         */
        private int randomIntRange(int min, int max) {
            Random random = new Random();
            return random.nextInt(max + 1 - min) + min;
        }
    }

}
