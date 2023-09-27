package plazma.ups.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationUtils {

    private AnnotationUtils() {
    }

    public static <A extends Annotation> Method[] getAnnotationMethods(Class<?> type, Class<A> annotationType) {
        Method[] methods = getMethods(type);
        List<Method> result = new ArrayList<>();
        Annotation a = null;
        for (Method method: methods) {
            a = method.getAnnotation(annotationType);
            if (method.getAnnotation(annotationType) != null) {
                result.add(method);
            }
        }
        return result.toArray(new Method[0]);

    }

    public static <A extends Annotation> Field[] getAnnotationFields(Class<?> type, Class<A> annotationType) {
        Field[] fields = getFields(type);
        List<Field> result = new ArrayList<>();
        for (Field field: fields) {
            if (field.getAnnotation(annotationType) != null) {
                result.add(field);
            }
        }
        return result.toArray(new Field[0]);
    }

    private static Method[] getMethods(Class<?> type) {
        return type == null ? null : type.getDeclaredMethods();
    }

    private static Field[] getFields(Class<?> type) {
        return type == null ? null : type.getDeclaredFields();
    }


}
