package leetcode;

import java.util.Arrays;

public class MakeNo {
    /**
     * 生成长度为size的达标数组
     * 达标：对于任意的 i<k<j,满足 [i] + [j] != [k]*2
     */
    public static int [] makeNo(int size){
        if(size == 1){
            return new int[]{1};
        }
        //size
        //一半达标，向上取整
        // 7 -> 4
        // 4个奇数+ 3个偶数
        int halfSize = (size+1)/2;
        int[] base = makeNo(halfSize);

        int[] ans = new int[size];
        int index = 0;
        for(;index < halfSize; index++){
            ans[index] = base[index] * 2 + 1;
        }
        for(int i = 0; index< size; index++, i++){
            ans[index] = base[i] * 2;
        }
        return ans;
    }

    public static void main(String[] args) {
        final int[] ints = MakeNo.makeNo(3);
        System.out.println(Arrays.toString(ints));
    }
}
