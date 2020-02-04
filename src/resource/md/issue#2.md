# [LeetCode] N数之和问题总结及1、15、18

### [LeetCode #1. 两数之和](https://leetcode-cn.com/problems/two-sum/)

题目描述

给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。

你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。

示例:
```
给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]
```

解法一：

暴力解法，两层嵌套循环遍历数组，外层指针 i 从 0 到 len-1，内层指针 j 从 i+1 到 len-1，如果 nums[i] + nums[j] = target，则返回结果

* 时间复杂度：O(n^2)
* 空间复杂度：O(1)

```java
/**
 * 38 ms, 18.16%
 * 37.5 MB, 38.65%
 */
class Solution1 {

    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        }
        return res;
    }
}
```

解法二：

遍历两次数组

1. 第一次遍历，先把数组中每个元素值 nums[i] 和下标 i 存入哈希表（为什么用哈希表？因为哈希表O(1)的查询效率，可以根据元素值快速定位该元素的下标）；
2. 第二次遍历，判断 target-nums[i] 差值是否存在于哈希表中
    * 如果存在，下标 i 和 target-nums[i]差值的下标 返回结果
    * 如果不存在，i++

需要注意的几点：

1. 判断 target-nums[i] 差值是否存在于哈希表中的同时，需要多加判断条件 hashmap.get(target - nums[i]) != i 以防取到同一个元素，例如 [3,2,4] target=6 的情况，如果不加此判断，结果是 [0,0]
2. nums[i] 的下标不能从哈希表中取 hashmap.get(nums[i]) (直接 i 就完事了)，如果都是从哈希表寻找，有可能会是同一个下标，因为如果数组中有重复元素的话，哈希表会覆盖最初的元素的下标，例如[3,3] target=6 的情况。

* 第一次遍历时间复杂度O(n)，第二次遍历时间复杂度也是O(n)，总体还是O(n)
* 空间复杂度：用到了哈希表存储数组元素，O(n)

```java
/**
 * 3 ms, 97.26%
 * 36.8 MB, 81.22%
 */
class Solution2 {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])
                //需要加 map.get(target - nums[i]) != i 这个判断以防取到同一个元素
                    && map.get(target - nums[i]) != i) {
                //res[0] = map.get(nums[i]); ==》这种取法不能AC！
                res[0] = i;
                res[1] = map.get(target - nums[i]);
                break;
            }
        }
        return res;
    }
}
```

解法三：

思路和解法二类似，也是利用哈希表用于快速定位元素的下标，但只遍历一次数组，直接判断 target-nums[i] 的差值是否存在于哈希表中

1. 如果存在直接返回2个下标 i 和 hashmap.get(target-nums[i])
2. 如果不存在将 nums[i] 存入哈希表

相较于解法二，只遍历了一次数组，同时也不需要注意解法二里面的注意点①

* 只遍历一次数组，时间复杂度：O(n)
* 空间复杂度：O(n)

```java
class Solution3 {

    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (map.containsKey(diff)) {
                res[0] = map.get(diff);
                res[1] = i;
                return res;
            }
            map.put(nums[i], i);
        }
        return res;
    }
}
```

---

### [LeetCode #15. 三数之和](https://leetcode-cn.com/problems/3sum/)

题目描述

给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。

注意：答案中不可以包含重复的三元组。

示例：
```
给定数组 nums = [-1, 0, 1, 2, -1, -4]，

满足要求的三元组集合为：
[
  [-1, 0, 1],
  [-1, -1, 2]
]
```

解法一：

此题不要求返回数组元素的下标，而是只需要数组元素值，因此可以先把数组从小到大排序再处理

最外层for循环，从左至右遍历排序后的数组 nums，固定指针 i 后，i 右边的剩余数组中最左端的指针为 l=i+1，最右端的指针为 r=len-1

例如：
```
[-4,-1,-1,0,1,2]
  ↑  ↑        ↑
  i  l        r
```

内层 while 循环，当 l < r 条件满足时，执行循环：

判断 sum = nums[i] + nums[l] + nums[r] 与 0 的关系

1. 如果 sum > 0，说明和偏大，让 nums[r] 变小，r 指针左移，r--
2. 如果 sum < 0，说明和偏小，让 nums[l] 变大，l 指针右移，l++
3. 如果 sum = 0，把结果记入结果集，同时 l、r 指针都分别往中间移，r--，l++

几个注意点：

1. for循环遍历 nums 只需要遍历下标 0 至 len-3 (i<len-2)
2. 如果 nums[i] > 0，可以直接跳出外层循环返回结果，因为 nums 是有序数组 r>l>i，必然 nums[r]>=nums[l]>=nums[i]>0，三者之和不可能等于0
3. 如果 nums[i] 和前一个元素值相同（nums[i]==nums[i-1]）这种情况要跳过，以免结果重复，但是注意 i=0 的情况，会 OutOfIndex 错误，所以需要加 i>0 的限制
4. 当 l 或 r 指针往中间移动时，需要跳过值相等的元素，以免重复判断或结果重复

以下情况避免重复判断（可以跳过也可以不跳过，因为对最终结果不会有影响）
```
[-4,-1,-1,0,1,2]
  ↑  ↑        ↑
  i  l        r   -4+(-1)+2<0，l 右移
       
[-4,-1,-1,0,1,2]
  ↑     ↑     ↑
  i   l(skip) r   l 元素值和上一个相同，跳过
     
[-4,-1,-1,0,1,2]
  ↑       ↑   ↑
  i       l   r
```

以下情况避免结果重复
```
[-1,-1,-1,0,2,2]
  ↑  ↑        ↑
  i  l        r   此时满足条件 -1+(-1)+2=0，l 和 r 都往中间移动一位

[-1,-1,-1,0,2,2]
  ↑     ↑   ↑
  i     l   r     l 和 r元素和上一个相同，必须跳过，否则结果就会重复
```

* 时间复杂度：O(n^2)，数组排序时间复杂度O(nlogn)，for循环遍历数组O(n)，while循环双指针遍历数组O(n)，总体时间复杂度 O(nlogn) + O(n) * O(n) = O(n^2)
* 空间复杂度：O(1)

```java
/**
 * 34 ms, 86.18%
 * 48.2 MB, 29.31%
 */
class Solution1 {

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) return res;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            //如果 nums[i]>0，因为nums[r]>=nums[l]>=nums[i]，相加不可能为0，跳出循环
            if (nums[i] > 0) break;
            //nums[i]==nums[i-1]避免重复，需跳过
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            //下标i之后的最左端指针l和最右端指针r
            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    //总和等于0，左指针右移，右指针左移，但是要跳过相同值
                    l++;
                    while (l < r && nums[l] == nums[l-1]) l++;
                    r--;
                    while (l < r && nums[r] == nums[r+1]) r--;
                }
                if (sum > 0) r--;
                if (sum < 0) l++;
            }
        }
        return res;
    }
}
```

---

### [LeetCode #18. 四数之和](https://leetcode-cn.com/problems/4sum)

题目描述

给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。

注意：

答案中不可以包含重复的四元组。

示例：
```
给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。

满足要求的四元组集合为：
[
  [-1,  0, 0, 1],
  [-2, -1, 1, 2],
  [-2,  0, 0, 2]
]
```

解法一：

参考3sum的思路
先对数组进行从小到大排序，使用4个指针分别指向4个元素，固定第一个指针 i，然后求三数之和等于 target-nums[i]
求3sum的方法是 固定第二个指针 j，然后移动 j 指针后面的最左指针 l 和最右指针 r，使四数之和 nums[i] + nums[j] + nums[l] + nums[r] = target

同3sum的解法类似，有以下几个优化点：

1. 第一个指针 i 固定后，因为数组是有序的，所以 4sum 的最小值，是 nums[i] 和之后连续的3个元素的和，即 min = nums[i] + nums[i+1] + nums[i+2] + nums[i+3]，
如果 min > target，那再往后遍历的话，4sum 只会越来越大，因此可以直接跳出循环
2. 同理，第一个指针 i 固定后，求 4sum 的最大值，是 nums[i] 和最后3个元素的和，即 max = nums[i] + nums[len-1] + nums[len-2] + nums[len-3]，
如果 max < target，说明 nums[i] 偏小了，因此可以直接进入下一个循环，i++
3. 第一个指针 i，第二个指针 j 固定后，4sum 的最小值是 min = nums[i] + nums[j] + nums[j+1] + nums[j+2]，如果 min > target，可以直接跳出循环
4. 同理，第一个指针 i，第二个指针 j 固定后，4sum 的最大值是 max = nums[i] + nums[j] + nums[len-1] + nums[len-2]，如果 max < target，可以直接进入下一个循环，j++
5. 指针 i 元素为了避免值重复，当 i > 0 && nums[i] == nums[i-1] 时需要跳过
6. 指针 j 元素为了避免值重复，当 j > i+1 && nums[j] == nums[j-1] 时需要跳过


```java
/**
 * 4 ms, 99.59%
 * 37.6 MB, 53.84%
 */
class Solution1 {

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 4) return res;
        Arrays.sort(nums);
        int len = nums.length;
        //加上这一行反而执行时间慢了
        //if (nums[len-1] + nums[len-2] + nums[len-3] + nums[len-4] < target) return res;
        int l, r, min, max;
        for (int i = 0; i < len - 3; i++) {
            if (i > 0 && nums[i] == nums[i-1]) continue;
            min = nums[i] + nums[i+1] + nums[i+2] + nums[i+3]; //①
            if (min > target) break;
            max = nums[i] + nums[len-1] + nums[len-2] + nums[len-3]; //②
            if (max < target) continue;
            for (int j = i+1; j < len - 2; j++) {
                if (j > i+1 && nums[j] == nums[j-1]) continue;
                l = j+1;
                r = len-1;
                min = nums[i] + nums[j] + nums[j+1] + nums[j+2]; //③
                if (min > target) break;
                max = nums[i] + nums[j] + nums[r] + nums[r-1]; //④
                if (max < target) continue;
                while (l < r) {
                    int sum = nums[i] + nums[j] + nums[l] + nums[r];
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        l++;
                        while (l < r && nums[l] == nums[l-1]) l++;
                        r--;
                        while (l < r && nums[r] == nums[r+1]) r--;
                    }
                    if (sum < target) l++;
                    if (sum > target) r--;
                }
            }
        }
        return res;
    }
}
```

* 时间复杂度：O(n^3)，在3sum的基础上外层有加了一层遍历，因此是 O(n)*O(n^2)=O(n^3)
* 空间复杂度：O(1)



















