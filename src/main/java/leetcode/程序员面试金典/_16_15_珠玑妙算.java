package leetcode.程序员面试金典;
//珠玑妙算游戏（the game of master mind）的玩法如下。
//
//计算机有4个槽，每个槽放一个球，颜色可能是红色（R）、黄色（Y）、绿色（G）或蓝色（B）。例如，计算机可能有RGGB 4种
// （槽1为红色，槽2、3为绿色，槽4为蓝色）。作为用户，你试图猜出颜色组合。打个比方，你可能会猜YRGB。要是猜对某个槽的颜色，
// 则算一次“猜中”；要是只猜对颜色但槽位猜错了，则算一次“伪猜中”。注意，“猜中”不能算入“伪猜中”。
//
//给定一种颜色组合solution和一个猜测guess，编写一个方法，返回猜中和伪猜中的次数answer，其中answer[0]为猜中的次数，answer[1]为伪猜中的次数。
//
//示例：
//
//输入： solution="RGBY",guess="GGRR"
//输出： [1,1]
//解释： 猜中1次，伪猜中1次。
//提示：
//
//len(solution) = len(guess) = 4
//solution和guess仅包含"R","G","B","Y"这4种字符
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/master-mind-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-06-11.
 */
public class _16_15_珠玑妙算{
    class Solution{
        int MAX_COLOR = 4;

        public int[] masterMind(String solution, String guess) {
            if (guess.length() != solution.length()) {
                return null;
            }
            Result res = new Result();
            int[] frequencies = new int[MAX_COLOR];
            // 计算猜中的次数并构造频率表
            for (int i = 0; i < guess.length(); i++) {
                if (guess.charAt(i) == solution.charAt(i)) {
                    res.hits++;
                }
                else {
                    // 如果没有猜中，则只对频率加1（频率表示伪猜中）
                    // 如果没有猜中，那么该位置应该一别使用
                    int code = code(solution.charAt(i));
                    if (code >= 0) {
                        frequencies[code]++;
                    }

                }
            }
            // 计算未猜中
            for (int i = 0; i < guess.length(); i++) {
                int code = code(guess.charAt(i));

                if (code >= 0 && frequencies[code] > 0 && guess.charAt(i) != solution.charAt(i)) {
                    res.pseudoHits++;
                    frequencies[code]--;
                }
            }
            return new int[]{res.hits, res.pseudoHits};
        }

        class Result{
            int hits = 0;
            int pseudoHits = 0;
        }

        int code(char c) {
            switch (c){
                case 'B':
                    return 0;
                case 'G':
                    return 1;
                case 'Y':
                    return 2;
                case 'R':
                    return 3;
                default:
                    return -1;
            }
        }
    }

    @Test
    public void test() {
        Solution solution = new Solution();
        int[] res = solution.masterMind("RGYG", "RYGG");
        System.out.println(Arrays.toString(res));
    }
}
