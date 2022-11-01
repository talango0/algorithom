package leetcode.jzhoffer;

import leetcode.tree.TreeNode;
import leetcode.tree._129_求根节点到叶节点数字之和;

/**
 * @author mayanwei
 * @date 2022-10-31.
 * @see _129_求根节点到叶节点数字之和
 */
public class 剑指_Offer_II_049_从根节点到叶节点的路径数字之和{
    //深度优先遍历  时间复杂度O(n)  空间复杂度O(n)
    class Solution{
        public int sumNumbers(TreeNode root){
            return dfs(root, 0);
        }
        public int dfs(TreeNode root, int prevSum){
            if(root == null){
                return 0;
            }
            int sum = prevSum * 10 + root.val;
            if(root.left == null && root.right == null){
                return sum;
            }else{
                return dfs(root.left, sum) + dfs(root.right, sum);
            }
        }
    }
}
