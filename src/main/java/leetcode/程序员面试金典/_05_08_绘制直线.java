package leetcode.程序员面试金典;

import java.util.Arrays;
//已知一个由像素点组成的单色屏幕，每行均有 w 个像素点，所有像素点初始为 0，左上角位置为 (0,0)。
//
//现将每行的像素点按照「每 32 个像素点」为一组存放在一个 int 中，再依次存入长度为 length 的一维数组中。
//
//我们将在屏幕上绘制一条从点 (x1,y) 到点 (x2,y) 的直线（即像素点修改为 1），请返回绘制过后的数组。
//
//注意：
//
//用例保证屏幕宽度 w 可被 32 整除（即一个 int 不会分布在两行上）
//
//示例1:
// 输入：length = 1, w = 32, x1 = 30, x2 = 31, y = 0
// 输出：[3]
// 解释：在第 0 行的第 30 位到第 31 位画一条直线，屏幕二进制形式表示为 [00000000000000000000000000000011]，因此返回 [3]
//
//示例2:
// 输入：length = 3, w = 96, x1 = 0, x2 = 95, y = 0
// 输出：[-1, -1, -1]
// 解释：由于二进制 11111111111111111111111111111111 的 int 类型代表 -1，因此返回 [-1,-1,-1]
//
//提示：
//
//1 <= length <= 10^5
//1 <= w <= 3 * 10^5
//0 <= x1 <= x2 < w
//0 <= y <= 10
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/draw-line-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @author mayanwei
 */
public class _05_08_绘制直线{


    static class Solution{
        public int[] drawLine(int length, int w, int x1, int x2, int y) {
            int[] ret = new int[length];
            // 注意根据所在行数计算偏移量
            int offset = y * w / 32;
            // 首位数字下标
            int head = x1 / 32 + offset;
            // 末位数字下标
            int rear = x2 / 32 + offset;
            // 把涉及到的数全部置 -1 也就是 0b11111111111111111111111111111111
            for (int i = head; i <= rear; i++) {
                ret[i] = -1;
            }
            // 调整首位数字
            ret[head] = ret[head] & -1 >>> x1 % 32;
            // 调整末位数字
            ret[rear] = ret[rear] & Integer.MIN_VALUE >> x2 % 32;
            return ret;
        }
    }

    // 绘制直线。有个单色屏幕存储在一个一维字节数组中，使得8个连续像素可以存放 在一个字节里。
    // 屏幕宽度为 w，且 w 可被 8 整除(即一个字节不会分布在两行上)，屏幕高度 6 可由数组长度及屏幕宽度推算得出。
    // 请实现一个函数，绘制从点(x1, y )到点(x2, y )的水平线。

    /**
     * 这个题目有个简单解法，用for循环迭代，从x1到 x2,一路设定每个像素。但是这么做没劲，效率也不高。
     * 更高的做法是，如果 x1 和 x2 相距甚远，其间包含几个完整字节，只要使用 screen[bytes_pos] = 0xFF，一次就能设置一整个字节。
     * 这条线起点和终点剩余部分的位，可用掩码设定。
     */
    class Solution1{
        public void drawLine(byte[] screen, int width, int x1, int x2, int y) {
            int start_offset = x1 % 8;
            int first_full_bytes = x1 / 8;
            if (start_offset != 0) {
                first_full_bytes++;
            }

            int end_offset = x2 % 8;
            int last_full_bytes = x2 / 8;
            if (last_full_bytes != 0) {
                last_full_bytes--;
            }

            //设置完整的字节
            for (int i = first_full_bytes; i <= last_full_bytes; i++) {
                screen[(width / 8) * y + i] = (byte) 0xff;
            }
            //创建开始行和结束行的掩码
            byte start_mask = (byte) (0xff >> start_offset);
            byte end_mask = (byte) (0xff >> end_offset);

            //设置开始与结束行
            if ((x1 / 8) == (x2 / 8)) { // x1和 x2 在同一字节
                byte mask = (byte) (start_mask & end_mask);
                screen[(width / 8) * y + (x1 / 8)] |= mask;
            }
            else {
                if (start_mask != 0) {
                    int byte_number = (width / 8) * y + first_full_bytes - 1;
                    screen[byte_number] |= start_mask;
                }
                if (end_mask != 0) {
                    int byte_number = (width) / 8 * y + last_full_bytes + 1;
                    screen[byte_number] |= end_mask;
                }
            }
        }
    }


    public static void main(String[] args) {
//        int i = new BigInteger("11111111111111111111111111111111", 2).intValue();
        Solution solution = new Solution();
        int[] ints = solution.drawLine(3, 96, 25, 95, 0);
        System.out.println(Arrays.toString(ints));
//        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));

    }


}
