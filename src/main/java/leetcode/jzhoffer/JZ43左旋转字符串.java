package leetcode.jzhoffer;

public class JZ43左旋转字符串 {
    /*

    题目描述
汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。对于一个给定的字符序列S，
请你把其循环左移K位后的序列输出。
例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。是不是很简单？OK，搞定它！
     */

    public static class Solution {
        public String LeftRotateString(String str,int n) {
            if(str == null){
                return null;
            }
            if(str == ""){
                return "";
            }
            for(int i = 0; i<n; i++){
               str = str.substring(1,str.length())+str.substring(0,1);
            }
            return str;
        }
    }

    public static void main(String[] args) {
        String S="abcXYZdef";
        Solution solution = new Solution();

        System.out.println(solution.LeftRotateString(S, 3));
    }
}
