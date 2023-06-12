package leetcode.程序员面试金典;
//给定一个整数数组，编写一个函数，找出索引m和n，只要将索引区间[m,n]的元素排好序，整个数组就是有序的。
// 注意：n-m尽量最小，也就是说，找出符合条件的最短序列。函数返回值为[m,n]，若不存在这样的m和n（例如整个数组是有序的），请返回[-1,-1]。
//
//示例：
//
//输入： [1,2,4,7,10,11,7,12,6,7,16,18,19]
//输出： [3,9]
//提示：
//
//0 <= len(array) <= 1000000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/sub-sort-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-06-11.
 */
public class _16_16_部分排序{
    /**
     * <pre>
     * ┌────────────────────────────────────┐
     * │ [1,2,4,7,10,11,8,12,6,7,16,18,19]  │
     * │                                    │
     * │ left: 1, 2, 4 ,7, 10, 11           │
     * │  mid: 8,12                         │
     * │right: 6, 7,16,18,19                │
     * │                                    │
     * │ min = min(item in middle and right)│
     * │ max = max(item in middle and left) │
     * │leftIndex: array[leftIndex] < min   │
     * │item: 11 10                         │
     * │index: 5  4                         │
     * │                                    │
     * │rightIndex:array[rightIndex] > max  │
     * │item: 6 7 16                        │
     * │index:8 9 10                        │
     * └────────────────────────────────────┘
     * </pre>
     */
    class Solution{
        public int[] subSort(int[] array) {
            if (array == null || array.length == 0) {
                return new int[]{-1, -1};
            }
            // 找到左边的子序列
            int endLeft = findEndOfLeftSubSequence(array);
            if (endLeft >= array.length - 1) { // 已排序
                return new int[]{-1, -1};
            }
            // 找到右边的子序列
            int startRight = findStartOfRightSubSequence(array);

            // 获取最大值和最小值
            int maxIndex = endLeft; // 左边最大值
            int minIndex = startRight;// 右边最小值
            for (int i = endLeft + 1; i < startRight; i++) {
                if (array[i] < array[minIndex]) {
                    minIndex = i;
                }
                if (array[i] > array[maxIndex]) {
                    maxIndex = i;
                }
            }

            // 向左移动直至小于 array[minIndex]
            int leftIndex = shrinkLeft(array, minIndex, endLeft);
            // 向右移动直至大于 array[maxIndex]
            int rightIndex = shrinkRight(array, maxIndex, startRight);

            return new int[]{leftIndex, rightIndex};
        }

        private int shrinkRight(int[] array, int maxIndex, int startRight) {
            int comp = array[maxIndex];
            for (int i = startRight; i < array.length; i++) {
                if (array[i] >= comp) {
                    return i - 1;
                }
            }
            return array.length - 1;
        }

        private int shrinkLeft(int[] array, int minIndex, int endLeft) {
            int comp = array[minIndex];
            for (int i = endLeft; i >= 0; i--) {
                if (array[i] <= comp) {
                    return i + 1;
                }
            }
            return 0;
        }

        private int findStartOfRightSubSequence(int[] array) {
            for (int i = array.length - 2; i >= 0; i--) {
                if (array[i] > array[i + 1]) {
                    return i + 1;
                }
            }
            return 0;
        }

        private int findEndOfLeftSubSequence(int[] array) {
            for (int i = 1; i < array.length; i++) {
                if (array[i] < array[i - 1]) {
                    return i - 1;
                }
            }
            return array.length - 1;
        }
    }


    class Solution1{
        public int[] subSort(int[] array) {
            if (array == null || array.length == 0) {
                return new int[]{-1, -1};
            }

            int l = -1, r = -1, min = array[array.length - 1], max = array[0];
            for (int i = 0; i < array.length; i++) {
                if (array[i] >= max) {
                    max = array[i];
                }
                else {
                    r = i;
                }
            }

            if (r == -1) {
                return new int[]{-1, -1};
            }

            for (int i = array.length - 1; i >= 0; i--) {
                if (array[i] <= min) {
                    min = array[i];
                }
                else {
                    l = i;
                }
            }

            return new int[]{l, r};
        }
    }


    @Test
    public void test() {

        int[] arr = new int[]{1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19}; // [3, 9]
        int[] arr1 = new int[]{1, 3, 9, 7, 5}; //[2,4]
        Solution solution = new Solution();
        Assertions.assertArrayEquals(solution.subSort(Arrays.copyOf(arr1, arr1.length)), new int[]{2, 4});
        Solution1 solution1 = new Solution1();
        Assertions.assertArrayEquals(solution1.subSort(Arrays.copyOf(arr, arr.length)), new int[]{3, 9});
    }

}
