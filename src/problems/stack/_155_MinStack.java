package problems.stack;

import problems.common.util.ListNode;

/**
 * 155. 最小栈
 * 难度：简单
 * <p>
 * 设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
 * <p>
 * push(x) -- 将元素 x 推入栈中。
 * pop() -- 删除栈顶的元素。
 * top() -- 获取栈顶元素。
 * getMin() -- 检索栈中的最小元素。
 * 示例:
 * <p>
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.getMin();   --> 返回 -2.
 *
 * @author kyan
 * @date 2020/1/29
 */
public class _155_MinStack {


    static class MinStack {

        private ListNode top;
        private int minVal;

        /**
         * initialize your data structure here.
         */
        public MinStack() {
        }

        public void push(int x) {
            ListNode n = new ListNode(x);
            if (isEmpty() || x < minVal) {
                minVal = x;
            }
            n.next = top;
            top = n;
        }

        public void pop() {
            if (!isEmpty()) {
                if (top.val == minVal) {
                    minVal = throughMin(top.next);
                }
                top = top.next;
            }
        }

        public int top() {
            if (!isEmpty()) {
                return top.val;
            }
            return -1;
        }

        public int getMin() {
            if (!isEmpty()) {
                return minVal;
            }
            return -1;
        }

        public int throughMin(ListNode top) {
            if (top != null) {
                int tempMin = top.val;
                while (top != null) {
                    tempMin = top.val < tempMin ? top.val : tempMin;
                    top = top.next;
                }
                return tempMin;
            }
            return -1;
        }

        public boolean isEmpty() {
            return top == null;
        }
    }

    /**
     * Your MinStack object will be instantiated and called as such:
     * MinStack obj = new MinStack();
     * obj.push(x);
     * obj.pop();
     * int param_3 = obj.top();
     * int param_4 = obj.getMin();
     */

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(2);
        minStack.push(0);
        minStack.push(3);
        minStack.push(0);
        System.out.println(minStack.getMin()); //0
        minStack.pop();
        System.out.println(minStack.getMin()); //0
        minStack.pop();
        System.out.println(minStack.getMin()); //0
        minStack.pop();
        System.out.println(minStack.getMin()); //2
    }
}
