package leetcode.程序员面试金典;
//假设你正在读取一串整数。每隔一段时间，你希望能找出数字 x 的秩(小于或等于 x 的值的个数)。请实现数据结构和算法来支持这些操作，也就是说：
//
//实现 track(int x)方法，每读入一个数字都会调用该方法；
//
//实现 getRankOfNumber(int x) 方法，返回小于或等于 x 的值的个数。
//
//注意：本题相对原题稍作改动
//
//示例:
//
//输入:
//["StreamRank", "getRankOfNumber", "track", "getRankOfNumber"]
//[[], [1], [0], [0]]
//输出:
//[null,0,null,1]
//提示：
//
//x <= 50000
//track和getRankOfNumber 方法的调用次数均不超过 2000 次
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/rank-from-stream-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2023-05-31.
 */
public class _10_10_数字流的秩{
    interface IStreamRank{
        void track(int x);

        int getRankOfNumber(int x);
    }


    /**
     * 思路，利用二叉搜索树，做一点变化，以提高效率，在插入的过程集中在每个节点记录一下左子树的个数。
     * 这样中旬查找 x 时能够提高效率
     * track 方法和 getRankOfNumber 方法在平衡树中的运行时间为 O(log n)，在不平衡树中为 O(n)。
     */
    class StreamRank implements IStreamRank{
        private Node root;

        @Override
        public void track(int x) {
            if (root == null) {
                root = new Node(x);
            }
            else {
                root.insert(x);
            }

        }

        @Override
        public int getRankOfNumber(int x) {
            return getRank(root, x);
        }

        private int getRank(Node root, int x) {
            if (root.val == x) {
                return root.leftSize;
            }
            else if (x < root.val) {
                return getRank(root.left, x);
            }
            else if (x > root.val) {
                return getRank(root.right, x) + 1 + root.leftSize;
            }
            return 0;
        }

        class Node{
            Node left;
            Node right;
            int leftSize;
            int val;

            public Node(int val) {
                this.val = val;
            }

            // 插入二叉搜索树
            public void insert(int val) {
                if (this.val < val) {
                    if (left != null) {
                        left.insert(val);
                    }
                    else {
                        left = new Node(val);
                        leftSize++;
                    }
                }
                else {
                    if (right != null) {
                        right.insert(val);
                    }
                    else {
                        right = new Node(val);
                    }
                }
            }

            // 获取
            public int getRank(int val) {
                if (this.val == val) {
                    return leftSize;
                }
                else if (val < this.val) {
                    if (left == null) {
                        return 0;
                    }
                    else {
                        return left.getRank(val);
                    }
                }
                else {
                    int rightRank = right == null ? 0 :right.getRank(val);
                    return rightRank == 0 ? 0 :leftSize + 1 + rightRank;
                }
            }
        }
    }

    class Solution2{
        class StreamRank{
            int[] tree;
            int n = 50001;

            int lowbit(int x) {
                return x & -x;
            }

            public StreamRank() {
                tree = new int[n + 1];
            }

            public void track(int x) {
                for (int i = x + 1; i <= n; i += lowbit(i))
                    tree[i] += 1;
            }

            public int getRankOfNumber(int x) {
                int ans = 0;
                for (int i = x + 1; i > 0; i -= lowbit(i))
                    ans += tree[i];
                return ans;
            }
        }

    }


    // 二分+插入排序
    class Solution3{
        class StreamRank{
            List<Integer> nums;

            public StreamRank() {
                nums = new ArrayList<>();
            }

            public void track(int x) {
                // 插入的位置刚好就是第一个大于x的下标
                int index = getRankOfNumber(x);
                nums.add(index, x);
            }

            public int getRankOfNumber(int x) {
                int n = nums.size();
                if (n == 0) {
                    return 0;
                }
                // 找到第一个大于 x 的下标
                int l = 0, r = n - 1;
                while (l <= r) {
                    int mid = (l + r) >> 1;
                    if (nums.get(mid) <= x) {
                        l = mid + 1;
                    }
                    else if (nums.get(mid) > x) {
                        r = mid - 1;
                    }
                }
                return l;
            }
        }
    }


    // 树状数组
    class Solution4{
        class StreamRank{

            int[] tree;
            int n = 50001;

            public StreamRank() {
                tree = new int[n + 1];
            }

            int lowbit(int x) {
                return x & (-x);
            }

            private void add(int x, int u) {
                for (int i = x; i < tree.length; i += lowbit(i)) {
                    tree[i] += u;
                }
            }

            private int query(int x) {
                int ans = 0;
                for (int i = x; i > 0; i -= lowbit(i)) {
                    ans += tree[i];
                }
                return ans;
            }

            public void track(int x) {
                add(x + 1, 1);
            }

            public int getRankOfNumber(int x) {
                return query(x + 1);
            }
        }
    }


}
