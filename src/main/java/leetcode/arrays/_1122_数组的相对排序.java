package leetcode.arrays;
//给你两个数组，arr1 和 arr2，arr2 中的元素各不相同，arr2 中的每个元素都出现在 arr1 中。
//
// 对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。未在 arr2 中出现过的元素需要按照升序放在 arr1 的末
//尾。
//
//
//
// 示例 1：
//
//
//输入：arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
//输出：[2,2,2,1,4,3,3,9,6,7,19]
//
//
// 示例 2:
//
//
//输入：arr1 = [28,6,22,8,44,17], arr2 = [22,28,8,6]
//输出：[22,28,8,6,17,44]
//
//
//
//
// 提示：
//
//
// 1 <= arr1.length, arr2.length <= 1000
// 0 <= arr1[i], arr2[i] <= 1000
// arr2 中的元素 arr2[i] 各不相同
// arr2 中的每个元素 arr2[i] 都出现在 arr1 中
//
//
// Related Topics 数组 哈希表 计数排序 排序 👍 242 👎 0

/**
 * @author mayanwei
 * @date 2022-11-03.
 */
public class _1122_数组的相对排序{
    class Solution{
        /**
         * 计数排序
         * <p>
         * 由于题目提示 0 <= arr1[i], arr2[i] <= 1000,想要对
         * arr1中的元素按照 arr2中项的相对顺序进行排序，
         * 未在 arr2 中出现过的元素按照升序放在 arr1 的末尾。
         */
        int[] relativeSortArray(int[] arr1, int[] arr2) {

            // 定义长度为 1001的数组 hash，用于模拟哈希表
            int[] hash = new int[1001];
            for (int n : arr1) {
                // 键为 arr1 中的元素，值为arr1中元素出现的次数
                hash[n]++;
            }
            // 定义数组索引 index并设置为 0 ，用于重置数组arr1中的元素值
            int index = 0;
            // 遍历数组arr2，只要arr2中的元素在数组 hash 中存在，则将其
            // 赋值给 arr1， 则对改元素在hash中出现的频次减一
            for (int n : arr2) {
                while (hash[n]-- > 0) {
                    arr1[index++] = n;
                }
            }
            // 针对不是 arr2中的元素，遍历整个数组 hash，只要起元素出现次数在一次
            // 及以上，将其赋值给 arr1，并将改元素在 hash 中出现的频次减一
            for (int n = 0; n < hash.length; ++n) {
                while (hash[n]-- > 0) {
                    arr1[index++] = n;
                }
            }

            return arr1;
        }

    }
}
