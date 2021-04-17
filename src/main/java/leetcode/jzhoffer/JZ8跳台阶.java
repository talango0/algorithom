package leetcode.jzhoffer;


/*

题目描述
一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
示例1
输入
1
返回值
1
示例2
输入
4
返回值
5
 */
public class JZ8跳台阶 {

    static class Solution {
        public int JumpFloor(int target) {
            if(target == 1){
                return 1;
            }
            if(target == 2){
                return 2;
            }
            return JumpFloor(target - 1) + JumpFloor(target - 2);
        }
    }
}
