package leetcode.string;

import leetcode.backtracing._22_括号生成;

/**
 * @see _22_括号生成
 */
public class _394_字符串解码 {
    /**
     * 递归
     */
    class Solution {
        private int index;
        private int n;

        public String decodeString(String s) {
            if (s == null || s.length() == 0) {
                return "";
            }
            n = s.length();
            index = 0;
            return decodeStr(s);
        }

        private String decodeStr(String s) {
            if (index == n || s.charAt(index) == ']') {
                return "";
            }
            char c = s.charAt(index);
            StringBuilder ret = new StringBuilder();
            if (Character.isDigit(c)) {
                int repeatNum = 0;
                while (index < n && Character.isDigit(s.charAt(index))) {
                    repeatNum = repeatNum * 10 + (s.charAt(index++) - '0');
                }
                index++;
                String repeatStr = decodeStr(s);
                index++;
                while (repeatNum-- > 0) {
                    ret.append(repeatStr);
                }
            } else if (Character.isLetter(c)) {
                ret.append(s.charAt(index++));
            }
            return ret.append(decodeStr(s)).toString();
        }
    }
}
