package leetcode.arrays.binarySearch;
//传送带上的包裹必须在 days 天内从一个港口运送到另一个港口。
//
// 传送带上的第 i 个包裹的重量为 weights[i]。每一天，我们都会按给出重量（weights）的顺序往传送带上装载包裹。
// 我们装载的重量不会超过船的最大运载重量。
//
// 返回能在 days 天内将传送带上的所有包裹送达的船的最低运载能力。
//
//
//
// 示例 1：
//输入：weights = [1,2,3,4,5,6,7,8,9,10], days = 5
//输出：15
//解释：
//船舶最低载重 15 就能够在 5 天内送达所有包裹，如下所示：
//第 1 天：1, 2, 3, 4, 5
//第 2 天：6, 7
//第 3 天：8
//第 4 天：9
//第 5 天：10
//
//请注意，货物必须按照给定的顺序装运，因此使用载重能力为 14 的船舶并将包装分成 (2, 3, 4, 5), (1, 6, 7), (8), (9), (1
//0) 是不允许的。
//
//
// 示例 2：
//输入：weights = [3,2,2,4,1,4], days = 3
//输出：6
//解释：
//船舶最低载重 6 就能够在 3 天内送达所有包裹，如下所示：
//第 1 天：3, 2
//第 2 天：2, 4
//第 3 天：1, 4
//
//
// 示例 3：
//输入：weights = [1,2,3,1,1], days = 4
//输出：3
//解释：
//第 1 天：1
//第 2 天：2
//第 3 天：3
//第 4 天：1, 1
//
//
//
//
// 提示：
//
//
// 1 <= days <= weights.length <= 5 * 10⁴
// 1 <= weights[i] <= 500
//
// Related Topics 数组 二分查找 👍 477 👎 0

/**
 * @author mayanwei
 * @date 2022-07-01.
 */
public class _1011_在D天内送达包裹的能力{
    class Solution {
        //定义：当运载能力为 x 时，需要 f(x) 天运完所有货物
        private int f(int [] weights, int x) {
            if(weights == null || weights.length==0){
                return 0;
            }
            int days = 0, compacity = x;
            for(int i = 0; i < weights.length; ) {
                int cap = x;
                while (i<weights.length) {
                    if (cap < weights[i]) {
                        break;
                    } else {
                        cap -= weights[i];
                    }
                    i++;
                }
                days ++;
            }
            return days;
        }
        //题目：在days天内运完weights的最低运载能力
        public int shipWithinDays(int[] weights, int days) {
            //船的最小在中应该是 weights 中元素的最大值，因为每次都得运一件获取走，不能装不下
            int left = 0;
            //最大载重是所有元素之和，即一次把所有货物运走。
            //因为是右开区间，所以额外加1
            int right = 1;
            for (int w: weights) {
                left = Math.max(left, w);
                right += w;
            }

            while (left<right) {
                int mid = left + (right-left)/2;
                if(f(weights, mid) <= days) {
                    //找左边界
                    right = mid;
                }
                // else if(f(weights, mid) < days) {
                //     //让 f(x) 大一些
                //     right = mid;
                // }
                else if(f(weights, mid) > days) {
                    //让 f(x) 小一些
                    left = mid+1;
                }
            }
            return left;

        }
    }
}
