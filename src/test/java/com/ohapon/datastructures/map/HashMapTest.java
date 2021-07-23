package com.ohapon.datastructures.map;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class HashMapTest {

    @Test
    public void testPutElement() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, "String 1");
        map.put(2, "String 2");
        map.put(3, "String 3");

        assertEquals(3, map.size());

        assertEquals("String 1", map.get(1));
        assertEquals("String 2", map.get(2));
        assertEquals("String 3", map.get(3));

        map.put(2, "String 2 NEW");
        assertEquals("String 2 NEW", map.get(2));
    }

    @Test
    public void testRemoveElement() {
        Map<Integer, String> map = createMapWithThreeElements();

        map.put(1, "String 1");
        map.put(2, "String 2");
        map.put(3, "String 3");

        assertEquals(3, map.size());

        assertEquals("String 1", map.remove(1));

        assertEquals(2, map.size());

        assertEquals(null, map.get(1));
        assertEquals("String 2", map.get(2));
        assertEquals("String 3", map.get(3));
    }

    @Test
    public void testGetElement() {
        Map<Integer, String> map = createMapWithThreeElements();

        assertEquals(3, map.size());

        assertEquals("String 1", map.get(1));
        assertEquals("String 2", map.get(2));
        assertEquals("String 3", map.get(3));
        assertEquals(null, map.get(4));
    }

    @Test
    public void testContainsKey() {
        Map<Integer, String> map = createMapWithThreeElements();

        assertEquals(3, map.size());

        assertTrue(map.containsKey(1));
        assertTrue(map.containsKey(2));
        assertTrue(map.containsKey(3));

        assertFalse(map.containsKey(4));

    }

    @Test
    public void testIsEmpty() {

        Map<Integer, String> map = new HashMap<>();
        assertTrue(map.isEmpty());

        map = createMapWithThreeElements();
        assertFalse(map.isEmpty());

        map.put(2, "String 2 NEW");
        assertEquals("String 2 NEW", map.get(2));
        assertFalse(map.isEmpty());
    }

    @Test
    public void testSize() {

        Map<Integer, String> map = new HashMap<>();
        assertEquals(0, map.size());

        map = createMapWithThreeElements();
        assertEquals(3, map.size());

        map.put(2, "String 2 NEW");
        assertEquals("String 2 NEW", map.get(2));
        assertEquals(3, map.size());
    }

    @Test
    public void testEmptyIterator() {
        Map map = new HashMap();
        Iterator iterator = map.iterator();
        assertNotNull(iterator);

        assertFalse(iterator.hasNext());

        assertThrows(NoSuchElementException.class, () -> {
            iterator.next();
        });

        assertThrows(IllegalStateException.class, () -> {
            iterator.remove();
        });

    }

    @Test
    public void testNextIterator() {
        Map map = createMapWithThreeElements();
        Iterator iterator = map.iterator();
        assertNotNull(iterator);

        assertTrue(iterator.hasNext());

        assertNotNull(iterator.next());
        assertNotNull(iterator.next());
        assertNotNull(iterator.next());

        assertFalse(iterator.hasNext());

        assertThrows(NoSuchElementException.class, () -> {
            iterator.next();
        });

    }

    @Test
    public void testRemoveIterator() {
        Map map = createMapWithThreeElements();
        Iterator iterator = map.iterator();
        assertNotNull(iterator);

        assertTrue(iterator.hasNext());

        assertNotNull(iterator.next());
        iterator.remove();

        assertNotNull(iterator.next());
        iterator.remove();

        assertNotNull(iterator.next());
        iterator.remove();

        assertFalse(iterator.hasNext());

    }


    private Map<Integer, String> createMapWithThreeElements() {
        Map<Integer, String> map = new HashMap<>();
        assertEquals(0, map.size());

        map.put(1, "String 1");
        map.put(2, "String 2");
        map.put(3, "String 3");

        return map;
    }

}
