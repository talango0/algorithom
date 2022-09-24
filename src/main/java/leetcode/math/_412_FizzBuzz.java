package leetcode.math;
//给你一个整数 n ，找出从 1 到 n 各个整数的 Fizz Buzz 表示，并用字符串数组 answer（下标从 1 开始）返回结果，其中：
//
//
// answer[i] == "FizzBuzz" 如果 i 同时是 3 和 5 的倍数。
// answer[i] == "Fizz" 如果 i 是 3 的倍数。
// answer[i] == "Buzz" 如果 i 是 5 的倍数。
// answer[i] == i （以字符串形式）如果上述条件全不满足。
//
//
//
//
// 示例 1：
//
//
//输入：n = 3
//输出：["1","2","Fizz"]
//
//
// 示例 2：
//
//
//输入：n = 5
//输出：["1","2","Fizz","4","Buzz"]
//
//
// 示例 3：
//
//
//输入：n = 15
//输出：["1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","1
//4","FizzBuzz"]
//
//
//
// 提示：
//
//
// 1 <= n <= 10⁴
//
//
// Related Topics 数学 字符串 模拟 👍 224 👎 0

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-09-24.
 */
public class _412_FizzBuzz{
    class Solution{
        private static final String Fizz = "Fizz";
        private static final String Buzz = "Buzz";

        public List<String> fizzBuzz(int n) {
            List<String> answer = new ArrayList<String>();
            for (int i = 1; i <= n; i++) {
                StringBuffer sb = new StringBuffer();
                if (i % 3 == 0) {
                    sb.append(Fizz);
                }
                if (i % 5 == 0) {
                    sb.append(Buzz);
                }
                if (sb.length() == 0) {
                    sb.append(i);
                }
                answer.add(sb.toString());
            }
            return answer;
        }
    }
}
