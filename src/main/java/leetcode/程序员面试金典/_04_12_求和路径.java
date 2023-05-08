package leetcode.程序员面试金典;
//给定一棵二叉树，其中每个节点都含有一个整数数值(该值或正或负)。设计一个算法，打印节点数值总和等于某个给定值的所有路径的数量。
// 注意，路径不一定非得从二叉树的根节点或叶节点开始或结束，但是其方向必须向下(只能从父节点指向子节点方向)。
//
//示例:
//给定如下二叉树，以及目标和sum = 22，
//
//              5
//             / \
//            4   8
//           /   / \
//          11  13  4
//         /  \    / \
//        7    2  5   1
//返回:
//
//3
//解释：和为 22的路径有：[5,4,11,2], [5,8,4,5], [4,11,7]
//提示：
//
//节点总数 <= 10000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/paths-with-sum-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.tree.TreeNode;

import java.util.HashMap;

/**
 * @author mayanwei
 * @date 2023-05-08.
 */
public class _04_12_求和路径{
    // 提示：
    //尝试简化问题。如果路径必须从根开始会如何？
    //不要忘记路径可能会重叠。例如，如果你正在寻找总和6，那么路径1 -> 3 -> 2和1 -> 3 -> 2 -> 4 -> 6 -> 2都是有效的。
    //如果每条路径必须从根开始，就从根开始遍历所有可能的路径。可以在遍历的同时追踪和，每次找到一个路径满足我们的目标和，就增加totalpaths的值。现在，如何将它扩展到可以在任何地方开始呢？记住：只需要一个蛮力算法即可完成。你可以稍后再优化。
    //为了将其扩展到从任何地方开始的路径，我们可以对所有节点重复此过程。
    //如果你已经设计了以上描述的算法，那么在平衡树中你会有一个O(NlogN)的算法。这是因为共N个节点，在最坏情况下，每个节点的深度是O(logN)。节点上方的每个节点都会访问一次。因此，N个节点将被访问O(logN)的时间。有一种优化算法，其运行时间为O(N)。
    //在当前的蛮力算法中重复了什么工作？
    //从根开始考虑每个路径（有n个这样的路径）作为一个数组。该蛮力算法具体运作如下：拿着每个数组来寻找所有具有特定和的连续子序列。我们这样做是计算了所有子数组以及它们的和。把目光聚焦在这个小问题上可能会大有裨益。给定一个数组，你如何寻找具有特定和的所有连续子序列？同样，想想蛮力算法中的重复工作。
    //我们正在寻找和为targetSum的子数组。注意，可以在常数时间得到runningSumi的值，这是从元素0到元素i的和。一个从i到j的子数组和为targetSum，则 runningSumi-1 + targetSum必须等于runningSumj（试着画一个数组或一条数字线）。随着往下走，可以追踪runningSum，那么如何能快速查找i对应的使前面等式成立的值？
    //尝试使用一个散列表，从runningSum的值映射到使用runningSum元素的个数。
    //一旦你完成了这样的算法，找出了和为给定值的所有连续子数组，试着将它应用到一棵树上。请记住，在遍历和修改散列表时，你可能需要在遍历回来时将散列表的“损坏”逆转。


    /**
     * 暴力法
     */
    class Solution1{
        public int pathSum(TreeNode root, int sum) {
            if (root == null) {
                return 0;
            }
            // 从root 开始，符合目标和的路径总数
            int count = countPathSumFromRoot(root, sum, 0);
            // 尝试左节点和右节点
            int leftCount = pathSum(root.left, sum);
            int rightCount = pathSum(root.right, sum);
            return count + leftCount + rightCount;
        }

        /**
         * 从root开始路径和为 targetSum 的路径总数
         */
        private int countPathSumFromRoot(TreeNode root, int targetSum, int pathSum) {
            if (root == null) {
                return 0;
            }
            int totalPaths = 0, currentSum = pathSum + root.val;

            if (currentSum == targetSum) {
                totalPaths++;
            }
            totalPaths += countPathSumFromRoot(root.left, targetSum, currentSum);
            totalPaths += countPathSumFromRoot(root.right, targetSum, currentSum);
            return totalPaths;
        }
    }


    class Solution2{
        // 记录前缀和
        HashMap<Long, Integer> preSumCount = new HashMap<>();
        long pathSum, targetSum;
        int res = 0;

        public int pathSum(TreeNode root, int sum) {
            if (root == null) {
                return 0;
            }
            this.pathSum = 0;
            this.targetSum = sum;
            this.preSumCount.put(0L, 1);
            traverse(root);
            return res;
        }

        private void traverse(TreeNode root) {
            if (root == null) {
                return;
            }
            // 前序位置
            pathSum += root.val;
            res += preSumCount.getOrDefault(pathSum - targetSum, 0);
            preSumCount.put(pathSum, preSumCount.getOrDefault(pathSum, 0) + 1);
            traverse(root.left);
            traverse(root.right);

            // 后序位置
            preSumCount.put(pathSum, preSumCount.get(pathSum) - 1);
            pathSum -= root.val;
        }
    }


    /**
     * 该算法的运行时间为 O(N)，其中 N 是树中节点的个数。之所以得出 O(N)的结论，是因为我们只对每个节点进行一次放问，
     * 并在每个节点处只完成 O(1)的计算工作。对于一棵平衡树， 由于使用了散列表，因此空间复杂度为 O(log N)。
     * 对于非平衡树，空间复杂度可以增长至 O(N)。
     */
    class Solution{
        public int pathSum(TreeNode root, int sum) {
            return countPathsWithSum(root, sum, 0, new HashMap<Integer, Integer>());
        }

        private int countPathsWithSum(TreeNode root, int targetSum, int runningSum, HashMap<Integer, Integer> pathCount) {
            if (root == null) {
                return 0;
            }

            /* 对终止于该节点，符合目标和路径进行计数 */
            runningSum += root.val;
            int sum = runningSum - targetSum;
            int totalPaths = pathCount.getOrDefault(sum, 0);

            /* 如果 runningSum 等于 targetSum，则发现一条从 root 开始的新路径。加上这条路径 */
            if (runningSum == targetSum) {
                totalPaths++;
            }

            /* 对 pathCount 加1,递归,对pathCount 减1 */
            increamentHashTable(pathCount, runningSum, 1);
            totalPaths += countPathsWithSum(root.left, targetSum, runningSum, pathCount);
            totalPaths += countPathsWithSum(root.right, targetSum, runningSum, pathCount);
            increamentHashTable(pathCount, runningSum, -1);
            return totalPaths;
        }

        private void increamentHashTable(HashMap<Integer, Integer> hashTable, int key, int delta) {
            int newCount = hashTable.getOrDefault(key, 0) + delta;
            if (newCount == 0) { // 等值为0时删除此健以减少空间使用
                hashTable.remove(key);
            }
            else {
                hashTable.put(key, newCount);
            }
        }
    }


}
