package cn.yyd.kankanshu.dagger.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by YanYadi on 2017/4/20.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedBound {
    String value() default "";
}
