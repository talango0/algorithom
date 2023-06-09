package leetcode.程序员面试金典;
//给定两个整数数组a和b，计算具有最小差绝对值的一对数值（每个数组中取一个值），并返回该对数值的差
//
//
//
//示例：
//
//输入：{1, 3, 15, 11, 2}, {23, 127, 235, 19, 8}
//输出：3，即数值对(11, 8)
//
//
//提示：
//
//1 <= a.length, b.length <= 100000
//-2147483648 <= a[i], b[i] <= 2147483647
//正确结果在区间 [0, 2147483647] 内
//通过次数21,833提交次数51,030
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/smallest-difference-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-06-09.
 */
public class _16_06_最小差{
    // 蛮力法, 时间复杂度花费 O(AB)
    class Solution{
        public int smallestDifference(int[] a, int[] b) {
            if (a.length == 0 || b.length == 0) {
                return -1;
            }
            long min = Integer.MAX_VALUE;
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b.length; j++) {
                    min = Math.min(min, Math.abs((long) a[i] - (long) b[j]));
                }
            }
            return (int) min;
        }
    }

    /**
     * <pre>
     *  先对数组排序，一旦数组有序，我们就可以通过数组进行迭代找出最小差值。
     * ┌──────────────────────────────┐
     * │  A : {1, 2, 11, 15}          │
     * │  B : {4, 12,19, 23, 127, 235}│
     * │       0  1  2   3   4    5   │
     * │  i = 0 1 2 2 3 3 3           │
     * │  j = 0 0 0 1 1 2 3           │
     * │ dif= 3 2 7 1 3 4 8           │
     * │ min= 3 2 2 1 1 1 1           │
     * └──────────────────────────────┘
     * 时间复杂度：O(mlogm + nlogn) ，m 为 a的长度， n为b的长度。
     * </pre>
     */
    class Solution2{
        public int smallestDifference(int[] a, int[] b) {
            Arrays.sort(a);
            Arrays.sort(b);
            int i = 0;
            int j = 0;
            long differences = Integer.MAX_VALUE;
            while (i < a.length && j < b.length) {
                if (Math.abs((long) a[i] - b[j]) < differences) {
                    differences = Math.abs((long) a[i] - b[j]);
                }
                // 移动较小值
                if (a[i] < b[j]) {
                    i++;
                }
                else {
                    j++;
                }
            }
            return (int) differences;
        }
    }

    class Solution3{
        public int smallestDifference(int[] a, int[] b) {
            Arrays.sort(a);
            Arrays.sort(b);
            int m = a.length, n = b.length;
            int i = 0;
            int j = 0;
            long differences = Math.abs((long) a[0] - b[0]);
            while (i < m && j < n) {
                differences = Math.min(differences, Math.abs((long) a[i] - b[j]));
                // 移动较小值
                if (a[i] < b[j]) {
                    while (i + 1 < m && a[i + 1] < b[j]) {
                        i++;
                    }
                    differences = Math.min(differences, Math.abs((long) a[i] - b[j]));
                    i++;
                }
                else if (a[i] > b[j]) {
                    while (j + 1 < n && a[i] > b[j + 1]) {
                        j++;
                    }
                    differences = Math.min(differences, Math.abs((long) a[i] - b[j]));
                    j++;
                }
                else {
                    return 0;
                }
            }
            return (int) differences;
        }
    }

    @Test
    public void test() {
        Solution2 solution2 = new Solution2();
        //int[] arr1 = new int[]{-2147483648, 1};
        //int[] arr2 = new int[]{2147483647, 0};
        int[] arr1 = new int[]{1, 3, 15, 11, 2};
        int[] arr2 = new int[]{23, 127, 235, 19, 8};
        System.out.println(solution2.smallestDifference(arr1, arr2));
    }
}
