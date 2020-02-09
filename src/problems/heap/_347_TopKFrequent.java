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
