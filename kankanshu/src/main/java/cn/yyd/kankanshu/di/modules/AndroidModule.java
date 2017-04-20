package cn.yyd.kankanshu.di.modules;

import javax.inject.Singleton;

import cn.yyd.kankanshu.KKApplication;
import cn.yyd.kankanshu.dao.BookListInterface;
import cn.yyd.kankanshu.dao.impl.Qing100BookList;
import dagger.Module;
import dagger.Provides;

/**
 * Created by YanYadi on 2017/4/18.
 */
@Module
public class AndroidModule {
    private final KKApplication application;

    public AndroidModule(KKApplication application) {
        this.application = application;
    }


    @Provides @Singleton
    public BookListInterface provideBookList() {
        return new Qing100BookList();
    }

    @Provides @Singleton KKApplication application() {
        return application;
    }

    @Provides String getName(){
        return "lazyLoad";
    }
}
