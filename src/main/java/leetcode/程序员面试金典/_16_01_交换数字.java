package leetcode.程序员面试金典;
//编写一个函数，不用临时变量，直接交换numbers = [a, b]中a与b的值。
//
//示例：
//
//输入: numbers = [1,2]
//输出: [2,1]
//提示：
//
//numbers.length == 2
//-2147483647 <= numbers[i] <= 2147483647
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/swap-numbers-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
/**
 * @author mayanwei
 * @date 2023-06-06.
 */
public class _16_01_交换数字{
    /**
     * <pre>
     * ┌─────────────────────────────┐
     * │         ─          ──       │
     * │  ─────────────────────────▶ │
     * │  0      b   diff    a       │
     * │                             │
     * │    a = 9, b = 4             │
     * │ a = a - b; // a = 9-4 = 5   │
     * │ b = a + b; // b = 5+4 = 9   │
     * │ a = b - a; // a = 9-5 = 4   │
     * │                             │
     * │                             │
     * │ // in binary  a=101,b=110   │
     * │                             │
     * │ a = a ^ b;// a=101^110=011  │
     * │ b = a ^ b;// b=101^011=110  │
     * │ a = a ^ b;// a=011^110=101  │
     * └─────────────────────────────┘
     * </pre>
     */
    class Solution {
        public int[] swapNumbers(int[] numbers) {
            if (numbers == null || numbers.length != 2) {
                return numbers;
            }
            numbers[0] = numbers[0] ^ numbers[1];
            numbers[1] = numbers[0] ^ numbers[1];
            numbers[0] = numbers[1] ^ numbers[0];
            return numbers;
        }
    }
}
