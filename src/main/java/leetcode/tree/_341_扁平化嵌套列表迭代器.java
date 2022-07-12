package leetcode.tree;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

// This is the interface that allows for creating nested lists.
// You should not implement it, or speculate about its implementation
interface NestedInteger {

      // @return true if this NestedInteger holds a single integer, rather than a nested list.
      public boolean isInteger();

      // @return the single integer that this NestedInteger holds, if it holds a single integer
      // Return null if this NestedInteger holds a nested list
      public Integer getInteger();

      // @return the nested list that this NestedInteger holds, if it holds a nested list
      // Return empty list if this NestedInteger holds a single integer
      public List<NestedInteger> getList();
}

/**
 * 实现NestedInteger
 */
class NestedIntegerImpl implements NestedInteger {
    private Integer val;
    private List<NestedInteger> list;
    @Override
    public boolean isInteger() {
        return val != null;
    }

    @Override
    public Integer getInteger() {
        return this.val;
    }

    @Override
    public List<NestedInteger> getList() {
        return this.list;
    }
}

/**
 * 对比 N 叉树
 */
class NTree {
    int val;
    NTree [] children;
}

public class _341_扁平化嵌套列表迭代器 {
    //我们只对整数型的 NestedInteger感兴趣，也就是我们只要叶子节点，所以在traverse函数中只要到达叶子节点的时候val加进去即可
    public class NestedIterator implements Iterator<Integer> {
        private Iterator<Integer> it;
        public NestedIterator(List<NestedInteger> nestedList) {
            //存放nestedList 打平的结果
            List<Integer> result = new LinkedList<>();
            for (NestedInteger node:nestedList) {
                //以每个节点为根进行遍历
                traverse(node, result);
            }
            // 得到result 列表的迭代器
            this.it = result.iterator();
        }
        private void traverse(NestedInteger root, List<Integer> result) {
            if (root.isInteger()) {
                //到达叶子节点
                result.add(root.getInteger());
                return;
            }
            //遍历N叉树框架
            for (NestedInteger child : root.getList()) {
                traverse(child, result);
            }
        }

        @Override
        public Integer next() {
            return it.next();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }
    }


    public class NestedIterator2 implements Iterator<Integer> {
        private LinkedList<NestedInteger> list;
        public NestedIterator2(List<NestedInteger> nestedList) {
            //迭代器应该是惰性的，防止数据量过大导致构造函数过慢，内存过高
            //不直接使用nestedList 的引用，是因为不能确定它的底层实现
            // 必须保证是 LinkedList, 否则下面的 addFirst 会很低效
            list = new LinkedList<>(nestedList);
        }


        @Override
        public Integer next() {
            //hasNext() 方法保证了第一个元素是整数类型
            return list.remove(0).getInteger();
        }

        @Override
        public boolean hasNext() {
            //循环差分列表，知道列表第一个元素是整形类型
            while (!list.isEmpty() && !list.get(0).isInteger()) {
                //当列表开头第一个元素是列表类型时，进入循环
                List<NestedInteger> first = list.remove(0).getList();
                for (int i = first.size()-1; i>=0; i--) {
                    list.addFirst(first.get(i));
                }
            }
            return !list.isEmpty();
        }
    }
}
