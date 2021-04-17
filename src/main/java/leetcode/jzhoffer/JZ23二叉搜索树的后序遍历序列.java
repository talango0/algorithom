package leetcode.jzhoffer;


/**
 * 题目描述
 * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则返回true,否则返回false。假设输入的数组的任意两个数字都互不相同。
 * 示例1
 * 输入
 * [4,8,6,12,16,14,10]
 * 返回值
 * true
 */
public class JZ23二叉搜索树的后序遍历序列 {
    static class Solutionq {
        public boolean V1rifySquenceOfBST(int [] sequence) {
            if(sequence==null || sequence.length==0){
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
}
