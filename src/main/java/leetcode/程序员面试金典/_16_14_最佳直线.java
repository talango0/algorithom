package leetcode.程序员面试金典;
//给定一个二维平面及平面上的 N 个点列表Points，其中第i个点的坐标为Points[i]=[Xi,Yi]。请找出一条直线，其通过的点的数目最多。
//
//设穿过最多点的直线所穿过的全部点编号从小到大排序的列表为S，你仅需返回[S[0],S[1]]作为答案，若有多条直线穿过了相同数量的点，
// 则选择S[0]值较小的直线返回，S[0]相同则选择S[1]值较小的直线返回。
//
//示例：
//
//输入： [[0,0],[1,1],[1,0],[2,0]]
//输出： [0,2]
//解释： 所求直线穿过的3个点的编号为[0,2,3]
//提示：
//
//2 <= len(Points) <= 300
//len(Points[i]) = 2
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/best-line-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author mayanwei
 * @date 2023-06-11.
 */
public class _16_14_最佳直线{
    /**
     * 向量共线，且有一个公共点，则三点在一条直线上。
     */
    class Solution{
        public int[] bestLine(int[][] points) {
            int[] res = new int[2];
            int num = 0;
            int maxNum = 0;
            int len = points.length;

            for (int i = 0; i < points.length; i++) {
                for (int j = i + 1; j < len - 1; j++) {
                    num = 2;
                    if (len - 1 - j + 1 + 1 <= maxNum) break;//已经凑不够了，提前剪枝
                    long x1 = points[j][0] - points[i][0];
                    long y1 = points[j][1] - points[i][1];

                    for (int k = j + 1; k < len; k++) {
                        long x2 = points[k][0] - points[i][0];
                        long y2 = points[k][1] - points[i][1];
                        if (x1 * y2 == x2 * y1) num++;      //满足向量共线的条件，且有一个公共顶点，故在一条直线上
                    }
                    if (num > maxNum) {
                        maxNum = num;
                        res[0] = i;
                        res[1] = j;
                    }
                }
            }
            return res;
        }
    }


    class Point{
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Line{
        double slope, yintercept;
        public static double epsilon = .0001;
        boolean infinite_slop = false;

        public Line(Point p1, Point p2) {
            double deltaY = p2.y - p1.y;
            double deltaX = p2.x - p1.x;
            if (deltaX == 0) {
                this.slope = Integer.MAX_VALUE;
                yintercept = p2.x;
            }
            else {
                this.slope = deltaY / deltaX; // 当deltaX 为 0 时应为无穷大，不应该抛出异常
                yintercept = p2.y - this.slope * p2.x;
            }
        }

        public static double floorToNearestEpsilon(double slope) {
            int r = (int) (slope / epsilon);
            return ((double) r) * epsilon;
        }

        public boolean isEquivalent(Line o) {
            if (isEquivalent(o.slope, slope) && isEquivalent(o.yintercept, yintercept)
                    && (infinite_slop == o.infinite_slop)) {
                return true;
            }
            return false;
        }

        private boolean isEquivalent(double a, double b) {
            return Math.abs(a - b) < epsilon;
        }
    }

    static class HashMapList<T, E>{
        private HashMap<T, ArrayList<E>> map = new HashMap<T, ArrayList<E>>();

        // 在key出以链表形式插入
        public void put(T key, E item) {
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(item);
        }

        // 在key处插入一组项目
        public void put(T key, ArrayList<E> items) {
            map.put(key, items);
        }

        public ArrayList<E> get(T key) {
            return map.get(key);
        }

        public boolean containsKeyValue(T key, E value) {
            ArrayList<E> list = get(key);
            if (list == null) {
                return false;
            }
            return list.contains(value);
        }

        public Set<T> keySet() {
            return map.keySet();
        }

        @Override
        public String toString() {
            return map.toString();
        }
    }

    class Solution1{
        /**
         * 找到穿过最多点的直线
         *
         * @param points
         * @return
         */
        Line findBestLine(Point[] points) {
            HashMapList<Double, Line> linesBySlope = getListOfLines(points);
            return getBestLine(linesBySlope);
        }

        /**
         * 将一对点作为一条直线加入到链表中
         */
        private HashMapList<Double, Line> getListOfLines(Point[] points) {
            HashMapList<Double, Line> linesBySlop = new HashMapList<>();
            for (int i = 0; i < points.length; i++) {
                for (int j = i + 1; j < points.length; j++) {
                    Line line = new Line(points[i], points[j]);
                    double key = Line.floorToNearestEpsilon(line.slope);
                    linesBySlop.put(key, line);
                }
            }
            return linesBySlop;
        }

        /**
         * 返回具有最多相近直线的直线
         */
        private Line getBestLine(HashMapList<Double, Line> linesBySlope) {
            Line bestLine = null;
            int bestCount = 0;
            Set<Double> slops = linesBySlope.keySet();
            for (Double slop : slops) {
                ArrayList<Line> lines = linesBySlope.get(slop);
                for (Line line : lines) {
                    // 对于当前直线向近的直线进行统计
                    int count = countEquivalentLines(linesBySlope, line);
                    // 如果比当前直线更好，则进行替换
                    if (count > bestCount) {
                        bestLine = line;
                        bestCount = count;
                    }
                }
            }

            return bestLine;
        }

        /**
         * 查找相似直线构成的散列表，请注意我们需要检查斜率为当前斜率正负 epsilon 的直线，这是对近似直线的定义。
         */
        private int countEquivalentLines(HashMapList<Double, Line> linesBySlope, Line line) {

            double key = Line.floorToNearestEpsilon(line.slope);
            int count = countEquivalentLines(linesBySlope.get(key), line);
            count += countEquivalentLines(linesBySlope.get(key - Line.epsilon), line);
            count += countEquivalentLines(linesBySlope.get(key + Line.epsilon), line);
            return count;
        }

        /**
         * 计算数组中相近直线的数目（斜率和 y 轴焦点相差一个 epsilon 之内）
         */
        private int countEquivalentLines(ArrayList<Line> lines, Line line) {
            if (lines == null) {
                return 0;
            }
            int count = 0;
            for (Line parallelLine : lines) {
                if (parallelLine.isEquivalent(line)) {
                    count++;
                }
            }
            return count;
        }


    }
}
