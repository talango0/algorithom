package leetcode.jzhoffer;

public class   JZ35数组中的逆序对 {
    /*
题目描述
在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组,求出这个数组中的逆序对的总数P。
并将P对1000000007取模的结果输出。 即输出P%1000000007
输入描述:
题目保证输入的数组中没有的相同的数字
数据范围：
	对于%50的数据,size<=10^4
	对于%75的数据,size<=10^5
	对于%100的数据,size<=2*10^5
示例1
输入
1,2,3,4,5,6,7,0
输出
7

     */


    /**
     * 超时
     */
    public class Solution {
        public int InversePairs(int [] array) {
            if(array == null || array.length == 0){
                return 0;
            }
            int count = 0;
            for(int i = 0; i<array.length-1; i++){
                for(int j = i+1; j<array.length; j++){
                    if(array[i]> array[j]){
                        count++;
                    }
                }
            }
            return count % 1000000007;
        }
    }


    /**
     * 采用归并排序
     */
    public static class Solution1 {
        private int cnt;
        private void MergeSort(int[] array, int start, int end){
            if(start >= end) {
                return;
            }
            int mid = (start+end)/2;
            MergeSort(array, start, mid);
            MergeSort(array, mid+1, end);
            MergeOne(array, start, mid, end);
        }
        private void MergeOne(int[] array, int start, int mid, int end){
            int[] temp = new int[end-start+1];
            int k=0, i=start, j=mid+1;
            while(i<=mid && j<= end){
                //如果前面的元素小于后面的不能构成逆序对
                if(array[i] <= array[j]) {
                    temp[k++] = array[i++];
                } else{
                    //如果前面的元素大于后面的，那么在前面元素之后的元素都能和后面的元素构成逆序对
                    temp[k++] = array[j++];
                    cnt = (cnt + (mid-i+1))%1000000007;
                }
            }
            while(i<= mid) {
                temp[k++] = array[i++];
            }
            while(j<=end) {
                temp[k++] = array[j++];
            }
            for(int l=0; l<k; l++){
                array[start+l] = temp[l];
            }
        }
        public int InversePairs(int [] array) {
            MergeSort(array, 0, array.length-1);
            return cnt;
        }
    }

    public static void main(String[] args) {
        int [] arr = {1,2,3,4,5,6,7,0};
        Solution1 solution1 = new Solution1();
        System.out.println(solution1.InversePairs(arr));
    }


}
