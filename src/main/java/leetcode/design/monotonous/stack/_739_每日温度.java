package leetcode.design.monotonous.stack;
//请根据每日 气温 列表，重新生成一个列表。对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。如果气温在这之后都不会升高，请在该位置用 0 来代替。
//
//
// 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2
//, 1, 1, 0, 0]。
//
// 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。
// Related Topics 栈 哈希表
// 👍 578 👎 0

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class _739_每日温度 {
    /**
     * 方法1，暴力法1
     */    static class Solution1 {
        public int[] dailyTemperatures(int[] T) {
            for(int i = 0, j = 1; i<T.length; i++){
                j = i+1;
                for(;j< T.length; j++){
                    if(T[i] < T[j]){
                        T[i] = j-i;
                        break;
                    }
                }
                if(j == T.length){
                    T[i] = 0;
                }
            }
            return T;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    /**
     *  暴力法2
     *  反向遍历温度列表。对于每个元素T[i]，在数组next中找到 T[i] + i 到100 中每个温度第一次出现的下标，将其中的最小下标记为warmerIndex，
     *  则 warmerIndex为下一次温度比当天高的下标。如果warmerIndex不为无穷大，则warmerIndex-i即为下一次温度比当前温度高的等待天数，最后令
     *  next[T[i]] = i;
     *
     *  时间复杂度：O(nm)O(nm)
     *  空间复杂度：O(m)O(m)
     */
    static class Solution2 {
        public int[] dailyTemperatures(int[] T) {
            int length = T.length;
            int [] ans = new int[length];
            //next 记录每个温度第一次出现的下标位置
            int [] next = new int[101];
            //next 数组初始为Integer.MAX_VALUE 在遍历列表的过程中更新。
            Arrays.fill(next, Integer.MAX_VALUE);
            for(int i = length-1; i >= 0; --i){
                int warmerIndex = Integer.MAX_VALUE;
                for(int t = T[i] + 1; t <= 100; ++t){
                    if( next[t] < warmerIndex){
                        warmerIndex = next[t];
                    }
                }
                if(warmerIndex < Integer.MAX_VALUE){
                    ans[i] = warmerIndex - i;
                }
                next[T[i]] = i;
            }
            return ans;
        }
    }

    /**
     * 单调栈
     * 时间复杂度 O(n)
     * 空间复杂度 O(n)
     */
    static class Solution3 {
        public int[] dailyTemperatures(int[] T) {
            int length = T.length;
            int [] ans = new int[length];
            Deque<Integer> stack = new LinkedList<>();
            for(int i = 0; i< length; i++){
                int temperature  = T[i];
                while(!stack.isEmpty() && temperature > T[stack.peek()]){
                    int prevIndex = stack.pop();
                    ans[prevIndex] = i - prevIndex;
                }
                stack.push(i);
            }
            return ans;
        }
    }

    @Test
    void testSolution1(){
        Solution1 solution1 = new Solution1();
        int[] ans = solution1.dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73});
        Assert.assertArrayEquals(new int[]{1, 1, 4, 2, 1, 1, 0, 0}, ans);
    }

    @Test
    void testSolution2(){
        Solution2 solution2 = new Solution2();
        int[] ans = solution2.dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73});
        Assert.assertArrayEquals(new int[]{1, 1, 4, 2, 1, 1, 0, 0}, ans);
    }


}
