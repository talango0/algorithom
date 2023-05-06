package leetcode.arrays;
//n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
//
//你需要按照以下要求，给这些孩子分发糖果：
//
//每个孩子至少分配到 1 个糖果。
//相邻两个孩子评分更高的孩子会获得更多的糖果。
//请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
//
//示例 1：
//
//输入：ratings = [1,0,2]
//输出：5
//解释：你可以分别给第一个、第二个、第三个孩子分发 2、1、2 颗糖果。
//示例 2：
//
//输入：ratings = [1,2,2]
//输出：4
//解释：你可以分别给第一个、第二个、第三个孩子分发 1、2、1 颗糖果。
//     第三个孩子只得到 1 颗糖果，这满足题面中的两个条件。
//提示：
//
//n == ratings.length
//1 <= n <= 2 * 104
//0 <= ratings[i] <= 2 * 104
//Related Topics
//
//👍 977, 👎 0

/**
 * @author mayanwei
 * @date 2022-08-31.
 */
public class _135_分发糖果{
    //思路及解法：两次遍历
    //我们可以将「相邻的孩子中，评分高的孩子必须获得更多的糖果」这句话拆分为两个规则，分别处理。
    //左规则：当 ratings[i−1]<ratings[i] 时，i号学生的糖果数量将比 i−1 号孩子的糖果数量多。
    //右规则：当 ratings[i]>ratings[i+1] 时，i 号学生的糖果数量将比 i+1 号孩子的糖果数量多。
    //我们遍历该数组两次，处理出每一个学生分别满足左规则或右规则时，最少需要被分得的糖果数量。每个人最终分得的糖果数量即为这两个数量的最大值。

    /**
     * <pre>
     * ┌───────────────────────┐
     * │                  1,0,2│
     * │        left[i]:  1,1,2│
     * │       right[j]:  2,1,1│
     * │max(left,right):  2,1,2│ ==> return 5
     * └───────────────────────┘
     * </pre>
     */
    class Solution{
        public int candy(int[] ratings) {
            int n = ratings.length;
            int[] left = new int[n];
            for (int i = 0; i < n; i++) {
                if (i > 0 && ratings[i] > ratings[i - 1]) {
                    left[i] = left[i - 1] + 1;
                }
                else {
                    left[i] = 1;
                }
            }
            int right = 0, ret = 0;
            for (int i = n - 1; i >= 0; i--) {
                if (i < n - 1 && ratings[i] > ratings[i + 1]) {
                    right++;
                }
                else {
                    right = 1;
                }
                ret += Math.max(left[i], right);
            }
            return ret;
        }
    }

    /**
     * <pre>
     * 我们从左到右枚举每一个同学，记前一个同学分得的糖果数量为  pre：
     * <li>如果当前同学比上一个同学评分高，说明我们就在最近的递增序列中，直接分配给该同学 pre+1 个糖果即可。</li>
     * <li>
     * 否则我们就在一个递减序列中，我们直接分配给当前同学一个糖果，并把该同学所在的递减序列中所有的同学都再多分配一个糖果，
     * 以保证糖果数量还是满足条件。
     * <ol>我们无需显式地额外分配糖果，只需要记录当前的递减序列长度，即可知道需要额外分配的糖果数量。</ol>
     * <ol>同时注意当当前的递减序列长度和上一个递增序列等长时，需要把最近的递增序列的最后一个同学也并进递减序列中。</ol>
     * </li>
     * 这样，我们只要记录当前递减序列的长度 dec，最近的递增序列的长度 inc 和前一个同学分得的糖果数量  pre 即可。
     * </pre>
     */
    class Solution2{
        public int candy(int[] ratings) {
            int n = ratings.length;
            int ret = 1;
            int inc = 1, dec = 0, pre = 1;
            for (int i = 1; i < n; i++) {
                if (ratings[i] >= ratings[i - 1]) {
                    dec = 0;
                    pre = ratings[i] == ratings[i - 1] ? 1 :pre + 1;
                    ret += pre;
                    inc = pre;
                }
                else {
                    dec++;
                    if (dec == inc) {
                        dec++;
                    }
                    ret += dec;
                    pre = 1;
                }
            }
            return ret;
        }
    }
}
