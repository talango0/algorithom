package leetcode.design;
//请设计并实现一个能够展开二维向量的迭代器。该迭代器需要支持 next 和 hasNext 两种操作。、
//
//示例：
//Vector2D iterator = new Vector2D([[1,2],[3],[4]]);
//
//iterator.next(); // 返回 1
//iterator.next(); // 返回 2
//iterator.next(); // 返回 3
//iterator.hasNext(); // 返回 true
//iterator.hasNext(); // 返回 true
//iterator.next(); // 返回 4
//iterator.hasNext(); // 返回 false

import java.util.NoSuchElementException;

/**
 * @author mayanwei
 * @date 2022-10-03.
 */
public class _251_展开二维向量{
    /**
     * 思路1，可以直接转换成一维链表然后调用链表的迭代
     * 思路2，定义两个索引变量，outer 和 inner
     *
     */
    class Vector2D{
        private int [][] vector;
        private int inner = 0; //内索引
        private int outer = 0; //外索引
        public Vector2D(int [][] v) {
            this.vector = v;
        }

        public int next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return vector[outer][inner ++];
        }

        public boolean hasNext(){
            while (outer < vector.length && inner == vector[outer].length) {
                inner = 0;
                outer ++;
            }
            return outer < vector.length;
        }
    }
}
