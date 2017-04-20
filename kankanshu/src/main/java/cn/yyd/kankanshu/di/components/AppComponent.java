package cn.yyd.kankanshu.di.components;

import javax.inject.Singleton;

import cn.yyd.kankanshu.KKApplication;
import cn.yyd.kankanshu.di.modules.AndroidModule;
import dagger.Component;

/**
 * Created by YanYadi on 2017/4/18.
 */
@Singleton
@Component(modules = AndroidModule.class)
public interface AppComponent {
    void inj(KKApplication application);
    KKApplication application();
//    ClassListFragment classList();
}
