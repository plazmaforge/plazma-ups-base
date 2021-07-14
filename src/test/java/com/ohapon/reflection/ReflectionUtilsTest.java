package com.ohapon.reflection;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtilsTest {

    @Test
    public void testNewInstance() throws Exception {
        MyReflectionClass object = ReflectionUtils.newInstance(MyReflectionClass.class);
        assertNotNull(object);
        assertEquals(MyReflectionClass.class, object.getClass());
    }

    @Test
    public void testGetMethodsWithoutParameters() {
        MyReflectionClass object = new MyReflectionClass();
        Method[] methods = ReflectionUtils.getMethodsWithoutParameters(object.getClass());

        List<String> result = new ArrayList<>();
        for (Method method: methods) {
            System.out.println(method);
            result.add(method.getName());
        }
        result.remove("init");
        result.remove("start");
        result.remove("stop");
        result.remove("prepare");
        result.remove("reset");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFinalMethods() {
        MyReflectionClass object = new MyReflectionClass();
        Method[] methods = ReflectionUtils.getFinalMethods(object.getClass());

        List<String> result = new ArrayList<>();
        for (Method method: methods) {
            System.out.println(method);
            result.add(method.getName());
        }
        result.remove("reset");
        result.remove("calculate");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testNonPublicMethods() {
        Class<?> type = MyReflectionClass.class;
        Method[] methods = ReflectionUtils.getNonPublicMethods(type);
        List<String> result = new ArrayList<>();
        for (Method method: methods) {
            System.out.println(method);
            result.add(method.getName());
        }
        result.remove("prepare");
        result.remove("reset");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetParents() {
        Class<?>[] types = ReflectionUtils.getParents(MyReflectionClass.class);
        List<String> result = new ArrayList<>();
        for (Class<?> type: types) {
            result.add(type.getName());
        }
        result.remove("java.lang.Object");
        result.remove("java.io.Serializable");
        result.remove("java.lang.Cloneable");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testPrivateFields() throws IllegalAccessException, NoSuchFieldException {
        MyReflectionClass object = new MyReflectionClass();
        Field[] fields = ReflectionUtils.getPrivateFields(object.getClass());
        List<String> result = new ArrayList<>();
        for (Field field: fields) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            Object fieldValue = getDefaultValue(fieldType);
            field.set(object, getDefaultValue(fieldType));

            assertEquals(fieldValue, field.get(object));
        }
        result.remove("code");
        result.remove("count");
        result.remove("percent");
        assertTrue(result.isEmpty());

    }

    private Object getDefaultValue(Class<?> type) {
        if (type == boolean.class) {
            return false;
        }
        if (type == byte.class
                || type == short.class
                || type == int.class
                || type == long.class
                ) {
            return 0;
        }
        if (type == long.class) {
            return 0L;
        }

        if (type == float.class) {
            return 0F;
        }
        if (type == double.class) {
            return 0D;
        }
        return null;
    }


}
