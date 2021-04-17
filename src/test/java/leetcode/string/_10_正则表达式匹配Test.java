package leetcode.string;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _10_正则表达式匹配Test {


    @Test
    void testSolution(){
        _10_正则表达式匹配.Solution solution = new _10_正则表达式匹配.Solution();
        Assert.assertEquals(true, solution.isMatch("aab","c*a*b"));
    }

}