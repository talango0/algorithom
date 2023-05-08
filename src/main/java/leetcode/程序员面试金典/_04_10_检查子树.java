package leetcode.程序员面试金典;
//检查子树。你有两棵非常大的二叉树：T1，有几万个节点；T2，有几万个节点。设计一个算法，判断 T2 是否为 T1 的子树。
//
//如果 T1 有这么一个节点 n，其子树与 T2 一模一样，则 T2 为 T1 的子树，也就是说，从节点 n 处把树砍断，得到的树与 T2 完全相同。
//
//注意：此题相对书上原题略有改动。
//
//示例1:
// 输入：t1 = [1, 2, 3], t2 = [2]
// 输出：true
//
//示例2:
// 输入：t1 = [1, null, 2, 4], t2 = [3, 2]
// 输出：false
//提示：
//
//树的节点数目范围为[0, 20000]。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/check-subtree-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.tree.TreeNode;

/**
 * @author mayanwei
 * @date 2023-05-08.
 */
public class _04_10_检查子树{
    //如果T2是T1的子树，它的中序遍历将如何与T1的比较？它的前序和后序遍历如何？
    //中序遍历无法告诉我们更多。毕竟，每个具有相同值的二叉搜索树（不管结构如何）将具有相同的中序遍历。这也就是中序遍历的含义：内容是有序的（如果它在二叉搜索树这种特定情况下不起作用，那么对于一般二叉树来说它肯定不起作用）。然而，前序遍历更具指示性。
    //你可能得出结论，如果T2.preorderTraversal()是T1.preorderTraversal()的子字符串，则T2是T1的子树。这几乎是事实，除非树可能有重复的值。假设T1和T2具有所有重复值，但结构不同。即使T2不是T1的子树，前序遍历看起来也是一样的。你如何处理这样的情况？
    //尽管问题似乎源于重复的值，但不止如此。问题是，前序遍历是相同的，只是因为我们跳过了空节点（因为它们是空的）。考虑在访问到空节点时往前序遍历的字符串中插入一个占位符。把空节点记录为一个“真正的”节点，你就可以区分出不同的结构了。
    //或者用递归法处理这个问题。给定一个特殊节点T1，可以检查它的子树是否匹配T2吗？

    /**
     * 思路1:利用前序遍历，时间复杂度O(m+n),空间复杂度O(m+n),其中n和m分别为 t1 和 t2 中的节点数目。
     * 因为可能会有数以􏱲万计的节点，我们或许希望能够降低该解法的􏰳间复杂度。
     */
    class Solution{
        public boolean checkSubTree(TreeNode t1, TreeNode t2) {
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            getPreOrder(t1, sb1);
            getPreOrder(t2, sb2);
            return sb1.toString().indexOf(sb2.toString()) != -1;
        }

        private void getPreOrder(TreeNode root, StringBuilder str) {
            if (root == null) {
                str.append("X");
                return;
            }
            str.append(root.val + " ");
            getPreOrder(root.left, str);
            getPreOrder(root.right, str);
        }
    }

    /**
     * <pre>
     * 另一种解法：搜遍较大的那棵树t1,每当 t1 的某个节点与 t2 的根节点匹配时，调用 treeMath，treeMath 方法会比较两个子树，检查二者是否相同。
     * 时间复杂度：粗略的看时 O(mn),但细分析一下运行时间更接近 O(n + km) 其中k为 t2 在 t1 中出现的次数，n 和 m分别为 t1, t2 节点数。
     * 空间复杂度：O(log(n) + log(m))
     * 注意
     * (1)简单解法会􏱒用 O(n + m)的内存，另一种解法则用 O(log(n) + log(m))的内存。记住: 要求可扩展性时，内存使用多􏱧关系重大。
     * (2) 简单解法的时间复杂度为 O(n + m)，另一种解法在最差情况下的执行时间为 O(nm)。话 说回来，只看最差情况的时间复杂度会有误导性，我们需要进一步观察。
     * (3) 如前所述，比较准确的运行时间为 O(n + km)，其中 k 为 T2 根节点在 T1 中出现的次数。 假设T1和T2的节点数据为0和p之间的随机数，
     * 则k大约为n/p，为什么?因为T1有n个 节点，每个节点有 1/p 的概率与 T2 根节点相同，因此，T1 中大􏱨有 n / p 个节点等于 T2 根节点
     * (T2.root)。举个例子，假设 p = 1000，n = 1 000 000 且 m = 100。我们需要检查的节点数量大数量约为
     * 1 100 000(1 100 000 = 1 000 000 + 100􏱪1 000 000 / 1000)。
     * 总的来说，在空间使用上，另一种解法显然较好，在时间复杂度上，也可能比简单解法更
     * 优。一切都取决于你作出哪些假设，以及要不要考虑􏱫􏱬最差情况的运行时间，来减少平􏱭情
     * 况的运行时间。
     * </pre>
     */
    class Solution2{
        class Solution{
            public boolean checkSubTree(TreeNode t1, TreeNode t2) {
                if (t2 == null) {  // 空树为任何树的子树
                    return true;
                }
                return subTree(t1, t2);
            }

            private boolean subTree(TreeNode t1, TreeNode t2) {
                if (t1 == null) { // t1 为空，且在不匹配的情况下
                    return false;
                }
                else if (t1.val == t2.val && matchTree(t1, t2)) {
                    return true;
                }
                return subTree(t1.left, t2) || subTree(t1.right, t2);
            }

            private boolean matchTree(TreeNode t1, TreeNode t2) {
                if (t1 == null && t2 == null) {
                    return true; // 子树无更多节点
                }
                else if (t1 == null || t2 == null) {
                    return false; // 其中一个树为空树
                }
                else if (t1.val != t2.val) {
                    return false; // 根节点不匹配
                }
                else {
                    return matchTree(t1.left, t2.left) && matchTree(t1.right, t2.right);
                }
            }
        }
    }
}
