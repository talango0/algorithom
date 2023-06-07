package leetcode.程序员面试金典;
//给定两条线段（表示为起点start = {X1, Y1}和终点end = {X2, Y2}），如果它们有交点，请计算其交点，没有交点则返回空值。
//
//要求浮点型误差不超过10^-6。若有多个交点（线段重叠）则返回 X 值最小的点，X 坐标相同则返回 Y 值最小的点。
//
//
//
//示例 1：
//
//输入：
//line1 = {0, 0}, {1, 0}
//line2 = {1, 1}, {0, -1}
//输出： {0.5, 0}
//示例 2：
//
//输入：
//line1 = {0, 0}, {3, 3}
//line2 = {1, 1}, {2, 2}
//输出： {1, 1}
//示例 3：
//
//输入：
//line1 = {0, 0}, {1, 1}
//line2 = {1, 0}, {2, 1}
//输出： {}，两条线段没有交点
//
//
//提示：
//
//坐标绝对值不会超过 2^7
//输入的坐标均是有效的二维坐标
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/intersection-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.Test;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-06-07.
 */
public class _16_03_交点{
    /**
     * <pre>
     * 两个线段相交：
     * 直线相交条件
     * AND
     * 交点 x 和 y 坐标位于线段 1 的范围内。
     * AND
     * 交点 x 和 y 坐标位于线段 2 的范围内。
     *
     * 如果两条线段位于同一条无限长度的直线上呢？如果时这种情况，则两条线段必须有一部分重合。
     * 如果我们按照 x 坐标的位置对两条线段排序（起点位于终点前，点1位于点2之前），那么两条线
     * 只能在下面的情况下相交
     * 假设：start1.x < start2.x && start1.x < end1.x && start2.x < end2.x
     * 两条线相交的条件为：
     * start2位于 start1 和 end1 之前。
     *
     * </pre>
     */
    class Solution{
        class Point{
            double x;
            double y;

            public Point(double x, double y) {
                this.x = x;
                this.y = y;
            }
        }

        class Line{
            double slope, yintercept;

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
        }

        public double[] intersection(int[] start1, int[] end1, int[] start2, int[] end2) {
            Point point = intersection(new Point(start1[0], start1[1]), new Point(end1[0], end1[1]), new Point(start2[0], start2[1]), new Point(end2[0], end2[1]));
            return point == null ? new double[0] :new double[]{point.x, point.y};
        }

        public Point intersection(Point start1, Point end1, Point start2, Point end2) {
            if (start1.x > end1.x) {
                swap(start1, end1);
            }
            if (start2.x > end2.x) {
                swap(start2, end2);
            }
            if (start1.x > start2.x) {
                swap(start1, start2);
                swap(end1, end2);
            }

            // 计算直线
            Line line1 = new Line(start1, end1);
            Line line2 = new Line(start2, end2);

            // 如果斜率相等，那么它们只在 start2 位于线1 且具有相同 y轴截距时相交
            if (line1.slope == line2.slope) {
                if (line1.yintercept == line2.yintercept && isBetween(start1, start2, end1)) {
                    return start2;
                }
                else {
                    if (line1.yintercept == line2.yintercept && isBetween(start2, start1, end2)) {
                        return start1;
                    }
                    else {
                        return null;
                    }
                }
            }
            Point intersection = null;
            // line1的斜率不存在
            if (line1.slope == Integer.MAX_VALUE) {
                intersection = new Point(line1.yintercept, line1.yintercept * line2.slope + line2.yintercept);
            }
            // line2的斜率不存在
            else if (line2.slope == Integer.MAX_VALUE) {
                intersection = new Point(line2.yintercept, line2.yintercept * line1.slope + line1.yintercept);
            }
            else {
                // 获交点坐标
                double x = (line2.yintercept - line1.yintercept) / (line1.slope - line2.slope);
                double y = line1.slope * x + line1.yintercept;
                intersection = new Point(x, y);
            }
            // 检查是否在线段范围内
            if (isBetween(start1, intersection, end1) && isBetween(start2, intersection, end2)) {
                return intersection;
            }
            return null;
        }

        private boolean isBetween(Point start, Point middle, Point end) {
            return isBetween(start.x, middle.x, end.x) && isBetween(start.y, middle.y, end.y);
        }

        private boolean isBetween(double start, double middle, double end) {
            if (start > end) {
                return end <= middle && middle <= start;
            }
            else {
                return start <= middle && middle <= end;
            }
        }

        private void swap(Point start1, Point end1) {
            double x = start1.x;
            double y = start1.y;
            start1.x = end1.x;
            start1.y = end1.y;
            end1.x = x;
            end1.y = y;
        }
    }

    @Test
    public void testSolution1() {
        Solution solution = new Solution();
        //double[] intersection = solution.intersection(new int[]{0, 3},
        //        new int[]{0, 6},
        //        new int[]{0, 1},
        //        new int[]{0, 5});

        double[] intersection = solution.intersection(new int[]{1, 0},
                new int[]{1, 1},
                new int[]{-1, 0},
                new int[]{3, 2});
        System.out.println(Arrays.toString(intersection));

    }


    class Soltion2{
        public double[] intersection(int[] start1_, int[] end1_, int[] start2_, int[] end2_) {

            // 为了避免给后面使用造成不便，修改了入参声明

            Point[] points = checkAndConvertIntoPoint(start1_, end1_, start2_, end2_);
            Point start1 = points[0];
            Point end1 = points[1];
            Point start2 = points[2];
            Point end2 = points[3];

            // 封装成直线类，以便计算斜率和截距
            Line line1 = new Line(start1, end1);
            Line line2 = new Line(start2, end2);

            // 误差精度
            double epslion = 1e-6f;
            // 两条直线的交点（如果存在的话）
            Point intersection;

            // 情况 1：（特殊情况）两条直线有一条斜率为正无穷
            if (line1.k == Integer.MAX_VALUE || line2.k == Integer.MAX_VALUE) {

                // 两条直线斜率都不存在时
                if (line1.k == Integer.MAX_VALUE && line2.k == Integer.MAX_VALUE) {

                    // 这里讨论两条直线重合的情况， b 不是截距的意思，而是表示 x = a 这条线段
                    if (Math.abs(line1.b - line2.b) <= epslion && (isBetween(start1, start2, end1)) || isBetween(start2, start1, end2)) {
                        if (isBetween(start1, start2, end1)) {
                            return new double[]{start2.x, start2.y};
                        }
                        else {
                            return new double[]{start1.x, start1.y};
                        }
                    }
                }

                // 其中一条直线斜率不存在
                if (line1.k == Integer.MAX_VALUE) {
                    intersection = new Point(line1.b, line1.b * line2.k + line2.b);
                }
                else {
                    intersection = new Point(line2.b, line2.b * line1.k + line1.b);
                }

            }
            else if (Math.abs(line1.k - line2.k) <= epslion) {
                // 情况 2：（特殊情况）斜率相等的情况下，如果在 y 轴上的截距相等，就表示两条直线重合
                if (Math.abs(line1.b - line2.b) <= epslion && isBetween(start1, start2, end1)) {
                    return new double[]{start2.x, start2.y};
                }
                return new double[0];
            }
            else {
                // 情况 3：（一般情况）使用公式计算交点的坐标
                double x = (line2.b - line1.b) / (line1.k - line2.k);
                double y = x * line1.k + line1.b;

                intersection = new Point(x, y);
            }

            // 检测所在直线的交点是否在两条线段的横纵坐标范围之内
            if (isBetween(start1, intersection, end1) && isBetween(start2, intersection, end2)) {
                return new double[]{intersection.x, intersection.y};
            }
            return new double[0];
        }


        private Point[] checkAndConvertIntoPoint(int[] start1_, int[] end1_, int[] start2_, int[] end2_) {
            // 封装成 Point 类，将 int 转换成 double 类型，以便于计算
            Point start1 = new Point(start1_[0], start1_[1]);
            Point end1 = new Point(end1_[0], end1_[1]);
            Point start2 = new Point(start2_[0], start2_[1]);
            Point end2 = new Point(end2_[0], end2_[1]);

            // 参数校验：保证横坐标符合约定
            // 对于单条线段而言，起点坐标总是横坐标较小的那一个
            if (start1.x > end1.x) {
                swap(start1, end1);
            }
            if (start2.x > end2.x) {
                swap(start2, end2);
            }

            // 对于两条线段而言，线段 1 的横坐标小于等于线段 2 的横坐标
            if (start1.x > start2.x) {
                // 两条线段交换
                swap(start1, start2);
                swap(end1, end2);
            }
            return new Point[]{start1, end1, start2, end2};
        }

        private boolean isBetween(double start, double middle, double end) {
            if (start > end) {
                // 逆序
                return end <= middle && middle <= start;
            }
            else {
                // 顺序
                return start <= middle && middle <= end;
            }
        }

        private boolean isBetween(Point start, Point middle, Point end) {
            return isBetween(start.x, middle.x, end.x) && isBetween(start.y, middle.y, end.y);
        }


        /**
         * 交换两个点坐标的数值
         *
         * @param point1
         * @param point2
         */
        private void swap(Point point1, Point point2) {
            double tempX = point1.x;
            double tempY = point1.y;

            point1.x = point2.x;
            point1.y = point2.y;

            point2.x = tempX;
            point2.y = tempY;
        }


        private class Line{
            /**
             * 斜率
             */
            private double k;

            /**
             * 在 y 轴上的截距
             */
            private double b;

            public Line(Point start, Point end) {
                double deltaY = end.y - start.y;
                double deltaX = end.x - start.x;

                // 注意：当 deltaX = 0 的时候，设置斜率为正无穷
                if (deltaX == 0) {
                    k = Integer.MAX_VALUE;
                    // 此时截距为直线在 x 轴上的截距，这里是特殊的做法
                    b = end.x;
                }
                else {
                    k = deltaY / deltaX;
                    b = end.y - k * end.x;
                }
            }
        }

        /**
         * 将输入封装成 Point，以便把 int 类型转换成 double 类型，便于计算
         */
        private class Point{
            private double x;
            private double y;

            public Point(double x, double y) {
                this.x = x;
                this.y = y;
            }
        }
    }


}
