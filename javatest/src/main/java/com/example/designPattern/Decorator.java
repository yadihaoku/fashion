package com.example.designPattern;

/**
 * Created by YanYadi on 2017/5/4.
 *
 *  装饰器模式  主要功能，扩展原有功能。
 */
public class Decorator {

    interface Shape{
        void draw();
    }

    static class Circle implements Shape{

        @Override public void draw() {
            System.out.println("draw circle");
        }
    }
    static class Rectangle implements Shape{

        @Override public void draw() {
            System.out.println("draw rectangle");
        }
    }
    static class Square implements Shape{

        @Override public void draw() {
            System.out.println("draw square");
        }
    }
    static abstract class AbstractDecorator implements Shape{
        protected Shape mShape;
        AbstractDecorator(Shape shape){
            this.mShape = shape;
        }
    }
    static class RedDecoratorImpl extends AbstractDecorator{

        RedDecoratorImpl(Shape shape){
            super(shape);
        }
        @Override public void draw() {
            mShape.draw();
            System.out.println("--draw red border--");
        }
    }

    public static void main(String[] args) {
        /**
         * 创建装饰器，装饰 Square 对象
         */
        RedDecoratorImpl redSquare = new RedDecoratorImpl(new Square());
        redSquare.draw();
        /**
         * 装饰 Rectangle 装饰器
         */
        RedDecoratorImpl redRect = new RedDecoratorImpl(new Rectangle());
        redRect.draw();

        /**
         * 绘制两层边框的 Square
         */
        RedDecoratorImpl doubleRedBorder = new RedDecoratorImpl(redSquare);
        doubleRedBorder.draw();
    }
}
