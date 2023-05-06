package leetcode.arrays.binarySearch;
//给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
//
// 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。
// （例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
//
// 进阶：
//
// 如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。
// 在这种情况下，你会怎样改变代码？
//
//
// 示例 1：
//输入：s = "abc", t = "ahbgdc"
//输出：true
//
//
// 示例 2：
//输入：s = "axc", t = "ahbgdc"
//输出：false
//
//
//
//
// 提示：
//
//
// 0 <= s.length <= 100
// 0 <= t.length <= 10^4
// 两个字符串都只由小写字符组成。
//
//
// Related Topics 双指针 字符串 动态规划 👍 710 👎 0

import java.util.ArrayList;

/**
 * @author mayanwei
 * @date 2022-08-10.
 * todo
 * @see 792 题「 匹配子序列的单词数
 */
public class _392_判断子序列{
    class Solution{
        /**
         * 利用双指针 i, j 分别指向 s, t，一边前进一边匹配子序列
         * <p>
         * 而如果巧妙运用二分查找，可以将时间复杂度降低，大约是 O(MlogN)。由于 N 相对 M 大很多，所以后者效率会更高
         * <p>
         * 二分思路：主要是对 t 进行预处理，用以给字典 index 将每个字符出现的索引位置按顺序存储下来
         *<pre>
         * ┌─────────────────────┐
         * │      0 1 2 3 4 5 6  │
         * │ t :  c a c b h b c  │
         * └─────────────────────┘
         * ┌─────────────────────┐
         * │       a:[1]         │
         * │index: b:[3,5]       │
         * │       c:[0,2,6]     │
         * │       h:[4]         │
         * └─────────────────────┘
         * 对于下面这种情况匹配了a,b,需要匹配 s 的c了，可以从字符串t的j下标开始顺序寻找c。复杂度为 O（n）
         * 我们可以借助上面的index 进行二分查找。
         * 我们在 c:[0,2,6] 中寻找大于 j = 4 的第一个元素。
         * 思路：二分查找寻找右边界。
         * 二分查找返回目标值 val 的索引，对于搜索左侧边界的二分查找，有一个特殊性质：
         * 当 val 不存在时，得到的索引恰好是比 val 大的最小元素索引。
         * 什么意思呢，就是说如果在数组 [0,1,3,4] 中搜索元素 2，算法会返回索引 2，
         * 也就是元素 3 的位置，元素 3 是数组中大于 2 的最小元素。所以我们可以利用二分搜索避免线性扫描。
         * ┌─────────────────────┐
         * │        j   c:[0,2,6]│
         * │s:  a b c            │
         * │                     │
         * │    0 1 2 3 4 5 6    │
         * │t:  c a c b h b c    │
         * │            j        │
         * └─────────────────────┘
         * </pre>
         */
        public boolean isSubsequence(String s, String t) {
            int m = s.length(), n = t.length();
            ArrayList<Integer>[] index = new ArrayList[256];
            // 先记下 t 中每个字符出现的位置
            for (int i = 0; i < n; i++) {
                char c = t.charAt(i);
                if (index[c] == null) {
                    index[c] = new ArrayList<>();
                }
                index[c].add(i);
            }

            // 串 t 上的指针
            int j = 0;
            // 借助 index 查找 s[i]
            for (int i = 0; i < m; i++) {
                char c = s.charAt(i);
                // 整个 t 压根儿没有字符c
                if (index[c] == null) {
                    return false;
                }
                int pos = left_bound(index[c], j);
                // 二分搜索区间中没有找到字符 c
                if (pos == index[c].size()) {
                    return false;
                }
                // 向前移动指针 j
                j = index[c].get(pos) + 1;
            }
            return true;
        }

        // 查找左侧边界的二分查找
        // 当 val 不存在时，得到的索引恰好是比 val 大的最小元素索引。
        // 什么意思呢，就是说如果在数组 [0,1,3,4] 中搜索元素 2，算法会返回索引 2，也就是元素 3 的位置，
        // 元素 3 是数组中大于 2 的最小元素。所以我们可以利用二分搜索避免线性扫描。
        int left_bound(ArrayList<Integer> arr, int tar) {
            int lo = 0, hi = arr.size();
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (tar > arr.get(mid)) {
                    lo = mid + 1;
                }
                else {
                    hi = mid;
                }
            }
            return lo;
        }
    }

    @Deprecated
    class Solution2{
        public boolean isSubsequence(String s, String t) {
            if (s == null || t == null) {
                return false;
            }
            int m = s.length(), n = t.length();
            int i = 0, j = 0;
            while (i < m && j < n) {
                if (s.charAt(i) == t.charAt(j)) {
                    i++;
                }
                j++;
            }
            return i == m;
        }
    }
}
