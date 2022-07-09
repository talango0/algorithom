package leetcode.tree;
//给定一棵二叉树 root，返回所有重复的子树。
//
// 对于同一类的重复子树，你只需要返回其中任意一棵的根结点即可。
//
// 如果两棵树具有相同的结构和相同的结点值，则它们是重复的。
//
//
//
// 示例 1：
//
//
//
//
//输入：root = [1,2,3,4,null,2,4,null,null,4]
//输出：[[2,4],[4]]
//
// 示例 2：
//
//
//
//
//输入：root = [2,1,1]
//输出：[[1]]
//
// 示例 3：
//
//
//
//
//输入：root = [2,2,2,3,null,3,null]
//输出：[[2,3],[3]]
//
//
//
// 提示：
//
//
// 树中的结点数在[1,10^4]范围内。
// -200 <= Node.val <= 200
//
// Related Topics 树 深度优先搜索 哈希表 二叉树 👍 436 👎 0


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
public class _652_寻找重复的子树 {
    class Solution {
        //记录所有子树以及出现的次数
        HashMap <String, Integer> memo = new HashMap<>();
        //记录重复的子树根节点
        LinkedList<TreeNode> res = new LinkedList<>();
        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            traverse(root);
            return res;
        }
        //辅助函数
        String traverse(TreeNode root) {
            if (root == null) {
                return "#";
            }
            String left = traverse(root.left);
            String right = traverse(root.right);
            String subTree = left+ ","+ right + "," + root.val;
            Integer freq = memo.getOrDefault(subTree, 0);
            //多次重复只会被加进去一次
            if (freq == 1) {
                res.add(root);
            }
            //给子树对应的次数加1
            memo.put(subTree, freq + 1);
            return subTree;
        }
    }
}
