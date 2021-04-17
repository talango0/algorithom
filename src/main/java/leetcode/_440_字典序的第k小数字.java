package leetcode;

public class _440_字典序的第k小数字 {
    //给定整数 n 和 k，找到 1 到 n 中字典序第 k 小的数字。
//
// 注意：1 ≤ k ≤ n ≤ 109。
//
// 示例 :
//
//
//输入:
//n: 13   k: 2
//
//输出:
//10
//
//解释:
//字典序的排列是 [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9]，所以第二小的数字是 10。
//
// 👍 163 👎 0

    static class Solution {
        /**
         * 问题建模：
         * 每一个结点都拥有10个孩子结点，因为作为一个前缀，它后面可以接0-9个数，可以发现最终的结果就是对这棵10叉数进行前序遍历。
         * 关键问题在于：
         * 1. 如何确定一个前缀下所有子结点的个数？
         * 2. 如何确定第k个数在当前的前缀下，怎么继续往下面的子结点查找？
         * 3. 如果第k个数不在当前的前缀，即当前的前缀比较小，如何扩大前缀，增大寻找的范围？
         * @param n
         * @param k
         * @return
         */
        public int findKthNumber(int n, int k) {
            int cur = 1;
            k = k-1;
            while (k>0){
                //计算前缀之前的step数
                int steps = getStep(n, cur, cur+1);
                //在子树中
                if(steps > k){
                    cur *= 10;
                    //多了一个确定结点，继续k-1
                    k -= 1;
                }else {
                    //第k个数不在以cur为根节点的树上

                    //cur在字典序数组中从左往右移动
                    cur += 1;
                    k -= steps;
                }
            }
            return cur;
        }

        private int getStep(int n, long cur, long next){
            int steps = 0;
            while(cur <= n){
                //比如n是195的情况195到100有96个数
                steps += Math.min( n+1, next) - cur;
                cur *= 10;
                next *= 10;
            }
            return steps;
        }
    }
}
