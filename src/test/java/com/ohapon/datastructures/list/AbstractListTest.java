package com.ohapon.datastructures.list;

import org.junit.Test;
import static org.junit.Assert.*;

public abstract class AbstractListTest {

    abstract List createList();

    @Test
    public void testAddElement() {

        List list = createList();
        assertEquals(0, list.size());

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");
        list.add(null);
        list.add("Element 4");
        assertEquals(5, list.size());

        assertEquals("Element 0", list.get(0));
        assertEquals("Element 1", list.get(1));
        assertEquals("Element 2", list.get(2));
        assertEquals(null, list.get(3));
        assertEquals("Element 4", list.get(4));

    }

    @Test
    public void testAddElementByIndex() {

        List list = createList();
        assertEquals(0, list.size());

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");
        list.add(null);
        list.add("Element 4");
        assertEquals(5, list.size());

        list.add("Element New", 2);
        assertEquals(6, list.size());

        assertEquals("Element 0", list.get(0));
        assertEquals("Element 1", list.get(1));
        assertEquals("Element New", list.get(2));
        assertEquals("Element 2", list.get(3));
        assertEquals(null, list.get(4));
        assertEquals("Element 4", list.get(5));

        list.add(null, 1);
        assertEquals(7, list.size());

        assertEquals("Element 0", list.get(0));
        assertEquals(null, list.get(1));
        assertEquals("Element 1", list.get(2));
        assertEquals("Element New", list.get(3));
        assertEquals("Element 2", list.get(4));
        assertEquals(null, list.get(5));
        assertEquals("Element 4", list.get(6));

    }

    @Test
    public void testAddElementMinIndexWhenThrow() {
        List list = createList();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", -1);
        });

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", -1);
        });
    }

    @Test
    public void testAddElementMaxIndexWhenThrow() {
        List list = createList();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", list.size() + 1);
        });

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", list.size() + 1);
        });
    }

    @Test
    public void testRemoveElementByIndex() {

        List list = createList();
        assertEquals(0, list.size());

        // add 5 elements
        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");
        list.add("Element 3");
        list.add("Element 4");

        assertEquals(5, list.size());

        Object value = list.remove(2);
        assertEquals("Element 2", value);

        assertEquals(4, list.size());

        assertEquals("Element 0", list.get(0));
        assertEquals("Element 1", list.get(1));
        assertEquals("Element 3", list.get(2));
        assertEquals("Element 4", list.get(3));


        list.add(null);
        list.add("New Element");
        list.add(null);

        assertEquals(7, list.size());

        value = list.remove(4);
        assertNull(value);
        assertEquals(6, list.size());

        assertEquals("Element 0", list.get(0));
        assertEquals("Element 1", list.get(1));
        assertEquals("Element 3", list.get(2));
        assertEquals("Element 4", list.get(3));
        assertEquals("New Element", list.get(4));
        assertEquals(null, list.get(5));

    }

    @Test
    public void testRemoveElementMinIndexWhenThrow() {
        List list = createList();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(0);
        });

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        });
    }

    @Test
    public void testRemoveElementMaxIndexWhenThrow() {
        List list = createList();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(list.size() + 1);
        });

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", list.size() + 1);
        });
    }

    @Test
    public void testIsEmpty() {

        List list = createList();
        assertTrue(list.isEmpty());

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertFalse(list.isEmpty());

        list.remove(0);
        assertFalse(list.isEmpty());

        list.remove(1);
        assertFalse(list.isEmpty());

        list.remove(0);
        assertTrue(list.isEmpty());

    }

    @Test
    public void testIsEmptyWithClear() {

        List list = createList();
        assertTrue(list.isEmpty());

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertFalse(list.isEmpty());

        list.clear();
        assertTrue(list.isEmpty());

    }

    @Test
    public void testSize() {

        List list = createList();
        assertEquals(0, list.size());

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertEquals(3, list.size());

        list.remove(0);
        assertEquals(2, list.size());

        list.remove(1);
        assertEquals(1, list.size());

        list.remove(0);
        assertEquals(0, list.size());

    }

    @Test
    public void testSizeWithClear() {

        List list = createList();
        assertEquals(0, list.size());

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertEquals(3, list.size());

        list.clear();
        assertEquals(0, list.size());

    }

    @Test
    public void testClear() {

        List list = createList();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertTrue(!list.isEmpty());
        assertEquals(3, list.size());

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

    }

    @Test
    public void testGetElement() {

        List list = createList();

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");
        list.add(null);
        list.add("Element 4");

        assertEquals("Element 0", list.get(0));
        assertEquals("Element 1", list.get(1));
        assertEquals("Element 2", list.get(2));
        assertEquals(null, list.get(3));
        assertEquals("Element 4", list.get(4));

    }

    @Test
    public void testSetElement() {

        List list = createList();

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");
        list.add(null);
        list.add("Element 4");

        assertEquals("Element 0", list.get(0));
        assertEquals("Element 1", list.get(1));
        assertEquals("Element 2", list.get(2));
        assertEquals(null, list.get(3));
        assertEquals("Element 4", list.get(4));

        assertEquals("Element 0", list.set("New Element 0", 0));
        assertEquals("Element 1", list.set(null, 1));
        assertEquals("Element 2", list.set("New Element 2", 2));
        assertEquals(null, list.set("New Element 3", 3));
        assertEquals("Element 4", list.set("New Element 4", 4));

        assertEquals("New Element 0", list.get(0));
        assertEquals(null, list.get(1));
        assertEquals("New Element 2", list.get(2));
        assertEquals("New Element 3", list.get(3));
        assertEquals("New Element 4", list.get(4));

    }

    @Test
    public void testGetElementMinIndexWhenThrow() {

        List list = createList();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(0);
        });

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });

    }

    @Test
    public void testGetElementMaxIndexWhenThrow() {

        List list = createList();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(list.size() + 1);
        });

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(list.size() + 1);
        });

    }

    @Test
    public void testSetElementMinIndexWhenThrow() {

        List list = createList();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", -1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", 0);
        });

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", -1);
        });

    }

    @Test
    public void testSetElementMaxIndexWhenThrow() {

        List list = createList();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", list.size() + 1);
        });

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", list.size() + 1);
        });

    }

    @Test
    public void testIndexOf() {

        List list = createList();

        list.add("Element 0");
        list.add("Element 1");
        list.add(null);
        list.add("Element 3");
        list.add("Element 4");

        assertEquals(0, list.indexOf("Element 0"));
        assertEquals(1, list.indexOf("Element 1"));
        assertEquals(2, list.indexOf(null));
        assertEquals(3, list.indexOf("Element 3"));
        assertEquals(4, list.indexOf("Element 4"));

        assertEquals(-1, list.indexOf("Element Zero"));

    }

    @Test
    public void testIndexOfWithDuplicate() {

        List list = createList();

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element Copy");
        list.add("Element 3");
        list.add("Element Copy");
        list.add("Element Copy");
        list.add("Element 6");
        list.add("Element 7");
        list.add(null);
        list.add(null);
        list.add("Element 10");

        assertEquals(0, list.indexOf("Element 0"));
        assertEquals(1, list.indexOf("Element 1"));
        assertEquals(2, list.indexOf("Element Copy"));
        assertEquals(3, list.indexOf("Element 3"));
        assertEquals(6, list.indexOf("Element 6"));
        assertEquals(7, list.indexOf("Element 7"));
        assertEquals(8, list.indexOf(null));
        assertEquals(10, list.indexOf("Element 10"));

    }

    @Test
    public void testLastIndexOf() {

        List list = createList();

        list.add("Element 0");
        list.add("Element 1");
        list.add(null);
        list.add("Element 3");
        list.add("Element 4");

        assertEquals(4, list.lastIndexOf("Element 4"));
        assertEquals(3, list.lastIndexOf("Element 3"));
        assertEquals(2, list.lastIndexOf(null));
        assertEquals(1, list.lastIndexOf("Element 1"));
        assertEquals(0, list.lastIndexOf("Element 0"));

        assertEquals(-1, list.lastIndexOf("Element Zero"));

    }

    @Test
    public void testLastIndexOfWithDuplicate() {

        List list = createList();

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element Copy");
        list.add("Element 3");
        list.add("Element Copy");
        list.add("Element Copy");
        list.add("Element 6");
        list.add("Element 7");
        list.add(null);
        list.add(null);
        list.add("Element 10");

        assertEquals(10, list.lastIndexOf("Element 10"));
        assertEquals(9, list.lastIndexOf(null));
        assertEquals(7, list.lastIndexOf("Element 7"));
        assertEquals(6, list.lastIndexOf("Element 6"));
        assertEquals(5, list.lastIndexOf("Element Copy"));
        assertEquals(3, list.lastIndexOf("Element 3"));
        assertEquals(1, list.lastIndexOf("Element 1"));
        assertEquals(0, list.lastIndexOf("Element 0"));

    }

    @Test
    public void testContains() {

        List list = createList();

        list.add("Element 0");
        list.add("Element 1");
        list.add(null);
        list.add("Element 3");
        list.add("Element 4");

        assertTrue(list.contains("Element 0"));
        assertTrue(list.contains("Element 1"));
        assertTrue(list.contains(null));
        assertTrue(list.contains("Element 3"));
        assertTrue(list.contains("Element 4"));

        assertFalse(list.contains("Element Zero"));

    }


}
