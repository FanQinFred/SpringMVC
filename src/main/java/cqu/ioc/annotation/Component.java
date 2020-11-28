package cqu.ioc.annotation;

import java.lang.annotation.*;

/**
 * 被标记的类将交由框架管理
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Component {
    String value() default "";
}