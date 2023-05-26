package leetcode.程序员面试金典;
//你有1000瓶苏打水，其中有一瓶有毒。你有10条可用于检测毒物的试纸。一滴毒药会使试纸永久变黄。你可以一次性地将任意数量的液滴置于试纸上，
// 你也可以多次重复使用试纸(只要结果是阴性的即可)。 但是，每天只能进行一次测试，用时 7 天才可得到测试结果。你如何用尽量少的时间找出哪瓶苏打水有毒?


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author mayanwei
 * @date 2023-05-19.
 */
public class _06_10_有毒苏打水{
    // 药瓶
    static class Bottle{
        private boolean poisoned = false;
        private int id;

        public boolean isPoisoned() {
            return poisoned;
        }

        public void setPoisoned(boolean poisoned) {
            this.poisoned = poisoned;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Bottle(int id) {
            this.id = id;
        }
    }

    // 试纸
    static class TestTripe{
        public static int DAYS_FOR_RESULT = 7;
        // 第 i 天该该试纸滴了哪些药瓶
        private List<List<Bottle>> dropsByDay = new ArrayList<>();

        private int id;

        public TestTripe(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        /*改变链表的尺寸使其足够大*/
        private void sizeDropsForDay(int day)  {
            while (dropsByDay.size() <= day) {
                dropsByDay.add(new ArrayList<>());
            }
        }

        /*在特定的一天加入某瓶苏打水的液体*/
        public void addDropOnDay(int day, Bottle bottle) {
            sizeDropsForDay(day);
            dropsByDay.get(day).add(bottle);
        }

        /*检查该组苏打水中是否有毒*/
        public boolean hasPoison(List<Bottle> bottles) {
            for (Bottle b : bottles) {
                if (b.isPoisoned()) {
                    return true;
                }
            }
            return false;
        }

        /*获取 DAYS_FOR_RESULT 天之前使用的苏打水*/
        public List<Bottle> getLastWeeksBottles(int day) {
            if (day < DAYS_FOR_RESULT) {
                return null;
            }
            return dropsByDay.get(day - DAYS_FOR_RESULT);
        }

        /*检查 DAYS_FOR_RESULT 之前有毒的苏打水*/
        public boolean isPositiveOnDay(int day) {
            int testDay = day - DAYS_FOR_RESULT;
            if (testDay < 0 || testDay >= dropsByDay.size()) {
                return false;
            }
            for (int d = 0; d <= testDay; d++) {
                List<Bottle> bottles = dropsByDay.get(d);
                if(hasPoison(bottles)) {
                    return true;
                }
            }
            return false;
        }


    }

    /**
     * 1. 简单方案：28天
     * 该方案的前提假设是，每一轮测试中都有多条试纸可以使用。对于1000瓶苏打水瓶和10条试纸的情况，该假设是合理的。
     * 如果不能做出上述假设，可以在代码中做一个"失效保险"。如果试纸只剩下一条，则一瓶一瓶取出进行测试i，即测试一瓶
     * 苏打水，等一周，在测试下一瓶苏打水。改方案最多花费 28天时间。
     */
    static class Solution1{


        // 上述代码模拟了苏打水瓶和试纸的一种方式，下面测一下
        int findPoisonBottle(List<Bottle> bottles, List<TestTripe> strips) {
            int today = 0;
            while (bottles.size() > 1 && strips.size() > 0) {
                // 运行测试
                runTestSet(bottles, strips, today);
                // 等待结果
                today += TestTripe.DAYS_FOR_RESULT;
                // 检查结果
                for (TestTripe strip : strips) {
                    if (strip.isPositiveOnDay(today)) {
                        bottles = strip.getLastWeeksBottles(today);
                        strips.remove(strip);
                        break;
                    }
                }
            }
            if (bottles.size() == 1 ) {
                return bottles.get(0).getId();
            }
            return -1;
        }

        /*将瓶子平均地分配在试纸上*/
        private void runTestSet(List<Bottle> bottles, List<TestTripe> strips, int today) {
            int index = 0;
            for (Bottle bottle : bottles) {
                TestTripe strip = strips.get(index);
                strip.addDropOnDay(today, bottle);
                index = (index + 1) % strips.size();
            }
        }
    }


    /**
     * 方案2
     */
    class Solution2 {
        public int findPoisonBottle(List<Bottle> bottles, List<TestTripe> strips) {
            if (bottles.size() > 1000 || strips.size() < 10) {
                return -1;
            }
            // 三位数字，加额外的一位
            int tests = 4;
            int nTestStrips = strips.size();

            // 检测
            for (int day = 0; day < tests; day++) {
                runTestSet(bottles, strips, day);
            }

            // 获取结果
            Set<Integer> previousResults = new HashSet<>();
            int[] digits = new int[tests];
            for (int day = 0; day < tests; day++) {
                int resultDay = day + TestTripe.DAYS_FOR_RESULT;
                digits[day] = getPositiveOnDay(strips, resultDay, previousResults);
                previousResults.add(digits[day]);
            }
            // 如果第1天的结果与第0天匹配，则更新数字
            if (digits[1] == -1) {
                digits[1] = digits[0];
            }

            //如果第2天与第0天或第1天匹配，则检查第3天。如果第3天和第2天相同，只需增加1.
            if (digits[2] == -1) {
                // 第3天没有更新结果
                if (digits[3] == -1) {
                    // digits[2] 与 digits[0] 或者 digits[1] 相同，但是 digits[2] 增加1后仍与 digits[0] 或者 digits[1] 匹配。
                    // 这意味着，digits[0] 增加1后与 digits[1] 匹配，或者相反的情况成立。
                     digits[2] = ((digits[0] + 1) % nTestStrips) == digits[1] ? digits[0] :digits[1];
                }
                else {
                    digits[2] = (digits[3] - 1 + nTestStrips) % nTestStrips;
                }
            }
            return  digits[0] * 100 + digits[1] * 10 + digits[2];

        }

        // 获取特定某一天的阳性结果，排出以前的检测结果
        private int getPositiveOnDay(List<TestTripe> strips, int resultDay, Set<Integer> previousResults) {
            return 0;
        }

        // 进行该天的所有检测
        private void runTestSet(List<Bottle> bottles, List<TestTripe> strips, int day) {
            if (day > 3) return;
            for (Bottle bottle : bottles) {
                int index = getTestStripIndexForDay(bottle, day, strips.size());
                TestTripe testTripe = strips.get(index);
                testTripe.addDropOnDay(day, bottle);
            }
        }

        private int getTestStripIndexForDay(Bottle bottle, int day, int nStrips) {
            int id = bottle.getId();
            switch (day) {
                case 0 : return id / 100;
                case 1 : return (id % 100)/10;
                case 2 : return id % 10;
                case 3 : return (id % 10 + 1) % nStrips;
                default: return -1;
            }
        }

    }

    /**
     * 方案3，最有方案7天
     * 只要 2^T >= B 该方案可行，其中T试纸数量，B位苏打水瓶数。
     */
    class Solution3{
        public int findPoisonBottle(List<Bottle> bottles, List<TestTripe> strips) {
            runTest(bottles, strips);
            List<Integer> positive = getPositiveOnDay(strips, 7);
            return setBits(positive);
        }

        // 构造一个数字，呈现阳性结果
        private int setBits(List<Integer> positive) {
            int id = 0;
            for (Integer bitIndex : positive) {
                id |= 1 << bitIndex;
            }
            return id;
        }

        // 获取该天该瓶苏打水应使用的试纸
        private List<Integer> getPositiveOnDay(List<TestTripe> strips, int day) {
            List<Integer> positive = new ArrayList<>();
            for (TestTripe strip : strips) {
                int id = strip.getId();
                if (strip.isPositiveOnDay(day)) {
                    positive.add(id);
                }
            }
            return positive;
        }

        // 将瓶中液体滴到试纸上
        private void runTest(List<Bottle> bottles, List<TestTripe> strips) {
            for (Bottle bottle : bottles) {
                int id = bottle.getId();
                int bitIndex = 0;
                while (id > 0) {
                    if ((id & 1) == 1) {
                        strips.get(bitIndex).addDropOnDay(0, bottle);
                    }
                    bitIndex++;
                    id >>= 1;
                }
            }

        }


    }

}
