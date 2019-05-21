package com.example.designPattern;

/**
 * Created by yanyadi on 2017/5/1.
 */

public class Bridge {

    /**
     * 定义绘制接口
     */
    interface DrawApi{
        void drawCircle(int radius);
    }
    /**
     * 红色圆圈，实现类1
     */
    static   class RedCircle implements DrawApi{


        @Override
        public void drawCircle(int radius) {
            System.out.println("draw read circle, radius = "+ radius);
        }
    }

    /**
     * 绿色圆圈，实现类2
     */
    static class GreenCircle implements DrawApi{

        @Override
        public void drawCircle(int radius) {
            System.out.println("draw green circle, radius = " + radius);
        }
    }

    static class Shape{
        DrawApi drawApi;
        int radius;

        Shape(DrawApi api, int radius){
            this.drawApi = api;
            this.radius = radius;
        }

        void draw(){
            drawApi.drawCircle(radius);
        }
    }

    public static void main(String[] args) {
        DrawApi greenCircle = new GreenCircle();
        Shape greenShape = new Shape(greenCircle, 10);

        DrawApi redCircle = new RedCircle();
        Shape redShape = new Shape(redCircle, 20);


        greenShape.draw();
        redShape.draw();
    }

}
