package plazma.ups.datastructures.list;

public abstract class AbstractList<T> implements List<T> {

    protected int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) > -1;
    }

    protected void validateIfIndexExists(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index must be between [0, "  + (size - 1) + "]");
        }
    }

    protected void validateIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be between [0, "  + size + "]");
        }
    }

}
