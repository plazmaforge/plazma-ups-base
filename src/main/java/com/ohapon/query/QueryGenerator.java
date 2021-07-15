package com.ohapon.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.*;
import java.util.StringJoiner;

public class QueryGenerator {

    public String getAll(Class<?> clazz) {
        requireNonNull(clazz, "Class must be not null");

        Query query = createQuery(clazz);
        validateColumns(query);

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
        requireNonNull(value, "Value must be not null");

        Class<?> clazz = value.getClass();

        Query query = createQuery(clazz);
        validateColumns(query);

        // INSERT INTO table_name (column1, column2, column3, ...)
        // VALUES (value1, value2, value3, ...);

        StringBuilder builder = new StringBuilder("INSERT INTO ");
        StringJoiner columnJoiner = new StringJoiner(", ", "(", ")");
        StringJoiner valueJoiner = new StringJoiner(", ", "(", ")");

        for (QueryColumn column: query.getColumns()) {
            columnJoiner.add(column.getName());
            Object columnValue = getValue(value, column);
            valueJoiner.add(toSqlValue(columnValue));
        }

        builder.append(query.getTableName());
        builder.append(" ");
        builder.append(columnJoiner);
        builder.append(" VALUES ");
        builder.append(valueJoiner);

        return builder.toString();

    }

    public String update(Object value) {
        requireNonNull(value, "Value must be not null");
        Class<?> clazz = value.getClass();

        Query query = createQuery(clazz);
        validateColumns(query);
        validateIdColumn(query);

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
            Object columnValue = getValue(value, column);
            builder.append(toSqlValue(columnValue));
            if (i < query.getColumns().size() - 1) {
                builder.append(", ");
            }
        }

        builder.append(" WHERE ");
        builder.append(query.getIdColumn().getName());
        builder.append(" = ");
        builder.append(getValue(value, query.getIdColumn()));

        return builder.toString();
    }

    public String getById(Class<?> clazz, Object id) {
        requireNonNull(clazz, "Class must be not null");

        Query query = createQuery(clazz);
        validateColumns(query);
        validateIdColumn(query);

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
        requireNonNull(clazz, "Class must be not null");

        Query query = createQuery(clazz);
        validateIdColumn(query);

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

    private QueryColumn createQueryColumn(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        String name = columnAnnotation.name().isEmpty() ? field.getName() : columnAnnotation.name();;
        boolean key = field.isAnnotationPresent(Id.class);
        return new QueryColumn(name, field, key);
    }

    private Query createQuery(Class<?> clazz) {

        Query query = new Query();
        query.setTableName(getTableName(clazz));

        List<QueryColumn> columns = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                QueryColumn column = createQueryColumn(field);
                columns.add(column);
                if (column.isKey()) {
                    query.setIdColumn(column);
                }
            }
        }

        query.setColumns(columns);
        return query;

    }

    private Object getValue(Object object, QueryColumn column) {
        Object value = null;
        try {
            Field field = column.getField();
            field.setAccessible(true);
            value = field.get(object);
            return value;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalArgumentException("Can't generate query", e);
        }
    }

    private String toSqlValue(Object value) {
        if (value == null) {
            return "NULL";
        }
        if (value.getClass() == String.class) {
            return "'" + value + "'";
        }
        return value.toString();
    }

    private void validateColumns(Query query) {
        if (query.getColumns().isEmpty()) {
            throw new IllegalArgumentException("Can't generate query: Columns are empty");
        }
    }

    private void validateIdColumn(Query query) {
        if (query.getIdColumn() == null) {
            throw new IllegalArgumentException("Can't generate query: Id column is missing");
        }
    }

    private static class QueryColumn {

        private String name;

        private Field field;

        private boolean key;

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
