package dev.excellent.client.module.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleInfo {
    String name();

    String description();

    Category category();

    int keyBind() default -1;

    boolean autoEnabled() default false;

    boolean allowDisable() default true;

    boolean hidden() default false;

}
