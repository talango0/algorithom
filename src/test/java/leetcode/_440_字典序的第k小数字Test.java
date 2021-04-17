package leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _440_字典序的第k小数字Test {

    @Test
    void testSolution(){
        _440_字典序的第k小数字.Solution solution = new _440_字典序的第k小数字.Solution();
        int res = solution.findKthNumber(13, 2);
        Assert.assertEquals(10, res);
    }

}