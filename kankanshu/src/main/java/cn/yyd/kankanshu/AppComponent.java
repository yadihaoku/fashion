package cn.yyd.kankanshu;

import javax.inject.Singleton;

import cn.yyd.kankanshu.modules.AndroidModule;
import dagger.Component;

/**
 * Created by YanYadi on 2017/4/18.
 */
@Singleton
@Component(modules = AndroidModule.class)
public interface AppComponent {
    void inj(KKApplication application);
}
