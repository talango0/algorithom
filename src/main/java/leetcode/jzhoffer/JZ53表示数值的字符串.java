package leetcode.jzhoffer;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class JZ53表示数值的字符串 {

    /**
     * 题目描述
     * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
     * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
     */
    public class Solution {
        /**
         * 方法1
         * @param str
         * @return
         */
        public boolean isNumeric(char[] str) {
            //1.  ^ 和 $ 框定正则表达式，它表示这个正则表达式对文本中的所有字符都进行匹配。如果省略这些标识，那么只要一个字符串中包含一个
            //数字这个正则表达
            String pattern = "^[-+]?\\d*(?:\\.\\d*)?(?:[eE][+\\-]?\\d+)?$";
            String s = new String(str);
            return Pattern.matches(pattern,s);
        }

        /**
         * 方法2
         */
        public boolean isNumeric2(char[] str) {
            boolean point = false, exp = false; // 标志小数点和指数

            for (int i = 0; i < str.length; i++) {
                if (str[i] == '+' || str[i] == '-') {
                    // +-号后面必定为数字 或 后面为.（-.123 = -0.123）
                    if (i + 1 == str.length || !(str[i + 1] >= '0' && str[i + 1] <= '9' || str[i + 1] == '.')) {
                        return false;
                    }
                    // +-号只出现在第一位或eE的后一位
                    if (!(i == 0 || str[i-1] == 'e' || str[i-1] == 'E')) {
                        return false;
                    }


                } else if (str[i] == '.') {
                    // .后面必定为数字 或为最后一位（233. = 233.0）
                    if (point || exp
                            || !(i + 1 < str.length && str[i + 1] >= '0' && str[i + 1] <= '9')) {
                        return false;
                    }
                    point = true;

                } else if (str[i] == 'e' || str[i] == 'E') {
                    // eE后面必定为数字或+-号
                    if (exp || i + 1 == str.length
                            || !(str[i + 1] >= '0' && str[i + 1] <= '9'
                            || str[i + 1] == '+'
                            || str[i + 1] == '-')) {
                        return false;
                    }
                    exp = true;

                } else if (str[i] >= '0' && str[i] <= '9') {


                } else {
                    return false;
                }

            }
            return true;
        }

        /**
         * 方法3
         */
        public boolean isNumeric3(char[] str) {
            String num=new String(str);
            try{
                BigDecimal bd=new BigDecimal(num);
            }catch(Exception e){
                return false;
            }
            return true;

        }
    }

    public static void main(String[] args) {

    }
}
