package leetcode.math;
//在考场里，一排有 N 个座位，分别编号为 0, 1, 2, ..., N-1 。
//
// 当学生进入考场后，他必须坐在能够使他与离他最近的人之间的距离达到最大化的座位上。如果有多个这样的座位，他会坐在编号最小的座位上。(另外，如果考场里没有人，
//那么学生就坐在 0 号座位上。)
//
// 返回 ExamRoom(int N) 类，它有两个公开的函数：其中，函数 ExamRoom.seat() 会返回一个 int （整型数据），代表学生坐的位
//置；函数 ExamRoom.leave(int p) 代表坐在座位 p 上的学生现在离开了考场。每次调用 ExamRoom.leave(p) 时都保证有学生坐在
//座位 p 上。
//
//
//
// 示例：
//
// 输入：["ExamRoom","seat","seat","seat","seat","leave","seat"], [[10],[],[],[],[]
//,[4],[]]
//输出：[null,0,9,4,2,null,5]
//解释：
//ExamRoom(10) -> null
//seat() -> 0，没有人在考场里，那么学生坐在 0 号座位上。
//seat() -> 9，学生最后坐在 9 号座位上。
//seat() -> 4，学生最后坐在 4 号座位上。
//seat() -> 2，学生最后坐在 2 号座位上。
//leave(4) -> null
//seat() -> 5，学生最后坐在 5 号座位上。
//
//
//
//
// 提示：
//
//
// 1 <= N <= 10^9
// 在所有的测试样例中 ExamRoom.seat() 和 ExamRoom.leave() 最多被调用 10^4 次。
// 保证在调用 ExamRoom.leave(p) 时有学生正坐在座位 p 上。
//
//
// Related Topics 设计 有序集合 👍 131 👎 0

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author mayanwei
 * @date 2022-08-10.
 */
public class _855_考场就座{
    // 单凡遇到在动态过程中取最值的要求，肯定要使用有序数据结构，常用到的数据结构就是二叉堆和平衡二叉搜索树
// 二叉堆实现的优先级队列取最值的时间复杂度为 O(logn)但只能删除最大值，平衡二叉树也可以取最值，也可以修改、删除任意值，而且时间复杂度都是 O(logN)
// 综上，二叉堆不能满足leave 操作，应该使用平和二叉树，这里会用到 Java 的一种数据结构 TreeSet，这是一种有序数据结构，底层时由红黑树维护的有序性
// ，一说到集合（Set）或者映射（Map），有的读者可能就想当然的认为是哈希集合（HashSet）或者哈希表（HashMap），这样理解是有点问题的。
// 因为哈希集合/映射底层是由哈希函数和数组实现的，特性是遍历无固定顺序，但是操作效率高，时间复杂度为 O(1)。
// 而集合/映射还可以依赖其他底层数据结构，常见的就是红黑树（一种平衡二叉搜索树），特性是自动维护其中元素的顺序，操作效率是 O(logN)。这种一般称为「有序集合/映射」


    /**首先需要把坐在教室的学生抽象成线段，我们可以简单的用一个大小为 2 的数组表示。另外，思路需要我们找到「最长」的线段，还需要去除线段，增加线段。*/
    class ExamRoom {
        // 将端点 p 映射到以 p 为左端点的线段
        private Map<Integer, int[]> startMap;
        // 将端点 p 映射到以 p 为右端点的线段
        private Map<Integer, int[]> endMap;
        // 根据线段大小从小到大存放所有线段
        private TreeSet<int[]> pq;
        private int n;
        public ExamRoom(int n) {
            this.n = n;
            this.startMap = new HashMap<>();
            this.endMap = new HashMap<>();
            pq = new TreeSet<>((a, b) -> {
                // 算出两个线段长度
                int distA = distance(a);
                int distB = distance(b);
                // 如果长度相同，就比较索引
                if (distA == distB) {
                    return b[0]-a[0];
                }
                // 长度更长的放在后面
                return distA - distB;
            });
            //在有序集合中先放一个虚拟线段
            addInterval(new int[]{-1, n});
        }

        /**去除一个线段 */
        private void removeInterval(int [] intv) {
            pq.remove(intv);
            startMap.remove(intv[0]);
            endMap.remove(intv[1]);
        }

        /**增加一个线段 */
        private  void addInterval(int [] intv) {
            pq.add(intv);
            startMap.put(intv[0], intv);
            endMap.put(intv[1], intv);
        }

        /**计算一个线段长度 不能简单地让它计算一个线段两个端点间的长度，而是让它计算该线段中点和端点之间的长度。*/
        private int distance(int [] intv) {
            int x = intv[0];
            int y = intv[1];
            if (x == -1) {
                return y;
            }
            else if (y == n) {
                return n-1-x;
            }
            // 中点和端点之间的长度
            return (y - x) / 2;
        }

        public int seat() {
            // 从有序集拿出最长的线段
            int [] longest = pq.last();
            int x = longest[0];
            int y = longest[1];
            int seat;
            if (x == -1) {
                seat = 0;
            }
            else if(y == n) {
                seat = n-1;
            }
            else {
                seat = (y-x)/2 + x;
            }

            // 将最长的线段分成两段
            int [] left = new int[] {x, seat};
            int [] right = new int[] {seat, y};
            removeInterval(longest);
            addInterval(left);
            addInterval(right);
            return seat;
        }

        public void leave(int p) {
            // 将 pp 左右的线段找出来
            int [] right = startMap.get(p);
            int [] left = endMap.get(p);
            // 合并两个线段成为一个线段
            int [] merge = new int[]{left[0], right[1]};

            removeInterval(left);
            removeInterval(right);
            addInterval(merge);
        }
    }

/**
 * Your ExamRoom object will be instantiated and called as such:
 * ExamRoom obj = new ExamRoom(n);
 * int param_1 = obj.seat();
 * obj.leave(p);
 */
}
