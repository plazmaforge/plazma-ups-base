package com.ohapon.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class QueryGenerator {

    public String getAll(Class<?> clazz) {
        checkClass(clazz);

        Query query = createQuery(clazz);

        StringBuilder builder = new StringBuilder("SELECT ");
        StringJoiner joiner = new StringJoiner(", ");

        for (QueryColumn column: query.getColumns()) {
            joiner.add(column.getName());
        }

        builder.append(joiner);
        builder.append(" FROM ");
        builder.append(query.getTableName());

        return builder.toString();
    }

    public String insert(Object value) {
        checkObject(value);
        Class<?> clazz = value.getClass();

        Query query = createQuery(clazz);

        // INSERT INTO table_name (column1, column2, column3, ...)
        // VALUES (value1, value2, value3, ...);

        StringBuilder builder = new StringBuilder("INSERT INTO ");
        StringJoiner columnJoiner = new StringJoiner(", ", "(", ")");
        StringJoiner valueJoiner = new StringJoiner(", ", "(", ")");

        for (QueryColumn column: query.getColumns()) {
            columnJoiner.add(column.getName());
            valueJoiner.add(toSqlValue(value, column));
        }

        builder.append(query.getTableName());
        builder.append(" ");
        builder.append(columnJoiner);
        builder.append(" VALUES ");
        builder.append(valueJoiner);

        return builder.toString();

    }

    public String update(Object value) {
        checkObject(value);
        Class<?> clazz = value.getClass();

        Query query = createQuery(clazz);

        // UPDATE table_name
        // SET column1 = value1, column2 = value2, ...
        // WHERE id = [id];

        StringBuilder builder = new StringBuilder("UPDATE ");

        builder.append(query.getTableName());
        builder.append(" SET ");

        for (int i = 0; i < query.getColumns().size(); i++) {
            QueryColumn column = query.getColumns().get(i);
            if (column.isKey()) {
                continue;
            }
            builder.append(column.getName());
            builder.append(" = ");
            builder.append(toSqlValue(value, column));
            if (i < query.getColumns().size() - 1) {
                builder.append(", ");
            }
        }

        builder.append(" WHERE ");
        builder.append(query.getIdColumn().getName());
        builder.append(" = ");
        builder.append(toSqlValue(value, query.getIdColumn()));

        return builder.toString();
    }

    public String getById(Class<?> clazz, Object id) {

        checkClass(clazz);

        Query query = createQuery(clazz);

        // SELECT column1, column2 ...
        // FROM table_name
        // WHERE id = [id]

        StringBuilder builder = new StringBuilder("SELECT ");
        StringJoiner joiner = new StringJoiner(", ");

        for (QueryColumn column: query.getColumns()) {
            joiner.add(column.getName());
        }

        builder.append(joiner);
        builder.append(" FROM ");
        builder.append(query.getTableName());
        builder.append(" WHERE ");
        builder.append(query.getIdColumn().getName());
        builder.append(" = ");
        builder.append(toSqlValue(id));

        return builder.toString();

    }

    public String delete(Class<?> clazz, Object id) {
        checkClass(clazz);

        Query query = createQuery(clazz);

        // DELETE
        // FROM table_name
        // WHERE id = [id]

        StringBuilder builder = new StringBuilder("DELETE FROM ");
        builder.append(query.getTableName());
        builder.append(" WHERE ");
        builder.append(query.getIdColumn().getName());
        builder.append(" = ");
        builder.append(toSqlValue(id));

        return builder.toString();
    }

    private String getTableName(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        if (annotation == null) {
            throw new IllegalArgumentException("@Table is missing");
        }
        return annotation.name().isEmpty() ? clazz.getName() : annotation.name();
    }

    private QueryColumn createColumn(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        String name = null;
        boolean key = false;
        if (columnAnnotation == null) {
            Id idAnnotation = field.getAnnotation(Id.class);
            if (idAnnotation == null) {
                return null;
            }
            name = idAnnotation.name().isEmpty() ? field.getName() : idAnnotation.name();
            key = true;
        } else {
            name = columnAnnotation.name().isEmpty() ? field.getName() : columnAnnotation.name();
        }
        if (name == null) {
            return null;
        }
        return new QueryColumn(name, field, key);
    }

    private Query createQuery(Class<?> clazz) {

        Query query = new Query();
        query.setTableName(getTableName(clazz));

        List<QueryColumn> columns = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            QueryColumn column = createColumn(field);
            if (column == null) {
                continue;
            }
            columns.add(column);
            if (column.isKey()) {
                query.setIdColumn(column);
            }
        }

        query.setColumns(columns);
        return query;

    }

    private String toSqlValue(Object object, QueryColumn column) {
        Object value = null;
        try {
            column.getField().setAccessible(true);
            value = column.getField().get(object);
            return toSqlValue(value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO: throw QueryGeneration exception
            return null;
        }
    }

    private String toSqlValue(Object value) {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        }
        // TODO: format value
        return value.toString();
    }

    private void checkClass(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must be not null");
        }
    }

    private void checkObject(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object must be not null");
        }
    }

    private void checkColumns(List<String> columnNames) {
        if (columnNames.isEmpty()) {
            throw new IllegalArgumentException("Can't generate query: No columns");
        }
    }

    private static class QueryColumn {

        private String name;

        private Field field;

        private boolean key;

        public QueryColumn() {
        }

        public QueryColumn(String name, Field field) {
            this.name = name;
            this.field = field;
        }

        public QueryColumn(String name, Field field, boolean key) {
            this.name = name;
            this.field = field;
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public boolean isKey() {
            return key;
        }

        public void setKey(boolean key) {
            this.key = key;
        }
    }

    private static class Query {

        private String tableName;
        private QueryColumn idColumn;
        private List<QueryColumn> columns;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public QueryColumn getIdColumn() {
            return idColumn;
        }

        public void setIdColumn(QueryColumn idColumn) {
            this.idColumn = idColumn;
        }

        public List<QueryColumn> getColumns() {
            return columns;
        }

        public void setColumns(List<QueryColumn> columns) {
            this.columns = columns;
        }
    }
}
