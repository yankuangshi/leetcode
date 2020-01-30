package problems.stack;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 225. 用队列实现栈
 * 难度：简单
 * 使用队列实现栈的下列操作：
 *
 * push(x) -- 元素 x 入栈
 * pop() -- 移除栈顶元素
 * top() -- 获取栈顶元素
 * empty() -- 返回栈是否为空
 * 注意:
 *
 * 你只能使用队列的基本操作-- 也就是 push to back, peek/pop from front, size, 和 is empty 这些操作是合法的。
 * 你所使用的语言也许不支持队列。 你可以使用 list 或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
 * 你可以假设所有操作都是有效的（例如, 对一个空的栈不会调用 pop 或者 top 操作）。
 *
 * @author kyan
 * @date 2020/1/30
 */
public class _225_ImplementStackUsingQueue {


    /**
     * 思路：利用队列Queue的特性FIFO
     * 入栈对应的入队很好理解
     * 出栈要求是队尾的元素出栈，就需要把队尾之前的所有元素都出队再入队排到原先的队尾元素后面，然后原先的队尾元素再出队
     *
     * time 0ms, beat 100.00%
     * space 34.2MB, beat 5.26%
     */
    public static class MyStack {

        private Queue<Integer> queue;


        /** Initialize your data structure here. */
        public MyStack() {
            queue = new LinkedList<>();
        }

        /** Push element x onto stack. */
        public void push(int x) {
            queue.offer(x);
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            if (empty()) {
                throw new RuntimeException("no element in stack");
            }
            shift();
            return queue.poll();
        }

        /** Get the top element. */
        public int top() {
            if (empty()) {
                throw new RuntimeException("no element in stack");
            }
            shift();
            Integer top = queue.poll();
            queue.add(top);
            return top;
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return queue.isEmpty();
        }

        /**
         * 把之前入队的size-1个元素一一出队然后重新入队，
         * 即把最后入队的元素挪动到队首
         */
        private void shift() {
            int shiftTimes = queue.size() - 1;
            for (int i = 0; i < shiftTimes; i++) {
                queue.add(queue.poll());
            }
        }
    }

    public static void main(String[] args) {
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        System.out.println(stack.top());
        System.out.println(stack.pop());
        System.out.println(stack.top());
        System.out.println(stack.empty());
    }
}
