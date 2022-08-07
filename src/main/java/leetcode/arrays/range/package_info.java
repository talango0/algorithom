package leetcode.arrays.range;

/**
 * 所谓区间问题，就是线段问题，让你合并所有线段、找出线段的交集等等。主要有两个技巧：
 * <p>
 * 1、排序。常见的排序方法就是按照区间起点排序，或者先按照起点升序排序，若起点相同，则按照终点降序排序。当然，如果你非要按照终点排序，无非对称操作，本质都是一样的。
 * <p>
 * 2、画图。就是说不要偷懒，勤动手，两个区间的相对位置到底有几种可能，不同的相对位置我们的代码应该怎么去处理。
 *
 * @see leetcode.arrays.range._1288_删除被覆盖区间
 * @see leetcode.arrays.range._986_区间列表的交集
 * @see leetcode.arrays.range._56_合并区间
 * @author mayanwei
 * @date 2022-08-06.
 */


