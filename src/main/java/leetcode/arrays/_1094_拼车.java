package leetcode.arrays;
//车上最初有 capacity 个空座位。车 只能 向一个方向行驶（也就是说，不允许掉头或改变方向）
//
// 给定整数 capacity 和一个数组 trips , trip[i] = [numPassengersi, fromi, toi] 表示第 i 次旅行有
// numPassengersi 乘客，接他们和放他们的位置分别是 fromi 和 toi 。这些位置是从汽车的初始位置向东的公里数。
//
// 当且仅当你可以在所有给定的行程中接送所有乘客时，返回 true，否则请返回 false。
//
//
//
// 示例 1：
//输入：trips = [[2,1,5],[3,3,7]], capacity = 4
//输出：false
//
//
// 示例 2：
//输入：trips = [[2,1,5],[3,3,7]], capacity = 5
//输出：true
//
//
//
//
// 提示：
//
//
// 1 <= trips.length <= 1000
// trips[i].length == 3
// 1 <= numPassengersi <= 100
// 0 <= fromi < toi <= 1000
// 1 <= capacity <= 10⁵
//
// Related Topics 数组 前缀和 排序 模拟 堆（优先队列） 👍 190 👎 0

/**
 * @see _370_区间加法
 * @see _1109_航班预订统计
 */
public class _1094_拼车 {
    class Solution {
        public boolean carPooling(int[][] trips, int capacity) {
            //最多有 1001 个车站
            int [] nums = new int[1001];
            Differance differance = new Differance(nums);
            for (int [] trip:trips) {
                int i = trip[1];
                //第 trip[2] 站乘客已经下车，即乘客在车上的区间是 [trip[1], trip[2]-1]
                int j = trip[2] - 1;
                differance.increment(i, j, trip[0]);
            }
            int [] res = differance.result();
            for (int i = 0; i<res.length; i++) {
                if (capacity < res[i]) {
                    return false;
                }
            }
            return true;
        }


    }

    //差分数组工具类
    class Differance {
        //差分数组
        private int [] diff;
        //输入一个初始数组，区间操作将在这个数组进行
        public Differance(int [] nums){
            assert nums.length> 0;
            diff = new int[nums.length];
            diff[0] = nums[0];
            for (int i = 1; i<nums.length; i++){
                diff[i] = nums[i] - nums[i-1];
            }
        }

        //给数组nums[i,...,j] 增加 val,（val 可为负数）
        public void increment(int i, int j, int val) {
            diff[i] += val;
            //当j+1>= diff.length 时，说明对 nums[i] 以后的整个数组都要进行修改，这时不需要在给diff减 val
            if ( j+1 < diff.length ) {
                diff[j+1] -= val;
            }
        }

        //返回结果数组
        public int [] result(){
            int [] res = new int [diff.length];
            res[0] = diff[0];
            for (int i = 1; i<diff.length; i++) {
                res[i] = res[i-1] + diff[i];
            }
            return res;
        }
    }
}
