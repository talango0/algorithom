package leetcode.arrays;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _739_每日温度Test {

@Test
    void testSolution1(){
    _739_每日温度.Solution1 solution1 = new _739_每日温度.Solution1();
    int[] ans = solution1.dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73});
    Assert.assertArrayEquals(new int[]{1, 1, 4, 2, 1, 1, 0, 0}, ans);
}

    @Test
    void testSolution2(){
        _739_每日温度.Solution2 solution2 = new _739_每日温度.Solution2();
        int[] ans = solution2.dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73});
        Assert.assertArrayEquals(new int[]{1, 1, 4, 2, 1, 1, 0, 0}, ans);
    }


}