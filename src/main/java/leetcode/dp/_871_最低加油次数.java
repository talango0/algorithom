package leetcode.dp;
//汽车从起点出发驶向目的地，该目的地位于出发位置东面 target 英里处。
//
// 沿途有加油站，每个 station[i] 代表一个加油站，它位于出发位置东面 station[i][0] 英里处，并且有 station[i][1] 升汽
//油。
//
// 假设汽车油箱的容量是无限的，其中最初有 startFuel 升燃料。它每行驶 1 英里就会用掉 1 升汽油。
//
// 当汽车到达加油站时，它可能停下来加油，将所有汽油从加油站转移到汽车中。
//
// 为了到达目的地，汽车所必要的最低加油次数是多少？如果无法到达目的地，则返回 -1 。
//
// 注意：如果汽车到达加油站时剩余燃料为 0，它仍然可以在那里加油。如果汽车到达目的地时剩余燃料为 0，仍然认为它已经到达目的地。
//
//
//
// 示例 1：
//
// 输入：target = 1, startFuel = 1, stations = []
//输出：0
//解释：我们可以在不加油的情况下到达目的地。
//
//
// 示例 2：
//
// 输入：target = 100, startFuel = 1, stations = [[10,100]]
//输出：-1
//解释：我们无法抵达目的地，甚至无法到达第一个加油站。
//
//
// 示例 3：
//
// 输入：target = 100, startFuel = 10, stations = [[10,60],[20,30],[30,30],[60,40]]
//
//输出：2
//解释：
//我们出发时有 10 升燃料。
//我们开车来到距起点 10 英里处的加油站，消耗 10 升燃料。将汽油从 0 升加到 60 升。
//然后，我们从 10 英里处的加油站开到 60 英里处的加油站（消耗 50 升燃料），
//并将汽油从 10 升加到 50 升。然后我们开车抵达目的地。
//我们沿途在1两个加油站停靠，所以返回 2 。
//
//
//
//
// 提示：
//
//
// 1 <= target, startFuel, stations[i][1] <= 10^9
// 0 <= stations.length <= 500
// 0 < stations[0][0] < stations[1][0] < ... < stations[stations.length-1][0] <
//target
//
//
// Related Topics 贪心 数组 动态规划 堆（优先队列） 👍 374 👎 0


import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-09-09.
 */
public class _871_最低加油次数{
    class Solution {
        public int minRefuelStops(int target, int startFuel, int[][] stations) {
            int n = stations.length;
            long [] dp = new long[n+1];
            // dp[i] 表示加油i次的最大行驶英里数，由于初始时汽油量时 startFuelFuel ，可以公里 startFuel 公里
            dp[0] = startFuel;
            for (int i = 0; i < n; i++) {
                for (int j = i; j >= 0; j--) {
                    if (dp[j] >= stations[i][0]) {
                        dp[j+1] = Math.max(dp[j+1], dp[j]+stations[i][1]);
                    }
                }
            }
            for (int i = 0; i <= n; i++) {
                if (dp[i] >= target) {
                    return i;
                }
            }
            return -1;
        }
    }


    class Solution2 {
        // 想象成不是只有在加油站才能加油，而是只要需要有，并且之前有加油站还没有加油，那么此时就可以加油。
        // 这样以来，如果要使得加油次数最少，那么加油就加最多的有，为了保证时间效率，用堆维护前面的未使用过的加油站里的油。
        // 需要加油而没有油是（也就是堆为空），那么就不能到达，此时返回-1
        public int minRefuelStops(int target, int startFuel, int[][] stations) {
            if (stations.length == 0) {
                return startFuel >= target ? 0:-1;
            }
            PriorityQueue<Integer> queue = new PriorityQueue<Integer>((o1, o2)->(o2-o1));
            int sum = startFuel;
            int ans = 0;
            for (int i = 0; i < stations.length; i++) {
                while (sum < stations[i][0]) {
                    Integer fuel = queue.poll();
                    if (fuel == null) {
                        return -1;
                    }
                    sum += fuel;
                    ans ++;
                }
                queue.offer(stations[i][1]);
            }
            while (sum < target) {
                Integer fuel = queue.poll();
                if (fuel == null) {
                    return -1;
                }
                sum += fuel;
                ans ++;
            }
            return ans;
        }
    }
}
