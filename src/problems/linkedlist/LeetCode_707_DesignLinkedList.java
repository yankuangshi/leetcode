package problems.linkedlist;

/**
 * 707. 设计链表
 * 难度：中等
 * 设计链表的实现。您可以选择使用单链表或双链表。单链表中的节点应该具有两个属性：val 和 next。val 是当前节点的值，next 是指向下一个节点的指针/引用。
 * 如果要使用双向链表，则还需要一个属性 prev 以指示链表中的上一个节点。假设链表中的所有节点都是 0-index 的。
 * <p>
 * 在链表类中实现这些功能：
 * <p>
 * get(index)：获取链表中第 index 个节点的值。如果索引无效，则返回-1。
 * addAtHead(val)：在链表的第一个元素之前添加一个值为 val 的节点。插入后，新节点将成为链表的第一个节点。
 * addAtTail(val)：将值为 val 的节点追加到链表的最后一个元素。
 * addAtIndex(index,val)：在链表中的第 index 个节点之前添加值为 val  的节点。如果 index 等于链表的长度，则该节点将附加到链表的末尾。如果 index 大于链表长度，则不会插入节点。如果index小于0，则在头部插入节点。
 * deleteAtIndex(index)：如果索引 index 有效，则删除链表中的第 index 个节点。
 *  
 * 示例：
 * <p>
 * MyLinkedList linkedList = new MyLinkedList();
 * linkedList.addAtHead(1);
 * linkedList.addAtTail(3);
 * linkedList.addAtIndex(1,2);   //链表变为1-> 2-> 3
 * linkedList.get(1);            //返回2
 * linkedList.deleteAtIndex(1);  //现在链表是1-> 3
 * linkedList.get(1);            //返回3
 *
 * @author kyan
 * @date 2020/1/27
 */
public class LeetCode_707_DesignLinkedList {

    public static class ListNode {
        int val;
        ListNode prev;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", prev=" + prev +
                    ", next=" + next +
                    '}';
        }
    }

    /**
     * time 14ms, beat 72.65%
     * space 44.7MB, beat 57.75%
     */
    public static class MyLinkedList {

        private ListNode head;
        private ListNode tail;
        private int count;

        /**
         * Initialize your data structure here.
         */
        public MyLinkedList() {
        }

        /**
         * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
         */
        public int get(int index) {
            if (index < 0 || index > count - 1) return -1;
            ListNode p = head;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
            return p.val;
        }

        /**
         * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
         */
        public void addAtHead(int val) {
            ListNode newHead = new ListNode(val);
            if (isEmpty()) {
                head = newHead;
                tail = head;
            } else {
                newHead.next = head;
                head.prev = newHead;
                head = newHead;
            }
            count++;
        }

        /**
         * Append a node of value val to the last element of the linked list.
         */
        public void addAtTail(int val) {
            ListNode newTail = new ListNode(val);
            if (isEmpty()) {
                tail = newTail;
                head = tail;
            } else {
                tail.next = newTail;
                newTail.prev = tail;
                tail = newTail;
            }
            count++;
        }

        /**
         * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
         */
        public void addAtIndex(int index, int val) {
            if (index < 0 || index > count) return;
            if (index == 0) {
                addAtHead(val);
                return;
            }
            if (index == count) {
                addAtTail(val);
                return;
            }
            ListNode p = head;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
            ListNode newNode = new ListNode(val);
            newNode.prev = p.prev;
            newNode.next = p;
            p.prev.next = newNode;
            p.prev = newNode;
            count++;
        }

        /**
         * Delete the index-th node in the linked list, if the index is valid.
         */
        public void deleteAtIndex(int index) {
            if (index < 0 || index > count - 1) return;
            if (index == 0) {
                //remove head
                ListNode newHead = head.next;
                head.next = null;
                //防止newHead为null
                if (newHead != null) {
                    newHead.prev = null;
                }
                head = newHead;
                count--;
                return;
            }
            if (index == count - 1) {
                //remove tail
                ListNode oldTail = tail;
                tail = tail.prev;
                tail.next = null;
                oldTail.prev = null;
                count--;
                return;
            }
            ListNode p = head;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
            p.prev.next = p.next;
            p.next.prev = p.prev;
            p.next = null;
            p.prev = null;
            count--;
            return;
        }

        public boolean isEmpty() {
            return count == 0 ? true : false;
        }

        @Override
        public String toString() {
            ListNode p = head;
            StringBuilder stringBuilder = new StringBuilder();
            while (p != null) {
                stringBuilder.append(p.val).append("->");
                p = p.next;
            }
            if (stringBuilder.length() > 2) {
                stringBuilder = stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            }
            return stringBuilder.toString();
        }
    }

    /**
     * Your MyLinkedList object will be instantiated and called as such:
     * MyLinkedList obj = new MyLinkedList();
     * int param_1 = obj.get(index);
     * obj.addAtHead(val);
     * obj.addAtTail(val);
     * obj.addAtIndex(index,val);
     * obj.deleteAtIndex(index);
     */
    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList();
        linkedList.addAtHead(1);
        System.out.println(linkedList);
        linkedList.addAtTail(3);
        System.out.println(linkedList);
        linkedList.addAtIndex(1, 2);
        System.out.println(linkedList);
        linkedList.get(1);
        linkedList.deleteAtIndex(1);
        System.out.println(linkedList);
        linkedList.get(1);
    }
}
