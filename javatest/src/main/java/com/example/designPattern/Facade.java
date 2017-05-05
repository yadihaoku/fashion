package com.example.designPattern;

/**
 * Created by YanYadi on 2017/5/4.
 * 外观模式 是一种结构模式，主要是为了解耦
 * 对外隐藏具体实现，通过一个管理类，把多个具体实现。统一进行封装。
 */
public class Facade {

    /**
     * 定义电子元件接口
     * 每个元件 都有 启动 和 停止接口
     */
    interface Unit{
        void start();
        void shutdown();
    }

    /**
     * 创建 cpu
     */
    static class Cpu implements Unit{

        @Override public void start() {
            System.out.println("cpu start");
        }

        @Override public void shutdown() {
            System.out.println("cpu shutdown");
        }
    }

    /**
     * 创建内存条
     */
    static class Memory implements Unit{

        @Override public void start() {
            System.out.println("memory start");
        }

        @Override public void shutdown() {
            System.out.println("memory stop");
        }
    }

    /**
     * 创建 硬盘
     */
    static class Disk implements Unit{

        @Override public void start() {
            System.out.println("disk start");
        }

        @Override public void shutdown() {
            System.out.println("shutdown: Disk");
        }
    }

    static class Computer{
        private Unit cpu;
        private Unit memory;
        private Unit disk;

        Computer(){
            cpu = new Cpu();
            memory = new Memory();
            disk = new Disk();
        }
        public void turnOn(){
            cpu.start();
            disk.start();
            memory.start();
        }
        public void turnOff(){
            cpu.shutdown();
            disk.shutdown();
            memory.shutdown();
        }
    }
    public static void main(String[] args) {

        Computer computer = new Computer();
        // 开机
        computer.turnOn();

        System.out.println("main: ========= shutdown");
        // 关机
        computer.turnOff();
    }
}
