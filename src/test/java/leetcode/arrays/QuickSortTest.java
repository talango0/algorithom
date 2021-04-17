package leetcode.arrays;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    int [] arr    = {13, 19, 9, 5, 12, 8, 7, 4, 21, 2, 5, 11};
    int [] expect = {2, 4, 5, 5, 7, 8, 9, 11, 12, 13, 19, 21};

    @Test
    void testSolution1(){
        QuickSort.Solution solution = new QuickSort.Solution();
        solution.quickSort(arr, 0, arr.length-1);
        Assert.assertArrayEquals(expect,arr);
    }

}