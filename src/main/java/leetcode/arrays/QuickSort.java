package leetcode.arrays;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @author mayanwei
 */
public class QuickSort {

    static class Solution{
        public int partion(int[] args, int p, int r){
            int x = args[r];
            int i = p-1;
            for(int j=p; j<=r-1; j++){
                //统计比x小的数有几个
                if(args[j] <= x){
                    i += 1;
                    exchange(args, j, i);
                }
            }
            exchange(args,i+1, r);
            return i+1;
        }

        private void exchange(int[] args, int j, int i) {
            int tmp = args[j];
            args[j] = args[i];
            args[i] = tmp;
        }


        /**
         * 递归实现
         * 快速排序本质上是一个二叉树的前序遍历。
         * 若要对 nums[p,r] 进行排序，我们首先找到一个分界点 q， 然后通过交换元素使得 nums[p, q-1] 都小于等于 nums[q],
         * 且 nums[q+1, r] 的元素都大于 nums[q], 然后递归地去 nums[p,q-1] 和 nums[q+1, r] 中去寻找新的分界点，最后
         * 整个数组就排好序了。
         *
         * @param args
         * @param p
         * @param r
         */
        public void quickSort(int[] args, int p, int r) {
            if( p < r){
                int q =partion(args, p, r);
                quickSort(args, p, q-1);
                quickSort(args, q+1, r);
            }
        }


        /**
         * 利用栈来实现
         * @param args
         * @param p
         * @param r
         */
        private void quickSort2(int [] args, int p, int r){
            Deque<Integer> s = new ArrayDeque<>(2);
            if(p < r){
                int q = partion(args, p, r);
                if (p < q-1) {
                    s.addFirst(p);
                    s.addFirst(q-1);
                }
                if (r > q+1) {
                    s.addFirst(q+1);
                    s.addFirst(r);
                }
                while (!s.isEmpty()){
                    r = s.pop().intValue();
                    p = s.pop().intValue();
                    q = partion(args, p, r);
                    if(p < q-1){
                        s.addFirst(p);
                        s.addFirst(q-1);
                    }
                    if(r > q+1){
                        s.addFirst(q+1);
                        s.addFirst(r);
                    }
                }

            }
        }
    }






    public static void main(String [] args) {
        int [] nums = {13, 19, 9, 5, 12, 8, 7, 4, 21, 2, 5, 11};
        Solution solution = new Solution();
        solution.quickSort(nums, 0, nums.length-1);
        System.out.println(Arrays.toString(nums));


    }

}


