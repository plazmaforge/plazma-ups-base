package plazma.ups.base.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {

    Class clazz();
}
