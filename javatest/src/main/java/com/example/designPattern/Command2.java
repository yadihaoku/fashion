package com.example.designPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YanYadi on 2017/5/15.
 * 定义一个动作演员，可以根据剧本需要完成一系列动作。
 */
public class Command2 {
    public static void main(String[] args) {
        //定义 演员 成龙
        Actor jackChen = new Actor("Jack Chen");
        //演义 演员 李连杰
        Actor JetLi = new Actor("Jet Li");

        //定义导演
        Director director = new Director();
        //跳动作
        ActionImpl jump = new JumpAction();
        jump.setActor(jackChen);
        //跑动作
        ActionImpl run = new RunAction();
        run.setActor(JetLi);
        //笑动作
        ActionImpl laugh = new LaughAcion();
        laugh.setActor(jackChen);

        //编排动作
        director.addAction(jump);
        director.addAction(laugh);
        director.addAction(run);

        //展示已经编排好的动作
        director.play();

    }

    /**
     * 定义导演类，负责编排所有的动作
     */
    static class Director{
        private List<Action> actions = new ArrayList<>();

        /**
         * 添加一个动作
         * @param action
         */
        public void addAction(Action action){
            actions.add(action);
        }

        /**
         * 执行已经编排好的动作
         */
        public void play(){
            for(Action action : actions)
                action.execute();

            actions.clear();
        }
    }



    /**
     * 定义动作接口
     */
    interface Action {
        void execute();
    }

    static abstract class ActionImpl implements Action {
        protected Actor mActor;

        public void setActor(Actor actor) {
            this.mActor = actor;
        }
    }

    /**
     * 定义具体动作- 》 跳
     */
    static class JumpAction extends ActionImpl {
        @Override public void execute() {
            mActor.jump();
        }
    }

    /**
     * 定义 跑 动作
     */
    static class RunAction extends ActionImpl{
        @Override public void execute() {
            mActor.run();
        }
    }

    /**
     * 定义 笑 动作
     */
    static class LaughAcion extends ActionImpl{

        @Override public void execute() {
           mActor.laugh();
        }
    }

    /**
     * 演员类， 拥有 jump \ laugh \ run 三个动作
     */
    static class Actor {
        String name;

        Actor(String n) {
            name = n;
        }

        public void jump() {
            System.out.println(name + " do action -> jump !");
        }

        public void run() {
            System.out.println(name + " do action -> run !");
        }

        public void laugh() {
            System.out.println(name + " do action -> laugh !");
        }
    }
}
