package leetcode.math.bitop;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * http://graphics.stanford.edu/~seander/bithacks.html#ReverseParallel
 *
 * @author mayanwei
 * @date 2022-08-05.
 */
public class BitOperation{
    @Test
    public void test1() {

        // 1. 利用或操作 ｜ 和空格将英文字符转换成小写
        Assert.assertEquals('a' | ' ', 'a');
        Assert.assertEquals('A' | ' ', 'a');

        // 2. 利用与操作 & 和下划线将引文字符转换成大写
        Assert.assertEquals('b' & '_', 'B');
        Assert.assertEquals('B' & '_', 'B');

        // 3. 利用亦或操作 ^ 和空格进行英文字符大小写互换
        Assert.assertEquals('d' ^ ' ', 'D');
        Assert.assertEquals('D' ^ ' ', 'd');

        // 4. 判断两个数是否异号(补码编码的符号位)
        int x = -1, y = 2;
        Assert.assertTrue((x ^ y) < 0);
        x = 2;
        y = 3;
        Assert.assertFalse((x ^ y) < 0);

        // 5. 不用临时变量交换两个数
        int a = 1, b = 2;
        a ^= b;
        b ^= a;
        a ^= b;
        Assert.assertEquals(a, 2);
        Assert.assertEquals(b, 1);

        // 6. 加1
        int n = 1;
        Assert.assertEquals(-~n, 2);

        // 7. 减1
        Assert.assertEquals(~-n, 0);
    }

    /**
     * @see _191_位1的个数
     * @see _231_2的幂
     */
    @Test
    public void test2() {
        // n & (n-1) 的作用： 消除数组 n  的二进制表示中的最后一个1
        int n = 3;
        Assert.assertEquals(Integer.toBinaryString(n), "11");
        Assert.assertEquals(Integer.toBinaryString(n - 1), "10");
        Assert.assertEquals(Integer.toBinaryString(n & (n - 1)), "10");
    }

    /**
     * @see _136_只出现一次的数字
     * @see _268_丢失的数字
     */
    @Test
    public void test3() {
        //异或运算的性质是需要我们牢记的：
        // 亦或满足交换律和结合律 2 ^ 3 ^ 2 = 3 ^ (2 ^ 2) = 3 ^ 0 = 3
        //一个数和它本身做异或运算结果为 0，即 a ^ a = 0；一个数和 0 做异或运算的结果为它本身，即 a ^ 0 = a。
        // a^a = 0 的运用
        int a = 5;
        Assert.assertEquals(a ^ a, 0);
        Assert.assertEquals(0 ^ a, 5);
    }

    @Test
    public void test4() {
        /* 在最低位添加一个数字 */
        int number = 8264;
        // number 的进制
        int R = 10;
        // 想在 number 的最低位添加的数字
        int appendVal = 3;
        // 运算，在最低位添加一位
        number = R * number + appendVal;
        // 此时 number = 82643
        Assert.assertEquals(number, 82643);

        /* 在最高位删除一个数字 */
        number = 8264;
        // number 的进制
        R = 10;
        // number 最高位的数字
        int removeVal = 8;
        // 此时 number 的位数
        int L = 4;
        // 运算，删除最高位数字
        number = (int) (number - removeVal * Math.pow(R, L-1));
        // 此时 number = 264
        Assert.assertEquals(number, 264);

    }
}
