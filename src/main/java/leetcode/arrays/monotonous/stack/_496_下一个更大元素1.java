package leetcode.arrays.monotonous.stack;
//nums1 中数字 x 的 下一个更大元素 是指 x 在 nums2 中对应位置 右侧 的 第一个 比 x 大的元素。
//
// 给你两个 没有重复元素 的数组 nums1 和 nums2 ，下标从 0 开始计数，其中nums1 是 nums2 的子集。
//
// 对于每个 0 <= i < nums1.length ，找出满足 nums1[i] == nums2[j] 的下标 j ，并且在 nums2 确定
//nums2[j] 的 下一个更大元素 。如果不存在下一个更大元素，那么本次查询的答案是 -1 。
//
// 返回一个长度为 nums1.length 的数组 ans 作为答案，满足 ans[i] 是如上所述的 下一个更大元素 。
//
//
//
// 示例 1：
//
//
//输入：nums1 = [4,1,2], nums2 = [1,3,4,2].
//输出：[-1,3,-1]
//解释：nums1 中每个值的下一个更大元素如下所述：
//- 4 ，用加粗斜体标识，nums2 = [1,3,4,2]。不存在下一个更大元素，所以答案是 -1 。
//- 1 ，用加粗斜体标识，nums2 = [1,3,4,2]。下一个更大元素是 3 。
//- 2 ，用加粗斜体标识，nums2 = [1,3,4,2]。不存在下一个更大元素，所以答案是 -1 。
//
// 示例 2：
//
//
//输入：nums1 = [2,4], nums2 = [1,2,3,4].
//输出：[3,-1]
//解释：nums1 中每个值的下一个更大元素如下所述：
//- 2 ，用加粗斜体标识，nums2 = [1,2,3,4]。下一个更大元素是 3 。
//- 4 ，用加粗斜体标识，nums2 = [1,2,3,4]。不存在下一个更大元素，所以答案是 -1 。
//
//
//
//
// 提示：
//
//
// 1 <= nums1.length <= nums2.length <= 1000
// 0 <= nums1[i], nums2[i] <= 10⁴
// nums1和nums2中所有整数 互不相同
// nums1 中的所有整数同样出现在 nums2 中
//
//
//
//
// 进阶：你可以设计一个时间复杂度为 O(nums1.length + nums2.length) 的解决方案吗？
// Related Topics 栈 数组 哈希表 单调栈 👍 761 👎 0


/**
 * @author mayanwei
 * @date 2022-07-03.
 */
public class _496_下一个更大元素1{
    class Solution {
        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            int m = nums1.length, n = nums2.length;
            int[] res = new int[m];
            for (int i = 0; i < m; ++i) {
                int j = 0;
                while (j < n && nums2[j] != nums1[i]) {
                    ++j;
                }
                int k = j + 1;
                while (k < n && nums2[k] < nums2[j]) {
                    ++k;
                }
                res[i] = k < n ? nums2[k] : -1;
            }
            return res;
        }


//     public int[] nextGreaterElement(int[] nums1, int[] nums2) {
//         Deque<Integer> stk = null;
//         Map<Integer, Integer> indexValueMap = new HashMap<Integer, Integer>();
//         for (int i = 0; i< nums2.length; i++) {
//             indexValueMap.put(nums2[i], i);
//         }
//         for (int i = nums1.length-1; i >= 0; i--) {
//             int cur = nums1[i];
//             int min_j = indexValueMap.get(cur);
//             stk = new LinkedList<Integer>();
//             for (int j = nums2.length-1; j>=min_j; j--) {
//                 while (!stk.isEmpty() && stk.peek()<= cur) {
//                     stk.pop();
//                 }
//                 nums1[i] = stk.isEmpty() ? -1 : stk.peek();
//                 stk.addFirst(nums2[j]);
//             }
//         }
//         return nums1;
//     }
    }
}
