package leetcode;

import java.util.Arrays;

public class KthMinPair {
    public static int[] kthMinPairs(int arr[], int k){
        if(arr == null || arr.length == 0){
            return null;
        }
        int n = arr.length;
        if(k > n*n){
            return null;
        }
        Arrays.sort(arr);
        int firstNum = arr[(k-1)/n];

        int lessFirstNumSize = 0;
        int firstNumSize = 0;
        for(int i = 0; i < n && arr[i] <= firstNum; i++){
            if(arr[i] < firstNum){
                lessFirstNumSize ++ ;
            }else {
                firstNumSize ++ ;
            }
        }
        int rest = k-(lessFirstNumSize * n);
        return new int[]{firstNum, arr[(rest-1)/firstNumSize]};
    }

    public static void main(String[] args) {
        int[] res = KthMinPair.kthMinPairs(new int[]{1, 1, 2}, 9);
        System.out.println(Arrays.toString(res));


    }
}
