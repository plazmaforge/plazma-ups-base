package plazma.ups.base.util.list;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class ArrayList<T> extends AbstractList<T> {

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
    public void add(T value, int index) {
        validateIndexForAdd(index);
        ensureCapacity();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = value;
        size++;
    }

    @Override
    public T remove(int index) {
        validateIfIndexExists(index);
        Object value = data[index];
        System.arraycopy(data, index + 1, data, index, data.length - index - 1);
        data[size - 1] = null;
        size--;
        return (T) value;
    }

    @Override
    public T get(int index) {
        validateIfIndexExists(index);
        return (T) data[index];
    }

    @Override
    public T set(Object value, int index) {
        validateIfIndexExists(index);
        Object oldValue = data[index];
        data[index] = value;
        return (T) oldValue;
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
        for (int i = 0; i < size; i++) {
            if (Objects.equals(data[i], value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(data[i], value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            result.add(String.valueOf(data[i]));
        }
        return result.toString();
    }

    private void ensureCapacity() {
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

    private class ArrayIterator implements Iterator<T> {

        int index = -1;

        @Override
        public boolean hasNext() {
            return index + 1 < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            index++;
            return (T) ArrayList.this.get(index);
        }

        @Override
        public void remove() {
            if (index < 0) {
                throw new IllegalStateException();
            }
            ArrayList.this.remove(index);
            index--;
        }
    }

}
