package leetcode.程序员面试金典;
//给定一个整数，打印该整数的英文描述。
//
//示例 1:
//
//输入: 123
//输出: "One Hundred Twenty Three"
//示例 2:
//
//输入: 12345
//输出: "Twelve Thousand Three Hundred Forty Five"
//示例 3:
//
//输入: 1 234 567
//输出: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
//示例 4:
//
//输入: 1 234 567 891
//输出: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/english-int-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.recursion._273_整数转换英文表示;

/**
 * @author mayanwei
 * @date 2023-06-09.
 * @see _273_整数转换英文表示
 */
public class _16_08_整数的英语表示{
    /**
     * 思路：convert(19 323 984) 时，我们可以考虑分段处理，每3位转换一次，并在适合的地方插入 thousand，
     * million。如下所示：
     * convert(19,323,984) = convert(19) + "million" + convert(323) + "thousand"
     * +convert(984)
     */
    class Solution{
        String[] singles = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        String[] teens = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        String[] tens = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        String[] thousands = {"", "Thousand", "Million", "Billion"};

        public String numberToWords(int num) {
            if (num == 0) {
                return "Zero";
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 3, unit = 1000000000; i >= 0; i--, unit /= 1000) {
                int curNum = num / unit;
                if (curNum != 0) {
                    num -= curNum * unit;
                    sb.append(toEnglish(curNum)).append(thousands[i]).append(" ");
                }
            }
            return sb.toString().trim();
        }

        public String toEnglish(int num) {
            StringBuffer curr = new StringBuffer();
            int hundred = num / 100;
            num %= 100;
            if (hundred != 0) {
                curr.append(singles[hundred]).append(" Hundred ");
            }
            int ten = num / 10;
            if (ten >= 2) {
                curr.append(tens[ten]).append(" ");
                num %= 10;
            }
            if (num > 0 && num < 10) {
                curr.append(singles[num]).append(" ");
            }
            else if (num >= 10) {
                curr.append(teens[num - 10]).append(" ");
            }
            return curr.toString();
        }
    }
}
