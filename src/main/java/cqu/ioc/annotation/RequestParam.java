package cqu.ioc.annotation;

import java.lang.annotation.*;

/**
 * 被标记的参数将在反射中被自动填入值
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface RequestParam {
    String value() default "";
}
