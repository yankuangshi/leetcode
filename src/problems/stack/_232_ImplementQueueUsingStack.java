package problems.stack;

import java.util.Stack;

/**
 * 232. 用栈实现队列
 * 难度：简单
 *
 * 使用栈实现队列的下列操作：
 *
 * push(x) -- 将一个元素放入队列的尾部。
 * pop() -- 从队列首部移除元素。
 * peek() -- 返回队列首部的元素。
 * empty() -- 返回队列是否为空。
 * 示例:
 *
 * MyQueue queue = new MyQueue();
 *
 * queue.push(1);
 * queue.push(2);
 * queue.peek();  // 返回 1
 * queue.pop();   // 返回 1
 * queue.empty(); // 返回 false
 * 说明:
 *
 * 你只能使用标准的栈操作 -- 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
 * 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
 * 假设所有操作都是有效的 （例如，一个空的队列不会调用 pop 或者 peek 操作）。
 *
 * @author kyan
 * @date 2020/1/30
 */
public class _232_ImplementQueueUsingStack {

    /**
     * 思路：
     * 利用2个stack，一个专用于push元素的pushStack，一个专用于pop元素的popStack
     * 队列入队一个元素时，直接push到pushStack；
     * 队列出队一个元素时，如果popStack不为空，则pop出栈顶元素出队；如果popStack为空，先把pushStack中元素转移至popStack再pop出栈顶元素；
     *
     * time 0ms, beat 100%
     * space 34.1MB, beat 21.4%
     */
    public static class MyQueue {

        private Stack<Integer> pushStack;
        private Stack<Integer> popStack;

        /** Initialize your data structure here. */
        public MyQueue() {
            pushStack = new Stack<>();
            popStack = new Stack<>();
        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            pushStack.push(x);
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            //如果pushStack和popStack都为空，则抛出异常
            if (popStack.isEmpty() && pushStack.isEmpty()) {
                throw new RuntimeException("no element in queue");
            }
            //如果popStack不为空，则直接pop栈顶元素
            if (!popStack.isEmpty()) {
                return popStack.pop();
            }
            //如果popStack为空，则说明pushStack肯定不为空，则先把pushStack中的所有元素转移至popStack后再pop
            shift();
            return popStack.pop();
        }

        /** Get the front element. */
        public int peek() {
            //如果pushStack和popStack都为空，则抛出异常
            if (popStack.isEmpty() && pushStack.isEmpty()) {
                throw new RuntimeException("no element in queue");
            }
            //如果popStack不为空，则直接peek栈顶元素
            if (!popStack.isEmpty()) {
                return popStack.peek();
            }
            //如果popStack为空，则说明pushStack肯定不为空，则先把pushStack中的所有元素转移至popStack后再peek
            shift();
            return popStack.peek();
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return pushStack.isEmpty() && popStack.isEmpty();
        }

        private void shift() {
            while (!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
    }

    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        System.out.println(queue.peek());
        System.out.println(queue.pop());
        System.out.println(queue.peek());
        System.out.println(queue.pop());
        System.out.println(queue.empty());
    }
}
