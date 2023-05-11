package leetcode.tree;
//给你一个整数数组 nums ，按要求返回一个新数组 counts 。数组 counts 有该性质： counts[i] 的值是 nums[i] 右侧小于
//nums[i] 的元素的数量。
//
//
//
// 示例 1：
//
//
//输入：nums = [5,2,6,1]
//输出：[2,1,1,0]
//解释：
//5 的右侧有 2 个更小的元素 (2 和 1)
//2 的右侧仅有 1 个更小的元素 (1)
//6 的右侧有 1 个更小的元素 (1)
//1 的右侧有 0 个更小的元素
//
//
// 示例 2：
//
//
//输入：nums = [-1]
//输出：[0]
//
//
// 示例 3：
//
//
//输入：nums = [-1,-1]
//输出：[0,0]
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 10⁵
// -10⁴ <= nums[i] <= 10⁴
//
// Related Topics 树状数组 线段树 数组 二分查找 分治 有序集合 归并排序 👍 844 👎 0

import java.util.LinkedList;
import java.util.List;

/**
 * 字节
 */
public class _315_计算右侧小于当前元素的个数 {
    /**
     * <pre>
     *
     *           lo        i mid+1  j  hi
     *            │        │  │     │  │
     * ┌──────────▼────────▼──▼─────▼──▼───┐
     * │ temp = [ 1, 1, 3, 5, 2, 4, 6, 7 ] │
     * │ nums = [ 1, 1, 2, 3, 4,         ] │
     * └─────────────────────────▲─────────┘
     *                           │
     *                           p
     *  在对 nums[lo,...,hi] 合并的过程中，每当执行 nums[p] = temp[i] 时，就可以确定 temp[i] 这个元素后面比它小的元素个数为
     *  j-mid-1。
     *  当然，nums[lo,...,hi] 本身只是一个子数组，这个子数组之后还会被merge，其中元素的位置还是会改变，但这是其他递归节点需要考虑的事，
     *  我们只要在merge函数中叠加每次merge时记录的结果即可。
     * </pre>
     */
    static class Solution {
        private class Pair {
            /**记录数组的元素值 */
            int val;
            /**记录元素在数组中的原始索引 */
            int id;
            Pair(int val, int id){
                this.val = val;
                this.id = id;
            }
        }

        //归并排序所用到的辅助数组
        private Pair[]tmp;
        //记录后面比自己小的元素个数
        private int [] count;

        public List<Integer> countSmaller(int[] nums) {
            int n = nums.length;
            count = new int[n];
            tmp = new Pair[n];
            Pair [] arr = new Pair[n];
            //记录元素原始的索引位置，以便在 count 数组中更新结果
            for (int i = 0; i< n; i++) {
                arr[i] = new Pair(nums[i], i);
            }

            sort(arr, 0, n-1);

            List<Integer> res = new LinkedList<>();
            for (int c : count) {
                res.add(c);
            }
            return res;
        }

        private void sort(Pair[] arr, int lo, int hi) {
            if(lo == hi) {
                return;
            }
            int mid = lo + (hi-lo)/2;
            sort(arr, lo, mid);
            sort(arr, mid+1, hi);
            merge(arr, lo, mid, hi);
        }
        private void merge(Pair[] arr, int lo, int mid, int hi) {
            for(int i = lo; i <= hi; i++) {
                tmp[i] = arr[i];
            }
            int i = lo, j = mid+1;
            for(int p = lo; p<=hi; p++) {
                if (i == mid+1) {
                    arr[p] = tmp[j++];
                }
                else if (j == hi+1) {
                    arr[p] = tmp[i++];
                    //更新count数组
                    count[arr[p].id] += j - mid -1;
                }
                else if (tmp[i].val > tmp[j].val) {
                    arr[p] = tmp[j++];
                }
                else {
                    arr[p] = tmp[i++];
                    //更新count数组
                    count[arr[p].id] += j - mid -1;
                }
            }

        }
    }

    public static void main(String[] args) {
        int [] arr = new int[] {5,2,6,1};
        Solution solution = new Solution();
        List<Integer> integers = solution.countSmaller(arr);
        System.out.println(integers.toString());
    }
}
