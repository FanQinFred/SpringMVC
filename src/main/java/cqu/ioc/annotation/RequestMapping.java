package cqu.ioc.annotation;

import java.lang.annotation.*;

/**
 * 被标记的类或方法将映射到某一url上处理某一类型的请求
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface RequestMapping {
    String value() default "";
    String method() default "";
}