package leetcode.string;

public class 测试_以空格分割字符串的子串最大长度 {

    /**
     * 时间复杂度 O(n)，空间复杂度O(1)
     */
    static class Solution{
        public static int longestSubString(String str){
            int ans = 0;
            //如果str为空串，则返回0
            if(str == null || str.length() == 0){
                return ans;
            }

            //从下标为0的位置开始遍历，首先遍历直到遇到第一个非' '的下标，排除以空格为开头的字符。
            //从该位置开始遍历，并启动计数器count，没遍历一个字符就加1，如果遇到下一个' '，则比较count和ans，ans = Math.max(ans, count)，然后count重置为0；
            int count = 0;
            int N = str.length();

            for(int i = 0; i < N; i++){
                while (i < N && ' ' == str.charAt(i)){
                    i++;
                }
                while (i < N && ' ' != str.charAt(i)){
                    i++;
                    count++;
                }
                ans = Math.max(ans, count);
                count = 0;
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        int ans = Solution.longestSubString(" aa bc   aaaa aaa ");
        System.out.println(ans);
    }
}
