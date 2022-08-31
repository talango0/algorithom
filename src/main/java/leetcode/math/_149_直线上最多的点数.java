package leetcode.math;
//给你一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点。求最多有多少个点在同一条直线上。
//
//示例 1：
//
//
//输入：points = [[1,1],[2,2],[3,3]]
//输出：3
//示例 2：
//
//
//输入：points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
//输出：4
//提示：
//
//1 <= points.length <= 300
//points[i].length == 2
//-104 <= xi, yi <= 104
//points 中的所有点 互不相同
//Related Topics
//
//👍 433, 👎 0

import java.util.HashMap;
import java.util.Map;

/**
 * 字节
 * @author mayanwei
 * @date 2022-08-25.
 */
public class _149_直线上最多的点数{
    class Solution{
        public int maxPoints(int[][] points) {
            //暴力穷举
            int n = points.length;
            if (n <= 2) {
                return n;
            }
            int res = 2;
            for (int i = 0; i < n; i++) {
                int x1 = points[i][0];
                int y1 = points[i][1];
                for (int j = i + 1; j < n; j++) {
                    int x2 = points[j][0];
                    int y2 = points[j][1];
                    int tmp = 2;
                    for (int k = j + 1; k < n; k++) {
                        int x3 = points[k][0];
                        int y3 = points[k][1];
                        int eq1 = (y2 - y1) * (x3 - x1);
                        int eq2 = (y3 - y1) * (x2 - x1);
                        if (eq1 == eq2) {
                            tmp++;
                        }
                    }
                    res = Math.max(res, tmp);
                }
            }
            return res;
        }

    }


    class Solution2{
        public int maxPoints(int[][] points) {
            int n = points.length;
            if (n <= 2) {
                return n;
            }
            int ret = 0;
            for (int i = 0; i < n; i++) {
                if (ret >= n - i || ret > n / 2) {
                    break;
                }
                Map<Integer, Integer> map = new HashMap<Integer, Integer>();
                for (int j = i + 1; j < n; j++) {
                    int x = points[i][0] - points[j][0];
                    int y = points[i][1] - points[j][1];
                    if (x == 0) {
                        y = 1;
                    }
                    else if (y == 0) {
                        x = 1;
                    }
                    else {
                        if (y < 0) {
                            x = -x;
                            y = -y;
                        }
                        int gcdXY = gcd(Math.abs(x), Math.abs(y));
                        x /= gcdXY;
                        y /= gcdXY;
                    }
                    int key = y + x * 20001;
                    map.put(key, map.getOrDefault(key, 0) + 1);
                }
                int maxn = 0;
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    int num = entry.getValue();
                    maxn = Math.max(maxn, num + 1);
                }
                ret = Math.max(ret, maxn);
            }
            return ret;
        }
        // 最大公约数
        public int gcd(int a, int b) {
            return b != 0 ? gcd(b, a % b) :a;
        }
    }
}
