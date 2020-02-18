package problems.linkedlist;

import problems.common.util.ListNode;

/**
 * 237. 删除链表中的节点
 * 难度：简单
 * <p>
 * 请编写一个函数，使其可以删除某个链表中给定的（非末尾）节点，你将只被给定要求被删除的节点。
 * <p>
 * 现有一个链表 -- head = [4,5,1,9]，它可以表示为:
 * 4 -> 5 -> 1 -> 9
 * <p>
 * 示例 1:
 * 输入: head = [4,5,1,9], node = 5
 * 输出: [4,1,9]
 * 解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
 * <p>
 * 示例 2:
 * 输入: head = [4,5,1,9], node = 1
 * 输出: [4,5,9]
 * 解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
 *  
 * <p>
 * 说明:
 * <p>
 * 链表至少包含两个节点。
 * 链表中所有节点的值都是唯一的。
 * 给定的节点为非末尾节点并且一定是链表中的一个有效节点。
 * 不要从你的函数中返回任何结果。
 *
 * @author kyan
 * @date 2020/1/7
 */
public class LeetCode_237_DeleteNodeInLinkedList {

    /**
     * 该题目是阅读理解题，意思是要在链表中删除当前给定的节点
     */
    public static class Solution1 {

        public void deleteNode(ListNode node) {
            //之所以可以直接获取node.next.val而不怕node.next为空是因为题干中说明了：给定的节点为非末尾节点并且一定是链表中的一个有效节点
            ListNode nextTemp = node.next;
            node.val = nextTemp.val;
            node.next = nextTemp.next;
            nextTemp.next = null; //help gc
        }
    }

}
