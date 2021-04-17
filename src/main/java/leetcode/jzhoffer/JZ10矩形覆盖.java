package leetcode.jzhoffer;

/**
 * 题目描述
 * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
 *
 * 比如n=3时，2*3的矩形块有3种覆盖方法：
 *
 * 示例1
 * 输入
 * 4
 * 返回值
 * 5
 */
public class JZ10矩形覆盖 {

    /**
     * 假设：n块矩形有f(n)种覆盖方法。进行逆向分析，要完成最后的搭建有两种可能。
     * 第一种情况等价于情形1中阴影部分的n-1块矩形有多少种覆盖方法，为f(n-1);
     * 第二种情况等价于情形2中阴影部分的n-2块矩形有多少种覆盖方法，为f(n-2);
     *
     * 故f(n) = f(n-1) + f(n-2)，还是一个斐波那契数列。。。。
     *
     * 且f(1) = 1, f(2) = 2
     */

    public class Solution {
        public int RectCover(int target) {
            if ( target < 1 ) {
                return 0;
            }
            int g = 1, f = 2;
            while ( --target>0 ) {
                f = f + g;
                g = f - g;
            }
            return g;
        }
    }
}
