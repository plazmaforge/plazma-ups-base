package com.ohapon.datastructures.list;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayListTest {

    @Test
    public void testAdd() {

        ArrayList list = new ArrayList();
        int size = 5; // < 10 (capacity)
        populateList(list, size);

        assertEquals(size, list.size());
        println("Size: " + list.size());
        printList(list);

        list.add("Element New", 2);
        assertEquals("Element 0", list.get(0));
        assertEquals("Element 1", list.get(1));
        assertEquals("Element New", list.get(2));
        assertEquals("Element 2", list.get(3));
        assertEquals("Element 3", list.get(4));
        assertEquals("Element 4", list.get(5));

        assertEquals(size + 1, list.size());
        println("Size: " + list.size());
        printList(list);

        list = new ArrayList();
        size = 12; // > 10 (capacity)
        populateList(list, size);

        assertEquals(size, list.size());
        println("Size: " + list.size());
        printList(list);

    }

    @Test
    public void testRemove() {

        ArrayList list = new ArrayList();
        int size = 5;
        populateList(list, size);

        assertEquals(size, list.size());
        println("Size: " + list.size());
        printList(list);

        Object value = list.remove(2);
        assertEquals("Element 2", value);

        assertEquals("Element 0", list.get(0));
        assertEquals("Element 1", list.get(1));
        assertEquals("Element 3", list.get(2));
        assertEquals("Element 4", list.get(3));

        assertEquals(size - 1, list.size());
        println("Size: " + list.size());
        printList(list);

    }

    @Test
    public void testStructure() {

        ArrayList list = new ArrayList();

        // isEmpty/size
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        int size = 5; // < 10 (capacity)
        populateList(list, size);

        // isEmpty/size
        assertTrue(!list.isEmpty());
        assertEquals(size, list.size());

        list.clear();

        // isEmpty/size
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

    }

    @Test
    public void testGetSet() {

        ArrayList list = new ArrayList();
        int size = 5; // < 10 (capacity)
        populateList(list, size);

        Object value = null;
        for (int i = 0 ; i < list.size(); i++) {
            value = list.get(i);
            assertEquals("Element " + i, value);
        }

        for (int i = 0 ; i < list.size(); i++) {
            list.set("New Element " + i, i);
            value = list.get(i);
            assertEquals("New Element " + i, value);
        }

    }

    @Test
    public void testIndex() {

        ArrayList list = new ArrayList();
        int size = 5;
        populateList(list, size);

        Object value = null;
        int index = 0;

        // indexOf/contains
        for (int i = 0 ; i < list.size(); i++) {
            value = list.get(i);
            index = list.indexOf(value);
            assertEquals(i, index);
            assertTrue(list.contains(value));
        }

        // lastIndexOf
        for (int i = list.size() - 1 ; i >= 0; i--) {
            value = list.get(i);
            index = list.lastIndexOf(value);
            assertEquals(i, index);
        }

        list = new ArrayList();
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
        assertEquals(2, index);

        index = list.lastIndexOf("Element Copy");
        assertEquals(5, index);

        // index: not found
        index = list.indexOf("Element Zero");
        assertEquals(-1, index);

        index = list.lastIndexOf("Element Zero");
        assertEquals(-1, index);

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
