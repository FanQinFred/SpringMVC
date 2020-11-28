package cqu.ioc.annotation;

import java.lang.annotation.*;


/**
 * 被标记的属性将被自动注入
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Autowire {
    String value() default "";
}
