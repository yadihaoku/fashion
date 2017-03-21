package cn.yyd.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 注：只有允许不同应用的客户端用 IPC 方式访问服务，
 * 并且想要在服务中处理多线程时，才有必要使用 AIDL。
 * 如果您不需要执行跨越不同应用的并发 IPC，就应该通
 * 过实现一个 Binder 创建接口；或者，如果您想执行
 * IPC，但根本不需要处理多线程，则使用 Messenger
 * 类来实现接口。无论如何，在实现 AIDL 之前，请您务必理解绑定服务。
 */
public class AidlService extends Service {
    private static final String TAG = "AidlService";

    ICalculator.Stub mBinder = new ICalculator.Stub() {
        @Override
        public String addition(int one, int two) throws RemoteException {
            return new StringBuffer(Integer.toString(one + two)).append("_from_AidlService").toString();
        }

        @Override
        public void sleep(int timeInMillisecond) throws RemoteException {

            try {
                System.out.println("-- Sleep -----TID =" + Thread.currentThread().getId());
                TimeUnit.MILLISECONDS.sleep(timeInMillisecond);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public Person makePerson() {
            System.out.println("-- makePerson -----TID =" + Thread.currentThread().getId());
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new Person("Sean",10,"Crazy Programmer_tid="+ Thread.currentThread().getId());
        }

        @Override
        public void getCurrentMillisecond() throws RemoteException {

            try {
                System.out.println("-- ThreadId ---" + Thread.currentThread().getId());
                System.out.println("-- ThreadId ---" + Thread.currentThread());
                TimeUnit.SECONDS.sleep(10);
                System.out.println("-- getCurrentMillisecond -----TID =" + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        @Override
        public List<String> getNames() throws RemoteException {
            List<String> name  = new ArrayList<>();
            name.add("1_aidlService");
            name.add("2_aidlService");
            name.add("3_aidlService");
            return name;
        }

        @Override
        public Map getParams(String prefix) throws RemoteException {
            return null;
        }

        @Override
        public void mixed(Person p) throws RemoteException {
            if(p != null){
                p.setAge(0);
                p.setName(p.getName() + "_AidlService");
            }
        }

        @Override
        public void setAge(Person p) throws RemoteException {
            if(p != null) {
                p.setAge(p.getAge() * 10);

                p.setName(p.getName() + "__out");
            }
        }

        @Override
        public void reset(Person p) throws RemoteException {
                if(p != null) {
                    p.setAge(0);
                    p.setName( p.getName()+"__inout");
                    p.setDescription(p.getDescription() + "__inout");
                }
        }

        public long getMyPid(){
            return Process.myPid();
        }

    };

    public AidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("------------onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ------------------");
    }

    /**
     * 返回值说明：
     * START_STICKY ： 如果服务被杀掉，稍后将尝试重建 Service 实例，并调用 onStartCommand ，并传入一个 null
     * intent.
     * <p>
     * START_STICKY_COMPATIBILITY 兼容模式跟 {@link Service#START_STICKY} 相同，如果 服务被杀死，将重建服务，但并不保证能
     * 调用 onStartCommand 。
     * <p>
     * START_REDELIVER_INTENT :如果服务被杀掉(在调用 onStartCommand 之后)，稍后建重建 Service 实例，并把重传最后一次发送的 intent
     * 传回 onStartCommand 中。这个方法不会传入一个null intent 调用 onStartCommand.
     * <p>
     * START_NOT_STICKY : 如果服务被杀掉(在调用 onStartCommand 之后)，将不会被重建。除非明确的调用 Context.startService .
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("-----------onStartCommand");
        return START_NOT_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("---------------onUnbind");
        System.out.println(intent);
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        System.out.println("---------------onRebind");
        System.out.println(intent);
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        System.out.println("----------onDestroy");
        super.onDestroy();
    }
}
