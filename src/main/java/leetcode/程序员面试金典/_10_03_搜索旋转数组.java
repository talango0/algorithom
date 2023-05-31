package leetcode.程序员面试金典;
//搜索旋转数组。给定一个排序后的数组，包含n个整数，但这个数组已被旋转过很多次了，次数不详。
// 请编写代码找出数组中的某个元素，假设数组元素原先是按升序排列的。
// 若有多个相同元素，返回索引值最小的一个。
//
//示例1:
//
// 输入: arr = [15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14], target = 5
// 输出: 8（元素5在该数组中的索引）
//示例2:
//
// 输入：arr = [15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14], target = 11
// 输出：-1 （没有找到）
//提示:
//
//arr 长度范围在[1, 1000000]之间
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/search-rotate-array-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 * @date 2023-05-30.
 */
public class _10_03_搜索旋转数组{
    /**
     * <pre>
     * ┌─────────┬───────────┬───────────┐
     * │    20   │ 50      40│           │
     * │  15     │       30  │        4  │
     * │10       │     20    │       3   │
     * │        5│   5       │ 2 2 2   2 │
     * │      0  │           │           │
     * └─────────┴───────────┴───────────┘
     *
     * </pre>
     */


    class Solution{

        public int search(int[] arr, int target) {
            int n = arr.length;
            while (n > 1 && arr[n - 1] == arr[0]) n--;
            int l = 0, r = n - 1;
            while (l < r) {
                int mid = (l + r) / 2;
                if (arr[mid] <= arr[r]) r = mid;
                else
                    l = mid + 1;
            }
            r = l + n - 1;
            if (target < arr[l]) return -1;
            while (l < r) {
                int mid = (l + r) / 2;
                if (arr[mid % n] >= target) r = mid;
                else l = mid + 1;
            }
            if (arr[l % n] == target) return l % n;
            return -1;
        }
    }

    class Solution1{
        public int search(int[] arr, int target) {
            return search(arr, 0, arr.length - 1, target);
        }

        int search(int a[], int left, int right, int x) {
            int mid = (left + right) / 2;
            if (x == a[mid]) {
                return mid;
            }
            if (right < left) {
                return -1;
            }
            // 左半边或有半边必有一边按正常顺序排列，找出是哪一半边，然后
            // 利用按正常顺序排列的半边，确定该搜索哪一边。
            if (a[left] < a[mid]) {// 左半边为正常排序
                if (x >= a[left] && x < a[mid]) {
                    // 搜索左半边
                    return search(a, left, mid - 1, x);
                }
                else {
                    // 搜索右半边
                    return search(a, mid + 1, right, x);
                }
            }
            else if (a[mid] < a[left]) {// 右半边为正常排序
                if (x > a[mid] && x <= a[right]) {
                    // 搜索右半边
                    return search(a, mid + 1, right, x);
                }
                else {
                    // 搜索左半边
                    return search(a, left, mid - 1, x);
                }

            }
            else if (a[left] == a[mid]) { // 左半边都是重复元素
                // 若右半边元素不同，则搜索那一边
                if (a[mid] != a[right]) {
                    return search(a, mid + 1, right, x); // 搜索右半边
                }
                // 否则两边都要搜索
                else {
                    int result = search(a, left, mid - 1, x);// 搜索左半边
                    if (result == -1) {
                        return search(a, mid + 1, right, x);// 搜索后半边
                    }
                    else {
                        return result;
                    }
                }

            }
            return -1;
        }
    }

}
