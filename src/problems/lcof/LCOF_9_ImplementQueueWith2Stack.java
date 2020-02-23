package problems.lcof;

import java.util.Stack;

/**
 * 面试题09. 用两个栈实现队列
 * 难度：简单
 * 题目描述
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，
 * 分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
 * 示例 1：
 * 输入：
 * ["CQueue","appendTail","deleteHead","deleteHead"]
 * [[],[3],[],[]]
 * 输出：[null,null,3,-1]
 *
 * 示例 2：
 * 输入：
 * ["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
 * [[],[],[5],[2],[],[]]
 * 输出：[null,-1,null,null,5,2]
 *
 * 相似题：
 * LeetCode 232. 用栈实现队列
 * LeetCode 225. 用队列实现栈
 *
 * @author kyan
 * @date 2020/2/22
 */
public class LCOF_9_ImplementQueueWith2Stack {

    public static class CQueue {

        private Stack<Integer> inStack;
        private Stack<Integer> outStack;

        public CQueue() {
            inStack = new Stack<>();
            outStack = new Stack<>();
        }

        public void appendTail(int value) {
            inStack.push(value);
        }

        public int deleteHead() {
            if (outStack.isEmpty()) {
                //如果outStack为空，先把inStack中的元素转移到outStack
                while (!inStack.isEmpty()) {
                    outStack.push(inStack.pop());
                }
            }
            //如果outStack不为空，就从outStack中pop出；如果从inStack转移后outStack还是空，则说明队列空，返回-1
            return outStack.isEmpty() ? -1 : outStack.pop();
        }
    }

    public static void main(String[] args) {
        CQueue queue = new CQueue();
        System.out.println(queue.deleteHead());
        queue.appendTail(3);
        queue.appendTail(4);
        System.out.println(queue.deleteHead());
        System.out.println(queue.deleteHead());
    }
}
