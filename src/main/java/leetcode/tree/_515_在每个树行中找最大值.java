package leetcode.tree;
//给定一棵二叉树的根节点 root ，请找出该二叉树中每一层的最大值。
//
//
//
// 示例1：
//
//
//
//
//输入: root = [1,3,2,5,3,null,9]
//输出: [1,3,9]
//
//
// 示例2：
//
//
//输入: root = [1,2,3]
//输出: [1,3]
//
//
//
//
// 提示：
//
//
// 二叉树的节点个数的范围是 [0,10⁴]
// -2³¹ <= Node.val <= 2³¹ - 1
//
//
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 259 👎 0


import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
public class _515_在每个树行中找最大值 {
    class Solution {
        public List<Integer> largestValues0(TreeNode root) {
            List<Integer> res = new LinkedList<Integer>();
            if (root == null) {
                return res;
            }
            Deque<TreeNode> stk = new LinkedList<TreeNode>();
            stk.offer(root);
            //while 循环控制从上向下一层层遍历
            while (!stk.isEmpty()) {
                int sz = stk.size();
                int max = Integer.MIN_VALUE;
                //for循环控制每一层的从左向右遍历
                for (int i=0; i<sz; i++) {
                    TreeNode node = stk.poll();
                    TreeNode left = node.left;
                    TreeNode right = node.right;
                    if(left != null){
                        stk.offer(left);
                    }
                    if(right != null) {
                        stk.offer(right);
                    }
                    max = Math.max(max, node.val);

                }
                res.add(max);
            }
            return res;
        }



        //采用DFS
        //要用 array 存储，因为要用索引随机访问
        List<Integer> res = new ArrayList<Integer>();
        public List<Integer> largestValues(TreeNode root) {
            dfs(root, 0);
            return res;
        }
        void dfs(TreeNode root, int depth) {
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
