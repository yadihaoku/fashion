package com.example.designPattern;

/**
 * Created by YanYadi on 2017/5/25.
 * 使用场景，在某一对象无法满足需求时。可以把该对象进行组合（对象适配）或继承（类适配）。
 * Android 中，Context 、ContextWrapper 都是典型的适配器模式.
 * 例子：
 * 有两台 android手机（一台micro usb、一台Type-c ）需要充电，但是只有一个苹果充电器和数据线。
 * 要给这两台手机充电，需要两个转接头。通过 对象适配和类适配来模拟这个转接头。
 */
public class Adapter {

    public static void main(String[] args) {
        IphoneChargeAdaptee appleCharger = new IphoneChargeAdaptee();
        MicroUsbAdapter microUsbAdapter = new MicroUsbAdapter(appleCharger);
        TypeCAdapter typeCAdapter = new TypeCAdapter();

        appleCharger.charge();
        microUsbAdapter.charge();
        typeCAdapter.charge();
    }
    /**
     * iphone 充电器，被适配对象
     */
    static class IphoneChargeAdaptee{
        public void charge(String device){
            System.out.println(device +" charged.");
        }

        /**
         * 默认只能 跟 apple 设备充电
         */
        private void charge(){
            charge("iphone,iPad,iPod");
        }
    }

    /**
     * 使用对象适配模式
     */
    static class MicroUsbAdapter{
        private IphoneChargeAdaptee adaptee;
        public MicroUsbAdapter(IphoneChargeAdaptee adaptee){
            this.adaptee = adaptee;
        }
        public void charge(){
           adaptee.charge("Android devices");
        }
    }

    static class TypeCAdapter extends IphoneChargeAdaptee{
        public void charge(){
            super.charge("Type C devices");
        }
    }



}
