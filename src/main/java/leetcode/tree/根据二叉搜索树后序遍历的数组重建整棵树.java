package leetcode.tree;

import common.TreeNode;

public class 根据二叉搜索树后序遍历的数组重建整棵树 {

    static class Solution1{
        /**
         * 根据二叉搜索数的后续遍历数组重建整棵树
         */
        public static TreeNode postArrayToBST(int [] postArr){
            return process(postArr, 0, postArr.length-1);
        }

        private static TreeNode process(int[] postArr, int l, int r) {
            if(l > r){
                return null;
            }
            TreeNode head = new TreeNode(postArr[r]);
            if(l == r) {
                return head;
            }
            // < > m肯定会更新
            // < m 会更新为 r-1
            // >  m - l-1
            int m = l-1;
            for(int i = l; i < r; i++){
                if(postArr[i] < postArr[r]){
                    m = i;
                }
            }
            head.left = process(postArr, l, m);
            head.right = process(postArr, m+1, r-1);
            return head;
        }
    }



    static class Solution2{
        /**
         * 根据二叉搜索数的后续遍历数组重建整棵树
         */
        public static TreeNode postArrayToBST(int [] postArr){
            return process(postArr, 0, postArr.length-1);
        }

        private static TreeNode process(int[] postArr, int l, int r) {
            if(l > r){
                return null;
            }
            TreeNode head = new TreeNode(postArr[r]);
            if(l == r) {
                return head;
            }
            // < > m肯定会更新
            // < m 会更新为 r-1
            // >  m - l-1
            int m = l-1;
            int left = l;
            int right = r-1;
            while (left <= right){
                int mid = left + ((right-left) >> 1);
                if(postArr[mid] < postArr[r]){
                    m = mid;
                    left = mid + 1;
                }else {
                    right = mid - 1;
                }
            }

            head.left = process(postArr, l, m);
            head.right = process(postArr, m+1, r-1);
            return head;
        }
    }

    public static void main(String[] args) {
        TreeNode treeNode = Solution2.postArrayToBST(new int[]{3, 5, 4});
        System.out.println(1);
    }

}
