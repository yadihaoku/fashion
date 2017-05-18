package com.example.designPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YanYadi on 2017/5/17.
 * 观察者模式，当对象之间存在一对多关系。最为合适。
 * 某个对象被修改，将自动通知。
 * 行为模式的一种
 */
public class Observer {

    public static void main(String[] args) {
        Subject subject = new Subject();

        subject.registerObserver(new Observer1(subject));
        subject.registerObserver(new Observer2(subject));

        subject.state = 100;
        subject.notifyStateChange();


        subject.state = 99;
        subject.notifyStateChange();
    }

    static abstract class AbstractObserver {
        protected Subject subject;
        AbstractObserver(Subject subject){
            this.subject = subject;
        }
        abstract void onStateChange();
    }

    static class Subject {
        List<AbstractObserver> observers;
        private int state;

        Subject() {
            observers = new ArrayList<>();
        }

        public void registerObserver(AbstractObserver observer) {
            observers.add(observer);
        }

        public void unregisterObserver(AbstractObserver observer) {
            observers.remove(observer);
        }

        public void notifyStateChange() {
            for (AbstractObserver observer : observers) {
                observer.onStateChange();
            }
        }
    }

    /**
     * 定义观察者 1
     */
    static class Observer1 extends AbstractObserver{

        Observer1(Subject subject) {
            super(subject);
        }

        @Override void onStateChange() {
            System.out.println("Observer1  state change----current state : " + subject.state);
        }
    }
    /**
     * 定义观察者 1
     */
    static class Observer2 extends AbstractObserver{

        Observer2(Subject subject) {
            super(subject);
        }

        @Override void onStateChange() {
            System.out.println("Observer2====  state change current state : " + subject.state);
        }
    }
}
