package problems.stack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 341. 扁平化嵌套列表迭代器
 * 难度：中等
 * <p>
 * 题目描述
 * <p>
 * 给你一个嵌套的整型列表。请你设计一个迭代器，使其能够遍历这个整型列表中的所有整数。
 * <p>
 * 列表中的每一项或者为一个整数，或者是另一个列表。
 * <p>
 * 示例 1:
 * 输入: [[1,1],2,[1,1]]
 * 输出: [1,1,2,1,1]
 * 解释: 通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,1,2,1,1]。
 * <p>
 * 示例 2:
 * 输入: [1,[4,[6]]]
 * 输出: [1,4,6]
 * 解释: 通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,4,6]。
 *
 * @author kyan
 * @date 2020/2/2
 */
public class LeetCode_341_FlattenNestedListIterator {

    /**
     * // This is the interface that allows for creating nested lists.
     * // You should not implement it, or speculate about its implementation
     */
    public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    /**
     * 3 ms, 99.67%
     * 37.7 MB, 42.19%
     */
    public static class NestedIterator implements Iterator<Integer> {

        private List<Integer> list;
        private int index;

        public NestedIterator(List<NestedInteger> nestedList) {
            list = iterateRecursively(nestedList);
            index = 0;
        }

        @Override
        public Integer next() {
            if (hasNext()) return list.get(index++);
            return null;
        }

        @Override
        public boolean hasNext() {
            return index  < list.size();
        }

        private List<Integer> iterateRecursively(List<NestedInteger> nestedList) {
            List<Integer> list = new ArrayList<>();
            for (NestedInteger nestedInteger : nestedList) {
                if (nestedInteger.isInteger()) {
                    list.add(nestedInteger.getInteger());
                } else {
                    list.addAll(iterateRecursively(nestedInteger.getList()));
                }
            }
            return list;
        }
    }

    /**
     * Your NestedIterator object will be instantiated and called as such:
     * NestedIterator i = new NestedIterator(nestedList);
     * while (i.hasNext()) v[f()] = i.next();
     */
}
