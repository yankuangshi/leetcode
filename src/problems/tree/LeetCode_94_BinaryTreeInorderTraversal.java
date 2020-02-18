package problems.tree;

import problems.common.util.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 94. 二叉树的中序遍历
 * 难度：简单
 * <p>
 * 给定一个二叉树，返回它的中序遍历。
 * <p>
 * 示例:
 * <p>
 * 输入: [1,null,2,3]
 *  1
 *   \
 *   2
 *  /
 * 3
 * <p>
 * 输出: [1,3,2]
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 * <p>
 * 相关练习:
 * 102. 二叉树的层次遍历
 * 107. 二叉树的层次遍历 II
 * 144. 二叉树的前序遍历
 * 145. 二叉树的后序遍历
 *
 * @author kyan
 * @date 2020/1/4
 */
public class LeetCode_94_BinaryTreeInorderTraversal {

    /**
     * 递归思想：left->root->right
     */
    public static class Solution1 {

        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            inorderTraversalInternally(root, list);
            return list;
        }

        private void inorderTraversalInternally(TreeNode root, List<Integer> list) {
            if (root == null) return;
            inorderTraversalInternally(root.left, list);
            list.add(root.val);
            inorderTraversalInternally(root.right, list);
        }
    }

    /**
     * 非递归思想：依靠栈来完成中序遍历
     *     1
     *    / \
     *   2   3
     *  / \ / \
     * 4  5 6  7
     * 首先深度遍历把根节点（包括在内）的左子节点都入栈 [1,2,4]
     * 然后出栈元素，检测每个出栈的出栈是否含有右子树，如果有右子树，则继续把右子树中的左节点都入栈
     * 入栈[1,2,4]  队列[]
     * 出栈[1,2]    队列[4]
     * 出栈[1]      队列[4,2]
     * 入栈[1,5]    队列[4,2]
     * 出栈[1]      队列[4,2,5]
     * 出栈[]       队列[4,2,5,1]
     * 入栈[3,6]    队列[4,2,5,1]
     * 出栈[3]      队列[4,2,5,1,6]
     * 出栈[]       队列[4,2,5,1,6,3]
     * 入栈[7]      队列[4,2,5,1,6,3]
     * 出栈[]       队列[4,2,5,1,6,3,7]
     */
    public static class Solution2 {

        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            if (root == null) return list;
            LinkedList<TreeNode> stack = new LinkedList<>();
    //        while (root != null) {
    //            stack.push(root);
    //            root = root.left;
    //        }
    //        while (!stack.isEmpty()) {
    //            TreeNode currNode = stack.pop();
    //            list.add(currNode.val);
    //            TreeNode tempNode = currNode.right;
    //            while (tempNode != null) {
    //                stack.push(tempNode);
    //                tempNode = tempNode.left;
    //            }
    //        }
            //以上代码可以简化成
            TreeNode currNode = root;
            while (!stack.isEmpty() || currNode != null) {
                while (currNode != null) {
                    stack.push(currNode);
                    currNode = currNode.left;
                }
                TreeNode node = stack.pop();
                list.add(node.val);
                currNode = node.right;
            }
            return list;
        }
    }

    public static class Solution3 {

        /**
         * 颜色标记：WRITE表示未访问过，GREY表示访问过
         */
        public enum Color {
            WHITE, GREY
        }

        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            if (root == null) return list;
            LinkedList<TreeNode> nodeStack = new LinkedList<>();
            LinkedList<Color> colorStack = new LinkedList<>();
            nodeStack.push(root);
            colorStack.push(Color.WHITE);
            while (!nodeStack.isEmpty() && !colorStack.isEmpty()) {
                TreeNode currNode = nodeStack.pop();
                Color color = colorStack.pop();
                if (color == Color.GREY) {
                    list.add(currNode.val);
                    continue;
                }
                if (currNode.right != null) {
                    nodeStack.push(currNode.right);
                    colorStack.push(Color.WHITE);
                }
                nodeStack.push(currNode);
                colorStack.push(Color.GREY);
                if (currNode.left != null) {
                    nodeStack.push(currNode.left);
                    colorStack.push(Color.WHITE);
                }
            }

            return list;
        }
    }



    public static void main(String[] args) {
        //      1
        //    /   \
        //   2     3
        //  / \   / \
        // 4   5 6   7
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        System.out.println(new Solution1().inorderTraversal(root));
        System.out.println(new Solution2().inorderTraversal(root));
        System.out.println(new Solution3().inorderTraversal(root));
        //should print [4, 2, 5, 1, 6, 3, 7]
    }
}
