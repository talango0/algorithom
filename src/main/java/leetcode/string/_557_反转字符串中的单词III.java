package leetcode.string;

import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class _557_反转字符串中的单词III {


    //给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
//
//
//
// 示例：
//
// 输入："Let's take LeetCode contest"
//输出："s'teL ekat edoCteeL tsetnoc"
//
//
//
//
// 提示：
//
//
// 在字符串中，每个单词由单个空格分隔，并且字符串中不会有任何额外的空格。
//
// Related Topics 字符串
// 👍 247 👎 0


    //leetcode submit region begin(Prohibit modification and deletion
     static class Solution {
        public static String reverseWords1(String s) {
            StringBuilder ret = new StringBuilder();
            int  i = 0, start, n = s.length();
            while (i<n){
                while (i<n && s.charAt(i) == ' '){
                    ret.append(s.charAt(i++));
                }
                //找到第一个不为 ' ' 的字符的起始位置
                start=i;
                //找到字符不为 ' ' 的索引下标
                while (i<n && s.charAt(i) != ' '){
                    i++;
                }
                //直接对s下标位于 [start, i) 之间的字符串从后往前加入至 ret
                for (int p = start; p<i; p++){
                    ret.append(s.charAt(start+i-p-1));
                }
            }
            return ret.toString();
        }


        /**
         * 由于题目限制每个单词只有一个空格，所以我们直接根据空格分割字符串，然后对每个单词反转拼接
         * @param str
         * @return
         */
        public static String reverseWords(String str){
            //判断字符串是否为null
            if(str == null){
                return str;
            }
            //常见一个StringBuilder用于组装Str，并执行reverse等操作；
            String ret = "";
            String[] split = str.split("\\s+");
            StringBuilder sb = new StringBuilder();
            for (String s : split) {
                ret += sb.append(s).reverse();
                ret += " ";
                //将sb清空
                sb.setLength(0);
            }

            //去掉字符串前后的空格
            ret.trim();
            return ret;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)


    public static void main(String[] args) {
        String str = "Let's take LeetCode contest";
        String res = Solution.reverseWords(str);

        System.out.println(res);
    }

    @Test
    public void test1(){
        String s = "hello world,   This is my fist;   ";
        String[] split = s.split("\\s+");

        System.out.println(Arrays.toString(split));

        System.out.println(" h sdf ".trim());
    }


}
