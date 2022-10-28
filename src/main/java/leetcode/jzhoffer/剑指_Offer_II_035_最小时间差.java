package leetcode.jzhoffer;
//给定一个 24 小时制（小时:分钟 "HH:MM"）的时间列表，找出列表中任意两个时间的最小时间差并以分钟数表示。
//
//
//
//示例 1：
//
//输入：timePoints = ["23:59","00:00"]
//输出：1
//示例 2：
//
//输入：timePoints = ["00:00","23:59","00:00"]
//输出：0
//
//
//提示：
//
//2 <= timePoints <= 2 * 104
//timePoints[i] 格式为 "HH:MM"
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/569nqc
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Collections;
import java.util.List;

/**
 * @see _539_最小时间差
 */
public class 剑指_Offer_II_035_最小时间差 {
    class Solution {
        /**
         * 将 timePoints排序后，最小时间差必然出现在 timePoints 的两个相邻时间，或者 timePoints 的两个收尾时间中。
         * 因此排序后序遍历一遍 timePoints即可得到最小时间差。
         */
        public int findMinDifference(List<String> timePoints) {
            Collections.sort(timePoints);
            int ans = Integer.MAX_VALUE;
            int t0Minutes = getMinutes(timePoints.get(0));
            int preMinutes = t0Minutes;
            for (int i = 1; i < timePoints.size(); i++) {
                int minutes = getMinutes(timePoints.get(i));
                // 相邻时间的时间差
                ans = Math.min(ans, minutes - preMinutes);
                preMinutes = minutes;
            }
            // 首尾的时间差
            ans = Math.min(ans, t0Minutes + 24 * 60 - preMinutes);
            return ans;
        }

        public int getMinutes(String t) {
            return ((t.charAt(0) - '0') * 10 + (t.charAt(1) - '0')) * 60
                    + (t.charAt(3) - '0') * 10 + t.charAt(4) - '0';
        }
    }
}
