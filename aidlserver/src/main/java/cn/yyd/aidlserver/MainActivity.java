package cn.yyd.aidlserver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);

        findViewById(R.id.btn_addition).setOnClickListener(this);
        findViewById(R.id.btn_get_names).setOnClickListener(this);
        findViewById(R.id.btn_make_person).setOnClickListener(this);
        findViewById(R.id.btn_sleep).setOnClickListener(this);
        findViewById(R.id.btn_oneway).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int vid = v.getId();
        if (vid == R.id.btn_start) {
            startSvr();
            return;
        } else if (vid == R.id.btn_stop) {
            if (mCalculator != null && mIsBind) {
                unbindService(serviceConnection);
                mIsBind = false;
            }
        } else if (vid == R.id.btn_addition) {
            if (mCalculator != null) {
                try {
                    String result = mCalculator.addition(10, 20);
                    System.out.println("result=" + result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else if (vid == R.id.btn_get_names) {
            if (mCalculator != null) {
                try {
                    List<String> names = mCalculator.getNames();
                    System.out.println("names=" + names);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else if (vid == R.id.btn_sleep) {
            if (mCalculator != null) {
                try {
                    mCalculator.sleep(10000);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else if (vid == R.id.btn_oneway) {
            if (mCalculator != null) {
                try {
                    System.out.println("-------one way------------");
                    mCalculator.getCurrentMillisecond();
                    System.out.println("-------one way------------");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else if (vid == R.id.btn_make_person) {
            if (mCalculator != null) {
                try {
                    Person person = mCalculator.makePerson();
                    System.out.println("name =" + person.getName());
                    System.out.println("age =" + person.getAge());
                    System.out.println("des =" + person.getDescription());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else if(vid == R.id.btn_mixed){
            if (mCalculator != null) {
                Person p1 = new Person("YYD",28,"programmer");
                try {
                    mCalculator.mixed(p1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                System.out.println("mixed:" + p1);
            }
        }
        else if(vid == R.id.btn_reset){
            if (mCalculator != null) {
                Person p1 = new Person("YYD",28,"programmer");
                try {
                    mCalculator.reset(p1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                System.out.println("reset:" + p1);
            }
        }
        else if(vid == R.id.btn_set_age){
            if (mCalculator != null) {
                Person p1 = new Person("YYD",28,"programmer");
                try {
                    mCalculator.setAge(p1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                System.out.println("setAge:" + p1);
            }
        }
    }

    private ICalculator mCalculator;
    private boolean mIsBind;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCalculator = ICalculator.Stub.asInterface(service);
            mIsBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBind = false;
            mCalculator = null;

            Toast.makeText(MainActivity.this, "服务已经断开",
                    Toast.LENGTH_SHORT).show();
        }
    };

    private void startSvr() {
        if (mIsBind) {
            Toast.makeText(this, "程序已经启动", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, AidlService.class);
//            startService(intent);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }
}
