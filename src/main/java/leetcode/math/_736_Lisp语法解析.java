package leetcode.math;
//给你一个类似 Lisp 语句的字符串表达式 expression，求出其计算结果。
//
// 表达式语法如下所示:
//
//
// 表达式可以为整数，let 表达式，add 表达式，mult 表达式，或赋值的变量。表达式的结果总是一个整数。
// (整数可以是正整数、负整数、0)
// let 表达式采用 "(let v1 e1 v2 e2 ... vn en expr)" 的形式，其中 let 总是以字符串 "let"来表示，接下来会跟
//随一对或多对交替的变量和表达式，也就是说，第一个变量 v1被分配为表达式 e1 的值，第二个变量 v2 被分配为表达式 e2 的值，依次类推；最终 let 表达
//式的值为 expr表达式的值。
// add 表达式表示为 "(add e1 e2)" ，其中 add 总是以字符串 "add" 来表示，该表达式总是包含两个表达式 e1、e2 ，最终结果是
//e1 表达式的值与 e2 表达式的值之 和 。
// mult 表达式表示为 "(mult e1 e2)" ，其中 mult 总是以字符串 "mult" 表示，该表达式总是包含两个表达式 e1、e2，最终结果
//是 e1 表达式的值与 e2 表达式的值之 积 。
// 在该题目中，变量名以小写字符开始，之后跟随 0 个或多个小写字符或数字。为了方便，"add" ，"let" ，"mult" 会被定义为 "关键字" ，不会
//用作变量名。
// 最后，要说一下作用域的概念。计算变量名所对应的表达式时，在计算上下文中，首先检查最内层作用域（按括号计），然后按顺序依次检查外部作用域。测试用例中每一个表
//达式都是合法的。有关作用域的更多详细信息，请参阅示例。
//
//
// 示例 1：
//
//
//输入：expression = "(let x 2 (mult x (let x 3 y 4 (add x y))))"
//输出：14
//解释：
//计算表达式 (add x y), 在检查变量 x 值时，
//在变量的上下文中由最内层作用域依次向外检查。
//首先找到 x = 3, 所以此处的 x 值是 3 。
//
//
// 示例 2：
//
//
//输入：expression = "(let x 3 x 2 x)"
//输出：2
//解释：let 语句中的赋值运算按顺序处理即可。
//
//
// 示例 3：
//
//
//输入：expression = "(let x 1 y 2 x (add x y) (add x y))"
//输出：5
//解释：
//第一个 (add x y) 计算结果是 3，并且将此值赋给了 x 。
//第二个 (add x y) 计算结果是 3 + 2 = 5 。
//
//
//
//
// 提示：
//
//
// 1 <= expression.length <= 2000
// exprssion 中不含前导和尾随空格
// expressoin 中的不同部分（token）之间用单个空格进行分隔
// 答案和所有中间计算结果都符合 32-bit 整数范围
// 测试用例中的表达式均为合法的且最终结果为整数
//
//
// Related Topics 栈 递归 哈希表 字符串 👍 172 👎 0


import java.util.*;

/**
 * @author mayanwei
 * @date 2022-09-21.
 */
public class _736_Lisp语法解析{

    class Solution{
        // 使用 scope 来记录作用域，每个遍历那个都一次记录下它从外到内的所有值，查找时只需要查找最后一个树枝
        Map<String, Deque<Integer>> scope = new HashMap<String, Deque<Integer>>();
        int start = 0;

        public int evaluate(String expression) {
            return innerEvaluate(expression);
        }

        public int innerEvaluate(String expression) {
            // 非表达式，可能为整数或者变量
            if (expression.charAt(start) != '(') {
                if (Character.isLowerCase(expression.charAt(start))) {
                    // 变量
                    String var = parseVar(expression);
                    return scope.get(var).peek();
                }
                else {
                    // 整数
                    return parseInt(expression);
                }
            }
            int ret;
            start++;
            // "let 表达式"
            if (expression.charAt(start) == 'l') {
                // 移除 "let "
                start += 4;
                List<String> vars = new ArrayList<String>();
                while (true) {
                    // let 表达式的最后一个 expr 表达式的值
                    if (!Character.isLowerCase(expression.charAt(start))) {
                        ret = innerEvaluate(expression);
                        break;
                    }
                    String var = parseVar(expression);
                    if (expression.charAt(start) == ')') {
                        // let 表达式的最后一个 expr 表达式的值
                        ret = scope.get(var).peek();
                        break;
                    }
                    vars.add(var);
                    // 移除空格
                    start++;
                    int e = innerEvaluate(expression);
                    scope.putIfAbsent(var, new ArrayDeque<Integer>());
                    scope.get(var).push(e);
                    // 移除空格
                    start++;
                }
                for (String var : vars) {
                    // 清除当时的作用域的变量
                    scope.get(var).pop();
                }

            }
            // "add 表达式"
            else if (expression.charAt(start) == 'a') {
                // 移除 "add "
                start += 4;
                int e1 = innerEvaluate(expression);
                // 空格
                start++;
                int e2 = innerEvaluate(expression);
                ret = e1 + e2;
            }
            // "mult 表达式"
            else {
                // 移除 "mult "
                start += 5;
                int e1 = innerEvaluate(expression);
                // 空格
                start++;
                int e2 = innerEvaluate(expression);
                ret = e1 * e2;
            }
            start++;
            return ret;
        }

        // 解析变量
        String parseVar(String expression) {
            int n = expression.length();
            StringBuffer ret = new StringBuffer();
            while (start < n && expression.charAt(start) != ' ' && expression.charAt(start) != ')') {
                ret.append(expression.charAt(start));
                start++;
            }
            return ret.toString();
        }

        // 解析整数
        int parseInt(String expression) {
            int n = expression.length();
            int ret = 0, sign = 1;
            if (expression.charAt(start) == '-') {
                sign = -1;
                start++;
            }
            while (start < n && Character.isDigit(expression.charAt(start))) {
                ret = ret * 10 + (expression.charAt(start) - '0');
                start++;
            }
            return sign * ret;
        }
    }
}
