package leetcode.arrays.random.searchremove;

import java.util.*;
//给定一个整数 n 和一个 无重复 黑名单整数数组blacklist。设计一种算法，
// 从 [0, n - 1] 范围内的任意整数中选取一个未加入黑名单blacklist的整数。
// 任何在上述范围内且不在黑名单blacklist中的整数都应该有 同等的可能性 被返回。
//
//优化你的算法，使它最小化调用语言 内置 随机函数的次数。
//
//实现Solution类:
//Solution(int n, int[] blacklist)初始化整数 n 和被加入黑名单blacklist的整数
//int pick()返回一个范围为 [0, n - 1] 且不在黑名单blacklist 中的随机整数
//
//
//示例 1：
//
//输入
//["Solution", "pick", "pick", "pick", "pick", "pick", "pick", "pick"]
//[[7, [2, 3, 5]], [], [], [], [], [], [], []]
//输出
//[null, 0, 4, 1, 6, 1, 0, 4]
//
//解释
//Solution solution = new Solution(7, [2, 3, 5]);
//solution.pick(); // 返回0，任何[0,1,4,6]的整数都可以。注意，对于每一个pick的调用，
//                 // 0、1、4和6的返回概率必须相等(即概率为1/4)。
//solution.pick(); // 返回 4
//solution.pick(); // 返回 1
//solution.pick(); // 返回 6
//solution.pick(); // 返回 1
//solution.pick(); // 返回 0
//solution.pick(); // 返回 4
//
//
//提示:
//
//1 <= n <= 109
//0 <= blacklist.length <= min(105, n - 1)
//0 <= blacklist[i] < n
//blacklist中所有值都 不同
//pick最多被调用2 * 104次
//

/**
 * @author mayanwei
 * @date 2022-07-03.
 */
public class _710_黑名单中的随机数{
    class Solution {
        //可以将 [0, n) 看作一个组数，然后将 blackList 中的元素移动到数组末尾，同时用一个哈希表进行映射
        private Map<Integer,Integer> map;
        private Random random;
        private int sz;
        public Solution(int n, int[] blacklist) {
            sz = n - blacklist.length;
            random = new Random();
            map = new HashMap<>();

            Set<Integer> black = new HashSet<Integer>();
            for (int b : blacklist) {
                if (b >= sz) {
                    black.add(b);
                }
            }

            int w = sz;
            for (int b: blacklist) {
                if (b < sz) {
                    while (black.contains(w)) {
                        ++w;
                    }
                    map.put(b, w);
                    ++w;
                }
            }

        }

        public int pick() {
            int x = random.nextInt(sz);
            return map.getOrDefault(x, x);
        }
    }

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(n, blacklist);
 * int param_1 = obj.pick();
 */
}
