package leetcode.程序员面试金典;
//给定一个布尔表达式和一个期望的布尔结果 result，布尔表达式由 0 (false)、1 (true)、& (AND)、 | (OR) 和 ^ (XOR) 符号组成。实现一个函数，
// 算出有几种可使该表达式得出 result 值的括号方法。
//
//示例 1:
//
//输入: s = "1^0|0|1", result = 0
//
//输出: 2
//解释:两种可能的括号方法是
//1^(0|(0|1))
//1^((0|0)|1)
//示例 2:
//
//输入: s = "0&0&0&1^1|0", result = 1
//
//输出: 10
//提示：
//
//运算符的数量不超过 19 个
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/boolean-evaluation-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import edu.princeton.cs.algs4.In;
import leetcode.jzhoffer.判断一个数组是否是二叉搜索数遍历的结果;

import java.util.HashMap;

/**
 * @author mayanwei
 * @date 2023-05-29.
 */
public class _08_14_布尔运算{

    class Solution {
        public class Info{
            public int t;// 为T的方法数
            public int f;// 为F的方法数

            public Info(int t, int f) {
                this.t = t;
                this.f = f;
            }
        }
        public int countEval(String s,int result) {
            int N = s.length();
            Info[][] dp = new Info[N][N];
            Info allInfo = func2(s.toCharArray(), 0, N-1, dp);
            return result == 0 ? allInfo.f : allInfo.t;
        }

        private Info func2(char[] chs, int L, int R, Info[][] dp) {
            if (dp[L][R] != null) {
                return dp[L][R];
            }
            int t = 0;
            int f = 0;
            if (L == R) {
                t = chs[L] == '1' ? 1 : 0; // 只有一个字符，那么只要
                f = chs[L] == '0' ? 1 : 0; //同理
            }
            else { // 一定是 R-L >= 3 的情况
                // split 表示符号位索引
                for (int split = L+1; split < R; split+=2) {
                    // 表示符号位左边的信息获取到的情况
                    Info leftInfo = func2(chs, L, split-1, dp);
                    // 右边的情况
                    Info rightInfo = func2(chs, split+1, R, dp);
                    int a = leftInfo.t, b = leftInfo.f;
                    int c = rightInfo.t, d = rightInfo.f;
                    switch (chs[split]) {
                        case '&':
                            // T*T
                            t += a*c;
                            // T*F，F*T,F*F
                            f += a*d + a*d + b*d;
                            break;
                        case '|':
                            // T*T,T*F,F*T
                            t += a*c + a*d + b*c;
                            // F*F
                            f += b*d;
                            break;
                        case '^':
                            // T*F
                            t += a*d + b*c;
                            // T*T,F*F
                            f += a*c + b*d;
                            break;
                    }

                }
            }
            Info info = new Info(t,f);
            dp[L][R] = info;
            return info;
        }


    }

    class Solution1{
        int countEval(String s, boolean result) {
            if (s.length() == 0) {
                return 0;
            }
            if (s.length() == 1) {
                return stringToBool(s) == result ? 1 :0;
            }
            int ways = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                String left = s.substring(0, i);
                String right = s.substring(i + 1, s.length());
                // 分别计算每一边的每种结果
                int leftTrue = countEval(left, true);
                int leftFalse = countEval(left, false);
                int rightTrue = countEval(right, true);
                int rightFalse = countEval(right, false);
                int total = (leftTrue + leftFalse) * (rightTrue + rightFalse);

                int totalTrue = 0;
                switch (c){
                    case '^': // 需要一个真和一个假
                        totalTrue = leftTrue * rightFalse + leftFalse * rightTrue;
                        break;
                    case '&': // 需要同时为真
                        totalTrue = leftTrue * rightTrue;
                        break;
                    case '|': // 需要不同时为假
                        totalTrue = leftTrue * rightTrue + leftFalse * rightTrue
                                + leftTrue * rightFalse;
                        break;
                }
                int subWays = result ? totalTrue :total - totalTrue;
                ways += subWays;
            }
            return ways;
        }

        private boolean stringToBool(String s) {
            return s.equals("1") ? true :false;
        }
    }

    // 上述解法，要提前计算 leftTure,leftFalse,rightTrue,rightFalse，在某些情况下属于额外工作
    // & 为 true，我们用不到 leftFalse,rightFalse 的结果
    // ｜为 false,我们用不到 leftTrue,rightTrue 的结果。
    // 另外，我们发现上面的递归吧相同的计算重复了很多遍
    //  0^0&0^1|1
    // 第1个字符周围添加括号， (0)^((0&0^1|1))
    //  第3个字符周围添加括号，(0)^((0)&(0^1|1))
    // 第3个字符周围添加括号,  (0^0)&(0^1|1)
    //  第1个字符周围添加括号, ((0)^(0))&(0^1|1)
    // 虽然这两个表达式不同，但有其相同的部分:(0^1|1)
    class Solution2{
        int countEval(String s, boolean result, HashMap<String, Integer> memo) {
            if (s.length() == 0) {
                return 0;
            }
            if (s.length() == 1) {
                return stringToBool(s) == result ? 1 :0;
            }
            if (memo.containsKey(result + s)) {
                return memo.get(result + s);
            }
            int ways = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                String left = s.substring(0, i);
                String right = s.substring(i + 1, s.length());
                // 分别计算每一边的每种结果
                int leftTrue = countEval(left, true, memo);
                int leftFalse = countEval(left, false, memo);
                int rightTrue = countEval(right, true, memo);
                int rightFalse = countEval(right, false, memo);
                int total = (leftTrue + leftFalse) * (rightTrue + rightFalse);

                int totalTrue = 0;
                switch (c){
                    case '^': // 需要一个真和一个假
                        totalTrue = leftTrue * rightFalse + leftFalse * rightTrue;
                        break;
                    case '&': // 需要同时为真
                        totalTrue = leftTrue * rightTrue;
                        break;
                    case '|': // 需要不同时为假
                        totalTrue = leftTrue * rightTrue + leftFalse * rightTrue
                                + leftTrue * rightFalse;
                        break;
                }
                int subWays = result ? totalTrue :total - totalTrue;
                ways += subWays;
            }
            memo.put(result + s, ways);
            return ways;
        }

        private boolean stringToBool(String s) {
            return s.equals("1") ? true :false;
        }
    }


}
