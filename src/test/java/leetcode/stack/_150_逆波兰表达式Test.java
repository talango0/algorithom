package leetcode.stack;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _150_逆波兰表达式Test {

    @Test
    public void testSolution(){
        _150_逆波兰表达式.Solution solution = new _150_逆波兰表达式.Solution();
        Assert.assertEquals(6,solution.evalRPN(new String[]{"2","3","*"}));
        Assert.assertEquals(6,solution.evalRPN(new String[]{"1","1","+","3","*"}));
    }

}