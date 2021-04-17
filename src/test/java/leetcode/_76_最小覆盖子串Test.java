package leetcode;


import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _76_最小覆盖子串Test {


    @Test
    public void testSolution(){
        _76_最小覆盖子串.Solution solution = new _76_最小覆盖子串.Solution();
        String s = solution.minWindow("ADOBECODEBANC", "ABC");
        Assert.assertEquals("BANC",s );
    }

}