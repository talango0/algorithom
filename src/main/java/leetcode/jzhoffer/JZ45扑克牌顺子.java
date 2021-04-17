package leetcode.jzhoffer;

import java.util.Collections;
import java.util.HashSet;

public class JZ45扑克牌顺子 {

    /*
    题目描述
LL今天心情特别好,因为他去买了一副扑克牌,发现里面居然有2个大王,2个小王(一副牌原本是54张^_^)...他随机从中抽出了5张牌,想测测自己的手气,
看看能不能抽到顺子,如果抽到的话,他决定去买体育彩票,嘿嘿！！“红心A,黑桃3,小王,大王,方片5”,“Oh My God!”不是顺子.....LL不高兴了
,他想了想,决定大\小 王可以看成任何数字,并且A看作1,J为11,Q为12,K为13。上面的5张牌就可以变成“1,2,3,4,5”(大小王分别看作2和4),“So Lucky!”。
LL决定去买体育彩票啦。 现在,要求你使用这幅牌模拟上面的过程,然后告诉我们LL的运气如何， 如果牌能组成顺子就输出true，否则就输出false。
为了方便起见,你可以认为大小王是0。
     */


    public static class Solution {
        /*
        数字范围 0-13十四个数

        0, 1，2，3，...,5
        1. 如果 n 个整数是连续的，max-min < n
        2. 因为 0 可以表示任何数，

         */

        public boolean isContinuous(int [] numbers) {
            if(numbers == null || numbers.length == 0){
                return false;
            }
            HashSet<Integer> set = new HashSet<>();
            int zeroCount = 0;
            for(int i = 0; i<numbers.length; i++){
                int number = numbers[i];
                if(number == 0){
                    zeroCount += 1;
                    continue;
                }
                if(set.contains(number)){
                    return false;
                }else {
                    set.add(number);
                }
            }
            int max = Collections.max(set);
            int min = Collections.min(set);
            int diff = max - min;  //4
            if(diff > set.size()+zeroCount-1){
                return false;
            }

            return true;
        }
    }
    public static void main(String[] args) {
        int[] num = new int[]{1, 2, 3, 6, 0};
        Solution solution = new Solution();
        boolean res = solution.isContinuous(num);

        System.out.println(res);
    }
}
