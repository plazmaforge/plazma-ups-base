package com.ohapon.reflection;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtilsTest {

    @Test
    public void testNewInstance() throws Exception {
        Object object = ReflectionUtils.newInstance(String.class);
        assertNotNull(object);
        assertEquals(String.class, object.getClass());

        object = ReflectionUtils.newInstance(java.util.ArrayList.class);
        assertNotNull(object);
        assertEquals(java.util.ArrayList.class, object.getClass());
    }

    @Test
    public void testGetMethodsWithoutParameters() throws Exception {
        Object object = "Hello";
        Method[] methods = ReflectionUtils.getMethodsWithoutParameters(object.getClass());
        for (Method method: methods) {
            method.invoke(object);
        }
    }

    @Test
    public void testFinalMethods() {
        Object object = new Object();
        Method[] methods = ReflectionUtils.getFinalMethods(object.getClass());
        for (Method method: methods) {
            System.out.println(method);
        }
    }

    @Test
    public void testNonPublicMethods() {
        Class<?> type = String.class;
        Method[] methods = ReflectionUtils.getNonPublicMethods(type);
        for (Method method: methods) {
            System.out.println(method);
        }
    }

    @Test
    public void testGetParents() {
        Class<?>[] types = ReflectionUtils.getParents(java.util.ArrayList.class);
        for (Class<?> type: types) {
            System.out.println(type);
        }
    }

    @Test
    public void testPrivateFields() throws IllegalAccessException {
        Object object = "Hello";
        Field[] fields = ReflectionUtils.getPrivateFields(object.getClass());
        for (Field field: fields) {
            System.out.println(field);

            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            field.set(object, getDefaultValue(fieldType));
        }
    }

    private Object getDefaultValue(Class<?> type) {
        if (type == boolean.class) {
            return false;
        }
        if (type == byte.class
                || type == short.class
                || type == int.class
                || type == long.class
                || type == float.class
                || type == double.class) {
            return 0;
        }
        return null;
    }

}
