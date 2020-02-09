package problems.array;

/**
 * 11. 盛最多水的容器
 * 难度：中等
 *
 * 题目描述
 *
 * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 说明：你不能倾斜容器，且 n 的值至少为 2。
 *
 * 图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 *
 * 示例:
 *
 * 输入: [1,8,6,2,5,4,8,3,7]
 * 输出: 49
 *
 * @author kyan
 * @date 2020/2/9
 */
public class _11_ContainerWithMostWater {

    /**
     * 解法一
     * 利用左右双指针缩小范围的技巧
     * 计算盛水的容积公式为 Math.min(height[i], height[j]) * (j - i)
     * 我们应该取 height[i] 和 height[j] 中的较短的一根的下标往中间移动，因为下标往中间靠会导致宽度 (j-i) 变小，宽度减小的情况下，希望高度可以变高来弥补。
     * 反证法看一下为什么不应该取 height[i] 和 height[j] 中的较长一根的下标往中间移动
     * 假设 height[i] < height[j]，那么 area = Math.min(height[i], height[j]) * (j - i) = height[i] * (j - i)
     * 下一步我们让较长的height[j]的下标 j 左移
     * 1、如果 height[j-1] > height[j]，那么容积 area = Math.min(height[i], height[j-1]) * (j-i-1) = height[i] * (j-i-1) < height[i] * (j-i)（因为 height[i]<height[j]<height[j-]))
     * 2、如果 height[j-1] < height[j]，那么容积 area = Math.min(height[i], height[j-1]) * (j-i-1)
     *  如果 height[i] < height[j-1]，那么容积还是 height[i] * (j-i-1) < height[i] * (j-i)
     *  如果 height[i] > height[j-1]，那么容积 height[j-1] * (j-i-1) < height[i] * (j-i-1) < height[i] * (j-i)
     * 因此可以得出结论，如果让较长一根的下标往中间移动，只会让容积越变越小，这样就失去了意义！
     *
     * 执行用时：3 ms, 91.18%
     * 内存消耗：40.3 MB, 8.77%
     */
    public static class Solution1 {

        public int maxArea(int[] height) {
            int max = 0, i = 0, j = height.length - 1;
            while (i < j) {
                max = Math.max(max, Math.min(height[i], height[j]) * (j - i));
                if (height[i] < height[j]) i++;
                else j--;
            }
            return max;
        }
    }

    public static void main(String[] args) {
        int[] height = {1,1,1,8,7,1,1,1,1};
        System.out.println(new Solution1().maxArea(height));
    }
}
