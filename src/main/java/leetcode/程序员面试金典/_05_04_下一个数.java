package leetcode.程序员面试金典;
//下一个数。给定一个正整数，找出与其二进制表达式中1的个数相同且大小最接近的那两个数（一个略大，一个略小）。
//
//示例1:
//
// 输入：num = 2（或者0b10）
// 输出：[4, 1] 或者（[0b100, 0b1]）
//示例2:
//
// 输入：num = 1
// 输出：[2, -1]
//提示:
//
//num的范围在[1, 2147483647]之间；
//如果找不到前一个或者后一个满足条件的正数，那么输出 -1。
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/closed-number-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-05-18.
 */
public class _05_04_下一个数{
    // 思路：题目有些绕，我们可以将 getNext 称为较大的数，getPrev 称为较小的数
    // 蛮力法，即在 n 的二进制中得到1的个数，然后增加或减少直至找到 1 的个数相同的数字。
    // 另外的做法：
    // 位操作法以及巧妙运用算数
    class Solution {
        public int[] findClosedNumbers(int num) {
            int[] ans = new int[2];
            Arrays.fill(ans,-1);
            int cnt = Integer.bitCount(num);
            if(num == 2147483647) return ans;
            for(int i = num - 1;i>0;i--){
                if(Integer.bitCount(i) == cnt){
                    ans[1] = i;
                    break;
                }
            }
            for(int i = num + 1;i<Integer.MAX_VALUE;i++){
                if(Integer.bitCount(i) == cnt){
                    ans[0] = i;
                    break;
                }
            }
            return ans;
        }
    }
    /**
     * <pre>
     ┌───────────────────────────────┐
     │n = 13948                      │
     │                               │
     │1  1  0  1  1 0 0 1 1 1 1 1 0 0│
     │13 12 11 10 9 8 7 6 5 4 3 2 1 0│
     │                               │
     │*getNext()                     │
     │1  1  0  1  1 0 1 1 1 1 1 1 0 0│
     │13 12 11 10 9 8 7 6 5 4 3 2 1 0│
     │                p              │
     │move 1 after p to right side   │
     │update one of 1 after p to 0   │
     │                               │
     │the size of 1 after p is c1 = 5│
     │set 0 after p, and then set c1-│
     │1 to right side                │
     │                               │
     │c0 : the size of 0 after p, c0=│
     │                               │
     │p = 7                          │
     │the next number is:            │
     │1  1  0  1  1 0 1 0 0 0 1 1 1 1│
     │13 12 11 10 9 8 7 6 5 4 3 2 1 0│
     │                               │
     │*getPrev()                     │
     │                               │
     │ calculate c0,c1               │
     │ c0 = 4                        │
     │ c1 = 5                        │
     │  p = c0 + c1 = 9              │
     │1  1  0  1  1 0 0 1 1 1 1 1 0 0│
     │13 12 11 10 9 8 7 6 5 4 3 2 1 0│
     │            p                  │
     │                               │
     │1  1  0  1  0 0 0 0 0 0 0 0 0 0│
     │13 12 11 10 9 8 7 6 5 4 3 2 1 0│
     │            p                  │
     │                               │
     │1  1  0  1  0 1 1 1 1 1 1 0 0 0│
     │13 12 11 10 9 8 7 6 5 4 3 2 1 0│
     │            p                  │
     └───────────────────────────────┘
     * </pre>
     */
    class Solution1 {
        public int[] findClosedNumbers(int num) {
            return new int [] {getNext(num), getPrev(num)};
        }

        private int getNext(int n) {
            // 计算 c0, c1, p
            int c = n;
            int c0 = 0,c1 = 0;
            while (((c&1) == 0) && c!= 0) {
                c0 ++;
                c >>= 1;
            }
            while ((c & 1) == 1) {
                c1 ++;
                c >>= 1;
            }
            // 错误：如果 n = 11..100..00，那么不存在更大的数由相同位数的1
            if (c0 + c1 == 31 || c0 + c1 == 0) {
                return -1;
            }

            int p = c0 + c1; //  最右非拖尾0的位置
            n |= (1 << p);
            n &= ~((1 << p) - 1); // 清除所有p的右侧位
            n |= (1 << (c1-1)) - 1; // 在右侧插入 (c1-1) 个1
            return n;
        }

        private int getPrev(int n) {
            // 计算c0,c1
            int c = n;
            int c0 = 0, c1= 0;
            while ((c & 1) == 1) {
                c1++;
                c >>= 1;
            }

            // 错误，如果 n = 00..0011..11,不存在更小的且1位数相同的值。
            if (c == 0) {
                return -1;
            }

            while ((c & 1) == 0 & c != 0) {
                c0 ++;
                c >>= 1;
            }

            int p = c0 + c1; // 最右侧非拖尾1的位置
            n &= ((~0) << (p+1)); // 从位置p开始清零

            int mask = (1 << (c1 + 1)) - 1; // 包括 c1 + 1 个1的序列。
            n |= mask << (c0-1);
            return n;
        }
    }


    /**
     * <pre>
     * ┌────────────────────────────────────┬─────────────────────────────────────┐
     * │ *getNext()                         │ *getPrev()                          │
     * │ 1  1  0  1  1 0 1 1 1 1 1 1 0 0    │ 1 0 0 0 0 0 1 1                     │
     * │ 13 12 11 10 9 8 7 6 5 4 3 2 1 0    │ 7 6 5 4 3 2 1 0                     │
     * │               p                    │ p                                   │
     * │ c0 is the count of 0 after p       │ c0 is the count of 0 after p, 5     │
     * │ c1 is the count of 1 after p       │ c1 is the count of 1 after p, 2     │
     * │ p = c0 + c1                        │ p = c0 + c1 = 5+ 2 =7               │
     * │                                    │                                     │
     * │ step1: set 1 at index of p         │ step1: set 0 at index of p          │
     * │ step2: set 0 where index in the    │ step2: set 1 where index in the     │
     * │        range of [0,p)              │        range of [0,p)               │
     * │ step3: set 1 where index in the    │ step3: set 0 where index in the     │
     * │        range of [0,c1-1]           │        range of [0,c0-1]            │
     * │                                    │                                     │
     * │ step1 and step2 :                  │ step1,step2,step3:                  │
     * │         c0                         │         c0                          │
     * │   n += 2  - 1 // set 1 idx [0,1]   │   n -= 2  - 1   //set 0 idx [0,1]   │
     * │   n += 1      // set 0 idx [0,p]   │   n -= 1        //01111111          │
     * │               // set 1 idx = p     │         c0-1    //                  │
     * │ step3:                             │   n -= 2    -1  //set 0 idx [0,c0-1]│
     * │         c1-1                       │                 //01110000          │
     * │   n += 2    -1// set 0 inx [0,c1-1]│                                     │
     * │                                    │                                     │
     * │ in summary:    c0            c1-1  │ in summary:    c0            c1-1   │
     * │   next = n + (2  -1) + 1 + (2   -1)│   next = n - (2  -1) - 1 - (2   -1) │
     * │               c0   c1-1            │               c0   c1-1             │
     * │        = n + 2  + 2    -1          │        = n - 2  - 2    +1           │
     * └────────────────────────────────────┴─────────────────────────────────────┘
     * </pre>
     */
    class Solution3 {
        public int[] findClosedNumbers(int num) {
            return new int[]{getNext(num), getPrev(num)};
        }

        private int getPrev(int n) {
            // 计算c0,c1
            int c = n;
            int c0 = 0, c1= 0;
            while ((c & 1) == 1) {
                c1++;
                c >>= 1;
            }

            // 错误，如果 n = 00..0011..11,不存在更小的且1位数相同的值。
            if (c == 0) {
                return -1;
            }

            while ((c & 1) == 0 & c != 0) {
                c0 ++;
                c >>= 1;
            }
            return n - (1 << c1) - (1 << (c0 -1)) + 1;
        }

        private int getNext(int n) {
            // 计算 c0, c1, p
            int c = n;
            int c0 = 0,c1 = 0;
            while (((c&1) == 0) && c!= 0) {
                c0 ++;
                c >>= 1;
            }
            while ((c & 1) == 1) {
                c1 ++;
                c >>= 1;
            }
            // 错误：如果 n = 11..100..00，那么不存在更大的数由相同位数的1
            if (c0 + c1 == 31 || c0 + c1 == 0) {
                return -1;
            }
            return n + (1 << c0) + (1 << (c1 -1)) -1;
        }
    }

}
