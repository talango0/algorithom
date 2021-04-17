package leetcode.jzhoffer;


public class JZ46孩子们的游戏_圆圈中最后剩下的数 {

    /**
     * 题目描述
     * 每年六一儿童节,牛客都会准备一些小礼物去看望孤儿院的小朋友,今年亦是如此。HF作为牛客的资深元老,自然也准备了一些小游戏。
     * 其中,有个游戏是这样的:首先,让小朋友们围成一个大圈。
     * 然后,他随机指定一个数m,让编号为0的小朋友开始报数。
     * 每次喊到m-1的那个小朋友要出列唱首歌,然后可以在礼品箱中任意的挑选礼物,并且不再回到圈中,
     * 从他的下一个小朋友开始,继续0...m-1报数....这样下去....
     * 直到剩下最后一个小朋友,可以不用表演,并且拿到牛客名贵的“名侦探柯南”典藏版(名额有限哦!!^_^)。
     * 请你试着想下,哪个小朋友会得到这份礼品呢？(注：小朋友的编号是从0到n-1)
     * 如果没有小朋友，请返回-1
     */
    public static class Solution {
        /*
        题目抽象：
        给定由一个 [0, ... , n-1] 构成的数组，第一次由 0 开始数 m 个数，然后删除，以后每次从删除的数下一个位置开始数 m 个数，然后删除，直到剩余一个数字，找出那个数字。
        比如： arr[0,1,2,3,4], m = 3
        第一次删除 2， 变成arr[0,1,3,4]
        第二次删除 0， 变成arr[1,3,4]
        第三次删除 4， 变成arr[1,3]
        第四次删除 1， 变成arr[3]

        方法1 递归
        假设
         */

        public int LastRemaining_Solution(int n, int m) {
            if(n <= 0){
                return -1;
            }
            int index = 0;
            for(int i=2; i<=n; ++i){
                index = (index + m) % i;
            }
            return index;
        }
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.LastRemaining_Solution(5,3));
    }
}
