package com.ohapon.datastructures.list;

public class ArrayList extends AbstractList {

    public static int DEFAULT_CAPACITY = 10;
    public static float DEFAULT_FACTOR = 1.5f;

    private Object[] data;
    private int initialCapacity;
    private float factor;

    public ArrayList() {
        init(DEFAULT_CAPACITY, DEFAULT_FACTOR);
    }

    public ArrayList(int initialCapacity, float factor) {
        init(initialCapacity, factor);
    }

    public ArrayList(int initialCapacity) {
        init(initialCapacity, DEFAULT_FACTOR);
    }

    @Override
    public void add(Object value) {
        add(value, size);
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
        truncData();
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
        truncData();
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

    ////

    protected void init(int initialCapacity, float factor) {
        this.size = 0;
        this.initialCapacity = initialCapacity < 1 ? DEFAULT_CAPACITY : initialCapacity;
        this.factor = factor < 1 ? DEFAULT_FACTOR : factor;
        this.data = new Object[initialCapacity];
    }

    protected void expandData(int index) {
        int newSize = size + 1;
        Object[] oldData = data;
        Object[] newData = data;

        boolean needExpand = false;
        if (newSize > getCapacity()) {
            needExpand = true;
            int newCapacity = growCapacity(newSize);
            newData = new Object[newCapacity];
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

    protected int growCapacity(int size) {
        return (int) (size * factor);
    }

    protected void collapseData(int index) {
        System.arraycopy(data, index + 1, data, index, data.length - index - 1);
        size--;
    }

    protected void truncData() {
        int newCapacity = growCapacity(size);
        int maxCapacity = newCapacity * 2;
        if ((getCapacity() > maxCapacity) && (newCapacity > size)) {
            Object[] newData = new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    protected int getCapacity() {
        return data.length;
    }

}
