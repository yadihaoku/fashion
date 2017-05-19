package com.example.designPattern;

/**
 * Created by YanYadi on 2017/5/18.
 * 策略模式，行为模式的一种
 * example: 模拟超市对会员不同的计价方式
 * 普通会员，无折扣
 * VIP会员，9折
 * 钻石会员，8折
 */
public class Strategy {
    public static void main(String[] args) {
        Price price = new Price(new VipMember());
        System.out.println(" calc last total.");
        System.out.println(price.quote(100));
    }
    /**
     * 定义打折模式
     */
    interface DiscountPattern {
        double calcTotal(double total);
    }

    /**
     * 定义普通会员
     */
    static class NormalMember implements DiscountPattern {

        @Override public double calcTotal(double total) {
            System.out.println(" normal member no discount.");
            return total;
        }
    }

    /**
     * VIP会员
     */
    static class VipMember implements DiscountPattern {

        @Override public double calcTotal(double total) {
            return total * 0.9;
        }
    }

    /**
     * 定义 钻石会员
     */
    static class DiamondMember implements DiscountPattern {

        @Override public double calcTotal(double total) {
            return total * 0.8;
        }
    }

    static class Price{
        private DiscountPattern discountPattern;

        public Price(DiscountPattern discountPattern) {
            this.discountPattern = discountPattern;
        }

        /**
         * 计算最终价格
         * @param total
         * @return
         */
        public double quote(double total){
            return discountPattern.calcTotal(total);
        }
    }
}
