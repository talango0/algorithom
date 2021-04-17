package leetcode.jzhoffer;

import java.util.*;

/**
 * @author mayanwei
 */
public class JZ54字符流中第一个不重复的字符 {
    /**
     * 题目描述
     * 请实现一个函数用来找出字符流中 第一个只出现一次 的字符。例如，当从字符流中只读出前两个字符"go"时，第一个只出现一次的字符是"g"。
     * 当从该字符流中读出前六个字符“google"时，第一个只出现一次的字符是"l"。
     * 输出描述:
     * 如果当前字符流没有存在出现一次的字符，返回#字符。
     */



    public static class Solution {
        int [] counter = new int[128];
        private int cnt = 0;
        List<Character> buffer = new ArrayList<>();

        //Insert one char from stringstream
        public void Insert(char ch){
            counter[ch]++;
            buffer.add(ch);
        }
        //return the first appearence once char in current stringstream
        public char FirstAppearingOnce(){
            for (Character item : buffer) {
                if(counter[item] == 1){
                    return item;
                }
            }
            return '#';
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String input = "google";
        for(int i = 0;i<input.length();i++){
            solution.Insert(input.charAt(i));
            System.out.print(solution.FirstAppearingOnce());
        }

    }
}
