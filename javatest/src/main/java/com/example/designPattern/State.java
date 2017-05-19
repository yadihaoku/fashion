package com.example.designPattern;

/**
 * Created by YanYadi on 2017/5/18.
 *
 * 状态模式，是一种策略模式。
 *
 * example: 模拟 电视遥控器的开关功能
 * 处于 关机时，下次按下开关，是打开。
 * 处于 开机时，下次按下开关，是关闭。
 */
public class State {

    public static void main(String[] args) {
        IState onState = new TurnOn();
        Tv tv = new Tv(onState);
        tv.press();
        tv.press();
        tv.press();
        tv.press();
        tv.press();
    }

    interface IState{
        void doAction(Tv tv);
    }
    static class TurnOn implements IState{

        @Override public void doAction(Tv tv) {
            System.out.println("turn on----");
            tv.setCurrentState(new TurnOff());
        }
    }
    static class TurnOff implements IState{

        @Override public void doAction(Tv tv) {
            System.out.println("shutdown ----");
            tv.setCurrentState(new TurnOn());
        }
    }

    static class Tv{
        Tv(IState initState){
            currentState = initState;
        }
        private IState currentState;
        public void setCurrentState(IState state){
            currentState = state;
        }

        /**
         * 按下开关
         */
        public void press(){
            currentState.doAction(this);
        }
    }

}
