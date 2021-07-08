package com.ohapon.datastructures.list;

import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedListTest {

    @Test
    public void testAdd() {

        LinkedList list = new LinkedList();
        int size = 5;
        populateList(list, size);

        assertEquals(list.size(), size);
        println("Size: " + list.size());
        printList(list);

        list.add("Element New", 2);
        assertEquals(list.get(0), "Element 0");
        assertEquals(list.get(1), "Element 1");
        assertEquals(list.get(2), "Element New");
        assertEquals(list.get(3), "Element 2");
        assertEquals(list.get(4), "Element 3");
        assertEquals(list.get(5), "Element 4");

        assertEquals(list.size(), size + 1);
        println("Size: " + list.size());
        printList(list);

    }

    @Test
    public void testRemove() {

        LinkedList list = new LinkedList();
        int size = 5;
        populateList(list, size);

        assertEquals(list.size(), size);
        println("Size: " + list.size());
        printList(list);

        Object value = list.remove(2);
        assertEquals(value, "Element 2");

        assertEquals(list.get(0), "Element 0");
        assertEquals(list.get(1), "Element 1");
        assertEquals(list.get(2), "Element 3");
        assertEquals(list.get(3), "Element 4");

        assertEquals(list.size(), size - 1);
        println("Size: " + list.size());
        printList(list);

    }

    @Test
    public void testStructure() {

        LinkedList list = new LinkedList();

        // isEmpty/size
        assertTrue(list.isEmpty());
        assertEquals(list.size(), 0);

        int size = 5; // < 10 (capacity)
        populateList(list, size);

        // isEmpty/size
        assertTrue(!list.isEmpty());
        assertEquals(list.size(), size);

        list.clear();

        // isEmpty/size
        assertTrue(list.isEmpty());
        assertEquals(list.size(), 0);

    }

    @Test
    public void testGetSet() {

        LinkedList list = new LinkedList();
        int size = 5; // < 10 (capacity)
        populateList(list, size);

        Object value = null;
        for (int i = 0 ; i < list.size(); i++) {
            value = list.get(i);
            assertEquals(value, "Element " + i);
        }

        for (int i = 0 ; i < list.size(); i++) {
            list.set("New Element " + i, i);
            value = list.get(i);
            assertEquals(value, "New Element " + i);
        }

    }

    @Test
    public void testIndex() {

        LinkedList list = new LinkedList();
        int size = 5;
        populateList(list, size);

        Object value = null;
        int index = 0;

        // indexOf/contains
        for (int i = 0 ; i < list.size(); i++) {
            value = list.get(i);
            index = list.indexOf(value);
            assertEquals(index, i);
            assertTrue(list.contains(value));
        }

        // lastIndexOf
        for (int i = list.size() - 1 ; i >= 0; i--) {
            value = list.get(i);
            index = list.lastIndexOf(value);
            assertEquals(index, i);
        }

        list = new LinkedList();
        list.add("Element 0");
        list.add("Element 1");
        list.add("Element Copy"); // index: 2
        list.add("Element 3");
        list.add("Element Copy");
        list.add("Element Copy"); // index: 5
        list.add("Element 6");
        list.add("Element 7");

        // index: found
        index = list.indexOf("Element Copy");
        assertEquals(index, 2);

        index = list.lastIndexOf("Element Copy");
        assertEquals(index, 5);

        // index: not found
        index = list.indexOf("Element Zero");
        assertEquals(index, -1);

        index = list.lastIndexOf("Element Zero");
        assertEquals(index, -1);

        // contains: found
        assertTrue(list.contains("Element Copy"));

        // contains: not found
        assertFalse(list.contains("Element Zero"));

    }

    private void println(Object value) {
        System.out.println(value);
    }

    private void populateList(List list, int size) {
        for (int i = 0; i < size; i++) {
            list.add("Element " + i);
        }
    }

    private void printList(List list) {
        if (list == null) {
            return;
        }
        for (int i = 0 ; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

}
