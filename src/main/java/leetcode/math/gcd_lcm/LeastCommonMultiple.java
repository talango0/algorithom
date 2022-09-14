package leetcode.math.gcd_lcm;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * @author mayanwei
 * @date 2022-09-14.
 */
public class LeastCommonMultiple{
    private final GreatCommonDivisor gcdFun = new GreatCommonDivisor();

    /**
     * 最小公倍数
     * @param a
     * @param b
     * @return
     */
    public int lcm(int a, int b) {
        return a*b / gcdFun.gcd(a,b);
    }

    @Test
    public void test(){
        Assert.assertEquals(lcm(21, 6), 42);
        Assert.assertEquals(lcm(lcm(21, 6),42), 42);
    }

}
