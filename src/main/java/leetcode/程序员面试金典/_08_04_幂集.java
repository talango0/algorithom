package leetcode.程序员面试金典;

//幂集。编写一种方法，返回某集合的所有子集。集合中不包含重复的元素。
//
//说明：解集不能包含重复的子集。
//
//示例:
//
// 输入： nums = [1,2,3]
// 输出：
//[
// [3],
// [1],
// [2],
// [1,2,3],
// [1,3],
// [2,3],
// [1,2],
// []
//]
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/power-set-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2023-05-25.
 */
public class _08_04_幂集{
    class Solution {
        List<List<Integer>> res = new LinkedList<>();
        public List<List<Integer>> subsets(int[] nums) {
            if (nums == null) {
                return res;
            }
            backtrace(0, nums.length, nums, new LinkedList<Integer> ());
            return res;
        }
        void backtrace(int start, int end, int[] nums, LinkedList<Integer> path) {
            res.add(new ArrayList<>(path));
            for (int i = start; i < end; i++) {
                path.add(nums[i]);
                backtrace(i + 1, end, nums, path);
                path.removeLast();
            }
        }
    }

    /**
     * 思考：一个集合会有多少个子集 2^n
     * 如果返回结果用一个子集列表表示，那么最佳的运行时间实际上就是所有子集中元素的总数，一共2^n 个子集，并且n个元素中每一个都只在
     * 这些子集中的一半出现，即2^{n-1} 个子集。因此这些子集的元素总个数位 n*2^{n-1}。
     * 因此，在时间或空间复杂度上，不可能做的比O(n*2^n)好。
     *
     *
     */
    class Solution2 {
        public List<List<Integer>> subsets(int[] nums) {
            return getSubSets(nums, 0);
        }

        private List<List<Integer>> getSubSets(int[] nums, int i) {
            List<List<Integer>> allSubSets = null;
            if(nums.length == i) {
                allSubSets = new ArrayList<>();
                allSubSets.add(new ArrayList<>());
            }
            else {
                allSubSets = getSubSets(nums, i+1);
                int item = nums[i];
                ArrayList<ArrayList<Integer>> moreSubSets = new ArrayList<>();
                for (List<Integer> subSet : allSubSets) {
                    ArrayList<Integer> newSubSet = new ArrayList<>();
                    newSubSet.addAll(subSet);
                    newSubSet.add(item);
                    moreSubSets.add(newSubSet);
                }
                allSubSets.addAll(moreSubSets);
            }
            return allSubSets;
        }
    }
}
