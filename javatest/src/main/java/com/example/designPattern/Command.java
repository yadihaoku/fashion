package com.example.designPattern;

import java.util.concurrent.TimeUnit;

/**
 * Created by YanYadi on 2017/5/15.
 * 命令模式，是一种行为模式。用于完成某一动作，并隐藏细节。并把结果通知给接收者。
 * <p>
 * exp: 模拟一网络请求库
 */
public class Command {

    public static void main(String[] args) {
        HttpClient client = new HttpClient();

        Cmd request = new Request(new Callback() {
            @Override public <T> void onReceive(T t) {
                System.out.println(t.toString());
            }
        });

        client.request(request);


        Cmd reqBaidu = new Request(new Callback() {
            @Override public <T> void onReceive(T t) {
                System.out.println("request baidu");
                System.out.println(t.toString());
            }
        });

        client.request(reqBaidu);
        reqBaidu.cancel();
    }

    /**
     * 定义 命令接口
     */
    interface Cmd {
        int STATUS_INIT = 0;
        int STATUS_REQUESTING = 1;
        int STATUS_COMPLETE = 2;

        void execute();

        void cancel();
    }

    /**
     * 定义 消息接受者
     */
    interface Callback {
        <T> void onReceive(T t);
    }


    static class Request implements Cmd {
        private Callback callback;

        private Thread mCurrentThread;

        private int mStatus;

        private boolean canceld;

        Request(Callback callback) {
            this.callback = callback;
            setStatus(STATUS_INIT);
        }

        private void setStatus(int status) {
            mStatus = status;
        }

        @Override public void execute() {
            mCurrentThread = Thread.currentThread();
            setStatus(STATUS_REQUESTING);
            // 模拟网络请求，阻塞线程
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (canceld)
                callback.onReceive("canceled");
            else
                callback.onReceive("success");
        }

        @Override public void cancel() {
            if (canceld) return;
            canceld = true;
            if (mStatus == STATUS_COMPLETE) return;

            if (mStatus > STATUS_INIT && mCurrentThread != null)
                mCurrentThread.interrupt();


        }
    }

    static class Work extends Thread {
        private Cmd cmd;

        Work(Cmd cmd) {
            this.cmd
                    = cmd;
        }

        @Override public void run() {
            cmd.execute();
        }
    }

    static class HttpClient {

        public void request(Cmd request) {
            new Work(request).start();
        }
    }


}
