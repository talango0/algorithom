package leetcode.bfs;

//你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9
//' 。每个拨轮可以自由旋转：例如把 '9' 变为 '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
//
// 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
//
// 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
//
// 字符串 target 代表可以解锁的数字，你需要给出解锁需要的最小旋转次数，如果无论如何不能解锁，返回 -1 。
//
//
//
// 示例 1:
//
//
//输入：deadends = ["0201","0101","0102","1212","2002"], target = "0202"
//输出：6
//解释：
//可能的移动序列为 "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202"。
//注意 "0000" -> "0001" -> "0002" -> "0102" -> "0202" 这样的序列是不能解锁的，
//因为当拨动到 "0102" 时这个锁就会被锁定。
//
//
// 示例 2:
//
//
//输入: deadends = ["8888"], target = "0009"
//输出：1
//解释：把最后一位反向旋转一次即可 "0000" -> "0009"。
//
//
// 示例 3:
//
//
//输入: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"],
//target = "8888"
//输出：-1
//解释：无法旋转到目标数字且不被锁定。
//
//
//
//
// 提示：
//
//
// 1 <= deadends.length <= 500
// deadends[i].length == 4
// target.length == 4
// target 不在 deadends 之中
// target 和 deadends[i] 仅由若干位数字组成
//
// Related Topics 广度优先搜索 数组 哈希表 字符串 👍 498 👎 0


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author mayanwei
 * @date 2022-06-18.
 */
public class _752_打开盘锁{
    /**
     * 思路
     * 从 "0000" 开始，转一次，可以穷举出 "1000", "9000", "0100", "0900"... 共 8 种密码。然后，再以这 8 种密码作为基础，对每个密码再转一下，穷举出所有可能…
     * <p>
     * 仔细想想，这就可以抽象成一幅图，每个节点有 8 个相邻的节点，又让你求最短距离，这不就是典型的 BFS 嘛
     * <p>
     * 双向BFS优化：从起点和终点同时开始遍历，当两边有交集的时候停止
     * <p>
     * 双向 BFS 还是遵循 BFS 算法框架的，只是不再使用队列，而是使用 HashSet 方便快速判断两个集合是否有交集。
     * <p>
     * 另外的一个技巧点就是 while 循环的最后交换 q1 和 q2 的内容，所以只要默认扩散 q1 就相当于轮流扩散 q1 和 q2。
     */

    class Solution1{
        public int openLock(String[] deadends, String target) {
            //记住死亡跳
            Set<String> dset = new HashSet<String>();
            for (String s : deadends) {
                dset.add(s);
            }
            //记录已经群举过的，防止再举
            Set<String> visted = new HashSet<>();
            Queue<String> q = new LinkedList<>();
            int step = 0;
            q.offer("0000");
            visted.add("0000");
            while (!q.isEmpty()) {
                int sz = q.size();
                for (int i = 0; i < sz; i++) {
                    String cur = q.poll();
                    //判断是否到大终点
                    if (dset.contains(cur)) {
                        continue;
                    }
                    if (target.equals(cur)) {
                        return step;
                    }

                    for (int j = 0; j < 4; j++) {
                        String up = plusOne(cur, j);
                        if (!visted.contains(up)) {
                            q.offer(up);
                            visted.add(up);
                        }
                        String down = minusOne(cur, j);
                        if (!visted.contains(down)) {
                            q.offer(down);
                            visted.add(down);
                        }
                    }

                }
                //在这里增加步数
                step++;
            }
            //如果群举完没找到就是找不到了
            return -1;
        }

        //将 s[i] 向上拨动一次
        String plusOne(String s, int i) {
            char[] ch = s.toCharArray();
            if (ch[i] == '9') {
                ch[i] = '0';
            }
            else {
                ch[i] += 1;
            }
            return new String(ch);
        }

        //将 s[i] 向下拨动一次
        String minusOne(String s, int i) {
            char[] ch = s.toCharArray();
            if (ch[i] == '0') {
                ch[i] = '9';
            }
            else {
                ch[i] -= 1;
            }
            return new String(ch);
        }

        // //BFS 框架，打印出所有可能的密码情况
        // void BFS(String target){
        //     Queue<String> q = new LinkedList<>();
        //     q.offer('0000');
        //     while(!q.isEmpty()) {
        //         int sz = q.size();
        //         for(int i = 0;i < sz; i++){
        //             String s = q.poll();
        //             //判断是否已经到达终点

        //             //将一个节点的相邻节点加入到队列中
        //             for(int i=0; i< n ; i++){
        //                 String up = plusOne(s, i);
        //                 String down = minusOne(s, i);
        //                 q.offer(up);
        //                 q.offer(down);
        //             }

        //         }
        //         // 这里增加步数
        //     }
        // }


    }


    //双向BHS
    class Solution2{
        public int openLock(String[] deadends, String target) {


            //记录已经群举过的，防止再举
            Set<String> deads = new HashSet<>();
            for (String s : deadends) {
                deads.add(s);
            }
            //用集合不用队列，可以快速判断元素是否存在
            Set<String> q1 = new HashSet<>();
            Set<String> q2 = new HashSet<>();
            Set<String> visited = new HashSet();
            int step = 0;
            q1.add("0000");
            q2.add(target);

            while (!q1.isEmpty() && !q2.isEmpty()) {
                // 哈希表在遍历的过程中不能修改，用tmp 存储扩散的结果
                Set<String> temp = new HashSet<>();
                //将q1 中的所有节点向外扩散
                for (String cur : q1) {
                    if (deads.contains(cur)) {
                        continue;
                    }
                    if (q2.contains(cur)) {
                        return step;
                    }
                    visited.add(cur);
                    for (int j = 0; j < 4; j++) {
                        String up = plusOne(cur, j);
                        String down = minusOne(cur, j);
                        if (!visited.contains(up))
                            temp.add(up);
                        if (!visited.contains(down))
                            temp.add(down);
                    }
                }
                //在这里增加步数
                step++;
                //这里交换一下 q1, q2 下一轮扩散的就是q2
                q1 = q2;
                q2 = temp;
            }
            //如果群举完没找到就是找不到了
            return -1;
        }

        //将 s[i] 向上拨动一次
        String plusOne(String s, int i) {
            char[] ch = s.toCharArray();
            if (ch[i] == '9') {
                ch[i] = '0';
            }
            else {
                ch[i] += 1;
            }
            return new String(ch);
        }

        //将 s[i] 向下拨动一次
        String minusOne(String s, int i) {
            char[] ch = s.toCharArray();
            if (ch[i] == '0') {
                ch[i] = '9';
            }
            else {
                ch[i] -= 1;
            }
            return new String(ch);
        }


    }
}
