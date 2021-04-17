package leetcode.jzhoffer;

/**
 * @author mayanwei
 *输入一个整数数组，判断该数组是不是某 二叉搜索树 的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
 */
public class 判断一个数组是否是二叉搜索数遍历的结果 {
    /**
     * 二叉搜索树，左子树< 根结点< 由子树
     */
    public static class Solution {
        public boolean VerifySquenceOfBST(int [] sequence) {

            if(sequence==null ){
                return false;
            }
            int length = sequence.length;
            return isRightBDTPosstVist(sequence,0,length-1);
        }


        private boolean isRightBDTPosstVist(int[] sequence, int start, int end) {
            if(start>=end){
                return true;
            }
            int root = sequence[end];
            int i=start;
            for(; i<end; i++){
               if(sequence[i] > root){
                   break;
               }
            }
            for(int j = i; j<end;j++){
                if(sequence[j]<root){
                    return false;
                }
            }
            return isRightBDTPosstVist(sequence, start, i-1) && isRightBDTPosstVist(sequence, i, end-1);
        }
    }

    public static void main(String[] args) {
        int [] seq = {1,4,2,3};

        Solution solution = new Solution();
        System.out.println(solution.VerifySquenceOfBST(seq));;
    }


}
