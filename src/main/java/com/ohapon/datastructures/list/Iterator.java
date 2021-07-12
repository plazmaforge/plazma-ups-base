package com.ohapon.datastructures.list;

public interface Iterator<T> {

    boolean hasNext();

    T next();

    void remove();

}
