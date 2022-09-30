package leetcode.math;
//给你一个用字符数组 tasks 表示的 CPU 需要执行的任务列表。其中每个字母表示一种不同种类的任务。任务可以以任意顺序执行，并且每个任务都可以在 1 个
//单位时间内执行完。在任何一个单位时间，CPU 可以完成一个任务，或者处于待命状态。
//
// 然而，两个 相同种类 的任务之间必须有长度为整数 n 的冷却时间，因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。
//
// 你需要计算完成所有任务所需要的 最短时间 。
//
//
//
// 示例 1：
//
//
//输入：tasks = ["A","A","A","B","B","B"], n = 2
//输出：8
//解释：A -> B -> (待命) -> A -> B -> (待命) -> A -> B
//     在本示例中，两个相同类型任务之间必须间隔长度为 n = 2 的冷却时间，而执行一个任务只需要一个单位时间，所以中间出现了（待命）状态。
//
// 示例 2：
//
//
//输入：tasks = ["A","A","A","B","B","B"], n = 0
//输出：6
//解释：在这种情况下，任何大小为 6 的排列都可以满足要求，因为 n = 0
//["A","A","A","B","B","B"]
//["A","B","A","B","A","B"]
//["B","B","B","A","A","A"]
//...
//诸如此类
//
//
// 示例 3：
//
//
//输入：tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
//输出：16
//解释：一种可能的解决方案是：
//     A -> B -> C -> A -> D -> E -> A -> F -> G -> A -> (待命) -> (待命) -> A -> (待
//命) -> (待命) -> A
//
//
//
//
// 提示：
//
//
// 1 <= task.length <= 10⁴
// tasks[i] 是大写英文字母
// n 的取值范围为 [0, 100]
//
//
// Related Topics 贪心 数组 哈希表 计数 排序 堆（优先队列） 👍 1026 👎 0

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-09-29.
 */
public class _621_任务调度器{
    /**
     * 模拟
     * 时间复杂度： O(|tasks| *n) n:任务的种类，本题中用大写字母表示，因此 n不会超过26
     * 空间负载度： O(n)
     */
    class Solution{
        public int leastInterval(char[] tasks, int n) {
            Map<Character, Integer> freq = new HashMap<>();
            for (char ch : tasks) {
                freq.put(ch, freq.getOrDefault(ch, 0) + 1);
            }

            //任务总数
            int m = freq.size();
            //(nextValid_i, rest_i) 表示第 i 个任务，
            // 其中 nextValid_i 表示其因冷却限制，最早可以执行的时间，rest_i 表示其剩余执行次数。
            // 初始 nextValid_i 均为1， rest_i 即为任务 i 在数组 tasks 中出现的次数。
            List<Integer> nextValid = new ArrayList<>();
            List<Integer> rest = new ArrayList<>();
            Set<Map.Entry<Character, Integer>> entrySet = freq.entrySet();
            for (Map.Entry<Character, Integer> entry : entrySet) {
                int value = entry.getValue();
                nextValid.add(1);
                rest.add(value);
            }

            int time = 0;
            for (int i = 0; i < tasks.length; i++) {
                time++;
                int minNextValid = Integer.MAX_VALUE;
                for (int j = 0; j < m; j++) {
                    if (rest.get(j) != 0) {
                        minNextValid = Math.min(minNextValid, nextValid.get(j));
                    }
                }
                time = Math.max(time, minNextValid);
                int best = -1;
                for (int j = 0; j < m; j++) {
                    if (rest.get(j) != 0 && nextValid.get(j) <= time) {
                        if (best == -1 || rest.get(j) > rest.get(best)) {
                            best = j;
                        }
                    }
                }
                nextValid.set(best, time + n + 1);
                rest.set(best, rest.get(best) - 1);
            }
            return time;
        }
    }


    /**
     * 构造
     * ◀──n+1───▶
     * ┌─────────────┐
     * │A B C D F G X│
     * │A B C D E F X│
     * │A B C D E F G│
     * │A B C D E F G│
     * │A B C        │
     * └─────────────┘
     * 此时n + 1 = 5，但我们填了7列，标记 X 的格子表示 CPU 的待命状态。
     * 如果我们填【超出】了 n+1 列，那么所有 CPU 待命的状态都是可以省去的。这时应为待命状态本身只是为了规定任意两个相邻任务的执行间隔至少为 n
     * 但如果列数超过了 n + 1，那么就算没有这些待命状态，任意两个相邻任务的执行间隔肯定也会至少为 n。此时总执行时间为任务的总数 ｜task｜】
     */
    class Solution2{
        public int leastInterval(char[] tasks, int n) {

            int m = tasks.length;
            int[] cnts = new int[26];
            for (char c : tasks) {
                cnts[c - 'A']++;
            }
            int max = 0, cnt = 0;
            for (int i = 0; i < 26; i++) {
                if (cnts[i] > max) {
                    max = cnts[i];
                    cnt = 1;
                }
                else if (cnts[i] == max) {
                    cnt++;
                }
            }
            // 最多数量的任务之间不放满：(max - 1) * (n + 1) + cnt
            // 最多数量的任务之间都放满，并且有溢出：m
            return Math.max((max - 1) * (n + 1) + cnt, m);
        }
    }

}
