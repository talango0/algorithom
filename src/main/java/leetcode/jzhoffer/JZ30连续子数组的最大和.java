package leetcode.jzhoffer;


/**
 * @author mayanwei
 */
public class JZ30连续子数组的最大和 {
    /*

    题目描述
HZ偶尔会拿些专业问题来忽悠那些非计算机专业的同学。今天测试组开完会后,
他又发话了:在古老的一维模式识别中,常常需要计算连续子向量的最大和,当向量全为正数的时候,问题很好解决。
但是,如果向量中包含负数,是否应该包含某个负数,并期望旁边的正数会弥补它呢？例如:{6,-3,-2,7,-15,1,2,2}
,连续子向量的最大和为8(从第0个开始,到第3个为止)。给一个数组，返回它的最大连续子序列的和，你会不会被他忽悠住？(子向量的长度至少是1)
     */

    public class Solution {
        /*
        方法1. 动态规划法
        状态定义： dp[i] 表示以 i 结尾的连续自数组的最大和。所以最终要求 dp[n-1]
        状态转移方程： dp[i] = max( array[i], dp[i-1] + array[i] )

         */
        public int FindGreatestSumOfSubArray(int[] array) {
            int size = array.length;
            int [] dp = new int[size+1];
            dp[0] = 0;
            int ret = array[0];
            for(int i = 1; i <= size; i++){
                dp[i] = Math.max(array[i-1], dp[i-1]+ array[i-1]);
                ret = Math.max(ret, dp[i]);
            }
            return ret;
        }
    }


    /**
     * 求最大字段和
     */
    class Kadane{

        public int maxSubArraySum(int [] A){
            int ans = 0,cur=0;
            for(int i = 0; i<A.length; i++){
                cur = A[i] + Math.max(cur, 0);
                ans = Math.max(ans, cur);
            }
            return ans;
        }
    }
}
