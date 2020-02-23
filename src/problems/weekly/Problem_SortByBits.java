package problems.weekly;

import java.util.*;

/**
 * BiWeekly Contest 20th
 * 5323. 根据数字二进制下 1 的数目排序 https://leetcode-cn.com/problems/sort-integers-by-the-number-of-1-bits/
 * 难度：简单
 *
 * 题目描述
 * 给你一个整数数组 arr 。请你将数组中的元素按照其二进制表示中数字 1 的数目升序排序。
 * 如果存在多个数字二进制中 1 的数目相同，则必须将它们按照数值大小升序排列。
 * 请你返回排序后的数组。
 *
 * 示例 1：
 * 输入：arr = [0,1,2,3,4,5,6,7,8]
 * 输出：[0,1,2,4,8,3,5,6,7]
 * 解释：[0] 是唯一一个有 0 个 1 的数。
 * [1,2,4,8] 都有 1 个 1 。
 * [3,5,6] 有 2 个 1 。
 * [7] 有 3 个 1 。
 * 按照 1 的个数排序得到的结果数组为 [0,1,2,4,8,3,5,6,7]
 *
 * 示例 2：
 * 输入：arr = [1024,512,256,128,64,32,16,8,4,2,1]
 * 输出：[1,2,4,8,16,32,64,128,256,512,1024]
 * 解释：数组中所有整数二进制下都只有 1 个 1 ，所以你需要按照数值大小将它们排序。
 *
 * 示例 3：
 * 输入：arr = [10000,10000]
 * 输出：[10000,10000]
 *
 * 示例 4：
 * 输入：arr = [2,3,5,7,11,13,17,19]
 * 输出：[2,3,5,17,7,11,13,19]
 *
 * 示例 5：
 * 输入：arr = [10,100,1000,10000]
 * 输出：[10,100,10000,1000]
 *
 * @author kyan
 * @date 2020/2/22
 */
public class Problem_SortByBits {

    /**
     * 冒泡
     * 298 ms
     */
    public static class Solution1 {

        public int[] sortByBits(int[] arr) {
            Map<Integer, Integer> bitMap = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                bitMap.put(arr[i], countBits(arr[i]));
            }
            for (int i = 0; i < arr.length; i++) {
                boolean flag = false;
                for (int j = 0; j < arr.length - i - 1; j++) {
                    if (bitMap.get(arr[j]) < bitMap.get(arr[j+1])) continue;
                    if (bitMap.get(arr[j]) > bitMap.get(arr[j+1])) {
                        swap(arr, j, j+1);
                        flag = true;
                    } else if (arr[j] > arr[j+1]) {
                        swap(arr, j, j+1);
                        flag = true;
                    }
                }
                if (!flag) break;
            }
            return arr;
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        /**
         * 计算数字中二进制位1的个数
         * @param num
         * @return
         */
        private Integer countBits(int num) {
            int bits = 0;
            while (num > 0) {
                bits += num & 1;
                num = num >> 1;
            }
            return bits;
        }

    }

    /**
     * 自定义比较器
     * 24 ms
     */
    public static class Solution2 {
        public int[] sortByBits(int[] arr) {
            Map<Integer, Integer> bitMap = new HashMap<>();
            Integer[] sortArr = new Integer[arr.length];
            for (int i = 0; i < arr.length; i++) {
                sortArr[i] = arr[i];
                bitMap.put(arr[i], countBits(arr[i]));
            }
            Arrays.sort(sortArr, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    //如果o1和o2的bit个数相同，则比较数值大小；如果bit个数不同，则比较bit个数
                    return (bitMap.get(o1).equals(bitMap.get(o2))) ? o1 - o2 : bitMap.get(o1) - bitMap.get(o2);
                }
            });
            int[] res = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                res[i] = sortArr[i];
            }
            return res;
        }

        private Integer countBits(int num) {
            int bits = 0;
            while (num > 0) {
                bits += num & 1;
                num = num >> 1;
            }
            return bits;
        }
    }

    /**
     * 自定义比较器 + JDK自带的计算bit个数 Integer.bitCount
     * 14 ms
     */
    public static class Solution3 {
        public int[] sortByBits(int[] arr) {
            Integer[] sortArr = new Integer[arr.length];
            for (int i = 0; i < arr.length; i++) {
                sortArr[i] = arr[i];
            }
            Arrays.sort(sortArr, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return Integer.bitCount(o1) == Integer.bitCount(o2) ? o1 - o2 : Integer.bitCount(o1) - Integer.bitCount(o2);
                }
            });
            int[] res = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                res[i] = sortArr[i];
            }
            return res;
        }
    }


    public static void main(String[] args) {
            int[] arr = {10000,10000};
//            int[] arr = {1024,512,256,128,64,32,16,8,4,2,1}; //[1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024]
//            int[] arr = {0,1,2,3,4,5,6,7,8}; //[0, 1, 2, 4, 8, 3, 5, 6, 7]
//            int[] arr = {2,3,5,7,11,13,17,19}; //[2, 3, 5, 17, 7, 11, 13, 19]
//        int[] arr = {10,100,1000,10000}; //[10, 100, 10000, 1000]
        System.out.println(Arrays.toString(new Solution1().sortByBits(arr)));
        System.out.println(Arrays.toString(new Solution2().sortByBits(arr)));
        System.out.println(Arrays.toString(new Solution3().sortByBits(arr)));
    }
}
