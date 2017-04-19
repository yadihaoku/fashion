package cn.yyd.kankanshu;

import android.app.Application;

import javax.inject.Inject;

import cn.yyd.kankanshu.dao.BookListInterface;
import cn.yyd.kankanshu.modules.AndroidModule;

/**
 * Created by YanYadi on 2017/3/22.
 */
public class KKApplication extends Application {

    @Inject BookListInterface mBookList;

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
