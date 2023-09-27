package plazma.ups.datastructures.list;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public abstract class AbstractListTest {

    abstract List createList();

    List createListWithThreeElements() {
        List list = createList();

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");

        return list;
    }

    @Test
    public void testAddElement() {

        List list = createList();

        list.add("Element 0");
        list.add(null);
        list.add("Element 2");
        assertEquals(3, list.size());

        assertEquals("Element 0", list.get(0));
        assertEquals(null, list.get(1));
        assertEquals("Element 2", list.get(2));

    }

    @Test
    public void testAddElementByIndex() {

        List list = createList();
        assertEquals(0, list.size());

        list.add("Element 0");
        list.add(null);
        list.add("Element 2");
        assertEquals(3, list.size());

        list.add("Element New", 1);
        assertEquals(4, list.size());

        assertEquals("Element 0", list.get(0));
        assertEquals("Element New", list.get(1));
        assertEquals(null, list.get(2));
        assertEquals("Element 2", list.get(3));

        list.add(null, 3);
        assertEquals(5, list.size());

        assertEquals("Element 0", list.get(0));
        assertEquals("Element New", list.get(1));
        assertEquals(null, list.get(2));
        assertEquals(null, list.get(3));
        assertEquals("Element 2", list.get(4));

    }

    @Test
    public void testThrowExceptionWhenAddElementOnEmptyList() {
        List list = createList();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", -1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", list.size() + 1);
        });
    }

    @Test
    public void testThrowExceptionWhenAddElementBelowRange() {
        List list = createListWithThreeElements();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", -1);
        });
    }

    @Test
    public void testThrowExceptionWhenAddElementAboveRange() {
        List list = createListWithThreeElements();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("Element", list.size() + 1);
        });

    }

    @Test
    public void testRemoveElementByIndex() {

        List list = createListWithThreeElements();
        assertEquals(3, list.size());

        Object value = list.remove(1);
        assertEquals("Element 1", value);

        assertEquals(2, list.size());

        assertEquals("Element 0", list.get(0));
        assertEquals("Element 2", list.get(1));


        list.add(null);
        list.add("New Element");
        list.add(null);

        assertEquals(5, list.size());

        value = list.remove(4);
        assertNull(value);
        assertEquals(4, list.size());

        assertEquals("Element 0", list.get(0));
        assertEquals("Element 2", list.get(1));
        assertEquals(null, list.get(2));
        assertEquals("New Element", list.get(3));

    }

    @Test
    public void testThrowExceptionWhenRemoveElementOnEmptyList() {
        List list = createList();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(0);
        });

    }

    @Test
    public void testThrowExceptionWhenRemoveElementBelowRange() {
        List list = createListWithThreeElements();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        });
    }

    @Test
    public void testThrowExceptionWhenRemoveElementAboveRange() {
        List list = createListWithThreeElements();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(list.size() + 1);
        });

    }

    @Test
    public void testIsEmpty() {

        List list = createList();
        assertTrue(list.isEmpty());

        list.add("Element 0");
        list.add("Element 1");

        assertFalse(list.isEmpty());

        list.remove(0);
        assertFalse(list.isEmpty());

        list.remove(0);
        assertTrue(list.isEmpty());

    }

    @Test
    public void testIsEmptyWithClear() {

        List list = createList();

        list.add("Element 0");
        list.add("Element 1");

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

        assertEquals(2, list.size());

        list.remove(0);
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

        assertEquals(2, list.size());

        list.clear();
        assertEquals(0, list.size());

    }

    @Test
    public void testClear() {

        List list = createList();

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list = createListWithThreeElements();

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
        list.add(null);
        list.add("Element 2");

        assertEquals("Element 0", list.get(0));
        assertEquals(null, list.get(1));
        assertEquals("Element 2", list.get(2));

    }

    @Test
    public void testSetElement() {

        List list = createList();

        list.add("Element 0");
        list.add(null);
        list.add("Element 2");

        assertEquals("Element 0", list.set("New Element 0", 0));
        assertEquals(null, list.set("New Element 1", 1));
        assertEquals("Element 2", list.set(null, 2));

        assertEquals("New Element 0", list.get(0));
        assertEquals("New Element 1", list.get(1));
        assertEquals(null, list.get(2));

    }

    @Test
    public void testThrowExceptionWhenGetElementOnEmptyList() {

        List list = createList();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(0);
        });

    }

    @Test
    public void testThrowExceptionWhenGetElementBelowRange() {

        List list = createListWithThreeElements();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });

    }

    @Test
    public void testThrowExceptionWhenGetElementAboveRange() {

        List list = createListWithThreeElements();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(list.size() + 1);
        });

    }

    //testThrowExceptionWhenSetElementOnEmptyList

    @Test
    public void testThrowExceptionWhenSetElementOnEmptyList() {

        List list = createList();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", -1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", 0);
        });

    }

    @Test
    public void testThrowExceptionWhenSetElementBelowRange() {

        List list = createListWithThreeElements();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", -1);
        });

    }

    @Test
    public void testThrowExceptionWhenSetElementAboveRange() {

        List list = createListWithThreeElements();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("Element", list.size() + 1);
        });

    }

    @Test
    public void testIndexOf() {

        List list = createList();

        list.add("Element 0");
        list.add(null);

        assertEquals(0, list.indexOf("Element 0"));
        assertEquals(1, list.indexOf(null));

        assertEquals(-1, list.indexOf("Element Zero"));

    }

    @Test
    public void testIndexOfWithDuplicate() {

        List list = createList();

        list.add("Element Copy");
        list.add(null);
        list.add("Element Copy");
        list.add(null);

        assertEquals(0, list.indexOf("Element Copy"));
        assertEquals(1, list.indexOf(null));
    }

    @Test
    public void testLastIndexOf() {

        List list = createList();

        list.add("Element 0");
        list.add(null);

        assertEquals(1, list.lastIndexOf(null));
        assertEquals(0, list.lastIndexOf("Element 0"));

        assertEquals(-1, list.lastIndexOf("Element Zero"));

    }

    @Test
    public void testLastIndexOfWithDuplicate() {

        List list = createList();

        list.add("Element Copy");
        list.add(null);
        list.add("Element Copy");
        list.add(null);

        assertEquals(3, list.lastIndexOf(null));
        assertEquals(2, list.lastIndexOf("Element Copy"));

    }

    @Test
    public void testContains() {

        List list = createList();

        list.add("Element 0");
        list.add(null);

        assertTrue(list.contains("Element 0"));
        assertTrue(list.contains(null));
        assertFalse(list.contains("Element Zero"));

    }

    @Test
    public void testEmptyIterator() {
        List list = createList();
        Iterator iterator = list.iterator();
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
        List list = createListWithThreeElements();
        Iterator iterator = list.iterator();
        assertNotNull(iterator);

        assertTrue(iterator.hasNext());

        assertEquals("Element 0", iterator.next());
        assertTrue(iterator.hasNext());

        assertEquals("Element 1", iterator.next());
        assertTrue(iterator.hasNext());

        assertEquals("Element 2", iterator.next());
        assertFalse(iterator.hasNext());

        assertThrows(NoSuchElementException.class, () -> {
            iterator.next();
        });

    }

    @Test
    public void testRemoveIterator() {
        List list = createListWithThreeElements();
        Iterator iterator = list.iterator();
        assertNotNull(iterator);

        assertTrue(iterator.hasNext());

        assertEquals("Element 0", iterator.next());
        assertTrue(iterator.hasNext());

        assertEquals("Element 1", iterator.next());
        assertTrue(iterator.hasNext());

        iterator.remove();

        assertEquals("Element 2", iterator.next());
        assertFalse(iterator.hasNext());

    }


}
