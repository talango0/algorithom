package leetcode.divide_conquer;
//给你一个由数字和运算符组成的字符串 expression ，按不同优先级组合数字和运算符，计算并返回所有可能组合的结果。你可以 按任意顺序 返回答案。
//
// 生成的测试用例满足其对应输出值符合 32 位整数范围，不同结果的数量不超过 10⁴ 。
//
//
//
// 示例 1：
//
//
//输入：expression = "2-1-1"
//输出：[0,2]
//解释：
//((2-1)-1) = 0
//(2-(1-1)) = 2
//
//
// 示例 2：
//
//
//输入：expression = "2*3-4*5"
//输出：[-34,-14,-10,-10,10]
//解释：
//(2*(3-(4*5))) = -34
//((2*3)-(4*5)) = -14
//((2*(3-4))*5) = -10
//(2*((3-4)*5)) = -10
//(((2*3)-4)*5) = 10
//
//
//
//
// 提示：
//
//
// 1 <= expression.length <= 20
// expression 由数字和算符 '+'、'-' 和 '*' 组成。
// 输入表达式中的所有整数值在范围 [0, 99]
//
//
// Related Topics 递归 记忆化搜索 数学 字符串 动态规划 👍 740 👎 0

import leetcode.arrays.MergeSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 解决该算法题利用了分治思想，以每个运算符作为分割点，把复杂问题分解成小的子问题，递归求解子问题，然后再通过子问题的结果计算出原问题的结果。
 * <p>
 * 把大规模的问题分解成小规模的问题递归求解，应该是计算机思维的精髓
 *
 * @see MergeSort
 * @author mayanwei
 * @date 2022-08-06.
 */
public class _241_为运算表达式设计优先级{
    class Solution{
        // 解决本题的关键：
        // 1. 不要思考整体，把目光聚焦局部，只看一个运算符
        // 2. 明确递归函数的定义是什么，相信并且利用递归函数的定义
        // 定义：计算算式 expression 所有可能的运算结果
        public List<Integer> diffWaysToCompute(String expression) {
            List<Integer> res = new LinkedList<>();
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                // 扫描算式中的运算符
                if (c == '-' || c == '*' || c == '+') {
                    /**分 */
                    // 以运算为中心，分割成两个字符串，分别递归计算
                    List<Integer> left = diffWaysToCompute(expression.substring(0, i));
                    List<Integer> right = diffWaysToCompute(expression.substring(i + 1));
                    /**治 */
                    // 通过子问题的结果，合成原问题的结果
                    for (int a : left) {
                        for (int b : right) {
                            if (c == '+') {
                                res.add(a + b);
                            }
                            else if (c == '-') {
                                res.add(a - b);
                            }
                            else if (c == '*') {
                                res.add(a * b);
                            }
                        }
                    }
                }
            }
            // base case
            // 如果 res 为空，说明算式是一个数字，没有运算符
            if (res.isEmpty()) {
                res.add(Integer.parseInt(expression));
            }
            return res;
        }
    }

    /**
     * 带备忘录优化
     */
    class Solution2{
        // 解决本题的关键：
        // 1. 不要思考整体，把目光聚焦局部，只看一个运算符
        // 2. 明确递归函数的定义是什么，相信并且利用递归函数的定义
        // 定义：计算算式 expression 所有可能的运算结果
        HashMap<String, List<Integer>> memo = new HashMap<>();

        public List<Integer> diffWaysToCompute(String expression) {
            // 避免重复计算
            if (memo.containsKey(expression)) {
                return memo.get(expression);
            }
            List<Integer> res = new LinkedList<>();
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                // 扫描算式中的运算符
                if (c == '-' || c == '*' || c == '+') {
                    /**分 */
                    // 以运算为中心，分割成两个字符串，分别递归计算
                    List<Integer> left = diffWaysToCompute(expression.substring(0, i));
                    List<Integer> right = diffWaysToCompute(expression.substring(i + 1));
                    /**治 */
                    // 通过子问题的结果，合成原问题的结果
                    for (int a : left) {
                        for (int b : right) {
                            if (c == '+') {
                                res.add(a + b);
                            }
                            else if (c == '-') {
                                res.add(a - b);
                            }
                            else if (c == '*') {
                                res.add(a * b);
                            }
                        }
                    }
                }
            }
            // base case
            // 如果 res 为空，说明算式是一个数字，没有运算符
            if (res.isEmpty()) {
                res.add(Integer.parseInt(expression));
            }
            // 将结果加进备忘录
            memo.put(expression, res);
            return res;
        }
    }
}
