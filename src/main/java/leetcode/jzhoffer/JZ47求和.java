package leetcode.jzhoffer;

public class JZ47求和 {

    public static class Solution {
        public int Sum_Solution(int n) {
            return (int) (n*((n+1)/2.0));
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int i = solution.Sum_Solution(10);
        System.out.println(i);

    }
}
