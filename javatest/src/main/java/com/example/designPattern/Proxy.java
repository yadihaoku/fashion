package com.example.designPattern;

/**
 * Created by YanYadi on 2017/5/9.
 */
public class Proxy {

    public static void main(String[] args) {
        ImageProxy proxy = new ImageProxy(new RectangleImage());
        proxy.display();

        ImageProxy gifProxy = new ImageProxy(new GifImage("anim.gif"));
        gifProxy.display();
    }

    interface Image {
        void display();
    }

    static class RectangleImage implements Image {

        @Override public void display() {
            System.out.println("display: Rectangle Image");
        }
    }

    static class GifImage implements Image {

        String gifUrl;

        GifImage(String gifUrl) {
            this.gifUrl = gifUrl;
            decodeGifUrl();
        }

        private void decodeGifUrl() {
            System.out.println("decodeGifUrl: decode gif stream.");
        }

        @Override public void display() {
            System.out.println("display GifImage.");
        }
    }

    static class ImageProxy implements Image {
        Image realImage;

        ImageProxy(Image invoker) {
            realImage = invoker;
        }

        @Override public void display() {
            realImage.display();
        }
    }
}
