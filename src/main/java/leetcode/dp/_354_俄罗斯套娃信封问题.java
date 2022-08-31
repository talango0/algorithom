package leetcode.dp;
//给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。
//
// 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
//
// 请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
//
// 注意：不允许旋转信封。
//
// 示例 1：
//
//
//输入：envelopes = [[5,4],[6,4],[6,7],[2,3]]
//输出：3
//解释：最多信封的个数为 3, 组合为: [2,3] => [5,4] => [6,7]。
//
// 示例 2：
//
//
//输入：envelopes = [[1,1],[1,1],[1,1]]
//输出：1
//
//
//
//
// 提示：
//
//
// 1 <= envelopes.length <= 10⁵
// envelopes[i].length == 2
// 1 <= wi, hi <= 10⁵
//
//
// Related Topics 数组 二分查找 动态规划 排序 👍 778 👎 0

import java.util.Arrays;
import java.util.Comparator;

/**
 * 字节
 * @see _300_最长上升子序列
 * @author mayanwei
 * @date 2022-08-03.
 */
public class _354_俄罗斯套娃信封问题{

    //这样的话算法的时间复杂度为 O(NlogN)，因为排序和计算 LIS 各需要 O(NlogN) 的时间，加到一起还是 O(NlogN)；空间复杂度为 O(N)，因为计算 LIS 的函数中需要一个 top 数组。

    class Solution {
        // 最长递增子序列的一个变种，因为每次合法的签到是大的套小的，相当于二维平面中找到一个最长递增的子序列，其长度就是最多嵌套的信封个数
        // 首先，对宽度 w 从小到大排序，确保了w 这个纬度可以互相嵌套所以我们只需要专注高度 h 这个维护能够互相嵌套即可。
        // 其次，两个 w 相同的新封不能相互包含，所以对于宽度 w 相同的信封，对高度 h 进行降序排序，保证LIS 中不存在多个w相同的信封（题目中说了长宽相同也无法嵌套）
        // envelopes = [[w,h], [w,h]...]
        public int maxEnvelopes(int[][] envelopes) {
            int n = envelopes.length;
            // 按宽度升序排序，如果宽度相同，按照高度将序排序
            Arrays.sort(envelopes, new Comparator<int []>(){
                @Override
                public int compare(int [] a, int [] b){
                    return a[0] == b[0] ? b[1]-a[1] : a[0]-b[0];
                }
            });
            // 对高度数组寻找 LIS
            int [] height= new int[n];
            for (int i = 0; i < n; i++) {
                height[i] = envelopes[i][1];
            }
            return lengthOfLIS(height);
        }

        // patience sequence
        public int lengthOfLIS(int[] nums) {
            int [] top = new int[nums.length];
            // 牌堆初始化为 0
            int piles = 0;
            for (int i = 0; i < nums.length; i++) {
                // 要处理的扑克牌
                int poker = nums[i];
                // 搜索左侧边界的二分查找
                int left = 0, right = piles;
                while (left < right) {
                    int mid = (left + right)/2;
                    if (top[mid] > poker) {
                        right = mid;
                    }
                    else if (top[mid] < poker) {
                        left = mid+1;
                    }
                    else {
                        right = mid;
                    }
                }
                /****/
                // 没有找到合适的，新建一堆
                if (left == piles) {
                    piles++;
                }
                // 把这张牌放在堆顶
                top[left] = poker;

            }
            // 牌堆数就是 LTS 长度
            return piles;
        }
    }
}
