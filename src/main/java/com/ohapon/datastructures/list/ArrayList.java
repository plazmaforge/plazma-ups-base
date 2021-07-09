package com.ohapon.datastructures.list;

public class ArrayList extends AbstractList {

    private static final int DEFAULT_CAPACITY = 10;
    private static final double DEFAULT_LOAD_FACTOR = 1.5f;

    private Object[] data;
    private double loadFactor;

    public ArrayList() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public ArrayList(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public ArrayList(int initialCapacity, double loadFactor) {
        this.loadFactor = loadFactor < 1 ? DEFAULT_LOAD_FACTOR : loadFactor;
        this.data = new Object[initialCapacity];
    }

    @Override
    public void add(Object value) {
        add(value, size);
    }

    @Override
    public void add(Object value, int index) {
        validateIndexForAdd(index);
        ensureCapacity();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = value;
        size++;
    }

    @Override
    public Object remove(int index) {
        validateIfIndexExists(index);
        Object value = data[index];
        System.arraycopy(data, index + 1, data, index, data.length - index - 1);
        size--;
        return value;
    }

    @Override
    public Object get(int index) {
        validateIfIndexExists(index);
        return data[index];
    }

    @Override
    public Object set(Object value, int index) {
        validateIfIndexExists(index);
        Object oldValue = data[index];
        data[index] = value;
        return oldValue;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    @Override
    public int indexOf(Object value) {
        if (value == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (value.equals(data[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        if (value == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (value.equals(data[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "ArrayList[]";
        }
        Object[] array = toArray();
        StringBuilder buf = new StringBuilder("ArrayList[");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(array[i]);
        }
        buf.append("]");
        return buf.toString();
    }

    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }
        Object[] result = new Object[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }

    protected void ensureCapacity() {
        if (size == getCapacity()) {
            int newCapacity = (int) (size * loadFactor);
            Object[] newData = new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    int getCapacity() {
        return data.length;
    }

}
