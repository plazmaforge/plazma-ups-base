package com.ohapon.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
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
        Class<?> parent = type;
        while ((parent = parent.getSuperclass()) != null) {
            result.add(parent);
        }
        Collections.reverse(result);
        return result.toArray(new Class[0]);
    }

    public static Class[] getInterfaces(Class<?> type) {
        checkType(type);
        return type.getInterfaces();
    }

    ////

    public static Method[] getMethodsWithoutParameters(Object object) {
        checkObject(object);
        return getMethodsWithoutParameters(object.getClass());
    }

    public static Method[] getFinalMethods(Object object) {
        checkObject(object);
        return getFinalMethods(object.getClass());
    }

    ////

    private static void checkType(Class<?> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type must be not null");
        }
    }

    private static void checkObject(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object must be not null");
        }
    }

    private static Method[] getMethods(Class<?> type) {
        return type == null ? null : type.getDeclaredMethods();
    }


}
