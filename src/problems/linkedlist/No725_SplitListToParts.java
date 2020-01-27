package problems.linkedlist;

/**
 * 725. 分隔链表
 * 难度：中等
 *
 * 给定一个头结点为 root 的链表, 编写一个函数以将链表分隔为 k 个连续的部分。
 *
 * 每部分的长度应该尽可能的相等: 任意两部分的长度差距不能超过 1，也就是说可能有些部分为 null。
 *
 * 这k个部分应该按照在链表中出现的顺序进行输出，并且排在前面的部分的长度应该大于或等于后面的长度。
 *
 * 返回一个符合上述规则的链表的列表。
 *
 * 举例： 1->2->3->4, k = 5 // 5 结果 [ [1], [2], [3], [4], null ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/split-linked-list-in-parts
 *
 * 示例 1：
 * 输入:
 * root = [1, 2, 3], k = 5
 * 输出: [[1],[2],[3],[],[]]
 *
 * 示例 2：
 * 输入:
 * root = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 3
 * 输出: [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/split-linked-list-in-parts
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *
 * @author kyan
 * @date 2020/1/27
 */
public class No725_SplitListToParts {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }

    /**
     * time 0ms, beat 100%
     * space 37.5MB, beat 5.06%
     *
     * @param root
     * @param k
     * @return
     */
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] arr = new ListNode[k];
        if (root == null) {
            return arr;
        } else if (k == 1) {
            arr[0] = root;
            return arr;
        }
        //遍历链表计算节点总数量size
        int size = 0;
        ListNode p = root;
        while (p != null) {
            size++;
            p = p.next;
        }
        //把链表分隔成k个part
        for (int i = 0; i < k; i++) {
            //计算每一part的节点数量
            int partSize = size / (k-i) + (size % (k-i) > 0 ? 1 : 0);
            size = size - partSize;
            arr[i] = root;
            while (partSize > 0) {
                ListNode nextTemp = root.next;
                //截断链表
                if (partSize == 1) {
                    root.next = null;
                }
                root = nextTemp;
                partSize--;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        ListNode root = new ListNode(1);
        root.next = new ListNode(2);
        root.next.next = new ListNode(3);
        root.next.next.next = new ListNode(4);
        root.next.next.next.next = new ListNode(5);
        root.next.next.next.next.next = new ListNode(6);
        root.next.next.next.next.next.next = new ListNode(7);
        root.next.next.next.next.next.next.next = new ListNode(8);
        root.next.next.next.next.next.next.next.next = new ListNode(9);
        root.next.next.next.next.next.next.next.next.next = new ListNode(10);
        No725_SplitListToParts solution = new No725_SplitListToParts();
        ListNode[] ret = solution.splitListToParts(root, 6);
        for (ListNode node : ret) {
            System.out.println(node);
        }
    }
}
