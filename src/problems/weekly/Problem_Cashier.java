package problems.weekly;

import java.util.HashMap;
import java.util.Map;

/**
 * BiWeekly Contest 20th
 * 5324. 每隔 n 个顾客打折 https://leetcode-cn.com/problems/apply-discount-every-n-orders/
 * 难度：中等
 *
 * 题目描述
 *
 * 超市里正在举行打折活动，每隔 n 个顾客会得到 discount 的折扣。
 *
 * 超市里有一些商品，第 i 种商品为 products[i] 且每件单品的价格为 prices[i] 。
 *
 * 结账系统会统计顾客的数目，每隔 n 个顾客结账时，该顾客的账单都会打折，折扣为 discount （也就是如果原本账单为 x ，那么实际金额会变成 x - (discount * x) / 100 ），然后系统会重新开始计数。
 *
 * 顾客会购买一些商品， product[i] 是顾客购买的第 i 种商品， amount[i] 是对应的购买该种商品的数目。
 *
 * 请你实现 Cashier 类：
 *
 * Cashier(int n, int discount, int[] products, int[] prices) 初始化实例对象，参数分别为打折频率 n ，折扣大小 discount ，超市里的商品列表 products 和它们的价格 prices 。
 * double getBill(int[] product, int[] amount) 返回账单的实际金额（如果有打折，请返回打折后的结果）。返回结果与标准答案误差在 10^-5 以内都视为正确结果。
 *  
 * 示例 1：
 * 输入
 * ["Cashier","getBill","getBill","getBill","getBill","getBill","getBill","getBill"]
 * [[3,50,[1,2,3,4,5,6,7],[100,200,300,400,300,200,100]],[[1,2],[1,2]],[[3,7],[10,10]],[[1,2,3,4,5,6,7],[1,1,1,1,1,1,1]],[[4],[10]],[[7,3],[10,10]],[[7,5,3,1,6,4,2],[10,10,10,9,9,9,7]],[[2,3,5],[5,3,2]]]
 * 输出
 * [null,500.0,4000.0,800.0,4000.0,4000.0,7350.0,2500.0]
 * 解释
 * Cashier cashier = new Cashier(3,50,[1,2,3,4,5,6,7],[100,200,300,400,300,200,100]);
 * cashier.getBill([1,2],[1,2]);                        // 返回 500.0, 账单金额为 = 1 * 100 + 2 * 200 = 500.
 * cashier.getBill([3,7],[10,10]);                      // 返回 4000.0
 * cashier.getBill([1,2,3,4,5,6,7],[1,1,1,1,1,1,1]);    // 返回 800.0 ，账单原本为 1600.0 ，但由于该顾客是第三位顾客，他将得到 50% 的折扣，所以实际金额为 1600 - 1600 * (50 / 100) = 800 。
 * cashier.getBill([4],[10]);                           // 返回 4000.0
 * cashier.getBill([7,3],[10,10]);                      // 返回 4000.0
 * cashier.getBill([7,5,3,1,6,4,2],[10,10,10,9,9,9,7]); // 返回 7350.0 ，账单原本为 14700.0 ，但由于系统计数再次达到三，该顾客将得到 50% 的折扣，实际金额为 7350.0 。
 * cashier.getBill([2,3,5],[5,3,2]);                    // 返回 2500.0
 *
 * @author kyan
 * @date 2020/2/22
 */
public class Problem_Cashier {

    /**
     * 142ms
     */
    public static class Cashier {

        private int count, n, discout;
        private Map<Integer, Integer> productMap;
        private int[] products, prices;

        public Cashier(int n, int discount, int[] products, int[] prices) {
            this.products = products;
            this.prices = prices;
            this.n = n;
            this.discout = discount;
            productMap = new HashMap<>(products.length);
            for (int i = 0; i < products.length; i++) {
                productMap.put(products[i], i);
            }
        }

        public double getBill(int[] product, int[] amount) {
            double total = 0;
            for (int i = 0; i < product.length; i++) {
                total += prices[productMap.get(product[i])] * amount[i];
            }
            count++;
            if (count % n == 0) {
                total = total - (discout * total) * 0.01;
            }
            return total;
        }
    }

    public static void main(String[] args) {
        int[] products = {1,2,3,4,5,6,7};
        int[] prices = {100,200,300,400,300,200,100};
        Cashier cashier = new Cashier(3,50, products, prices);
        int[] n1 = {1,2}, n2 = {1,2};
        System.out.println(cashier.getBill(n1, n2));                        // 返回 500.0, 账单金额为 = 1 * 100 + 2 * 200 = 500.
        int[] n3 = {3,7}, n4 = {10,10};
        System.out.println(cashier.getBill(n3, n4));                  // 返回 4000.0
        int[] n5 = {1,2,3,4,5,6,7}, n6 = {1,1,1,1,1,1,1};
        System.out.println(cashier.getBill(n5, n6));    // 返回 800.0 ，账单原本为 1600.0 ，但由于该顾客是第三位顾客，他将得到 50% 的折扣，所以实际金额为 1600 - 1600 * (50 / 100) = 800 。
        int[] n7 = {4}, n8 = {10};
        System.out.println(cashier.getBill(n7, n8));                        // 返回 4000.0
        int[] n9 = {7,3}, n10 = {10,10};
        System.out.println(cashier.getBill(n9, n10));                      // 返回 4000.0
        int[] n11 = {7,5,3,1,6,4,2}, n12 = {10,10,10,9,9,9,7};
        System.out.println(cashier.getBill(n11, n12));// 返回 7350.0 ，账单原本为 14700.0 ，但由于系统计数再次达到三，该顾客将得到 50% 的折扣，实际金额为 7350.0 。
        int[] n13 = {2,3,5}, n14 = {5,3,2};
        System.out.println(cashier.getBill(n13, n14));                    // 返回 2500.0
    }
}
