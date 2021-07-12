package com.ohapon.datastructures.list;

import java.util.Objects;
import java.util.StringJoiner;

public class LinkedList extends AbstractList {

    private Node first;
    private Node last;

    private static class Node {
        Node prev;
        Node next;
        Object data;
    }

    @Override
    public void add(Object value) {
        Node node = new Node();
        node.data = value;

        if (size == 0) {
            first = node;
            last = first;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        size++;
    }

    @Override
    public void add(Object value, int index) {
        validateIndexForAdd(index);
        Node curr = findByIndex(index);
        if (curr == null) {
            // structure error
            return;
        }
        Node prev = curr.prev;

        Node node = new Node();
        node.data = value;

        prev.next = node;
        node.prev = prev;
        node.next = curr;
        curr.prev = node;

        if (index == 0) {
            first = node;
        }
        if (index == size) {
            last = node;
        }

        size++;
    }

    @Override
    public Object remove(int index) {
        validateIfIndexExists(index);
        Node curr = findByIndex(index);
        if (curr == null) {
            // structure error
            return null;
        }
        Node prev = curr.prev;
        Node next = curr.next;

        if (prev != null) {
            prev.next = curr.next;
        }

        if (next != null) {
            next.prev = curr.prev;
        }

        if (index == 0) {
            first = curr.next;
        }
        if (index == size - 1) {
            last = curr.prev;
        }

        size--;

        return curr.data;
    }

    @Override
    public Object get(int index) {
        validateIfIndexExists(index);
        Node curr = findByIndex(index);
        if (curr == null) {
            // structure error
            return null;
        }
        return curr.data;
    }

    @Override
    public Object set(Object value, int index) {
        validateIfIndexExists(index);
        Node curr = findByIndex(index);
        if (curr == null) {
            // structure error
            return null;
        }
        Object oldValue = curr.data;
        curr.data = value;
        return oldValue;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public int indexOf(Object value) {
        if (first == null) {
            return -1;
        }
        Node curr = first;
        for (int i = 0; i < size; i++) {
            if (curr == null) {
                // structure error
                return -1;
            }
            if (Objects.equals(curr.data, value)) {
                return i;
            }
            curr = curr.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        if (last == null) {
            return -1;
        }
        Node curr = last;
        for (int i = size - 1; i >= 0; i--) {
            if (curr == null) {
                // structure error
                return -1;
            }
            if (Objects.equals(curr.data, value)) {
                return i;
            }
            curr = curr.prev;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringJoiner buf = new StringJoiner(", ", "[", "]");
        Node curr = first;
        for (int i = 0; i < size; i++) {
            buf.add(curr.data == null ? null : curr.data.toString());
        }
        return buf.toString();
    }

    protected Node findByIndex(int index) {
        if (first == null) {
            return null;
        }
        Node curr =  first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return curr;
            }
            if (curr == null) {
                // structure error
                return null;
            }
            curr = curr.next;
        }
        return null;
    }

}
