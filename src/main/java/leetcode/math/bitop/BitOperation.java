package leetcode.math.bitop;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * http://graphics.stanford.edu/~seander/bithacks.html#ReverseParallel
 *
 * @author mayanwei
 * @date 2022-08-05.
 */
public class BitOperation{
    /* 常见位操作，获取与设置数位*/
    /**
     * 1. 获取数位
     * 将1左移i位，得到形如 000010000，然后对这个值与num执行 位与(AND) 操作,从而将 i 位之外的所有位清0，最后检查结果是否位0，
     * 不为 0 说明 i 位为 1，否则，i位为0
     */
    public boolean getBit(int num, int i) {
        return ((1 << i) & num) != 0;
    }


    /**
     * 2. 设置数位
     * <pre>
     * 1 l mv i ->  0000010000
     * or op |  ->  0110001000
     *              0110011000
     * </pre>
     */
    public int setBit(int num, int i) {
        return num | (i << i);
    }

    /**
     *3. 清零数位
     * 该方法与 setBit 相反，首先将 00010000 取反进而得到 11101111 的数字。
     * 接着，对该数字和 num 执行位与（AND），这样只会清零 num 的第 i 为，其余则保持不变。
     */
    public int clearBit(int num, int i) {
        int mask = 1 << i;
        return num & mask;
    }

    //如果要清零最高位至第i位所有的数位（包括最高位和第 i 位），就需要创建一个第i位为1 (1<<i) 的掩码。然后将其减1得到一串第一部分全为0
    // 第二部分全为 1 的数字。之后，将目标数字与该掩码执行 位与（AND） 操作，即得到只保留了最后 i 位的数字。
    public int clearBitsMBSthroughI(int num, int i) {
        int mask = (1 << i) - 1;
        return num & mask;
    }

    // 如果要清零第i位至第0位的所有数位（包括第i位和第0位），使用一串 1 构成数字（-1）并将其左移 i + 1 位，如此便得到一串第一部分全为1
    // 第二部分全为 0 的数字
    public int clearBitsthrough0(int num, int i) {
        int mask = (-1 << (i + 1));
        return  num & mask;
    }

    /**
     * 4. 更新数位
     * 将第 i 位的值设置为 v ，首先，用类似 1111101111 将第 i 清零，然后将待写入值 v 左移 i 位，得倒一个 i 位为 v,但其余位均为0的数。
     * 最后，对之前取得的两个结果执行 位或 操作，v 为1，则将num的i位更新位1，否则该位仍为 0
     */
    public int updateBit(int num, int i, boolean bitIs1) {
        int v = bitIs1 ? 1 : 0;
        int mask = ~(v << i);
        return (num & mask) | (v << i);
    }

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

        // 8, a+b
        Assert.assertEquals(getSum(2,3), 5);
    }

    /**
     * 不用加减符号的的加法
     * @param a
     * @param b
     * @return
     */
    public static int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }
        return a;
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

    @Test
    public void test5(){
        int [] nums = new int[]{1,2,3};
        int max = Arrays.stream(nums).max().getAsInt();
        Assert.assertEquals(3, max);

        int sum = Arrays.stream(nums).sum();
        Assert.assertEquals(6, sum);
    }

    @Test
    public void test6(){
        System.out.println( 5 & (-5));
        System.out.println(Integer.toBinaryString(5));
        System.out.println(Integer.toBinaryString(-5));
        System.out.println(Integer.toBinaryString(5 & (-5)));

        int [] a  = new int[5];
        Arrays.fill(a, 1);
        Assert.assertArrayEquals(new int[]{1,1,1,1,1}, a);

    }



    @Test
    public void test7(){
        int i = -1;
        System.out.println(String.format("%32s",Integer.toBinaryString(i)).replaceAll(" ", "0"));

        i >>>= -1;

        System.out.println(String.format("%32s",Integer.toBinaryString(i)).replaceAll(" ", "0"));
        System.out.println();
    }

    static class Int2Binary {
        private static String binaryString;

        private static void int2binary(int n) {
            binaryString = Integer.toBinaryString(n);
        }

        private static void addPadding(){
            binaryString = String.format("%32s", binaryString).replaceAll(" ", "0");
        }

        private static void addBlocks(String separator) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i< binaryString.length(); i++ ){
                sb.append(binaryString.charAt(i));
                if (i%4 == 3 && i != binaryString.length() - 1) {
                    sb.append(separator);
                }
            }
            binaryString = sb.toString();
        }

        public static void convert2Binary(int n) {
            int2binary(n);
            addPadding();
            addBlocks("_");
        }

        public static void main(String[] args) {
            int n = 10;
            for (int i = -5; i <= 5; i++){
                convert2Binary(i);
                System.out.format("Binary representation of %2d is %s\n", i, binaryString);
            }
        }
    }
}
