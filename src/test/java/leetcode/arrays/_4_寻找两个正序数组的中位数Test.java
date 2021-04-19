package leetcode.arrays;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _4_寻找两个正序数组的中位数Test {



    @Test
    public void testSolution(){
        _4_寻找两个正序数组的中位数.Solution1 solution1 = new _4_寻找两个正序数组的中位数.Solution1();
        double media = solution1.findMedianSortedArrays(new int[]{1, 3, 4, 9}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        Assert.assertEquals(4.0,media,2);

    }

    @Test
    public void testSolution2(){
        _4_寻找两个正序数组的中位数.Solution2 solution2 = new  _4_寻找两个正序数组的中位数.Solution2();
        double media = solution2.findMedianSortedArrays( new int[]{ 1, 3, 4, 9 }, new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9 } );
        Assert.assertEquals(4.0,media,2);

    }
}