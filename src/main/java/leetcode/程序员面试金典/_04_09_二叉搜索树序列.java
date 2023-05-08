package leetcode.程序员面试金典;
//从左向右遍历一个数组，通过不断将其中的元素插入树中可以逐步地生成一棵二叉搜索树。
//
//给定一个由不同节点组成的二叉搜索树 root，输出所有可能生成此树的数组。
//
//
//
//示例 1:
//
//输入: root = [2,1,3]
//输出: [[2,1,3],[2,3,1]]
//解释: 数组 [2,1,3]、[2,3,1] 均可以通过从左向右遍历元素插入树中形成以下二叉搜索树
//      2 
//     / \ 
//    1   3
//示例2:
//
//输入: root = [4,1,null,null,3,2]
//输出: [[4,1,3,2]]
//
//
//提示：
//
//二叉搜索树中的节点数在[0, 1000]的范围内
//1 <= 节点值<= 10^6
//用例保证符合要求的数组数量不超过 5000
//
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/bst-sequences-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2023-05-07.
 */
public class _04_09_二叉搜索树序列{
    class Solution{

        public List<List<Integer>> BSTSequences(TreeNode node) {
            List<List<Integer>> result = new ArrayList<>();
            if (node == null) {
                result.add(new LinkedList<>());
                return result;
            }
            LinkedList<Integer> prefix = new LinkedList<>();
            prefix.add(node.val);

            // 对左右子树递归
            List<List<Integer>> leftSeq = BSTSequences(node.left);
            List<List<Integer>> rightSeq = BSTSequences(node.right);

            // 从链表的左右两端交替计算
            for (List<Integer> left : leftSeq) {
                for (List<Integer> right : rightSeq) {
                    List<List<Integer>> weaved = new ArrayList<>();
                    weaveList(new LinkedList<>(left), new LinkedList<>(right), weaved, prefix);
                    result.addAll(weaved);
                }
            }
            return result;
        }

        /**
         * 以所以可能的方式对链表同时交替计算。该算法从一个链表的头部移除元素，递归，并对另一个链表做相同的操作。
         *
         * @param first
         * @param second
         * @param results
         * @param prefix
         */
        private void weaveList(LinkedList<Integer> first, LinkedList<Integer> second,
                               List<List<Integer>> results, LinkedList<Integer> prefix) {
            // first 为空或者 second 为空，将剩余部分加入到（复制后）prefix 中，并储存结果
            if (first.size() == 0 || second.size() == 0) {
                LinkedList<Integer> result = (LinkedList<Integer>) prefix.clone();
                result.addAll(first);
                result.addAll(second);
                results.add(result);
                return;
            }

            // 将 first 的头部加入到 prefix 后进行递归。移除头部元素会破坏 first，因此我们需要在后续操作时将元素放回。
            int headFirst = first.removeFirst();
            prefix.addLast(headFirst);
            weaveList(first, second, results, prefix);
            prefix.removeLast();
            first.addFirst(headFirst);

            // 对 second 做相同操作
            int headSecond = second.removeFirst();
            prefix.addLast(headSecond);
            weaveList(first, second, results, prefix);
            prefix.removeLast();
            second.addFirst(headSecond);
        }
    }
}

class Solution2{

    public List<List<Integer>> BSTSequences(TreeNode root) {
        if (root == null) {
            List<List<Integer>> empty = new ArrayList<>();
            empty.add(new ArrayList<>());
            return empty;
        }
        List<List<Integer>> listl = BSTSequences(root.left);
        List<List<Integer>> listr = BSTSequences(root.right);
        List<List<Integer>> merge;
        //特殊情况不用merge减少递归次数
        if ((listl.size() == 1 && listl.get(0).size() == 0) || (listr.size() == 1 && listr.get(0).size() == 0)) {
            merge = listl.get(0).size() == 0 ? listr :listl;
        }
        else merge = merge(listl, listr);
        for (List<Integer> list : merge) {
            list.add(0, root.val);
        }
        return merge;
    }

    private List<List<Integer>> merge(List<List<Integer>> lList, List<List<Integer>> rList) {
        List<List<Integer>> res = new ArrayList<>();
        for (List<Integer> l1 : lList) {
            for (List<Integer> l2 : rList) {
                res.addAll(relativeMerge(l1, l2, 0));
            }
        }
        return res;
    }

    // 回溯进行相对排序
    private List<List<Integer>> relativeMerge(List<Integer> listl, List<Integer> listr, int start) {
        List<List<Integer>> res = new ArrayList<>();
        if (listr.size() == 0) {
            res.add(listl);
            return res;
        }
        int lenl = listl.size();
        //一个长度为lenl的list首尾都可以插入，所以可以插入的点实际上是lenl+1
        for (int j = start; j <= lenl; j++) {
            listl.add(j, listr.get(0));
            Integer remove = listr.remove(0);
            res.addAll(relativeMerge(new ArrayList<>(listl), new ArrayList<>(listr), j + 1));
            listl.remove(j);
            listr.add(0, remove);
        }
        return res;
    }
}

class Solution3{
    private List<List<Integer>> ans;

    public List<List<Integer>> BSTSequences(TreeNode root) {
        ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        // 如果 root==null 返回 [[]]
        if (root == null) {
            ans.add(path);
            return ans;
        }
        List<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        // 开始进行回溯
        bfs(queue, path);
        return ans;
    }

    /**
     * 回溯法+广度优先遍历.
     */
    private void bfs(List<TreeNode> queue, List<Integer> path) {
        // queue 为空说明遍历完了，可以返回了
        if (queue.isEmpty()) {
            ans.add(new ArrayList<>(path));
            return;
        }
        // 将 queue 拷贝一份，用于稍后回溯
        List<TreeNode> copy = new ArrayList<>(queue);
        // 对 queue 进行循环，每循环考虑 “是否 「将当前 cur 节点从 queue 中取出并将其左右子
        // 节点加入 queue ，然后将 cur.val 加入到 path 末尾」 ” 的情况进行回溯
        for (int i = 0; i < queue.size(); i++) {
            TreeNode cur = queue.get(i);
            path.add(cur.val);
            queue.remove(i);
            // 将左右子节点加入队列
            if (cur.left != null) queue.add(cur.left);
            if (cur.right != null) queue.add(cur.right);
            bfs(queue, path);
            // 恢复 path 和 queue ，进行回溯
            path.remove(path.size() - 1);
            queue = new ArrayList<>(copy);
        }
    }
}
