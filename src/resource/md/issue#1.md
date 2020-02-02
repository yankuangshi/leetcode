# [LeetCode] 单调栈问题总结 #496 #503 #739 #1019 #42 #84

单调栈即满足单调性的栈结构，可以是单调递增栈或是单调递减栈。

以下以伪代码来维护一个整数类型的单调递增栈为例：

现在假设已有单调递增栈，栈中自底向上元素是 [1,3,5]，现在要插入元素2，则需要依次弹出元素5，3之后，元素2才能入栈，得以维持栈的单调递增性，操作后栈变为 [1,2]

```
插入元素2

| 5 |
| 3 |
|_1_| 

2<栈顶元素5，5出栈

|   |
| 3 |
|_1_| 

2<栈顶元素3，3出栈

|   |
|   |
|_1_| 

2>栈顶元素1，2入栈

|   |
| 2 |
|_1_|
```

用伪代码描述以上过程：

```
//x为待入栈的元素
while (!stack.isEmpty() && x < stack.peek()) {
    stack.pop()
}
stack.push(x)
```

同样的，对于单调递减栈 [5,3,1]，当需要插入元素4时，需要依次弹出1，3之后，元素4才能入栈

```
插入元素4

| 1 |
| 3 |
|_5_|

4>栈顶元素1，1出栈

|   |
| 3 |
|_5_|

4>栈顶元素3，3出栈

|   |
|   |
|_5_|

4<栈顶元素5，4入栈

|   |
| 4 |
|_5_|
```

同样的，以下伪代码可以用于维护一个单调递减栈：

```
while (!stack.isEmpty() && x > stack.peek()) {
    stack.pop()
}
stack.push(x)
```

该类数据结构可以解决以下几类问题：

1. 求当前元素的下一个更大元素 (next greater element)
2. 求当前元素的下一个更小元素 (next smaller element)
3. 求当前元素的上一个更大元素 (pre greater element)
4. 求当前元素的上一个更小元素 (pre smaller element)

### [LeetCode #496. 下一个更大元素 I](https://leetcode-cn.com/problems/next-greater-element-i)

题目描述

给定两个没有重复元素的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。找到 nums1 中每个元素在 nums2 中的下一个比其大的值。

nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出-1。

示例1：
```
输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
输出: [-1,3,-1]
解释:
    对于num1中的数字4，你无法在第二个数组中找到下一个更大的数字，因此输出 -1。   
    对于num1中的数字1，第二个数组中数字1右边的下一个较大数字是 3。
    对于num1中的数字2，第二个数组中没有下一个更大的数字，因此输出 -1。
```

示例 2:
```
输入: nums1 = [2,4], nums2 = [1,2,3,4].
输出: [3,-1]
解释:
    对于num1中的数字2，第二个数组中的下一个较大数字是3。
    对于num1中的数字4，第二个数组中没有下一个更大的数字，因此输出 -1。
```

注意:
1. nums1和nums2中所有元素是唯一的。
2. nums1和nums2 的数组大小都不超过1000。

解法一：

利用一个单调递减栈和一个哈希表，单调递减栈用于获取当前元素的下一个更大元素，哈希表用于存储当前元素的下一个更大元素的映射关系，便于`nums1`数组中的元素可以快速定位。
但是要注意，之所以这里可以用哈希表，是因为题干中说了`nums1`和`nums2`数组中的元素都是唯一的，否则有可能哈希表中数据会被覆盖。

例如：nums2 = [2,1,3,4]
```
我们构造一个单调递减栈（自下而上递减）

step1：[2,1,3,4] stack empty,push(2)
        ^
|   |
|   |
|_2_|

step2：[2,1,3,4] 1<top(2),push(2)
          ^
|   |
| 1 |
|_2_|

step3：[2,1,3,4] 3>top(1),pop(1)
            ^
|   |   
| 2 |  mappings (1,3)
|_3_|

step4：[2,1,3,4] 3>top(2),pop(2),push(3)
            ^
|   |   
|   |  mappings (1,3) (2,3)
|_3_|

step5：[2,1,3,4] 4>top(3),pop(3),push(4)
              ^
|   |   
|   |  mappings (1,3) (2,3) (3,4)
|_4_|

step6：pop(4)

|   |   mappings (1,3) (2,3) (3,4) (4,-1)
|   |
|___|

所以对于nums2=[2,1,3,4]
元素2的下一个更大树是 mappings.get(2) = 3
元素1的下一个更大树是 mappings.get(1) = 3
元素3的下一个更大树是 mappings.get(3) = 4
元素4的下一个更大树是 mappings.get(4) = -1
```

```java
/**
 4ms, beat 85.68%
 * 37.6MB, beat 7.29%
 */
class Solution {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        LinkedList<Integer> stack = new LinkedList<>();
        Map<Integer, Integer> mappings = new HashMap<>();
        for (int n : nums2) {
            while (!stack.isEmpty() && stack.peek() < n) {
                mappings.put(stack.pop(), n); //映射被弹出的元素的下一个更大元素
            }
            stack.push(n);
        }
        int[] ans = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = mappings.getOrDefault(nums1[i], -1);
        }
        return ans;
    }
}
```

解法二：

解法一中，对于数组`nums2`我们是从前往后遍历来构造单调递减栈的，可以看出获取的结果顺序并不是按元素在数组中的顺序依次获得的。
解法二，我们尝试从后往前遍历数组`nums2`，仍旧使用单调递减栈，过程如下：

```
step1：[1,2,4,3] stack empty,push(3)
              ^
|   |
|   | mappings (3,-1)
|_3_|

step2：[1,2,4,3] 4>top(3),pop(3),push(4)
            ^
|   |
|   | mappings (3,-1) (4,-1)
|_4_|

step3：[1,2,4,3] 2<top(4),push(2)
          ^
|   |
| 2 | mappings (3,-1) (4,-1) (2,4)
|_4_|

step4：[1,2,4,3] 1<top(2),push(1)
        ^
| 1 |
| 2 | mappings (3,-1) (4,-1) (2,4) (1,2)
|_4_|

因此对于数组[1,2,4,3]，对应的next greater element数组为 [2,4,-1,-1]
```

```java
/**
 * 4ms, beat 85.82%
 * 37.3MB, beat 26.36%
 */
class Solution {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> mappings = nextGreaterElementHelper(nums2);
        int[] ans = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = mappings.get(nums1[i]);
        }
        return ans;
    }

    private Map<Integer, Integer> nextGreaterElementHelper(int[] nums) {
        Map<Integer, Integer> mappings = new HashMap<>();
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }
            mappings.put(nums[i], stack.isEmpty() ? -1 : stack.peek());
            stack.push(nums[i]);
        }
        return mappings;
    }
}
```

---

### [LeetCode #503. 下一个更大元素 II](https://leetcode-cn.com/problems/next-greater-element-ii/)

题目描述

给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。

示例 1:
```
输入: [1,2,1]
输出: [2,-1,2]
解释: 第一个 1 的下一个更大的数是 2；
数字 2 找不到下一个更大的数； 
第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
```
注意: 输入数组的长度不会超过 10000。

解法一：

比较直观的想法是把该数组扩展至2倍，然后利用单调递减栈，从后往前遍历扩展后的数组依次找出下一个更大值，过程如下：

例如：nums = [3,8,4,1,2]

```
[3,8,4,1,2] 扩展至2倍 [3,8,4,1,2,3,8,4,1,2]
然后创建一个数组用于存储结果，从后往前遍历数组 [3,8,4,1,2,3,8,4,1,2]

step1：[3,8,4,1,2,3,8,4,1,2] stack empty,push(2)
                          ^
|   |
|   | [ , , , , , , , , ,-1]
|_2_|

step2：[3,8,4,1,2,3,8,4,1,2] 1<top(2),push(1)
                        ^
|   |
| 1 | [ , , , , , , , ,2,-1]
|_2_|

step3：[3,8,4,1,2,3,8,4,1,2] 4>top(1),pop(1),4>top(2),pop(2),push(4)
                      ^
|   |
|   | [ , , , , , , ,-1,2,-1]
|_4_|

step4：[3,8,4,1,2,3,8,4,1,2] 8>top(4),pop(4),push(8)
                    ^
|   |
|   | [ , , , , , ,-1,-1,2,-1]
|_8_|

step5：[3,8,4,1,2,3,8,4,1,2] 3<top(8),push(3)
                  ^
|   |
| 3 | [ , , , , ,8,-1,-1,2,-1]
|_8_|

step6：[3,8,4,1,2,3,8,4,1,2] 2<top(3),push(2)
                ^
| 2 |
| 3 | [ , , , ,3,8,-1,-1,2,-1]
|_8_|

step7：[3,8,4,1,2,3,8,4,1,2] 1<top(2),push(1)
              ^
| 1 |
| 2 |
| 3 | [ , , ,2,3,8,-1,-1,2,-1]
|_8_|

step8：[3,8,4,1,2,3,8,4,1,2] 4>top(1),pop(1),4>top(2),pop(2),4>top(3),pop(3),4<top(8),push(4)
            ^
|   |
| 4 | [ , ,8,2,3,8,-1,-1,2,-1]
|_8_|

step9：[3,8,4,1,2,3,8,4,1,2] 8>top(4),pop(4),8>=top(8),pop(8),push(8)
          ^
|   |
|   | [ ,-1,8,2,3,8,-1,-1,2,-1]
|_8_|

step10：[3,8,4,1,2,3,8,4,1,2] 3<top(8),push(3)
         ^
|   |
| 3 | [8,-1,8,2,3,8,-1,-1,2,-1]
|_8_|

所以最后结果取前5位 [8,-1,8,2,3]
```

```java
/**
 * 13ms, beat 92.7%
 * 40.6MB, beat 43.99%
 */
class Solution1 {

    public int[] nextGreaterElements(int[] nums) {
        LinkedList<Integer> stack = new LinkedList<>();
        int[] ans = new int[nums.length];
        int[] tempAns = new int[nums.length*2];
        int[] tempNums = new int[nums.length*2];
        //扩展2倍
        for (int i = 0; i < nums.length; i++) {
            tempNums[i] = nums[i];
            tempNums[nums.length + i] = nums[i];
        }
        for (int i = tempNums.length-1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= tempNums[i]) {
                //把小的栈顶数字弹出
                stack.pop();
            }
            tempAns[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(tempNums[i]);
        }
        for (int i = 0; i < nums.length; i++) {
            ans[i] = tempAns[i];
        }
        return ans;
    }
}
```

解法二（优化）：

解法一中，我们把原数组扩展至了2倍，我们也可以在原数组的基础上进行循环遍历而不需要扩展，区别在于结果数组中的值有可能会被第二次循环遍历所覆盖，
过程如下：

例如：[3,8,4,1,2]
```
step1：[3,8,4,1,2] stack empty,push(2)
                ^
|   |
|   | [ , , , ,-1]
|_2_|

step2：[3,8,4,1,2] 1<top(2),push(1)
              ^
|   |
| 1 | [ , , ,2,-1]
|_2_|

step3：[3,8,4,1,2] 4>top(1),pop(1),4>top(2),pop(2),push(4)
            ^
|   |
|   | [ , ,-1,2,-1]
|_4_|

step4：[3,8,4,1,2] 8>top(4),pop(4),push(8)
          ^
|   |
|   | [ ,-1,-1,2,-1]
|_8_|

step5：[3,8,4,1,2] 3<top(8),push(3)
        ^
|   |
| 3 | [8,-1,-1,2,-1]
|_8_|

step6：第二次循环 [3,8,4,1,2] 2<top(3),push(2)
                          ^
| 2 |
| 3 | [8,-1,-1,2,3]
|_8_|

step7：[3,8,4,1,2] 1<top(2),push(1)
              ^
| 1 |
| 2 |
| 3 | [8,-1,-1,2,3]
|_8_|

step8：[3,8,4,1,2] 4>top(1),pop(1),4>top(2),pop(2),4>top(3),pop(3),4<top(8),push(4)
            ^
|   |
| 4 | [8,-1,8,2,3]
|_8_|

step9：[3,8,4,1,2] 8>top(4),pop(4),8>=top(8),pop(8),push(8)
          ^
|   |
|   | [8,-1,8,2,3]
|_8_|

step10：[3,8,4,1,2] 3<top(8),push(3)
         ^
|   |
| 3 | [8,-1,8,2,3]
|_8_|
```

```java
class Solution2 {

    public int[] nextGreaterElements(int[] nums) {
        int[] ans = new int[nums.length];
        LinkedList<Integer> stack = new LinkedList<>();
        //遍历次数为2*nums.length
        for (int i = nums.length*2-1; i >=0; i--) {
            int index = i % nums.length;
            while (!stack.isEmpty() && stack.peek() <= nums[index]) {
                stack.pop();
            }
            ans[index] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[index]);
        }
        return ans;
    }
}
```

---

### [LeetCode #1019. 链表中的下一个更大节点](https://leetcode-cn.com/problems/next-greater-node-in-linked-list)

题目描述

给出一个以头节点 head 作为第一个节点的链表。链表中的节点分别编号为：node_1, node_2, node_3, ... 。

每个节点都可能有下一个更大值（next larger value）：对于 node_i，如果其 next_larger(node_i) 是 node_j.val，那么就有 j > i 且  node_j.val > node_i.val，而 j 是可能的选项中最小的那个。如果不存在这样的 j，那么下一个更大值为 0 。

返回整数答案数组 answer，其中 answer[i] = next_larger(node_{i+1}) 。

注意：在下面的示例中，诸如 [2,1,5] 这样的输入（不是输出）是链表的序列化表示，其头节点的值为 2，第二个节点值为 1，第三个节点值为 5 。

示例 1：
```
输入：[2,1,5]
输出：[5,5,0]
```
示例 2：
```
输入：[2,7,4,3,5]
输出：[7,0,5,5,0]
```
示例 3：
```
输入：[1,7,5,1,9,2,5,1]
输出：[7,9,9,9,0,5,0,0]
```
提示：
对于链表中的每个节点，1 <= node.val <= 10^9
给定列表的长度在 [0, 10000] 范围内

解法一：

一种思路是先把链表转换成数组，由于题目中提示了链表长度最大是10000，因此可以先初始化一个长度10000的临时整型数组`int nums = new int[10000]`用于存放链表数据。
然后遍历链表把节点数据一一存入临时数组，链表遍历完成能够得到链表总长度`count`，再从后往前遍历临时数组`nums[count-1] ~ nums[0]`，利用单调递减栈找出下一个更大值，
过程如上[LeetCode #503.下一个更大元素 II]解法。

```java
/**
 * 18 ms, 94.77%
 * 40.4 MB, 46.32%
 */
class Solution1 {

    public int[] nextLargerNodes(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new int[1];
        int[] nums = new int[10000];
        int count = 0;
        while (head != null) {
            nums[count] = head.val;
            count++;
            head = head.next;
        }
        int[] res = new int[count];
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = count-1; i >=0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? 0 : stack.peek();
            stack.push(nums[i]);
        }
        return res;
    }
}
```

解法二：

仍旧利用单调递减栈，从前往后遍历链表。由于从前往后遍历依次入栈的话，是无法保证按顺序获取到下一个更大值的结果的，因此需要记录链表节点的index，单调栈中存储的应该是
节点的index，而不是节点值，过程如下：

例如：2->7->4->3->5
```
step1: [2,7,4,3,5] 栈空，入栈第一个节点2对应的index=0
index:  0,1,2,3,4 
        ^
|   |
|   |
|_0_|

step2: [2,7,4,3,5] 栈顶index=0对应的2，小于待入栈的7，index=0弹出，节点7的index=1入栈
index:  0,1,2,3,4 
          ^
|   |  
|   |  res[0]=7
|_1_|

step3: [2,7,4,3,5] 栈顶index=1对应的7，大于待入栈的4，节点4的index=2入栈
index:  0,1,2,3,4 
            ^
|   |
| 2 |
|_1_|

step4: [2,7,4,3,5] 栈顶index=2对应的4，大于待入栈的3，节点3的index=3入栈
index:  0,1,2,3,4 
              ^
| 3 |
| 2 |
|_1_|

step5: [2,7,4,3,5] 栈顶index=3对应的3，小于5，index=3弹出，栈顶index=2对应的4，小于5，继续弹出index=2，栈顶index=1对应的7，大于5，5的index=4入栈
index:  0,1,2,3,4 
                ^
|   |
| 4 |  res[3]=5 res[2]=5
|_1_|

最后结果为 res=[7,0,5,5,0]
```

解法一中我们先是遍历了一次链表获取到链表长度后再初始化结果数组`res=new int[count]`，解法二中我们也可以先初始化一个题目允许的最长长度数组用于临时存储
结果`ans=new int[10000]`，一边遍历链表一边获得结果，最后再返回最终的前`count`个数组值。
```java
/**
 * 22ms, beat 93.33%
 * 40.2MB, beat 60.05%
 */
class Solution2 {

    public int[] nextLargerNodes(ListNode head) {
        //nums数组用于存放链表节点值
        int[] nums = new int[10000];
        //ans数组用于临时存放结果
        int[] ans = new int[10000];
        LinkedList<Integer> stack = new LinkedList<>();
        int count = 0; //用作链表的cursor，遍历完成后即是链表长度
        while (head != null) {
            nums[count] = head.val;
            while (!stack.isEmpty() && nums[stack.peek()] < head.val) {
                ans[stack.pop()] = head.val;
            }
            stack.push(count);
            //cursor+1，链表指针后移
            count++;
            head = head.next;
        }
        int[] res = new int[count];
        for (int i = 0; i < count; i++) {
            res[i] = ans[i];
        }
        return res;
    }
}
```

解法三：

思路和解法二相似，从前往后遍历链表，不把链表转换成数组，而是把节点`ListNode`和节点对应的`index`包装成一个新类：

```java
/**
 * time 13ms, beat 95.50%
 * space 41.3MB, beat 12.84%
 */
class Solution3 {

    static class IndexNode {
        ListNode node;
        int index;

        public IndexNode(ListNode node, int index) {
            this.node = node;
            this.index = index;
        }
    }
    public int[] nextLargerNodes(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new int[1];
        int count = 0;
        ListNode cursor = head;
        while (cursor != null) {
            count++;
            cursor = cursor.next;
        }
        int[] res = new int[count];
        int i = 0;
        LinkedList<IndexNode> stack = new LinkedList<>();
        while (head != null) {
            while (!stack.isEmpty() && stack.peek().node.val < head.val) {
                res[stack.pop().index] = head.val;
            }
            stack.push(new IndexNode(head, i));
            i++;
            head = head.next;
        }
        return res;
    }
}
```

解法四：

利用递归，可以实现从后往前遍历链表，同时利用单调递减栈的方式。
```java
/**
 * 21ms, beat 93.69%
 * 42.1MB, beat 5.09%
 */
class Solution4 {

    public int[] nextLargerNodes(ListNode head) {
        LinkedList<Integer> stack = new LinkedList<>();
        ArrayList<Integer> array = new ArrayList<>();
        reduce(head, stack, array);
        int size = array.size();
        int[] res = new int[size];
        //获取到的结果要反向取出
        for (int i = 0; i < size; i++) {
            res[i] = array.get(size-i-1);
        }
        return res;
    }

    private void reduce(ListNode node, LinkedList<Integer> stack, ArrayList<Integer> array) {
        if (node == null) {
            return;
        }
        reduce(node.next, stack, array);
        while (!stack.isEmpty() && stack.peek() <= node.val) {
            stack.pop();
        }
        array.add(stack.isEmpty() ? 0 : stack.peek());
        stack.push(node.val);
    }
}
```

---

### [LeetCode #739. 每日温度](https://leetcode-cn.com/problems/daily-temperatures/)

题目描述

根据每日 气温 列表，请重新生成一个列表，对应位置的输入是你需要再等待多久温度才会升高超过该日的天数。如果之后都不会升高，请在该位置用 0 来代替。

例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。

提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。

解法一：

计算出气温列表中每个温度的下一个更高温度所在的index
```
T   = [73,74,75,71,69,72,76,73]
ans = [ 1, 2, 6, 5, 5, 6, 6, 7]
```

T[i]的 next greater element 在 T 列表的 index=ans[i] 处，例如73(T[0])的 next greater element 是 T[ans[0]]=74，
74(T[1])的 next greater element 是 T[ans[1]]=75，当 ans[i]=i 的时候，说明 T[i] 的 next greater element 不存在。

最后结果只需要 ans[i]-i 即可。
```
ans   = [1,2,6,5,5,6,6,7]
index = [0,1,2,3,4,5,6,7]
final = [1,1,4,2,1,1,0,0]
```

```java
/**
 * time 35ms, beat 77.08%
 * space 43.5MB, beat 19.12%
 */
class Solution {

    public int[] dailyTemperatures(int[] T) {
        int[] days = new int[T.length];
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = T.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && T[stack.peek()] <= T[i]) {
                stack.pop();
            }
            days[i] = stack.isEmpty() ? i : stack.peek();
            stack.push(i);
        }
        for (int i = 0; i < days.length; i++) {
            days[i] = days[i] - i;
        }
        return days;
    }
}
```

---

### [LeetCode #84. 柱状图中最大的矩形](https://leetcode-cn.com/problems/largest-rectangle-in-histogram/)

题目描述

给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。

求在该柱状图中，能够勾勒出来的矩形的最大面积。

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/10/12/histogram.png)

以上是柱状图的示例，其中每个柱子的宽度为 1，给定的高度为 [2,1,5,6,2,3]。

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/10/12/histogram_area.png)

图中阴影部分为所能勾勒出的最大矩形面积，其面积为 10 个单位。

示例:
```
输入: [2,1,5,6,2,3]
输出: 10
```

解法一：

对于每个柱子，我们求出以该柱子为高的最大矩形面积`max_width[i]*height[i]`，然后比较每个柱子对应的最大矩形面积中的最大即可。

![largest_rectangle_area](https://user-images.githubusercontent.com/45935870/73605882-0b6df300-45df-11ea-8ffd-5ba3fc836724.jpeg)

柱子2的最大矩形面积是1\*2=2
柱子1的最大矩形面积是6\*1=6
柱子5的最大矩形面积是2\*5=10
柱子6的最大矩形面积是1\*6=6
柱子2的最大矩形面积是4\*2=8
柱子3的最大矩形面积是1\*3=3

最大面积是柱子5对应的2\*5=10

现在的问题就是如何求每个柱子的最大矩形对应的最大宽度`max_width`。根据前面几道题目的讨论，我们知道可以利用单调栈求下一个更大值，那么对于这道题，同样也可以利用
单调栈求上一个更小值和下一个更小值。可以发现数字5的上一个更小值是`heights[1]=1`，下一个更小值是`heights[4]=2`，这两个数的index差值减一就是我们需要求得的最大宽度。

那么对于某一个数如果没有上一个更小值，则可以记为-1，没有下一个更小值，则可以记为数组长度值`heights.length`，因此依次计算：

例如数组 T = [2,1,5,6,2,3]

2的pre smaller element不存在，index=-1；next smaller element为1，index=1
1的pre smaller element不存在，index=-1；next smaller element不存在，index=T.length=6
5的pre smaller element为1，index=1；next smaller element为2，index=4
6的pre smaller element为5，index=2；next smaller element为2，index=4
2的pre smaller element为1，index=1；next smaller element为不存在，index=T.length=6
3的pre smaller element为2，index=4；next smaller element为不存在，index=T.length=6

然后计算每个数对应的最大矩形面积 heights[i]*(next_smaller_element_index - pre_smaller_element_index - 1) ：

2=>2*[1-(-1)-1]=2
1=>1*[6-(-1)-1]=6
5=>5*(4-1-1)=10
6=>6*(4-2-1)=6
2=>2*(6-1-1)=8
3=>3*(6-4-1)=3

```java
/**
 * time 18ms, beat 83.54%
 * space 40.4MB, beat 86.66%
 */
class Solution1 {

    public int largestRectangleArea(int[] heights) {
        int[] preSmallerElements = preSmallerElements(heights);
        int[] nextSmallerElements = nextSmallerElements(heights);
        int res = 0;
        for (int i = 0; i < heights.length; i++) {
            res = Math.max(res, heights[i] * (nextSmallerElements[i] - preSmallerElements[i] - 1));
        }
        return res;
    }

    private int[] preSmallerElements(int[] heights) {
        int[] ans = new int[heights.length];
        LinkedList<Integer> stack = new LinkedList<>();
        //从后往前遍历
        for (int i = heights.length - 1; i >= 0; i--) {
            ans[i] = -1;
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                ans[stack.pop()] = i;
            }
            stack.push(i);
        }
        return ans;
    }

    private int[] nextSmallerElements(int[] heights) {
        int[] ans = new int[heights.length];
        LinkedList<Integer> stack = new LinkedList<>();
        //从前往后遍历
        for (int i = 0; i < heights.length; i++) {
            ans[i] = heights.length;
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                ans[stack.pop()] = i;
            }
            stack.push(i);
        }
        return ans;
    }
}
```

解法二：

解法二在解法一的思路基础上对代码进行优化，可以通过一次遍历就求出每个数对应的上一个更小值和下一个更小值

```java
/**
 * time 10ms, beat 90.50%
 * space 39.6MB, beat 98.02%
 */
class Solution2 {

    public int largestRectangleArea(int[] heights) {
        int[] preSmallerElements = new int[heights.length];
        int[] nextSmallerElements = new int[heights.length];
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = 0; i < heights.length; i++) {
            nextSmallerElements[i] = heights.length;
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                nextSmallerElements[stack.pop()] = i;
            }
            preSmallerElements[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        int res = 0;
        for (int i = 0; i < heights.length; i++) {
            res = Math.max(res, heights[i] * (nextSmallerElements[i] - preSmallerElements[i] - 1));
        }
        return res;
    }

    public static void main(String[] args) {
        int[] heights = {2,1,5,6,2,3};
        System.out.println(new Solution1().largestRectangleArea(heights));
        System.out.println(new Solution2().largestRectangleArea(heights));
    }

}
```

---

### [LeetCode #42. 接雨水](https://leetcode-cn.com/problems/trapping-rain-water/)

题目描述

给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/10/22/rainwatertrap.png)

上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 感谢 Marcos 贡献此图。

示例:
```
输入: [0,1,0,2,1,0,1,3,2,1,2,1]
输出: 6
```

解法一：

要能够盛水，那肯定需要有凹槽才能盛水，凹槽的特点是左右两边数字比中间大就会形成凹槽，例如 1 0 2。

我们可以利用单调递减栈的特点，例如 某一个时刻单调递减栈的状态如下：
```
|   |
| 0 |
|_1_|
```

接下来数字2入栈，由于是递减栈，2>栈顶元素0，所以栈顶元素0需要弹出，因此这就形成了一个凹槽 [1,0,2]，由此可以来计算凹槽接水的面积

可以把数字想象成木板，1和2分别是左右2块木板，待入栈的2就是右边那块木板，而弹出0之后，栈顶的1就是左边的木板，要计算凹槽接水的面积，根据木桶原理，需要计算较短木板的高度，
然后再由较短木板高度减去弹出的数字就是实际能接水的高度差，而宽度则是右边木板和左边木板的距离差。

![trapping_water](https://user-images.githubusercontent.com/45935870/73605796-a665cd80-45dd-11ea-8e6f-8371fb3508aa.jpg)

面积计算=[min(待入栈height,栈顶height) - 出栈height]*(待入栈height的下标 - 栈顶height的下标 - 1)

例如 [1,0,2] 这个凹槽的接水面积：[min(2,1)-0]*(3-1-1)=1

```java
class Solution1 {

    public int trap(int[] heights) {
        int res = 0;
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] < heights[i]) {
                int tempHeight = heights[stack.pop()];
                if (!stack.isEmpty()) {
                    int leftIndex = stack.peek();
                    res += (Math.min(heights[leftIndex], heights[i]) - tempHeight) * (i - leftIndex - 1);
                }
            }
            stack.push(i);
        }
        return res;
    }
}
```