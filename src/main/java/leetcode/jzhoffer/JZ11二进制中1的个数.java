package leetcode.jzhoffer;

/**
 * 题目描述
 * 输入一个整数，输出该数32位二进制表示中1的个数。其中负数用补码表示。
 * 示例1
 * 输入

 * 10
 * 返回值

 * 2
 */
public class JZ11二进制中1的个数 {
    /**
     * 方法 1
     */
    static class Solution {
        public int NumberOf1(int n) {
            int num = 0;
            String nStr = Integer.toBinaryString(n);
            for(int i = 0;i< nStr.length();i++){
                if(nStr.charAt(i) == '1') {
                    num++;
                }
            }
            return num;
        }
    }


    /**
     * 方法2
     * 超级简单容易理解 &(与)
     * 把这个数逐次 右移 然后和1 与,
     * 就得到最低位的情况,其他位都为0,
     * 如果最低位是0和1与 之后依旧 是0，如果是1，与之后还是1。
     * 对于32位的整数 这样移动32次 就记录了这个数二进制中1的个数了
     */
    static class Solution2{
        public int NumberOf1(int n){
            int count = 0;
            while (n != 0) {
                count++;
                n &= (n - 1);
            }
            return count;
        }
    }

}
