package leetcode.string;

import java.util.*;

public class Partition {
    //在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
//
// 示例 1:
//
// 输入: [3,2,1,5,6,4] 和 k = 2
//输出: 5
//
//
// 示例 2:
//
// 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
//输出: 4
//
// 说明:
//
// 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
// Related Topics 堆 分治算法
// 👍 739 👎 0
    

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        private final List<List<String>> res = new ArrayList<>();
        private final Deque<String> path = new ArrayDeque<>();
        public List<List<String>> partition(String s){
            int len = s.length();
            if(len == 0){
                return res;
            }
            backTracing(s, 0, len);
            return res;
        }
        public void backTracing(String s, int cur, int length) {
            if(cur == length){
                res.add(new ArrayList<>(path));
                return;
            }
            for(int i = cur; i < length; i++){
                if(!checkPalinedrome(s, cur, i)){
                    continue;
                }
                path.addLast(s.substring( cur, i+1));
                backTracing(s, i+1, length);
                path.removeLast();
            }
        }
        private boolean checkPalinedrome(String s, int left, int right){
            while(left < right){
                if(s.charAt(left) != s.charAt(right)){
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
