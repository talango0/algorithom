package leetcode.stack;

import java.util.Stack;
//返回 s 字典序最小的子序列，该子序列包含 s 的所有不同字符，且只包含一次。
//
// 注意：该题与 316 https://leetcode.com/problems/remove-duplicate-letters/ 相同
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
// 1 <= s.length <= 1000
// s 由小写英文字母组成
//
//
// Related Topics 栈 贪心 字符串 单调栈 👍 162 👎 0

/**
 * @author mayanwei
 * @date 2022-07-02.
 */
public class _1081_不同字符的最小子序列{
    class Solution {
        public String smallestSubsequence(String s) {
            //存放去重的结果
            Stack<Character> stk = new Stack<>();
            //布尔数组初始值为 falsefalse ，记录栈中是否存在某个字符
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
