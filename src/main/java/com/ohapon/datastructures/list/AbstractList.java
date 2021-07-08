package com.ohapon.datastructures.list;

public abstract class AbstractList implements List {

    protected int size;

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

    public abstract Object[] toArray();

    ////

    protected void checkIndex(int index, int from, int to) {
        if (index < from || index > to) {
            throw new IndexOutOfBoundsException("Index must be between [" + from + ", "  + to + "]");
        }
    }




}
