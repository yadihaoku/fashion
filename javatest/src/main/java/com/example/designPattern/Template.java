package com.example.designPattern;

/**
 * Created by YanYadi on 2017/5/19.
 * 行为方式的一种，把一件按流程完成的工作，定义成抽象方法。
 * 执行该工作的顺序不可修改。
 * example: 比如人活着的一天，
 * 1. 起床
 * 2. 吃早餐
 * 3. 上班
 * 4. 下班
 * 顺序是固定的，每个步骤每个人都是不同的。可以让不同的子类去实现
 */
public class Template {
    public static void main(String[] args) {
        OneDay programmer = new ProgrammerLive();
        OneDay teacher = new TeacherLive();
        programmer.wakeUp();
        System.out.println("-==-=-=-=-=-=--=");
        teacher.wakeUp();
    }
    /**
     * 定义一天
     */
    static abstract class OneDay {
        // 起床
        abstract void getUp();

        // 早餐
        abstract void breakFast();

        // 上班
        abstract void goToWork();

        //下班
        abstract void offWork();

        /**
         * 每天的开始，醒来。开始一天的生活
         */
        public final void wakeUp() {
            getUp();
            breakFast();
            goToWork();
            offWork();
        }
    }

    /**
     * 程序员的一天
     */
    static class ProgrammerLive extends OneDay{

        @Override void getUp() {
            System.out.println("看手机、穿T恤，牛仔裤。");
        }

        @Override void breakFast() {
            System.out.println("鸡蛋灌饼");
        }

        @Override void goToWork() {
            System.out.println(" 看微博，刷csdn, 看新闻，浏览github ");
        }

        @Override void offWork() {
            System.out.println("要下班了，开始 coding");
        }
    }
    /**
     * 老师的一天
     */
    static class TeacherLive extends OneDay{

        @Override void getUp() {
            System.out.println("穿衣服");
        }

        @Override void breakFast() {
            System.out.println("牛奶、面包");
        }

        @Override void goToWork() {
            System.out.println("讲课");
        }

        @Override void offWork() {
            System.out.println("布置作业，下班");
        }
    }
}
