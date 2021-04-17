package leetcode.jzhoffer;

import java.util.*;

public class JZ27字符串的排列 {
    /*
    题目描述
输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
输入描述:
输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。
     */

    public static class Solution {
        /*
        ABC
        ACB
        BAC
        BCA
        CAB
        CBA
         */
        LinkedHashSet<String> refSet = new LinkedHashSet<>();
        public void swap(char [] arr, int i, int j){
            char tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        public void permutation(char[] arr, int p, int q) {
            if(p == q){
                refSet.add(new String(arr));
            }
            for(int i = p; i<= q; i++){
                swap(arr, i, p);
                permutation(arr, p+1, q);
                swap(arr, i, p);
            }
        }

        public ArrayList<String> Permutation(String str) {
            if(str == null || str.length() == 0){
                return new ArrayList<>(0);
            }
            char[] arr = str.toCharArray();
            permutation(arr, 0, arr.length-1);
            ArrayList<String> res = new ArrayList<>();
            refSet.forEach(item->{
                res.add(item);
            });
            Collections.sort(res);
            return res;
        }

    }
    class Solution2{
        public ArrayList<String> Permutation(String str){
            ArrayList<String> res = new ArrayList<>();
            if(str.length() == 0){
                return res;
            }
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String s = new String(array);
            res.add(str);
            while (true){
                s = nextString(s);
                if(!s.equals("finish")){
                    res.add(s);
                }else {
                    break;
                }
            }
            return res;
        }

        public String nextString(String str){
            char[] array = str.toCharArray();
            int length = str.length();
            int i = length-2;
            for (;i>=0 && array[i]>= array[i+1];i--) {}
            if(i == -1) {
                return "finish";
            }

            int j = length-1;
            for(; j>=0 && array[j] <= array[i]; j--) {}
            //swap i,j
            char tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
            //swap i,j
            for(int a=i+1, b=length-1; a<b;a++,b--){
                tmp = array[a];
                array[a] = array[b];
                array[b] = tmp;
            }
            return new String(array);

        }
    }
    public static void swap(int []arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static  void permutation(int [] arr, int p, int q){
        if(p == q){
            System.out.println(Arrays.toString(arr));
        }
        for(int i = p; i<=q; i++){
            swap(arr, i, p);
            permutation(arr, p+1, q);
            swap(arr, i, p);
        }
    }

    public static void main(String[] args) {
//        int [] array = {1,2,3,4};
//        permutation(array,0,3);

        Solution solution = new Solution();
        ArrayList<String> ab = solution.Permutation("aba");
        System.out.println(ab);
    }
}
