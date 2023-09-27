package plazma.ups.annotations;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AnnotationUtilsTest {

    @Test
    public void testGetAnnotationMethods() {
        Class<?> type = MyRunClass.class;
        Method[] methods = AnnotationUtils.getAnnotationMethods(type, Run.class);
        List<String> result = new ArrayList<>();
        for (Method method: methods) {
            result.add(getMethodName(method));
        }
        result.remove("run");
        result.remove("call");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAnnotationFields() {
        Class<?> type = MyInjectClass.class;
        Field[] fields = AnnotationUtils.getAnnotationFields(type, Inject.class);
        List<String> result = new ArrayList<>();
        for (Field field: fields) {
            result.add(getFieldName(field));
        }
        result.remove("name");
        result.remove("list1");
        result.remove("list2");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testUseAnnotationFields() throws InstantiationException, IllegalAccessException {
        MyInjectClass object = new MyInjectClass();
        Field[] fields = AnnotationUtils.getAnnotationFields(MyInjectClass.class, Inject.class);
        List<String> result = new ArrayList<>();
        for (Field field: fields) {
            Inject inject = field.getAnnotation(Inject.class);
            Class<?> clazz = inject.clazz();
            Object value = clazz.newInstance();
            field.set(object, value);
        }
    }

    private String getMethodName(Method method) {
        return method.getName();
    }

    private String getFieldName(Field field) {
        return field.getName();
    }


}
