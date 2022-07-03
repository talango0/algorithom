package leetcode;
// 指定年份和月份，返回该月多少天
/**
 * @author mayanwei
 * @date 2022-07-03.
 */
public class _1118_一月有多少天{
    class Solution {
        public int numberOfDays(int Y, int M) {
            // 一三五七八十蜡，31 天永不差
            if (M == 1 || M == 3 || M == 5|| M == 7 || M == 8 || M == 10 || M == 12) return 31;

            // 四六九东三十整
            if (M == 4 || M == 6 || M == 9 || M == 11) return 30;
            if (Y % 100 == 0) {  // 世纪年
                if (Y % 400 == 0) {
                    return 29;
                } else {
                    return 28;
                }
            } else {  // 普通年
                if (Y % 4 == 0) {
                    return 29;
                } else {
                    return 28;
                }
            }
        }
    }
}
