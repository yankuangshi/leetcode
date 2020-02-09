package problems.heap;

import java.util.*;

/**
 * 347. 前 K 个高频元素
 * 难度：中等
 *
 * 题目描述
 *
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 *
 * 示例 1:
 *
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 * 示例 2:
 *
 * 输入: nums = [1], k = 1
 * 输出: [1]
 * 说明：
 *
 * 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
 * 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
 *
 * @author kyan
 * @date 2020/2/9
 */
public class _347_TopKFrequent {

    /**
     * 解法一
     * 先遍历一次数组，把数字与出现的频次映射关系存进hashmap
     * 然后利用大顶堆（使用优先级队列实现），把每个 hashMap 中的 entry 放入堆内（通过比较 entry 的 value 大小，entry 的 value 存的是频次）
     * 最后循环K次从堆顶取出 entry，把结果方式返回列表
     *
     * 执行用时：22 ms, 62.9%
     * 内存消耗：42.7 MB, 5.02%
     */
    public static class Solution1 {

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

    /**
     * 解法二
     * 解法一的基础上进行优化
     * 1. 堆内不存放 Map 的 Entry 节点，而是直接存放数字，但是比较的是数字的频次大小
     * 2. 构建大小为K的小顶堆，超过K后，需要和堆顶值的频次比较大小，如果比堆顶值的频次大，则移除堆顶值，加入新的值
     * 最后从小顶堆取出所有结果，即为出现频率最高的K个数字
     *
     * 执行用时：22 ms, 62.9%
     * 内存消耗：40.5 MB, 49.29%
     */
    public static class Solution2 {

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

    /**
     * 解法三
     * 遍历数组统计频次存入hashmap后
     * 创建一个大小为 nums.length + 1 的新数组用于存储数字，index 是该数字出现的频次
     * 例如 nums = [1,1,1,2,2,3,3,3,3]
     * 统计完得到 hashmap=[(1,3),(2,2),(3,4)]
     * 创建新数组 C[nums.length+1]
     *          ---------------------
     * 数组C[10] | | | | | | | | | | |
     *          ---------------------
     * 下标       0 1 2 3 4 5 6 7 8 9
     *
     * 然后已下标作为出现频次，填入新的数组 C
     *
     *          ---------------------
     * 数组C[10] | | |2|1|3| | | | | |
     *          ---------------------
     * 下标       0 1 2 3 4 5 6 7 8 9
     *
     * 要求出现频次TopK的数字，只需要从数组C从后往前遍历（排除没值的index）K 次
     *
     * 这里需要注意2点：
     * 1、为什么新数组 C 的长度是 nums.length + 1 ？
     * 假设最坏的情况就是 nums 数组中所有数字的出现频次都是1次，那么新数组 C 中至少要能够存放 nums.length 个数字，再加上 index=0 的位置
     * 意味着频次出现0的数字，这个数字是不存在的，因此 index=0 的位置将被空出来，所以新数组 C 的长度应该是 nums.length+1，多出的 1 就是给
     * C[0]的。
     * 2、如果频次相同怎么办？
     * 对于频次相同的数字，只能把它们放到同一个数组 bucket 中（感觉是不是很像HashMap发生哈希冲突时内部数组？），同一个 bucket 要能够存放2个不同数字，
     * 那么可以使用 List，所以新数组 C 是存放 List 数组 List[] C = new List[len+1]
     *
     * 时间复杂度O(N)
     * 执行用时：16 ms, 96.05%
     * 内存消耗：40.8 MB, 33.76%
     */
    public static class Solution3 {

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

    public static void main(String[] args) {
//        int[] nums = {1,1,1,2,2,3,3,3,3};
        int[] nums = {1};
        System.out.println(new Solution1().topKFrequent(nums, 1));
        System.out.println(new Solution2().topKFrequent(nums, 1));
        System.out.println(new Solution3().topKFrequent(nums, 1));
    }
}
