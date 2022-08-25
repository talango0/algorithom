package leetcode.backtracing;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个长度为4的整数数组 cards 。你有 4 张卡片，每张卡片上都包含一个范围在 [1,9] 的数字。您应该使用运算符 ['+', '-', '*', '/'] 和括号 '(' 和 ')' 将这些卡片上的数字排列成数学表达式，以获得值24。
 * <p>
 * 你须遵守以下规则:
 * <p>
 * 除法运算符 '/' 表示实数除法，而不是整数除法。
 * <ul>
 * 	<li>例如， <code>4 /(1 - 2 / 3)= 4 /(1 / 3)= 12</code> 。</li>
 * </ul>
 * </li>
 * <li>每个运算都在两个数字之间。特别是，不能使用 <code>“-”</code> 作为一元运算符。
 * <ul>
 * 	<li>例如，如果 <code>cards =[1,1,1,1]</code> ，则表达式 <code>“-1 -1 -1 -1”</code> 是 <strong>不允许</strong> 的。</li>
 * </ul>
 * </li>
 * <li>你不能把数字串在一起
 * <ul>
 * 	<li>例如，如果 <code>cards =[1,2,1,2]</code> ，则表达式 <code>“12 + 12”</code> 无效。</li>
 * </ul>
 * </li>
 * 如果可以得到这样的表达式，其计算结果为 24 ，则返回 true ，否则返回 false 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: cards = [4, 1, 8, 7]
 * 输出: true
 * 解释: (8-4) * (7-1) = 24
 * 示例 2:
 * <p>
 * 输入: cards = [1, 2, 1, 2]
 * 输出: false
 * 提示:
 * <p>
 * cards.length == 4
 * 1 <= cards[i] <= 9
 * Related Topics
 * <p>
 * 👍 392, 👎 0
 * <p>
 *  @author mayanwei
 *  @date 2022-08-25.
 */

public class _679_24点游戏{

    // 回溯枚举 + 部分简直优化

    class Solution{
        private final int TARGET = 24;
        private final double EPSILON = 1e-6;
        private final int add = 0, multiply = 1, subtract = 2, divide = 3;

        public boolean judgePoint24(int[] cards) {
            List<Double> list = new ArrayList<Double>(cards.length);
            for (int card : cards) {
                list.add((double) card);
            }
            return solve(list);
        }

        public boolean solve(List<Double> list) {
            // 退出条件
            if (list.size() == 0) {
                return false;
            }
            if (list.size() == 1) {
                return Math.abs(list.get(0) - TARGET) < EPSILON;
            }

            int size = list.size();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // list里面挑2个数做运算，运算结果放在list2
                    if (i != j) {
                        List<Double> list2 = new ArrayList<>();
                        for (int k = 0; k < size; k++) {
                            if (k != i && k != j) {
                                list2.add(list.get(k));
                            }
                        }
                        for (int k = 0; k < 4; k++) {
                            // 作出选择
                            if (k < 2 && i > j) {
                                continue;
                            }
                            if (k == add) {
                                list2.add(list.get(i) + list.get(j));
                            }
                            else if (k == multiply) {
                                list2.add(list.get(i) * list.get(j));
                            }
                            else if (k == subtract) {
                                list2.add(list.get(i) - list.get(j));
                            }
                            else if (k == divide) {
                                if (list.get(i) == 0) {
                                    continue;
                                }
                                else {
                                    list2.add(list.get(i) / list.get(j));
                                }
                            }
                            if (solve(list2)) {
                                return true;
                            }

                            // 撤销选择
                            list2.remove(list2.size() - 1);
                        }
                    }
                }
            }
            return false;
        }
    }

    @Test
    public void test(){
        int[] data = {4, 1, 2, 3};
        boolean res = true;
        Solution solution = new Solution();
        Assert.assertEquals(solution.judgePoint24(data), res);
    }
}
