package leetcode.程序员面试金典;
//设计并实现一个算法，找出二叉树中某两个节点的第一个共同祖先。不得将其他的节点存储在另外的数据结构中。注意：这不一定是二叉搜索树。
//
//例如，给定如下二叉树: root = [3,5,1,6,2,0,8,null,null,7,4]
//    3
//   / \
//  5   1
// / \ / \
//6  2 0  8
//  / \
// 7   4
//
//示例 1:
//输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
//输出: 3
//解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
//
//示例 2:
//输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
//输出: 5
//解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。
//说明:
//
//所有节点的值都是唯一的。
//p、q 为不同节点且均存在于给定的二叉树中。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/first-common-ancestor-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.tree.TreeNode;

/**
 * @author mayanwei
 * @date 2023-05-07.
 */
public class _04_08_首个共同祖先{
    /**
     * 小心！你的算法处理只有一个节点的情况吗？会发生什么事？你可能要微调返回值。
     * 如果每个节点都有一个到其父节点的链接，我们可以利用9.2节问题2.7的方法。然而，面试官可能不会让我们作出这样的假设。
     * 第一个共同的祖先是最深的节点，这样p和q都是后代。想想你要如何识别这个节点。
     * 你如何弄清p是否为节点n的后代？
     * 从根节点开始。你能确定根是第一个共同祖先吗？如果不是，你能分辨出第一个共同祖先在根节点的哪一边吗？
     * 尝试递归方法。检查p和q是否为左子树和右子树的后代。如果它们是不同的树的后代，那么当前节点是第一个共同的祖先。如果它们是同一子树的后代，则该子树保存第一个共同祖先。现在，你该如何有效地实现它呢？
     * 在更简单的算法中，我们有一个方法表明x是n的后代，另一个方法是递归查找第一个共同的祖先。这样是在子树中反复搜索相同的元素。我们应该将其合并成一个firstCommonAncestor方法。那么什么样的返回值会给我们需要的信息？
     * firstCommonAncestor函数可以返回第一个共同的祖先（如果p和q都包含在树里），如果p在树上而q不在，返回p；如果q在树上而p不在，返回q；否则，返回空。
     */
    class Solution {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            return find(root, p.val, q.val);
        }
        private TreeNode find(TreeNode root, int val1, int val2) {
            if (root == null) {
                return null;
            }
            if (root.val == val1 || root.val == val2) {
                return root;
            }
            TreeNode left = find(root.left, val1, val2);
            TreeNode right  = find(root.right, val1, val2);
            if (left != null && right != null){
                return root;
            }
            return left == null ? right:left;
        }
    }

}
