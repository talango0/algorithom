package leetcode.arrays;

import java.util.LinkedList;
import java.util.List;
//给你一个整数数组 arr ，请使用 煎饼翻转 完成对数组的排序。
//
// 一次煎饼翻转的执行过程如下：
//
//
// 选择一个整数 k ，1 <= k <= arr.length
// 反转子数组 arr[0...k-1]（下标从 0 开始）
//
//
// 例如，arr = [3,2,1,4] ，选择 k = 3 进行一次煎饼翻转，反转子数组 [3,2,1] ，得到 arr = [1,2,3,4] 。
//
// 以数组形式返回能使 arr 有序的煎饼翻转操作所对应的 k 值序列。任何将数组排序且翻转次数在 10 * arr.length 范围内的有效答案都将被判断
//为正确。
//
//
//
// 示例 1：
//输入：[3,2,4,1]
//输出：[4,2,4,3]
//解释：
//我们执行 4 次煎饼翻转，k 值分别为 4，2，4，和 3。
//初始状态 arr = [3, 2, 4, 1]
//第一次翻转后（k = 4）：arr = [1, 4, 2, 3]
//第二次翻转后（k = 2）：arr = [4, 1, 2, 3]
//第三次翻转后（k = 4）：arr = [3, 2, 1, 4]
//第四次翻转后（k = 3）：arr = [1, 2, 3, 4]，此时已完成排序。
//
//
// 示例 2：
//输入：[1,2,3]
//输出：[]
//解释：
//输入已经排序，因此不需要翻转任何内容。
//请注意，其他可能的答案，如 [3，3] ，也将被判断为正确。
//
//
//
//
// 提示：
//
//
// 1 <= arr.length <= 100
// 1 <= arr[i] <= arr.length
// arr 中的所有整数互不相同（即，arr 是从 1 到 arr.length 整数的一个排列）
//
//
// Related Topics 贪心 数组 双指针 排序 👍 272 👎 0
/**
 * @author mayanwei
 * @date 2022-08-07.
 */
public class _969_煎饼排序{
    class Solution {
        // 1、找到 n 个大饼中最大的那个
        // 2、把这个这个最大的饼移动到最底下
        // 3、递归调用 pancakeSort(arr, n-1)
        // base case n == 1时，排序1个饼时不需要翻转
        // 其实很简单，比如第 3 块饼是最大的，我们想把它换到最后，也就是换到第 n 块。可以这样操作：
        // 1、用锅铲将前 3 块饼翻转一下，这样最大的饼就翻到了最上面。
        // 2、用锅铲将前 n 块饼全部翻转，这样最大的饼就翻到了第 n 块，也就是最后一块。

        // 记录反转操作序列
        LinkedList<Integer> res = new LinkedList<>();

        public List<Integer> pancakeSort(int[] arr) {
            sort(arr, arr.length);
            return res;
        }
        void sort(int [] cakes, int n) {
            // base case
            if ( n== 1) {
                return;
            }
            // 寻找最大饼的索引
            int maxCake = 0;
            int maxCakeIndex = 0;
            for (int i = 0; i < n; i++) {
                if (cakes[i] > maxCake) {
                    maxCake = cakes[i];
                    maxCakeIndex = i;
                }
            }
            // 第一次翻转，将最大饼翻到最上面
            reverse(cakes, 0, maxCakeIndex);
            res.add(maxCakeIndex + 1);
            // 第二次翻转，将最大的翻到最下面
            reverse(cakes, 0, n-1);
            res.add(n);
            // 递归掉哟过
            sort(cakes, n-1);
        }

        void reverse(int[] arr, int i, int j) {
            while (i < j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++; j--;
            }
        }
    }
}
