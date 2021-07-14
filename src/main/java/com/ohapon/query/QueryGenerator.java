package com.ohapon.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class QueryGenerator {

    public String getAll(Class<?> clazz) {
        checkClass(clazz);
        String tableName = getTableName(clazz);
        List<String> columnNames = getColumnNames(clazz);
        checkColumns(columnNames);

        StringBuilder builder = new StringBuilder("SELECT ");
        StringJoiner joiner = new StringJoiner(", ");

        for (String columnName: columnNames) {
            joiner.add(columnName);
        }

        builder.append(joiner);
        builder.append(" FROM ");
        builder.append(tableName);
        builder.append(";");

        return builder.toString();
    }

    public String insert(Object value) {
        // TODO
        // INSERT INTO table_name (column1, column2, column3, ...)
        // VALUES (value1, value2, value3, ...);
        return null;
    }

    public String update(Object value) {
        // TODO
        // UPDATE table_name
        // SET column1 = value1, column2 = value2, ...
        // WHERE id = [id];
        return null;
    }

    public String getById(Class<?> clazz, Object id) {
        // TODO
        // SELECT column1, column2 ...
        // FROM table_name
        // WHERE id = [id]
        return null;
    }

    public String delete(Class<?> clazz, Object id) {
        // TODO
        // DELETE
        // FROM table_name
        // WHERE id = [id]
        return null;
    }

    private String getTableName(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        if (annotation == null) {
            throw new IllegalArgumentException("@Table is missing");
        }
        return annotation.name().isEmpty() ? clazz.getName() : annotation.name();
    }

    private String getColumnName(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation == null) {
            return null;
        }
        return annotation.name().isEmpty() ? field.getName() : annotation.name();
    }

    private List<String> getColumnNames(Class<?> clazz) {
        List<String> result = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            String columnName = getColumnName(field);
            if (columnName != null) {
                result.add(columnName);
            }
        }
        return result;
    }

    private void checkClass(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must be not null");
        }
    }

    private void checkColumns(List<String> columnNames) {
        if (columnNames.isEmpty()) {
            throw new IllegalArgumentException("Can't generate query: No columns");
        }
    }


}
