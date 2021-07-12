package com.ohapon.datastructures.list;

import java.util.Objects;
import java.util.StringJoiner;

public class LinkedList<T> extends AbstractList<T> {

    private Node first;
    private Node last;

    @Override
    public void add(T value, int index) {
        validateIndexForAdd(index);
        Node node = new Node(value);

        if (size == 0) {
            first = last = node;
        } else if (index == 0) {
            node.next = first;
            first.prev = node;
            first = node;
        } else if (index == size) {
            node.prev = last;
            last.next = node;
            last = node;
        } else {

            Node current = findByIndex(index);

            node.prev = current.prev;
            node.next = current;

            current.prev.next = node;
            current.prev = node;

        }

        size++;
    }

    @Override
    public T remove(int index) {
        validateIfIndexExists(index);
        Node current = findByIndex(index);

        if (size == 1) {
            first = last = null;
        } else if (index == 0) {
            first.next.prev = null;
            first = first.next;
        } else if (index == size - 1) {
            last.prev.next = null;
            last = last.prev;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }

        size--;

        return (T) current.data;
    }

    @Override
    public T get(int index) {
        validateIfIndexExists(index);
        Node current = findByIndex(index);
        return (T) current.data;
    }

    @Override
    public T set(Object value, int index) {
        validateIfIndexExists(index);
        Node current = findByIndex(index);
        Object oldValue = current.data;
        current.data = value;
        return (T) oldValue;
    }

    @Override
    public void clear() {
        Node current = first;
        Node next = null;
        for (int i = 0; i < size; i++) {
            next = current.next;
            current.prev = null;
            current.next = null;
            current.data = null;

            current = next;
        }

        first = last = null;
        size = 0;
    }

    @Override
    public int indexOf(Object value) {
        if (isEmpty()) {
            return -1;
        }
        Node current = first;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(current.data, value)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        if (isEmpty()) {
            return -1;
        }
        Node current = last;
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(current.data, value)) {
                return i;
            }
            current = current.prev;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(", ", "[", "]");
        Node curr = first;
        for (int i = 0; i < size; i++) {
            result.add(String.valueOf(curr.data));
        }
        return result.toString();
    }

    private Node findByIndex(int index) {
        Node current = null;
        if (index == 0) {
            current = first;
        } else if (index == size - 1) {
            current = last;
        } else {
            if (index <= size / 2) {
                current = first;
                for (int i = 0; i < index; i++) {
                    current = current.next;
                }
            } else {
                current = last;
                for (int i = size - 1; i > index; i--) {
                    current = current.prev;
                }
            }
        }
        return current;
    }

    private static class Node {
        private Node prev;
        private Node next;
        private Object data;

        private Node() {
        }

        public Node(Object data) {
            this.data = data;
        }
    }

}
