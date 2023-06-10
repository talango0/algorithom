package leetcode.程序员面试金典;
//给定 N 个人的出生年份和死亡年份，第 i 个人的出生年份为 birth[i]，死亡年份为 death[i]，实现一个方法以计算生存人数最多的年份。
//
//你可以假设所有人都出生于 1900 年至 2000 年（含 1900 和 2000 ）之间。如果一个人在某一年的任意时期处于生存状态，那么他应该被纳入那一年的统计中。
// 例如，生于 1908 年、死于 1909 年的人应当被列入 1908 年和 1909 年的计数。
//
//如果有多个年份生存人数相同且均为最大值，输出其中最小的年份。
//
//
//
//示例：
//
//输入：
//birth = [1900, 1901, 1950]
//death = [1948, 1951, 2000]
//输出： 1901
//
//
//提示：
//
//0 < birth.length == death.length <= 10000
//birth[i] <= death[i]
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/living-people-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-06-10.
 */
public class _16_10_生存人数{

    static class Person{
        int birth;
        int death;

        public Person(int birth, int death) {
            this.birth = birth;
            this.death = death;
        }
    }

    /**
     * 蛮力法
     * 时间复杂度 O(RP
     */
    class Solution1{
        // 找到生存人数最多的一年，对每一年进行遍历，检查当年有多少人生存
        public int maxAliveYear(Person[] persons, int min, int max) {
            int maxAlive = 0;
            int maxAliveYear = min;
            for (int year = 0; year <= max; year++) {
                int alive = 0;
                for (Person person : persons) {
                    if (person.birth <= year && person.death >= year) {
                        alive++;
                    }
                }
                if (alive > maxAlive) {
                    maxAlive = alive;
                    maxAliveYear = year;
                }
            }
            return maxAliveYear;
        }
    }

    /**
     * 稍加改进
     * 可以创建一个数组记录每一年出生的人数，之后，只需要对人员列表进行迭代，对于每一个人增加上述数组中对应的年份。
     * 运行时间总计为 O(PY + R)。最􏳷的情况下，Y 的􏰂即为 R，因此我们并没有取得 比前述算法更优的算法.
     */
    class Solution{
        class Person{
            int birth;
            int death;

            public Person(int birth, int death) {
                this.birth = birth;
                this.death = death;
            }
        }

        public int maxAliveYear(int[] birth, int[] death) {
            if (birth == null || death == null || birth.length != death.length) {
                return -1;
            }
            int n = birth.length;
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            Person[] persons = new Person[n];
            for (int i = 0; i < n; i++) {
                min = Math.min(min, birth[i]);
                max = Math.max(max, death[i]);
                persons[i] = new Person(birth[i], death[i]);
            }
            return maxAliveYear(persons, min, max);
        }

        int maxAliveYear(Person[] persons, int min, int max) {
            int[] years = createYearMap(persons, min, max);
            int most = getMaxIndex(years);
            return most + min;
        }

        private int getMaxIndex(int[] years) {
            int max = 0;
            for (int i = 0; i < years.length; i++) {
                if (years[i] > years[max]) {
                    max = i;
                }
            }
            return max;
        }

        /**
         * 将每个人的年份加入到映射中。
         *
         * @param persons
         * @param min
         * @param max
         * @return
         */
        private int[] createYearMap(Person[] persons, int min, int max) {
            int[] years = new int[max - min + 1];
            for (Person person : persons) {
                incrementRange(years, person.birth - min, person.death - min);
            }
            return years;
        }

        private void incrementRange(int[] years, int start, int end) {
            for (int i = start; i <= end; i++) {
                years[i]++;
            }
        }
    }

    /**
     * <pre>
     * 更优解。
     * ┌─────────────────────────────────────┐
     * │ birth: 12 20 10 01 10 23 13 90 83 75│
     * │ death: 15 90 98 72 98 82 98 98 99 94│
     * ├─────────────────────────────────────┤
     * │ birth: 01 10 10 12 13 20 23 75 83 90│
     * │ death: 15 72 82 90 94 98 98 98 98 99│
     * └─────────────────────────────────────┘
     * 不需要匹配出生和死亡。每一次出生都会增加一个人，每一次死亡都会减少一个人。
     * 第0年时，没有人存活
     * 第1年时，有一次出生
     * 第2年至9年，没有任何事情发生
     * 第10年，此时发现两次出生，共3人存活
     * 第15年时，有1人死亡。至此，剩下2人存活。
     * 以此内，遍历这样的两个数组，就可以记录每个时间点存活的人数。
     * </pre>
     */
    class Solution3{
        public int maxAliveYear(int[] birth, int[] death) {
            Arrays.sort(birth);
            Arrays.sort(death);
            int birthIndex = 0;
            int deathIndex = 0;
            int currentlyAlive = 0;
            int maxAlive = 0;
            int maxAliveYear = Integer.MAX_VALUE;

            while (birthIndex < birth.length) {
                //当我们发现同一年中有出生和􏳭􏳮的情况时，希望在记录􏳭􏳮􏳽􏳾记录出生，这样我们可 以将当年计算为存活年份。这也就是为什么会使用<=
                if (birth[birthIndex] <= death[deathIndex]) {
                    currentlyAlive++;
                    if (currentlyAlive > maxAlive) {
                        maxAlive = currentlyAlive;
                        maxAliveYear = birth[birthIndex];
                    }
                    birthIndex++; // 移动出生索引
                }
                else {
                    currentlyAlive--; // 包括死亡
                    deathIndex++;
                }
            }
            return maxAliveYear;
        }
    }

    /**
     * <pre>
     * ┌─────────────────────────────────────┐
     * │ birth: 12 20 10 01 10 23 13 90 83 75│
     * │ death: 15 90 98 72 98 82 98 98 99 94│
     * │                                     │
     * │ 01: +1  10: +1 10: +1 12: +1 13: +1 │
     * │ 15: -1  20: +1 23: +1 72: -1 75: +1 │
     * │ 82: -1  83: +1 90: +1 90: -1 94: -1 │
     * │ 98: -1  98: -1 98: -1 98: -1 99: -1 │
     * └─────────────────────────────────────┘
     *     创建了一个年份数组 array[years] 表示当年人口变化。为了创建该数组，需要遍历人员列表，当有人出生时加1，有人死亡时减1。
     * 一旦得到该数组，就可以对年份进行遍历并随着遍历的进行记录当前人口（每次都增加array[year]的值）。
     *     但上面的做法存在一个问题：1908年死亡的人直到 1909年才应该从总人口中移除。一种简单的修正时，不对 array[deathYear]
     * 进行减1，而是对 array[deathYear+1]进行减1操作。
     * </pre>
     * 更优的解法。该解法将花费 O(R+P)的时间，其中 R 是年份的范围，P 是人员的数量。尽管对于很多预期 中的输入数据来说，
     * O(R+P)或许会快于O(P log P)的时间复杂度，但是你无法直接对两个时间 复杂度进行比较并由此得出其中一个要略胜一筹。。
     * 差分数组
     *
     */
    class Solution4 {
        public int maxAliveYear(int[] birth, int[] death) {
            int n = birth.length;
            // 差分数组
            int[] arr = new int[2003];
            for(int i=0; i<n; i++){
                arr[birth[i]]++;
                arr[death[i]+1]--;
            }
            int max = 0, idx = 0, sum = 0;
            for(int i=1900; i<2002; i++){
                sum += arr[i];
                if(sum > max){
                    max = sum;
                    idx = i;
                }
            }
            return idx;
        }
    }

    @Test
    public void test() {
        int[] births = new int[]{1972, 1908, 1915, 1957, 1960, 1948, 1912, 1903, 1949, 1977, 1900, 1957, 1934, 1929, 1913, 1902, 1903, 1901};
        int[] deaths = new int[]{1997, 1932, 1963, 1997, 1983, 2000, 1926, 1962, 1955, 1997, 1998, 1989, 1992, 1975, 1940, 1903, 1983, 1969};
        Solution3 solution3 = new Solution3();
        Assert.assertEquals(1960, solution3.maxAliveYear(births, deaths));
    }

}
