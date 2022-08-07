package leetcode.arrays;
//给你一个按升序排序的整数数组 num（可能包含重复数字），请你将它们分割成一个或多个长度至少为 3 的子序列，其中每个子序列都由连续整数组成。
//
// 如果可以完成上述分割，则返回 true ；否则，返回 false 。
//
//
//
// 示例 1：
//
//
//输入: [1,2,3,3,4,5]
//输出: True
//解释:
//你可以分割出这样两个连续子序列 :
//1, 2, 3
//3, 4, 5
//
//
// 示例 2：
//
//
//输入: [1,2,3,3,4,4,5,5]
//输出: True
//解释:
//你可以分割出这样两个连续子序列 :
//1, 2, 3, 4, 5
//3, 4, 5
//
//
// 示例 3：
//
//
//输入: [1,2,3,4,4,5]
//输出: False
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 10000
//
//
// Related Topics 贪心 数组 哈希表 堆（优先队列） 👍 388 👎 0

import edu.princeton.cs.algs4.In;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2022-08-07.
 */
public class _659_分割数组为连续子序列{
    class Solution{
        public boolean isPossible(int[] nums) {
            // freq 记录每个元素出现的次数，比如 freq[3] = 2说明元素3 在nums 中出现了2次
            Map<Integer, Integer> freq = new HashMap<>();
            // need 记录哪些元素可以被接到其他子序列后面
            // 比如现在已经组成了两个子序列 [1，2，3，4] 和 [2，3，4]，那么 need[5] 的值就应该是2，说明元素5 的需求为2
            Map<Integer, Integer> need = new HashMap<>();
            for (int v : nums) {
                freq.put(v, freq.getOrDefault(v, 0) + 1);
            }
            for (int v : nums) {
                if (freq.get(v) == 0) {
                    // 已经被用到其他子序列中
                    continue;
                }
                // 先判断 v 是否能接到其他子序列后面
                if (need.containsKey(v) && need.get(v) > 0) {
                    // v 可以接到之前的某个序列后面
                    freq.put(v, freq.getOrDefault(v, 0) - 1);
                    // 对 v的需求减1
                    need.put(v, need.getOrDefault(v, 0) - 1);
                    // 对 v+1 的需求加1
                    need.put(v + 1, need.getOrDefault(v + 1, 0) + 1);
                }
                else if (freq.get(v) > 0 && freq.getOrDefault(v + 1, 0) > 0 && freq.getOrDefault(v + 2, 0) > 0) {
                    freq.put(v, freq.getOrDefault(v, 0) - 1);
                    freq.put(v + 1, freq.getOrDefault(v + 1, 0) - 1);
                    freq.put(v + 2, freq.getOrDefault(v + 2, 0) - 1);
                    // 对 v+3 的需求加一
                    need.put(v + 3, need.getOrDefault(v + 3, 0) + 1);
                }
                else {
                    // 两种情况都不符合，则无法分配
                    return false;
                }
            }
            return true;
        }
    }


    // 你可能会说斗地主里面的顺子至少需要1张
    boolean isPossible(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();
        Map<Integer, List<List<Integer>>> need = new HashMap<>();

        for (int v : nums) {
            freq.put(v, freq.getOrDefault(v, 0) + 1);
        }

        for (int v : nums) {
            if (freq.get(v) == 0) {
                continue;
            }

            if (need.containsKey(v) && need.getOrDefault(v, new ArrayList<>(0)).size() > 0) {
                // v 可以接到之前的某个序列后面
                freq.put(v, freq.getOrDefault(v, 0) - 1);
                // 随便取一个需要 v 的子序列
                List<Integer> seq = need.get(v).get(need.get(v).size());
                need.get(v).remove(need.get(v).size() - 1);
                // 把 v 接到这个子序列后面
                seq.add(v);
                // 这个子序列的需求变成了 v + 1
                List<List<Integer>> needV = need.getOrDefault(v + 1, new ArrayList<>());
                needV.add(seq);
                need.put(v + 1, needV);

            }
            else if (freq.getOrDefault(v, 0) > 0
                    && freq.getOrDefault(v + 1, 0) > 0
                    && freq.getOrDefault(v + 2, 0) > 0) {
                // 可以将 v 作为开头
                freq.put(v, freq.getOrDefault(v, 0)-1);
                freq.put(v+1, freq.getOrDefault(v+1, 0)-1);
                freq.put(v+2, freq.getOrDefault(v+2, 0)-1);

                // 新建一个长度为 3 的子序列 [v,v + 1,v + 2]
                List<Integer> seq  = new ArrayList<>();
                seq.add(v);
                seq.add( v + 1);
                seq.add(v + 2);
                // 对 v + 3 的需求加一
                List<List<Integer>> needV = new ArrayList<>();
                needV.add(seq);
                need.put(v + 3, needV);

            }
            else {
                return false;
            }
        }

        // 打印切分出的所有子序列
        for (Map.Entry<Integer, List<List<Integer>>> it : need.entrySet()) {
            for (List<Integer> seq:it.getValue()){
                for (Integer v : seq) {
                    System.out.println(v + " ");
                }
            }
        }
        return true;
    }

}
