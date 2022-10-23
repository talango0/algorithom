package leetcode.jzhoffer;
//请根据每日 气温 列表 temperatures ，重新生成一个列表，要求其对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。如果气温在这之后都不
//会升高，请在该位置用 0 来代替。
//
//
//
// 示例 1:
//
//
//输入: temperatures = [73,74,75,71,69,72,76,73]
//输出: [1,1,4,2,1,1,0,0]
//
//
// 示例 2:
//
//
//输入: temperatures = [30,40,50,60]
//输出: [1,1,1,0]
//
//
// 示例 3:
//
//
//输入: temperatures = [30,60,90]
//输出: [1,1,0]
//
//
//
// 提示：
//
//
// 1 <= temperatures.length <= 10⁵
// 30 <= temperatures[i] <= 100
//
//
//
//
//
// 注意：本题与主站 739 题相同： https://leetcode-cn.com/problems/daily-temperatures/
//
// Related Topics 栈 数组 单调栈 👍 65 👎 0

import leetcode.design.monotonous.stack._739_每日温度;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author mayanwei
 * @date 2022-10-23.
 * @see _739_每日温度
 */
public class 剑指_Offer_II_038_每日温度{
    class Solution{
        public int[] dailyTemperatures(int[] temperatures) {
            if (temperatures == null) {
                return null;
            }
            int n = temperatures.length;
            Deque<Integer> monoStack = new LinkedList<>();
            int[] res = new int[n];
            monoStack.offer(n - 1);
            for (int i = n - 2; i >= 0; i--) {
                if (temperatures[i] < temperatures[monoStack.peek()]) {
                    res[i] = monoStack.peek() - i;
                }
                else {
                    while (!monoStack.isEmpty() && temperatures[i] >= temperatures[monoStack.peek()]) {
                        monoStack.pop();
                    }
                    res[i] = monoStack.isEmpty() ? 0 :monoStack.peek() - i;
                }
                monoStack.push(i);
            }
            return res;

        }
    }
}
