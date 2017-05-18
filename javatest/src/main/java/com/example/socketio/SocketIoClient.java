package com.example.socketio;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by YanYadi on 2017/5/17.
 */
public class SocketIoClient {
    //"http://msg.etao.cn/hub";
    private static final String MSG_URL = "http://172.18.12.12:9092";

    static Socket socket;

    public static void main(String[] args) throws Exception {

        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = false;
        opts.secure = false;
        opts.transports = new String[]{"websocket", "polling"};

        socket = IO.socket(MSG_URL, opts);

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override public void call(Object... args) {
                System.out.println("connect");
                System.out.println(Arrays.toString(args));
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override public void call(Object... args) {
                System.out.println("disconnect");
                System.out.println(Arrays.toString(args));
            }
        }).on("login", new Emitter.Listener() {
            @Override public void call(Object... args) {
                System.out.println("login event");
                System.out.println(Arrays.toString(args));
            }
        });

        socket.connect();

        socket.emit("join", getParams());
        socket.emit("login", "asasdfsdf");

        socket.on("hi", new Emitter.Listener() {
            @Override public void call(Object... args) {
                socket.emit("join", getParams());
            }
        });

    }

    static JSONObject getParams() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("supplierid", "asdfsaf");
            jsonObject.put("appid", "YanYadi");
            jsonObject.put("deviceid", "javaclient");
            jsonObject.put("eventid", "MAM-BA102-1001");
            jsonObject.put("msg", "YaDi");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject;
    }
}
