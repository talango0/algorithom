# //给你一个数组 rectangles ，其中 rectangles[i] = [xi, yi, ai, bi] 表示一个坐标轴平行的矩形。这个矩形的左下顶点是
# // (xi, yi) ，右上顶点是 (ai, bi) 。
# //
# // 如果所有矩形一起精确覆盖了某个矩形区域，则返回 true ；否则，返回 false 。
# //
# // 示例 1：
# //
# //
# //输入：rectangles = [[1,1,3,3],[3,1,4,2],[3,2,4,4],[1,3,2,4],[2,3,3,4]]
# //输出：true
# //解释：5 个矩形一起可以精确地覆盖一个矩形区域。
# //
# //
# // 示例 2：
# //
# //
# //输入：rectangles = [[1,1,2,3],[1,3,2,4],[3,1,4,2],[3,2,4,4]]
# //输出：false
# //解释：两个矩形之间有间隔，无法覆盖成一个矩形。
# //
# // 示例 3：
# //
# //
# //输入：rectangles = [[1,1,3,3],[3,1,4,2],[1,3,2,4],[2,2,4,4]]
# //输出：false
# //解释：因为中间有相交区域，虽然形成了矩形，但不是精确覆盖。
# //
# //
# //
# // 提示：
# //
# //
# // 1 <= rectangles.length <= 2 * 10⁴
# // rectangles[i].length == 4
# // -10⁵ <= xi, yi, ai, bi <= 10⁵
# //
# //
# // Related Topics 数组 扫描线 👍 229 👎 0

class Solution:
    # 从面积和顶点两个角度考虑
    # 从顶点的角度看，当某一个点同时时2个或者4个小矩形的顶点时，该点最终不是顶点；当某一个点同时时1个或者3个小矩形的顶点时，该点最终时一个顶点。
    # points 集合中最后应该只有 4 个顶点对吧，如果 len(points) != 4 说明最终构成的图形肯定不是完美矩形
    # 但是如果 len(points) == 4 是否能说明最终构成的图形肯定是完美矩形呢？也不行，因为题目并没有说 rectangles 中的小矩形不存在重复
    def isRectangleCover(self, rectangles: List[List[int]]) -> bool:
        X1, Y1 = float('inf'), float('inf')
        X2, Y2 = -float('inf'), -float('inf')
        # 记录所有矩形的面积之和
        actual_area = 0
        # 哈希集合，记录最终图形的顶点
        points = set()
        for x1, y1, x2, y2 in rectangles:
            # 计算完美矩形的理论坐标
            X1, Y1 = min(x1, X1), min(y1, Y1)
            X2, Y2 = max(x2, X2), max(y2, Y2)
            # 累加所有小矩形的面积
            actual_area += (x2 - x1) * (y2 - y1)
            # 先计算出小矩形每个点的坐标
            p1, p2 = (x1, y1), (x1, y2)
            p3, p4 = (x2, y1), (x2, y2)
            # 对于每个点，如果存在集合中，删除它
            # 如果不存在集合中，添加它
            # 在集合中剩下的点都是出现奇次的点
            for p in [p1, p2, p3, p4]:
                if p in points:
                    points.remove(p)
                else:
                    points.add(p)
        # 计算完美矩形的理论面积
        expect_area = (X2 - X1) * (Y2 - Y1)
        # 面积应该相等
        if expect_area != actual_area:
            return False

        # 判断最终留下的顶点个数是否为4
        if len(points) is not 4:
            return False
        # 判断留下的 4 个顶点是否是完美矩形的顶点
        if (X1, Y1) not in points \
                or (X1, Y2) not in points \
                or (X2, Y1) not in points \
                or (X2, Y2) not in points:
            return False

        return True