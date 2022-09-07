package leetcode.stack;

import java.util.Stack;
//给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
//
//
//
// 示例 1：
//
//
//输入：s = "bcabc"
//输出："abc"
//
//
// 示例 2：
//
//
//输入：s = "cbacdcbc"
//输出："acdb"
//
//
//
// 提示：
//
//
// 1 <= s.length <= 10⁴
// s 由小写英文字母组成
//
//
//
//
// 注意：该题与 1081 https://leetcode-cn.com/problems/smallest-subsequence-of-
//distinct-characters 相同
//
// Related Topics 栈 贪心 字符串 单调栈 👍 807 👎 0

/**
 * @author mayanwei
 * @date 2022-07-02.
 * 单调栈
 * @see _1081_不同字符的最小子序列
 *
 */
public class _316_去除重复字符串{
    class Solution {
        public String removeDuplicateLetters(String s) {
            //存放去重的结果
            Stack<Character> stk = new Stack<>();
            //布尔数组初始值为 false ，记录栈中是否存在某个字符
            //输入字符均为 ASCII 字符，所以大小265够用了
            boolean [] inStack = new boolean[256];
            int [] count = new int[256];
            char [] chars = s.toCharArray();

            for(char c:chars) {
                count[c]++;
            }
            for (char c:chars) {
                count[c] -- ;
                //如果c在栈中，直接跳过
                if (inStack[c]) {
                    continue;
                }
                while (!stk.isEmpty() && stk.peek() > c){
                    if(count[stk.peek()] == 0){
                        break;
                    }
                    inStack[stk.pop()] = false;
                }
                //若不存在，则插入栈顶并标记为存在。
                stk.push(c);
                inStack[c] = true;
            }
            StringBuilder sb = new StringBuilder();
            while (!stk.empty()) {
                sb.append(stk.pop());
            }
            //栈中元素插入顺序是反的，需要 reverse 一下
            return sb.reverse().toString();
        }
    }
}
