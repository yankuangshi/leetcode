# [LeetCode] TopK问题总结及215

TopK经典问题：从 nums 无序数组中，找出最大的K个数，这就是经典的TopK问题。

TopK的问题可以转化为 LeetCode 中的与之类似的一题，即

### [LeetCode #215. 数组中的第K个最大元素](https://leetcode-cn.com/problems/kth-largest-element-in-an-array/)

题目描述：

在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。

示例 1:
```
输入: [3,2,1,5,6,4] 和 k = 2
输出: 5
示例 2:

输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
输出: 4
```
说明:

你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。

### 解法一

最容易想到的就是对无序数组进行全局排序，如果是降序排列，则 nums[k-1] 就是第K大元素；如果是升序排序，则 nums[len-k] 是第 K 大元素。

这里不自己实现快排算法，使用JDK自带的`Arrays.sort`进行排序，时间复杂度是O(NlogN)。

```java
/**
 * 2 ms, 96.89%
 * 37 MB, 92.87%
 */
class Solution {

    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length-k];
    }

}
```

### 解法二

解法二中对数组进行了全局排序，但是其实对于求第 K 大元素，只需要对最大的前 K 个元素进行局部排序就可以了。因此可以借助冒泡思想，每次从数组中找到最大值，经过 K 次冒泡，就可以求出第 K 大元素。

我们知道全局的冒泡排序平均时间复杂度是O(N^2)，那么对于局部的只冒泡K次的时间复杂度是O(K*N)。当 K 比较小时，比如1、2，那么最好的时间复杂度是 O(N)，但是当 K 等于 N/2 或者接近 N 时，这种情况的时间复杂度就是O(N^2)，从实际的代码运行中，我们也可以看出该解法的效率很低。

```java
/**
 * 97 ms, 5.04%
 * 37.9 MB, 45.73%
 */
class Solution {

    public int findKthLargest(int[] nums, int k) {
        int len = nums.length;
        //外循环，冒泡K次
        for (int i = 0; i < k; i++) {
            //内循环，两两比较大小
            // 如果 nums[j] 比 nums[j+1] 大，则交换位置，最大的元素会被移到数组末尾
            for (int j = 0; j < len - 1 - i; j++) {
                if (nums[j] > nums[j+1]) {
                    swap(nums, j, j+1);
                }
            }
        }
        //经过K次冒泡后，数组 nums[len-k] ~ nums[len-1] 为最大的前K个元素，并按照升序排列
        return nums[len-k];
    }
    
    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
```

### 解法三

借助堆的特性（小顶堆的堆顶是最小元素，大顶堆的堆顶是最大元素）来解这道题。

* 如果使用大顶堆，先将数组元素依次放入大顶堆中，然后循环 K 次，从堆顶取出元素，第 K 次取出来的堆顶元素就是第 K 大元素。

* 如果使用小顶堆，也是先将数组元素依次放入小顶堆中，然后循环 len-K 次，从堆顶取出元素，第 len-K 次取出的堆顶元素就是第 K 大元素。

在 Java 中，可以直接借助优先级队列 PriorityQueue 来实现一个大顶堆或小顶堆。

```java
/**
 * 借助大顶堆的解法
 * 12 ms, 39.37%
 * 36.8 MB, 95.08%
 */
class Solution {

    public int findKthLargest(int[] nums, int k) {
        //构造函数，利用lambda表达式构造大顶堆 (a,b) -> b-a
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(nums.length, (a, b) -> b - a);
        for (int i = 0; i < nums.length; i++) {
            maxHeap.offer(nums[i]);
        }
        for (int j = 1; j < k; j++) {
            maxHeap.poll();
        }
        return maxHeap.peek();
    }
}

/**
 * 借助小顶堆的解法
 * 10 ms, 44.51%
 * 37 MB, 92.81%
 */
class Solution {

    public int findKthLargest(int[] nums, int k) {
        //构造函数，默认就是小顶堆
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            minHeap.offer(nums[i]);
        }
        for (int j = 0; j < nums.length - k; j++) {
            minHeap.poll();
        }
        return minHeap.peek();
    }
}
```

### 解法四

在解法三的基础上进行优化，不需要一开始就构建一个大小为数组长度的堆，可以只构建（维护）大小为 K 的堆即可。

算法过程如下：

1、先把数组中前 K 个元素加入优先级队列，构建一个小顶堆（此时堆顶的这个最小元素就是这 K 个元素中的第 K 大元素）
2、依次遍历剩下的数组元素 nums[k] ~ nums[len-1]
    
    * 如果小于等于堆顶元素，跳过
    * 如果大于堆顶元素，则和堆顶元素交换后重新堆化
    
前 K 个元素建堆的时间复杂度是 O(K)，遍历剩下的元素并且重新堆化的时间复杂度是 (N-K)\*O(logK)，因此总的时间复杂度是 O(K) + (N-K)\*(NlogK) ~ O(NlogK)

仍旧利用 Java 中的优先级队列 PriorityQueue 来帮助实现，但是由于 PriorityQueue 并没有对堆顶元素的 replace 功能，因此只能先 poll 出堆顶元素，再 offer 加入新的元素，由 PriorityQueue 自动进行重新堆化。

可以看出解法四比解法三在时间效率上提升不少。

```java
/**
 * 5 ms, 65.84%
 * 38.6 MB, 5.05%
 */
class Solution {

    public int findKthLargest(int[] nums, int k) {
        //构造一个大小为K的小顶堆
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        for (int i = 0; i < k; i++) {
            minHeap.offer(nums[i]);
        }
        for (int j = k; j < nums.length; j++) {
            if (nums[j] > minHeap.peek()) {
                //如果新元素比堆顶元素大，则替换
                //但是由于PriorityQueue不提供replace方法，只好先poll再offer
                minHeap.poll();
                minHeap.offer(nums[j]);
            }
        }
        return minHeap.peek();
    }

}
```

### 解法五

实现思路同解法四，但是由自己来实现小顶堆而不是借助优先级队列。从解法四的算法思路上看出，我们需要自己手动实现2个额外的方法

1、建"小顶堆"的方法 buildHeap
2、堆化方法 heapify

首先第一步是建堆，可以不借助另一个数组，完成"原地"建堆的，有2种思路：

1、第一种思路是从前往后处理数据，从下往上的堆化过程。该方法可以借助堆中插入一个元素的思路，假设起始堆中只包含了一个数据，就是下标为 0 的数据，然后我们把下标从 1 到 k-1 的数据依次插入堆中，这样就完成了建堆过程。
2、第二种思路是从后往前处理数据，从上往下堆化。该方法从后往前处理数组数据，从倒数第一个非叶子节点开始，依次堆化。

第二种思路示例如下：

```
以下例子中为了方便，数组从下标 1 开始

数组：x 7 5 19 8 4 1 20 13 16
下标：0 1 2 3  4 5 6 7  8  9
  
//从后往前，第一个非叶子节点是下标为4的元素8    

      7(1)
     /    \
    5(2)    19(3)
   /    \    /  \
  8(4) 4(5) 1(6) 20(7)
 /   \
13(8) 16(9)

//从元素8，从上而下堆化，和它的右子节点16进行交换

      7(1)
     /    \
    5(2)   19(3)
   /    \    /  \
  16(4) 4(5) 1(6) 20(7)
 /   \
13(8) 8(9)

//下一个非叶子节点是下标为3的元素19，和它的右子节点20进行交换

      7(1)
     /    \
    5(2)   20(3)
   /    \    /  \
  16(4) 4(5) 1(6) 19(7)
 /   \
13(8) 8(9)

//下一个非叶子节点是下标为2的元素5，5和16交换，5再和13交换

      7(1)
     /    \
    16(2)   20(3)
   /    \    /  \
  13(4) 4(5) 1(6) 19(7)
 /   \
5(8) 8(9)

//最后一个非叶子节点是下标为1的元素7，7和20交换，7再和19交换

      20(1)
     /    \
    16(2)   19(3)
   /    \    /  \
  13(4) 4(5) 1(6) 7(7)
 /   \
5(8) 8(9)

最后堆化完成后的数组变为：x 20 16 19 13 4 1 7 5 8
```

因此从上往下的堆化过程，用代码实现如下

```java
/**
 * 堆化函数
 * 如果数组中元素从下标 0 开始存储元素，那么对于下标为 i 的父节点，左右子节点的下标分别为 2*i+1 和 2*i+2
 * @param a 需要堆化的数组
 * @param k 为数组大小
 * @param i 当前父节点的下标
 */
public void heapify(int[] a, int k, int i) {
    //临时变量 minPos 用于存储最小值的下标，先假设父节点最小
    int minPos = i;
    while (true) {
        //和左子节点比较
        if (i*2+1 < k && a[i*2+1] < a[i]) minPos = i*2+1;
        //和右子节点比较
        if (i*2+2 < k && a[i*2+2] < a[minPos]) minPos = i*2+2;
        //如果minPos没有发生变化，说明父节点已经是最小了，直接跳出
        if (minPos == i) break;
        //否则交换
        swap(a, i, minPos);
        //父节点下标进行更新，继续堆化
        i = minPos;
    }
}
```

对于建堆，我们是从后往前遍历，需要找到倒数第一个非叶子节点。对于下标从 0 开始的大小为 K 的数组，倒数第一个非叶子节点下标为 K/2-1，因此建堆函数如下：

```java
/**
 * 建堆函数
 * 从倒数第一个非叶子节点开始堆化，倒数第一个非叶子节点下标为 K/2-1
 * @param a
 * @param k
 */
public void buildHeap(int[] a, int k) {
    for (int i = k/2 - 1; i >= 0; i--) {
        heapify(a, k, i);
    }
}
```

结合整体算法逻辑，最终的求第K大元素的代码如下：

```java
/**
 * 1 ms, 100%
 * 36.9 MB, 94.47%
 */
class Solution {

    public int findKthLargest(int[] nums, int k) {
        //前K个元素原地建小顶堆
        buildHeap(nums, k);
        //遍历剩下元素，比堆顶小，跳过；比堆顶大，交换后重新堆化
        for (int i = k; i < nums.length; i++) {
            if (nums[i] > nums[0]) {
                swap(nums, i, 0);
                heapify(nums, k, 0);
            }
        }
        //K个元素的小顶堆的堆顶即是第K大元素
        return nums[0];
    }

    /**
     * 建堆函数
     * 从倒数第一个非叶子节点开始堆化，倒数第一个非叶子节点下标为 K/2-1
     * @param a
     * @param k
     */
    public void buildHeap(int[] a, int k) {
        for (int i = k/2 - 1; i >= 0; i--) {
            heapify(a, k, i);
        }
    }

    /**
     * 堆化函数
     * 父节点下标i，左右子节点的下标分别为 2*i+1 和 2*i+2
     * @param a
     * @param k
     * @param i
     */
    public void heapify(int[] a, int k, int i) {
        //临时变量 minPos 用于存储最小值的下标，先假设父节点最小
        int minPos = i;
        while (true) {
            //和左子节点比较
            if (i*2+1 < k && a[i*2+1] < a[i]) minPos = i*2+1;
            //和右子节点比较
            if (i*2+2 < k && a[i*2+2] < a[minPos]) minPos = i*2+2;
            //如果minPos没有发生变化，说明父节点已经是最小了，直接跳出
            if (minPos == i) break;
            //否则交换
            swap(a, i, minPos);
            //父节点下标进行更新，继续堆化
            i = minPos;
        }
    }

    public void swap(int[] a, int n, int m) {
        int tmp = a[n];
        a[n] = a[m];
        a[m] = tmp;
    }

}
```

### 解法六

继"排序 + 小顶堆"解法，时间复杂度最小的方法。借助"快速排序"中的"快速选择" partition（分区函数）的方法实现。

顺便回忆一下”快排“的实现：
```
quick_sort(int[] arr, int low, int high) {
    if (low >= high) return;
    int i = partition(arr, low, high);
    quick_sort(arr, low, i-1);
    quick_sort(arr, i+1, high);
}
```

其中 partition 分区函数会任意选择一个元素（该解法中选择最后一个元素 nums[len-1]）作为 pivot（分区点），将数组中小于 pivot 的点都放置到其左边，将大于pivot的点都放置在其右边，最终 partition 函数返回 pivot 的下标 i

经过这一步骤后，数组将分成3部分

1、nums[0] ~ nums[i-1] 都是不大于 pivot 的元素
2、nums[i+1] ~ nums[len-1] 都是不小于 pivot 的元素
3、nums[i] 是 pivot 元素

partition 分区过程如下

![partition](https://user-images.githubusercontent.com/45935870/74081379-6db86f00-4a89-11ea-8534-99e4afa86a69.gif)

现在思考如何借助 partition 分区来帮助找到第K大元素？

如果 pivot 点刚好是第K大元素，那么它的左边一定有 K-1 个不小于它的元素，它的下标应该是 len-k（数组最末尾是 len-1）

所以
1、当 partition 函数返回的下标 i=len-k，则 arr[i] 就是我们要求的第K大元素
2、当 partition 函数返回的下标 i<len-k，那么说明第K大元素在下标 i 的右边，我们继续分区在 arr[i+1, len-1] 区间内查找：partition(arr, i+1, len-1)
3、当 partition 函数返回的下标 i>len-k，那么说明第K大元素在下标 i 的左边，我们继续分区在 arr[0, i-1] 区间内查找：partition(arr, 0, i-1)

该方法的时间复杂度是O(n)

第一次分区，需要对大小为n的数组执行分区操作，遍历n个元素；
第二次分区，只需要对大小为n/2的数组执行分区操作，遍历n/2个元素；
第三次分区，遍历n/4；
第四次分区，遍历n/8；
...
直到缩减为1
累加起来 n + n/2 + n/4 + n/8 + ... + 1 该公式是个等比数列，是个等比数列求和问题

假设
S = n + n/2 + n/4 + n/8 + .... + 1，两边同时乘以2
2S = 2n + n + n/2 + n/4 + ... + 2
2S - S = (2n + n + n/2 + n/4 + ... + 2) - (n + n/2 + n/4 + n/8 + .... + 1) = 2n-1
S = 2n-1
所以总体时间复杂度为O(n)

```java
/**
 * 41 ms, 14.81%
 * 38.1 MB, 26.92%
 */
class Solution {

    public int findKthLargest(int[] nums, int k) {
        int len = nums.length;
        int targetIndex = len - k;
        int low = 0, high = len - 1;
        while (true) {
            int i = partition(nums, low, high);
            if (i == targetIndex) {
                return nums[i];
            } else if (i < targetIndex) {
                low = i + 1;
            } else {
                high = i - 1;
            }
        }
    }

    /**
     * 分区函数，将 arr[high] 作为 pivot 分区点
     * i、j 两个指针，i 作为标记“已处理区间”和“未处理区间”的分界点，也即 i 左边的（low~i-1）都是“已处理区”。
     * j 指针遍历数组，当 arr[j] 小于 pivot 时，就把 arr[j] 放到“已处理区间”的尾部，也即是 arr[i] 所在位置
     * 因此 swap(arr, i, j) 然后 i 指针后移，i++
     * 直到 j 遍历到数组末尾 arr[high]，将 arr[i] 和 arr[high]（pivot点） 进行交换，返回下标 i，就是分区点的下标。
     */
    private int partition(int[] arr, int low, int high) {
        int i = low;
        int pivot = arr[high];
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high);
        return i;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
```

虽然该方法可以AC，但是时间效率很低，实际平均通过时间为 41ms, beat 14.81%。可以猜测应该是测试用例中有极端情况的存在，导致 partition 分区效率变低，比如数组为升序或倒序数组。因此可以通过随机选择 pivot 分区点来提高分区效率。

```java
private int partition(int[] arr, int low, int high) {
    if (high > low) {
        //在下标 low 和 high 之间随机选择，然后和下标 high 元素进行交换
        int random = low + new Random().nextInt(high - low);
        swap(arr, high, random);
    }
    int i = low;
    int pivot = arr[high];
    for (int j = low; j < high; j++) {
        if (arr[j] < pivot) {
            swap(arr, i, j);
            i++;
        }
    }
    swap(arr, i, high);
    return i;
}
```

通过以上优化，实际通过时间缩短到 2ms，beat 96.88%。

### 总结

总结一下以上各个解法

* 全局快速排序，时间复杂度 O(NlogN)；"原地"排序，空间复杂度O(1)；
* 局部冒泡排序，时间复杂度 O(K*N)，取决于 K 的大小，极端情况下会导致 O(N^2)，效率会很低；"原地"排序，空间复杂度O(1)；
* 堆（小顶堆或大顶堆），对整个数组全量构建堆，时间复杂度O(NlogN)；借助了优先级队列做为堆，空间复杂度O(N)；
* 堆（小顶堆），只对数组K个元素构建堆，时间复杂度O(NlogK)；借助了优先级队列做为堆，空间复杂度O(K)；
* 自己实现的堆，只对数组K个元素构建堆，时间复杂度O(NlogK)；"原地"建堆，空间复杂度O(1)；
* 快速选择，partition分区，时间复杂度O(N)；没有借助其他内存空间，空间复杂度O(1)；

---

### [LeetCode #347. 前 K 个高频元素](https://leetcode-cn.com/problems/top-k-frequent-elements/)

题目描述：

给定一个非空的整数数组，返回其中出现频率前 k 高的元素。

示例 1:
```
输入: nums = [1,1,1,2,2,3], k = 2
输出: [1,2]
```
示例 2:
```
输入: nums = [1], k = 1
输出: [1]
说明：
```
你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
你的算法的时间复杂度必须优于 O(nlogn) , n 是数组的大小。

### 思路

这道题其实就是 TopK 问题的应用，统计频次最高的前 K 个元素。

### 解法一（大顶堆）

首先统计频次这一步骤不可少，因此需要遍历一次数组 nums，用 HashMap 保存数字与出现频次的映射关系。
然后利用大顶堆（借助Java中的优先级队列），把每个 HashMap 中的 Entry 放入大顶堆中（通过比较 Entry 的 value 大小，Entry 的 value 值代表着数字出现的频次）
最后循环 K 次从堆顶取出 Entry（一共取出 K 个 Entry），把每个 Entry 的 key（Entry 的 key 代表着数字本身）放入返回列表

参考代码如下：

```java
/**
 * 执行用时：22 ms, 62.9%
 * 内存消耗：42.7 MB, 5.02%
 */
class Solution {

    public List<Integer> topKFrequent(int[] nums, int k) {
        if (nums == null) return null;
        Map<Integer, Integer> frequentMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            frequentMap.put(nums[i], frequentMap.getOrDefault(nums[i], 0) + 1);
        }
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>((e1, e2) -> (e2.getValue() - e1.getValue()));
        for (Map.Entry<Integer, Integer> entry : frequentMap.entrySet()) {
            pq.offer(entry);
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            res.add(pq.poll().getKey());
        }
        return res;
    }
}
```

### 解法二（小顶堆）

在解法一基础上优化：

1、堆内部并不需要存放 Map 的整个 Entry 对象，而只需要存放数字（也就是 Entry 的 key）即可，通过比较 Entry 的 value 大小（频次大小）入堆。
2、不需要对数组全部元素构建堆，只需要 HashMap 键值对中的前 K 个 key 构建大小 K 的小顶堆，之后的键值对中的 key 需要和堆顶 key 所对应的频次比较大小，如果比堆顶 key 的频次大，则移除堆顶 key，加入新的值。

最后从小顶堆依次取出结果，只不过从小顶堆取出的结果是按频次升序排序的，因为题目并没有要求必须按频次从高到低降序输出，所以可以不进行 reverse 操作

参考代码如下：

```java
/**
 * 执行用时：22 ms, 62.9%
 * 内存消耗：40.5 MB, 49.29%
 */ 
class Solution {

    public List<Integer> topKFrequent(int[] nums, int k) {
        if (nums == null) return null;
        Map<Integer, Integer> frequentMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            frequentMap.put(nums[i], frequentMap.getOrDefault(nums[i], 0) + 1);
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>((n1, n2) -> (frequentMap.get(n1) - frequentMap.get(n2)));
        for(Integer n : frequentMap.keySet()) {
            if (pq.size() < k) {
                pq.offer(n);
            } else {
                if (frequentMap.get(n) > frequentMap.get(pq.peek())) {
                    pq.poll();
                    pq.offer(n);
                }
            }
        }
        List<Integer> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            res.add(pq.poll());
        }
        //小顶堆取出的TopK结果是按频率升序的，因为题目并没有做要求必须按出现频率逆序输出，所以不进行reverse操作
        //Collections.reverse(res);
        return res;
    }
}
```

以上两种解法时间复杂度都是 O(NlogK)，那有没有效率更高的解法？

### 解法三

遍历数组统计频次存入 HashMap 后，创建一个大小为 nums.length + 1 的新数组用于存储数字，index 为该数字出现的频次

例如 nums = [1,1,1,2,2,3,3,3,3]

统计完得到数字与频次的键值对 hashmap = [(1,3),(2,2),(3,4)]

创建新数组 C 长度为 nums.length+1

```
         ---------------------
数组C[10] | | | | | | | | | | |
         ---------------------
下标       0 1 2 3 4 5 6 7 8 9
```

然后以频次作为下标，把数字和频次键值对依次填入新的数组 C

```
         ---------------------
数组C[10] |x|x|2|1|3|x|x|x|x|x|
         ---------------------
下标       0 1 2 3 4 5 6 7 8 9
```

最后，出现频次TopK的数字，只需要对数组 C 从后往前遍历（排除没值的index）K 次就可以了。

这里需要注意2点：

1、为什么新数组 C 的长度是 nums.length + 1 ？

假设最坏的情况就是 nums 数组中所有数字的出现频次都是1次，那么新数组 C 中至少要能够存放 nums.length 个数字，再加上 index=0 的位置意味着频次出现0的数字，这个数字是不存在的，因此 index=0 的位置将被空出来，所以新数组 C 的长度应该是 nums.length+1，多出的 1 就是给C[0]的。

2、如果频次相同怎么存放？

对于频次相同的数字，只能把它们放在数组 C 的同一个 bucket 中（感觉是不是很像 HashMap 发生哈希冲突时内部数组？），同一个 bucket 要能够存放2个不同数字，那么可以学习 HashMap 解决哈希冲突的方法，数组 C 的每个 bucket 存放 List 链表 List[] C = new List[len+1]

这种解法第一次统计频次遍历数组时间复杂度O(N)，之后也是使用数组来存放频次，因此总体时间复杂度为O(N)。

参考代码如下：

```java
/**
 * 执行用时：16 ms, 96.05%
 * 内存消耗：40.8 MB, 33.76%
 */
class Solution {

    public List<Integer> topKFrequent(int[] nums, int k) {
        if (nums == null) return null;
        Map<Integer, Integer> frequentMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            frequentMap.put(nums[i], frequentMap.getOrDefault(nums[i], 0) + 1);
        }
        //以下行要注意，数组长度应该是 nums.length+1，因为index=0（频次为0）的数字是不存在的，index=0位置永远为空，因此该多算一位
        List[] buckets = new List[nums.length+1];
        for (Map.Entry<Integer, Integer> entry : frequentMap.entrySet()) {
            int i = entry.getValue(); //freq
            if (buckets[i] == null) {
                buckets[i] = new ArrayList<>();
            }
            buckets[i].add(entry.getKey());
        }
        List<Integer> res = new ArrayList<>();
        for (int i = buckets.length - 1; i >= 0 && res.size() < k; i--) {
            if (buckets[i] != null) {
                res.addAll(buckets[i]);
            }
        }
        return res;
    }
}
```

### 参考资料：

[排序（下）：如何用快排思想在O(n)内查找第K大元素？](https://time.geekbang.org/column/article/41913) by 极客时间
[堆和堆排序：为什么说堆排序没有快速排序块？](https://time.geekbang.org/column/article/69913) by 极客时间