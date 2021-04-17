package leetcode.arrays;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class _45_跳跃游戏IITest {
    @Test
    void testSolution(){
        _45_跳跃游戏II.Solution solution = new _45_跳跃游戏II.Solution();
        Assert.assertEquals(2, solution.jump(new int[]{2,3,1,1,4}));
    }

}