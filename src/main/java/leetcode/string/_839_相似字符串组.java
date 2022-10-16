package leetcode.string;

/**
 * @author mayanwei
 * @date 2022-10-16.
 * @see _854_相似度为K的字符串
 */
public class _839_相似字符串组{
    class Solution{
        /**
         * 把每一个字符串看作点，字符串之间是否相似看成边，可以发现
         * 本题询问的是给定的图中有多少连通分量。
         * 于是可以想到用并查集维护节点间的连通性。
         * <p>
         * 时间复杂度 O(n*n*m + nlogn)
         * 空间复杂度 O(n)
         */
        int[] f;

        public int numSimilarGroups(String[] strs) {
            int n = strs.length;
            int m = strs[0].length();
            f = new int[n];
            for (int i = 0; i < n; i++) {
                f[i] = i;
            }
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    int fi = find(i), fj = find(j);
                    if (fi == fj) {
                        continue;
                    }
                    if (check(strs[i], strs[j], m)) {
                        // strs[i] 和 strs[j] 相似，union
                        f[fi] = fj;
                    }
                }
            }
            int res = 0;
            for (int i = 0; i < n; i++) {
                if (f[i] == i) {
                    res++;
                }
            }
            return res;
        }

        private int find(int x) {
            // 路径优化
            return f[x] == x ? x :(f[x] = find(f[x]));
        }

        public boolean check(String a, String b, int len) {
            int num = 0;
            for (int i = 0; i < len; i++) {
                if (a.charAt(i) != b.charAt(i)) {
                    num++;
                    if (num > 2) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
