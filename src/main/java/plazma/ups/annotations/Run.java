package plazma.ups.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Run {
}
