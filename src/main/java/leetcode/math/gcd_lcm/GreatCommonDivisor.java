package leetcode.math.gcd_lcm;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * 最大公约数
 * @author mayanwei
 * @date 2022-09-14.
 */
public class GreatCommonDivisor{


    /**
     * a 和 b 的最大公约数 用辗转相除法求
     *
     * 满足交换律 gcd(a,b) == gcd(b,a)
     * 满足结合律 gcd(a,gcd(b,c)) == gcd(gcd(a,b),c)
     * @param a
     * @param b
     * @return
     */
    public int gcd(int a, int b){
        return b == 0 ? a : gcd(b, a % b);
    }


    @Test
    public void test(){
        Assert.assertEquals(gcd(18, 48), 6);
        Assert.assertEquals(gcd(18, 48), gcd(48, 18));
        Assert.assertEquals(gcd(18, gcd(6,48)), gcd(gcd(18,6),48));

    }
}
