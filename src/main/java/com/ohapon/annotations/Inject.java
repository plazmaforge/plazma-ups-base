package com.ohapon.annotations;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {

    Class clazz();
}
