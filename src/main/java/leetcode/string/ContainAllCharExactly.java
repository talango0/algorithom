package leetcode.string;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringJoiner;

public class ContainAllCharExactly {

    /**
     题 目 二
     给 定 长 度 为 m 的 字 符 串 aim ， 以 及 一 个 长 度 为 n 的 字 符 串 str
     问 能 否 在 str 中 找 到 一 个 长 度 为 m 的 连 续 子 串 ，
     使 得 这 个 子 串 刚 好 由 aim 的 个 字 符 组 成 ， 顺 序 无 所 谓 ，
     返 回 任 意 满 足 条 件 的 一 个 子 串 的 起 始 位 置 ， 未 找 到 返 回 -1
     */

    static class Solution1{
        //O(n^3*log n
        public static int containExactly1(String s, String a){
            if(s == null || a == null || s.length() <  a.length()){
                return -1;
            }
            char[] aim = a.toCharArray();
            Arrays.sort(aim);
            String aimSort = String.valueOf(aim);
            for(int l = 0; l< s.length(); l++){
                for(int r=l; r < s.length(); r++){
                    char[] cur = s.substring(l, r + 1).toCharArray();
                    Arrays.sort(cur);
                    String curSort = String.valueOf(cur);
                    if(curSort.equals(aimSort)){
                        return l;
                    }
                }
            }
            return -1;
        }
    }

    static class Solution2{
        public static int containExactly2(String s, String a){
            if(s == null || a == null || s.length() <  a.length()){
                return -1;
            }
            char[] str = s.toCharArray();
            char[] aim = a.toCharArray();
            Arrays.sort(aim);
            String aimSort = String.valueOf(aim);
            for(int l = 0; l< s.length() - aim.length; l++){
                if(isCountEqual(str, l, aim)){
                    return l;
                }
            }
            return -1;
        }

        private static boolean isCountEqual(char[] str, int l, char[] aim) {
            int[] count = new int[256];
            for(int i = 0; i < aim.length; i++){
                count[aim[i]]++;
            }
            for(int i = 0; i< aim.length; i++){
                if(count[str[l+i]] -- == 0){
                    return false;
                }
            }
            return true;
        }
    }


    static class Solution3{
        public static int containExactly(String s, String a){
            if(s == null || a == null || s.length() <  a.length()){
                return -1;
            }
            char[] aim = a.toCharArray();
            int[] count = new int[256];
            for(int i = 0; i < aim.length; i++){
                count[aim[i]] ++;
            }
            int m = aim.length;
            char[] str = s.toCharArray();
            int inValidTimes = 0;
            int r = 0;
            for(; r<m; r++){
                if(count[str[r]] -- <= 0){
                    inValidTimes ++;
                }
            }
            //第一次形成了长度为m的长度
            for(; r< str.length; r++){
                if(inValidTimes == 0){
                    return r-m;
                }
                if(count[str[r]] -- <= 0){
                    inValidTimes ++;
                }
                if(count[str[r-m]] ++ <0){
                    inValidTimes --;
                }
            }

            return inValidTimes == 0? r-m: -1;
        }
    }
}
