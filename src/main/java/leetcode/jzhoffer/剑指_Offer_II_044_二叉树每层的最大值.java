package leetcode.jzhoffer;

import leetcode.tree.TreeNode;
import leetcode.tree._515_在每个树行中找最大值;

import java.util.ArrayList;
import java.util.List;

//给定一棵二叉树的根节点 root ，请找出该二叉树中每一层的最大值。 
//
// 
//
// 示例1： 
//
// 
//输入: root = [1,3,2,5,3,null,9]
//输出: [1,3,9]
//解释:
//          1
//         / \
//        3   2
//       / \   \  
//      5   3   9 
// 
//
// 示例2： 
//
// 
//输入: root = [1,2,3]
//输出: [1,3]
//解释:
//          1
//         / \
//        2   3
// 
//
// 示例3： 
//
// 
//输入: root = [1]
//输出: [1]
// 
//
// 示例4： 
//
// 
//输入: root = [1,null,2]
//输出: [1,2]
//解释:      
//          1 
//           \
//            2     
// 
//
// 示例5： 
//
// 
//输入: root = []
//输出: []
// 
//
// 
//
// 提示： 
//
// 
// 二叉树的节点个数的范围是 [0,10⁴] 
// 
// -2³¹ <= Node.val <= 2³¹ - 1 
// 
//
// 
//
// 
// 注意：本题与主站 515 题相同： https://leetcode-cn.com/problems/find-largest-value-in-
//each-tree-row/ 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 32 👎 0


/**
 * @author mayanwei
 * @date 2022-10-30.
 * @see _515_在每个树行中找最大值
 */
public class 剑指_Offer_II_044_二叉树每层的最大值{
    class Solution{
        // 采用DFS
        // 采用 array 存储，因为要用索引随机访问
        List<Integer> res = new ArrayList<>();

        public List<Integer> largestValues(TreeNode root) {
            dfs(root, 0);
            return res;
        }

        public void dfs(TreeNode root, int depth) {
            if (root == null) {
                return;
            }
            if (res.size() <= depth) {
                res.add(root.val);
            }
            else {
                res.set(depth, Math.max(res.get(depth), root.val));
            }

            dfs(root.left, depth + 1);
            dfs(root.right, depth + 1);
        }
    }
}
