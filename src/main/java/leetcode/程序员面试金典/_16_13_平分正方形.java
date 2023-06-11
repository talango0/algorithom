package leetcode.程序员面试金典;
//给定两个正方形及一个二维平面。请找出将这两个正方形分割成两半的一条直线。假设正方形顶边和底边与 x 轴平行。
//
//每个正方形的数据square包含3个数值，正方形的左下顶点坐标[X,Y] = [square[0],square[1]]，以及正方形的边长square[2]。
// 所求直线穿过两个正方形会形成4个交点，请返回4个交点形成线段的两端点坐标（两个端点即为4个交点中距离最远的2个点，
// 这2个点所连成的线段一定会穿过另外2个交点）。2个端点坐标[X1,Y1]和[X2,Y2]的返回格式为{X1,Y1,X2,Y2}，要求若X1 != X2，需保证X1 < X2，
// 否则需保证Y1 <= Y2。
//
//若同时有多条直线满足要求，则选择斜率最大的一条计算并返回（与Y轴平行的直线视为斜率无穷大）。
//
//示例：
//
//输入：
//square1 = {-1, -1, 2}
//square2 = {0, -1, 2}
//输出： {-1,0,2,0}
//解释： 直线 y = 0 能将两个正方形同时分为等面积的两部分，返回的两线段端点为[-1,0]和[2,0]
//提示：
//
//square.length == 3
//square[2] > 0
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/bisect-squares-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-06-10.
 */
public class _16_13_平分正方形{
    /**
     * <pre>
     *     square1 = {-1, -1, 2}
     *     square2 = {0, -1, 2}
     *           y
     *     ┌────┴────┬────┐
     *     │    │    │    │
     *    ─◉━━━━■━━━━■━━━━◉─ x
     *     │    │    │    │
     *     └────┬────┴────┘
     *
     *  直线 y = 0 能将两个正方形同时分为等面积的两部分，返回的两线段端点为[-1,0]和[2,0]
     *
     *  两个正方形平方的直线必定通过两个正方形的中心。我们很容易知道该直线的斜率为 slop = (y1 - y2)/(x1 - x2)。
     *  通过这两个正方形的中心计算的处直线的斜率之后，我们可以使用相同的公式计算线段的起点和终点。
     *  假设原点(0, 0)位于左上角.
     * </pre>
     */
    class Solution{
        class Point{
            double x, y;

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

        class Square{
            double left, right, bottom, top;
            double size;

            public Square(double left, double right, double bottom, double top) {
                this.left = left;
                this.right = right;
                this.bottom = bottom;
                this.top = top;
                this.size = this.right - this.left;
            }

            public Point middle() {
                return new Point((this.left + this.right) / 2.0, (this.bottom + this.top) / 2.0);
            }

            /**
             * 返回连接 mid1 和 mid2 线段与1号正方形的交点，也就是说，从mid1到mid2 画一条线，延长至正方形边界相交。
             *
             * @param mid1
             * @param mid2
             * @return
             */
            public Point extend(Point mid1, Point mid2, double size) {
                // 计算连接mid1和mid2直线的方向
                double xdir = mid1.x < mid2.x ? -1 :1;
                double ydir = mid1.y < mid2.y ? -1 :1;
                // 如果 mid1.x 和mid2.x 的值相同，那么会出现为 0 的异常，因此要对该情况特殊处理
                if (mid1.x == mid2.x) {
                    return new Point(mid1.x, mid1.y + ydir * size / 2.0);
                }
                double slope = (mid1.y - mid2.y) / (mid1.x - mid1.y);
                double x1 = 0;
                double y1 = 0;
                if (Math.abs(slope) == 1){
                    x1 = mid1.x + xdir * size / 2.0;
                    y1 = mid1.y + ydir * size / 2.0;
                }
                else if(Math.abs(slope) < 1) {
                    x1 = mid1.x + xdir * size / 2.0;
                    y1 = slope * (x1 - mid1.x) + mid1.y;
                }
                else { //较大斜率
                    y1 = mid1.y + ydir * size / 2.0;
                    x1 = (y1 - mid1.y) / slope + mid1.x;
                }
                return new Point(x1, y1);
            }

            public Line cut(Square other) {
                // 计算那条线将与正方形的边相交
                Point p1 = extend(this.middle(), other.middle(), this.size);
                Point p2 = extend(this.middle(), other.middle(), -1 * this.size);
                Point p3 = extend(other.middle(), this.middle(), other.size);
                Point p4 = extend(other.middle(), this.middle(), -1 * other.size);
                // 对于上述的点，找到线段的起止点，start 为最左点（如果相同则为较靠上放的点
                // end 为最有点（如果相同则为较靠下方的点））
                Point start = p1;
                Point end = p1;
                Point[] points = {p2, p3, p4};
                for (int i = 0; i < points.length; i++) {
                    if (points[i].x < start.x || (points[i].x == start.x && points[i].y < start.y)) {
                        start = points[i];
                    }
                    else if (points[i].x > end.x || (points[i].x == end.x && points[i].y > end.y)) {
                        end = points[i];
                    }
                }
                return new Line(start, end);
            }
        }
    }

    class Solution1{
        public double[] cutSquares(int[] square1, int[] square2) {
            // 第一个正方形的中点
            double x1 = square1[0] + square1[2] / 2.0;
            double y1 = square1[1] + square1[2] / 2.0;
            int d1 = square1[2];
            // 第二个正方形的中点
            double x2 = (square2[0] + square2[2]) / 2.0;
            double y2 = (square2[1] + square2[2]) / 2.0;
            int d2 = square2[2];
            // 结果集
            double[] res = new double[4];
            System.out.println(x1 + " " + x2);
            System.out.println(x2 + " " + y2);
            // 两个中心在同一条x轴上，此时这两条直线的斜率都是无穷大
            if (x1 == x2) {
                res[0] = x1;
                res[1] = Math.min(square1[1], square2[1]);
                res[2] = x1;
                res[3] = Math.max(square1[1] + d1, square2[1] + d2);
            }
            else {
                // 斜率存在，则计算斜率和截距
                double slope = (y1 - y2) / (x1 - x2); // 斜率计算公式
                double b = y1 - slope * x1;
                System.out.println(slope + " " + b);
                // 斜率绝对值大于1，则说明与正方形的上边和下边相交
               if (Math.abs(slope) > 1) {
                    res[1] = Math.min(square1[1], square2[1]);
                    res[0] = (res[1] - b) / slope;
                    res[3] = Math.max(square1[1] + d1, square2[1] + d2);
                    res[2] = (res[3] - b) / slope;
                }
                else { // 斜率绝对值小于等于1，说明与正方形的左边和右边相交
                    res[0] = Math.min(square1[0], square2[0]);
                    res[1] = res[0] * slope + b;
                    res[2] = Math.max(square1[0] + d1, square2[0] + d2);
                    res[3] = res[2] * slope + b;
                }
            }

            // 题目要求，如果 x1 < x2，如果结果不满足，交换两个点的坐标即可
            if (res[0] > res[2]) {
                swap(res, 0, 2);
                swap(res, 1, 3);
            }
            return res;
        }

        private void swap(double[] arr, int i, int j) {
            double tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    @Test
    public void test() {
        Solution1 solution1 = new Solution1();
        double[] res = solution1.cutSquares(new int[]{-1, 1, 3}, new int[]{0, 0, 5});
        System.out.println(Arrays.toString(res));

    }

}
