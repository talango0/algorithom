package leetcode.backtracing;
//给出集合 [1,2,3,...,n]，其所有元素共有 n! 种排列。
//
// 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
//
//
// "123"
// "132"
// "213"
// "231"
// "312"
// "321"
//
//
// 给定 n 和 k，返回第 k 个排列。
//
//
//
// 示例 1：
//
//
//输入：n = 3, k = 3
//输出："213"
//
//
// 示例 2：
//
//
//输入：n = 4, k = 9
//输出："2314"
//
//
// 示例 3：
//
//
//输入：n = 3, k = 1
//输出："123"
//
//
//
//
// 提示：
//
//
// 1 <= n <= 9
// 1 <= k <= n!
//
//
// Related Topics 递归 数学 👍 703 👎 0

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-09-04.
 */
public class _60_排列序列{

    class Solution{
        public String getPermutation(int n, int k) {
            int[] factorial = new int[n];
            factorial[0] = 1;
            for (int i = 1; i < n; ++i) {
                factorial[i] = factorial[i - 1] * i;
            }

            --k;
            StringBuffer ans = new StringBuffer();
            int[] valid = new int[n + 1];
            Arrays.fill(valid, 1);
            for (int i = 1; i <= n; ++i) {
                int order = k / factorial[n - i] + 1;
                for (int j = 1; j <= n; ++j) {
                    order -= valid[j];
                    if (order == 0) {
                        ans.append(j);
                        valid[j] = 0;
                        break;
                    }
                }
                k %= factorial[n - i];
            }
            return ans.toString();
        }
    }

    class Solution2{
        public int count = 0;
        public int locate_k;
        public List<List<Integer>> res = new ArrayList<>();

        public String getPermutation(int n, int k) {
            int[] sequence = new int[n];
            for (int i = 0; i < n; i++) {
                sequence[i] = i + 1;
            }

            if (k == 1) {
                return toStr(sequence);
            }

            int i_n = 1;        //每组包含的个数
            for (int i = n - 1; i >= 1; i--) {
                i_n *= i;    //i_n = 6
            }

            while (locate_k < k) {
                locate_k += i_n;
            }

            locate_k /= i_n;    //确定我们应该从哪个数字开始
            //2

            count = k - (locate_k - 1) * i_n - 1;

            List<Integer> path = new ArrayList<>();
            path.add(sequence[locate_k - 1]);
            backtrack(sequence, path);

            return toStr(path);
        }

        private void backtrack(int[] sequence, List<Integer> path) {
            if (path.size() == sequence.length) {
                if (count == 0) {
                    res.add(new ArrayList<>(path));
                }
                count--;
                return;
            }

            for (int i = 0; i < sequence.length; i++) {
                if (!path.contains(sequence[i])) {
                    path.add(sequence[i]);
                    backtrack(sequence, path);
                    path.remove(path.size() - 1);
                }
            }
        }

        //--------------HELPER FUNCTION----------------------------------
        private String toStr(List<Integer> path) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < path.size(); i++) {
                sb.append(path.get(i));
            }
            return sb.toString();
        }

        private String toStr(int[] sequence) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < sequence.length; i++) {
                sb.append(sequence[i]);
            }
            return sb.toString();
        }

    }


    @Test
    public void test() {
        Solution2 solution2 = new Solution2();
        System.out.println(solution2.getPermutation(4, 9));
    }
}
