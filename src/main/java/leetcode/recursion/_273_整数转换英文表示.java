package leetcode.recursion;
//将非负整数 num 转换为其对应的英文表示。
//
//
//
// 示例 1：
//
//
//输入：num = 123
//输出："One Hundred Twenty Three"
//
//
// 示例 2：
//
//
//输入：num = 12345
//输出："Twelve Thousand Three Hundred Forty Five"
//
//
// 示例 3：
//
//
//输入：num = 1234567
//输出："One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
//
//
//
//
// 提示：
//
//
// 0 <= num <= 2³¹ - 1
//
//
// Related Topics 递归 数学 字符串 👍 291 👎 0


/**
 * @author mayanwei
 * @date 2022-09-19.
 */
public class _273_整数转换英文表示{
    /**
     * 非负整数num = 2^31 - 1 最多10位
     */
    class Solution{
        // 小于20 的数可以直接得到其英文
        // 大于大于20且小于100的数，首先将十位转换成英文表示，然后堆各位递归第转换成英文表示
        // 大于等于100的数首先将百位转换成英文表示，然后对其余部分（十位和个位）递归转换成阴文表示
        String[] singles = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        String[] teens = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

        String[] tens = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        String[] thousands = {"", "Thousand", "Million", "Billion"};

        //时间复杂度：O(1)
        //空间复杂度：O(1)
        public String numberToWords(int num) {
            if (num == 0) {
                return "Zero";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 3, unit = 1000000000; i >= 0; i--, unit /= 1000) {
                int curNum = num / unit;
                if (curNum != 0) {
                    num -= curNum * unit;
                    StringBuilder curr = new StringBuilder();
                    recursion(curr, curNum);
                    curr.append(thousands[i]).append(" ");
                    sb.append(curr);
                }
            }
            return sb.toString().trim();
        }

        public void recursion(StringBuilder curr, int num) {
            if (num == 0) {
                return;
            }
            else if (num < 10) {
                curr.append(singles[num]).append(" ");
            }
            else if (num < 20) {
                curr.append(teens[num - 10]).append(" ");
            }
            else if (num < 100) {
                curr.append(tens[num / 10]).append(" ");
                recursion(curr, num % 10);
            }
            else {
                curr.append(singles[num / 100]).append(" Hundred ");
                recursion(curr, num % 100);
            }

        }
    }


    /**
     * 迭代方式
     */
    class Solution1 {
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
            } else if (num >= 10) {
                curr.append(teens[num - 10]).append(" ");
            }
            return curr.toString();
        }
    }
}
