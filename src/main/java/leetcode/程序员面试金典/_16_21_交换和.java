package leetcode.程序员面试金典;
//给定两个整数数组，请交换一对数值（每个数组中取一个数值），使得两个数组所有元素的和相等。
//
//返回一个数组，第一个元素是第一个数组中要交换的元素，第二个元素是第二个数组中要交换的元素。若有多个答案，返回任意一个均可。
// 若无满足条件的数值，返回空数组。
//
//示例:
//
//输入: array1 = [4, 1, 2, 1, 1, 2], array2 = [3, 6, 3, 3]
//输出: [1, 3]
//示例:
//
//输入: array1 = [1, 2, 3], array2 = [4, 5, 6]
//输出: []
//提示：
//
//1 <= array1.length, array2.length <= 100000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/sum-swap-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author mayanwei
 * @date 2023-06-14.
 */
public class _16_21_交换和{

    /**
     * <pre>
     * ┌───────────────────────────┐
     * │ sumA - a + b = sumB -b + a│
     * │ (a-b)*2 = sumA + sumB     │
     * │  a-b = (sumA + sumB)/2    │
     * └───────────────────────────┘
     * </pre>
     */
    class Solution{
        // 蛮力法 时间复杂度O(AB)
        //public int[] findSwapValues(int[] array1, int[] array2) {
        //    int sum1 = Arrays.stream(array1).sum();
        //    int sum2 = Arrays.stream(array2).sum();
        //    for (int one : array1) {
        //        for (int two : array2) {
        //            int newSum1 = sum1 - one + two;
        //            int newSum2 = sum2 + one - two;
        //            if (newSum1 == newSum2) {
        //                return new int[]{one, two};
        //            }
        //        }
        //    }
        //    return null;
        //}
        public int[] findSwapValues(int[] array1, int[] array2) {
            Integer target = getTarget(array1, array2);
            if (target == null) {
                return new int[0];
            }
            for (int one : array1) {
                for (int two : array2) {

                    if (one - two == target) {
                        return new int[]{one, two};
                    }
                }
            }
            return new int[0];
        }

        private Integer getTarget(int[] array1, int[] array2) {
            int sum1 = Arrays.stream(array1).sum();
            int sum2 = Arrays.stream(array2).sum();

            if ((sum1 - sum2) % 2 != 0) {
                return null;
            }
            return (sum1 - sum2) / 2;
        }
    }

    /**
     * 并不需要编写 one-two == target这样的代码，而是要使用 two == one-target 这样的代码。
     * 如何才能快速在数组B中找到等于 one-target 的值呢？
     * 可以使用散列表快速完成该过程。只需要将数组B中的所有元素加入到散列表中即可。然后，对数组A
     * 进行迭代并在B中查找合适的元素。
     * 时间复杂度 O(A+B)
     */
    class Solution1{
        class Solution{
            public int[] findSwapValues(int[] array1, int[] array2) {
                Integer target = getTarget(array1, array2);
                if (target == null) {
                    return new int[0];
                }
                return findDifference(array1, array2, target);
            }

            /**
             * 查找一对有特定差值的数
             */
            private int[] findDifference(int[] array1, int[] array2, Integer target) {
                HashSet<Integer> contents2 = getContent(array2);
                for (int one : array1) {
                    int two = one - target;
                    if (contents2.contains(two)) {
                        return new int[]{one, two};
                    }
                }
                return new int[0];
            }

            /**
             * 将数组内容加入到散列表中
             */
            private HashSet<Integer> getContent(int[] array2) {
                HashSet<Integer> set = new HashSet<>();
                for (int item : array2) {
                    set.add(item);
                }
                return set;
            }

            private Integer getTarget(int[] array1, int[] array2) {
                int sum1 = Arrays.stream(array1).sum();
                int sum2 = Arrays.stream(array2).sum();

                if ((sum1 - sum2) % 2 != 0) {
                    return null;
                }
                return (sum1 - sum2) / 2;
            }
        }
    }

    /**
     * 另一种方法，如果数字是有序的，我们可以通过对其进行迭代以找到合适的一对数组。这种方法会占用较少空间。
     * 如果array1和array2无序，则需要先排队，耗时O(nlogn),因此该方法的最坏情况下的负载度为 0(AlogA+BlogB)
     */
    class Solution2 {
        public int[] findSwapValues(int[] array1, int[] array2) {
            Integer target = getTarget(array1, array2);
            if (target == null) {
                return new int[0];
            }
            return findDifference(array1, array2, target);
        }

        private int[] findDifference(int[] array1, int[] array2, Integer target) {
            int a = 0;
            int b = 0;
            while (a < array1.length && b < array2.length) {
                int difference = array1[a] - array2[b];
                // 将difference 和 target 比较，如果 difference太小，则将 a 移至较大的数；如果difference太大
                // 则将 b 移至较大的数，如果相等则返回次对数
                if (difference == target) {
                    return new int[]{array1[a], array1[b]};
                }
                else if (difference < target) {
                    a++;
                } else {
                    b++;
                }
            }
            return new int[0];
        }

        private Integer getTarget(int[] array1, int[] array2) {
            int sum1 = Arrays.stream(array1).sum();
            int sum2 = Arrays.stream(array2).sum();

            if ((sum1 - sum2) % 2 != 0) {
                return null;
            }
            return (sum1 - sum2) / 2;
        }
    }

}
