package leetcode.程序员面试金典;
//堆箱子。给你一堆n个箱子，箱子宽 wi、深 di、高 hi。箱子不能翻转，将箱子堆起来时，下面箱子的宽度、高度和深度必须大于上面的箱子。
// 实现一种方法，搭出最高的一堆箱子。箱堆的高度为每个箱子高度的总和。
//
//输入使用数组[wi, di, hi]表示每个箱子。
//
//示例1:
//
// 输入：box = [[1, 1, 1], [2, 2, 2], [3, 3, 3]]
// 输出：6
//示例2:
//
// 输入：box = [[1, 1, 1], [2, 3, 4], [2, 6, 7], [3, 4, 5]]
// 输出：10
//提示:
//
//箱子的数目不大于3000个。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/pile-box-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author mayanwei
 * @date 2023-05-29.
 */
public class _08_13_堆箱子{
    //思考：b1,b2,...,bn这些箱子，能够堆出的最高箱堆等于max
    // (底部为 b1 的最高箱堆，底部位 b2 的最高箱堆... 底部位 bn 的最高箱堆)
    // 也就是说，只要试着用每个箱子的箱堆底部并打出可能的最高高度。就能找到箱堆的最高高度。
    // 怎样找出一某个箱子为第的最高箱堆呢？具体做法与之前的完全相同，我们会递归地在第二层
    // 以不同的箱子位底继续堆箱子。如此反复。
    class Box{
        int h;
        int x;
        int y;

        public boolean canBeAbove(Box bottom) {
            return bottom.h > this.h && bottom.x > this.x && bottom.y > this.y;
        }
    }

    class Solution1{
        int createStack(ArrayList<Box> boxes) {
            // 将于高度将序排序
            Collections.sort(boxes, new Comparator<Box>(){
                @Override
                public int compare(Box o1, Box o2) {
                    return o1.h < o2.h ? 1 :(o1.h == o2.h ? 0 :-1);
                }
            });

            int maxHeight = 0;
            for (int i = 0; i < boxes.size(); i++) {
                int height = createStack(boxes, i);
                maxHeight = Math.max(maxHeight, height);
            }
            return maxHeight;
        }

        int createStack(ArrayList<Box> boxes, int bottomIndex) {
            Box bottom = boxes.get(bottomIndex);
            int maxHeight = 0;
            for (int i = bottomIndex + 1; i < boxes.size(); i++) {
                if (boxes.get(i).canBeAbove(bottom)) {
                    int height = createStack(boxes, i);
                    maxHeight = Math.max(height, maxHeight);
                }
            }
            maxHeight += bottom.h;
            return maxHeight;
        }
    }

    // 上述代码的问题是效率太低，我们可能已经找出了以b4 为底的最优解，但还是尝试找到类似
    // {b3, b4, ...} 的最佳解决方案。
    // 可以采用备忘录缓存结果。
    class Solution2{
        int createStack(ArrayList<Box> boxes) {
            // 将于高度降序排序
            Collections.sort(boxes, new Comparator<Box>(){
                @Override
                public int compare(Box o1, Box o2) {
                    return o1.h < o2.h ? 1 :(o1.h == o2.h ? 0 :-1);
                }
            });
            int maxHeight = 0;
            int[] stackMap = new int[boxes.size()];
            for (int i = 0; i < boxes.size(); i++) {
                int height = createStack(boxes, i, stackMap);
                maxHeight = Math.max(height, maxHeight);
            }
            return maxHeight;

        }

        private int createStack(ArrayList<Box> boxes, int bottomIndex, int[] stackMap) {
            if (bottomIndex < boxes.size() && stackMap[bottomIndex] > 0) {
                return stackMap[bottomIndex];
            }
            Box bottom = boxes.get(bottomIndex);
            int maxHeight = 0;
            for (int i = bottomIndex + 1; i < boxes.size(); i++) {
                if (boxes.get(i).canBeAbove(bottom)) {
                    int height = createStack(boxes, i, stackMap);
                    maxHeight = Math.max(height, maxHeight);
                }
            }
            maxHeight += bottom.h;
            stackMap[bottomIndex] = maxHeight;
            return maxHeight;
        }
    }

    class Solution{
        public int pileBox(int[][] box) {
            Arrays.sort(box, (o1, o2) -> {
                return o2[2] - o1[2];
            });
            int maxHeight = 0;
            int[] stackMap = new int[box.length];
            for (int i = 0; i < box.length; i++) {
                int height = createStack(box, i, stackMap);
                maxHeight = Math.max(maxHeight, height);
            }
            return maxHeight;
        }

        private int createStack(int[][] box, int bottomIndex, int[] stackMap) {
            if (bottomIndex < box.length && stackMap[bottomIndex] > 0) {
                return stackMap[bottomIndex];
            }
            int[] bottom = box[bottomIndex];
            int maxHeight = 0;
            for (int i = bottomIndex + 1; i < box.length; i++) {
                if (canBeAbove(box[i], bottom)) {
                    int height = createStack(box, i, stackMap);
                    maxHeight = Math.max(maxHeight, height);
                }
            }
            maxHeight += bottom[2];
            stackMap[bottomIndex] = maxHeight;
            return maxHeight;
        }

        // box 能否放在bottom 上面
        boolean canBeAbove(int[] box, int[] bottom) {
            return box[0] < bottom[0] && box[1] < bottom[1] && box[2] < bottom[2];
        }
    }
}

class Solution2{
    public int pileBox(int[][] box) {
        Arrays.sort(box, (a, b) -> {
            if (a[0] == b[0]) {
                if (a[1] == b[1]) {
                    return b[2] - a[2];
                }
                else {
                    return b[1] - a[1];
                }
            }
            return a[0] - b[0];
        });
        int ans = box[0][2];
        int n = box.length;
        int[] dp = new int[n];
        dp[0] = box[0][2];
        for (int i = 1; i < n; i++) {
            dp[i] = box[i][2];
            for (int j = 0; j < i; j++) {
                if (box[j][1] < box[i][1] && box[j][2] < box[i][2]) {
                    dp[i] = Math.max(dp[i], dp[j] + box[i][2]);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
