package leetcode.程序员面试金典;
//在经典汉诺塔问题中，有 3 根柱子及 N 个不同大小的穿孔圆盘，盘子可以滑入任意一根柱子。
// 一开始，所有盘子自上而下按升序依次套在第一根柱子上(即每一个盘子只能放在更大的盘子上面)。移动圆盘时受到以下限制:
//(1) 每次只能移动一个盘子;
//(2) 盘子只能从柱子顶端滑出移到下一根柱子;
//(3) 盘子只能叠在比它大的盘子上。
//
//请编写程序，用栈将所有盘子从第一根柱子移到最后一根柱子。
//
//你需要原地修改栈。
//
//示例1:
//
// 输入：A = [2, 1, 0], B = [], C = []
// 输出：C = [2, 1, 0]
//示例2:
//
// 输入：A = [1, 0], B = [], C = []
// 输出：C = [1, 0]
//提示:
//
//A中盘子的数目不大于14个。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/hanota-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


import java.util.List;
import java.util.Stack;

/**
 * @author mayanwei
 * @date 2023-05-26.
 */
public class _08_06_汉诺塔问题{
    /**
     * <pre>
     *         │              │            │
     *       ┌─┼─┐            │            │
     *      ┌┴─┼─┴┐           │            │
     *     ┌┴──┼──┴┐          │            │
     *    ┌┴───┼───┴┐         │            │
     *   ┌┴────┼────┴┐        │            │
     * ──┴─────┴─────┴────────┴────────────┴────
     * 从最简单的例子 n = 1 开始，
     * 当 n=1 时，能否将盘子1从1移至3？ 可以，只需要一步即可。
     * 当 n=2 时，能否将盘子1和盘子2从柱1移至柱3？可以。
     * (1)盘子1:1 -> 2
     * (2)盘子2:1 -> 3
     * (2)盘子1:2 -> 3
     * 当 n=3 时，能否将盘子1，2，3 从1移动至3？可以。
     * (1)从上面可知，可以将上面的两个盘子从一根柱子移动到另一根柱子，因此假设已经这么做了，只不过，这里是将两个盘子移至柱2。
     * (2)盘子3:1 -> 3
     * (3)将盘子1,2: 2 -> 3，重复步骤(1)即可。
     * 当 n=4 时，能否将盘子1，2，3，4 从1移动至3？可以
     * (1)将1,2,3移至柱2.具体做法见上面的例子。
     * (2)将4:1 -> 3
     * (3)将1,2,3: 2 -> 3
     * 注意：柱2 和柱3 之间并无多大区别，只是叫法不一样，实则是等价的。把柱2作为缓冲将盘子移至柱3，与把柱3作为缓冲将盘子移至柱2，
     * 两者并无区别。
     * </pre>
     */


    static class Tower{
        private int index;
        // 栈模拟盘子
        private Stack<Integer> disks;

        public Tower(int index) {
            disks = new Stack<>();
            this.index = index;
        }

        public void add(int d) {
            if (!disks.isEmpty() && disks.peek() <= d) {
                System.out.println("Error placing disks " + d);
            }
            else {
                disks.push(d);
            }
        }

        void moveDisks(int n, Tower destination, Tower buffer) {
            if (n > 0) {
                // 将顶端n-1个盘子从origin 移至buffer,将destination用作缓冲区
                moveDisks(n - 1, buffer, destination);
                // 将origin 顶端的盘子移至destination
                moveTopTo(destination);
                // 将顶部n-1个盘子从buffer移至destination，将origin 用作缓冲区
                buffer.moveDisks(n - 1, destination, this);
            }
        }

        private void moveTopTo(Tower destination) {
            int top = disks.pop();
            destination.add(top);
        }

    }

    public static void main(String[] args) {
        int n = 3;
        Tower[] towers = new Tower[n];
        for (int i = 0; i < 3; i++) {
            towers[i] = new Tower(i);
        }
        for (int i = n - 1; i >= 0; i--) {
            towers[0].add(i);
        }
        towers[0].moveDisks(n, towers[2], towers[1]);
        System.out.println(towers[2].disks.toString());
    }


    class Solution{
        public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
            int n = A.size();
            hanhuoHelper(n, A, B, C);
        }
        void hanhuoHelper(int n,
                          List<Integer> source,
                          List<Integer> buffer,
                          List<Integer> destination) {
            if (n == 1) {
                destination.add(source.remove(source.size() - 1));
                return;
            }
            hanhuoHelper(n - 1, source, destination, buffer);
            destination.add(source.remove(source.size() - 1));
            hanhuoHelper(n - 1, buffer, source, destination);
        }

    }
}
