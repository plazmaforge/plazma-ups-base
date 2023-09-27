package plazma.ups.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static <T> T newInstance(Class<T> type) throws InstantiationException, IllegalAccessException {
        checkType(type);
        return type.newInstance();
    }

    public static Method[] getMethodsWithoutParameters(Class<?> type) {
        checkType(type);
        Method[] methods = getMethods(type);
        List<Method> result = new ArrayList<>();
        for (Method method: methods) {
            if (method.getParameterCount() == 0) {
                result.add(method);
            }
        }
        return result.toArray(new Method[0]);
    }

    public static Method[] getFinalMethods(Class<?> type) {
        checkType(type);
        Method[] methods = getMethods(type);
        List<Method> result = new ArrayList<>();
        for (Method method: methods) {
            if (Modifier.isFinal(method.getModifiers())) {
                result.add(method);
            }
        }
        return result.toArray(new Method[0]);
    }

    public static Method[] getNonPublicMethods(Class<?> type) {
        checkType(type);
        Method[] methods = getMethods(type);
        List<Method> result = new ArrayList<>();
        for (Method method: methods) {
            if (!Modifier.isPublic(method.getModifiers())) {
                result.add(method);
            }
        }
        return result.toArray(new Method[0]);
    }

    public static Class[] getParents(Class<?> type) {
        checkType(type);
        List<Class> result = new ArrayList<>();
        Class<?> superclass = type.getSuperclass();
        if (superclass != null) {
            result.add(superclass);
        }
        Class<?>[] interfaces = type.getInterfaces();
        if (interfaces.length > 0) {
            result.addAll(Arrays.asList(interfaces));
        }
        return result.toArray(new Class[0]);
    }

    public static Field[] getPrivateFields(Class<?> type) {
        checkType(type);
        Field[] fields = getFields(type);
        List<Field> result = new ArrayList<>();
        for (Field field: fields) {
            if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
                result.add(field);
            }
        }
        return result.toArray(new Field[0]);
    }


    private static void checkType(Class<?> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type must be not null");
        }
    }

    private static Method[] getMethods(Class<?> type) {
        return type == null ? null : type.getDeclaredMethods();
    }

    private static Field[] getFields(Class<?> type) {
        return type == null ? null : type.getDeclaredFields();
    }


}
