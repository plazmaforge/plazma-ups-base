package com.ohapon.datastructures.list;

public class ArrayList extends AbstractList {

    public static int DEFAULT_CAPACITY = 10;
    public static int DEFAULT_FACTOR = 10;

    private Object[] data;

    private int capacity;
    private int factor;

    public ArrayList() {
        init(DEFAULT_CAPACITY, DEFAULT_FACTOR);
    }

    public ArrayList(int capacity, int factor) {
        init(capacity, factor);
    }

    public ArrayList(int capacity) {
        init(capacity, DEFAULT_FACTOR);
    }

    @Override
    public void add(Object value) {
        int index = size;
        expandData(index);
        data[index] = value;
    }

    @Override
    public void add(Object value, int index) {
        checkIndex(index, 0, size);
        expandData(index);
        data[index] = value;
    }

    @Override
    public Object remove(int index) {
        checkIndex(index, 0, size - 1);
        Object value = data[index];
        collapseData(index);
        // TODO: truncData
        return value;
    }

    @Override
    public Object get(int index) {
        checkIndex(index, 0, size - 1);
        return data[index];
    }

    @Override
    public Object set(Object value, int index) {
        checkIndex(index, 0, size - 1);
        data[index] = value;
        return value;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;

        // TODO: truncData
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

    ////

    protected void init(int capacity, int factor) {
        this.size = 0;
        this.capacity = capacity < 1 ? DEFAULT_CAPACITY : capacity;
        this.factor = factor < 1 ? DEFAULT_FACTOR : factor;
        this.data = new Object[capacity];
    }

    protected void expandData(int index) {
        int newSize = size + 1;
        Object[] oldData = data;
        Object[] newData = data;

        boolean needExpand = false;
        if (newSize > capacity) {
            needExpand = true;
            capacity = newSize + factor;
            newData = new Object[capacity];
            data = newData;
        }

        if (needExpand) {
            System.arraycopy(oldData, 0, newData, 0, index);
        }

        if (index < size) {
            System.arraycopy(oldData, index, newData, index + 1, size - index);
        }

        size++;

    }

    protected void collapseData(int index) {

        Object[] oldData = data;
        Object[] newData = data;

        System.arraycopy(oldData, index + 1, newData, index, oldData.length - index - 1);

        size--;
    }

}
