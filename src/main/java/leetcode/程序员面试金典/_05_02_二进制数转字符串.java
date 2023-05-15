package leetcode.程序员面试金典;
//二进制数转字符串。给定一个介于0和1之间的实数（如0.72），类型为double，打印它的二进制表达式。
// 如果该数字无法精确地用32位以内的二进制表示，则打印“ERROR”。
//
//示例1:
//
// 输入：0.625
// 输出："0.101"
//示例2:
//
// 输入：0.1
// 输出："ERROR"
// 提示：0.1无法被二进制准确表示
//
//
//提示：
//
//32位包括输出中的 "0." 这两位。
//题目保证输入用例的小数位数最多只有 6 位
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/binary-number-to-string-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
/**
 * @author mayanwei
 * @date 2023-05-15.
 */
public class _05_02_二进制数转字符串{
    /**
     * <pre>
     * ┌────────────────────────────┐
     * │             1      2      3│
     * │0.101 = 1*1/2 +0*1/2 +1*1/2 │
     * │     2                      │
     * └────────────────────────────┘
     * 为了打印小数部分，我们可以将这个数乘以2，检查2n是否大于或等于1.
     * 若 r>=1,可知n的小数点后面正好有个1。
     * ┌─────────────────────────┐
     * │ r = 2  *n               │
     * │      10                 │
     * │   = 2  *0.101           │
     * │      10      2          │
     * │          0      1      2│
     * │   = 1*1/2 +0*1/2 +1*1/2 │
     * │                         │
     * │   = 1.01                │
     * │         2               │
     * └─────────────────────────┘
     * </pre>
     */
    class Solution {
        public String printBin(double num) {
            if (num <= 0 || num >= 1) {
                return "ERROR";
            }
            StringBuilder binary = new StringBuilder();
            binary.append("0.");
            while (num > 0) {
                // 对长度设限：32个字符
                if (binary.length() >= 32) {
                    return "ERROR";
                }
                double r = num * 2;
                if (r >= 1) {
                    binary.append(1);
                    num = r - 1;
                } else {
                    binary.append(0);
                    num = r;
                }
            }
            return binary.toString();
        }
    }
}
