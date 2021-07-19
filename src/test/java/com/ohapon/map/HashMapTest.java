package com.ohapon.map;

import com.ohapon.datastructures.map.HashMap;
import com.ohapon.datastructures.map.Map;
import org.junit.Test;
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
        Map<Integer, String> map = createMapThreeElements();

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
        Map<Integer, String> map = createMapThreeElements();

        assertEquals(3, map.size());

        assertEquals("String 1", map.get(1));
        assertEquals("String 2", map.get(2));
        assertEquals("String 3", map.get(3));
        assertEquals(null, map.get(4));
    }

    @Test
    public void testContainsKey() {
        Map<Integer, String> map = createMapThreeElements();

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

        map = createMapThreeElements();
        assertFalse(map.isEmpty());

        map.put(2, "String 2 NEW");
        assertEquals("String 2 NEW", map.get(2));
        assertFalse(map.isEmpty());
    }

    @Test
    public void testSize() {

        Map<Integer, String> map = new HashMap<>();
        assertEquals(0, map.size());

        map = createMapThreeElements();
        assertEquals(3, map.size());

        map.put(2, "String 2 NEW");
        assertEquals("String 2 NEW", map.get(2));
        assertEquals(3, map.size());
    }

    private Map<Integer, String> createMapThreeElements() {
        Map<Integer, String> map = new HashMap<>();
        assertEquals(0, map.size());

        map.put(1, "String 1");
        map.put(2, "String 2");
        map.put(3, "String 3");

        return map;
    }

}
