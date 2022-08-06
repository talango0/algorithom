package leetcode.math;

import edu.princeton.cs.algs4.In;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-08-06.
 */
public class ModOp{

    /**
     * 因为 a*b 可能会溢出，所以采用下式转化
     * <p>
     * (a*b)%c = (a%c)(b%c)%c
     * <p>
     * 证明如下：
     * <p>
     * a = Ak +B；b = Ck + D
     * <p>
     * 其中 A,B,C,D 是任意常数，那么：
     * <p>
     * ab = ACk^2 + ADk + BCk +BD
     * <p>
     * ab % k = BD % k
     * <p>
     * 又因为：
     * <p>
     * a % k = B；b % k = D
     * <p>
     * 所以：
     * <p>
     * (a % k)(b % k) % k = BD % k
     * <p>
     * 换句话说，对乘法的结果求模，等价于先对每个因子都求模，然后对因子相乘的结果再求模。
     */
    @Test
    public void test1() {
        Assert.assertEquals((2 * 3) % 5, (2 % 5) * (3 % 5) % 5);
    }
}
