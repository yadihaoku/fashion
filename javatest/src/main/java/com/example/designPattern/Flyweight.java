package com.example.designPattern;

import java.util.HashMap;

/**
 * Created by YanYadi on 2017/5/4.
 * 享元模式  主要目的是减少对象的创建数量
 * <p>
 * 当一个应用中使用了大量的对象，这些对象造成了很大的存储开销，
 * 而对象的大部分状态或参数都是相同（内部状态）的时候，可以考虑
 * 使用享元模式，使用享元模式可以是这些对象引用都共享相同的实例，
 * 降低存储开销，而对象之间的不同的状态参数(外部状态)则使用外部参数传入来实现。
 * <p>
 * ---
 * <p>
 * 例子是，我们要画一些形状（方块 和 圆形）。形状的颜色、大小不同。
 * 所以，我们可以把相同的行为进行共享（绘制方式）。把外部状态以参数形式传入。
 */
public class Flyweight {
    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            Shape shape = ShapeFactory.getShape(getRandomShapeType());
            shape.setColor(getRandomColor());
            shape.setSize(i);
            shape.draw();
        }

        System.out.println("---- shape instance count = " + ShapeFactory.mShapes.size());

    }

    private static String getRandomShapeType() {
        String shape[] = {"circle", "square"};
        return shape[(int) (Math.random() * shape.length)];
    }

    private static String getRandomColor() {
        String colors[] = {"red", "green", "blue", "yellow", "pink"};
        return colors[(int) (Math.random() * colors.length)];
    }

    interface Shape {
        void draw();

        void setSize(int size);

        void setColor(String color);
    }

    static abstract class AbstractShape implements Shape {

        protected int size;
        protected String color;

        @Override public void setSize(int size) {
            this.size = size;
        }

        @Override public void setColor(String color) {
            this.color = color;
        }
    }

    static class Circle extends AbstractShape {
        @Override public void draw() {
            System.out.println(String.format("()draw a colour = %s,  radius = %d circle.", color, size));
        }
    }

    static class Square extends AbstractShape {
        @Override public void draw() {
            System.out.println(String.format("[]draw a colour = %s,  radius = %d square.", color, size));
        }
    }

    static class ShapeFactory {
        static HashMap<String, Shape> mShapes = new HashMap<>();

        public static Shape getShape(String type) {
            if (mShapes.containsKey(type)) return mShapes.get(type);
            Shape shape = null;
            if (type.equalsIgnoreCase("circle"))
                shape = new Circle();
            else if (type.equalsIgnoreCase("square"))
                shape = new Square();
            else
                throw new RuntimeException("unknown type");
            mShapes.put(type, shape);
            return shape;
        }
    }
}
