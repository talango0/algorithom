package leetcode.arrays.random.searchremove;

import java.util.*;

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
