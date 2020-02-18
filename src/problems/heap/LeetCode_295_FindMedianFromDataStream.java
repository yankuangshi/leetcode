package problems.heap;

import java.util.PriorityQueue;

/**
 * 295. 数据流的中位数
 * 难度：困难
 *
 * 题目描述
 *
 * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 * 例如，
 * [2,3,4] 的中位数是 3
 * [2,3] 的中位数是 (2 + 3) / 2 = 2.5
 *
 * 设计一个支持以下两种操作的数据结构：
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 * 示例：
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 *
 * 进阶:
 * 如果数据流中所有整数都在 0 到 100 范围内，你将如何优化你的算法？
 * 如果数据流中 99% 的整数都在 0 到 100 范围内，你将如何优化你的算法？
 *
 * @author kyan
 * @date 2020/2/18
 */
public class LeetCode_295_FindMedianFromDataStream {

    /**
     * 双堆
     * 维护2个堆（一个大顶堆一个小顶堆），大顶堆存储前半部分数据，小顶堆存储后半部分数据，且大顶堆数据大于小顶堆数据
     *
     * 例如：
     * 如果n是偶数，那么前n/2个数据存储在大顶堆中，后n/2个数据存储在小顶堆中；那么大顶堆的堆顶元素加上小顶堆的堆顶元素除以2就是中位数；
     * 如果n是奇数，前n/2+1个数据存储在大顶堆，后n/2个数据存储在小顶堆；那么大顶堆的堆顶元素就是中位数；
     *
     * 当添加一个新数据时，如果新元素大于等于大顶堆堆顶元素，则新数据加入小顶堆，否则加入大顶堆。
     *
     * 随着数据的加入和删除，大顶堆和小顶堆的个数会出现失衡，也就是不再符合先前约定的n/2和n/2、或者n/2+1和n/2，这种情况就需要移动2个堆的堆顶元素来再次实现平衡。比如：
     *
     * MaxHeap     MinHeap
     *    13      15
     *    /\      /\
     *   8  9    20 21
     *  /       /
     * 7       30
     *
     * //插入16，16>MaxHeap.peek()，加入小顶堆
     * MaxHeap     MinHeap
     *    13      15
     *    /\      /\
     *   8  9   16 21
     *  /       /\
     * 7       30 20
     *
     * //不再符合大顶堆5个和小顶堆4个的规格（总个数n=9），把小顶堆堆顶数据移动到大顶堆，中位数15
     * MaxHeap     MinHeap
     *   15      16
     *   /\      /\
     *  13  9   20 21
     *  /\      /
     * 7  8    30
     *
     * 85 ms, 90.96%
     * 70.2 MB, 5.10%
     */
    public static class MedianFinder {

        private PriorityQueue<Integer> maxHeap;
        private PriorityQueue<Integer> minHeap;
        private int count;

        /** initialize your data structure here. */
        public MedianFinder() {
            maxHeap = new PriorityQueue<>((a, b) -> (b - a));
            minHeap = new PriorityQueue<>((a, b) -> (a - b));
        }

//        public void addNum(int num) {
//            if (maxHeap.isEmpty() || num < maxHeap.peek()) {
//                maxHeap.add(num);
//            } else {
//                minHeap.add(num);
//            }
//            count++;
//            //维持 maxHeap 数量最多比 minHeap 数量多一个，或者相等
//            while (maxHeap.size() - minHeap.size() < 0 || maxHeap.size() - minHeap.size() > 1) {
//                if (maxHeap.size() - minHeap.size() < 0) {
//                    //从 minHeap 转移至 maxHeap
//                    maxHeap.add(minHeap.poll());
//                }
//                if (maxHeap.size() - minHeap.size() > 1) {
//                    //从 maxHeap 转移至 minHeap
//                    minHeap.add(maxHeap.poll());
//                }
//            }
//        }

        public void addNum(int num) {
            maxHeap.add(num);
            minHeap.add(maxHeap.poll());
            if (maxHeap.size() < minHeap.size()) {
                maxHeap.add(minHeap.poll());
            }
            count++;
        }

        public double findMedian() {
            if (maxHeap.isEmpty() && minHeap.isEmpty()) return 0;
            if (count % 2 == 0) {
                return maxHeap.peek() / 2.0d + minHeap.peek() / 2.0d;
            } else {
                return maxHeap.peek();
            }
        }
    }

    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(1);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(6);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(5);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(3);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(2);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(4);
        System.out.println(medianFinder.findMedian());
    }

}
