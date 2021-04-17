package leetcode.jzhoffer;

/**
 * @author mayanwei
 */
public class JZ49把字符串转换成整数 {
    /*
题目描述
将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0
输入描述:
输入一个字符串,包括数字字母符号,可以为空
输出描述:
如果是合法的数值表达则返回该数字，否则返回0
示例1
输入

复制
+2147483647
1a33

输出
2147483647
0
     */

    public class Solution {


        /*
        链接：https://www.nowcoder.com/questionTerminal/1277c681251b4372bdef344468e4f26e?answerType=1&f=discussion
来源：牛客网

如何处理溢出情况是本题的难点，通过举一个例子来表达关键代码处的思路。
如果问题背景变成如下：正确的数字范围是-128~127，待分析字符串是-134和129。
1. 重新设置边界。当待分析字符串为负数时 limit = -128; 当待分析字符串为正数时 limit = -127。(注意待分析字符串为正数时，边界limit被故意定义为了负数)
2. 分析-134时，关键代码第一行中的判断条件 limit / 10 = -12;此时result = 0。
    （1）“-” ：此时result = 0; 确定是负数
    （2）"1”:  0 > -12  -----> 此时result = 0; 可以添加
    （3）"3”:  -1 > -12 ----->此时result = -1;  可以添加
    （4）"4”:  -13 < -12 ----> 此时result = -13; 不可以添加（数字有n位，若result的前n - 1位 < 边界的前n - 1位，则无论第n位加上什么，都会超出边界）
3. 分析129时，关键代码第一行中的判断条件 limit / 10 = -12;此时result = 0。
    （1）"1”:  0 > -12 ---> 此时result = 0; 可以添加
    （2）"2”:  -1 > -12 ---> 此时result = -1; 可以添加
    （3）"9”:  -12 == -12  &&  (result乘以十)-120 < (边界) -127 + (第n位) 9 ---> 此时result = -12;
     不可以添加（数字有n位，虽然前n - 1位都是正常的，但加上第n位的数可能超出边界）

         */
        public int StrToInt(String str) {
            if (str == null || str.length() == 0 || str == "+" || str == "-") {
                return 0;
            }
            int limit = -Integer.MAX_VALUE;
            int label = 1;
            int result = 0;
            for (int i = 0; i < str.length(); i++){
                if (i == 0 && ((str.charAt(i) == '-') || str.charAt(i) == '+')){  // 第一位如果是负数,"+"或者"-"
                    if (str.charAt(i) == '-'){  // 若是"-",则改变边界条件(limit)和符号位(label)
                        limit = Integer.MIN_VALUE;
                        label = -1;
                    }
                    continue;
                }else if (Character.isDigit(str.charAt(i))){//str.charAt(i) >= '0' && str.charAt(i) <= '9'
                    int num = str.charAt(i) - '0';
                    int temp = result * 10;
                    if (result >= (limit / 10) && temp >= (limit + num) ){  //关键在这一步
                        result = result * 10 - num;
                    }else {     // 数值溢出, 返回0
                        return 0;
                    }
                }else {     // 如果是非法字符, 返回0
                    return 0;
                }
            }
            return label > 0 ? -result: result;
        }
    }

    public static void main(String[] args) {
        int a =1;
//        System.out.println(-0x7fffffff/10);
//        int b = Character.digit('0', 10);
//
//        System.out.println(-127/10);
//        System.out.println(Integer.MIN_VALUE);
//

        int b = a;
        Integer.valueOf("256",10);
//        System.out.println(Integer.valueOf("-123"));
    }
}
