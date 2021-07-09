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
        println(list);

        list.add("Element New", 2);
        assertEquals("Element 0", list.get(0));
        assertEquals("Element 1", list.get(1));
        assertEquals("Element New", list.get(2));
        assertEquals("Element 2", list.get(3));
        assertEquals("Element 3", list.get(4));
        assertEquals("Element 4", list.get(5));

        assertEquals(size + 1, list.size());
        println("Size: " + list.size());
        println(list);

        list = new ArrayList();
        size = 12; // > 10 (capacity)
        populateList(list, size);

        assertEquals(size, list.size());
        println("Size: " + list.size());
        println(list);

        // add: null-1
        list.add(null);
        assertEquals(size + 1, list.size());

        // add: null-2
        list.add(null);
        assertEquals(size + 2, list.size());

    }

    @Test
    public void testAddThrow() {
        final ArrayList list = new ArrayList();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", -1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", list.size() + 1);
        });

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", -1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", list.size() + 1);
        });
    }

    @Test
    public void testRemove() {

        ArrayList list = new ArrayList();
        int size = 5;
        populateList(list, size);

        assertEquals(size, list.size());
        println("Size: " + list.size());
        println(list);

        Object value = list.remove(2);
        assertEquals("Element 2", value);

        assertEquals("Element 0", list.get(0));
        assertEquals("Element 1", list.get(1));
        assertEquals("Element 3", list.get(2));
        assertEquals("Element 4", list.get(3));

        assertEquals(size - 1, list.size()); // size = 4
        println("Size: " + list.size());
        println(list);

        list.add(null);          // index = 4, size = 5
        list.add("New Element"); // index = 5, size = 6
        list.add(null);          // index = 6, size = 7

        value = list.remove(4);
        assertNull(value);
        assertEquals(6, list.size()); // size = 6

        assertEquals("Element 0", list.get(0));
        assertEquals("Element 1", list.get(1));
        assertEquals("Element 3", list.get(2));
        assertEquals("Element 4", list.get(3));
        assertEquals("New Element", list.get(4));
        assertEquals(null, list.get(5));


    }

    @Test
    public void testRemoveThrow() {
        final ArrayList list = new ArrayList();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(0);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(list.size() + 1);
        });

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", list.size() + 1);
        });
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
        int size = 5;
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

        assertNotNull(list.get(3));
        list.set(null, 3);
        assertNull(list.get(3));

    }

    @Test
    public void testGetThrow() {
        final ArrayList list = new ArrayList();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(0);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(list.size() + 1);
        });

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(list.size() + 1);
        });
    }

    @Test
    public void testSetThrow() {
        final ArrayList list = new ArrayList();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", -1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", 0);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", list.size() + 1);
        });

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", -1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", list.size() + 1);
        });
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

        // indexOf/lastIndexOf/contains: null
        assertEquals(-1, list.indexOf(null));
        assertEquals(-1, list.lastIndexOf(null));
        assertFalse(list.contains(null));

        list.add(null);         // index = 8
        list.add("Element 9");  // index = 9
        list.add(null);         // index = 10
        list.add("Element 11"); // index = 11

        assertEquals(8, list.indexOf(null));
        assertEquals(10, list.lastIndexOf(null));
        assertTrue(list.contains(null));

    }

    private void println(Object value) {
        System.out.println(value);
    }

    private void populateList(List list, int size) {
        for (int i = 0; i < size; i++) {
            list.add("Element " + i);
        }
    }

}
