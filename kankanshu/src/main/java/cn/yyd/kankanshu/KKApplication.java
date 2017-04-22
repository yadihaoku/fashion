package cn.yyd.kankanshu;

import android.app.Application;

import cn.yyd.kankanshu.di.components.AppComponent;
import cn.yyd.kankanshu.di.components.DaggerAppComponent;
import cn.yyd.kankanshu.di.modules.AndroidModule;

/**
 * Created by YanYadi on 2017/3/22.
 */
public class KKApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
        mAppComponent.inj(this);

    }

}
