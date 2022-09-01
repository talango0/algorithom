package leetcode.tree;
//给你一棵二叉搜索树，请你返回一棵平衡后的二叉搜索树，新生成的树应该与原来的树有着相同的节点值。如果有多种构造方法，请你返回任意一种。
//
//如果一棵二叉搜索树中，每个节点的两棵子树高度差不超过 1 ，我们就称这棵二叉搜索树是平衡的 。
//
//
//
//示例 1：
//
//
//
//输入：root = [1,null,2,null,3,null,4,null,null]
//输出：[2,1,3,null,null,null,4]
//解释：这不是唯一的正确答案，[3,1,4,null,2,null,null] 也是一个可行的构造方案。
//示例 2：
//
//
//
//输入: root = [2,1,3]
//输出: [2,1,3]
//
//
//提示：
//
//树节点的数目在[1, 104]范围内。
//1 <= Node.val <= 105
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/balance-a-binary-search-tree
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.ArrayList;
import java.util.List;

/**
 * @see _108_将有序数组转换为二叉搜索树
 *
 */
public class _1382_将二叉搜索树变平衡 {

    class Solution {
        //这题可难可简单，如果要难，就是红黑树左旋右旋那一套，不过真的没必要这么搞。
        //我们简单点，就是用中序遍历获取 BST 的有序结果，然后用 108. 将有序数组转换为二叉搜索树 的解法代码，将这个有序数组转化成平衡 BST。
        public TreeNode balanceBST(TreeNode root) {
            //中序遍历获取有序数组
            List<Integer> nums = traverse(root);
            //从有序遍历构建BST
            return build(nums, 0, nums.size()-1);
        }
        // 返回中序遍历结果
        List<Integer> traverse(TreeNode root) {
            List<Integer> res = new ArrayList<Integer>();
            if (root == null) {
                return res;
            }
            res.addAll(traverse(root.left));
            res.add(root.val);
            res.addAll(traverse(root.right));
            return res;
        }

        TreeNode build(List<Integer> nums, int left, int right) {
            //有序递增区间[left, right] 中间位置为根节点，左侧为该根左子树，右侧为该根右子树
            if (left > right) {
                // 区间为空
                return null;
            }
            int mid = (left+right)/2;
            // 构造根root
            TreeNode root = new TreeNode(nums.get(mid));
            // 构造左子树
            root.left = build(nums, left, mid-1);
            // 构造右子树
            root.right = build(nums, mid+1, right);
            return root;
        }
    }

    class Solution2{
        //省去不必要的创建对象过程
        List<TreeNode> list = new ArrayList<>();
        public TreeNode balanceBST(TreeNode root) {
            process(root);
            return build(list,0,list.size()-1);
        }

        private TreeNode build(List<TreeNode> list,int begin, int end) {
            if (begin>end){
                return null;
            }
            if (begin==end){
                TreeNode treeNode = list.get(begin);
                treeNode.right=null;
                treeNode.left=null;
                return treeNode;
            }
            int mid = begin+(end-begin)/2;
            TreeNode ans = list.get(mid);
            ans.left=build(list,begin,mid-1);
            ans.right=build(list,mid+1,end);

            return ans;
        }

        private void process(TreeNode root) {
            if (root==null){
                return;
            }
            process(root.left);
            // 中序位置添加，有序
            list.add(root);
            process(root.right);
        }
    }
}
