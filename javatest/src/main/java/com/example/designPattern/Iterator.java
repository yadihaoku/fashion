package com.example.designPattern;

import java.util.NoSuchElementException;

/**
 * Created by YanYadi on 2017/5/16.
 * 迭代器模式 行为模式的一种
 */
public class Iterator {

    public static void main(String[] args) {
        Arrays<String> arrays = new Arrays();
        arrays.add("1adsfa");
        arrays.add("2123123");
        arrays.add("3asdfasf");
        arrays.add("312312");
        arrays.add("4sdafsaf");
        arrays.add("4asdfsa");
        arrays.add("4asdf");
        arrays.add("BBBBB");
        arrays.add("ADdfdfd");
        arrays.add("AAAAAA");

        ArrayIterator iterator = arrays.getIterator();
        while (iterator.hasNext())
            System.out.println(iterator.next());
    }

    interface ArrayIterator<T> {
        boolean hasNext();

        T next();
    }

    interface ArrayContainer<T> {
        ArrayIterator<T> getIterator();

        void add(T t);
    }

    static class Arrays<T> implements ArrayContainer<T> {
        private Object[] objects = new Object[10];

        private int size;

        @Override public ArrayIterator<T> getIterator() {
            return new ArrIteratorImpl<T>();
        }


        public void add(T o) {
            int s = size;
            if(s == objects.length){
                throw new IndexOutOfBoundsException();
            }
            objects[s] = o;
            size = s + 1;
        }

        class ArrIteratorImpl<T> implements ArrayIterator {
            private int remaining = size;
            @Override public boolean hasNext() {
                return remaining != 0;
            }

            @Override public Object next() {
                if (remaining == 0) throw new NoSuchElementException();
                return objects[size - remaining--];
            }
        }
    }

}
